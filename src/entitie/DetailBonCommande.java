/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entitie;

import article.Article;
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
 * @author geres
 */
@Entity
@Table(name = "DetailBonCommande")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetailBonCommande.findAll", query = "SELECT d FROM DetailBonCommande d"),
    @NamedQuery(name = "detailByIdBon", query = "SELECT d FROM DetailBonCommande d,BonCommande b where"),
    @NamedQuery(name = "DetailBonCommande.findByIdBon", query = "SELECT d FROM DetailBonCommande d  WHERE d.idBonCommande = :idBonCommande"),
    @NamedQuery(name = "DetailBonCommande.findByIdDetailBonCommande", query = "SELECT d FROM DetailBonCommande d WHERE d.idDetailBonCommande = :idDetailBonCommande"),
    @NamedQuery(name = "DetailBonCommande.findByPuachat", query = "SELECT d FROM DetailBonCommande d WHERE d.puachat = :puachat"),
    @NamedQuery(name = "DetailBonCommande.findByLibDetailBonCommande", query = "SELECT d FROM DetailBonCommande d WHERE d.libDetailBonCommande = :libDetailBonCommande"),
    @NamedQuery(name = "DetailBonCommande.findByQuantiteDetailBonCommande", query = "SELECT d FROM DetailBonCommande d WHERE d.quantiteDetailBonCommande = :quantiteDetailBonCommande"),
    @NamedQuery(name = "DetailBonCommande.findByDateperemption", query = "SELECT d FROM DetailBonCommande d WHERE d.dateperemption = :dateperemption")})
public class DetailBonCommande implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDetailBonCommande")
    private int idDetailBonCommande;
    @Basic(optional = false)
    @Column(name = "puachat")
    private int puachat;
    @Column(name = "libDetailBonCommande")
    private String libDetailBonCommande;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "quantiteDetailBonCommande")
    private Double quantiteDetailBonCommande;
    @Column(name = "dateperemption")
    @Temporal(TemporalType.DATE)
    private Date dateperemption;
    @JoinColumn(name = "idBonCommande", referencedColumnName = "idBonCommande")
    @ManyToOne(optional = false)
    private BonCommande idBonCommande;
    @JoinColumn(name = "idArticle", referencedColumnName = "idArticle")
    @ManyToOne(optional = false)
    private Article idArticle;

    public DetailBonCommande() {
    }

    public DetailBonCommande(int idDetailBonCommande) {
        this.idDetailBonCommande = idDetailBonCommande;
    }

    public DetailBonCommande(int idDetailBonCommande, int puachat) {
        this.idDetailBonCommande = idDetailBonCommande;
        this.puachat = puachat;
    }

    public int getIdDetailBonCommande() {
        return idDetailBonCommande;
    }

    public void setIdDetailBonCommande(int idDetailBonCommande) {
        this.idDetailBonCommande = idDetailBonCommande;
    }

    public int getPuachat() {
        return puachat;
    }

    public void setPuachat(int puachat) {
        this.puachat = puachat;
    }

    public String getLibDetailBonCommande() {
        return libDetailBonCommande;
    }

    public void setLibDetailBonCommande(String libDetailBonCommande) {
        this.libDetailBonCommande = libDetailBonCommande;
    }

    public Double getQuantiteDetailBonCommande() {
        return quantiteDetailBonCommande;
    }

    public void setQuantiteDetailBonCommande(Double quantiteDetailBonCommande) {
        this.quantiteDetailBonCommande = quantiteDetailBonCommande;
    }

    public Date getDateperemption() {
        return dateperemption;
    }

    public void setDateperemption(Date dateperemption) {
        this.dateperemption = dateperemption;
    }

    public BonCommande getIdBonCommande() {
        return idBonCommande;
    }

    public void setIdBonCommande(BonCommande idBonCommande) {
        this.idBonCommande = idBonCommande;
    }

    public Article getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Article idArticle) {
        this.idArticle = idArticle;
    }
    
}
