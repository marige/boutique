/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autorisation;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import superpackage.SuperClass;
import utilisateur.Utilisateur;
import utilisateur.exceptions.NonexistentEntityException;

/**
 *
 * @author OBAM
 */
public class AutorisationJpaController extends SuperClass implements Serializable {
    public void create(Autorisation autorisation) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(autorisation);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
     public void updateAttribut(List<Autorisation> ancienneAutoList ,List<Autorisation> newautoList) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //destruction des anciennes permissions
            if(!ancienneAutoList.isEmpty())
            this.destroyListAutorisation(ancienneAutoList);
            //inscription de la nouvelle liste 
            if(!newautoList.isEmpty())
            for(Autorisation a:newautoList){             
                em.persist(a);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    

    public void edit(Autorisation autorisation) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            autorisation = em.merge(autorisation);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = autorisation.getIdautorisation();
                if (findAutorisation(id) == null) {
                    throw new NonexistentEntityException("The autorisation with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Autorisation autorisation;
            try {
                autorisation = em.getReference(Autorisation.class, id);
                autorisation.getIdautorisation();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The autorisation with id " + id + " no longer exists.", enfe);
            }
            em.remove(autorisation);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
   
     public void destroyListAutorisation(List<Autorisation> ancienneAutoList) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            for(Autorisation a:ancienneAutoList){
            try {
                a = em.getReference(Autorisation.class, a.getIdautorisation());
                a.getIdautorisation();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The autorisation with id " + a.getIdautorisation() + " no longer exists.", enfe);
            }
            em.remove(a);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public List<Autorisation> findAutorisationEntities() {
        return findAutorisationEntities(true, -1, -1);
    }

    public List<Autorisation> findAutorisationEntities(int maxResults, int firstResult) {
        return findAutorisationEntities(false, maxResults, firstResult);
    }

    private List<Autorisation> findAutorisationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Autorisation.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Autorisation findAutorisation(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Autorisation.class, id);
        } finally {
            em.close();
        }
    }

    public int getAutorisationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Autorisation> rt = cq.from(Autorisation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public List<Autorisation> getUserAutorisation(Utilisateur user){
        EntityManager em=getEntityManager();
        List<Autorisation> auto= em.createNamedQuery("findUserAutorisation")
                                   .setParameter("utilisateur",user)
                                   .getResultList();
                return auto;
    }
    
}
