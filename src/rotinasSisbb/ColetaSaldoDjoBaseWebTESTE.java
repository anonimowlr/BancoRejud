package rotinasSisbb;

import br.com.bb.jibm3270.RoboException;
import dao.DAO;
import dao.DesconciliacaoDAO;
import java.util.List;
import javax.swing.JOptionPane;
import entidade.Desconciliacao;
import entidade.Usuario;
import rotinasPortal.Coletas;
import util.Utils;
import sisbbgeneric.LogarUsuarioSisbb;
import sisbbgeneric.Janela3270;


/**
 *
 * @author suporte
 */
public class ColetaSaldoDjoBaseWebTESTE {
               
    DesconciliacaoDAO<Desconciliacao> desconciliacaoDAO = new DesconciliacaoDAO<>("rejud");
    DAO<String> d = new DAO<>("rejud");
    
    Usuario user = new Usuario();
    Coletas coletas = new Coletas();
    
    List<Desconciliacao> lista = null;
    String msgfim = "";
    String agenciaDetentora;
    String saldoDeposito;
    String dataSaldo;
    
    
//    public void inicioRotinaSisbb(Janela3270 sisbb, String sistema) throws Throwable {
//        LogarUsuarioSisbb logar = new LogarUsuarioSisbb();
//        logar.autenticar(sistema, sisbb);
//        posicionarTela(sisbb);        
//        createList(sisbb);        
//        lerLista(sisbb);
//       
//    }
    
    public void inicioRotinaAlternativa(Janela3270 sisbb, String sistema, List<Desconciliacao> subLista) throws Throwable {

        sisbb.setTamanho(700, 500);
        
        LogarUsuarioSisbb logar = new LogarUsuarioSisbb();
        logar.autenticar(sistema, sisbb);
        
        posicionarTela(sisbb);
        
        lerSubLista(sisbb, subLista);
        
    }

    
    private void posicionarTela(Janela3270 sisbb) throws RoboException {

        sisbb.aguardar(1, 3, "DJOM000M");
        sisbb.teclarAguardarTroca("@E");
        sisbb.posicionar(21, 11);
        sisbb.teclar("12");
        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardar(21, 3, "Informe");
        sisbb.posicionar(21, 44);
        sisbb.teclar("1915");
        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardar(21, 3, "Conta");
        sisbb.posicionar(19, 11);
        sisbb.teclar("12");
        sisbb.teclarAguardarTroca("@E");

    }

//    public void createList(Janela3270 sisbb) {
//
//        lista = desconciliacaoDAO.buscarParaLerSaldoDJO();
//        if (lista.size() < 1) {
//            JOptionPane.showMessageDialog(null, "Não há registros a complementar");
//        }
//
//    }


    public List<Desconciliacao> createListTESTE() {

        List<Desconciliacao> lista2 = desconciliacaoDAO.buscarParaLerSaldoDJO();
        if (lista2.size() < 1) {
            JOptionPane.showMessageDialog(null, "Não há registros a complementar");
        }
        return lista2;
    }
    
    
    private void lerSubLista(Janela3270 sisbb, List<Desconciliacao> subLista) throws Throwable {

        int n = 0;

        for (Desconciliacao s : subLista) {

            Integer codigo = s.getCodigoDesconciliacao();
            String contaDepositaria = s.getContaDepositaria();

            coletaDadosDjo(sisbb, contaDepositaria);

            if (saldoDeposito != null) {
                d.editarBaseWeb("tb_desconciliacao_djo_paj_t", "SALDO_DEPOSITO", saldoDeposito, codigo);
            }
            d.editarBaseWeb("tb_desconciliacao_djo_paj_t", "DATA_ROTINA_SALDO_DJO", Utils.getDataAtualYMD(), codigo);

            n++;

        }
        
    }
    
    
//    public void lerLista(Janela3270 sisbb) throws Throwable {
//        
//        int n = 0;
//        int qtdreg = lista.size();
//        
//        for (Desconciliacao s : lista) {
//            
//            Integer codigo = s.getCodigoDesconciliacao();
//            String contaDepositaria = s.getContaDepositaria();       
//            
//            coletaDadosDjo(sisbb, contaDepositaria);
//            
//            if (saldoDeposito != null) {
//                d.editarBaseWeb("tb_desconciliacao_djo_paj", "SALDO_DEPOSITO", saldoDeposito, codigo);
//            }            
//            d.editarBaseWeb("tb_desconciliacao_djo_paj", "DATA_ROTINA_SALDO_DJO", Utils.getDataAtualYMD(), codigo);
//    
//            n++;
//            
//        }
//        
//    }
    

