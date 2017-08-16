
package controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import autorisation.Autorisation;

/**
 *
 * @author OBAM
 */
@Entity
@Table(name = "controler")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Controler.findAll", query = "SELECT c FROM Controler c"),
    @NamedQuery(name = "Controler.findByNomcontroler", query = "SELECT c FROM Controler c WHERE c.nomcontroler = :nomcontroler"),
    @NamedQuery(name = "Controler.findByDescription", query = "SELECT c FROM Controler c WHERE c.description = :description")})
public class Controler implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nomcontroler")
    private String nomcontroler;
    @Column(name = "description")
    private String description;
    
    @OneToMany(mappedBy="controler",cascade=CascadeType.ALL)
     private List<Autorisation> listAutorisation;
    
    public Controler() {
    }

    public Controler(String nomcontroler) {
        this.nomcontroler = nomcontroler;
    }

    public String getNomcontroler() {
        return nomcontroler;
    }
    
    public void setNomcontroler(String nomcontroler) {
        this.nomcontroler = nomcontroler;
    }
    
    public void setListautorisation(List<Autorisation> listAutorisation){
      this.listAutorisation=listAutorisation;
    }
    public List<Autorisation> getListautorisation(){
        return this.listAutorisation;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nomcontroler != null ? nomcontroler.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Controler)) {
            return false;
        }
        Controler other = (Controler) object;
        if ((this.nomcontroler == null && other.nomcontroler != null) || (this.nomcontroler != null && !this.nomcontroler.equals(other.nomcontroler))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "utilisateur.Controler[ nomcontroler=" + nomcontroler + " ]";
    }
    
}
