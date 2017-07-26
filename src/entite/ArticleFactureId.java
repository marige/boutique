/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 *
 * @author OBAM
 */
@Embeddable
public class ArticleFactureId implements Serializable {
 private Article art;
 private Facture fact;
 
 public ArticleFactureId(){ 
 }
 
 @ManyToOne
 public Article getArticle(){
     return this.art;
 }
 
 public void setArticle(Article a){
     this.art=a;
 }
 
 @ManyToOne
 public Facture getFacture(){
     return this.fact;
 }
 public void setFacture(Facture f){
     this.fact=f;
 }

 
}
