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

public class DespacharCustoTarifa extends Thread {
    DAO<String> d = new DAO<>("rejud");
    Coletas coletas = new Coletas();
    public DespacharCustoTarifa() {
    }

    @Override
    public void run() {
       coletas.autenticarUsuario();
        try {
            interacao();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    @SuppressWarnings("CallToThreadStopSuspendOrResumeManager")
    private void interacao() throws SQLException, Exception {
        Usuario user = new Usuario();
        String matricula = user.getMatricula();

        if (matricula == null) {
            JOptionPane.showMessageDialog(null, "Sem usuário logado, não será possível continuar");
            return;
        }

        PreparedStatement stmt;
        Connection con;
        con = ConnectionFactory.conectar("rejud");
        String query = "Select * from tb_tarifa where (OBS_DESPACHO_TARIFA is null or OBS_DESPACHO_TARIFA = '') and (FUNCIONARIO_RESPONSAVEL = ? and ID_SITUACAO = 'Aguardando Despacho da Efetivacao');";
        stmt = con.prepareStatement(query);

        stmt.setString(1, matricula);

        if (user.getGrupoUsuario().equals("ADMINISTRADOR")) {
            query = "Select * from tb_tarifa where (OBS_DESPACHO_TARIFA is null or OBS_DESPACHO_TARIFA = '' and ID_SITUACAO = 'Aguardando Despacho da Efetivacao')";
            stmt = con.prepareStatement(query);
        }

        ResultSet rs = stmt.executeQuery();
        int numeroReg = d.contareg(rs);

        if (numeroReg < 1) {
            coletas.setSize();
            JOptionPane.showMessageDialog(null, " Fim de Rotina!!");
            con.close();
            stmt.close();
            rs.close();
            stop();
        }

        do {
            String dataAtualTexto = Utils.getDataAtual();
            String  dataAtual= Utils.formataData(dataAtualTexto).toString();
            int codigo = rs.getInt("CODIGO");
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

            coletas.setURL("https://juridico.intranet.bb.com.br/paj/processo/custos/efetivacao/despacho/pesquisar");

            
            if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {
                String n = coletas.lerValorElementoID("modaldivMessagesGlobal");
                if (!n.equals("")) {
                     codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_tarifa", "OBS_DESPACHO_TARIFA", n, codigo);
                    interacao();
                }
            }
            
            nomeElemento = "custoForm:NPJDecorate:pesquisarDecorate:pesquisarInput";    //ano npj
            valorElemento = rs.getString("NPJ").subSequence(0, 4).toString();
            coletas.aguardaElementoTelaByName(nomeElemento);
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);

            nomeElemento = "custoForm:NPJDecorate:pesquisarDecorate:numProcesso";       //meio npj
            coletas.clickElementId(driver, "custoForm:NPJDecorate:pesquisarDecorate:numProcesso");
            coletas.pausar(1000);
            valorElemento = rs.getString("NPJ").subSequence(4, tamanhoNpj).toString();
            coletas.aguardaElementoTelaByID(nomeElemento);
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);

            nomeElemento = "custoForm:NPJDecorate:pesquisarDecorate:numVariacao";       //var npj
            valorElemento = rs.getString("VARIACAO_NPJ");
            coletas.aguardaElementoTelaByName(nomeElemento);
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);

            coletas.clickElementId(driver, "custoForm:btPesquisar");
            coletas.pausar(1000);

            if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                if (!n.equals("")) {
                    codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_tarifa", "OBS_DESPACHO_TARIFA", n, codigo);
                    interacao();
                }
            }
            //coletas.pausar(2000);
            Boolean p = coletas.isElementPresentID(driver, "custoForm:resultadoDataTable:0:abrirJanelaDetalharCustoProcesso");

