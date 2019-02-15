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
import util.Utils;
import sisbbgeneric.LogarUsuarioSisbb;
import sisbbgeneric.Janela3270;

/**
 *
 * @author suporte
 */
public class ColetaAgenciaDetentoraDjo {

    DAO<String> d = new DAO<>("rejud");
    Coletas coletas = new Coletas();
    String msgfim = "";

    String agenciaDetentora;
    String saldoDeposito;
    String dataSaldo;
    
    

    public void inicioRotinaSisbb(Janela3270 sisbb, String sistema) throws Throwable {
        LogarUsuarioSisbb logar = new LogarUsuarioSisbb();
        logar.autenticar(sistema, sisbb);
        posicionarTela(sisbb);
        lerbancoDados(sisbb);
    }

    private void posicionarTela(Janela3270 sisbb) throws RoboException {

        sisbb.aguardar(1, 3, "DJOM000M");
        sisbb.teclarAguardarTroca("@E");
        sisbb.posicionar(21, 11);
        sisbb.teclar("12");
        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardar(21, 3, "Informe");
        sisbb.posicionar(21, 44);
        sisbb.teclar("3793");
        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardar(21, 3, "Conta");
        sisbb.posicionar(19, 11);
        sisbb.teclar("12");
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
        String query = "SELECT *  from tb_inedito where ((DEPENDENCIA_DTRA is null or DEPENDENCIA_DTRA='') AND ((FUNCIONARIO_RESPONSAVEL_SOLICITACAO = ? ))) ORDER BY TIPO_DESTINACAO DESC";

        stmt = con.prepareStatement(query);

        stmt.setString(1, matricula);

        if (user.getGrupoUsuario().equals("ADMINISTRADOR")) {

            query = "SELECT *  from tb_inedito where ((DEPENDENCIA_DTRA is null or DEPENDENCIA_DTRA=''))";
            stmt = con.prepareStatement(query);
        }

        ResultSet rs = stmt.executeQuery();

        int numeroReg = d.contareg(rs);

        if (numeroReg < 1) {

          sisbb.rotinaEncerramento();
          JOptionPane.showMessageDialog(null, "Fim de rotina");

        }

        do {

            String npj = rs.getString("NPJ");
            String valorResgate = rs.getString("VL_RSGT");
            String contaJudicial = rs.getString("CONTA_DEPOSITARIA");

            capturaAgenciaDetentoraDeposito(sisbb, npj, valorResgate, contaJudicial);

            if (!msgfim.equals("")) {
                int codigo = rs.getInt("CODIGO");
                con.close();
                rs.close();
                stmt.close();

                d.editar("tb_inedito", "DEPENDENCIA_DTRA", agenciaDetentora, codigo);
                d.editar("tb_inedito", "SALDO_CONTA_DEPOSITARIA", saldoDeposito, codigo);
                d.editar("tb_inedito", "DATA_SALDO", dataSaldo, codigo);
                d.editar("tb_inedito", "OBS_COLETA_DTRA", msgfim, codigo);
                lerbancoDados(sisbb);
            }

        } while (rs.next());

    }

