/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package categorie;

import entitie.Article;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "categorie")
public class Categorie implements Serializable{    
    @Column(name = "libCategorie")
    private String libcat;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCategorie")
    private int idcat;
    
    @OneToMany(cascade=ALL,mappedBy="categorie")
    private List<Article> listArticles;
    
    public Categorie() {
    }
   
 
    public int getIdCategorie() {
        return this.idcat;
    }

    public void setIdCategorie(Integer idCategorie) {
       this.idcat=idCategorie;
    }
    
 
    public String getLibCategorie() {
        return this.libcat;
    }

    public void setLibCategorie(String libCategorie) {
        this.libcat=libCategorie;
    }
    public void setListArticle(List article){ 
        this.listArticles=article;
    }
    
    public void setArticle(Article a){
        a.setCategorie(this);
        listArticles.add(a);
    }
    
    public List<Article>  getListArticle(){
        return this.listArticles;
    }
    @Override
    public String toString(){
        return this.libcat;
    }
}