    private void coletaDadosDjo(Janela3270 sisbb, String contaJudicial) throws Throwable {
        
        if(contaJudicial.equals("0") || contaJudicial=="0"){
            return;
        }

        while (!sisbb.copiar(6, 7, 18).equals("Por Conta judicial")) {
            sisbb.teclarAguardarTroca("@3");
        }

        sisbb.posicionar(6, 33);
        sisbb.teclar("             ");
        sisbb.posicionar(6, 33);
        sisbb.teclar(contaJudicial);
        sisbb.teclarAguardarTroca("@E");
        
        Thread.sleep(200);
        
        String texto = sisbb.copiar(12, 20, 30);
        
        if (texto.equals("CONTA JUDICIAL NAO LOCALIZADA.")) {
            sisbb.teclarAguardarTroca("@E");
            msgfim = texto;
            agenciaDetentora = "101010";
            saldoDeposito = "000000";
            
            return;
            
        }
        
        texto = sisbb.copiar(23, 3, 51);
        
        if (texto.equals("Conta judicial da esfera Tribut.Mun.")) {
            
            posicionarContaJudicialTipoDeJustica(sisbb, contaJudicial, "15");
            
        } else if (texto.equals("Conta judicial da esfera Estadual")) {
            
            posicionarContaJudicialTipoDeJustica(sisbb, contaJudicial, "12");
            
        } else if (texto.equals("Conta judicial da esfera Tribut.Est.")) {
            
            posicionarContaJudicialTipoDeJustica(sisbb, contaJudicial, "14");
            
        } else if (texto.equals("Conta judicial da esfera Trabalhista")) {
            
            posicionarContaJudicialTipoDeJustica(sisbb, contaJudicial, "13");
            
        } else if (texto.equals("Conta Precatório. Altere a opção no menu principal.")) {
            return;
//            ver conta 4400127460711
//            posicionarContaJudicialTipoDeJustica(sisbb, contaJudicial, "22");
            
        }
        
        Thread.sleep(300);
        
        sisbb.posicionar(21, 17);
        sisbb.teclar("01");
        sisbb.teclarAguardarTroca("@E");
        
        Thread.sleep(500);
        texto = sisbb.copiar(14, 10, 6);
        
        if (texto.equals("Deseja")) { 
            sisbb.aguardar(10,31,"Mensagem");
            sisbb.posicionar(14, 33);
            sisbb.teclar("S");
            sisbb.teclarAguardarTroca("@E");
            
        }
        
        texto = sisbb.copiar(12, 5, 1);
        
        if (texto.equals("2")) { 
            sisbb.aguardar(1,3,"DJOM0610");
            sisbb.teclarAguardarTroca("@3");

        }
        
        Thread.sleep(500);
        
        sisbb.aguardarInd(1,3,"DJOM0970");
        sisbb.posicionar(21, 11);           //ver trava
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
        
        if (sisbb.copiar(23, 3, 4).equals("Erro")) {
            JOptionPane.showMessageDialog(null, "Erro Sisbb!");
            return;
        }
        
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
                String saldoDepositoTexto = sisbb.copiar(i, 65, 14);
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

    private void posicionarContaJudicialTipoDeJustica(Janela3270 sisbb, String contaJudicial, String tipoDeJustica) throws RoboException, InterruptedException {

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
        Thread.sleep(500);
        sisbb.aguardar(21, 3, "Conta");
        sisbb.posicionar(19, 11);
        sisbb.teclar("12");
        sisbb.teclarAguardarTroca("@E");
        sisbb.posicionar(6, 33);
        sisbb.teclar(contaJudicial);
        sisbb.teclarAguardarTroca("@E");

    }



}
