package rotinasPortal;

import util.Utils;
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

public class AnaliseLiberacaoCustoTarifa extends Thread {

    DAO<String> d = new DAO<>("rejud");

    Coletas coletas = new Coletas();

    public AnaliseLiberacaoCustoTarifa() {

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
        String query = "Select * from tb_tarifa where (OBS_ANALISE_LIBERACAO is null or OBS_ANALISE_LIBERACAO = '') and (FUNCIONARIO_RESPONSAVEL = ? AND ID_SITUACAO = 'Aguardando Analise/Liberacao');";
        stmt = con.prepareStatement(query);

        stmt.setString(1, matricula);

        if (user.getGrupoUsuario().equals("ADMINISTRADOR")) {
            query = "Select * from tb_tarifa where (OBS_ANALISE_LIBERACAO is null or OBS_ANALISE_LIBERACAO = '' AND ID_SITUACAO = 'Aguardando Analise/Liberacao')";
            stmt = con.prepareStatement(query);

        }

        ResultSet rs = stmt.executeQuery();

        int numeroReg = d.contareg(rs);

        if (numeroReg < 1) {

            coletas.setSize();
            driver.close();
            JOptionPane.showMessageDialog(null, " Fim de Rotina!!");
            
            con.close();
            stmt.close();
            rs.close();
            stop();

        }

        do {

            String agenciaContabil = rs.getString("DEPENDENCIA_CONTABIL");
            int tamanhoNpj = coletas.calculaTamanhoValorElemento(rs.getString("NPJ"));
            int tamanhoAgenciaContabil = coletas.calculaTamanhoValorElemento(rs.getString("DEPENDENCIA_CONTABIL"));

            if (tamanhoAgenciaContabil <= 3) {
                agenciaContabil = "0" + agenciaContabil;
            }

            String bancoBeneficiario = rs.getString("BANCO_BENEFICIARIO");
            String valorTarifa = rs.getString("VALOR_TARIFA");
            String cnpj = null;

            BigDecimal valorBanco = coletas.tratarNumero(valorTarifa);
            String valorTarifaTratado = valorBanco.toString();

            int tamanhoValorTarifa = valorTarifa.length();

            if (tamanhoValorTarifa == 1) {

                valorTarifa = valorTarifa + ",00";

            }
            String numeroDocumento = rs.getString("NUMERO_DOCUMENTO");
            String dataDocumento = rs.getString("DATA_DOCUMENTO");
            String dataTratada = Utils.formatDataTexto(dataDocumento);

            String enderecoDocumento = rs.getString("ENDERECO_DOCUMENTO");

            nomeElemento = "";
            valorElemento = "";

            driver.manage().window().maximize();

            coletas.setURL("https://juridico.intranet.bb.com.br/paj/processo/custos/analise/pesquisarCustoAnalise");

            
            if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                if (!n.equals("")) {
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_tarifa", "OBS_ANALISE_LIBERACAO", n, codigo);
                    interacao();
                }
            }

//            nomeElemento = "custoForm:incluirCustoDecorate:bbjur";            
            nomeElemento = "custoForm:NPJDecorate:incluirCustoDecorate:incluirCustoInput"; //4 primeiro digitos npj
            valorElemento = rs.getString("NPJ").subSequence(0, 4).toString();
            coletas.aguardaElementoTelaByName(nomeElemento);
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);

//            nomeElemento = "custoForm:incluirCustoDecorate:numProcesso";
            nomeElemento = "custoForm:NPJDecorate:incluirCustoDecorate:numProcesso";    //meio npj
            coletas.clickElementId(driver, "custoForm:NPJDecorate:incluirCustoDecorate:numProcesso");
            coletas.pausar(1000);
            valorElemento = rs.getString("NPJ").subSequence(4, tamanhoNpj).toString();
            coletas.aguardaElementoTelaByID(nomeElemento);
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);

