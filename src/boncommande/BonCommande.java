/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boncommande;

import detailboncommande.DetailBonCommande;
import fournisseur.Fournisseur;
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

/**
 *
 * @author OBAM
 */
@Entity
@Table(name = "boncommande")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Boncommande.findAll", query = "SELECT b FROM BonCommande b"),
    @NamedQuery(name = "Boncommande.bonParPeriode", query =  "SELECT b from BonCommande b where b.dateBonCommande BETWEEN :dateDebut and :dateFin order by b.dateBonCommande desc,b.libBonCommande asc"),
  
    @NamedQuery(name = "Boncommande.findByDateBonCommande", query = "SELECT b FROM BonCommande b WHERE b.dateBonCommande = :dateBonCommande"),
    @NamedQuery(name = "Boncommande.findByDatereception", query = "SELECT b FROM BonCommande b WHERE b.datereception = :datereception"),
    @NamedQuery(name = "Boncommande.findByLibBonCommande", query = "SELECT b FROM BonCommande b WHERE b.libBonCommande = :libBonCommande"),
    @NamedQuery(name = "Boncommande.findByReception", query = "SELECT b FROM BonCommande b WHERE b.reception = :reception")})
public class BonCommande implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idBonCommande")
    private int idBonCommande;
    @Column(name = "dateBonCommande")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBonCommande;
    @Column(name = "datereception")
    @Temporal(TemporalType.DATE)
    private Date datereception;
    @Column(name = "libBonCommande")
    private String libBonCommande;
    @Column(name = "reception")
    private Boolean reception;
      @Column(name = "montant")
    private int montant;

    @ManyToOne
      @JoinColumn(name="idFournisseur")
         private Fournisseur fournisseur=null;
    
    @OneToMany(mappedBy="boncommande",cascade=CascadeType.ALL)
         private List<DetailBonCommande> detail=null;
    
        
    public BonCommande() {
    }

    public BonCommande(int idBonCommande) {
        this.idBonCommande = idBonCommande;
    }
    public void setDetailBonCommande(List<DetailBonCommande> d){
        this.detail=d;
    }
    public List<DetailBonCommande> getDetailBonCommande(){
        return this.detail;
    }
    
    public void setFournisseur(Fournisseur f){
        this.fournisseur=f;
    }
    public Fournisseur getFournisseur(){
        return this.fournisseur;
    }
    public int getIdBonCommande() {
        return idBonCommande;
    }
    public void setIdBonCommande(Integer idBonCommande) {
        this.idBonCommande = idBonCommande;
    }

    public Date getDateBonCommande() {
        return dateBonCommande;
    }

    public void setDateBonCommande(Date dateBonCommande) {
        this.dateBonCommande = dateBonCommande;
    }

    public Date getDatereception() {
        return datereception;
    }

    public void setDatereception(Date datereception) {
        this.datereception = datereception;
    }

    public String getLibBonCommande() {
        return libBonCommande;
    }

    public void setLibBonCommande(String libBonCommande) {
        this.libBonCommande = libBonCommande;
    }

    public Boolean getReception() {
        return reception;
    }

    public void setReception(Boolean reception) {
        this.reception = reception;
    }
    public void setMontant(int montant){
       this.montant=montant;
    }
    public int getMontant(){
        return this.montant;
    }
    
}
