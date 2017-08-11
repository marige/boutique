
package article;

import detailboncommande.DetailBonCommande;
import categorie.Categorie;
import vente.Vente;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name= "article")
//@XmlRootElement
public class Article implements Serializable{
    private static final long serialVersionUID = 1L;       
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idarticle")
    private int idArticle;
    @Column(name = "libarticle")
    private String libarticle;
    @Column(name = "stock")
    private int stock;
    @Column(name = "prixvente")
    private int prixvente;
    @Column(name = "stocksecurite")
    private int stocksecurite;
 
 
    //gestion de 
    @ManyToOne
    //integration des identifiants de champs de après
    @JoinColumn (name="idcategorie")
    private Categorie categorie;
    
    @OneToMany(mappedBy="articleV",cascade=CascadeType.ALL)
    private List<Vente> vente=null;    
    
    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL)
    private List<DetailBonCommande> detailBonCommandeList;
     
    public Article() {
       
    }
   
    public Article(int idarticle) {
      this.idArticle=idarticle;
    }
    public void setDetailBonCommande(List<DetailBonCommande> detailBonCommandeList){
        this.detailBonCommandeList=detailBonCommandeList;
    }
    public List<DetailBonCommande> getDetailBonCommandeList(){
        return this.detailBonCommandeList;
    }
    
    public int getStockSecurite(){
        return this.stocksecurite;
    }
    public void setStockSecurite(int secur){
        this.stocksecurite=secur;
    }
    public int getPrixVente(){
        return this.prixvente;
    }
  
    public void setPrixVente(int prixVente){
        this.prixvente=prixVente;
    }
   
    public int getIdarticle() {
        return this.idArticle;
    }
    
    public void setIdarticle(int idarticle) {
       this.idArticle=idarticle;    
    }
   
    public String getLibarticle() {
        return this.libarticle;
    }

    public void setLibarticle(String libarticle) {
        this.libarticle=libarticle;
    }
   
    public int getStock() {
       return this.stock;
    }

    public void setStock(int stock) {
      this.stock=stock;
    }
    public void setCategorie(Categorie cat){
        this.categorie=cat;
    }
    public Categorie getCategorie(){
        return this.categorie;
    }
    public List<Vente> getVenteArticle(){
        return this.vente;
    }
    public void addVenteToArticle(Vente v){
        this.vente.add(v);
    }
      
    @Override
    public String toString(){
        return this.libarticle;
    }
 
    
    
}