//            nomeElemento = "custoForm:incluirCustoDecorate:numVariacao";        
            nomeElemento = "custoForm:NPJDecorate:incluirCustoDecorate:numVariacao"; //var npj
            valorElemento = rs.getString("VARIACAO_NPJ");
            coletas.aguardaElementoTelaByName(nomeElemento);
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);

            coletas.clickElementId(driver, "custoForm:btPesquisar"); //botâo consultar
            coletas.pausar(1000);

            if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                if (!n.equals("")) {
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_tarifa", "OBS_ANALISE_LIBERACAO", n, codigo);
                    interacao();
                }
            }

            Boolean p = coletas.isElementPresentID(driver, "custoForm:dataTablelista:0:abrirJanelaDetalharCustoProcesso");

            if (!p) {

                int codigo = rs.getInt("CODIGO");
                con.close();
                rs.close();
                stmt.close();
                d.editar("tb_tarifa", "OBS_ANALISE_LIBERACAO", "Nenhum registro encontrado", codigo);
                interacao();
            }

            // ler quantidade de solicitações de levantamento listada na página.
            nomeElemento = "custoForm:divContador";
            String n = coletas.lerValorElementoID(nomeElemento);

            Integer qtdreg = Integer.parseInt(coletas.quantidadeRegistrosListadosTelaTelaTarifa(n));
//                        if (qtdreg>10){
//                            
//                             Boolean b = coletas.isElementPresentID(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:j_id455");
//
//                                if (b) {
//                                    coletas.clickElementId(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:j_id455");
//                                    coletas.aguardaElementoTelaByID("pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:10:seta");
//
//                                }
  
