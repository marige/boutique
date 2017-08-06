/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package categorie;

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
public class CategorieJpaController extends SuperClass implements Serializable {
     EntityManager em=null;
      
    public void create(Categorie categorie) {
        try {
            em=getEntityManager();
            em.getTransaction().begin();
            em.persist(categorie);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categorie categorie) throws Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            categorie = em.merge(categorie);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categorie.getIdCategorie();        
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
            Categorie categorie;
            try {
                categorie = em.getReference(Categorie.class, id);
                categorie.getIdCategorie();
            
            em.remove(categorie);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categorie> findCategorieEntities() {
        return findCategorieEntities(true, -1, -1);
    }

    public List<Categorie> findCategorieEntities(int maxResults, int firstResult) {
        return findCategorieEntities(false, maxResults, firstResult);
    }

    private List<Categorie> findCategorieEntities(boolean all, int maxResults, int firstResult) {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categorie.class));
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

    public Categorie findCategorie(Integer id) {
         em = getEntityManager();
        try {
            return em.find(Categorie.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategorieCount() {
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
