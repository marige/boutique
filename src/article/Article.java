
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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name= "article")
public class Article implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idarticle")
    private int idArticle;
    @Column(name = "stock")
    private Integer stock;
    @Column(name = "prixvente")
    private Integer prixvente;
    @Column(name = "stocksecurite")
    private Integer stocksecurite; 
    @Column(name = "libarticle")
    private String libarticle;
 
    //gestion de 
    @ManyToOne
    //integration des identifiants de champs de après
    @JoinColumn(name="idcategorie")
    private Categorie categorie;
    
    @OneToMany(mappedBy="articleV",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Vente> vente=null;    
    
    @OneToMany(mappedBy="article",cascade=CascadeType.ALL)
    private List<DetailBonCommande> detailBonCommandeList;

   
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
    public void setDetailBonCommande(List<DetailBonCommande> detailBonCommandeList){
        this.detailBonCommandeList=detailBonCommandeList;
    }
    public List<DetailBonCommande> getDetailBonCommandeList(){
        return this.detailBonCommandeList;
    }
    
    @Override
    public String toString(){
        return this.libarticle;
    }


    public Integer getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Integer getPrixvente() {
        return prixvente;
    }

    public void setPrixvente(int prixvente) {
        this.prixvente = prixvente;
    }

    public Integer getStocksecurite() {
        return stocksecurite;
    }

    public void setStocksecurite(int stocksecurite) {
        this.stocksecurite = stocksecurite;
    }

    public Article() {
    }
    
}
