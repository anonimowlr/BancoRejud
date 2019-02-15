package rotinasSisbb;

import br.com.bb.jibm3270.RoboException;
import conexao.ConnectionFactory;
import dao.DAO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import entidade.SolicitacaoTarifa;
import entidade.Usuario;
import rotinasPortal.Coletas;
import sisbbgeneric.LogarUsuarioSisbb;
import sisbbgeneric.Janela3270;

public class VerificarSituacaoPartidaEgt {

    DAO<String> d = new DAO<>("rejud");
    Coletas coletas = new Coletas();
    String msgfim = "";
    String numeroEgt = "";
    Usuario user = new Usuario();

    public void inicioRotinaSisbb(Janela3270 sisbb, String sistema) throws Throwable {
        String matricula = user.getMatricula();

        if (matricula == null) {
            JOptionPane.showMessageDialog(null, "Sem usuário logado, não será possível continuar");
            return;

        }

        LogarUsuarioSisbb logar = new LogarUsuarioSisbb();
        logar.autenticar(sistema, sisbb);
        posicionarTela(sisbb);
        selecionaRegLeitura();
        lerbancoDados(sisbb);
        
        
        
       
       
    }

    private void selecionaRegLeitura() throws SQLException {

        PreparedStatement stmt;
        Connection con;
        con = ConnectionFactory.conectar("rejud");
        String query = "Update tb_tarifa as t set t.ID_SITUACAO_EGT = '' where (t.ID_SITUACAO_EGT not like '%Processado%' and t.FUNCIONARIO_RESPONSAVEL = ? ) ";

        stmt = con.prepareStatement(query);
        stmt.setString(1, user.getMatricula());
        stmt.executeUpdate();

        if (user.getGrupoUsuario().equals("ADMINISTRADOR")) {
            query = "Update tb_tarifa as t set t.ID_SITUACAO_EGT = '' where (t.ID_SITUACAO_EGT not like '%Processado%') ";
            stmt = con.prepareStatement(query);
            stmt.executeUpdate();
        }

        con.close();
        stmt.close();

    }

    private void lerbancoDados(Janela3270 sisbb) throws SQLException, Throwable {
        
        Usuario user = new Usuario();
        

        if (user.getMatricula() == null) {
            JOptionPane.showMessageDialog(null, "Sem usuário logado, não será possível continuar");
            return;

        }

        SolicitacaoTarifa s = new SolicitacaoTarifa();
        PreparedStatement stmt;
        Connection con;
        con = ConnectionFactory.conectar("rejud");
        String query = "Select * from tb_tarifa where ((NUMERO_EGT <> '' AND NUMERO_EGT is not null) AND ((FUNCIONARIO_RESPONSAVEL = ?) AND (ID_SITUACAO = 'Efetivacao aguardando processamento EVT' OR ID_SITUACAO = 'Efetivado/Liquidado') AND(ID_SITUACAO_EGT IS NULL OR ID_SITUACAO_EGT ='')))";

        stmt = con.prepareStatement(query);
        //stmt.setString(1, "Nenhum registro encontrado.");
        stmt.setString(1, user.getMatricula());
        
        
        if (user.getGrupoUsuario().equals("ADMINISTRADOR")) {

            query = "Select * from tb_tarifa where ((NUMERO_EGT <> '' AND NUMERO_EGT is not null) AND ((ID_SITUACAO = 'Efetivacao aguardando processamento EVT' OR ID_SITUACAO = 'Efetivado/Liquidado')AND (ID_SITUACAO_EGT IS NULL OR ID_SITUACAO_EGT ='')))";
             stmt = con.prepareStatement(query);
        }

      
        ResultSet rs = stmt.executeQuery();

        int numeroReg = d.contareg(rs);

        if (numeroReg < 1) {
            
         
          JOptionPane.showMessageDialog(null, "Fim de rotina");
          con.close();
          stmt.close();
          rs.close();
          sisbb.rotinaEncerramento();
          return;
          

            

        }

        
        do {

            String npj = rs.getString("NPJ");

            String numeroEgt = rs.getString("NUMERO_EGT");
            String dataDepachoPortal = rs.getString("DATA_DESPACHO_PORTAL");

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

            consultarPartida(sisbb, numeroEgt, dataDepachoPortal);

            if (!msgfim.equals("")) {
                int codigo = rs.getInt("CODIGO");
                con.close();
                rs.close();
                stmt.close();
                d.editar("tb_tarifa", "ID_SITUACAO_EGT", msgfim, codigo);

                lerbancoDados(sisbb);
                return;
                
            } 
                
            
           
            
        
        } while (rs.next());
        
        

    }

    private void posicionarTela(Janela3270 sisbb) throws RoboException {

        sisbb.aguardar(6, 9, "Confeccionar");

        sisbb.posicionar(19, 20);

    }

    public void consultarPartida(Janela3270 sisbb, String numeroEgt, String dataDepachoPortal) throws Throwable {
       int tamandoDataDespacho = 0;
        while (!sisbb.copiar(19, 3, 15).equals("Informe a opção")) {
            sisbb.teclarAguardarTroca("@3");
            
        
        }
        
        
        if(dataDepachoPortal != null){
        tamandoDataDespacho = dataDepachoPortal.length();
        }

        sisbb.teclar("21");
        sisbb.teclarAguardarTroca("@E");

        sisbb.aguardar(1, 3, "EGTM153L");

        if (tamandoDataDespacho > 0) {
            
            sisbb.posicionar(7, 16);
            sisbb.teclar("  ");
            sisbb.posicionar(7, 16);
            sisbb.teclar("01");
            sisbb.posicionar(7, 21);
            sisbb.teclar("  ");
            sisbb.posicionar(7, 21);
            sisbb.teclar(dataDepachoPortal.subSequence(5, 7).toString());
            
            
            
            sisbb.posicionar(7, 33);
            sisbb.teclar("  ");
            sisbb.posicionar(7, 33);
            sisbb.teclar("30");
            sisbb.posicionar(7, 38);
            sisbb.teclar("  ");
            sisbb.posicionar(7, 38);
            sisbb.teclar(dataDepachoPortal.subSequence(5, 7).toString());

            
            
           

        }

        
        
        sisbb.posicionar(10, 5);
        sisbb.teclar(numeroEgt);
        sisbb.teclarAguardarTroca("@E");
        
        if(sisbb.copiar(23, 3, 3).equals("Nen")){
         return;
        }

        sisbb.aguardar(11, 3, "_");

        sisbb.posicionar(11, 3);
        sisbb.teclar("x");
        sisbb.teclarAguardarTroca("@E");

        sisbb.aguardar(13, 8, "Evento");

        String lancamento = sisbb.copiar(7, 20, 8);

        if (!lancamento.equals(numeroEgt)) {
            msgfim = "Partida não encontrada";
            return;
        }

        String n = sisbb.copiar(11, 17, 15);

        if (!n.equals("")) {

            msgfim = n;
            return;
        }

    }

}
