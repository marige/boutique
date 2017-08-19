/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package societe;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OBAM
 */
@Entity
@Table(name = "societe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Societe.findAll", query = "SELECT s FROM Societe s"),
    @NamedQuery(name = "Societe.findByIdsociete", query = "SELECT s FROM Societe s WHERE s.idsociete = :idsociete"),
    @NamedQuery(name = "Societe.findByLibelle", query = "SELECT s FROM Societe s WHERE s.libelle = :libelle"),
    @NamedQuery(name = "Societe.findByIfu", query = "SELECT s FROM Societe s WHERE s.ifu = :ifu"),
    @NamedQuery(name = "Societe.findByRcm", query = "SELECT s FROM Societe s WHERE s.rcm = :rcm"),
    @NamedQuery(name = "Societe.findByEntetedocument", query = "SELECT s FROM Societe s WHERE s.entetedocument = :entetedocument"),
    @NamedQuery(name = "Societe.findByPiedsdocument", query = "SELECT s FROM Societe s WHERE s.piedsdocument = :piedsdocument")})
public class Societe implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idsociete")
    private Integer idsociete;
    @Column(name = "libelle")
    private String libelle;
    @Column(name = "ifu")
    private String ifu;
    @Column(name = "rcm")
    private String rcm;
    @Column(name = "entetedocument")
    private String entetedocument;
    @Column(name = "piedsdocument")
    private String piedsdocument;
    @Column(name = "adresse")
    private String adresse;

    public Societe() {
        this.adresse=this.entetedocument=this.ifu=this.libelle=this.piedsdocument=this.rcm=" ";
    }

    public Societe(Integer idsociete) {
        this.idsociete = idsociete;
    }

    public Integer getIdsociete() {
        return idsociete;
    }

    public void setIdsociete(Integer idsociete) {
        this.idsociete = idsociete;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getIfu() {
        return ifu;
    }

    public void setIfu(String ifu) {
        this.ifu = ifu;
    }

    public String getRcm() {
        return rcm;
    }

    public void setRcm(String rcm) {
        this.rcm = rcm;
    }

    public String getEntetedocument() {
        return entetedocument;
    }

    public void setEntetedocument(String entetedocument) {
        this.entetedocument = entetedocument;
    }

    public String getPiedsdocument() {
        return piedsdocument;
    }

    public void setPiedsdocument(String piedsdocument) {
        this.piedsdocument = piedsdocument;
    }
   public void setAdresse(String adresse){
       this.adresse=adresse;
   }
   public String getAdresse(){
       return this.adresse;
   }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociete != null ? idsociete.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Societe)) {
            return false;
        }
        Societe other = (Societe) object;
        if ((this.idsociete == null && other.idsociete != null) || (this.idsociete != null && !this.idsociete.equals(other.idsociete))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "societe.Societe[ idsociete=" + idsociete + " ]";
    }
    
}
