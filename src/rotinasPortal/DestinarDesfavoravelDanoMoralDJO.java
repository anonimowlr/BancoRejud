/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rotinasPortal;

import dao.DesconciliacaoDAO;
import dao.DestinarDesfavoravelDanoMoralDjoDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import entidade.Resgate;
import entidade.Usuario;
import static rotinasPortal.Coletas.driver;
import rotinasWeb.AtualizacaoWeb;
import util.Utils;

/**
 *
 * @author f5078775
 */
public class DestinarDesfavoravelDanoMoralDJO extends Thread {
    
    private final String bancoDados = "rejud";
    List<Resgate> lista = null;
    
    DesconciliacaoDAO desconciliacaoDAO = new DesconciliacaoDAO(bancoDados);
    AtualizacaoWeb atualizacaoWeb = new AtualizacaoWeb(bancoDados);
    DestinarDesfavoravelDanoMoralDjoDAO<Resgate> destinarDesfavoravelDAO = new DestinarDesfavoravelDanoMoralDjoDAO<>(bancoDados);
    Coletas coletas = new Coletas();
    Usuario user = new Usuario();

    public DestinarDesfavoravelDanoMoralDJO() {
    }

    @Override
    public void run() {

        if (user.getMatricula() == null) {
            JOptionPane.showMessageDialog(null, "Sem usuário logado");
            this.stop();
        }

        createList();
        try {
            lerLista();
        } catch (SQLException ex) {
            Logger.getLogger(DestinarDesfavoravelDanoMoralDJO.class.getName()).log(Level.SEVERE, null, ex);
        }
        coletas.setSize();
        JOptionPane.showMessageDialog(null, "fim de rotina");
        coletas.encerraPortal(driver);

    }

