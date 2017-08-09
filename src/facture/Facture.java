/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facture;

import vente.Vente;
import vente.Vente;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "facture")
@XmlRootElement
public class Facture implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFacture")
    private int idFacture;
    @Column(name = "dateFacture")
    @Temporal(TemporalType.DATE)
    private Date dateFacture;
    @Column(name = "client")
    private String client;
    @Column(name="montant")
    private int montant;
    
   @OneToMany(mappedBy="factureV" ,cascade=CascadeType.ALL)
   private List<Vente> venteFacture=new ArrayList();
   
    
    public Facture() {
    } 
    public Facture(Integer idFacture) {
        this.idFacture = idFacture;
    }
  
    public Integer getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(Integer idFacture) {
        this.idFacture = idFacture;
    }

    public Date getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(Date dateFacture) {
        this.dateFacture = dateFacture;
    }
    
    public void addVenteToFacture(Vente vente){
        this.venteFacture.add(vente);
    }
    public List<Vente>  getVenteFacture(){
        return this.venteFacture;
    }
    
    public String getClient(){
        return this.client;
    }
    
    public void setClient(String client){
        this.client=client;
    }
    public void setMontant(int montant){
        this.montant=montant;
    }
    public int getMontant(){
        return this.montant;
    }
    
    
}
