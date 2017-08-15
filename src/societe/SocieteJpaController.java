/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package societe;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import exceptions.NonexistentEntityException;
import superpackage.SuperClass;

/**
 *
 * @author OBAM
 */
public class SocieteJpaController extends SuperClass implements Serializable {

    public void create(Societe societe) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(societe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Societe societe) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            societe = em.merge(societe);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = societe.getIdsociete();
                if (findSociete(id) == null) {
                    throw new NonexistentEntityException("The societe with id " + id + " no longer exists.");
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
            Societe societe;
            try {
                societe = em.getReference(Societe.class, id);
                societe.getIdsociete();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The societe with id " + id + " no longer exists.", enfe);
            }
            em.remove(societe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Societe> findSocieteEntities() {
        return findSocieteEntities(true, -1, -1);
    }

    public List<Societe> findSocieteEntities(int maxResults, int firstResult) {
        return findSocieteEntities(false, maxResults, firstResult);
    }

    private List<Societe> findSocieteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Societe.class));
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

    public Societe findSociete(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Societe.class, id);
        } finally {
            em.close();
        }
    }

    public int getSocieteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Societe> rt = cq.from(Societe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
