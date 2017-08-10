/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableView;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import superpackage.SuperClass;

@Entity
public class DetailFacture extends SuperClass implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private int pu;
    private int qte;
    private int montant;
    private String date;
    private String libArticle;
    private String client;
    
    public DetailFacture(){
    };
    
    public DetailFacture(int id,String lib,int pu,int qte,int montant){
      this.id=id;this.pu=pu;this.qte=qte;this.montant=montant;this.libArticle=lib;
    }
    public Integer getId() {
        return id;
    }
    public void setMOntant(){
        this.montant=pu*qte;
    }
    public void setDate(String date){
        this.date=date;
    }
    public String getDate(){
        return this.date;
    }
    public int getMontant(){
     return this.montant;
    }
    public int getPu(){
        return this.pu;
    }
    public void setPu(int pu){
        this.pu=pu;
    }
    public void setQte(int qte){
        this.qte=qte;
    }
    public int getQte(){
        return this.qte;
    }
    
    public void setLibArticle(String lib){
        this.libArticle=lib;
    }
    public String getLibArticle(){
        return this.libArticle;
    }
    
  public String getClient(){
      return this.client;
  }
  public void setClient(String client){
      this.client=client;
  }
    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "entite.tableView.DetailFacture[ id=" + id + " ]";
    }     
}
