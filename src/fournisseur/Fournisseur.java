/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fournisseur;

import boncommande.BonCommande;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OBAM
 */
@Entity
@Table(name = "fournisseur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fournisseur.findAll", query = "SELECT f FROM Fournisseur f"),
    @NamedQuery(name = "Fournisseur.findByIdFournisseur", query = "SELECT f FROM Fournisseur f WHERE f.idFournisseur = :idFournisseur"),
    @NamedQuery(name = "Fournisseur.findByDetailsFournisseur", query = "SELECT f FROM Fournisseur f WHERE f.detailsFournisseur = :detailsFournisseur"),
    @NamedQuery(name = "Fournisseur.findByIfuFournisseur", query = "SELECT f FROM Fournisseur f WHERE f.ifuFournisseur = :ifuFournisseur"),
    @NamedQuery(name = "Fournisseur.findByLibFournisseur", query = "SELECT f FROM Fournisseur f WHERE f.libFournisseur = :libFournisseur"),
    @NamedQuery(name = "Fournisseur.findByRcmFournisseur", query = "SELECT f FROM Fournisseur f WHERE f.rcmFournisseur = :rcmFournisseur"),
    @NamedQuery(name = "Fournisseur.findByTelFournisseur", query = "SELECT f FROM Fournisseur f WHERE f.telFournisseur = :telFournisseur")})
public class Fournisseur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFournisseur")
    private Integer idFournisseur;
    @Column(name = "detailsFournisseur")
    private String detailsFournisseur;
    @Column(name = "ifuFournisseur")
    private String ifuFournisseur;
    @Column(name = "libFournisseur")
    private String libFournisseur;
    @Column(name = "rcmFournisseur")
    private String rcmFournisseur;
    @Column(name = "telFournisseur")
    private String telFournisseur;
    
    @OneToMany(mappedBy="fournisseur",cascade=CascadeType.ALL)
     private List<BonCommande> listcommande;

    public Fournisseur() {
    }

    public Fournisseur(int idFournisseur) {
        this.idFournisseur = idFournisseur;
    }
    public void setListCommande( List<BonCommande> list){
        this.listcommande=list;
    }
    public List<BonCommande> getListCommande(){
        return this.listcommande;
    } 

    public Integer getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(Integer idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    public String getDetailsFournisseur() {
        return detailsFournisseur;
    }

    public void setDetailsFournisseur(String detailsFournisseur) {
        this.detailsFournisseur = detailsFournisseur;
    }

    public String getIfuFournisseur() {
        return ifuFournisseur;
    }

    public void setIfuFournisseur(String ifuFournisseur) {
        this.ifuFournisseur = ifuFournisseur;
    }

    public String getLibFournisseur() {
        return libFournisseur;
    }

    public void setLibFournisseur(String libFournisseur) {
        this.libFournisseur = libFournisseur;
    }

    public String getRcmFournisseur() {
        return rcmFournisseur;
    }

    public void setRcmFournisseur(String rcmFournisseur) {
        this.rcmFournisseur = rcmFournisseur;
    }

    public String getTelFournisseur() {
        return telFournisseur;
    }

    public void setTelFournisseur(String telFournisseur) {
        this.telFournisseur = telFournisseur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFournisseur != null ? idFournisseur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fournisseur)) {
            return false;
        }
        Fournisseur other = (Fournisseur) object;
        if ((this.idFournisseur == null && other.idFournisseur != null) || (this.idFournisseur != null && !this.idFournisseur.equals(other.idFournisseur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.libFournisseur;
    }
    
}
