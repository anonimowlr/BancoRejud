
package rotinasSisbb;

import br.com.bb.jibm3270.RoboException;
import conexao.ConnectionFactory;
import dao.DAO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JOptionPane;
import entidade.SolicitacaoLevantamento;
import entidade.Usuario;
import rotinasPortal.Coletas;
import sisbbgeneric.LogarUsuarioSisbb;
import sisbbgeneric.Janela3270;


public class PartidaEgtCustoTarifa {
    DAO<String> d = new DAO<>("rejud");
    Coletas coletas = new Coletas();
    String msgfim = "";
    String numeroEgt ="";
    
    public void inicioRotinaSisbb(Janela3270 sisbb,String sistema) throws Throwable {
       LogarUsuarioSisbb logar = new LogarUsuarioSisbb();
        logar.autenticar(sistema, sisbb);
        posicionarTela(sisbb);
        lerbancoDados(sisbb);
    }
    
    
     private void lerbancoDados(Janela3270 sisbb) throws SQLException, Throwable {
       Usuario user = new Usuario();
        String matricula = user.getMatricula();
        
        if (matricula == null) {
            JOptionPane.showMessageDialog(null, "Sem usuário logado, não será possível continuar");
            return;

        }

        SolicitacaoLevantamento s = new SolicitacaoLevantamento();
        PreparedStatement stmt;
        Connection con;
        con = ConnectionFactory.conectar("rejud");
        String query = "SELECT *  from tb_tarifa where ((OBS_PARTIDA_EGT is null or OBS_PARTIDA_EGT='') AND ((FUNCIONARIO_RESPONSAVEL = ? ) AND (ID_SITUACAO = 'Efetivacao aguardando processamento EVT' OR ID_SITUACAO = 'Efetivado/Liquidado') AND (DATA_DESPACHO_PORTAL <> '' OR DATA_DESPACHO_PORTAL IS NOT NULL )))";
        
        stmt = con.prepareStatement(query);

        stmt.setString(1, matricula);
        
        
        
        
        if (user.getGrupoUsuario().equals("ADMINISTRADOR")) {
               
              query = "Select * from tb_tarifa where ((OBS_PARTIDA_EGT is null or OBS_PARTIDA_EGT='') AND (ID_SITUACAO = 'Efetivacao aguardando processamento EVT' OR ID_SITUACAO = 'Efetivado/Liquidado') AND (DATA_DESPACHO_PORTAL <> '' OR DATA_DESPACHO_PORTAL IS NOT NULL ))";
              stmt = con.prepareStatement(query);
        }
        
        
        
        
        
       
        ResultSet rs = stmt.executeQuery();

        int numeroReg = d.contareg(rs);

        if (numeroReg < 1) {

                sisbb.rotinaEncerramento(); 
                con.close();
                rs.close();
                stmt.close();
                JOptionPane.showMessageDialog(null, "Fim da rotina!!");
                return;
               
        }

        

        do {
            
           String npj = rs.getString("NPJ");
           String dataCredito  = rs.getString("DATA_CREDITO");
           Double valorCredito = rs.getDouble("VALOR_CREDITO");
           
           NumberFormat formatter = new DecimalFormat("R$ 0.00");
           String valorCreditoFormatado = formatter.format(valorCredito);
           
           
           
           
           

            String bancoBeneficiario = rs.getString("BANCO_BENEFICIARIO");
            String contaJudicial = rs.getString("CONTA_DEPOSITARIA");
            String valorTarifa = rs.getString("VALOR_TARIFA");
            String cnpj = null;

            BigDecimal valorBanco = coletas.tratarNumero(valorTarifa);
            String valorTarifaTratado = valorBanco.toString();
            
            int tamanhoValorTarifa = valorTarifa.length();

            if (tamanhoValorTarifa == 1) {

                valorTarifa = valorTarifa + ",00";

            }
            String numeroDocumento = rs.getString("NUMERO_DOCUMENTO");
            //String dataCreditoTexto = rs.getString("DATA_CREDITO").toString();
            //String dataCreditoTratada = Utils.formatDataTexto(dataCreditoTexto);

            String enderecoDocumento = rs.getString("ENDERECO_DOCUMENTO");
            
            
            efetuarPartidaEgt(sisbb, npj,valorTarifaTratado,bancoBeneficiario,valorCreditoFormatado,dataCredito);
            
            
            
            
            if (!msgfim.equals("")) {
                        int codigo = rs.getInt("CODIGO");
                        con.close();
                        rs.close();
                        stmt.close();
                        d.editar("tb_tarifa","OBS_PARTIDA_EGT", msgfim, codigo);
                        d.editar("tb_tarifa","NUMERO_EGT", numeroEgt, codigo);
                        lerbancoDados(sisbb);
                        return;
                    }
            

            
            
            
            
        } while (rs.next());
         
    }

