/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boncommande;

import exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import superpackage.SuperClass;

/**
 *
 * @author OBAM
 */
public class BonCommandeJpaController extends SuperClass implements Serializable {

    public void create(BonCommande boncommande) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(boncommande);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BonCommande boncommande) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            boncommande = em.merge(boncommande);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = boncommande.getIdBonCommande();
                if (findBoncommande(id) == null) {
                    throw new NonexistentEntityException("The boncommande with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BonCommande boncommande;
            try {
                boncommande = em.getReference(BonCommande.class, id);
                boncommande.getIdBonCommande();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The boncommande with id " + id + " no longer exists.", enfe);
            }
            em.remove(boncommande);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BonCommande> findBoncommandeEntities() {
        return findBoncommandeEntities(true, -1, -1);
    }

    public List<BonCommande> findBoncommandeEntities(int maxResults, int firstResult) {
        return findBoncommandeEntities(false, maxResults, firstResult);
    }

    private List<BonCommande> findBoncommandeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BonCommande.class));
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

    public BonCommande findBoncommande(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BonCommande.class, id);
        } finally {
            em.close();
        }
    }

    public int getBoncommandeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BonCommande> rt = cq.from(BonCommande.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public List<BonCommande> getListBonCommandeParDate(Date dateDebut,Date dateFin){
       EntityManager em = getEntityManager();
        List<BonCommande> l=  em.createNamedQuery("Boncommande.bonParPeriode")
                .setParameter("dateFin",dateFin, TemporalType.DATE)
                .setParameter("dateDebut",dateDebut,TemporalType.DATE)
                .getResultList();
        em.close();
       return l;
    }
    
}
