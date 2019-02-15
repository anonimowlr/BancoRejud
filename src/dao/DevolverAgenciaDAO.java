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
import entidade.Resgate;
import entidade.Usuario;

/**
 *
 * @author f5078775
 */
public class DevolverAgenciaDAO<E> {
      Usuario user = new Usuario();
    
    
   
    
    
     public List<Resgate> buscar() throws SQLException {

        List<Resgate> listaResgates = new ArrayList<>();

        
        
        try{
        Connection con = null;
        con = ConnectionFactory.conectar("rejud");
        String sql = "select * from view_tb_devolucao_ag where OBS_DEVOLUCAO IS NULL OR OBS_DEVOLUCAO =''";
        PreparedStatement stmt = con.prepareStatement(sql);
        

      

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Resgate s  = new Resgate();
            s.setCod(rs.getInt("cod"));
            s.setNpj(rs.getString("NPJ"));
            s.setIdNpj(rs.getString("ID_NPJ"));
            s.setIDValor(rs.getBigDecimal("ID_Valor"));
            s.setIDDeposito(rs.getString("ID_Deposito"));
            s.setProcesAutor(rs.getString("Proces_Autor"));
            s.setSolicOrdem(rs.getString("solic_ordem"));
            s.setContaDepositaria(rs.getString("CONTA_DEPOSITARIA"));
            s.setDjo51AgenciaResponsavel(rs.getString("djo_51_agencia_responsavel"));
            
            
            
            

            listaResgates.add(s);

        }
        
        }catch(Exception ex){
            
            JOptionPane.showMessageDialog(null, ex);
            
        } finally{
            ConnectionFactory.fecharConexao();
        }
       

        return listaResgates;
    }
     
     
     
     
     
      public void editarGenerico(String nomeTabela, String nomeCampo, String n, int num)  {
        Usuario user = new Usuario();

        
        try{
        Connection con = null;
        con = ConnectionFactory.conectar("rejud");
        String sql = "UPDATE " + nomeTabela + " SET " + nomeCampo + " = ? where cod = ?";
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
     
     
    
}
