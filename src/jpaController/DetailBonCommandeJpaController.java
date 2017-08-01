/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpaController;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entitie.BonCommande;
import entitie.Article;
import entitie.DetailBonCommande;
import java.util.List;
import javax.persistence.EntityManager;
import jpaController.exceptions.NonexistentEntityException;
import superpackage.SuperClass;

/**
 *
 * @author geres
 */
public class DetailBonCommandeJpaController extends SuperClass implements Serializable {
    EntityManager em = null;
    
    public void create(DetailBonCommande detailBonCommande) {
       
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BonCommande idBonCommande = detailBonCommande.getIdBonCommande();
            if (idBonCommande != null) {
                idBonCommande = em.getReference(idBonCommande.getClass(), idBonCommande.getIdBonCommande());
                detailBonCommande.setIdBonCommande(idBonCommande);
            }
            Article idArticle = detailBonCommande.getIdArticle();
            if (idArticle != null) {
                idArticle = em.getReference(idArticle.getClass(), idArticle.getIdarticle());
                detailBonCommande.setIdArticle(idArticle);
            }
            em.persist(detailBonCommande);
            if (idBonCommande != null) {
                idBonCommande.getDetailBonCommandeList().add(detailBonCommande);
                idBonCommande = em.merge(idBonCommande);
            }
            if (idArticle != null) {
                idArticle.getDetailBonCommandeList().add(detailBonCommande);
                idArticle = em.merge(idArticle);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetailBonCommande detailBonCommande) throws NonexistentEntityException, Exception {
       
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetailBonCommande persistentDetailBonCommande = em.find(DetailBonCommande.class, detailBonCommande.getIdDetailBonCommande());
            BonCommande idBonCommandeOld = persistentDetailBonCommande.getIdBonCommande();
            BonCommande idBonCommandeNew = detailBonCommande.getIdBonCommande();
            Article idArticleOld = persistentDetailBonCommande.getIdArticle();
            Article idArticleNew = detailBonCommande.getIdArticle();
            if (idBonCommandeNew != null) {
                idBonCommandeNew = em.getReference(idBonCommandeNew.getClass(), idBonCommandeNew.getIdBonCommande());
                detailBonCommande.setIdBonCommande(idBonCommandeNew);
            }
            if (idArticleNew != null) {
                idArticleNew = em.getReference(idArticleNew.getClass(), idArticleNew.getIdarticle());
                detailBonCommande.setIdArticle(idArticleNew);
            }
            detailBonCommande = em.merge(detailBonCommande);
            if (idBonCommandeOld != null && !idBonCommandeOld.equals(idBonCommandeNew)) {
                idBonCommandeOld.getDetailBonCommandeList().remove(detailBonCommande);
                idBonCommandeOld = em.merge(idBonCommandeOld);
            }
            if (idBonCommandeNew != null && !idBonCommandeNew.equals(idBonCommandeOld)) {
                idBonCommandeNew.getDetailBonCommandeList().add(detailBonCommande);
                idBonCommandeNew = em.merge(idBonCommandeNew);
            }
            if (idArticleOld != null && !idArticleOld.equals(idArticleNew)) {
                idArticleOld.getDetailBonCommandeList().remove(detailBonCommande);
                idArticleOld = em.merge(idArticleOld);
            }
            if (idArticleNew != null && !idArticleNew.equals(idArticleOld)) {
                idArticleNew.getDetailBonCommandeList().add(detailBonCommande);
                idArticleNew = em.merge(idArticleNew);
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
            BonCommande idBonCommande = detailBonCommande.getIdBonCommande();
            if (idBonCommande != null) {
                idBonCommande.getDetailBonCommandeList().remove(detailBonCommande);
                idBonCommande = em.merge(idBonCommande);
            }
            Article idArticle = detailBonCommande.getIdArticle();
            if (idArticle != null) {
                idArticle.getDetailBonCommandeList().remove(detailBonCommande);
                idArticle = em.merge(idArticle);
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
        em = getEntityManager();
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
        em = getEntityManager();
        try {
            return em.find(DetailBonCommande.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetailBonCommandeCount() {
        em = getEntityManager();
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
