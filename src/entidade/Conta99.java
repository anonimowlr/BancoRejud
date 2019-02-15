/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author f4281065
 */
public class Conta99 implements Serializable {
    
    private int codigoConta99;
    private Date dataMovimentacao;
    private int codigoHistorico;
    private String descricaoHistorico;
    private int codigoBanco;
    private int codigoAgencia;
    private double cpfCnpj;
    private String descricaoBancos;
    private long numeroDoc;
    private int codigoOrigem;
    private int numeroLote;
    private double valor;
    private Date dataRotina;
    private int qtdReg;
    private String tipoLancamento;

    public Conta99() {
    }

    public int getCodigoConta99() {
        return codigoConta99;
    }

    public void setCodigoConta99(int codigoConta99) {
        this.codigoConta99 = codigoConta99;
    }

    public Date getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(Date dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public int getCodigoHistorico() {
        return codigoHistorico;
    }

    public void setCodigoHistorico(int codigoHistorico) {
        this.codigoHistorico = codigoHistorico;
    }

    public String getDescricaoHistorico() {
        return descricaoHistorico;
    }

    public void setDescricaoHistorico(String descricaoHistorico) {
        this.descricaoHistorico = descricaoHistorico;
    }

    public int getCodigoBanco() {
        return codigoBanco;
    }

    public void setCodigoBanco(int codigoBanco) {
        this.codigoBanco = codigoBanco;
    }

    public int getCodigoAgencia() {
        return codigoAgencia;
    }

    public void setCodigoAgencia(int codigoAgencia) {
        this.codigoAgencia = codigoAgencia;
    }

    public double getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(double cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getDescricaoBancos() {
        return descricaoBancos;
    }

    public void setDescricaoBancos(String descricaoBancos) {
        this.descricaoBancos = descricaoBancos;
    }

    public long getNumeroDoc() {
        return numeroDoc;
    }

    public void setNumeroDoc(long numeroDoc) {
        this.numeroDoc = numeroDoc;
    }    

    public int getCodigoOrigem() {
        return codigoOrigem;
    }

    public void setCodigoOrigem(int codigoOrigem) {
        this.codigoOrigem = codigoOrigem;
    }

    public int getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(int numeroLote) {
        this.numeroLote = numeroLote;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getDataRotina() {
        return dataRotina;
    }

    public void setDataRotina(Date dataRotina) {
        this.dataRotina = dataRotina;
    }

    public int getQtdReg() {
        return qtdReg;
    }

    public void setQtdReg(int qtdReg) {
        this.qtdReg = qtdReg;
    }

    public String getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(String tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
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
        final Conta99 other = (Conta99) obj;
        if (this.codigoConta99 != other.codigoConta99) {
            return false;
        }
        if (this.codigoHistorico != other.codigoHistorico) {
            return false;
        }
        if (this.codigoBanco != other.codigoBanco) {
            return false;
        }
        if (this.codigoAgencia != other.codigoAgencia) {
            return false;
        }
        if (Double.doubleToLongBits(this.cpfCnpj) != Double.doubleToLongBits(other.cpfCnpj)) {
            return false;
        }
        if (Double.doubleToLongBits(this.numeroDoc) != Double.doubleToLongBits(other.numeroDoc)) {
            return false;
        }
        if (this.codigoOrigem != other.codigoOrigem) {
            return false;
        }
        if (this.numeroLote != other.numeroLote) {
            return false;
        }
        if (Double.doubleToLongBits(this.valor) != Double.doubleToLongBits(other.valor)) {
            return false;
        }
        if (this.qtdReg != other.qtdReg) {
            return false;
        }
        if (!Objects.equals(this.descricaoHistorico, other.descricaoHistorico)) {
            return false;
        }
        if (!Objects.equals(this.descricaoBancos, other.descricaoBancos)) {
            return false;
        }
        if (!Objects.equals(this.tipoLancamento, other.tipoLancamento)) {
            return false;
        }
        if (!Objects.equals(this.dataMovimentacao, other.dataMovimentacao)) {
            return false;
        }
        if (!Objects.equals(this.dataRotina, other.dataRotina)) {
            return false;
        }
        return true;
    }
           
    @Override
    public String toString() {
        return "Conta99{" + "codigoConta99=" + codigoConta99 + ", dataMovimentacao=" + 
                dataMovimentacao + ", codigoHistorico=" + codigoHistorico + ", descricaoHistorico=" + 
                descricaoHistorico + ", codigoBanco=" + codigoBanco + ", codigoAgencia=" + 
                codigoAgencia + ", cpfCnpj=" + cpfCnpj + ", descricaoBancos=" + 
                descricaoBancos + ", numeroDoc=" + numeroDoc + ", codigoOrigem=" + 
                codigoOrigem + ", numeroLote=" + numeroLote + ", valor=" + 
                valor + ", dataRotina=" + dataRotina + '}';
    }
    
}
