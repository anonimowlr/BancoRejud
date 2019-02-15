package rotinasSisbb;

import br.com.bb.jibm3270.RoboException;
import dao.ResgateDAO;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import entidade.Resgate;
import rotinasPortal.Coletas;
import rotinasPortal.TratarIneditoPortal;
import util.Utils;
import sisbbgeneric.LogarUsuarioSisbb;
import sisbbgeneric.Janela3270;

/**
 *
 * @author suporte
 */
public class ColetarDadosDJOSequencial {

    TratarIneditoPortal tratarIneditoPortal = new TratarIneditoPortal(0);
    List<Resgate> lista = null;
    List<Resgate> listaResgate = null;

    ResgateDAO<Resgate> resgateDAO = new ResgateDAO<>("rejud");
    Coletas coletas = new Coletas();
    String msgfim = "";

    String agenciaDetentora;
    String saldoDeposito;
    String dataSaldo;

    public void inicioRotinaSisbb(Janela3270 sisbb, String sistema) throws Throwable {

        LogarUsuarioSisbb logar = new LogarUsuarioSisbb();
        logar.autenticar(sistema, sisbb);

        posicionarTela(sisbb);

        createList(sisbb);

        lerLista(sisbb);
            

    }

    private void posicionarTela(Janela3270 sisbb) throws RoboException {

        sisbb.aguardar(1, 3, "DJOM000M");
        sisbb.teclarAguardarTroca("@E");
        sisbb.posicionar(21, 11);
        sisbb.teclar("12");
        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardar(21, 3, "Informe");
        sisbb.posicionar(21, 44);
        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardar(21, 3, "Conta");
        sisbb.posicionar(19, 11);
        sisbb.teclar("18");
        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardar(1, 3, "DJOMR000");
        sisbb.posicionar(21, 11);
        sisbb.teclar("51");
        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardar(1, 3, "DJOMR510");
        sisbb.posicionar(21, 11);
        sisbb.teclar("01");
        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardar(1, 3, "DJOMR511");
        sisbb.posicionar(6, 59);
        sisbb.teclar("             ");

    }

    public void createList(Janela3270 sisbb) throws SQLException {

        listaResgate = resgateDAO.buscar();
        if (listaResgate.size() < 1) {
            JOptionPane.showMessageDialog(null, "Não há coleta de DJO para o sequencial");
        }

    }

    public void lerLista(Janela3270 sisbb) throws Throwable {

        int n = 0;
        int qtdreg = listaResgate.size();

        for (Resgate s : listaResgate) {

            BigDecimal valorRegateBanco = s.getIDValor();
            String contaDepositaria = s.getContaDepositaria();
            String oficioBanco = s.getSolicOrdem();
            int codigo = s.getCod();

            coletaDadosDJO(sisbb, contaDepositaria, valorRegateBanco, oficioBanco, codigo);

            n++;

        }

    }

