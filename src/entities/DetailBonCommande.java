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
@Table(name = "DetailBonCommande")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetailBonCommande.findAll", query = "SELECT d FROM DetailBonCommande d"),
    @NamedQuery(name = "DetailBonCommande.findByIdDetailBonCommande", query = "SELECT d FROM DetailBonCommande d WHERE d.idDetailBonCommande = :idDetailBonCommande"),
    @NamedQuery(name = "DetailBonCommande.findByCoutDetailBonCommande", query = "SELECT d FROM DetailBonCommande d WHERE d.coutDetailBonCommande = :coutDetailBonCommande"),
    @NamedQuery(name = "DetailBonCommande.findByLibDetailBonCommande", query = "SELECT d FROM DetailBonCommande d WHERE d.libDetailBonCommande = :libDetailBonCommande")})
public class DetailBonCommande implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDetailBonCommande")
    private Integer idDetailBonCommande;
    @Column(name = "coutDetailBonCommande")
    private Integer coutDetailBonCommande;
    @Column(name = "libDetailBonCommande")
    private String libDetailBonCommande;
    @JoinColumn(name = "BonCommande_idBonCommande", referencedColumnName = "idBonCommande")
    @ManyToOne(optional = false)
    private BonCommande bonCommandeidBonCommande;

    public DetailBonCommande() {
    }

    public DetailBonCommande(Integer idDetailBonCommande) {
        this.idDetailBonCommande = idDetailBonCommande;
    }

    public Integer getIdDetailBonCommande() {
        return idDetailBonCommande;
    }

    public void setIdDetailBonCommande(Integer idDetailBonCommande) {
        this.idDetailBonCommande = idDetailBonCommande;
    }

    public Integer getCoutDetailBonCommande() {
        return coutDetailBonCommande;
    }

    public void setCoutDetailBonCommande(Integer coutDetailBonCommande) {
        this.coutDetailBonCommande = coutDetailBonCommande;
    }

    public String getLibDetailBonCommande() {
        return libDetailBonCommande;
    }

    public void setLibDetailBonCommande(String libDetailBonCommande) {
        this.libDetailBonCommande = libDetailBonCommande;
    }

    public BonCommande getBonCommandeidBonCommande() {
        return bonCommandeidBonCommande;
    }

    public void setBonCommandeidBonCommande(BonCommande bonCommandeidBonCommande) {
        this.bonCommandeidBonCommande = bonCommandeidBonCommande;
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
        return "entities.DetailBonCommande[ idDetailBonCommande=" + idDetailBonCommande + " ]";
    }
    
}
