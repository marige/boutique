/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

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
@Table(name = "Utilisateur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Utilisateur.findAll", query = "SELECT u FROM Utilisateur u"),
    @NamedQuery(name = "Utilisateur.findByIdUtilisateur", query = "SELECT u FROM Utilisateur u WHERE u.idUtilisateur = :idUtilisateur"),
    @NamedQuery(name = "Utilisateur.findByLibUtilisateur", query = "SELECT u FROM Utilisateur u WHERE u.libUtilisateur = :libUtilisateur"),
    @NamedQuery(name = "Utilisateur.findByIfuUtilisateur", query = "SELECT u FROM Utilisateur u WHERE u.ifuUtilisateur = :ifuUtilisateur"),
    @NamedQuery(name = "Utilisateur.findByRcmUtilisateur", query = "SELECT u FROM Utilisateur u WHERE u.rcmUtilisateur = :rcmUtilisateur"),
    @NamedQuery(name = "Utilisateur.findByDetailsUtilisateur", query = "SELECT u FROM Utilisateur u WHERE u.detailsUtilisateur = :detailsUtilisateur")})
public class Utilisateur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUtilisateur")
    private Integer idUtilisateur;
    @Column(name = "libUtilisateur")
    private String libUtilisateur;
    @Column(name = "ifuUtilisateur")
    private String ifuUtilisateur;
    @Column(name = "rcmUtilisateur")
    private String rcmUtilisateur;
    @Column(name = "detailsUtilisateur")
    private String detailsUtilisateur;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUtilisateur")
    private List<BonCommande> bonCommandeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUtilisateur")
    private List<Facture> factureList;

    public Utilisateur() {
    }

    public Utilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getLibUtilisateur() {
        return libUtilisateur;
    }

    public void setLibUtilisateur(String libUtilisateur) {
        this.libUtilisateur = libUtilisateur;
    }

    public String getIfuUtilisateur() {
        return ifuUtilisateur;
    }

    public void setIfuUtilisateur(String ifuUtilisateur) {
        this.ifuUtilisateur = ifuUtilisateur;
    }

    public String getRcmUtilisateur() {
        return rcmUtilisateur;
    }

    public void setRcmUtilisateur(String rcmUtilisateur) {
        this.rcmUtilisateur = rcmUtilisateur;
    }

    public String getDetailsUtilisateur() {
        return detailsUtilisateur;
    }

    public void setDetailsUtilisateur(String detailsUtilisateur) {
        this.detailsUtilisateur = detailsUtilisateur;
    }

    @XmlTransient
    public List<BonCommande> getBonCommandeList() {
        return bonCommandeList;
    }

    public void setBonCommandeList(List<BonCommande> bonCommandeList) {
        this.bonCommandeList = bonCommandeList;
    }

    @XmlTransient
    public List<Facture> getFactureList() {
        return factureList;
    }

    public void setFactureList(List<Facture> factureList) {
        this.factureList = factureList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUtilisateur != null ? idUtilisateur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Utilisateur)) {
            return false;
        }
        Utilisateur other = (Utilisateur) object;
        if ((this.idUtilisateur == null && other.idUtilisateur != null) || (this.idUtilisateur != null && !this.idUtilisateur.equals(other.idUtilisateur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Utilisateur[ idUtilisateur=" + idUtilisateur + " ]";
    }
    
}
