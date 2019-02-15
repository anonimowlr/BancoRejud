package rotinasPortal;

import static rotinasPortal.Coletas.driver;
import static rotinasPortal.Coletas.nomeElemento;
import static rotinasPortal.Coletas.valorElemento;
import conexao.ConnectionFactory;
import dao.DAO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import entidade.Usuario;

public class CriarSolicitacaoLevPortal extends Thread {

    Usuario user = new Usuario();
    String matricula = user.getMatricula();
    String msgfim = "";
    static String  usuarioResponsavel;
    DAO<String> d = new DAO<>("rejud");

    Coletas coletas = new Coletas();

    @Override
    public void run() {

       coletas.autenticarUsuario();
        
        try {
            selecionarResponsavelDestinacao();
        } catch (SQLException ex) {
            Logger.getLogger(CriarSolicitacaoLevPortal.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }
    
    public void selecionarResponsavelDestinacao() throws SQLException{
        coletas.setSize();
        usuarioResponsavel = JOptionPane.showInputDialog(null, "Informe o responsavel para o qual deseja incluir as solicitações");
        driver.manage().window().maximize();
        verSolicitacaoExistente();
        
    }

    private void verSolicitacaoExistente() throws SQLException {

        if (matricula == null) {
            JOptionPane.showMessageDialog(null, "Sem usuário logado, não será possível continuar");
            return;

        }
        
       
        
        
        coletas.setURL("https://juridico.intranet.bb.com.br/paj/levantamento/solicitacao/pesquisar");

        PreparedStatement stmt;
        Connection con;
        con = ConnectionFactory.conectar("rejud");

        String query = "Select * from tb_solicitacao_levantamento "
                + "where ((OBS is null or OBS = '') and RESPONSAVEL_SOLICITACAO_INCLUSAO = ?)";

        stmt = con.prepareStatement(query);
        //stmt.setString(1, "Nenhum registro encontrado.");
        stmt.setString(1, usuarioResponsavel);
        
        
        if (user.getGrupoUsuario().equals("ADMINISTRADOR")) {
            query = "Select * from tb_solicitacao_levantamento "
                    + "where (OBS is null or OBS = '') "
//                    + "and responsavel_solicitacao_inclusao = 'f6002164'"
                    + "order by CODIGO DESC";
            stmt = con.prepareStatement(query);
            
        }
        
        ResultSet rs = stmt.executeQuery();
        
        

        int numeroReg = d.contareg(rs);

        if (numeroReg < 1) {

            coletas.pausar(10000);
            stmt.close();
            con.close();
            rs.close();
            if (user.getGrupoUsuario().equals("ADMINISTRADOR")){
            verSolicitacaoExistente();
            } else{
                coletas.setSize();
                JOptionPane.showMessageDialog(null, "Fim da rotina!!");
                driver.close();
                stop();
                
                
            }
        }

        do {
            //while (rs.next()){
            nomeElemento = "";
            valorElemento = "";

            driver.manage().window().maximize();

            coletas.setURL("https://juridico.intranet.bb.com.br/paj/levantamento/solicitacao/pesquisar");
            coletas.clickElementId(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:linkDados");

            nomeElemento = "pesquisarSolicitacaoLevantamentoDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoInput";

            String valorResgateBanco = rs.getString("VALOR_SOLICITACAO");
            BigDecimal valorBanco = coletas.tratarNumero(valorResgateBanco);
            String valorResgateTratado = valorBanco.toString();
            String oficio = rs.getString("OFICIO");
            String dataOficio = rs.getString("DATA_OFICIO");
            String dataOficioTratada = coletas.tratarVariavel(dataOficio);
            String validadeOficio = coletas.validadeOficio();
            String enderecoDocumento1 = rs.getString("ENDERECO_DOCUMENTO1");
            String enderecoDocumento2 = rs.getString("ENDERECO_DOCUMENTO2");
            String responsavelSolicitacao = rs.getString("RESPONSAVEL_SOLICITACAO_INCLUSAO");
            String saldoBanco = rs.getString("SALDO");
            BigDecimal saldoBancoConvertido = coletas.tratarNumero(saldoBanco);
            String saldoBancoTratado = saldoBancoConvertido.toString();
            String justificativa = rs.getString("HISTORICO");

            String tipLevt = rs.getString("TIPO_LEVANTAMENTO");

            String npj = rs.getString("NPJ");
            String variacao = rs.getString("VARIACAO_NPJ");

            valorElemento = rs.getString("NPJ").subSequence(0, 4).toString();
            coletas.aguardaElementoTelaByName(nomeElemento);
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);

            nomeElemento = "pesquisarSolicitacaoLevantamentoDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoNumeroProcessoInput";
            int tamanhoNpj = coletas.calculaTamanhoValorElemento(rs.getString("NPJ"));
            valorElemento = rs.getString("NPJ").subSequence(4, tamanhoNpj).toString();
            coletas.aguardaElementoTelaByName(nomeElemento);
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);

            nomeElemento = "pesquisarSolicitacaoLevantamentoDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoVariacaoInput";
            valorElemento = rs.getString("VARIACAO_NPJ");
            coletas.aguardaElementoTelaByName(nomeElemento);
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);

            coletas.aguardaElementoTelaByName("pesquisarSolicitacaoLevantamentoDepositoForm:btPesquisar");
            coletas.pausar(1000);
            coletas.clickElementId(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:btPesquisar");
            coletas.pausar(1000);

            if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                if (!n.equals("")) {
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_solicitacao_levantamento","OBS", n, codigo);
                    verSolicitacaoExistente();
                }
            }
            if (coletas.isElementPresentID(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:j_id433")) {
                String n = coletas.lerValorElementoID("pesquisarSolicitacaoLevantamentoDepositoForm:j_id433");
                if (!n.equals("")) {

                    criarSolicitacao(npj, valorResgateTratado, variacao, saldoBancoConvertido, tipLevt, oficio, dataOficioTratada, validadeOficio, justificativa, enderecoDocumento1, enderecoDocumento2);

                    if (!msgfim.equals("")) {
                        int codigo = rs.getInt("CODIGO");
                        con.close();
                        rs.close();
                        stmt.close();
                        d.editar("tb_solicitacao_levantamento","OBS", msgfim, codigo);
                        verSolicitacaoExistente();
                    }

                }

            }

            Boolean p = coletas.isElementPresentID(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:j_id426");

            if (p) {
                String n = coletas.lerValorElementoID("pesquisarSolicitacaoLevantamentoDepositoForm:j_id426");
                if (!n.equals("")) {
                    con.close();
                    rs.close();
                    stmt.close();
                    int codigo = rs.getInt("CODIGO");
                    d.editar("tb_solicitacao_levantamento","ID_SITUACAO", n, codigo);
                    //interacao();
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
                    coletas.aguardaElementoTelaByID("pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:10:j_id669");

                }

            }

            for (int reg = 0; reg <= qtdreg; reg++) {

                if (reg == qtdreg) {
                    criarSolicitacao(npj, valorResgateTratado, variacao, saldoBancoConvertido, tipLevt, oficio, dataOficioTratada, validadeOficio, justificativa, enderecoDocumento1, enderecoDocumento2);

                    if (!msgfim.equals("")) {
                        int codigo = rs.getInt("CODIGO");
                        con.close();
                        rs.close();
                        stmt.close();
                        d.editar("tb_solicitacao_levantamento","OBS", msgfim, codigo);
                        verSolicitacaoExistente();

                    }
                }

                nomeElemento = "pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":oficioDecorate:oficioOutput";
                String ordem = coletas.lerValorElementoID(nomeElemento);

                int tamanhoOrdem = coletas.calculaTamanhoValorElemento(oficio);
                if (tamanhoOrdem >= 15) {
                    oficio = rs.getString("OFICIO").subSequence(0, 15).toString();
                }
                nomeElemento = "pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":valorDecorate:valorOutput";
                String valorElemento = coletas.lerValorElementoID(nomeElemento);
                int tamanho = coletas.calculaTamanhoValorElemento(valorElemento);

                String valorResgate = coletas.lerValorElementoID(nomeElemento).subSequence(3, tamanho).toString();
                BigDecimal valorPortal = coletas.tratarNumero(valorResgate);
                String situacaoPortal;

                if (valorBanco.equals(valorPortal)) {

                    Boolean b = coletas.isElementPresentID(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":situacaoDecorate:situacaoOutput");

                    if (b) {
                        situacaoPortal = coletas.lerValorElementoID("pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":situacaoDecorate:situacaoOutput");
                        if (!situacaoPortal.equals("Excluída")) {
                            coletas.setSize();
                            

                            int i = JOptionPane.showConfirmDialog(null, "Já existe solicitação de levantamento para: " + valorBanco + " " + "NPJ:" + npj + " " +  "Solicitado por " + responsavelSolicitacao + ", Deseja incluir outra?",
                                    "Inclusao de solicitação de levantamento", JOptionPane.YES_NO_OPTION);

                            if (i == JOptionPane.NO_OPTION) {

                                int codigo = rs.getInt("CODIGO");
                                con.close();
                                rs.close();
                                stmt.close();
                                d.editar("tb_solicitacao_levantamento","OBS", "Já existe solicitação deste valor", codigo);
                                verSolicitacaoExistente();
                            } else {
                                driver.manage().window().maximize();
                                criarSolicitacao(npj, valorResgateTratado, variacao, saldoBancoConvertido, tipLevt, oficio, dataOficioTratada, validadeOficio, justificativa, enderecoDocumento1, enderecoDocumento2);
                                if (!msgfim.equals("")) {
                                    int codigo = rs.getInt("CODIGO");
                                    con.close();
                                    rs.close();
                                    stmt.close();
                                    d.editar("tb_solicitacao_levantamento","OBS", msgfim, codigo);
                                    verSolicitacaoExistente();

                                }

                            }

                        }
                    }

                }

            }
            con.close();
            stmt.close();
            rs.close();

        } while (rs.next());
        
        coletas.setSize();
        JOptionPane.showMessageDialog(null, " Fim da rotina!!");
        con.close();
        stmt.close();
        rs.close();

    }

    public void criarSolicitacao(String npj, String valor, String variacao, BigDecimal saldo, String tipLevt, String oficio, String dataOficioTratada, String validadeOficio, String justificativa, String enderecoDocumento1, String enderecoDocumento2) throws SQLException {

        while (coletas.isElementPresentID(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:j_id74")) {
            coletas.clickElementId(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:j_id74");
        }

        coletas.aguardaElementoTelaByID("incluirLevantamentoForm:slDepositoDecorate:j_id93");
        coletas.clickElementId(driver, "incluirLevantamentoForm:slDepositoDecorate:j_id93");
        coletas.aguardaElementoTelaByID("pesquisarSolicitacaoDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoInput");
        coletas.procuraElementoPorId(driver, "pesquisarSolicitacaoDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoInput", npj.subSequence(0, 4).toString());
        int tamanhoNpj = coletas.calculaTamanhoValorElemento(npj);
        coletas.procuraElementoPorId(driver, "pesquisarSolicitacaoDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoNumeroProcessoInput", npj.subSequence(4, tamanhoNpj).toString());
        coletas.procuraElementoPorId(driver, "pesquisarSolicitacaoDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoVariacaoInput", variacao);
        coletas.clickElementId(driver, "pesquisarSolicitacaoDepositoForm:btPesquisar");

        if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

            String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

            if (!n.equals("")) {
                msgfim = n;
                return;
            }
        }

        if (coletas.isElementPresentID(driver, "pesquisarSolicitacaoDepositoForm:j_id261")) {
            String n = coletas.lerValorElementoID("pesquisarSolicitacaoDepositoForm:j_id261");
            if (!n.equals("")) {

                msgfim = n;
                return;
            }

        }

        // ler quantidade de depósitos listados na página.
        String n = coletas.lerValorElementoID("pesquisarSolicitacaoDepositoForm:j_id265");

        Integer qtdreg = Integer.parseInt(coletas.quantidadeRegistrosListados(n));

        for (int reg = 0; reg <= qtdreg; reg++) {

            if (reg == 10 || reg == 20 || reg == 30 || reg == 40 || reg == 50 || reg == 50) {

                coletas.clickElementXpath(driver, ".//*[@id='pesquisarSolicitacaoDepositoForm:dataTabletableDep:ds_table']/tbody/tr/td[6]");

            }

            if (reg == qtdreg) {
                msgfim = "Saldo divergente do informado, apague esta mensagem e corrija o saldo!!";
                return;

            }

            String valorContabil = coletas.lerValorElementoID("pesquisarSolicitacaoDepositoForm:dataTabletableDep:" + reg + ":tbVlr2Decorate:tbVlr2Output");
            int tamanho = coletas.calculaTamanhoValorElemento(valorContabil);
            valorContabil = valorContabil.subSequence(3, tamanho).toString();
            BigDecimal valorContabilConvertido = coletas.tratarNumero(valorContabil);

            if (valorContabilConvertido.equals(saldo)) {

                coletas.pausar(1000);
                coletas.clickElementXpath(driver, ".//*[@id='pesquisarSolicitacaoDepositoForm:dataTabletableDep:" + reg + ":j_id290']/input");

                coletas.clickElementId(driver, "pesquisarSolicitacaoDepositoForm:btSelecionarr");

                if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                    n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                    if (!n.equals("")) {
                        msgfim = n;
                        return;
                    }
                }

                coletas.aguardaElementoTelaByID("incluirLevantamentoForm:valorCausaInclusaoDecorate:valorCausaInclusaoInput");

                if (tipLevt.equals("Total")) {

                    coletas.clickElementId(driver, "incluirLevantamentoForm:tipoLevantamentoSelectDecorate:tipoLevantamentoSelectRadio:1");
                } else {
                    coletas.clickElementId(driver, "incluirLevantamentoForm:tipoLevantamentoSelectDecorate:tipoLevantamentoSelectRadio:0");
                }

                coletas.procuraElementoPorId(driver, "incluirLevantamentoForm:valorCausaInclusaoDecorate:valorCausaInclusaoInput", valor);
                coletas.pausar(1000);
                coletas.clickElementId(driver, "incluirLevantamentoForm:dtOficioDecorate:dtOficioInputInputDate");
                coletas.procuraElementoPorId(driver, "incluirLevantamentoForm:dtOficioDecorate:dtOficioInputInputDate", dataOficioTratada);
                coletas.pausar(1000);
                coletas.procuraElementoPorId(driver, "incluirLevantamentoForm:numOficioDecorate:numOficioInput", oficio);
                coletas.pausar(1000);
                coletas.clickElementId(driver, "incluirLevantamentoForm:dtOficioDecorate:dtOficioInputInputDate");
                coletas.clickElementId(driver, "incluirLevantamentoForm:dtValidadeOrdemDecorate:dtValidadeOrdemInputInputDate");
                coletas.pausar(1000);
                coletas.procuraElementoPorId(driver, "incluirLevantamentoForm:dtValidadeOrdemDecorate:dtValidadeOrdemInputInputDate", validadeOficio);
                coletas.pausar(1000);
               
                
                coletas.clickElementId(driver, "incluirLevantamentoForm:ObservacaoDecorate:ObservacaoInput");

                coletas.procuraElementoPorId(driver, "incluirLevantamentoForm:ObservacaoDecorate:ObservacaoInput", justificativa);

                coletas.clickElementId(driver, "incluirLevantamentoForm:btIncluirDocumento");

                if (coletas.isElementPresentID(driver, "incluirLevantamentoForm:dtOficioDecorate:j_id1095")) {

                    n = coletas.lerValorElementoID("incluirLevantamentoForm:dtOficioDecorate:j_id1095");

                    if (!n.equals("")) {
                        msgfim = n;
                        return;
                    }

                }

          
                coletas.aguardaElementoTelaByID("pesquisarDocumentoForm:j_id432");
                coletas.procuraElementoPorId(driver, "pesquisarDocumentoForm:j_id432", enderecoDocumento1);
                coletas.clickElementId(driver, "pesquisarDocumentoForm:btSalvar");
                coletas.pausar(1000);
                coletas.aguardaElementoTelaByID("modaldivMessagesGlobal");
                
                if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                    n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                    if (!n.equals("")) {
                        msgfim = n;
                        return;
                    }
                }

                coletas.aguardaElementoTelaByID("pesquisarDocumentoForm:botaoOk");
                coletas.clickElementId(driver, "pesquisarDocumentoForm:botaoOk");

                if (!enderecoDocumento2.equals("")) {
                    coletas.clickElementId(driver, "incluirLevantamentoForm:btIncluirDocumento");
                    coletas.aguardaElementoTelaByID("pesquisarDocumentoForm:j_id432");
                    coletas.procuraElementoPorId(driver, "pesquisarDocumentoForm:j_id432", enderecoDocumento2);
                    coletas.clickElementId(driver, "pesquisarDocumentoForm:btSalvar");
                    coletas.aguardaElementoTelaByID("modaldivMessagesGlobal");

                    if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                        n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                        if (!n.equals("")) {
                            msgfim = n;
                            return;
                        }
                    }

                    coletas.aguardaElementoTelaByID("pesquisarDocumentoForm:botaoOk");
                    coletas.clickElementId(driver, "pesquisarDocumentoForm:botaoOk");

                }

                coletas.clickElementId(driver, "incluirLevantamentoForm:btSalvarInclusao");

                coletas.aguardaElementoTelaByID("modaldivMessagesGlobal");

                if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                    n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                    if (!n.equals("")) {
                        msgfim = n;
                        return;
                    }
                }

                verSolicitacaoExistente();

            }

        }

    }

}