//                        }

            for (int reg = 0; reg <= qtdreg; reg++) {

                if (reg == qtdreg) {
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_tarifa", "OBS_ANALISE_LIBERACAO", "Analise não efetuada, nao encontrado registro deste valor na situação aguardando anlise/liberação ", codigo);
                    interacao();

                }

                String especificacao = coletas.lerValorElementoID("custoForm:dataTablelista:"+ reg + ":j_id266"); //especificacao 264
                String solicitante = coletas.lerValorElementoID("custoForm:dataTablelista:" + reg + ":colDependenciaSolicitacaoDecorate:j_id281"); //solicitante 279
                nomeElemento = "custoForm:dataTablelista:" + reg + ":j_id334"; //valor 332
                String valorElemento = coletas.lerValorElementoID(nomeElemento);
                int tamanho = coletas.calculaTamanhoValorElemento(valorElemento);

                String valorTarifaPortal = coletas.lerValorElementoID(nomeElemento).toString();
                BigDecimal valorPortal = coletas.tratarNumero(valorTarifaPortal);

                if (valorBanco.equals(valorPortal) && especificacao.equals("Tarifa DOC/TED - Levantamento de Depositos outros") && solicitante.equals("1915")) {

                    Boolean b = coletas.isElementPresentID(driver, "custoForm:dataTablelista:" + reg + ":analisar");

                    if (b) {
                        coletas.clickElementId(driver, "custoForm:dataTablelista:" + reg + ":analisar");

                    }

                    if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                        String msgfim = coletas.lerValorElementoID("modaldivMessagesGlobal");

                        if (!msgfim.equals("")) {
                            int codigo = rs.getInt("CODIGO");
                            con.close();
                            rs.close();
                            stmt.close();
                            d.editar("tb_tarifa", "OBS_ANALISE_LIBERACAO", msgfim, codigo);
                            interacao();
                        }

                        if (coletas.isElementPresentID(driver, "custoForm:acoesDecorate:acoesRadio:0")) {
                            coletas.clickElementId(driver, "custoForm:acoesDecorate:acoesRadio:0");
                           
                            coletas.pausar(2000);

                        }

                        if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                            msgfim = coletas.lerValorElementoID("modaldivMessagesGlobal");

                            if (!msgfim.equals("")) {
                                int codigo = rs.getInt("CODIGO");
                                con.close();
                                rs.close();
                                stmt.close();
                                d.editar("tb_tarifa", "OBS_ANALISE_LIBERACAO", msgfim, codigo);
                                interacao();
                            }
                        }

                        coletas.aguardaElementoTelaByID("custoForm:dependenciaContabilSelecionadaidDecorate:dependenciaContabilSelecionadaidListMenu");

                        int qtdElementoSeletor = coletas.contaElementosSeletor(driver, "custoForm:dependenciaContabilSelecionadaidDecorate:dependenciaContabilSelecionadaidListMenu");

                        coletas.selecionarNomeEmSeletor("custoForm:dependenciaContabilSelecionadaidDecorate:dependenciaContabilSelecionadaidListMenu", agenciaContabil, qtdElementoSeletor);

                        qtdElementoSeletor = coletas.contaElementosSeletor(driver, "custoForm:selectPessoaConglomeradoDecorate:selectPessoaConglomeradoListMenu");

                        coletas.selecionarNomeEmSeletor("custoForm:selectPessoaConglomeradoDecorate:selectPessoaConglomeradoListMenu", "BANCO DO BRASIL", qtdElementoSeletor);

                        coletas.procuraElementoPorId(driver, "custoForm:textoJustificativaDecorate:textoJustificativaInput", "Trata-se de Tarifa DOC/TED ref. a levt de depósito judicial em favor do BB  efetuado em outros Bancos cfe IN 869-2  item 2.4.5");

                        coletas.pausar(1000);
                        
                        
                        Boolean jaPossuiDocumento = coletas.isElementPresentID(driver, "custoForm:dataGridDocumentos:0:j_id482");

                        coletas.clickElementId(driver, "custoForm:btSalvar");
                        coletas.pausar(1000);
                        driver.switchTo().alert().accept();
                        
                        
                          if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                            msgfim = coletas.lerValorElementoID("modaldivMessagesGlobal");

                            if (!msgfim.equals("")) {
                                int codigo = rs.getInt("CODIGO");
                                con.close();
                                rs.close();
                                stmt.close();
                                d.editar("tb_tarifa", "OBS_ANALISE_LIBERACAO", msgfim, codigo);
                                interacao();
                            }
                        }

                        b = coletas.isElementPresentID(driver, "custoForm:dependenciaContabilSelecionadaidDecorate:j_id505");

                        if (b){
                            msgfim = coletas.lerValorElementoID("custoForm:dependenciaContabilSelecionadaidDecorate:j_id505");
    
                            if (!msgfim.equals("")) {
                                int codigo = rs.getInt("CODIGO");
                                con.close();
                                rs.close();
                                stmt.close();
                                d.editar("tb_tarifa", "OBS_ANALISE_LIBERACAO", msgfim, codigo);
                                interacao();
                            }

                        }

                        if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                            msgfim = coletas.lerValorElementoID("modaldivMessagesGlobal");

                            if (!msgfim.equals("")) {
                                int codigo = rs.getInt("CODIGO");
                                con.close();
                                rs.close();
                                stmt.close();
                                d.editar("tb_tarifa", "OBS_ANALISE_LIBERACAO", msgfim, codigo);
                                interacao();
                            }
                        }
                        
                        
                        //coletas.aguardaElementoTelaByID("incluirDocumentoCustoProcessoForm:botaoNao");
                        
                        if(jaPossuiDocumento){
                        
                        coletas.clickElementId(driver, "incluirDocumentoCustoProcessoForm:botaoNao");
                        } else{
                           coletas.clickElementId(driver, "incluirDocumentoCustoProcessoForm:botaoSim");    //incluir documento custo
                        }
                        
                        
                        coletas.aguardaElementoTelaByID("incluirDocumentoCustoProcessoForm:uploadArquivo");
                        coletas.procuraElementoPorId(driver, "incluirDocumentoCustoProcessoForm:uploadArquivo", enderecoDocumento);
                        coletas.pausar(1000);
                        coletas.aguardaElementoTelaByID("incluirDocumentoCustoProcessoForm:btSalvar");
                        coletas.clickElementId(driver, "incluirDocumentoCustoProcessoForm:btSalvar");
                        coletas.clickElementId(driver, "incluirDocumentoCustoProcessoForm:btConcluir");
                        
                        
                        if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                            msgfim = coletas.lerValorElementoID("modaldivMessagesGlobal");

                            if (msgfim.equals("")) {
                                int codigo = rs.getInt("CODIGO");
                                con.close();
                                rs.close();
                                stmt.close();
                                d.editar("tb_tarifa", "OBS_ANALISE_LIBERACAO", "Analise liberação realizada com sucesso", codigo);
                                interacao();
                            } else{
                               
                                int codigo = rs.getInt("CODIGO");
                                con.close();
                                rs.close();
                                stmt.close();
                                d.editar("tb_tarifa", "OBS_ANALISE_LIBERACAO", msgfim, codigo);
                                interacao();
                            }
                                
                        }

                        interacao();
                    }

                }

            }

            con.close();
            stmt.close();
            rs.close();

        } while (rs.next());

    }

}
