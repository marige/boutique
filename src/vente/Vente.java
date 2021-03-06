
package vente;

import facture.Facture;
import article.Article;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
            query = "SELECT v from Vente v where v.factureV.dateFacture BETWEEN :dateDebut and :dateFin order by v.factureV.idFacture desc"),
    @NamedQuery(name = "findListVenteFacture", 
            query = "SELECT v from Vente v where v.factureV.idFacture=:idfacture order by v.idVente asc")
})
     
public class Vente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idvente")
    private int idVente;
    //les autres champs  de la table vente
    @Column(name = "qte")
    private Integer qte;
    @Column(name = "pu")
    private Integer pu;
   
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