   private void posicionarTela(Janela3270 sisbb) throws RoboException {
        
          sisbb.aguardar(6, 9, "Confeccionar");
         
          sisbb.posicionar(19, 20);
         
   
   }
         
    
     
    
    
    
    
     public void efetuarPartidaEgt(Janela3270 sisbb, String npj, String valor,String bancoBeneficiario , String valorCredito, String dataCredito) throws Throwable 
    {
         
       
        
      while(!sisbb.copiar(19, 3, 15).equals("Informe a opção")){
            sisbb.teclarAguardarTroca("@3");
      }
        
        
        
         sisbb.teclar("01");
         sisbb.teclarAguardarTroca("@E");
        
        
        
         
         sisbb.aguardar(1, 3, "EGTP1110");
         sisbb.posicionar(7, 16);
         sisbb.teclar("1100237");
         
         
         
         
         
         
         sisbb.teclarAguardarTroca("@E");
         
         sisbb.aguardar(15, 9, "Evento");

         sisbb.posicionar(16, 3);
         sisbb.teclar("x");
         sisbb.teclarAguardarTroca("@E");
         sisbb.aguardar(5, 3, "Dependência");
         sisbb.posicionar(6, 16);
         sisbb.teclar("99738691");
         
         sisbb.posicionar(7, 16);
         sisbb.teclar(valor);
         
         sisbb.posicionar(12, 6);
         sisbb.teclar("3793");
         
         sisbb.posicionar(12, 11);
         
         sisbb.teclar("99738691");
         
         
         sisbb.posicionar(12, 23);
         
         sisbb.teclar(valor);
         sisbb.teclarAguardarTroca("@E");
         
        sisbb.aguardar(22, 3, "Confirma os dados do lançamento?");
        sisbb.posicionar(22, 36);
         
        sisbb.teclar("s");
        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardar(23, 3, "Pressione ENTER para continuar");
        sisbb.teclarAguardarTroca("@E"); 
        sisbb.aguardar(7, 16, "1100237");
        
        sisbb.posicionar(8, 16);
        sisbb.teclar("REJUD99");
        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardar(19, 37, "Registra Finalidade");
        
        sisbb.posicionar(12, 13);
        sisbb.teclar("Tarifa DOC/TED ref. a levto de depósito judicial em favor do BB ");
        sisbb.posicionar(13, 13);
        sisbb.teclar("Conta judicial depositada em outros bancos cfe IN 869-2,2.4.5:  Banco depositário: " + bancoBeneficiario );
        sisbb.posicionar(15, 13);
        sisbb.teclar("NPJ : " + npj + "- Valor crédito recebido  " +  valorCredito   );
        sisbb.posicionar(16, 13);
        sisbb.teclar("Data Crédito: " + dataCredito );
        sisbb.teclarAguardarTroca("@E");
        
        if(sisbb.copiar(17, 7, 6).equals("Existe")){
            sisbb.teclarAguardarTroca("@E");
        }
        
        
        
        sisbb.aguardar(22, 3, "Confirma Confecção do Lançamento");
        sisbb.posicionar(22, 37);
        sisbb.teclar("s" );
        
        sisbb.teclarAguardarTroca("@E");
        
        
        String n = sisbb.copiar(23, 3, 38);
        
        numeroEgt = coletas.tratarVariavel(n);
        
    
        if (!n.equals("")){
            
            msgfim = n;
            return;
        }
        
    

   
}

    


}



