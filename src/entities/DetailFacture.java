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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "detailFacture")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetailFacture.findAll", query = "SELECT d FROM DetailFacture d"),
    @NamedQuery(name = "DetailFacture.findByIddetailFacture", query = "SELECT d FROM DetailFacture d WHERE d.iddetailFacture = :iddetailFacture")})
public class DetailFacture implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddetailFacture")
    private Integer iddetailFacture;
    @JoinColumn(name = "idFacture", referencedColumnName = "idFacture")
    @ManyToOne(optional = false)
    private Facture idFacture;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iddetailFacture")
    private List<Article> articleList;

    public DetailFacture() {
    }

    public DetailFacture(Integer iddetailFacture) {
        this.iddetailFacture = iddetailFacture;
    }

    public Integer getIddetailFacture() {
        return iddetailFacture;
    }

    public void setIddetailFacture(Integer iddetailFacture) {
        this.iddetailFacture = iddetailFacture;
    }

    public Facture getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(Facture idFacture) {
        this.idFacture = idFacture;
    }

    @XmlTransient
    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddetailFacture != null ? iddetailFacture.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetailFacture)) {
            return false;
        }
        DetailFacture other = (DetailFacture) object;
        if ((this.iddetailFacture == null && other.iddetailFacture != null) || (this.iddetailFacture != null && !this.iddetailFacture.equals(other.iddetailFacture))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DetailFacture[ iddetailFacture=" + iddetailFacture + " ]";
    }
    
}
