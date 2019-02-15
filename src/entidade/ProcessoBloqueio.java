
package entidade;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author f4281065
 */
public class ProcessoBloqueio {
    
    private int id;
    private String npj;
    private Date dataHora;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNpj() {
        return npj;
    }

    public void setNpj(String npj) {
        this.npj = npj;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final ProcessoBloqueio other = (ProcessoBloqueio) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.npj, other.npj)) {
            return false;
        }
        if (!Objects.equals(this.dataHora, other.dataHora)) {
            return false;
        }
        return true;
    }
    
    
}
