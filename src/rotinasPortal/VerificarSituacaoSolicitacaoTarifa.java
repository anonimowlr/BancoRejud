/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

public class VerificarSituacaoSolicitacaoTarifa extends Thread {

    Usuario user = new Usuario();
    String matricula = user.getMatricula();

    DAO<String> d = new DAO<>("rejud");

    Coletas coletas = new Coletas();

    public VerificarSituacaoSolicitacaoTarifa() {
    }

    @Override
    public void run() {

        coletas.autenticarUsuario();
        try {
            selecionaRegLeitura();
            interacao();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);

        }
    }

    private void selecionaRegLeitura() throws SQLException {

        PreparedStatement stmt;
        Connection con;
        con = ConnectionFactory.conectar("rejud");
        String query = "Update tb_tarifa as t set t.ID_SITUACAO = '' where (t.ID_SITUACAO not like '%Efetivado/Liquidado%' and t.FUNCIONARIO_RESPONSAVEL = ? ) ";

        stmt = con.prepareStatement(query);
        stmt.setString(1, matricula);
        stmt.executeUpdate();

        if (user.getGrupoUsuario().equals("ADMINISTRADOR")) {
            query = "Update tb_tarifa as t set t.ID_SITUACAO = '' where (t.ID_SITUACAO not like '%Efetivado/Liquidado%') ";
            stmt = con.prepareStatement(query);
            stmt.executeUpdate();
        }

        con.close();
        stmt.close();
        

    }

    private void interacao() throws SQLException {

        if (matricula == null) {
            JOptionPane.showMessageDialog(null, "Sem usuário logado, não será possível continuar");
            return;

        }

        PreparedStatement stmt;
        Connection con;
        con = ConnectionFactory.conectar("rejud");

        String query = "Select * from tb_tarifa where ((ID_SITUACAO is null or ID_SITUACAO = '') and FUNCIONARIO_RESPONSAVEL = ?)";
        stmt = con.prepareStatement(query);
        //stmt.setString(1, "Nenhum registro encontrado.");
        stmt.setString(1, matricula);
        
        if (user.getGrupoUsuario().equals("ADMINISTRADOR")) {
            query = "Select * from tb_tarifa where ((ID_SITUACAO is null or ID_SITUACAO = ''))";
            stmt = con.prepareStatement(query);
            
        }
       
       
        ResultSet rs = stmt.executeQuery();

        int numeroReg = d.contareg(rs);

        if (numeroReg < 1) {
            coletas.setSize();
            JOptionPane.showMessageDialog(null, "Nenhum registro encontrado para leitura no Portal");
            con.close();
            stmt.close();
            stop();
        }

        do {
            
            
            int tamanhoNpj = coletas.calculaTamanhoValorElemento(rs.getString("NPJ"));
            String bancoBeneficiario = rs.getString("BANCO_BENEFICIARIO");
            String valorTarifa = rs.getString("VALOR_TARIFA");
            String cnpj= null;
            
            
            BigDecimal valorBanco = coletas.tratarNumero(valorTarifa);
            String valorTarifaTratado = valorBanco.toString();
            
            
            
            int tamanhoValorTarifa = valorTarifa.length();
            
            if (tamanhoValorTarifa == 1){
                
                valorTarifa = valorTarifa + ",00";
                
            }
            String numeroDocumento = rs.getString("NUMERO_DOCUMENTO");
            String dataDocumento = rs.getString("DATA_DOCUMENTO");
            String dataTratada = Utils.formatDataTexto(dataDocumento);
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
           
            nomeElemento = "";
            valorElemento = "";

            driver.manage().window().maximize();

            coletas.setURL("https://juridico.intranet.bb.com.br/paj/processo/custos/consultar/pesquisar");
            

            nomeElemento = "custosForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoInput";
            valorElemento = rs.getString("NPJ").subSequence(0, 4).toString();
            coletas.aguardaElementoTelaByName(nomeElemento);
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);

            nomeElemento = "custosForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoNumeroProcessoInput";
            coletas.clickElementId(driver, "custosForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoNumeroProcessoInput");
            valorElemento = rs.getString("NPJ").subSequence(4, tamanhoNpj).toString();
            coletas.aguardaElementoTelaByID(nomeElemento);
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);
            

            nomeElemento = "custosForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoVariacaoInput";
            valorElemento = rs.getString("VARIACAO_NPJ");
            coletas.aguardaElementoTelaByName(nomeElemento);
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);
           
            coletas.aguardaElementoTelaByName("custosForm:btPesquisar");
            
            coletas.clickElementId(driver, "custosForm:btPesquisar");
           

            if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                if (!n.equals("")) {
                    int codigo = rs.getInt("CODIGO");
                    stmt.close();
                    rs.close();
                    con.close();
                    d.editar("tb_tarifa","ID_SITUACAO", n, codigo);
                    interacao();
                }
            }
            
            coletas.pausar(1000);
            
            Boolean b = coletas.isElementPresentID(driver, "custosForm:j_id308");
            
            if (!b){
                int codigo = rs.getInt("CODIGO");
                    stmt.close();
                    rs.close();
                    con.close();
                    d.editar("tb_tarifa","ID_SITUACAO", "Nenhum registro encontrado", codigo);
                    interacao();
            }
            
            
            
            

            // ler quantidade de solicitações de levantamento listada na página.
            nomeElemento = "custosForm:j_id308";
            String n = coletas.lerValorElementoID(nomeElemento);

            Integer qtdreg = Integer.parseInt(coletas.quantidadeRegistrosListados(n));

            if (qtdreg > 10) {

              b = coletas.isElementPresentID(driver, "custosForm:j_id337");

                if (b) {
                    coletas.clickElementId(driver, "custosForm:j_id337");
                    coletas.pausar(1000);
                    coletas.aguardaElementoTelaByID("custosForm:dataTableresultadoDataTable:10:j_id407");

                }

            }

            String valorResgateBanco = rs.getString("VALOR_TARIFA");
            
            String valorResgateTratado = valorBanco.toString();

            for (int reg = 0; reg <= qtdreg; reg++) {
                
                    if (reg==qtdreg){
                            int codigo = rs.getInt("CODIGO");
                            con.close();
                            rs.close();
                            stmt.close();
                            d.editar("tb_tarifa","ID_SITUACAO", "Não encontrado valor correspondente a esta solicitacao de custo/tarifa", codigo);
                            interacao();
                        
                    }
                
                

                nomeElemento = "custosForm:dataTableresultadoDataTable:" + reg + ":j_id366";
                String especificacao = coletas.lerValorElementoID(nomeElemento);
                
                nomeElemento = "custosForm:dataTableresultadoDataTable:" + reg + ":j_id403";
                String valorElemento = coletas.lerValorElementoID(nomeElemento);
                int tamanho = coletas.calculaTamanhoValorElemento(valorElemento);

                String valorResgate = coletas.lerValorElementoID(nomeElemento).toString();
                BigDecimal valorPortal = coletas.tratarNumero(valorResgate);
                String situacaoPortal;

                if (valorBanco.equals(valorPortal) && especificacao.equals("Tarifa DOC/TED - Levantamento de Depositos outros Bancos")) {

                     b = coletas.isElementPresentID(driver, "custosForm:dataTableresultadoDataTable:" + reg + ":j_id401");

                    if (b) {
                        situacaoPortal = coletas.lerValorElementoID("custosForm:dataTableresultadoDataTable:" + reg + ":j_id401");
                        if (!situacaoPortal.equals("")) {
                            situacaoPortal = situacaoPortal;
                            int codigo = rs.getInt("CODIGO");
                            stmt.close();
                            rs.close();
                            con.close();
                            d.editar("tb_tarifa","ID_SITUACAO", situacaoPortal, codigo);
                            
                            interacao();

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

        {
        }

    }

}
