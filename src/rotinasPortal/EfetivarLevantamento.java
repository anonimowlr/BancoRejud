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
import javax.swing.JOptionPane;
import entidade.Usuario;

public class EfetivarLevantamento extends Thread {

    DAO<String> d = new DAO<>("rejud");

    Coletas coletas = new Coletas();

    public EfetivarLevantamento() {

    }

    @Override
    public void run() {

        coletas.autenticarUsuario();
        try {
            interacao();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);

        }
    }

    private void interacao() throws SQLException {
        Usuario user = new Usuario();
        String matricula = user.getMatricula();

        if (matricula == null) {
            JOptionPane.showMessageDialog(null, "Sem usuário logado, não será possível continuar");
            return;

        }

        PreparedStatement stmt;
        Connection con;
        con = ConnectionFactory.conectar("rejud");
        String query = "Select * from tb_solicitacao_levantamento where ((OBS_CONTABILIZACAO is null or OBS_CONTABILIZACAO = '') and (RESPONSAVEL_SOLICITACAO_INCLUSAO = ? and OBS like '%sucesso%' and ID_SITUACAO<>'Concluída')) ORDER BY TIPO_LEVANTAMENTO ASC";
        stmt = con.prepareStatement(query);

        stmt.setString(1, matricula);

        if (user.getGrupoUsuario().equals("ADMINISTRADOR")) {
            query = "Select * from tb_solicitacao_levantamento where ((OBS_CONTABILIZACAO is null or OBS_CONTABILIZACAO = '') and (OBS like '%sucesso%' and ID_SITUACAO<>'Concluída'))";
            stmt = con.prepareStatement(query);

        }

        ResultSet rs = stmt.executeQuery();

        int numeroReg = d.contareg(rs);

        if (numeroReg < 1) {

            coletas.setSize();
            JOptionPane.showMessageDialog(null, " Fim da Contabilização!!");
            con.close();
            stmt.close();
            rs.close();
            stop();

        }

        do {
            //while (rs.next()){
            nomeElemento = "";
            valorElemento = "";

            driver.manage().window().maximize();

            coletas.setURL("https://juridico.intranet.bb.com.br/paj/levantamento/solicitacao/pesquisar");
            coletas.clickElementId(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:linkDados");

            nomeElemento = "pesquisarSolicitacaoLevantamentoDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoInput";
            valorElemento = rs.getString("NPJ").subSequence(0, 4).toString();
            coletas.aguardaElementoTelaByName(nomeElemento);
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);

            nomeElemento = "pesquisarSolicitacaoLevantamentoDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoNumeroProcessoInput";
            int tamanhoNpj = coletas.calculaTamanhoValorElemento(rs.getString("NPJ"));
            valorElemento = rs.getString("NPJ").subSequence(4, tamanhoNpj).toString();
            coletas.aguardaElementoTelaByName(nomeElemento);
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);
            //coletas.pausar(1000);

            nomeElemento = "pesquisarSolicitacaoLevantamentoDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoVariacaoInput";
            valorElemento = rs.getString("VARIACAO_NPJ");
            coletas.aguardaElementoTelaByName(nomeElemento);
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);

           

            coletas.selecionarElementoName("pesquisarSolicitacaoLevantamentoDepositoForm:j_id140", 1);

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
                    d.editar("tb_solicitacao_levantamento","OBS_CONTABILIZACAO", n, codigo);
                    interacao();
                }
            }

            Boolean p = coletas.isElementPresentID(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:j_id433");

            if (p) {
                String n = coletas.lerValorElementoID("pesquisarSolicitacaoLevantamentoDepositoForm:j_id433");
                if (!n.equals("")) {
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_solicitacao_levantamento","OBS_CONTABILIZACAO", n, codigo);
                    interacao();
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

            String valorResgateBanco = rs.getString("VALOR_SOLICITACAO");
            BigDecimal valorBanco = coletas.tratarNumero(valorResgateBanco);
            String valorResgateTratado = valorBanco.toString();
            String levantador = rs.getString("LEVANTADOR");
            String agenciaOperadora = "";
            
            if (levantador.equals("Adverso ou Terceiro")) {
                agenciaOperadora = "1915";
            } else {
                agenciaOperadora = "3793";
            }
            
            String finalidade = rs.getString("TIPO_DESTINACAO");
            String especificacao = rs.getString("ESPECIFICA_DESTINACAO");
            String favorecido = rs.getString("BENEFICIARIO");

            for (int reg = 0; reg <= qtdreg; reg++) {

                if (reg == qtdreg) {
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_solicitacao_levantamento", "OBS_CONTABILIZACAO", "Contabilização não efetuada, valor da solicitação ou número de ofício divergente ", codigo);
                    interacao();

                }

                nomeElemento = "pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":oficioDecorate:oficioOutput";
                String ordem = coletas.lerValorElementoID(nomeElemento);
                String ordemBanco = rs.getString("OFICIO");
                
                int tamanhoOrdem = coletas.calculaTamanhoValorElemento(ordemBanco);
                if (tamanhoOrdem >= 15) {
                    ordemBanco = rs.getString("OFICIO").subSequence(0, 15).toString();
                }
                
                nomeElemento = "pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":valorDecorate:valorOutput";
                String valorElemento = coletas.lerValorElementoID(nomeElemento);
                int tamanho = coletas.calculaTamanhoValorElemento(valorElemento);

                String valorResgate = coletas.lerValorElementoID(nomeElemento).subSequence(3, tamanho).toString();
                BigDecimal valorPortal = coletas.tratarNumero(valorResgate);

                if (valorBanco.equals(valorPortal) && ordem.equals(ordemBanco)) {

                    Boolean b = coletas.isElementPresentID(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":seta");

                    if (b) {
                        coletas.clickElementId(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":seta");

                    }

                    if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                        String msgfim = coletas.lerValorElementoID("modaldivMessagesGlobal");

                        if (!msgfim.equals("")) {
                            int codigo = rs.getInt("CODIGO");
                            con.close();
                            rs.close();
                            stmt.close();
                            d.editar("tb_solicitacao_levantamento", "OBS_CONTABILIZACAO", msgfim, codigo);
                            interacao();
                        }

                        if (coletas.isElementPresentID(driver, "incluirLevantamentoForm:dataTabletableDest:0:j_id1624")) {
                            coletas.clickElementId(driver, "incluirLevantamentoForm:btSalvarLiberacao");
                            driver.switchTo().alert().accept();
                            coletas.pausar(2000);

                        }

                        if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                            msgfim = coletas.lerValorElementoID("modaldivMessagesGlobal");

                            if (!msgfim.equals("")) {
                                int codigo = rs.getInt("CODIGO");
                                con.close();
                                rs.close();
                                stmt.close();
                                d.editar("tb_solicitacao_levantamento", "OBS_CONTABILIZACAO", msgfim, codigo);
                                interacao();
                            }
                        }

                        
                        coletas.aguardaElementoTelaByID("incluirLevantamentoForm:j_id1442");
                        
                        
                        coletas.clickElementId(driver, "incluirLevantamentoForm:pesquisarDependenciaDecorate:j_id1418");
                        coletas.aguardaElementoTelaByID("includePopup:pesquisarDependenciaForm:prefixoDecorate:prefixoInput");
                        coletas.procuraElementoPorId(driver, "includePopup:pesquisarDependenciaForm:prefixoDecorate:prefixoInput", agenciaOperadora);
                        coletas.pausar(1000);
                        coletas.clickElementId(driver, "includePopup:pesquisarDependenciaForm:pesquisarDependencia");
                        coletas.aguardaElementoTelaByID("includePopup:pesquisarDependenciaForm:dataTablePopupUnidadesOrganizacionais:0:selecionar");
                        coletas.clickElementId(driver, "includePopup:pesquisarDependenciaForm:dataTablePopupUnidadesOrganizacionais:0:selecionar");
                        coletas.pausar(1000);

//                        coletas.clickElementId(driver, "incluirLevantamentoForm:j_id1494");
                        coletas.clickElementId(driver, "incluirLevantamentoForm:j_id1442");
                        coletas.aguardaElementoTelaByID("incluirDepositoAdversoForm:levantadorDecorate:levantadorRadio:1");
                        coletas.destinarLevantamento(levantador, finalidade, especificacao, favorecido, valorResgateTratado);

                        coletas.clickElementId(driver, "incluirDepositoAdversoForm:btSalvar2");

                        b = coletas.isElementPresentID(driver, "incluirDepositoAdversoForm:finalidadeDecorate:j_id280");

                        if (b) {
                            msgfim = coletas.lerValorElementoID("incluirDepositoAdversoForm:finalidadeDecorate:j_id280");

                            if (!msgfim.equals("")) {
                                int codigo = rs.getInt("CODIGO");
                                con.close();
                                rs.close();
                                stmt.close();
                                d.editar("tb_solicitacao_levantamento", "OBS_CONTABILIZACAO", msgfim, codigo);
                                interacao();
                            }

                        }

                        //coletas.aguardaElementoTelaByID("incluirDepositoAdversoForm:j_id586");
                        if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                            msgfim = coletas.lerValorElementoID("modaldivMessagesGlobal");

                            if (msgfim.equals("Operação realizada com sucesso!")) {
                                coletas.clickElementId(driver, "botaoFecharMsg");
                                coletas.pausar(2000);
                            } else {
                                int codigo = rs.getInt("CODIGO");
                                String nomeCampo = "OBS_CONTABILIZACAO";
                                con.close();
                                rs.close();
                                stmt.close();
                                d.editar("tb_solicitacao_levantamento", nomeCampo, msgfim, codigo);
                                interacao();
                            }
                        }

                        coletas.clickElementId(driver, "incluirDepositoAdversoForm:btConcluir");

                        coletas.pausar(2000);
                        coletas.clickElementId(driver, "incluirLevantamentoForm:btSalvarLiberacao");

                        driver.switchTo().alert().accept();
                        coletas.pausar(2000);

                        coletas.clickElementId(driver, "incluirDepositoAdversoForm:btEfetivar");

                        driver.switchTo().alert().accept();
                        coletas.pausar(2000);

                        if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                            msgfim = coletas.lerValorElementoID("modaldivMessagesGlobal");

                            if (!msgfim.equals("")) {
                                int codigo = rs.getInt("CODIGO");

                                con.close();
                                rs.close();
                                stmt.close();

                                d.editar("tb_solicitacao_levantamento", "OBS_CONTABILIZACAO", msgfim, codigo);

                                BigDecimal valorDespacho = new BigDecimal(50000);

                                if (valorBanco.compareTo(valorDespacho) == 1) {

                                    msgfim = "VALOR ACIMA DE 50 MIL. INFORME AO GERENTE PARA DESPACHO!!";

                                    if (!msgfim.equals("")) {

                                        d.editar("tb_solicitacao_levantamento", "OBS_CONTABILIZACAO", msgfim, codigo);
                                        interacao();
                                    }
                                }

                                interacao();
                            }
                            
                        }

                    }

                }

            }

            con.close();
            stmt.close();
            rs.close();

        } while (rs.next());

    }

}
