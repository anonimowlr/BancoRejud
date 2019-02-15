/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author suporte
 */
@Entity
@Table(name = "tb_tarifa")
@NamedQueries({
    @NamedQuery(name = "SolicitacaoTarifa.findAll", query = "SELECT s FROM SolicitacaoTarifa s")})
public class SolicitacaoTarifa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "NPJ")
    private String npj;
    @Column(name = "VARIACAO_NPJ")
    private Integer variacaoNpj;
    @Basic(optional = false)
    @Column(name = "VALOR_TARIFA")
    private double valorTarifa;
    @Basic(optional = false)
    @Column(name = "BANCO_BENEFICIARIO")
    private String bancoBeneficiario;
    @Column(name = "CNPJ_BENEFICIARIO")
    private String cnpjBeneficiario;
    @Column(name = "CONTA_DEPOSITARIA")
    private String contaDepositaria;
    @Column(name = "DEPENDENCIA_OPERADORA")
    private Integer dependenciaOperadora;
    @Column(name = "DEPENDENCIA_SOLICITANTE")
    private Integer dependenciaSolicitante;
    @Column(name = "DEPENDENCIA_RESPONSAVEL")
    private Integer dependenciaResponsavel;
    @Column(name = "ENDERECO_DOCUMENTO")
    private String enderecoDocumento;
    @Column(name = "NUMERO_EGT")
    private String numeroEgt;
    @Column(name = "EVENTO_EGT")
    private String eventoEgt;
    @Column(name = "FUNCIONARIO_RESPONSAVEL")
    private String funcionarioResponsavel;
    @Column(name = "DATA_SOLICITACAO")
    @Temporal(TemporalType.DATE)
    private Date dataSolicitacao;
    @Column(name = "FUNCIONARIO_RESP_INCLUSAO")
    private String funcionarioRespInclusao;
    @Column(name = "OBS_INCLUIR_BANCO_BENEFICIARIO")
    private String obsIncluirBancoBeneficiario;
    @Column(name = "OBS_INCLUIR_TARIFA_PORTAL")
    private String obsIncluirTarifaPortal;
    @Column(name = "OBS_ANALISE_LIBERACAO")
    private String obsAnaliseLiberacao;
    @Column(name = "OBS_EFETIVAR_TARIFA")
    private String obsEfetivarTarifa;
    @Column(name = "OBS_DESPACHO_TARIFA")
    private String obsDespachoTarifa;
    @Column(name = "ID_SITUACAO")
    private String idSituacao;
    @Basic(optional = false)
    @Column(name = "DEPENDENCIA_CONTABIL")
    private int dependenciaContabil;
    @Basic(optional = false)
    @Column(name = "NUMERO_DOCUMENTO")
    private String numeroDocumento;
    @Column(name = "DATA_DOCUMENTO")
    @Temporal(TemporalType.DATE)
    private Date dataDocumento;
    @Column(name = "PROTOCOLO_GSV")
    private String protocoloGsv;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR_CREDITO")
    private Double valorCredito;
    @Column(name = "DATA_CREDITO")
    @Temporal(TemporalType.DATE)
    private Date dataCredito;
    @Column(name = "DATA_INCLUSAO")
    @Temporal(TemporalType.DATE)
    private Date dataInclusao;
    @Column(name = "OBS_PARTIDA_EGT")
    private String obsPartidaEgt;
    @Column(name = "OBS_DESPACHAR_EGT")
    private String obsDespacharEgt;
    @Column(name = "DATA_DESPACHO_PORTAL")
    @Temporal(TemporalType.DATE)
    private String dataDespachoPortal;
    @Column(name = "ID_SITUACAO_EGT")
    private String idSituacaoEgt;

    public SolicitacaoTarifa() {
    }

    public SolicitacaoTarifa(Integer codigo) {
        this.codigo = codigo;
    }

    public SolicitacaoTarifa(Integer codigo, String npj, double valorTarifa, String bancoBeneficiario, int dependenciaContabil, String numeroDocumento) {
        this.codigo = codigo;
        this.npj = npj;
        this.valorTarifa = valorTarifa;
        this.bancoBeneficiario = bancoBeneficiario;
        this.dependenciaContabil = dependenciaContabil;
        this.numeroDocumento = numeroDocumento;
    }

     public SolicitacaoTarifa(Integer codigo, String npj, int variacaoNpj, double valorTarifa, String bancoBeneficiario, String enderecoDocumento, String funcionarioResponsavel, Date dataSolicitacao, int dependenciaContabil, String numeroDocumento, Date dataDocumento) {
        this.codigo = codigo;
        this.npj = npj;
        this.variacaoNpj = variacaoNpj;
        this.valorTarifa = valorTarifa;
        this.bancoBeneficiario = bancoBeneficiario;
        this.enderecoDocumento = enderecoDocumento;
        this.funcionarioResponsavel = funcionarioResponsavel;
        this.dataSolicitacao = dataSolicitacao;
        this.dependenciaContabil = dependenciaContabil;
        this.numeroDocumento = numeroDocumento;
        this.dataDocumento = dataDocumento;
    }

    @SuppressWarnings("SillyAssignment")
    public SolicitacaoTarifa( String npj, int variacaoNpj, double valorTarifa, String bancoBeneficiario, String enderecoDocumento, String funcionarioResponsavel, Date dataSolicitacao, int dependenciaContabil, String numeroDocumento, Date dataDocumento, String protocoloGsv, Double valorCredito, Date dataCredito) {
        this.codigo = codigo;
        this.npj = npj;
        this.variacaoNpj = variacaoNpj;
        this.valorTarifa = valorTarifa;
        this.bancoBeneficiario = bancoBeneficiario;
        this.dependenciaOperadora = dependenciaOperadora;
        this.cnpjBeneficiario = cnpjBeneficiario;
        this.contaDepositaria = contaDepositaria;
        this.dependenciaSolicitante = dependenciaSolicitante;
        this.dependenciaResponsavel = dependenciaResponsavel;
        this.enderecoDocumento = enderecoDocumento;
        this.numeroEgt = numeroEgt;
        this.eventoEgt = eventoEgt;
        this.funcionarioResponsavel = funcionarioResponsavel;
        this.dataSolicitacao = dataSolicitacao;
        this.funcionarioRespInclusao = funcionarioRespInclusao;
        this.obsIncluirBancoBeneficiario = obsIncluirBancoBeneficiario;
        this.obsIncluirTarifaPortal = obsIncluirTarifaPortal;
        this.obsAnaliseLiberacao = obsAnaliseLiberacao;
        this.obsEfetivarTarifa = obsEfetivarTarifa;
        this.obsDespachoTarifa = obsDespachoTarifa;
        this.idSituacao = idSituacao;
        this.dependenciaContabil = dependenciaContabil;
        this.numeroDocumento = numeroDocumento;
        this.dataDocumento = dataDocumento;
        this.protocoloGsv = protocoloGsv;
        this.valorCredito = valorCredito;
        this.dataCredito = dataCredito;
        this.dataInclusao = dataInclusao;
    }

    
    
    
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNpj() {
        return npj;
    }

    public void setNpj(String npj) {
        this.npj = npj;
    }

    public Integer getVariacaoNpj() {
        return variacaoNpj;
    }

    public void setVariacaoNpj(Integer variacaoNpj) {
        this.variacaoNpj = variacaoNpj;
    }

    public double getValorTarifa() {
        return valorTarifa;
    }

    public void setValorTarifa(double valorTarifa) {
        this.valorTarifa = valorTarifa;
    }

    public String getBancoBeneficiario() {
        return bancoBeneficiario;
    }

    public void setBancoBeneficiario(String bancoBeneficiario) {
        this.bancoBeneficiario = bancoBeneficiario;
    }

    public String getCnpjBeneficiario() {
        return cnpjBeneficiario;
    }

    public void setCnpjBeneficiario(String cnpjBeneficiario) {
        this.cnpjBeneficiario = cnpjBeneficiario;
    }

    public String getContaDepositaria() {
        return contaDepositaria;
    }

    public void setContaDepositaria(String contaDepositaria) {
        this.contaDepositaria = contaDepositaria;
    }

    public Integer getDependenciaOperadora() {
        return dependenciaOperadora;
    }

    public void setDependenciaOperadora(Integer dependenciaOperadora) {
        this.dependenciaOperadora = dependenciaOperadora;
    }

    public Integer getDependenciaSolicitante() {
        return dependenciaSolicitante;
    }

    public void setDependenciaSolicitante(Integer dependenciaSolicitante) {
        this.dependenciaSolicitante = dependenciaSolicitante;
    }

    public Integer getDependenciaResponsavel() {
        return dependenciaResponsavel;
    }

    public void setDependenciaResponsavel(Integer dependenciaResponsavel) {
        this.dependenciaResponsavel = dependenciaResponsavel;
    }

    public String getEnderecoDocumento() {
        return enderecoDocumento;
    }

    public void setEnderecoDocumento(String enderecoDocumento) {
        this.enderecoDocumento = enderecoDocumento;
    }

    public String getNumeroEgt() {
        return numeroEgt;
    }

    public void setNumeroEgt(String numeroEgt) {
        this.numeroEgt = numeroEgt;
    }

    public String getEventoEgt() {
        return eventoEgt;
    }

    public void setEventoEgt(String eventoEgt) {
        this.eventoEgt = eventoEgt;
    }

    public String getFuncionarioResponsavel() {
        return funcionarioResponsavel;
    }

    public void setFuncionarioResponsavel(String funcionarioResponsavel) {
        this.funcionarioResponsavel = funcionarioResponsavel;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getFuncionarioRespInclusao() {
        return funcionarioRespInclusao;
    }

    public void setFuncionarioRespInclusao(String funcionarioRespInclusao) {
        this.funcionarioRespInclusao = funcionarioRespInclusao;
    }

    public String getObsIncluirBancoBeneficiario() {
        return obsIncluirBancoBeneficiario;
    }

    public void setObsIncluirBancoBeneficiario(String obsIncluirBancoBeneficiario) {
        this.obsIncluirBancoBeneficiario = obsIncluirBancoBeneficiario;
    }

    public String getObsIncluirTarifaPortal() {
        return obsIncluirTarifaPortal;
    }

    public void setObsIncluirTarifaPortal(String obsIncluirTarifaPortal) {
        this.obsIncluirTarifaPortal = obsIncluirTarifaPortal;
    }

    public String getObsAnaliseLiberacao() {
        return obsAnaliseLiberacao;
    }

    public void setObsAnaliseLiberacao(String obsAnaliseLiberacao) {
        this.obsAnaliseLiberacao = obsAnaliseLiberacao;
    }

    public String getObsEfetivarTarifa() {
        return obsEfetivarTarifa;
    }

    public void setObsEfetivarTarifa(String obsEfetivarTarifa) {
        this.obsEfetivarTarifa = obsEfetivarTarifa;
    }

    public String getObsDespachoTarifa() {
        return obsDespachoTarifa;
    }

    public void setObsDespachoTarifa(String obsDespachoTarifa) {
        this.obsDespachoTarifa = obsDespachoTarifa;
    }

    public String getIdSituacao() {
        return idSituacao;
    }

    public void setIdSituacao(String idSituacao) {
        this.idSituacao = idSituacao;
    }

    public int getDependenciaContabil() {
        return dependenciaContabil;
    }

    public void setDependenciaContabil(int dependenciaContabil) {
        this.dependenciaContabil = dependenciaContabil;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Date getDataDocumento() {
        return dataDocumento;
    }

    public void setDataDocumento(Date dataDocumento) {
        this.dataDocumento = dataDocumento;
    }

    public String getProtocoloGsv() {
        return protocoloGsv;
    }

    public void setProtocoloGsv(String protocoloGsv) {
        this.protocoloGsv = protocoloGsv;
    }

    public Double getValorCredito() {
        return valorCredito;
    }

    public void setValorCredito(Double valorCredito) {
        this.valorCredito = valorCredito;
    }

    public Date getDataCredito() {
        return dataCredito;
    }

    public void setDataCredito(Date dataCredito) {
        this.dataCredito = dataCredito;
    }

    public Date getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public String getObsPartidaEgt() {
        return obsPartidaEgt;
    }

    public void setObsPartidaEgt(String obsPartidaEgt) {
        this.obsPartidaEgt = obsPartidaEgt;
    }

    public String getObsDespacharEgt() {
        return obsDespacharEgt;
    }

    public void setObsDespacharEgt(String obsDespacharEgt) {
        this.obsDespacharEgt = obsDespacharEgt;
    }

    public String getDataDespachoPortal() {
        return dataDespachoPortal;
    }

    public void setDataDespachoPortal(String dataDespachoPortal) {
        this.dataDespachoPortal = dataDespachoPortal;
    }

    public String getIdSituacaoEgt() {
        return idSituacaoEgt;
    }

    public void setIdSituacaoEgt(String idSituacaoEgt) {
        this.idSituacaoEgt = idSituacaoEgt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitacaoTarifa)) {
            return false;
        }
        SolicitacaoTarifa other = (SolicitacaoTarifa) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojo.SolicitacaoTarifa[ codigo=" + codigo + " ]";
    }
    
}
