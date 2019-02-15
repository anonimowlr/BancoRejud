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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author f5078775
 */
@Entity
@Table(name = "tb_oficio_upload")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UploadOficio.findAll", query = "SELECT u FROM UploadOficio u")
    , @NamedQuery(name = "UploadOficio.findById", query = "SELECT u FROM UploadOficio u WHERE u.id = :id")
    , @NamedQuery(name = "UploadOficio.findByNomeArquivo", query = "SELECT u FROM UploadOficio u WHERE u.nomeArquivo = :nomeArquivo")
    , @NamedQuery(name = "UploadOficio.findByStatus", query = "SELECT u FROM UploadOficio u WHERE u.status = :status")
    , @NamedQuery(name = "UploadOficio.findByDataEnvio", query = "SELECT u FROM UploadOficio u WHERE u.dataEnvio = :dataEnvio")
    , @NamedQuery(name = "UploadOficio.findByObs", query = "SELECT u FROM UploadOficio u WHERE u.obs = :obs")})
public class UploadOficio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nome_arquivo")
    private String nomeArquivo;
    @Column(name = "status")
    private String status;
    @Column(name = "data_envio")
    @Temporal(TemporalType.DATE)
    private Date dataEnvio;
    @Column(name = "obs")
    private String obs;
    @JoinColumn(name = "cd_tipo_envio", referencedColumnName = "cd_tip_envio")
    @ManyToOne
    private TipoUpload cdTipoEnvio;

    public UploadOficio() {
    }

    public UploadOficio(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public TipoUpload getCdTipoEnvio() {
        return cdTipoEnvio;
    }

    public void setCdTipoEnvio(TipoUpload cdTipoEnvio) {
        this.cdTipoEnvio = cdTipoEnvio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UploadOficio)) {
            return false;
        }
        UploadOficio other = (UploadOficio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojo.UploadOficio[ id=" + id + " ]";
    }
    
}