    private void capturaAgenciaDetentoraDeposito(Janela3270 sisbb, String npj, String valorResgate, String contaJudicial) throws Throwable {

        while (!sisbb.copiar(6, 7, 18).equals("Por Conta judicial")) {
            sisbb.teclarAguardarTroca("@3");
        }

        sisbb.posicionar(6, 33);
        sisbb.teclar("             ");
        sisbb.posicionar(6, 33);
        sisbb.teclar(contaJudicial);
        sisbb.teclarAguardarTroca("@E");
        
        
        String texto = sisbb.copiar(12,20,30);
        
        
            if(texto.equals("CONTA JUDICIAL NAO LOCALIZADA.")){
                sisbb.teclarAguardarTroca("@E");
                msgfim = texto;
                agenciaDetentora="101010";
                saldoDeposito="000000";

                return;
                
            }
        
        
        
        
        
       texto = sisbb.copiar(23,3,36);
        
        
                if(texto.equals("Conta judicial da esfera Tribut.Mun.")){
                    
                    posicionarContaJudicialTipoDeJustica(sisbb,contaJudicial,"15");
                    
                
                    
        
                 } else if(texto.equals("Conta judicial da esfera Estadual")){
                     
                     posicionarContaJudicialTipoDeJustica(sisbb,contaJudicial,"12");
                     
                 }else if(texto.equals("Conta judicial da esfera Tribut.Est.")){
                     
                      posicionarContaJudicialTipoDeJustica(sisbb,contaJudicial,"14");
                     
                 } else if(texto.equals("Conta judicial da esfera Trabalhista")){
                     
                      posicionarContaJudicialTipoDeJustica(sisbb,contaJudicial,"13");
                     
                 }
        
            
        
            
        
        
        sisbb.posicionar(21, 17);
        sisbb.teclar("01");
        sisbb.teclarAguardarTroca("@E");

         texto = sisbb.copiar(14, 10, 6);

        if (texto.equals("Deseja")) {
            sisbb.teclarAguardarTroca("@E");

        }

        texto = sisbb.copiar(12, 5, 1);

        if (texto.equals("2")) {
            sisbb.teclarAguardarTroca("@3");

        }

        sisbb.posicionar(21, 11);
        sisbb.teclar("03");
        sisbb.teclarAguardarTroca("@E");

        texto = sisbb.copiar(11, 15, 14);

        if (texto.equals("Este  depósito")) {
            sisbb.teclarAguardarTroca("@E");

        }

        for (int i = 20; i >= 17; i--) {

            while (!sisbb.copiar(23, 3, 14).equals("Última página.")) {
                sisbb.teclarAguardarTroca("@8");

            }

            agenciaDetentora = sisbb.copiar(i, 14, 4);

            if (!agenciaDetentora.equals("")) {
                break;
            }

        }
        

        sisbb.teclarAguardarTroca("@6");
        sisbb.teclarAguardarTroca("@E");

        
        
        
        for (int i = 21; i >= 17; i--) {

            while (!sisbb.copiar(23, 3, 14).equals("Ultima página.")) {
                sisbb.teclarAguardarTroca("@8");
                texto = sisbb.copiar(23, 3, 5);
                
                if(texto.equals("Tecle")){
                   sisbb.teclarAguardarTroca("@3"); 
                   sisbb.teclarAguardarTroca("@6");
                   sisbb.posicionar(14, 19);
                   sisbb.teclar(Utils.getDiaAtual());
                   sisbb.posicionar(14, 21);
                   sisbb.teclar(Utils.getMesAtual());
                   sisbb.posicionar(14, 23);
                   sisbb.teclar(Utils.getAnoAtual());
                   sisbb.teclarAguardarTroca("@E");
                    
                }

            }

            
            
            
            
            
            
            texto = sisbb.copiar(i, 80, 1);

            if (texto.equals("C")) {
                String saldoDepositoTexto = sisbb.copiar(i,65,14);
                 saldoDeposito = Utils.tratarVariavel(saldoDepositoTexto);
                 dataSaldo = Utils.getDataAtualFormatoMysql().toString();
                break;
            }

        }

        if (!agenciaDetentora.equals("")) {
            msgfim = "Agencia Coletada";

            return;
        }

    }

    private void posicionarContaJudicialTipoDeJustica(Janela3270 sisbb, String contaJudicial,String tipoDeJustica) throws RoboException {
        
        
        
        while (!sisbb.copiar(1, 3, 8).equals("DJOM0000")) {
            sisbb.teclarAguardarTroca("@3");
        }
        
        
        
        sisbb.aguardar(1, 3, "DJOM0000");
        
        sisbb.posicionar(21, 11);
        sisbb.teclar(tipoDeJustica);
        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardar(21, 3, "Informe");
        sisbb.posicionar(21, 44);
        sisbb.teclar("3793");
        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardar(21, 3, "Conta");
        sisbb.posicionar(19, 11);
        sisbb.teclar("12");
        sisbb.teclarAguardarTroca("@E");
        sisbb.posicionar(6, 33);
        sisbb.teclar(contaJudicial);
        sisbb.teclarAguardarTroca("@E");
       
    }

    
}
