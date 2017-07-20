/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author geres
 */
@Entity
@Table(name = "BonCommande")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BonCommande.findAll", query = "SELECT b FROM BonCommande b"),
    @NamedQuery(name = "BonCommande.findByIdBonCommande", query = "SELECT b FROM BonCommande b WHERE b.idBonCommande = :idBonCommande"),
    @NamedQuery(name = "BonCommande.findByLibBonCommande", query = "SELECT b FROM BonCommande b WHERE b.libBonCommande = :libBonCommande"),
    @NamedQuery(name = "BonCommande.findByDateBonCommande", query = "SELECT b FROM BonCommande b WHERE b.dateBonCommande = :dateBonCommande")})
public class BonCommande implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idBonCommande")
    private Integer idBonCommande;
    @Column(name = "libBonCommande")
    private String libBonCommande;
    @Column(name = "dateBonCommande")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBonCommande;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bonCommandeidBonCommande")
    private List<DetailBonCommande> detailBonCommandeList;
    @JoinColumn(name = "idUtilisateur", referencedColumnName = "idUtilisateur")
    @ManyToOne(optional = false)
    private Utilisateur idUtilisateur;
    @JoinColumn(name = "Fournisseur_idFournisseur", referencedColumnName = "idFournisseur")
    @ManyToOne(optional = false)
    private Fournisseur fournisseuridFournisseur;

    public BonCommande() {
    }

    public BonCommande(Integer idBonCommande) {
        this.idBonCommande = idBonCommande;
    }

    public Integer getIdBonCommande() {
        return idBonCommande;
    }

    public void setIdBonCommande(Integer idBonCommande) {
        this.idBonCommande = idBonCommande;
    }

    public String getLibBonCommande() {
        return libBonCommande;
    }

    public void setLibBonCommande(String libBonCommande) {
        this.libBonCommande = libBonCommande;
    }

    public Date getDateBonCommande() {
        return dateBonCommande;
    }

    public void setDateBonCommande(Date dateBonCommande) {
        this.dateBonCommande = dateBonCommande;
    }

    @XmlTransient
    public List<DetailBonCommande> getDetailBonCommandeList() {
        return detailBonCommandeList;
    }

    public void setDetailBonCommandeList(List<DetailBonCommande> detailBonCommandeList) {
        this.detailBonCommandeList = detailBonCommandeList;
    }

    public Utilisateur getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Utilisateur idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Fournisseur getFournisseuridFournisseur() {
        return fournisseuridFournisseur;
    }

    public void setFournisseuridFournisseur(Fournisseur fournisseuridFournisseur) {
        this.fournisseuridFournisseur = fournisseuridFournisseur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBonCommande != null ? idBonCommande.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BonCommande)) {
            return false;
        }
        BonCommande other = (BonCommande) object;
        if ((this.idBonCommande == null && other.idBonCommande != null) || (this.idBonCommande != null && !this.idBonCommande.equals(other.idBonCommande))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.BonCommande[ idBonCommande=" + idBonCommande + " ]";
    }
    
}
