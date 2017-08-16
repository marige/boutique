/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilisateur;

import autorisation.Autorisation;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OBAM
 */
@Entity
@Table(name = "utilisateur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Utilisateur.findAll", query = "SELECT u FROM Utilisateur u"),
    @NamedQuery(name = "Utilisateur.findByIdutilisateur", query = "SELECT u FROM Utilisateur u WHERE u.idutilisateur = :idutilisateur"),
    @NamedQuery(name = "Utilisateur.findByLogin", query = "SELECT u FROM Utilisateur u WHERE u.login = :login"),
    @NamedQuery(name = "Utilisateur.findByMotpasse", query = "SELECT u FROM Utilisateur u WHERE u.motpasse = :motpasse")})
public class Utilisateur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idutilisateur")
    private Integer idutilisateur;
    @Basic(optional = false)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @Column(name = "motpasse")
    private String motpasse;
    @Column(name = "fonction")
    private String fonction;
    @Column(name = "typeuser")
    private String typeuser;
    @Column(name = "nom")
    private String nom;
   
    @OneToMany(mappedBy="utilisateur",cascade=CascadeType.ALL)
     private List<Autorisation> autorisation;
     
    public Utilisateur() {
    }
   public void setAutorisation(List<Autorisation> autorisation){
       this.autorisation=autorisation;
   }
   public List<Autorisation> getAutorisation(){
       return this.autorisation;
   }
    public Utilisateur(Integer idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

    public Utilisateur(Integer idutilisateur, String login, String motpasse) {
        this.idutilisateur = idutilisateur;
        this.login = login;
        this.motpasse = motpasse;
    }
    public String getNom(){
        return this.nom;
    }
    public void setNom(String nom){
        this.nom=nom;
    }
    public void setFonction(String fonction){
        this.fonction=fonction;
    }
    public String getFonction(){
        return this.fonction;
    }
    public void setTypeUser(String type){
        this.typeuser=type;
    }
    public String getTypeUser(){
        return this.typeuser;
    }
    
    public Integer getIdutilisateur() {
        return idutilisateur;
    }

    public void setIdutilisateur(Integer idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotpasse() {
        return motpasse;
    }

    public void setMotpasse(String motpasse) {
        this.motpasse = motpasse;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idutilisateur != null ? idutilisateur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Utilisateur)) {
            return false;
        }
        Utilisateur other = (Utilisateur) object;
        if ((this.idutilisateur == null && other.idutilisateur != null) || (this.idutilisateur != null && !this.idutilisateur.equals(other.idutilisateur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.login;
    }
    
}
