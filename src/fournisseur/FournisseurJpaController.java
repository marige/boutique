/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fournisseur;

import exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import superpackage.SuperClass;

/**
 *
 * @author OBAM
 */
public class FournisseurJpaController extends SuperClass implements Serializable {

    public void create(Fournisseur fournisseur) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(fournisseur);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fournisseur fournisseur) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            fournisseur = em.merge(fournisseur);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fournisseur.getIdFournisseur();
                if (findFournisseur(id) == null) {
                    throw new NonexistentEntityException("The fournisseur with id " + id + " no longer exists.");
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
            Fournisseur fournisseur;
            try {
                fournisseur = em.getReference(Fournisseur.class, id);
                fournisseur.getIdFournisseur();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fournisseur with id " + id + " no longer exists.", enfe);
            }
            em.remove(fournisseur);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fournisseur> findFournisseurEntities() {
        return findFournisseurEntities(true, -1, -1);
    }

    public List<Fournisseur> findFournisseurEntities(int maxResults, int firstResult) {
        return findFournisseurEntities(false, maxResults, firstResult);
    }

    private List<Fournisseur> findFournisseurEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fournisseur.class));
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

    public Fournisseur findFournisseur(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fournisseur.class, id);
        } finally {
            em.close();
        }
    }

    public int getFournisseurCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fournisseur> rt = cq.from(Fournisseur.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