    private void coletaDadosDJO(Janela3270 sisbb, String contaDepositaria, BigDecimal valorRegateBanco, String oficioBanco, int codigo) throws RoboException, InterruptedException {
                
        if (contaDepositaria.contains("461")){
            Thread.sleep(500);
        }
        
        int i;
        while (!sisbb.copiar(5, 44, 7).equals("Mandado")) {
            Thread.sleep(500);
            sisbb.teclarAguardarTroca("@3");
            Thread.sleep(500);
            
            if (sisbb.copiar(1, 3, 8).equals("DJOMR511")) {
                break;
            }
        }

        if (sisbb.copiar(1, 3, 8).equals("DJOMR510")) {
            sisbb.posicionar(21, 11);
            sisbb.teclar("01");
            sisbb.teclarAguardarTroca("@E");
        }
        
        sisbb.aguardar(1, 3, "DJOMR511");
        sisbb.posicionar(6, 59);
        sisbb.teclar("             ");
        sisbb.posicionar(6, 59);
        sisbb.teclar(contaDepositaria);
        sisbb.teclarAguardarTroca("@E");

        if (sisbb.copiar(23, 3, 6).equals("Nenhum") || sisbb.copiar(23, 3, 6).equals("Altere")) {
            return;
        }

        // posiconar na primeira página  da tela de resgate       
        while (!sisbb.copiar(23, 3, 16).equals("Primeira página.")) {
            sisbb.teclarAguardarTroca("@7");
            Thread.sleep(300);
        }

        // posicionar na última tela de resgates   
        while (!sisbb.copiar(23, 3, 14).equals("Última página.") || sisbb.copiar(23, 3, 6).equals("Nenhum")) {
            
            if (sisbb.copiar(23, 3, 6).equals("Nenhum")) {
               sisbb.teclarAguardarTroca("@7");
                break;
            } else {
                sisbb.teclarAguardarTroca("@8");
                Thread.sleep(300);
            }
            
        }

        while (!sisbb.copiar(23, 3, 16).equals("Primeira página.")) {

            for (i = 20; i >= 11; i--) {
                String situacaoResgate;

                while (!sisbb.copiar(9, 63, 13).equals("Valor Líquido")) {
                        sisbb.teclarAguardarTroca("@3");
                        Thread.sleep(300);
                    }
                
                
                if (!sisbb.copiar(i, 3, 5).equals("_")) {
                    Thread.sleep(300);
                    sisbb.posicionar(i, 3);
                    sisbb.teclar("x");
                    sisbb.teclarAguardarTroca("@E");
                    sisbb.aguardar(1, 3, "DJOMR033");
                    situacaoResgate = sisbb.copiar(8, 61, 14);
                    // percorrer detalhe do resgate
                    while (!sisbb.copiar(23, 3, 14).equals("Última Página.")) {

                        sisbb.teclarAguardarTroca("@8");

                    }

                    while (!sisbb.copiar(23, 3, 16).equals("Primeira Página.")) {

                        for (int j = 18; j >= 14; j--) {
                            while (!sisbb.copiar(12, 50, 3).equals("Cta")) {
                                sisbb.teclarAguardarTroca("@3");
                                Thread.sleep(300);
                            }
                            
                            if (!sisbb.copiar(j, 47, 6).equals("_")) {

                                sisbb.posicionar(j, 47);
                                sisbb.teclar("x");
                                sisbb.teclarAguardarTroca("@E");
                                sisbb.aguardar(8, 52, "Dados");

                                String ir = sisbb.copiar(15, 64, 13);
                                BigDecimal impostoRenda;
                                
                                if (ir.equals("")) {
                                    impostoRenda = new BigDecimal(0.00);
                                } else {
                                    impostoRenda = Utils.converterDobleParaBigDecimal(ir);
                                }
                                
                                String resgateLiquido = sisbb.copiar(18, 64, 15);
                                BigDecimal valorResgateLiquido = Utils.converterDobleParaBigDecimal(resgateLiquido);
                                String oficioDjoSisbb = sisbb.copiar(5, 24, 20);
                                String agenciaPagadora = sisbb.copiar(8, 24, 4);

                                BigDecimal valorResgateBruto = valorResgateLiquido.add(impostoRenda);

                                if (valorResgateBruto.compareTo(valorRegateBanco) == 0 && situacaoResgate.equals("RESGATADO/PAGO") && oficioBanco.equals(oficioDjoSisbb)) {

                                    //String msgfim = tratarIneditoPortal.tratarIneditoPortal(valorResgateBruto, contaDepositaria, oficioBanco, agenciaPagadora);
                                    coletaDadosResgate(sisbb, codigo);
                                    //ineditoDAO.editarGenerico("tb_inedito", "OBS_TRATAR", msgfim, codigo);

                                    return;

                                } else {

                                    sisbb.teclarAguardarTroca("@3");

                                    if (j == 14) {
                                        sisbb.teclarAguardarTroca("@7");

                                    }

                                }

                            }

                        }

                    }

                }

            }

            if (i == 10) {
                sisbb.teclarAguardarTroca("@3");
                
                
                sisbb.aguardar(1, 3, "DJOMR511");
                
                
                sisbb.teclarAguardarTroca("@7");
                Thread.sleep(600);
            }

        }
        
//        if(sisbb.copiar(23, 3, 16).equals("Primeira página.")){
//                
//               //ineditoDAO.editarGenerico("tb_inedito", "OBS_TRATAR", "Valor resgate informado não corrresponde ao do DJO", codigo);
//        }        
        
    }

    private void coletaDadosResgate(Janela3270 sisbb, int codigo) throws RoboException {

        Resgate resgate = new Resgate();
        
        resgate.setDjo51(sisbb.copiar(9, 60, 18));
        resgate.setDjo51Parcela(sisbb.copiar(10, 60, 3));
        resgate.setDjo51Protocolo(sisbb.copiar(6, 24, 20));
        resgate.setFinalizadoPor(sisbb.copiar(5, 61, 8));

        resgate.setDjo51Capitalresgatado(Utils.converterDobleParaBigDecimal(sisbb.copiar(12, 60, 18)));

        if (sisbb.copiar(13, 60, 18).equals("")) {
            resgate.setDjo51Jurosprojetado(Utils.converterDobleParaBigDecimal("0,00"));
        } else {
            resgate.setDjo51Jurosprojetado(Utils.converterDobleParaBigDecimal(sisbb.copiar(13, 60, 18)));
        }

        sisbb.teclarAguardarTroca("@3");

        resgate.setDjo51Beneficiario(sisbb.copiar(9, 24, 20));
        resgate.setDjo51AgenciaResponsavel(sisbb.copiar(8, 24, 4));

        resgate.setDjo51Ordem(sisbb.copiar(5, 24, 21));
        resgate.setDjo51Finalidade(sisbb.copiar(11, 24, 20));

        if (sisbb.copiar(24, 33, 9).equals("F10 Texto") || sisbb.copiar(5, 61, 8).equals("DJOSW023")) {
            resgate.setAlvaraEletronico("SIM");
        } else {
            resgate.setAlvaraEletronico("NAO");
        }

        sisbb.teclarAguardarTroca("@12");
        if (sisbb.copiar(23, 2, 5).equals("SS161")) {
            return;
        }
        sisbb.aguardar(12, 35, "Participantes");
        
        resgate.setDjo51F12ParteNome(sisbb.copiar(16, 5, 35));
        resgate.setDjo51F12ParteTipo(sisbb.copiar(16, 57, 20));

        resgate.setCod(codigo);
        
        resgateDAO.inserirDadosDJOSequencial(resgate);

    }

}
