/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpaControler;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.BonCommande;
import entities.DetailBonCommande;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpaControler.exceptions.NonexistentEntityException;

/**
 *
 * @author geres
 */
public class DetailBonCommandeJpaController implements Serializable {

    public DetailBonCommandeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetailBonCommande detailBonCommande) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BonCommande bonCommandeidBonCommande = detailBonCommande.getBonCommandeidBonCommande();
            if (bonCommandeidBonCommande != null) {
                bonCommandeidBonCommande = em.getReference(bonCommandeidBonCommande.getClass(), bonCommandeidBonCommande.getIdBonCommande());
                detailBonCommande.setBonCommandeidBonCommande(bonCommandeidBonCommande);
            }
            em.persist(detailBonCommande);
            if (bonCommandeidBonCommande != null) {
                bonCommandeidBonCommande.getDetailBonCommandeList().add(detailBonCommande);
                bonCommandeidBonCommande = em.merge(bonCommandeidBonCommande);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetailBonCommande detailBonCommande) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetailBonCommande persistentDetailBonCommande = em.find(DetailBonCommande.class, detailBonCommande.getIdDetailBonCommande());
            BonCommande bonCommandeidBonCommandeOld = persistentDetailBonCommande.getBonCommandeidBonCommande();
            BonCommande bonCommandeidBonCommandeNew = detailBonCommande.getBonCommandeidBonCommande();
            if (bonCommandeidBonCommandeNew != null) {
                bonCommandeidBonCommandeNew = em.getReference(bonCommandeidBonCommandeNew.getClass(), bonCommandeidBonCommandeNew.getIdBonCommande());
                detailBonCommande.setBonCommandeidBonCommande(bonCommandeidBonCommandeNew);
            }
            detailBonCommande = em.merge(detailBonCommande);
            if (bonCommandeidBonCommandeOld != null && !bonCommandeidBonCommandeOld.equals(bonCommandeidBonCommandeNew)) {
                bonCommandeidBonCommandeOld.getDetailBonCommandeList().remove(detailBonCommande);
                bonCommandeidBonCommandeOld = em.merge(bonCommandeidBonCommandeOld);
            }
            if (bonCommandeidBonCommandeNew != null && !bonCommandeidBonCommandeNew.equals(bonCommandeidBonCommandeOld)) {
                bonCommandeidBonCommandeNew.getDetailBonCommandeList().add(detailBonCommande);
                bonCommandeidBonCommandeNew = em.merge(bonCommandeidBonCommandeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detailBonCommande.getIdDetailBonCommande();
                if (findDetailBonCommande(id) == null) {
                    throw new NonexistentEntityException("The detailBonCommande with id " + id + " no longer exists.");
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
            DetailBonCommande detailBonCommande;
            try {
                detailBonCommande = em.getReference(DetailBonCommande.class, id);
                detailBonCommande.getIdDetailBonCommande();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detailBonCommande with id " + id + " no longer exists.", enfe);
            }
            BonCommande bonCommandeidBonCommande = detailBonCommande.getBonCommandeidBonCommande();
            if (bonCommandeidBonCommande != null) {
                bonCommandeidBonCommande.getDetailBonCommandeList().remove(detailBonCommande);
                bonCommandeidBonCommande = em.merge(bonCommandeidBonCommande);
            }
            em.remove(detailBonCommande);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetailBonCommande> findDetailBonCommandeEntities() {
        return findDetailBonCommandeEntities(true, -1, -1);
    }

    public List<DetailBonCommande> findDetailBonCommandeEntities(int maxResults, int firstResult) {
        return findDetailBonCommandeEntities(false, maxResults, firstResult);
    }

    private List<DetailBonCommande> findDetailBonCommandeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetailBonCommande.class));
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

    public DetailBonCommande findDetailBonCommande(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetailBonCommande.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetailBonCommandeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetailBonCommande> rt = cq.from(DetailBonCommande.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
