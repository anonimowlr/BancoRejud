/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conexao.ConnectionFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import entidade.SolicitacaoInedito;
import entidade.Usuario;


public class IneditoDAO<E> {
    
    private final String bancoDados;
    
    Usuario user = new Usuario();

    
    public IneditoDAO(String bancoDados) {
        this.bancoDados = bancoDados;
    }
    
    
    public void inserirInedito(SolicitacaoInedito inedito) {

        Connection con = null;
        try {
            con = ConnectionFactory.conectar(bancoDados);

            String sql = "INSERT INTO tb_inedito (NPJ,VARIACAO_NPJ,VL_RSGT,CONTA_DEPOSITARIA,FUNCIONARIO_RESPONSAVEL_SOLICITACAO,DATA_SOLICITACAO,TIPO_DESTINACAO) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, inedito.getNpj());
            stmt.setInt(2, inedito.getVariacaoNpj());
            stmt.setDouble(3, inedito.getVlRsgt());
            stmt.setString(4, inedito.getContaDepositaria());
            stmt.setString(5, inedito.getFuncionarioResponsavelSolicitacao());
            stmt.setDate(6, (Date) inedito.getDataSolicitacao());
            stmt.setString(7, inedito.getTipoDestinacao());
            
            

            stmt.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Dados gravados com sucesso!!");
            con.close();
            stmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
    
    
    
    
     public List<SolicitacaoInedito> listaIneditosParaComplementar() throws SQLException {

        List<SolicitacaoInedito> listaIneditos = new ArrayList<>();

        
        
        try{
        Connection con = null;
        con = ConnectionFactory.conectar(bancoDados);
        String sql = "select * from tb_inedito where  (OBS_COMPLEMENTAR <> 'Operacao realizada com sucesso.'  or OBS_COMPLEMENTAR = ''  or OBS_COMPLEMENTAR is null) and (DEPENDENCIA_DTRA IS NOT NULL AND DEPENDENCIA_DTRA <> '') order by TIPO_DESTINACAO DESC";
        PreparedStatement stmt = con.prepareStatement(sql);
        

      

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            SolicitacaoInedito s  = new SolicitacaoInedito();
            s.setCodigo(rs.getInt("CODIGO"));
            s.setNpj(rs.getString("NPJ"));
            s.setVariacaoNpj(rs.getInt("VARIACAO_NPJ"));
            s.setNrOficio(rs.getString("NR_OFICIO"));
            s.setContaDepositaria(rs.getString("CONTA_DEPOSITARIA"));
            s.setSituacaoResgateSisbb(rs.getString("SITUACAO_RESGATE_SISBB"));
            s.setSituacaoResgatePortal(rs.getString("SITUACAO_RESGATE_PORTAL"));
            s.setDependenciaOprd(rs.getInt("DEPENDENCIA_OPRD"));
            s.setDependenciaDtra(rs.getInt("DEPENDENCIA_DTRA"));
            s.setFuncionarioResponsavelSolicitacao(rs.getString("FUNCIONARIO_RESPONSAVEL_SOLICITACAO"));
            s.setTipoDestinacao(rs.getString("TIPO_DESTINACAO"));
            s.setDataSolicitacao(rs.getDate("DATA_SOLICITACAO"));
            s.setFuncionarioResponsavelInclusao(rs.getString("FUNCIONARIO_RESPONSAVEL_INCLUSAO"));
            s.setDataInclusao(rs.getDate("DATA_INCLUSAO"));
            s.setObsComplementar(rs.getString("OBS_COMPLEMENTAR"));
            s.setObsTratar(rs.getString("OBS_TRATAR"));
            s.setSaldoContaDepositaria(rs.getDouble("SALDO_CONTA_DEPOSITARIA"));
            s.setDataSaldo(rs.getDate("DATA_SALDO"));
            s.setObsColetaDtra(rs.getString("OBS_COLETA_DTRA"));
            
            
            
            
            

            listaIneditos.add(s);

        }
        
        }catch(Exception ex){
            
            JOptionPane.showMessageDialog(null, ex);
            
        } finally{
            ConnectionFactory.fecharConexao();
        }
       

        return listaIneditos;
    }
     
     
     
      public List<SolicitacaoInedito> buscarParaTratar() throws SQLException {

        List<SolicitacaoInedito> listaIneditos = new ArrayList<>();

        
        
        try{
        Connection con = null;
        con = ConnectionFactory.conectar(bancoDados);
        String sql = "select * from tb_inedito where (OBS_TRATAR IS NULL OR OBS_TRATAR = '') AND (OBS_COMPLEMENTAR IS NOT NULL  AND OBS_COMPLEMENTAR <>'' ) order by TIPO_DESTINACAO DESC";
        PreparedStatement stmt = con.prepareStatement(sql);
        

      

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            SolicitacaoInedito s  = new SolicitacaoInedito();
            s.setCodigo(rs.getInt("CODIGO"));
            s.setNpj(rs.getString("NPJ"));
            s.setVariacaoNpj(rs.getInt("VARIACAO_NPJ"));
            s.setVlRsgt(rs.getDouble("VL_RSGT"));
            s.setNrOficio(rs.getString("NR_OFICIO"));
            s.setContaDepositaria(rs.getString("CONTA_DEPOSITARIA"));
            s.setSituacaoResgateSisbb(rs.getString("SITUACAO_RESGATE_SISBB"));
            s.setSituacaoResgatePortal(rs.getString("SITUACAO_RESGATE_PORTAL"));
            s.setDependenciaOprd(rs.getInt("DEPENDENCIA_OPRD"));
            s.setDependenciaDtra(rs.getInt("DEPENDENCIA_DTRA"));
            s.setFuncionarioResponsavelSolicitacao(rs.getString("FUNCIONARIO_RESPONSAVEL_SOLICITACAO"));
            s.setTipoDestinacao(rs.getString("TIPO_DESTINACAO"));
            s.setDataSolicitacao(rs.getDate("DATA_SOLICITACAO"));
            s.setFuncionarioResponsavelInclusao(rs.getString("FUNCIONARIO_RESPONSAVEL_INCLUSAO"));
            s.setDataInclusao(rs.getDate("DATA_INCLUSAO"));
            s.setObsComplementar(rs.getString("OBS_COMPLEMENTAR"));
            s.setObsTratar(rs.getString("OBS_TRATAR"));
            s.setSaldoContaDepositaria(rs.getDouble("SALDO_CONTA_DEPOSITARIA"));
            s.setDataSaldo(rs.getDate("DATA_SALDO"));
            s.setObsColetaDtra(rs.getString("OBS_COLETA_DTRA"));
            
            
            
            
            

            listaIneditos.add(s);

        }
        
        }catch(Exception ex){
            
            JOptionPane.showMessageDialog(null, ex);
            
        } finally{
            ConnectionFactory.fecharConexao();
        }
       

        return listaIneditos;
    }

     

     
     
     
     public void editarGenerico(String nomeTabela, String nomeCampo, String n, int num)  {
        Usuario user = new Usuario();

        
        try{
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
        
        
        }catch (Exception ex){
            
            JOptionPane.showMessageDialog(null, ex);
            
        } finally{
            
            ConnectionFactory.fecharConexao();
        }
        

    }
    
    
    
