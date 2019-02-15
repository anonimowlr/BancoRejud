/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conexao.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import entidade.Efetivacao;
import entidade.Usuario;

/**
 *
 * @author f5078775
 * @param <E>
 */
public class EfetivacaoDAO<E> {

    private final String bancoDados;
    
    Usuario user = new Usuario();

    /**
     * Inicia o construtor informando a qual Banco de Dados a ser conectado
     * 
     * @param bancoDados nome do BD
     */
    public EfetivacaoDAO(String bancoDados) {
        this.bancoDados = bancoDados;
    }

       
    public List<Efetivacao> buscar() throws SQLException {
        List<Efetivacao> listaEfetivacao = new ArrayList<>();

        Efetivacao efetivacao = null;
        try {
            Connection con = ConnectionFactory.conectar(bancoDados);
            String sql = "SELECT * FROM tb_efetivacao_dep "
                    + "where (SITUACAO_DEPOSITO <> 'DEPOSITO CONCLUIDO - COMPLEMENTO REALIZADO' OR  SITUACAO_DEPOSITO IS NULL OR SITUACAO_DEPOSITO = '') "
                    //+ "AND BANCO_DEPOSITARIO NOT LIKE 'BANRISUL' "
//                    + "AND BANCO_DEPOSITARIO LIKE 'BANRISUL' "
                    + "order by CODIGO DESC";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
           

            while (rs.next()) {

                efetivacao = new Efetivacao();

                efetivacao.setNpj(rs.getString("NPJ"));
                efetivacao.setVariacaoNpj(rs.getInt("VARIACAO_NPJ"));
                efetivacao.setCodigo(rs.getInt("CODIGO"));
                efetivacao.setContaDepositaria(rs.getString("CONTA_DEPOSITARIA"));
                efetivacao.setAutor(rs.getString("AUTOR"));
                efetivacao.setProcesso(rs.getString("PROCESSO"));
                efetivacao.setValorEfetivacao(rs.getBigDecimal("VALOR_EFETIVACAO"));
                efetivacao.setBancoDepositario(rs.getString("BANCO_DEPOSITARIO"));
                efetivacao.setContaDepositaria(rs.getString("CONTA_DEPOSITARIA"));
                efetivacao.setAgenciaDepositaria(rs.getInt("AGENCIA_DEPOSITARIA"));
                efetivacao.setSituacaoDeposito(rs.getString("SITUACAO_DEPOSITO"));
                efetivacao.setFunciResponsavelSolicitacao(rs.getString("FUNCI_RESPONSAVEL_SOLICITACAO"));
                efetivacao.setDataSolicitacao(rs.getDate("DATA_SOLICITACAO"));
                efetivacao.setFunciResponsavelComplementacao(rs.getString("FUNCI_RESPONSAVEL_COMPLEMENTACAO"));
                efetivacao.setDataComplementacao(rs.getDate("DATA_COMPLEMENTACAO"));
                efetivacao.setProtocoloGsv(rs.getInt("PROTOCOLO_GSV"));
                efetivacao.setSituacaoProlocolo(rs.getString("SITUACAO_PROLOCOLO"));
                
                
                listaEfetivacao.add(efetivacao);
                
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao buscar lista de efetivação");
        } finally {
            ConnectionFactory.fecharConexao();
        }
        return listaEfetivacao;
    }
    
    
    public void editarGenerico(String nomeTabela, String nomeCampo, String n, int num)  {

        try {
            
            Connection con = null;
            con = ConnectionFactory.conectar(bancoDados);
            String sql = "UPDATE " + nomeTabela + " SET " + nomeCampo + " = ? where CODIGO = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            //seta os valores
            // stmt.setString(1, nomeCam);
            stmt.setString(1, n);
            stmt.setInt(2, num);

            //executa o codigo
            stmt.executeUpdate();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, ex);

        } finally {

            ConnectionFactory.fecharConexao();
        }


    }
    

}
    

