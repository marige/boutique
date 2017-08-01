/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaController;

import entitie.Facture;
import entite.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import superpackage.SuperClass;

/**
 *
 * @author OBAM
 */
public class FactureJpaController extends SuperClass implements Serializable {
  
    EntityManager em=null;

    public void create(Facture facture) {
       
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(facture);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Facture facture) throws NonexistentEntityException, Exception {
      
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            facture = em.merge(facture);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facture.getIdFacture();
                if (findFacture(id) == null) {
                    throw new NonexistentEntityException("The facture with id " + id + " no longer exists.");
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
    
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Facture facture;
            try {
                facture = em.getReference(Facture.class, id);
                facture.getIdFacture();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facture with id " + id + " no longer exists.", enfe);
            }
            em.remove(facture);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Facture> findFactureEntities() {
        return findFactureEntities(true, -1, -1);
    }

    public List<Facture> findFactureEntities(int maxResults, int firstResult) {
        return findFactureEntities(false, maxResults, firstResult);
    }

    private List<Facture> findFactureEntities(boolean all, int maxResults, int firstResult) {
         em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Facture.class));
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

    public Facture findFacture(Integer id) {
         em = getEntityManager();
        try {
            return em.find(Facture.class, id);
        } finally {
            em.close();
        }
    }

    public int getFactureCount() {
         em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Facture> rt = cq.from(Facture.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
