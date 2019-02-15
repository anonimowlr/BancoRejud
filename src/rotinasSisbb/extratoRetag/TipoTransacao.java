/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rotinasSisbb.extratoRetag;

import br.com.bb.jibm3270.RoboException;
import dao.Conta99DAO;
import entidade.Conta99;
import sisbbgeneric.Janela3270;
import util.Utils;

/**
 *
 * @author f4281065
 */
public class TipoTransacao {
    
    private String saldoFinalStr;    
    private String codigoMov;
    private boolean ehNumero;
    
    Conta99DAO<Conta99> conta99Dao;

    public TipoTransacao(Conta99DAO<Conta99> conta99Dao) {
        this.conta99Dao = conta99Dao;
    }
        
    /**
     * Percorre linha por linha o extrato do retag procurando pelas transacoes,
     * seta o saldo final do extrato.
     * 
     * @param sisbb janela sisbb
     * @throws RoboException interacao sisbb
     */
    public void gravaTransacao(Janela3270 sisbb) throws RoboException, Exception {
                
        for (int i = 8; i < 22; i++) {

            codigoMov = sisbb.copiar(i, 13, 3);
            
            try {
                int valor = Integer.parseInt(codigoMov);
                ehNumero = true;
            } catch (NumberFormatException e) {
                ehNumero = false;
            }
                        
            if (ehNumero) {
                
                Conta99 conta99 = new Conta99();                
                
                switch (codigoMov) {
                    case "976":
                    case "623":
                        docTed(sisbb, conta99, i);
                        break;
                    default:
                        transacaoPadrao(sisbb, conta99, i);
                }
                
            }

            if (sisbb.copiar(i, 13, 11).equals("Saldo Final")) {
                saldoFinalStr = sisbb.copiar(i, 64, 16).trim();
                saldoFinalStr = saldoFinalStr.replace(".", "");
                saldoFinalStr = saldoFinalStr.replace(",", ".");
            } 

        }

    }
    
    /**
     * Seta os campos do objeto Conta99 para inserir no BD, metodo aplicado 
     * quando o tipo de transacao ocupa duas linhas do extrato
     * 
     * @param sisbb janela sisbb
     * @param conta99 objeto Conta99
     * @param i linha sisbb
     * @throws RoboException interacao sisbb
     */
    private void docTed(Janela3270 sisbb, Conta99 conta99, int i) throws Exception {
 
        transacaoPadrao(sisbb, conta99, i);

        conta99.setCodigoBanco(Integer.parseInt(sisbb.copiar(i + 1, 17, 3)));
        conta99.setCodigoAgencia(Integer.parseInt(sisbb.copiar(i + 1, 21, 4)));
        conta99.setCpfCnpj(Double.parseDouble(sisbb.copiar(i + 1, 26, 14).trim()));
        conta99.setDescricaoBancos(sisbb.copiar(i + 1, 41, 14));

        conta99Dao.inserirMovimento(conta99);

    }
    
    /**
     * Seta os campos do objeto Conta99 para inserir no BD, metodo aplicado 
     * quando o tipo de transacao ocupa uma linhas do extrato
     * 
     * @param sisbb janela sisbb
     * @param conta99 objeto Conta99
     * @param i linha sisbb
     * @throws RoboException interacao sisbb
     */
    private void transacaoPadrao(Janela3270 sisbb, Conta99 conta99, int i) throws Exception {

        conta99.setCodigoHistorico(Integer.parseInt(codigoMov));      
        conta99.setDataMovimentacao(Utils.formataData(sisbb.copiar(4, 39, 10)));
        conta99.setDescricaoHistorico(sisbb.copiar(i, 17, 16));        
        conta99.setNumeroDoc(Long.parseLong((sisbb.copiar(i, 34, 16).trim())));        
        conta99.setNumeroLote(Integer.parseInt(sisbb.copiar(i, 56, 5)));
        
        if (!sisbb.copiar(i, 51, 5).equals("")) {
            conta99.setCodigoOrigem(Integer.parseInt(sisbb.copiar(i, 51, 4)));
        }

        String valorSISBB = sisbb.copiar(i, 64, 16).trim();
        valorSISBB = valorSISBB.replace(".", "");
        valorSISBB = valorSISBB.replace(",", ".");
        conta99.setValor(Double.parseDouble(valorSISBB));
        
        conta99.setTipoLancamento(sisbb.copiar(i, 80, 1));
        
        if (!codigoMov.equals("976") && !codigoMov.equals("623")) {
            conta99Dao.inserirMovimento(conta99);
        }
        
    }

    public String getSaldoFinalStr() {
        return saldoFinalStr;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TipoTransacao other = (TipoTransacao) obj;
        return true;
    }    
    
}
