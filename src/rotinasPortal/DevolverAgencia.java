/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rotinasPortal;

import dao.DesconciliacaoDAO;
import dao.DevolverAgenciaDAO;
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
public class DevolverAgencia extends Thread {

    DesconciliacaoDAO desconciliacaoDAO = new DesconciliacaoDAO("rejud");
    AtualizacaoWeb atualizacaoWeb = new AtualizacaoWeb("rejud");
    
    Coletas coletas = new Coletas();
    List<Resgate> lista = null;
    Usuario user = new Usuario();
    DevolverAgenciaDAO<Resgate> devolverAgenciaDAO = new DevolverAgenciaDAO<>();

    public DevolverAgencia() {
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
            Logger.getLogger(DevolverAgencia.class.getName()).log(Level.SEVERE, null, ex);
        }
        coletas.setSize();
        JOptionPane.showMessageDialog(null, "fim de rotina");
        coletas.encerraPortal(driver);

    }

    public void createList() {

        try {
            lista = devolverAgenciaDAO.buscar();
            if (lista.size() < 1) {
                JOptionPane.showMessageDialog(null, "Não há registros a devolver");
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

            String valorResgate = s.getIDValor().toString();
            String ordem = s.getSolicOrdem();
            String contaDepositaria = s.getContaDepositaria();
            String agenciaResponsavelMandado = s.getDjo51AgenciaResponsavel();

            procuraResgatePortalDevolver(idNpj, contaDepositaria, valorResgate, agenciaResponsavelMandado, ordem, codigo);

            n++;

            if (n == qtdreg) {
//                desconciliacaoDAO.atualizacaoOnline();                
                atualizacaoWeb.atualizacaoOnline();
            }

        }

    }

    public void procuraResgatePortalDevolver(String npj, String contaDepositaria, String valorResgate, String agenciaResponsavelMandado, String ordem, int codigo) {

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
        coletas.pausar(2000);

        
        if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

            String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

            if (!n.equals("")) {

                devolverAgenciaDAO.editarGenerico("tb_cpj_cadastro", "OBS_DEVOLUCAO", n, codigo);
                return;
            }
        }

        Boolean p = coletas.isElementPresentID(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:j_id433");

        if (p) {
            String n = coletas.lerValorElementoID("pesquisarSolicitacaoLevantamentoDepositoForm:j_id433");
            if (!n.equals("")) {
                devolverAgenciaDAO.editarGenerico("tb_cpj_cadastro", "OBS_DEVOLUCAO", n, codigo);
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

        coletas.pausar(1000);
        
        for (int reg = 0; reg < qtdreg; reg++) {

            String ordemPortal = coletas.lerValorElementoID("pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":j_id559");
            String contaDepositariaPortal = coletas.lerValorElementoID("pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":j_id646");
            String valorResgatePortal = coletas.lerValorElementoID("pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":j_id580");

            if (valorResgate.equals(Utils.tratarVariavel(valorResgatePortal)) && ordem.equals(ordemPortal) && contaDepositariaPortal.equals(contaDepositaria)) {

                if (coletas.isElementPresentID(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":seta")) {
                    coletas.clickElementId(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":seta");
                } else {
                    devolverAgenciaDAO.editarGenerico("tb_cpj_cadastro", "OBS_DEVOLUCAO", "Sem seta para devolver", codigo);
                    return;
                }

                if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                    n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                    if (!n.equals("")) {

                        devolverAgenciaDAO.editarGenerico("tb_cpj_cadastro", "OBS_DEVOLUCAO", n, codigo);
                        return;
                    }
                }

                //verificar se ocorreu erro f4281065
                if (driver.getPageSource().toLowerCase().contains("houve um problema no processamento de sua")) {
                    //deu erro, pular esta conta, vamos para a proxima...
                    return;
                }

                //coletas.destinarLevantamentoDjo(levantador, finalidade, especificacao, favorecido, valorResgate);

                coletas.pausar(1000);
                
                if (coletas.isElementPresentID(driver, "formBotoesFinalizar:btRecusar")) {
                    coletas.clickElementId(driver, "formBotoesFinalizar:btRecusar");
                } else {
                    return;
                }
                
//                coletas.aguardaElementoTelaByID("formBotoesFinalizar:btRecusar");
//                coletas.clickElementId(driver, "formBotoesFinalizar:btRecusar");

                if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                    n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                    if (!n.equals("")) {

                        devolverAgenciaDAO.editarGenerico("tb_cpj_cadastro", "OBS_DEVOLUCAO", n, codigo);
                        return;
                    }
                }

                
                
                if(coletas.isElementPresentXpath(driver, "html/body/div[1]/div[4]/div[2]/div/div[1]/div[1]/form[1]/table/tbody/tr/td/table/tbody/tr/td/div/div[1]/a")){
                    
                        devolverAgenciaDAO.editarGenerico("tb_cpj_cadastro", "OBS_DEVOLUCAO", "Com documento vinculado", codigo);
                        return;
               
                }
                
                
                coletas.aguardaElementoTelaByID("formJustificativa:pesquisarDependenciaDecorate:j_id747"); //746
                coletas.pausar(1000);
                coletas.clickElementId(driver, "formJustificativa:pesquisarDependenciaDecorate:j_id747"); //746
                
                coletas.pausar(3000);
                

                coletas.aguardaElementoTelaByID("includePopup:pesquisarDependenciaForm:prefixoDecorate:prefixoInput");
                
                coletas.procuraElementoPorId(driver, "includePopup:pesquisarDependenciaForm:prefixoDecorate:prefixoInput", agenciaResponsavelMandado);
                
                coletas.aguardaElementoTelaByID("includePopup:pesquisarDependenciaForm:pesquisarDependencia");

                coletas.clickElementId(driver, "includePopup:pesquisarDependenciaForm:pesquisarDependencia");
                                
                coletas.aguardaElementoTelaByID("includePopup:pesquisarDependenciaForm:dataTablePopupUnidadesOrganizacionais:0:selecionar");

                coletas.clickElementId(driver,  "includePopup:pesquisarDependenciaForm:dataTablePopupUnidadesOrganizacionais:0:selecionar");
                                
                coletas.pausar(2000);
     
                coletas.procuraElementoPorId(driver, "formJustificativa:txJustificativaDecorate:txJustificativaInput", "Favor anexar alvará de levantamento cfe. Previsto na IN 869-2 - item 2.4.1.2.6, para fins de nossa destinação");

                coletas.clickElementId(driver, "formJustificativa:btRecusarSolicitacao");

                
                if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {
                    n = coletas.lerValorElementoID("modaldivMessagesGlobal");
                    if (!n.equals("")) {
                        devolverAgenciaDAO.editarGenerico("tb_cpj_cadastro", "OBS_DEVOLUCAO", n, codigo);
                        return;
                    }
                }

                return;

            }

        }

    }
    
}
