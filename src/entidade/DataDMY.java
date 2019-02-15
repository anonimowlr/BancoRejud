/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

/**
 *
 * @author f4281065
 */
public class DataDMY {
    
    private String diaMovimentacao;
    private String mesMovimentacao;
    private String anoMovimentacao;

    public DataDMY() {
    }
    
     /**
     * Retorna data sem espaço.
     * 
     * @return String com a data formato ddMMyyyy
     */
    public String stringDMY() {
        return diaMovimentacao + mesMovimentacao + anoMovimentacao;
    }

    public String getDiaMovimentacao() {
        return diaMovimentacao;
    }

    public void setDiaMovimentacao(String diaMovimentacao) {
        this.diaMovimentacao = diaMovimentacao;
    }

    public String getMesMovimentacao() {
        return mesMovimentacao;
    }

    public void setMesMovimentacao(String mesMovimentacao) {
        this.mesMovimentacao = mesMovimentacao;
    }

    public String getAnoMovimentacao() {
        return anoMovimentacao;
    }

    public void setAnoMovimentacao(String anoMovimentacao) {
        this.anoMovimentacao = anoMovimentacao;
    }

    @Override
    public String toString() {
        return diaMovimentacao + "/" + mesMovimentacao + "/" + anoMovimentacao;
    }

}
