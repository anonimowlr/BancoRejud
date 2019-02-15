/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rotinasSisbb;

import br.com.bb.jibm3270.RoboException;
import dao.Conta99DAO;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import entidade.Conta99;
import entidade.DataDMY;
import enumEntidade.ContaEnum;
import rotinasSisbb.extratoRetag.TipoTransacao;
import sisbbgeneric.Janela3270;
import sisbbgeneric.LogarUsuarioSisbb;
import util.Utils;

/**
 * Classe para capturar as transações da conta jurídica
 * 
 * @author f4281065
 */
public class ColetaConta99 {
        
    ContaEnum contaEnum;
    Conta99DAO<Conta99> conta99Dao;

    DataDMY dataObj = new DataDMY();
    
    public ColetaConta99(ContaEnum contaEnum) { 
        this.contaEnum = contaEnum;
        this.conta99Dao = new Conta99DAO<>(contaEnum.getBancoDados(), contaEnum.getTabelaBancoDados());
    }
        

    public void inicioRotinaSisbb(String sistema) throws Throwable {
        
        Date dataRecenteMovDB = conta99Dao.buscaDataRecente("DATA_MOV");        //ultima entrada data de movimentação RETAG no BD
        String dataAlterada = alteraDia(dataRecenteMovDB, 1);
        
        if (Utils.getDataAtualYMD().equals(dataAlterada)) {
            JOptionPane.showMessageDialog(null, "Rotina de " + dataObj.toString() + " já realizada");
        } else {
            
            Janela3270 sisbb = new Janela3270();
            sisbb.setTamanho(700, 500);
            LogarUsuarioSisbb logar = new LogarUsuarioSisbb();
            logar.autenticar(sistema, sisbb);

            int j = 1;
            while (!Utils.getDataAtualYMD().equals(alteraDia(dataRecenteMovDB, j))) {
                posicionarTela(sisbb);
                BigDecimal saldoTotal = percorrerExtrato(sisbb);                //grava as transações e verificar saldos
    
                BigDecimal creditoBD = new BigDecimal(String.valueOf(conta99Dao.saldoCredito99(alteraDia(dataRecenteMovDB, j))));
                BigDecimal debitoBD = new BigDecimal(String.valueOf(conta99Dao.saldoDebito99(alteraDia(dataRecenteMovDB, j))));
                BigDecimal saldoFinalBD = creditoBD.subtract(debitoBD);
                
                if (!saldoTotal.equals(saldoFinalBD)) {
                    JOptionPane.showMessageDialog(null, "Favor verificar a coleta.\n"
                            + "Transações capturadas não conferem com o saldo final.\n\n"
                            + "Saldo capturado pelo banco de dados:\nR$ " + saldoFinalBD + "\n"
                            + "Diferença saldo anterior com final:\nR$ " + saldoTotal,
                            "Atenção", JOptionPane.WARNING_MESSAGE);
                }
                
                j++;
            }
                   
            
//            ComplementoTFI compe = new ComplementoTFI(conta99Dao, contaEnum);
//            compe.inicioRotina(sisbb);
            
            sisbb.rotinaEncerramento();
//            JOptionPane.showMessageDialog(null, "Rotina finalizada");
            
            int k = JOptionPane.showConfirmDialog(null, "Rotina de captura das transações RETAG finalizada!!\n\n"
                    + "Gostaria de complementar as transações via COMPE?\n\n",
                    "Coleta RETAG", JOptionPane.YES_NO_OPTION);

            if (k == JOptionPane.YES_OPTION) {
                ComplementoTFI compe = new ComplementoTFI(contaEnum);
                compe.rotinaTFI();
            }
            
        }
  
    }
 
    /**
     * Posicionamento, interação janela sisbb no RETAG YC9
     * 
     * @param sisbb janela sisbb
     * @throws RoboException
     * @throws ParseException 
     */
    private void posicionarTela(Janela3270 sisbb) throws RoboException, ParseException {
       
        sisbb.aguardarInd(1, 2, "MTG");
        sisbb.posicionar(21, 18);
        sisbb.teclarAguardarTroca("YC9");
        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardar(1, 2, "MPX09M");
        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardar(3, 23, "Extrato");
        sisbb.posicionar(4, 11);
        sisbb.teclar(contaEnum.getNumeroAgencia());
        sisbb.posicionar(4, 40);
        sisbb.teclar(contaEnum.getNumeroConta());

        for (int i = 18; i < 20; i++) {
            sisbb.posicionar(i, 18);
            sisbb.teclar(dataObj.getDiaMovimentacao());
            sisbb.posicionar(i, 23);
            sisbb.teclar(dataObj.getMesMovimentacao());
            sisbb.posicionar(i, 28);
            sisbb.teclar(dataObj.getAnoMovimentacao());
        }

        sisbb.teclarAguardarTroca("@E");
    }

    /**
     * Percorre as páginas do extrato da conta judicial no RETAG
     * 
     * @param sisbb janela sisbb
     * @return saldo final do extrato (final - inicial) para conferência
     * @throws RoboException
     * @throws Exception 
     */
    private BigDecimal percorrerExtrato(Janela3270 sisbb) throws RoboException, Exception {

        if (sisbb.copiar(9, 15, 7).equals("N  A  O") || sisbb.copiar(10, 2, 11).equals("OBSERVACOES")) {
            sisbb.teclarAguardarTroca("@3");
            return new BigDecimal("0.0");
        } else {

            TipoTransacao tt = new TipoTransacao(conta99Dao);

            String saldoAnteriorStr = sisbb.copiar(8, 64, 16).trim();
            saldoAnteriorStr = saldoAnteriorStr.replace(".", "");
            saldoAnteriorStr = saldoAnteriorStr.replace(",", ".");

            while (sisbb.copiar(23, 2, 8).equals("Continua")) {

                tt.gravaTransacao(sisbb);
                sisbb.teclarAguardarTroca("@8");
            }
            
            tt.gravaTransacao(sisbb);

            BigDecimal saldoFinal = new BigDecimal(tt.getSaldoFinalStr());
            BigDecimal saldoAnterior = new BigDecimal(saldoAnteriorStr);
            BigDecimal total = saldoFinal.subtract(saldoAnterior);
                
            sisbb.teclarAguardarTroca("@3");

            return total;

        }
   
    }
   
     /**
     * Altera a data através de acrescimo de dias
     * 
     * @param data data a ser alterada
     * @param i dias a acrescentar (+i) ou diminuir (-i)
     * @return String com a data formato yyyy-MM-dd
     * @throws ParseException Formato errado para conversao
     */
    @SuppressWarnings("static-access")
    public String alteraDia(Date data, int i) throws ParseException {
 
        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
        
        Date dataFormat = fd.parse(fd.format(data)); //StrToDate
        
        java.util.Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(dataFormat);
        cal.add(GregorianCalendar.DAY_OF_MONTH, i);
        
        String dataCapturaStr = fd.format(cal.getTime());                 
        
        dataObj.setDiaMovimentacao(String.format("%02d", cal.get(cal.DAY_OF_MONTH)));
        dataObj.setMesMovimentacao(String.format("%02d", cal.get(cal.MONTH)+1));
        dataObj.setAnoMovimentacao(String.format("%04d", cal.get(cal.YEAR)));
        
        return dataCapturaStr;
    }
    
}
