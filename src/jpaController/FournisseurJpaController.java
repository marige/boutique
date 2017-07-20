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
import entities.BonCommande;
import entities.Fournisseur;
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
public class FournisseurJpaController implements Serializable {

    public FournisseurJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fournisseur fournisseur) {
        if (fournisseur.getBonCommandeList() == null) {
            fournisseur.setBonCommandeList(new ArrayList<BonCommande>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<BonCommande> attachedBonCommandeList = new ArrayList<BonCommande>();
            for (BonCommande bonCommandeListBonCommandeToAttach : fournisseur.getBonCommandeList()) {
                bonCommandeListBonCommandeToAttach = em.getReference(bonCommandeListBonCommandeToAttach.getClass(), bonCommandeListBonCommandeToAttach.getIdBonCommande());
                attachedBonCommandeList.add(bonCommandeListBonCommandeToAttach);
            }
            fournisseur.setBonCommandeList(attachedBonCommandeList);
            em.persist(fournisseur);
            for (BonCommande bonCommandeListBonCommande : fournisseur.getBonCommandeList()) {
                Fournisseur oldFournisseuridFournisseurOfBonCommandeListBonCommande = bonCommandeListBonCommande.getFournisseuridFournisseur();
                bonCommandeListBonCommande.setFournisseuridFournisseur(fournisseur);
                bonCommandeListBonCommande = em.merge(bonCommandeListBonCommande);
                if (oldFournisseuridFournisseurOfBonCommandeListBonCommande != null) {
                    oldFournisseuridFournisseurOfBonCommandeListBonCommande.getBonCommandeList().remove(bonCommandeListBonCommande);
                    oldFournisseuridFournisseurOfBonCommandeListBonCommande = em.merge(oldFournisseuridFournisseurOfBonCommandeListBonCommande);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fournisseur fournisseur) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fournisseur persistentFournisseur = em.find(Fournisseur.class, fournisseur.getIdFournisseur());
            List<BonCommande> bonCommandeListOld = persistentFournisseur.getBonCommandeList();
            List<BonCommande> bonCommandeListNew = fournisseur.getBonCommandeList();
            List<String> illegalOrphanMessages = null;
            for (BonCommande bonCommandeListOldBonCommande : bonCommandeListOld) {
                if (!bonCommandeListNew.contains(bonCommandeListOldBonCommande)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BonCommande " + bonCommandeListOldBonCommande + " since its fournisseuridFournisseur field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<BonCommande> attachedBonCommandeListNew = new ArrayList<BonCommande>();
            for (BonCommande bonCommandeListNewBonCommandeToAttach : bonCommandeListNew) {
                bonCommandeListNewBonCommandeToAttach = em.getReference(bonCommandeListNewBonCommandeToAttach.getClass(), bonCommandeListNewBonCommandeToAttach.getIdBonCommande());
                attachedBonCommandeListNew.add(bonCommandeListNewBonCommandeToAttach);
            }
            bonCommandeListNew = attachedBonCommandeListNew;
            fournisseur.setBonCommandeList(bonCommandeListNew);
            fournisseur = em.merge(fournisseur);
            for (BonCommande bonCommandeListNewBonCommande : bonCommandeListNew) {
                if (!bonCommandeListOld.contains(bonCommandeListNewBonCommande)) {
                    Fournisseur oldFournisseuridFournisseurOfBonCommandeListNewBonCommande = bonCommandeListNewBonCommande.getFournisseuridFournisseur();
                    bonCommandeListNewBonCommande.setFournisseuridFournisseur(fournisseur);
                    bonCommandeListNewBonCommande = em.merge(bonCommandeListNewBonCommande);
                    if (oldFournisseuridFournisseurOfBonCommandeListNewBonCommande != null && !oldFournisseuridFournisseurOfBonCommandeListNewBonCommande.equals(fournisseur)) {
                        oldFournisseuridFournisseurOfBonCommandeListNewBonCommande.getBonCommandeList().remove(bonCommandeListNewBonCommande);
                        oldFournisseuridFournisseurOfBonCommandeListNewBonCommande = em.merge(oldFournisseuridFournisseurOfBonCommandeListNewBonCommande);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fournisseur.getIdFournisseur();
                if (findFournisseur(id) == null) {
                    throw new NonexistentEntityException("The fournisseur with id " + id + " no longer exists.");
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
            Fournisseur fournisseur;
            try {
                fournisseur = em.getReference(Fournisseur.class, id);
                fournisseur.getIdFournisseur();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fournisseur with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<BonCommande> bonCommandeListOrphanCheck = fournisseur.getBonCommandeList();
            for (BonCommande bonCommandeListOrphanCheckBonCommande : bonCommandeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Fournisseur (" + fournisseur + ") cannot be destroyed since the BonCommande " + bonCommandeListOrphanCheckBonCommande + " in its bonCommandeList field has a non-nullable fournisseuridFournisseur field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(fournisseur);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fournisseur> findFournisseurEntities() {
        return findFournisseurEntities(true, -1, -1);
    }

    public List<Fournisseur> findFournisseurEntities(int maxResults, int firstResult) {
        return findFournisseurEntities(false, maxResults, firstResult);
    }

    private List<Fournisseur> findFournisseurEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fournisseur.class));
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

    public Fournisseur findFournisseur(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fournisseur.class, id);
        } finally {
            em.close();
        }
    }

    public int getFournisseurCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fournisseur> rt = cq.from(Fournisseur.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
