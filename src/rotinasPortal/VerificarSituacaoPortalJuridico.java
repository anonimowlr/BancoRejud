/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

public class VerificarSituacaoPortalJuridico extends Thread {

    Usuario user = new Usuario();
    String matricula = user.getMatricula();

    DAO<String> d = new DAO<>("rejud");

    Coletas coletas = new Coletas();

    public VerificarSituacaoPortalJuridico() {
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
        String query = "Update tb_solicitacao_levantamento as t set t.ID_SITUACAO = '' where (t.ID_SITUACAO not like '%Concluída%' and t.RESPONSAVEL_SOLICITACAO_INCLUSAO = ? ) ";

        stmt = con.prepareStatement(query);
        stmt.setString(1, matricula);
        stmt.executeUpdate();

        if (user.getGrupoUsuario().equals("ADMINISTRADOR")) {
            query = "Update tb_solicitacao_levantamento as t set t.ID_SITUACAO = '' where (t.ID_SITUACAO not like '%Concluída%') ";
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

        String query = "Select * from tb_solicitacao_levantamento where ((ID_SITUACAO is null or ID_SITUACAO = '') and RESPONSAVEL_SOLICITACAO_INCLUSAO = ?)";
        stmt = con.prepareStatement(query);
        //stmt.setString(1, "Nenhum registro encontrado.");
        stmt.setString(1, matricula);
        
        if (user.getGrupoUsuario().equals("ADMINISTRADOR")) {
            query = "Select * from tb_solicitacao_levantamento where ((ID_SITUACAO is null or ID_SITUACAO = ''))";
            stmt = con.prepareStatement(query);
            
        }
       
       
        ResultSet rs = stmt.executeQuery();

        int numeroReg = d.contareg(rs);

        if (numeroReg < 1) {
            
            coletas.setSize();
            driver.close();
            JOptionPane.showMessageDialog(null, "Nenhum registro encontrado para leitura no Portal");
            con.close();
            stmt.close();
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
            coletas.pausar(1000);
            coletas.aguardaElementoTelaByName("pesquisarSolicitacaoLevantamentoDepositoForm:btPesquisar");
            coletas.pausar(1000);
            coletas.clickElementId(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:btPesquisar");
            coletas.pausar(1000);

            if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                if (!n.equals("")) {
                    int codigo = rs.getInt("CODIGO");
                    stmt.close();
                    rs.close();
                    con.close();
                    d.editar("tb_solicitacao_levantamento","ID_SITUACAO", n, codigo);
                    interacao();
                }
            }
            if (coletas.isElementPresentID(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:j_id424")) {
                String n = coletas.lerValorElementoID("pesquisarSolicitacaoLevantamentoDepositoForm:j_id424");
                if (!n.equals("")) {
                    int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_solicitacao_levantamento","ID_SITUACAO", n, codigo);
                    interacao();
                }

            }

            

            // ler quantidade de solicitações de levantamento listada na página.
             Integer qtdreg = null;
            if(coletas.isElementPresentID(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:j_id436")){
                nomeElemento = "pesquisarSolicitacaoLevantamentoDepositoForm:j_id436";
            String n = coletas.lerValorElementoID(nomeElemento);

            qtdreg = Integer.parseInt(coletas.quantidadeRegistrosListados(n));
            } else{
                
                 int codigo = rs.getInt("CODIGO");
                    con.close();
                    rs.close();
                    stmt.close();
                    d.editar("tb_solicitacao_levantamento","ID_SITUACAO", "nenhum registro encontrado", codigo);
                    interacao();
            }
        
            
            
            

           
            
            
            
            
            
            
            
            
            
            

            if (qtdreg > 10) {

                Boolean b = coletas.isElementPresentID(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:j_id465");





                if (b) {
                    coletas.clickElementId(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:j_id465");
                    coletas.pausar(2000);
                    coletas.aguardaElementoTelaByID("pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:10:j_id669");

                }

            }

            String valorResgateBanco = rs.getString("VALOR_SOLICITACAO");
            BigDecimal valorBanco = coletas.tratarNumero(valorResgateBanco);
            String valorResgateTratado = valorBanco.toString();

            for (int reg = 0; reg <= qtdreg; reg++) {
                
                    if (reg==qtdreg){
                            int codigo = rs.getInt("CODIGO");
                            con.close();
                            rs.close();
                            stmt.close();
                            d.editar("tb_solicitacao_levantamento","ID_SITUACAO", "Não encontrado valor correspondente a este levantamento", codigo);
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
                String situacaoPortal;

                if (valorBanco.equals(valorPortal) && ordem.equals(ordemBanco)) {

                    Boolean b = coletas.isElementPresentID(driver, "pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":situacaoDecorate:situacaoOutput");

                    if (b) {
                        situacaoPortal = coletas.lerValorElementoID("pesquisarSolicitacaoLevantamentoDepositoForm:dataTabletablesolicitacaoResgate:" + reg + ":situacaoDecorate:situacaoOutput");
                        if (!situacaoPortal.equals("")) {
                            situacaoPortal = situacaoPortal;
                            int codigo = rs.getInt("CODIGO");
                            stmt.close();
                            rs.close();
                            con.close();
                            d.editar("tb_solicitacao_levantamento","ID_SITUACAO", situacaoPortal, codigo);
                            
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