    public void createList() {

        try {
            lista = destinarDesfavoravelDAO.buscar();
            if (lista.size() < 1) {
                JOptionPane.showMessageDialog(null, "Não há registros a contabilizar desf dano moral");
            } else {
                coletas.autenticarUsuario();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void lerLista() throws SQLException {

        int n = 0;
        int qtdreg = lista.size();

        for (Resgate s : lista) {

            Integer codigo = s.getCod();
            String idNpj = s.getIdNpj();
            String npj = s.getNpj();
            String levantador = "Adverso ou terceiro";
            String favorecido = s.getProcesAutor();
            String finalidade = "Condenacoes e Acordos";
            String especificacao = "Dano Moral, Dano Material e Repeticao de Indebito";
            String valorResgate = s.getIDValor().toString();
            String ordem = s.getSolicOrdem();

            
            String contaDepositaria = s.getContaDepositaria();

            procuraResgatePortalDestinacao(levantador, finalidade, especificacao, favorecido, idNpj,contaDepositaria, valorResgate, ordem, codigo);

            n++;

            if (n == qtdreg) {
//                desconciliacaoDAO.atualizacaoOnline();
                
                atualizacaoWeb.atualizacaoOnline();
            }

        }

    }

    public void procuraResgatePortalDestinacao(String levantador, String finalidade, String especificacao, String favorecido, String npj, String contaDepositaria, String valorResgate, String ordem, int codigo) {

        String nomeElemento;
        String valorElemento;
        driver.manage().window().maximize();

        coletas.setURL("https://juridico.intranet.bb.com.br/paj/levantamento/solicitacao/pesquisar");
        coletas.clickElementId(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:linkDados");

        nomeElemento = "pesquisarSolicitacaoLevantamentoDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoInput";
        valorElemento = npj.subSequence(0, 4).toString();
        coletas.aguardaElementoTelaByName(nomeElemento);
        coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);

        nomeElemento = "pesquisarSolicitacaoLevantamentoDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoNumeroProcessoInput";
        int tamanhoNpj = coletas.calculaTamanhoValorElemento(npj);
        valorElemento = npj.subSequence(4, tamanhoNpj).toString();
        coletas.aguardaElementoTelaByName(nomeElemento);
        coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);
        //coletas.pausar(1000);

        nomeElemento = "pesquisarSolicitacaoLevantamentoDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoVariacaoInput";
        valorElemento = npj.subSequence(13, tamanhoNpj).toString();
        coletas.aguardaElementoTelaByName(nomeElemento);
        coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);

        coletas.selecionarElementoName("pesquisarSolicitacaoLevantamentoDepositoForm:j_id140", 8);

        coletas.aguardaElementoTelaByName("pesquisarSolicitacaoLevantamentoDepositoForm:btPesquisar");
        coletas.pausar(1000);
        coletas.clickElementId(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:btPesquisar");
        coletas.pausar(1000);

        if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

            String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

            if (!n.equals("")) {

                destinarDesfavoravelDAO.editarGenerico("tb_cpj_cadastro", "OBS_CONTABILIZACAO", n, codigo);
                return;
            }
        }

        Boolean p = coletas.isElementPresentID(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:j_id433");

        if (p) {
            String n = coletas.lerValorElementoID("pesquisarSolicitacaoLevantamentoDepositoForm:j_id433");
            if (!n.equals("")) {
                destinarDesfavoravelDAO.editarGenerico("tb_cpj_cadastro", "OBS_CONTABILIZACAO", n, codigo);
                return;
            }

        }

        // ler quantidade de solicitações de levantamento listada na página.
        nomeElemento = "pesquisarSolicitacaoLevantamentoDepositoForm:j_id436";
        String n = coletas.lerValorElementoID(nomeElemento);

        Integer qtdreg = Integer.parseInt(coletas.quantidadeRegistrosListados(n));

        if (qtdreg > 10) {

            Boolean b = coletas.isElementPresentID(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:j_id465");

            if (b) {
                coletas.clickElementId(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:j_id465");
                coletas.aguardaElementoTelaByID("pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:10:seta");

            }

        }

        for (int reg = 0; reg < qtdreg; reg++) {

            String ordemPortal = coletas.lerValorElementoID("pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":j_id559");
            String contaDepositariaPortal = coletas.lerValorElementoID("pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":j_id646");
            String valorResgatePortal = coletas.lerValorElementoID("pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":j_id580");

            if (valorResgate.equals(Utils.tratarVariavel(valorResgatePortal)) && ordem.equals(ordemPortal)  && contaDepositariaPortal.equals(contaDepositaria)) {

                if(coletas.isElementPresentID(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":seta")){
                  coletas.clickElementId(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":seta");   
                } else{
                 destinarDesfavoravelDAO.editarGenerico("tb_cpj_cadastro", "OBS_CONTABILIZACAO", "Sem seta para destinar", codigo); 
                 return;
                }
              
                
                if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                    n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                    if (!n.equals("")) {

                        destinarDesfavoravelDAO.editarGenerico("tb_cpj_cadastro", "OBS_CONTABILIZACAO", n, codigo);
                        return;
                    }
                }

                coletas.destinarLevantamentoDjo(levantador, finalidade, especificacao, favorecido, valorResgate);

//                coletas.aguardaElementoTelaByID("incluirDepositoAdversoForm:btSalvar");
                coletas.aguardaElementoTelaByID("incluirDestinacaoForm:btIncluir");
 
                coletas.clickElementId(driver, "incluirDestinacaoForm:btIncluir");
                if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                    n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                    if (!n.equals("")) {

                        destinarDesfavoravelDAO.editarGenerico("tb_cpj_cadastro", "OBS_CONTABILIZACAO", n, codigo);
                        return;
                    }
                }
                
  //                while(coletas.isElementPresentID(driver, "incluirDepositoAdversoForm:btSalvar")){
                while (coletas.isElementPresentID(driver, "incluirDestinacaoForm:btIncluir")) {

                    coletas.clickElementId(driver, "incluirDestinacaoForm:btIncluir");
                    if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                        n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                        if (!n.equals("")) {

                            destinarDesfavoravelDAO.editarGenerico("tb_cpj_cadastro", "OBS_CONTABILIZACAO", n, codigo);
                            return;
                        }
                    }
                }
            
                
//                coletas.aguardaElementoTelaByID("incluirDepositoAdversoForm:txjustificativaDecorate:txjustificativaInput");
                coletas.aguardaElementoTelaByID("incluirDestinacaoForm:txjustificativaDecorate:txjustificativaInput");
                coletas.procuraElementoPorId(driver, "incluirDestinacaoForm:txjustificativaDecorate:txjustificativaInput", "Contabilização efetuada cfe IN 869-2  item 2.4.1");

                coletas.clickElementId(driver, "incluirDestinacaoForm:btConcluir");

                if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                    n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                    if (!n.equals("")) {

                        destinarDesfavoravelDAO.editarGenerico("tb_cpj_cadastro", "OBS_CONTABILIZACAO", n, codigo);
                        return;
                    }
                }

                return;

            }

        }

    }
}
