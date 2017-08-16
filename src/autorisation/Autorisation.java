/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autorisation;

import controller.Controler;
import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import utilisateur.Utilisateur;

/**
 *
 * @author OBAM
 */
@Entity
@Table(name = "autorisation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Autorisation.findAll", query = "SELECT a FROM Autorisation a"),})
public class Autorisation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idautorisation")
    private Integer idautorisation;
  /*  @Basic(optional = false)
    @Column(name = "idutilisateur")
    private int idutilisateur;
    @Basic(optional = false)
    @Column(name = "nomcontroler")
    private String nomcontroler;*/
     
    @ManyToOne
    @JoinColumn(name="nomcontroler")
     private Controler controler;
    
    @ManyToOne
    @JoinColumn(name="idutilisateur")
     private Utilisateur utilisateur;
     
    public void setUtilisateur(Utilisateur u){
        this.utilisateur=u;
    }
    
    public Utilisateur getUtilisateur(){
        return this.utilisateur;
    }
    public void setControler(Controler c){
        this.controler=c;
    }
    public Controler getControler(){
        return this.controler;
    }
    public Autorisation() {
    }

    public Autorisation(Integer idautorisation) {
        this.idautorisation = idautorisation;
    }
  
    public Integer getIdautorisation() {
        return idautorisation;
    }

    public void setIdautorisation(Integer idautorisation) {
        this.idautorisation = idautorisation;
    }
/*
    public int getIdutilisateur() {
        return idutilisateur;
    }

    public void setIdutilisateur(int idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

    public String getNomcontroler() {
        return nomcontroler;
    }

    public void setNomcontroler(String nomcontroler) {
        this.nomcontroler = nomcontroler;
    }
*/
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idautorisation != null ? idautorisation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Autorisation)) {
            return false;
        }
        Autorisation other = (Autorisation) object;
        if ((this.idautorisation == null && other.idautorisation != null) || (this.idautorisation != null && !this.idautorisation.equals(other.idautorisation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "utilisateur.Autorisation[ idautorisation=" + idautorisation + " ]";
    }
    
}
