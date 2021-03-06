/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detailboncommande;

import boncommande.BonCommande;
import exceptions.NonexistentEntityException;
import facture.Facture;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import superpackage.SuperClass;
import vente.Vente;

public class DetailBonCommandeJpaController extends SuperClass implements Serializable {

    public void create(DetailBonCommande detailBonCommande) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(detailBonCommande);
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
            detailBonCommande = em.merge(detailBonCommande);
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
    
     public List<DetailBonCommande> getListDetailParBon(BonCommande b){
        EntityManager  em = getEntityManager();
        List<DetailBonCommande> l=  em.createNamedQuery("listDetailParCommande")
                .setParameter("idboncommande",b.getIdBonCommande())
                .getResultList();
        em.close();
       return l;
    }
    
    
    
}
