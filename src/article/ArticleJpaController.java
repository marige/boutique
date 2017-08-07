/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package article;

import categorie.Categorie;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import superpackage.SuperClass;

/**
 *
 * @author OBAM
 */
public class ArticleJpaController extends SuperClass implements Serializable{
      
     EntityManager em=null;
     public ArticleJpaController() {
    }

    public void create(Article article) {
        try {
            em=this.getEntityManager();
            em.getTransaction().begin();
            em.persist(article);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Article article) throws Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            article = em.merge(article);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = article.getIdarticle();        
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws Exception{
            em = getEntityManager();
            em.getTransaction().begin();
            Article article;
            try {
                article = em.getReference(Article.class, id);
                article.getIdarticle();
            
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
              em = getEntityManager();
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
        em = getEntityManager();
        try {
            return em.find(Article.class, id);
        } finally {
            em.close();
        }
    }

    public int getArticleCount() {
         em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categorie> rt = cq.from(Categorie.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
