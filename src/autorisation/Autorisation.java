/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autorisation;

import controller.Controler;
import java.io.Serializable;
import javax.persistence.Basic;
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
import utilisateur.Utilisateur;

/**
 *
 * @author OBAM
 */
@Entity
@Table(name = "autorisation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "findUserAutorisation", query = "SELECT a FROM Autorisation a where a.utilisateur=:utilisateur"),
    @NamedQuery(name = "isAuthorized", query = "SELECT a FROM Autorisation a where a.utilisateur.idutilisateur=:iduser and a.controler.nomcontroler=:controlerName"),})
public class Autorisation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idautorisation")
    private int idautorisation;
  /*  @Basic(optional = false)
    @Column(name = "idutilisateur")
    private int idutilisateur;
    @Basic(optional = false)
    @Column(name = "nomcontroler")
    private String nomcontroler;*/
     
    @ManyToOne
    @JoinColumn(name="nomcontroler")
     private Controler controler;
    
    @ManyToOne
    @JoinColumn(name="idutilisateur")
     private Utilisateur utilisateur;
     
    public void setUtilisateur(Utilisateur u){
        this.utilisateur=u;
    }
    
    public Utilisateur getUtilisateur(){
        return this.utilisateur;
    }
    public void setControler(Controler c){
        this.controler=c;
    }
    public Controler getControler(){
        return this.controler;
    }
    public Autorisation() {
    }

    public Autorisation(int idautorisation) {
        this.idautorisation = idautorisation;
    }
  
    public int getIdautorisation() {
        return idautorisation;
    }

    public void setIdautorisation(int idautorisation) {
        this.idautorisation = idautorisation;
    }
    @Override
    public String toString() {
        return "utilisateur.Autorisation[ idautorisation=" + idautorisation + " ]";
    }
    
}
