package entidade;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SolicitacaoLevantamento {

    private int codigo;//1
    private String npj;  //2
    private int variacaoNpj;//3
    private String oficio;//4
    private String dataOficio;//5
    private String historico;//6
    private String valorSolicitacao;//7
    private String enderecoDocumento1;//8
    private String adversoBB;//9
    private String contaJudicialOB;//10
    private String saldo;//11
    private String responsavelSolicitacaoInclusao;//12
    private String dataSolicitacao;//13
    private String responsavelInclusao;//14
    private String dataInclusao;//15
    private String obs;//16
    private String contaJudicial;//17
    private String saldoContabil;//18
    private String enderecoDocumento2;//19
    private String levantador;//20
    private String tipoLevantamento;//21
    private String finalidade;//22
    private String especificacao;//23
    private String beneficiario;//24
    private String cpfBeneficiario;//25
    private String idSituacao;//26
    private String saldoColetadoportal;//27
    private String obsDespacho;//28
    private String obsContabillizacao;//29
    private String obsIncluirParte;//30
    private String incluirParte;//31

    public SolicitacaoLevantamento() {

    }

    public SolicitacaoLevantamento(int codigo, String npj, int variacaoNpj, String oficio, String dataOficio, String valorSolicitacao, String enderecoDocumento1, String saldo, String obs, String enderecoDocumento2, String levantador, String tipoLevantamento, String finalidade, String especificacao, String beneficiario, String cpfBeneficiario, String obsDespacho, String obsContabillizacao, String obsIncluirParte) {
        this.codigo = codigo;
        this.npj = npj;
        this.variacaoNpj = variacaoNpj;
        this.oficio = oficio;
        this.dataOficio = dataOficio;
        this.valorSolicitacao = valorSolicitacao;
        this.enderecoDocumento1 = enderecoDocumento1;
        this.saldo = saldo;
        this.obs = obs;
        this.enderecoDocumento2 = enderecoDocumento2;
        this.levantador = levantador;
        this.tipoLevantamento = tipoLevantamento;
        this.finalidade = finalidade;
        this.especificacao = especificacao;
        this.beneficiario = beneficiario;
        this.cpfBeneficiario = cpfBeneficiario;
        this.obsDespacho = obsDespacho;
        this.obsContabillizacao = obsContabillizacao;
        this.obsIncluirParte = obsIncluirParte;
       
 
    
    
    }
    
    
    
    

    public SolicitacaoLevantamento(int codigo, String npj, int variacaoNpj, String oficio, String dataOficio, String historico, String valorSolicitacao, String enderecoDocumento1, String adversoBB, String contaJudicialOB, String saldo, String responsavelSolicitacaoInclusao, String dataSolicitacao, String responsavelInclusao, String dataInclusao, String obs, String contaJudicial, String saldoContabil, String enderecoDocumento2, String levantador, String tipoLevantamento, String finalidade, String especificacao, String beneficiario, String cpfBeneficiario, String idSituacao, String saldoColetadoportal, String obsDespacho, String obsContabillizacao, String obsIncluirParte, String incluirParte) {
        this.codigo = codigo;
        this.npj = npj;
        this.variacaoNpj = variacaoNpj;
        this.oficio = oficio;
        this.dataOficio = dataOficio;
        this.historico = historico;
        this.valorSolicitacao = valorSolicitacao;
        this.enderecoDocumento1 = enderecoDocumento1;
        this.adversoBB = adversoBB;
        this.contaJudicialOB = contaJudicialOB;
        this.saldo = saldo;
        this.responsavelSolicitacaoInclusao = responsavelSolicitacaoInclusao;
        this.dataSolicitacao = dataSolicitacao;
        this.responsavelInclusao = responsavelInclusao;
        this.dataInclusao = dataInclusao;
        this.obs = obs;
        this.contaJudicial = contaJudicial;
        this.saldoContabil = saldoContabil;
        this.enderecoDocumento2 = enderecoDocumento2;
        this.levantador = levantador;
        this.tipoLevantamento = tipoLevantamento;
        this.finalidade = finalidade;
        this.especificacao = especificacao;
        this.beneficiario = beneficiario;
        this.cpfBeneficiario = cpfBeneficiario;
        this.idSituacao = idSituacao;
        this.saldoColetadoportal = saldoColetadoportal;
        this.obsDespacho = obsDespacho;
        this.obsContabillizacao = obsContabillizacao;
        this.obsIncluirParte = obsIncluirParte;
        this.incluirParte = incluirParte;
    }

    public SolicitacaoLevantamento(String npj, int variacaoNpj, String historico, String oficio, String data, String valorSolicitacao, String enderecoDocumento1, String saldo, String enderecoDocumento2, String levantador, String tipoLevantamento, String finalidade, String especificacao, String beneficiario, String cpfBeneficiario, String incluirParte) {
        this.npj = npj;
        this.variacaoNpj = variacaoNpj;
        this.historico = historico;
        this.oficio = oficio;
        this.dataOficio = data;
        this.valorSolicitacao = valorSolicitacao;
        this.enderecoDocumento1 = enderecoDocumento1;
        this.saldo = saldo;
        this.enderecoDocumento2 = enderecoDocumento2;
        this.levantador = levantador;
        this.tipoLevantamento = tipoLevantamento;
        this.finalidade = finalidade;
        this.especificacao = especificacao;
        this.beneficiario = beneficiario;
        this.cpfBeneficiario = cpfBeneficiario;
        this.incluirParte = incluirParte;
        Calendar c = Calendar.getInstance();
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataSolicitacao = sdf.format(d);

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

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public String getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(String dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getValorSolicitacao() {
        return valorSolicitacao;
    }

    public void setValorSolicitacao(String valorSolicitacao) {
        this.valorSolicitacao = valorSolicitacao;
    }

    public String getEnderecoDocumento1() {
        return enderecoDocumento1;
    }

    public void setEnderecoDocumento1(String enderecoDocumento1) {
        this.enderecoDocumento1 = enderecoDocumento1;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getEnderecoDocumento2() {
        return enderecoDocumento2;
    }

    public void setEnderecoDocumento2(String enderecoDocumento2) {
        this.enderecoDocumento2 = enderecoDocumento2;
    }

    public String getLevantador() {
        return levantador;
    }

    public void setLevantador(String levantador) {
        this.levantador = levantador;
    }

    public String getTipoLevantamento() {
        return tipoLevantamento;
    }

    public void setTipoLevantamento(String tipoLevantamento) {
        this.tipoLevantamento = tipoLevantamento;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade;
    }

    public String getEspecificacao() {
        return especificacao;
    }

    public void setEspecificacao(String especificacao) {
        this.especificacao = especificacao;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getCpfBeneficiario() {
        return cpfBeneficiario;
    }

    public void setCpfBeneficiario(String cpfBeneficiario) {
        this.cpfBeneficiario = cpfBeneficiario;
    }

    public String getIncluirParte() {
        return incluirParte;
    }

    public void setIncluirParte(String incluirParte) {
        this.incluirParte = incluirParte;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDataOficio() {
        return dataOficio;
    }

    public void setDataOficio(String dataOficio) {
        this.dataOficio = dataOficio;
    }

    public String getAdversoBB() {
        return adversoBB;
    }

    public void setAdversoBB(String adversoBB) {
        this.adversoBB = adversoBB;
    }

    public String getContaJudicialOB() {
        return contaJudicialOB;
    }

    public void setContaJudicialOB(String contaJudicialOB) {
        this.contaJudicialOB = contaJudicialOB;
    }

    public String getResponsavelSolicitacaoInclusao() {
        return responsavelSolicitacaoInclusao;
    }

    public void setResponsavelSolicitacaoInclusao(String responsavelSolicitacaoInclusao) {
        this.responsavelSolicitacaoInclusao = responsavelSolicitacaoInclusao;
    }

    public String getResponsavelInclusao() {
        return responsavelInclusao;
    }

    public void setResponsavelInclusao(String responsavelInclusao) {
        this.responsavelInclusao = responsavelInclusao;
    }

    public String getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(String dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getContaJudicial() {
        return contaJudicial;
    }

    public void setContaJudicial(String contaJudicial) {
        this.contaJudicial = contaJudicial;
    }

    public String getSaldoContabil() {
        return saldoContabil;
    }

    public void setSaldoContabil(String saldoContabil) {
        this.saldoContabil = saldoContabil;
    }

    public String getIdSituacao() {
        return idSituacao;
    }

    public void setIdSituacao(String idSituacao) {
        this.idSituacao = idSituacao;
    }

    public String getSaldoColetadoportal() {
        return saldoColetadoportal;
    }

    public void setSaldoColetadoportal(String saldoColetadoportal) {
        this.saldoColetadoportal = saldoColetadoportal;
    }

    public String getObsDespacho() {
        return obsDespacho;
    }

    public void setObsDespacho(String obsDespacho) {
        this.obsDespacho = obsDespacho;
    }

    public String getObsContabillizacao() {
        return obsContabillizacao;
    }

    public void setObsContabillizacao(String obsContabillizacao) {
        this.obsContabillizacao = obsContabillizacao;
    }

    public String getObsIncluirParte() {
        return obsIncluirParte;
    }

    public void setObsIncluirParte(String obsIncluirParte) {
        this.obsIncluirParte = obsIncluirParte;
    }

}
