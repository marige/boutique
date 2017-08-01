/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entitie;

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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author geres
 */
@Entity
@Table(name = "Fournisseur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fournisseur.findAll", query = "SELECT f FROM Fournisseur f"),
    @NamedQuery(name = "Fournisseur.findByIdFournisseur", query = "SELECT f FROM Fournisseur f WHERE f.idFournisseur = :idFournisseur"),
    @NamedQuery(name = "Fournisseur.findByLibFournisseur", query = "SELECT f FROM Fournisseur f WHERE f.libFournisseur = :libFournisseur"),
    @NamedQuery(name = "Fournisseur.findByTelFournisseur", query = "SELECT f FROM Fournisseur f WHERE f.telFournisseur = :telFournisseur"),
    @NamedQuery(name = "Fournisseur.findByIfuFournisseur", query = "SELECT f FROM Fournisseur f WHERE f.ifuFournisseur = :ifuFournisseur"),
    @NamedQuery(name = "Fournisseur.findByRcmFournisseur", query = "SELECT f FROM Fournisseur f WHERE f.rcmFournisseur = :rcmFournisseur"),
    @NamedQuery(name = "Fournisseur.findByDetailsFournisseur", query = "SELECT f FROM Fournisseur f WHERE f.detailsFournisseur = :detailsFournisseur")})
public class Fournisseur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFournisseur")
    private Integer idFournisseur;
    @Column(name = "libFournisseur")
    private String libFournisseur;
    @Column(name = "telFournisseur")
    private String telFournisseur;
    @Column(name = "ifuFournisseur")
    private String ifuFournisseur;
    @Column(name = "rcmFournisseur")
    private String rcmFournisseur;
    @Column(name = "detailsFournisseur")
    private String detailsFournisseur;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFournisseur")
    private List<BonCommande> bonCommandeList;

    public Fournisseur() {
    }

    public Fournisseur(Integer idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    public Integer getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(Integer idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    public String getLibFournisseur() {
        return libFournisseur;
    }

    public void setLibFournisseur(String libFournisseur) {
        this.libFournisseur = libFournisseur;
    }

    public String getTelFournisseur() {
        return telFournisseur;
    }

    public void setTelFournisseur(String telFournisseur) {
        this.telFournisseur = telFournisseur;
    }

    public String getIfuFournisseur() {
        return ifuFournisseur;
    }

    public void setIfuFournisseur(String ifuFournisseur) {
        this.ifuFournisseur = ifuFournisseur;
    }

    public String getRcmFournisseur() {
        return rcmFournisseur;
    }

    public void setRcmFournisseur(String rcmFournisseur) {
        this.rcmFournisseur = rcmFournisseur;
    }

    public String getDetailsFournisseur() {
        return detailsFournisseur;
    }

    public void setDetailsFournisseur(String detailsFournisseur) {
        this.detailsFournisseur = detailsFournisseur;
    }

    @XmlTransient
    public List<BonCommande> getBonCommandeList() {
        return bonCommandeList;
    }

    public void setBonCommandeList(List<BonCommande> bonCommandeList) {
        this.bonCommandeList = bonCommandeList;
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
        return  libFournisseur ;
    }
    
}
