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
import entities.Article;
import entities.Stock;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpaControler.exceptions.IllegalOrphanException;
import jpaControler.exceptions.NonexistentEntityException;

/**
 *
 * @author geres
 */
public class StockJpaController implements Serializable {

    public StockJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Stock stock) {
        if (stock.getArticleList() == null) {
            stock.setArticleList(new ArrayList<Article>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Article> attachedArticleList = new ArrayList<Article>();
            for (Article articleListArticleToAttach : stock.getArticleList()) {
                articleListArticleToAttach = em.getReference(articleListArticleToAttach.getClass(), articleListArticleToAttach.getIdArticle());
                attachedArticleList.add(articleListArticleToAttach);
            }
            stock.setArticleList(attachedArticleList);
            em.persist(stock);
            for (Article articleListArticle : stock.getArticleList()) {
                Stock oldIdStockOfArticleListArticle = articleListArticle.getIdStock();
                articleListArticle.setIdStock(stock);
                articleListArticle = em.merge(articleListArticle);
                if (oldIdStockOfArticleListArticle != null) {
                    oldIdStockOfArticleListArticle.getArticleList().remove(articleListArticle);
                    oldIdStockOfArticleListArticle = em.merge(oldIdStockOfArticleListArticle);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Stock stock) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Stock persistentStock = em.find(Stock.class, stock.getIdStock());
            List<Article> articleListOld = persistentStock.getArticleList();
            List<Article> articleListNew = stock.getArticleList();
            List<String> illegalOrphanMessages = null;
            for (Article articleListOldArticle : articleListOld) {
                if (!articleListNew.contains(articleListOldArticle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Article " + articleListOldArticle + " since its idStock field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Article> attachedArticleListNew = new ArrayList<Article>();
            for (Article articleListNewArticleToAttach : articleListNew) {
                articleListNewArticleToAttach = em.getReference(articleListNewArticleToAttach.getClass(), articleListNewArticleToAttach.getIdArticle());
                attachedArticleListNew.add(articleListNewArticleToAttach);
            }
            articleListNew = attachedArticleListNew;
            stock.setArticleList(articleListNew);
            stock = em.merge(stock);
            for (Article articleListNewArticle : articleListNew) {
                if (!articleListOld.contains(articleListNewArticle)) {
                    Stock oldIdStockOfArticleListNewArticle = articleListNewArticle.getIdStock();
                    articleListNewArticle.setIdStock(stock);
                    articleListNewArticle = em.merge(articleListNewArticle);
                    if (oldIdStockOfArticleListNewArticle != null && !oldIdStockOfArticleListNewArticle.equals(stock)) {
                        oldIdStockOfArticleListNewArticle.getArticleList().remove(articleListNewArticle);
                        oldIdStockOfArticleListNewArticle = em.merge(oldIdStockOfArticleListNewArticle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = stock.getIdStock();
                if (findStock(id) == null) {
                    throw new NonexistentEntityException("The stock with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Stock stock;
            try {
                stock = em.getReference(Stock.class, id);
                stock.getIdStock();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The stock with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Article> articleListOrphanCheck = stock.getArticleList();
            for (Article articleListOrphanCheckArticle : articleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Stock (" + stock + ") cannot be destroyed since the Article " + articleListOrphanCheckArticle + " in its articleList field has a non-nullable idStock field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(stock);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Stock> findStockEntities() {
        return findStockEntities(true, -1, -1);
    }

    public List<Stock> findStockEntities(int maxResults, int firstResult) {
        return findStockEntities(false, maxResults, firstResult);
    }

    private List<Stock> findStockEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Stock.class));
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

    public Stock findStock(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Stock.class, id);
        } finally {
            em.close();
        }
    }

    public int getStockCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Stock> rt = cq.from(Stock.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
