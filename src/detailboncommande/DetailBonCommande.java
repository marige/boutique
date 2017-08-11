/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detailboncommande;

import article.Article;
import boncommande.BonCommande;
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

/**
 *
 * @author OBAM
 */
@Entity
@Table(name = "detailboncommande")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Detailboncommande.findAll", query = "SELECT d FROM DetailBonCommande d"),
    @NamedQuery(name = "Detailboncommande.findByIdDetailBonCommande", query = "SELECT d FROM DetailBonCommande d WHERE d.idDetailBonCommande = :idDetailBonCommande"),
    @NamedQuery(name = "Detailboncommande.findByPuachat", query = "SELECT d FROM DetailBonCommande d WHERE d.puachat = :puachat"),
    @NamedQuery(name = "Detailboncommande.findByQuantiteDetailBonCommande", query = "SELECT d FROM DetailBonCommande d WHERE d.quantiteDetailBonCommande = :quantiteDetailBonCommande"),
    @NamedQuery(name = "Detailboncommande.findByIdArticle", query = "SELECT d FROM DetailBonCommande d WHERE d.article.idArticle = :idArticle")})
public class DetailBonCommande implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDetailBonCommande")
    private Integer idDetailBonCommande;
    @Column(name = "puachat")
    private Integer puachat;
    @Column(name = "quantiteDetailBonCommande")
    private Integer quantiteDetailBonCommande;
      
    @ManyToOne
     @JoinColumn(name="idarticle")
     private Article article=null;
    
     @ManyToOne
     @JoinColumn(name="idboncommande")
     private BonCommande boncommande=null;
     
    

    public DetailBonCommande() {
    }

    public DetailBonCommande(Integer idDetailBonCommande) {
        this.idDetailBonCommande = idDetailBonCommande;
    }

    public Integer getIdDetailBonCommande() {
        return idDetailBonCommande;
    }
    public void setArticle(Article a){
        this.article=a;
    }
    public Article getArticle(){
        return this.article;
    }
    public void setBonCommande(BonCommande bc){
        this.boncommande=bc;
    }
    public BonCommande getBonCommande(){
        return this.boncommande;
    }
    
    public void setIdDetailBonCommande(Integer idDetailBonCommande) {
        this.idDetailBonCommande = idDetailBonCommande;
    }

    public Integer getPuachat() {
        return puachat;
    }

    public void setPuachat(Integer puachat) {
        this.puachat = puachat;
    }

    public Integer getQuantiteDetailBonCommande() {
        return quantiteDetailBonCommande;
    }

    public void setQuantiteDetailBonCommande(Integer quantiteDetailBonCommande) {
        this.quantiteDetailBonCommande = quantiteDetailBonCommande;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetailBonCommande != null ? idDetailBonCommande.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetailBonCommande)) {
            return false;
        }
        DetailBonCommande other = (DetailBonCommande) object;
        if ((this.idDetailBonCommande == null && other.idDetailBonCommande != null) || (this.idDetailBonCommande != null && !this.idDetailBonCommande.equals(other.idDetailBonCommande))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "boncommande.Detailboncommande[ idDetailBonCommande=" + idDetailBonCommande + " ]";
    }
    
}
