package rotinasSisbb;

import br.com.bb.jibm3270.RoboException;
import dao.IneditoDAO;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import entidade.Desconciliacao;
import entidade.SolicitacaoInedito;
import rotinasPortal.Coletas;
import rotinasPortal.TratarIneditoPortal;
import util.Utils;
import sisbbgeneric.LogarUsuarioSisbb;
import sisbbgeneric.Janela3270;

/**
 *
 * @author suporte
 */
public class TratarInedito {

    TratarIneditoPortal tratarIneditoPortal = new TratarIneditoPortal(0);
    List<Desconciliacao> lista = null;
    List<SolicitacaoInedito> listaInedito = null;

    IneditoDAO<SolicitacaoInedito> ineditoDAO = new IneditoDAO<>("rejud");
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
        sisbb.posicionar(21, 11);
        sisbb.teclar("51");
        sisbb.teclarAguardarTroca("@E");
        sisbb.posicionar(21, 11);
        sisbb.teclar("01");
        sisbb.teclarAguardarTroca("@E");
        sisbb.posicionar(6, 59);
        sisbb.teclar("             ");

    }

    public void createList(Janela3270 sisbb) throws SQLException {

        listaInedito = ineditoDAO.buscarParaTratar();
        if (listaInedito.size() < 1) {
            JOptionPane.showMessageDialog(null, "Não há inéditos a tratar");
            return;

        }

    }

    public void lerLista(Janela3270 sisbb) throws Throwable {

        int n = 0;
        int qtdreg = listaInedito.size();

        for (SolicitacaoInedito s : listaInedito) {

            BigDecimal valorRegateBanco = Utils.converterDobleParaBigDecimal(s.getVlRsgt());
            String contaDepositaria = s.getContaDepositaria();
            String oficioBanco = s.getNrOficio();
            int codigo = s.getCodigo();

            coletaDadosDJO(sisbb, contaDepositaria, valorRegateBanco, oficioBanco, codigo);

            n++;

        }

    }

    private void coletaDadosDJO(Janela3270 sisbb, String contaDepositaria, BigDecimal valorRegateBanco, String oficioBanco, int codigo) throws RoboException {
        int i;
        while (!sisbb.copiar(5, 44, 7).equals("Mandado")) {
            sisbb.teclarAguardarTroca("@3");
        }

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

        }

        // posicionar na última tela de resgates   
        while (!sisbb.copiar(23, 3, 14).equals("Última página.")) {

            sisbb.teclarAguardarTroca("@8");

        }

        while (!sisbb.copiar(23, 3, 16).equals("Primeira página.")) {

            for (i = 20; i >= 11; i--) {
                String situacaoResgate;

                while (!sisbb.copiar(9, 63, 13).equals("Valor Líquido")) {

                    sisbb.teclarAguardarTroca("@3");

                }

                if (!sisbb.copiar(i, 3, 5).equals("_")) {
                    sisbb.posicionar(i, 3);
                    sisbb.teclar("x");
                    sisbb.teclarAguardarTroca("@E");
                    situacaoResgate = sisbb.copiar(8, 61, 14);
                    // percorrer detalhe do resgate
                    while (!sisbb.copiar(23, 3, 14).equals("Última Página.")) {

                        sisbb.teclarAguardarTroca("@8");

                    }

                    while (!sisbb.copiar(23, 3, 16).equals("Primeira Página.")) {

                        for (int j = 18; j >= 14; j--) {
                            while (!sisbb.copiar(12, 50, 3).equals("Cta")) {

                                sisbb.teclarAguardarTroca("@3");
                            }
                            if (!sisbb.copiar(j, 47, 6).equals("_")) {

                                sisbb.posicionar(j, 47);
                                sisbb.teclar("x");
                                sisbb.teclarAguardarTroca("@E");

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

                                if (valorResgateBruto.compareTo(valorRegateBanco) == 0 && situacaoResgate.equals("RESGATADO/PAGO")) {

                                    String msgfim = tratarIneditoPortal.tratarIneditoPortal(valorResgateBruto, contaDepositaria, oficioBanco, agenciaPagadora);

                                    ineditoDAO.editarGenerico("tb_inedito", "OBS_TRATAR", msgfim, codigo);

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
                sisbb.teclarAguardarTroca("@7");

            }

        }
        
         if(sisbb.copiar(23, 3, 16).equals("Primeira página.")){
                
               ineditoDAO.editarGenerico("tb_inedito", "OBS_TRATAR", "Valor resgate informado não corrresponde ao do DJO- verifique se há IR e some ao valor do resgate", codigo);
        }
        
        
    }

}
