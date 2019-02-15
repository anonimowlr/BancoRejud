
package rotinasSisbb;

import br.com.bb.jibm3270.RoboException;
import conexao.ConnectionFactory;
import dao.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import entidade.SolicitacaoInedito;
import entidade.Usuario;
import rotinasPortal.Coletas;
import sisbbgeneric.LogarUsuarioSisbb;
import sisbbgeneric.Janela3270;

/**
 *
 * @author suporte
 */
public class ColetaDadosUsuarios {
    
    
    
    DAO<String> d = new DAO<>("rejud");
    Coletas coletas = new Coletas();
    String msgfim = "";
    
    String codigoUor ;
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void inicioRotinaSisbb(Janela3270 sisbb,String sistema) throws Throwable {
       LogarUsuarioSisbb logar = new LogarUsuarioSisbb();
        logar.autenticar(sistema, sisbb);
        posicionarTela(sisbb);
        lerbancoDados(sisbb);
    }
    
     private void posicionarTela(Janela3270 sisbb) throws RoboException {
        
         
         
   
   
        
        
        
         sisbb.aguardar(1, 3, "P0013");
         sisbb.teclarAguardarTroca("@E");
         sisbb.posicionar(21, 20);
         sisbb.teclar("03");
         sisbb.teclarAguardarTroca("@E");
         sisbb.posicionar(21, 20);
         sisbb.teclar("01");
         sisbb.teclarAguardarTroca("@E");
         
         
         
    
    }
     
     
     
     
     
     
     
     
     
     private void lerbancoDados(Janela3270 sisbb) throws SQLException, Throwable {
        Usuario user = new Usuario();
        String matricula = user.getMatricula();

        if (matricula == null) {
            JOptionPane.showMessageDialog(null, "Sem usuário logado, não será possível continuar");
            return;

        }

        SolicitacaoInedito inedito = new SolicitacaoInedito();
        PreparedStatement stmt;
        Connection con;
        con = ConnectionFactory.conectar("rejud");
        String query = "SELECT *  from tb_usuarios where MATRICULA = ? )))";
        
        stmt = con.prepareStatement(query);

        stmt.setString(1, matricula);
        
        
        
        if (user.getGrupoUsuario().equals("ADMINISTRADOR")) {
               
              query = "SELECT *  from tb_usuarios";
              stmt = con.prepareStatement(query);
        }
        
        
        
        
        
       
        ResultSet rs = stmt.executeQuery();

        int numeroReg = d.contareg(rs);

        if (numeroReg < 1) {

                
                JOptionPane.showMessageDialog(null, "Fim de Rotina");
               
        }

        

        do {
            
           String chaveFunci = rs.getString("MATRICULA");
          
           
            
            capturaDadosUsuario(sisbb, chaveFunci);
            
            
            
            
            if (!msgfim.equals("")) {
                        int codigo = rs.getInt("CODIGO");
                        con.close();
                        rs.close();
                        stmt.close();
                        
                        d.editar("tb_usuarios","CODIGO_UOR", codigoUor, codigo);
                        lerbancoDados(sisbb);
                    }
            

            
            
            
            
        } while (rs.next());
         
    }

    private void capturaDadosUsuario(Janela3270 sisbb, String matricula) throws Throwable {
        
        
        //while(!sisbb.copiar(6, 7, 18).equals("Por Conta judicial")){
           // sisbb.teclarAguardarTroca("@3");
      //}
        
        
        
        
        
        
        
        
        
        
        
        sisbb.posicionar(21,65);
        sisbb.teclar(matricula.subSequence(1, 8).toString());
        sisbb.teclarAguardarTroca("@E");
        sisbb.posicionar(21,17);
        sisbb.teclar("01");
        sisbb.teclarAguardarTroca("@E");
        
        String texto = sisbb.copiar(14, 10, 6);
        
        if(texto.equals("Deseja")){
            sisbb.teclarAguardarTroca("@E");
            
        }
        
        
        texto = sisbb.copiar(12, 5, 1);
        
        
        if(texto.equals("2")){
            sisbb.teclarAguardarTroca("@3");
            
        }
        
        
        
        sisbb.posicionar(21,11);
        sisbb.teclar("03");
        sisbb.teclarAguardarTroca("@E");
        
        texto = sisbb.copiar(11, 15, 14);
        
            if(texto.equals("Este  depósito")){
                sisbb.teclarAguardarTroca("@E");
                
            }
            
        for(int i = 20; i>=17;i--){
            
            
            while(!sisbb.copiar(23,3,14).equals("Última página.")){
                sisbb.teclarAguardarTroca("@8");
                
            }
            
             texto = sisbb.copiar(i,14,4);
            
             
            if(!texto.equals("")){
                msgfim = "Agencia Coletada";
                
                return;
            }
            
        
            
            
        }
        
        
    }
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
}
