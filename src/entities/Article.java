/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

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
 * @author geres
 */
@Entity
@Table(name = "Article")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Article.findAll", query = "SELECT a FROM Article a"),
    @NamedQuery(name = "Article.findByIdArticle", query = "SELECT a FROM Article a WHERE a.idArticle = :idArticle"),
    @NamedQuery(name = "Article.findByLibArticle", query = "SELECT a FROM Article a WHERE a.libArticle = :libArticle"),
    @NamedQuery(name = "Article.findByPrixArticle", query = "SELECT a FROM Article a WHERE a.prixArticle = :prixArticle"),
    @NamedQuery(name = "Article.findByCoutArticle", query = "SELECT a FROM Article a WHERE a.coutArticle = :coutArticle")})
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idArticle")
    private Integer idArticle;
    @Column(name = "libArticle")
    private String libArticle;
    @Column(name = "prixArticle")
    private Integer prixArticle;
    @Column(name = "coutArticle")
    private Integer coutArticle;
    @JoinColumn(name = "idCategorie", referencedColumnName = "idCategorie")
    @ManyToOne(optional = false)
    private Categorie idCategorie;
    @JoinColumn(name = "idStock", referencedColumnName = "idStock")
    @ManyToOne(optional = false)
    private Stock idStock;
    @JoinColumn(name = "iddetailFacture", referencedColumnName = "iddetailFacture")
    @ManyToOne(optional = false)
    private DetailFacture iddetailFacture;

    public Article() {
    }

    public Article(Integer idArticle) {
        this.idArticle = idArticle;
    }

    public Integer getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Integer idArticle) {
        this.idArticle = idArticle;
    }

    public String getLibArticle() {
        return libArticle;
    }

    public void setLibArticle(String libArticle) {
        this.libArticle = libArticle;
    }

    public Integer getPrixArticle() {
        return prixArticle;
    }

    public void setPrixArticle(Integer prixArticle) {
        this.prixArticle = prixArticle;
    }

    public Integer getCoutArticle() {
        return coutArticle;
    }

    public void setCoutArticle(Integer coutArticle) {
        this.coutArticle = coutArticle;
    }

    public Categorie getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(Categorie idCategorie) {
        this.idCategorie = idCategorie;
    }

    public Stock getIdStock() {
        return idStock;
    }

    public void setIdStock(Stock idStock) {
        this.idStock = idStock;
    }

    public DetailFacture getIddetailFacture() {
        return iddetailFacture;
    }

    public void setIddetailFacture(DetailFacture iddetailFacture) {
        this.iddetailFacture = iddetailFacture;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArticle != null ? idArticle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Article)) {
            return false;
        }
        Article other = (Article) object;
        if ((this.idArticle == null && other.idArticle != null) || (this.idArticle != null && !this.idArticle.equals(other.idArticle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Article[ idArticle=" + idArticle + " ]";
    }
    
}