            if (!p) {
                codigo = rs.getInt("CODIGO");
                con.close();
                rs.close();
                stmt.close();
                d.editar("tb_tarifa", "OBS_DESPACHO_TARIFA", "Nenhum registro encontrado", codigo);
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
//                                }
//                        }

            for (int reg = 0; reg <= qtdreg; reg++) {
                if (reg == qtdreg) {
                     codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_tarifa", "OBS_DESPACHO_TARIFA", "Despacho não efetuado, nao encontrado registro deste valor na situação aguardando despacho ", codigo);
                    interacao();
                }
                
               coletas.pausar(1000);
               
              
                String especificacao = coletas.lerValorElementoID("custoForm:resultadoDataTable:" + reg + ":j_id354");
//                String especificacao = coletas.lerValorElementoByXpth("html/body/div[1]/div[4]/div[2]/div/div[1]/div[1]/form/div[2]/div/center/table/tbody/tr[2]/td/table/tbody/tr/td[4]");
                
                String operadora = coletas.lerValorElementoID("custoForm:resultadoDataTable:" + reg + ":colDependenciaOberadoraDecorate:j_id369");
//                String operadora = coletas.lerValorElementoByXpth("html/body/div[1]/div[4]/div[2]/div/div[1]/div[1]/form/div[2]/div/center/table/tbody/tr[2]/td/table/tbody/tr/td[6]");
                
                nomeElemento = "custoForm:resultadoDataTable:" + reg + ":j_id422";
                
                String valorElemento = coletas.lerValorElementoID(nomeElemento);
                int tamanho = coletas.calculaTamanhoValorElemento(valorElemento);

                String valorTarifaPortal = coletas.lerValorElementoID(nomeElemento);
                BigDecimal valorPortal = coletas.tratarNumero(valorTarifaPortal);

                if (valorBanco.equals(valorPortal) && especificacao.equals("Tarifa DOC/TED - Levantamento de Depositos outros") && operadora.equals("1915")) {

                    Boolean b = coletas.isElementPresentID(driver, "custoForm:resultadoDataTable:" + reg + ":aprovar");

                    if (b) {
                        
                        coletas.clickElementId(driver, "custoForm:resultadoDataTable:" + reg + ":aprovar");
                       
                    }

                    
                if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                if (!n.equals("")) {
                     codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_tarifa", "OBS_DESPACHO_TARIFA", n, codigo);
                    interacao();
                }
            }
                    
                coletas.aguardaElementoTelaByID("formulario:btConfirmar");
                // formulario:dataTableDocumentos:0:j_id689--- documento anexado
                //pesquisarDocumentoForm:j_id407-- botao para incluir documento
                Boolean jaPossuiDocumento = coletas.isElementPresentID(driver, "formulario:dataTableDocumentos:0:j_id689");
               
                if (!jaPossuiDocumento){
                    coletas.clickElementId(driver, "formulario:btIncluirDocumento");
                    coletas.procuraElementoPorId(driver, "pesquisarDocumentoForm:j_id407", enderecoDocumento);
                    coletas.clickElementId(driver, "pesquisarDocumentoForm:btSalvar");
                } else{
                    coletas.clickElementId(driver, "formulario:btConfirmar");
                    driver.switchTo().alert().accept();
                }
                    
                coletas.pausar(2000);
                      
                        if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                           String  msgfim = coletas.lerValorElementoID("modaldivMessagesGlobal");

                            if (msgfim.equals("Operação realizada com sucesso")) {
                                codigo = rs.getInt("CODIGO");
                                con.close();
                                rs.close();
                                stmt.close();
                                d.editar("tb_tarifa", "OBS_DESPACHO_TARIFA", msgfim, codigo);
                                d.editar("tb_tarifa", "DATA_DESPACHO_PORTAL",dataAtual, codigo);
                                interacao();
                            } else{
                               
                                codigo = rs.getInt("CODIGO");
                                con.close();
                                rs.close();
                                stmt.close();
                                d.editar("tb_tarifa", "OBS_DESPACHO_TARIFA", msgfim, codigo);
                                interacao();
                            }
                                
                        }
                        interacao();
                    }
                }

            con.close();
            stmt.close();
            rs.close();

        } while (rs.next());{
        }
    }

}
