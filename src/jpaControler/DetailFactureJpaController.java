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
import entities.Facture;
import entities.Article;
import entities.DetailFacture;
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
public class DetailFactureJpaController implements Serializable {

    public DetailFactureJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetailFacture detailFacture) {
        if (detailFacture.getArticleList() == null) {
            detailFacture.setArticleList(new ArrayList<Article>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Facture idFacture = detailFacture.getIdFacture();
            if (idFacture != null) {
                idFacture = em.getReference(idFacture.getClass(), idFacture.getIdFacture());
                detailFacture.setIdFacture(idFacture);
            }
            List<Article> attachedArticleList = new ArrayList<Article>();
            for (Article articleListArticleToAttach : detailFacture.getArticleList()) {
                articleListArticleToAttach = em.getReference(articleListArticleToAttach.getClass(), articleListArticleToAttach.getIdArticle());
                attachedArticleList.add(articleListArticleToAttach);
            }
            detailFacture.setArticleList(attachedArticleList);
            em.persist(detailFacture);
            if (idFacture != null) {
                idFacture.getDetailFactureList().add(detailFacture);
                idFacture = em.merge(idFacture);
            }
            for (Article articleListArticle : detailFacture.getArticleList()) {
                DetailFacture oldIddetailFactureOfArticleListArticle = articleListArticle.getIddetailFacture();
                articleListArticle.setIddetailFacture(detailFacture);
                articleListArticle = em.merge(articleListArticle);
                if (oldIddetailFactureOfArticleListArticle != null) {
                    oldIddetailFactureOfArticleListArticle.getArticleList().remove(articleListArticle);
                    oldIddetailFactureOfArticleListArticle = em.merge(oldIddetailFactureOfArticleListArticle);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetailFacture detailFacture) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetailFacture persistentDetailFacture = em.find(DetailFacture.class, detailFacture.getIddetailFacture());
            Facture idFactureOld = persistentDetailFacture.getIdFacture();
            Facture idFactureNew = detailFacture.getIdFacture();
            List<Article> articleListOld = persistentDetailFacture.getArticleList();
            List<Article> articleListNew = detailFacture.getArticleList();
            List<String> illegalOrphanMessages = null;
            for (Article articleListOldArticle : articleListOld) {
                if (!articleListNew.contains(articleListOldArticle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Article " + articleListOldArticle + " since its iddetailFacture field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idFactureNew != null) {
                idFactureNew = em.getReference(idFactureNew.getClass(), idFactureNew.getIdFacture());
                detailFacture.setIdFacture(idFactureNew);
            }
            List<Article> attachedArticleListNew = new ArrayList<Article>();
            for (Article articleListNewArticleToAttach : articleListNew) {
                articleListNewArticleToAttach = em.getReference(articleListNewArticleToAttach.getClass(), articleListNewArticleToAttach.getIdArticle());
                attachedArticleListNew.add(articleListNewArticleToAttach);
            }
            articleListNew = attachedArticleListNew;
            detailFacture.setArticleList(articleListNew);
            detailFacture = em.merge(detailFacture);
            if (idFactureOld != null && !idFactureOld.equals(idFactureNew)) {
                idFactureOld.getDetailFactureList().remove(detailFacture);
                idFactureOld = em.merge(idFactureOld);
            }
            if (idFactureNew != null && !idFactureNew.equals(idFactureOld)) {
                idFactureNew.getDetailFactureList().add(detailFacture);
                idFactureNew = em.merge(idFactureNew);
            }
            for (Article articleListNewArticle : articleListNew) {
                if (!articleListOld.contains(articleListNewArticle)) {
                    DetailFacture oldIddetailFactureOfArticleListNewArticle = articleListNewArticle.getIddetailFacture();
                    articleListNewArticle.setIddetailFacture(detailFacture);
                    articleListNewArticle = em.merge(articleListNewArticle);
                    if (oldIddetailFactureOfArticleListNewArticle != null && !oldIddetailFactureOfArticleListNewArticle.equals(detailFacture)) {
                        oldIddetailFactureOfArticleListNewArticle.getArticleList().remove(articleListNewArticle);
                        oldIddetailFactureOfArticleListNewArticle = em.merge(oldIddetailFactureOfArticleListNewArticle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detailFacture.getIddetailFacture();
                if (findDetailFacture(id) == null) {
                    throw new NonexistentEntityException("The detailFacture with id " + id + " no longer exists.");
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
            DetailFacture detailFacture;
            try {
                detailFacture = em.getReference(DetailFacture.class, id);
                detailFacture.getIddetailFacture();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detailFacture with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Article> articleListOrphanCheck = detailFacture.getArticleList();
            for (Article articleListOrphanCheckArticle : articleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This DetailFacture (" + detailFacture + ") cannot be destroyed since the Article " + articleListOrphanCheckArticle + " in its articleList field has a non-nullable iddetailFacture field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Facture idFacture = detailFacture.getIdFacture();
            if (idFacture != null) {
                idFacture.getDetailFactureList().remove(detailFacture);
                idFacture = em.merge(idFacture);
            }
            em.remove(detailFacture);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetailFacture> findDetailFactureEntities() {
        return findDetailFactureEntities(true, -1, -1);
    }

    public List<DetailFacture> findDetailFactureEntities(int maxResults, int firstResult) {
        return findDetailFactureEntities(false, maxResults, firstResult);
    }

    private List<DetailFacture> findDetailFactureEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetailFacture.class));
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

    public DetailFacture findDetailFacture(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetailFacture.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetailFactureCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetailFacture> rt = cq.from(DetailFacture.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
