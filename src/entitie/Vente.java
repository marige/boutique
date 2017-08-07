/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitie;

import article.Article;
import java.io.Serializable;
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
@Table(name = "vente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "finListVenteDate", 
            query = "SELECT v from Vente v,Facture f,Article a where v.factureV=f and v.articleV=a and  v.factureV.dateFacture BETWEEN :dateDebut and :dateFin order by v.factureV.dateFacture")})

public class Vente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idvente")
    private int idVente;
       
    @ManyToOne
    @JoinColumn(name = "idarticle") 
    private Article articleV;
   
    @ManyToOne
    @JoinColumn(name = "idfacture")  
    private Facture factureV;
    
    public int getIdVente(){
        return this.idVente;
    }
    public void setIdVente(int idvente){
        this.idVente=idvente;
    }
    
    public Article getArticle(){
        return this.articleV;
    }
    
    public void setArticle(Article a){
       this.articleV=a;
    }
    
    public Facture getFacture(){
        return this.factureV;
    }
    
    public void setFacture(Facture f){
       this.factureV=f;
    }    
    //les autres champs  de la table vente
    @Column(name = "qte")
    private Integer qte;
    @Column(name = "pu")
    private Integer pu;

    public Vente() {
    }
    public void setQte(int qte){
        this.qte=qte;
    }
    public int getQte(){
        return this.qte;
    }
    public void setPu(int pu){
        this.pu=pu;
    }
    public int getPu(){
        return this.pu;
    }
 
}
