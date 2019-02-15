package entidade;

public class Extrato {

    private int codigo;
    private String npj;
    private int variacaoNpj;
    private String contaControle;
    private String saldoContaControle;
    private String bancoDepositario;
    private String agenciaDdepositaria;
    private String contaDepositaria;
    private int variacaoContaDepositaria;
    private String idDeposito;
    private String saldoContaDepositaria;
    private String autor;
    private String reu;
    private String processo;
    private String obs;
    private String funcionarioResponsavel;
    private String modalidadeDeposito;

    public Extrato() {
    }

    public Extrato(String npj, String agenciaDdepositaria, String contaDepositaria, int variacaoContaDepositaria, String funcionarioResponsavel, String modalidadeDeposito) {

        this.npj = npj;

        this.agenciaDdepositaria = agenciaDdepositaria;
        this.contaDepositaria = contaDepositaria;
        this.variacaoContaDepositaria = variacaoContaDepositaria;

        this.funcionarioResponsavel = funcionarioResponsavel;
        this.modalidadeDeposito = modalidadeDeposito;
    }

    public Extrato(int codigo, String npj, int variacaoNpj, String contaControle, String saldoContaControle, String bancoDepositario, String agenciaDdepositaria, String contaDepositaria, int variacaoContaDepositaria, String idDeposito, String saldoContaDepositaria, String autor, String reu, String processo, String obs, String funcionarioResponsavel, String modalidadeDeposito) {
        this.codigo = codigo;
        this.npj = npj;
        this.variacaoNpj = variacaoNpj;
        this.contaControle = contaControle;
        this.saldoContaControle = saldoContaControle;
        this.bancoDepositario = bancoDepositario;
        this.agenciaDdepositaria = agenciaDdepositaria;
        this.contaDepositaria = contaDepositaria;
        this.variacaoContaDepositaria = variacaoContaDepositaria;
        this.idDeposito = idDeposito;
        this.saldoContaDepositaria = saldoContaDepositaria;
        this.autor = autor;
        this.reu = reu;
        this.processo = processo;
        this.obs = obs;
        this.funcionarioResponsavel = funcionarioResponsavel;
        this.modalidadeDeposito = modalidadeDeposito;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNpj() {
        return npj;
    }

    public void setNpj(String npj) {
        this.npj = npj;
    }

    public int getVariacaoNpj() {
        return variacaoNpj;
    }

    public void setVariacaoNpj(int variacaoNpj) {
        this.variacaoNpj = variacaoNpj;
    }

    public String getContaControle() {
        return contaControle;
    }

    public void setContaControle(String contaControle) {
        this.contaControle = contaControle;
    }

    public String getSaldoContaControle() {
        return saldoContaControle;
    }

    public void setSaldoContaControle(String saldoContaControle) {
        this.saldoContaControle = saldoContaControle;
    }

    public String getBancoDepositario() {
        return bancoDepositario;
    }

    public void setBancoDepositario(String bancoDepositario) {
        this.bancoDepositario = bancoDepositario;
    }

    public String getAgenciaDdepositaria() {
        return agenciaDdepositaria;
    }

    public void setAgenciaDdepositaria(String agenciaDdepositaria) {
        this.agenciaDdepositaria = agenciaDdepositaria;
    }

    public String getContaDepositaria() {
        return contaDepositaria;
    }

    public void setContaDepositaria(String contaDepositaria) {
        this.contaDepositaria = contaDepositaria;
    }

    public int getVariacaoContaDepositaria() {
        return variacaoContaDepositaria;
    }

    public void setVariacaoContaDepositaria(int variacaoContaDepositaria) {
        this.variacaoContaDepositaria = variacaoContaDepositaria;
    }

    public String getIdDeposito() {
        return idDeposito;
    }

    public void setIdDeposito(String idDeposito) {
        this.idDeposito = idDeposito;
    }

    public String getSaldoContaDepositaria() {
        return saldoContaDepositaria;
    }

    public void setSaldoContaDepositaria(String saldoContaDepositaria) {
        this.saldoContaDepositaria = saldoContaDepositaria;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getReu() {
        return reu;
    }

    public void setReu(String reu) {
        this.reu = reu;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getFuncionarioResponsavel() {
        return funcionarioResponsavel;
    }

    public void setFuncionarioResponsavel(String funcionarioResponsavel) {
        this.funcionarioResponsavel = funcionarioResponsavel;
    }

    public String getModalidadeDeposito() {
        return modalidadeDeposito;
    }

    public void setModalidadeDeposito(String modalidadeDeposito) {
        this.modalidadeDeposito = modalidadeDeposito;
    }

}
