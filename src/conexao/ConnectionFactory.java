
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class ConnectionFactory {
    
    
    private static  Connection con = null;
   
    private static final String DB_DRIVER ="com.mysql.jdbc.Driver";
        
    private static final String DB_USER ="basso";
  
    private static final String DB_PASSWORD ="2579368410";

    
    public static Connection conectar(String banco) {

       String DB_CONEXAO = "jdbc:mysql://10.105.87.250/" + banco;

       //String DB_CONEXAO = "jdbc:mysql://172.20.0.33/" + banco;

        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(DB_CONEXAO, DB_USER, DB_PASSWORD);
            return con;

        } catch (ClassNotFoundException ex) { //  quando a conexao cai na exceção retorna a  variavel con do tipo connection nulo assim quem chamou o método sabe que a conexão falhou

            JOptionPane.showMessageDialog(null, "O drive do banco de dados não foi encontrado");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível conectar ao banco de dados");

        }

        return con;

    }

    public static void fecharConexao() {

        if (con != null) {

            try {
                con.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão com o banco de dados: " + ex);
            }
        }

    }

}
