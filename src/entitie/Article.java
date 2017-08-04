/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitie;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
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
import javax.xml.bind.annotation.XmlTransient;

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
    @Column(name = "prixachat")
    private int prixachat;
    @Column(name = "prixvente")
    private int prixvente;
 
    //gestion de 
    @ManyToOne
    //integration des identifiants de champs de après
    @JoinColumn (name="idcategorie")
    private Categorie categorie;
    
    @OneToMany(mappedBy="articleV",cascade=CascadeType.ALL)
    private List<Vente> vente=null;    
    
    @OneToMany(mappedBy = "idArticle",cascade = CascadeType.ALL)
    private List<DetailBonCommande> detailBonCommandeList;
     
    public Article() {
        super();
    }
     public Article(int id,String lib,int stock,Set factureSet){
      this.setIdarticle(id);
      this.setLibarticle(lib);
      this.setStock(stock);
     // this.factureSet=factureSet;
    }
     
    public Article(int id,String lib,int stock){
      this.setIdarticle(id);
      this.setLibarticle(lib);
      this.setStock(stock);
    }

    public Article(int idarticle) {
      this.idArticle=idarticle;
    }
    public int getPrixAchat(){
        return this.prixachat;
    }
    public int getPrixVente(){
        return this.prixvente;
    }
    public void setPrixAchat(int prixachat){
        this.prixachat=prixachat;
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
    public Categorie getCategorie(Categorie cat){
        return this.categorie;
    }
    public List<Vente> getVenteArticle(){
        return this.vente;
    }
    public void addVenteToArticle(Vente v){
        this.vente.add(v);
    }
       
    @XmlTransient
    public List<DetailBonCommande> getDetailBonCommandeList() {
        return detailBonCommandeList;
    }

    public void setDetailBonCommandeList(List<DetailBonCommande> detailBonCommandeList) {
        this.detailBonCommandeList = detailBonCommandeList;
    }
    @Override
    public String toString(){
        return this.libarticle;
    }
 
    
    
}
