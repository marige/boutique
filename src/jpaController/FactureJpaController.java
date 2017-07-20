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
import entities.Utilisateur;
import entities.DetailFacture;
import entities.Facture;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpaController.exceptions.IllegalOrphanException;
import jpaController.exceptions.NonexistentEntityException;

/**
 *
 * @author geres
 */
public class FactureJpaController implements Serializable {

    public FactureJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Facture facture) {
        if (facture.getDetailFactureList() == null) {
            facture.setDetailFactureList(new ArrayList<DetailFacture>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Utilisateur idUtilisateur = facture.getIdUtilisateur();
            if (idUtilisateur != null) {
                idUtilisateur = em.getReference(idUtilisateur.getClass(), idUtilisateur.getIdUtilisateur());
                facture.setIdUtilisateur(idUtilisateur);
            }
            List<DetailFacture> attachedDetailFactureList = new ArrayList<DetailFacture>();
            for (DetailFacture detailFactureListDetailFactureToAttach : facture.getDetailFactureList()) {
                detailFactureListDetailFactureToAttach = em.getReference(detailFactureListDetailFactureToAttach.getClass(), detailFactureListDetailFactureToAttach.getIddetailFacture());
                attachedDetailFactureList.add(detailFactureListDetailFactureToAttach);
            }
            facture.setDetailFactureList(attachedDetailFactureList);
            em.persist(facture);
            if (idUtilisateur != null) {
                idUtilisateur.getFactureList().add(facture);
                idUtilisateur = em.merge(idUtilisateur);
            }
            for (DetailFacture detailFactureListDetailFacture : facture.getDetailFactureList()) {
                Facture oldIdFactureOfDetailFactureListDetailFacture = detailFactureListDetailFacture.getIdFacture();
                detailFactureListDetailFacture.setIdFacture(facture);
                detailFactureListDetailFacture = em.merge(detailFactureListDetailFacture);
                if (oldIdFactureOfDetailFactureListDetailFacture != null) {
                    oldIdFactureOfDetailFactureListDetailFacture.getDetailFactureList().remove(detailFactureListDetailFacture);
                    oldIdFactureOfDetailFactureListDetailFacture = em.merge(oldIdFactureOfDetailFactureListDetailFacture);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Facture facture) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Facture persistentFacture = em.find(Facture.class, facture.getIdFacture());
            Utilisateur idUtilisateurOld = persistentFacture.getIdUtilisateur();
            Utilisateur idUtilisateurNew = facture.getIdUtilisateur();
            List<DetailFacture> detailFactureListOld = persistentFacture.getDetailFactureList();
            List<DetailFacture> detailFactureListNew = facture.getDetailFactureList();
            List<String> illegalOrphanMessages = null;
            for (DetailFacture detailFactureListOldDetailFacture : detailFactureListOld) {
                if (!detailFactureListNew.contains(detailFactureListOldDetailFacture)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetailFacture " + detailFactureListOldDetailFacture + " since its idFacture field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idUtilisateurNew != null) {
                idUtilisateurNew = em.getReference(idUtilisateurNew.getClass(), idUtilisateurNew.getIdUtilisateur());
                facture.setIdUtilisateur(idUtilisateurNew);
            }
            List<DetailFacture> attachedDetailFactureListNew = new ArrayList<DetailFacture>();
            for (DetailFacture detailFactureListNewDetailFactureToAttach : detailFactureListNew) {
                detailFactureListNewDetailFactureToAttach = em.getReference(detailFactureListNewDetailFactureToAttach.getClass(), detailFactureListNewDetailFactureToAttach.getIddetailFacture());
                attachedDetailFactureListNew.add(detailFactureListNewDetailFactureToAttach);
            }
            detailFactureListNew = attachedDetailFactureListNew;
            facture.setDetailFactureList(detailFactureListNew);
            facture = em.merge(facture);
            if (idUtilisateurOld != null && !idUtilisateurOld.equals(idUtilisateurNew)) {
                idUtilisateurOld.getFactureList().remove(facture);
                idUtilisateurOld = em.merge(idUtilisateurOld);
            }
            if (idUtilisateurNew != null && !idUtilisateurNew.equals(idUtilisateurOld)) {
                idUtilisateurNew.getFactureList().add(facture);
                idUtilisateurNew = em.merge(idUtilisateurNew);
            }
            for (DetailFacture detailFactureListNewDetailFacture : detailFactureListNew) {
                if (!detailFactureListOld.contains(detailFactureListNewDetailFacture)) {
                    Facture oldIdFactureOfDetailFactureListNewDetailFacture = detailFactureListNewDetailFacture.getIdFacture();
                    detailFactureListNewDetailFacture.setIdFacture(facture);
                    detailFactureListNewDetailFacture = em.merge(detailFactureListNewDetailFacture);
                    if (oldIdFactureOfDetailFactureListNewDetailFacture != null && !oldIdFactureOfDetailFactureListNewDetailFacture.equals(facture)) {
                        oldIdFactureOfDetailFactureListNewDetailFacture.getDetailFactureList().remove(detailFactureListNewDetailFacture);
                        oldIdFactureOfDetailFactureListNewDetailFacture = em.merge(oldIdFactureOfDetailFactureListNewDetailFacture);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facture.getIdFacture();
                if (findFacture(id) == null) {
                    throw new NonexistentEntityException("The facture with id " + id + " no longer exists.");
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
            Facture facture;
            try {
                facture = em.getReference(Facture.class, id);
                facture.getIdFacture();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facture with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetailFacture> detailFactureListOrphanCheck = facture.getDetailFactureList();
            for (DetailFacture detailFactureListOrphanCheckDetailFacture : detailFactureListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Facture (" + facture + ") cannot be destroyed since the DetailFacture " + detailFactureListOrphanCheckDetailFacture + " in its detailFactureList field has a non-nullable idFacture field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Utilisateur idUtilisateur = facture.getIdUtilisateur();
            if (idUtilisateur != null) {
                idUtilisateur.getFactureList().remove(facture);
                idUtilisateur = em.merge(idUtilisateur);
            }
            em.remove(facture);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Facture> findFactureEntities() {
        return findFactureEntities(true, -1, -1);
    }

    public List<Facture> findFactureEntities(int maxResults, int firstResult) {
        return findFactureEntities(false, maxResults, firstResult);
    }

    private List<Facture> findFactureEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Facture.class));
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

    public Facture findFacture(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Facture.class, id);
        } finally {
            em.close();
        }
    }

    public int getFactureCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Facture> rt = cq.from(Facture.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