    public void atualizaStatusInedito(){
    Usuario user = new Usuario(); 
    
    try{
        Connection con = null;
        con = ConnectionFactory.conectar(bancoDados);
         String  sql = "update tb_desconciliacao_djo_paj  as t1 set SITUACAO = 'INEDITO TRATADO', NOME_TRATAMENTO = NULL,DATA_SITUACAO = now(),FUNCIONARIO_RESPONSAVEL_SITUACAO = ? WHERE  exists (select * from tb_inedito as t2 where t1.NPJ=t2.NPJ and t1.CONTA_DEPOSITARIA=t2.CONTA_DEPOSITARIA and (t1.SITUACAO ='INEDITO SOLICITADO' or t1.SITUACAO ='' or t1.SITUACAO is null) AND t2.OBS_TRATAR  LIKE '%' 'sucesso' '%' )"; 
   
        PreparedStatement stmt = con.prepareStatement(sql);

        //seta os valores
        // stmt.setString(1, nomeCam);
        stmt.setString(1, user.getMatricula());
       

        //executa o codigo
        stmt.executeUpdate();
        
        
        }catch (Exception ex){
            
            JOptionPane.showMessageDialog(null, ex);
            
        } finally{
            
            ConnectionFactory.fecharConexao();
        }
        


    
        
        
        
        
    }
    
    
    
}
