/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpaController;

import entities.Article;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Categorie;
import entities.Stock;
import entities.DetailFacture;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpaController.exceptions.NonexistentEntityException;

/**
 *
 * @author geres
 */
public class ArticleJpaController implements Serializable {

    public ArticleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Article article) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categorie idCategorie = article.getIdCategorie();
            if (idCategorie != null) {
                idCategorie = em.getReference(idCategorie.getClass(), idCategorie.getIdCategorie());
                article.setIdCategorie(idCategorie);
            }
            Stock idStock = article.getIdStock();
            if (idStock != null) {
                idStock = em.getReference(idStock.getClass(), idStock.getIdStock());
                article.setIdStock(idStock);
            }
            DetailFacture iddetailFacture = article.getIddetailFacture();
            if (iddetailFacture != null) {
                iddetailFacture = em.getReference(iddetailFacture.getClass(), iddetailFacture.getIddetailFacture());
                article.setIddetailFacture(iddetailFacture);
            }
            em.persist(article);
            if (idCategorie != null) {
                idCategorie.getArticleList().add(article);
                idCategorie = em.merge(idCategorie);
            }
            if (idStock != null) {
                idStock.getArticleList().add(article);
                idStock = em.merge(idStock);
            }
            if (iddetailFacture != null) {
                iddetailFacture.getArticleList().add(article);
                iddetailFacture = em.merge(iddetailFacture);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Article article) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Article persistentArticle = em.find(Article.class, article.getIdArticle());
            Categorie idCategorieOld = persistentArticle.getIdCategorie();
            Categorie idCategorieNew = article.getIdCategorie();
            Stock idStockOld = persistentArticle.getIdStock();
            Stock idStockNew = article.getIdStock();
            DetailFacture iddetailFactureOld = persistentArticle.getIddetailFacture();
            DetailFacture iddetailFactureNew = article.getIddetailFacture();
            if (idCategorieNew != null) {
                idCategorieNew = em.getReference(idCategorieNew.getClass(), idCategorieNew.getIdCategorie());
                article.setIdCategorie(idCategorieNew);
            }
            if (idStockNew != null) {
                idStockNew = em.getReference(idStockNew.getClass(), idStockNew.getIdStock());
                article.setIdStock(idStockNew);
            }
            if (iddetailFactureNew != null) {
                iddetailFactureNew = em.getReference(iddetailFactureNew.getClass(), iddetailFactureNew.getIddetailFacture());
                article.setIddetailFacture(iddetailFactureNew);
            }
            article = em.merge(article);
            if (idCategorieOld != null && !idCategorieOld.equals(idCategorieNew)) {
                idCategorieOld.getArticleList().remove(article);
                idCategorieOld = em.merge(idCategorieOld);
            }
            if (idCategorieNew != null && !idCategorieNew.equals(idCategorieOld)) {
                idCategorieNew.getArticleList().add(article);
                idCategorieNew = em.merge(idCategorieNew);
            }
            if (idStockOld != null && !idStockOld.equals(idStockNew)) {
                idStockOld.getArticleList().remove(article);
                idStockOld = em.merge(idStockOld);
            }
            if (idStockNew != null && !idStockNew.equals(idStockOld)) {
                idStockNew.getArticleList().add(article);
                idStockNew = em.merge(idStockNew);
            }
            if (iddetailFactureOld != null && !iddetailFactureOld.equals(iddetailFactureNew)) {
                iddetailFactureOld.getArticleList().remove(article);
                iddetailFactureOld = em.merge(iddetailFactureOld);
            }
            if (iddetailFactureNew != null && !iddetailFactureNew.equals(iddetailFactureOld)) {
                iddetailFactureNew.getArticleList().add(article);
                iddetailFactureNew = em.merge(iddetailFactureNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = article.getIdArticle();
                if (findArticle(id) == null) {
                    throw new NonexistentEntityException("The article with id " + id + " no longer exists.");
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
            Article article;
            try {
                article = em.getReference(Article.class, id);
                article.getIdArticle();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The article with id " + id + " no longer exists.", enfe);
            }
            Categorie idCategorie = article.getIdCategorie();
            if (idCategorie != null) {
                idCategorie.getArticleList().remove(article);
                idCategorie = em.merge(idCategorie);
            }
            Stock idStock = article.getIdStock();
            if (idStock != null) {
                idStock.getArticleList().remove(article);
                idStock = em.merge(idStock);
            }
            DetailFacture iddetailFacture = article.getIddetailFacture();
            if (iddetailFacture != null) {
                iddetailFacture.getArticleList().remove(article);
                iddetailFacture = em.merge(iddetailFacture);
            }
            em.remove(article);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Article> findArticleEntities() {
        return findArticleEntities(true, -1, -1);
    }

    public List<Article> findArticleEntities(int maxResults, int firstResult) {
        return findArticleEntities(false, maxResults, firstResult);
    }

    private List<Article> findArticleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Article.class));
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

    public Article findArticle(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Article.class, id);
        } finally {
            em.close();
        }
    }

    public int getArticleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Article> rt = cq.from(Article.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
