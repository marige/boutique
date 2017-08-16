/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.Controler;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import superpackage.SuperClass;
import utilisateur.exceptions.NonexistentEntityException;
import utilisateur.exceptions.PreexistingEntityException;

/**
 *
 * @author OBAM
 */
public class ControlerJpaController extends SuperClass implements Serializable {

    public void create(Controler controler) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(controler);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findControler(controler.getNomcontroler()) != null) {
                throw new PreexistingEntityException("Controler " + controler + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Controler controler) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            controler = em.merge(controler);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = controler.getNomcontroler();
                if (findControler(id) == null) {
                    throw new NonexistentEntityException("The controler with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Controler controler;
            try {
                controler = em.getReference(Controler.class, id);
                controler.getNomcontroler();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The controler with id " + id + " no longer exists.", enfe);
            }
            em.remove(controler);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Controler> findControlerEntities() {
        return findControlerEntities(true, -1, -1);
    }

    public List<Controler> findControlerEntities(int maxResults, int firstResult) {
        return findControlerEntities(false, maxResults, firstResult);
    }

    private List<Controler> findControlerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Controler.class));
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

    public Controler findControler(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Controler.class, id);
        } finally {
            em.close();
        }
    }

    public int getControlerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Controler> rt = cq.from(Controler.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
