/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entitie;

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
    @NamedQuery(name = "BonCommande.findByDateBonCommande", query = "SELECT b FROM BonCommande b WHERE b.dateBonCommande = :dateBonCommande"),
    @NamedQuery(name = "BonCommande.findByReception", query = "SELECT b FROM BonCommande b WHERE b.reception = :reception"),
    @NamedQuery(name = "BonCommande.findByDatereception", query = "SELECT b FROM BonCommande b WHERE b.datereception = :datereception")})
public class BonCommande implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idBonCommande")
    private int idBonCommande;
    @Column(name = "libBonCommande")
    private String libBonCommande;
    @Column(name = "dateBonCommande")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBonCommande;
    @Column(name = "reception")
    private Boolean reception;
    @Column(name = "datereception")
    @Temporal(TemporalType.DATE)
    private Date datereception;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBonCommande")
    private List<DetailBonCommande> detailBonCommandeList;
    @JoinColumn(name = "idFournisseur", referencedColumnName = "idFournisseur")
    @ManyToOne(optional = false)
    private Fournisseur idFournisseur;

    public BonCommande() {
    }

    public BonCommande(int idBonCommande) {
        this.idBonCommande = idBonCommande;
    }

    public int getIdBonCommande() {
        return idBonCommande;
    }

    public void setIdBonCommande(int idBonCommande) {
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

    public Boolean getReception() {
        return reception;
    }

    public void setReception(Boolean reception) {
        this.reception = reception;
    }

    public Date getDatereception() {
        return datereception;
    }

    public void setDatereception(Date datereception) {
        this.datereception = datereception;
    }

    @XmlTransient
    public List<DetailBonCommande> getDetailBonCommandeList() {
        return detailBonCommandeList;
    }

    public void setDetailBonCommandeList(List<DetailBonCommande> detailBonCommandeList) {
        this.detailBonCommandeList = detailBonCommandeList;
    }

    public Fournisseur getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(Fournisseur idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

   
    
}
