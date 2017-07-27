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
import java.util.ArrayList;
import java.util.List;
import entities.Facture;
import entities.Utilisateur;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpaController.exceptions.IllegalOrphanException;
import jpaController.exceptions.NonexistentEntityException;

/**
 *
 * @author geres
 */
public class UtilisateurJpaController implements Serializable {

    public UtilisateurJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Utilisateur utilisateur) {
        if (utilisateur.getBonCommandeList() == null) {
            utilisateur.setBonCommandeList(new ArrayList<BonCommande>());
        }
        if (utilisateur.getFactureList() == null) {
            utilisateur.setFactureList(new ArrayList<Facture>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<BonCommande> attachedBonCommandeList = new ArrayList<BonCommande>();
            for (BonCommande bonCommandeListBonCommandeToAttach : utilisateur.getBonCommandeList()) {
                bonCommandeListBonCommandeToAttach = em.getReference(bonCommandeListBonCommandeToAttach.getClass(), bonCommandeListBonCommandeToAttach.getIdBonCommande());
                attachedBonCommandeList.add(bonCommandeListBonCommandeToAttach);
            }
            utilisateur.setBonCommandeList(attachedBonCommandeList);
            List<Facture> attachedFactureList = new ArrayList<Facture>();
            for (Facture factureListFactureToAttach : utilisateur.getFactureList()) {
                factureListFactureToAttach = em.getReference(factureListFactureToAttach.getClass(), factureListFactureToAttach.getIdFacture());
                attachedFactureList.add(factureListFactureToAttach);
            }
            utilisateur.setFactureList(attachedFactureList);
            em.persist(utilisateur);
            for (BonCommande bonCommandeListBonCommande : utilisateur.getBonCommandeList()) {
                Utilisateur oldIdUtilisateurOfBonCommandeListBonCommande = bonCommandeListBonCommande.getIdUtilisateur();
                bonCommandeListBonCommande.setIdUtilisateur(utilisateur);
                bonCommandeListBonCommande = em.merge(bonCommandeListBonCommande);
                if (oldIdUtilisateurOfBonCommandeListBonCommande != null) {
                    oldIdUtilisateurOfBonCommandeListBonCommande.getBonCommandeList().remove(bonCommandeListBonCommande);
                    oldIdUtilisateurOfBonCommandeListBonCommande = em.merge(oldIdUtilisateurOfBonCommandeListBonCommande);
                }
            }
            for (Facture factureListFacture : utilisateur.getFactureList()) {
                Utilisateur oldIdUtilisateurOfFactureListFacture = factureListFacture.getIdUtilisateur();
                factureListFacture.setIdUtilisateur(utilisateur);
                factureListFacture = em.merge(factureListFacture);
                if (oldIdUtilisateurOfFactureListFacture != null) {
                    oldIdUtilisateurOfFactureListFacture.getFactureList().remove(factureListFacture);
                    oldIdUtilisateurOfFactureListFacture = em.merge(oldIdUtilisateurOfFactureListFacture);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Utilisateur utilisateur) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Utilisateur persistentUtilisateur = em.find(Utilisateur.class, utilisateur.getIdUtilisateur());
            List<BonCommande> bonCommandeListOld = persistentUtilisateur.getBonCommandeList();
            List<BonCommande> bonCommandeListNew = utilisateur.getBonCommandeList();
            List<Facture> factureListOld = persistentUtilisateur.getFactureList();
            List<Facture> factureListNew = utilisateur.getFactureList();
            List<String> illegalOrphanMessages = null;
            for (BonCommande bonCommandeListOldBonCommande : bonCommandeListOld) {
                if (!bonCommandeListNew.contains(bonCommandeListOldBonCommande)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BonCommande " + bonCommandeListOldBonCommande + " since its idUtilisateur field is not nullable.");
                }
            }
            for (Facture factureListOldFacture : factureListOld) {
                if (!factureListNew.contains(factureListOldFacture)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Facture " + factureListOldFacture + " since its idUtilisateur field is not nullable.");
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
            utilisateur.setBonCommandeList(bonCommandeListNew);
            List<Facture> attachedFactureListNew = new ArrayList<Facture>();
            for (Facture factureListNewFactureToAttach : factureListNew) {
                factureListNewFactureToAttach = em.getReference(factureListNewFactureToAttach.getClass(), factureListNewFactureToAttach.getIdFacture());
                attachedFactureListNew.add(factureListNewFactureToAttach);
            }
            factureListNew = attachedFactureListNew;
            utilisateur.setFactureList(factureListNew);
            utilisateur = em.merge(utilisateur);
            for (BonCommande bonCommandeListNewBonCommande : bonCommandeListNew) {
                if (!bonCommandeListOld.contains(bonCommandeListNewBonCommande)) {
                    Utilisateur oldIdUtilisateurOfBonCommandeListNewBonCommande = bonCommandeListNewBonCommande.getIdUtilisateur();
                    bonCommandeListNewBonCommande.setIdUtilisateur(utilisateur);
                    bonCommandeListNewBonCommande = em.merge(bonCommandeListNewBonCommande);
                    if (oldIdUtilisateurOfBonCommandeListNewBonCommande != null && !oldIdUtilisateurOfBonCommandeListNewBonCommande.equals(utilisateur)) {
                        oldIdUtilisateurOfBonCommandeListNewBonCommande.getBonCommandeList().remove(bonCommandeListNewBonCommande);
                        oldIdUtilisateurOfBonCommandeListNewBonCommande = em.merge(oldIdUtilisateurOfBonCommandeListNewBonCommande);
                    }
                }
            }
            for (Facture factureListNewFacture : factureListNew) {
                if (!factureListOld.contains(factureListNewFacture)) {
                    Utilisateur oldIdUtilisateurOfFactureListNewFacture = factureListNewFacture.getIdUtilisateur();
                    factureListNewFacture.setIdUtilisateur(utilisateur);
                    factureListNewFacture = em.merge(factureListNewFacture);
                    if (oldIdUtilisateurOfFactureListNewFacture != null && !oldIdUtilisateurOfFactureListNewFacture.equals(utilisateur)) {
                        oldIdUtilisateurOfFactureListNewFacture.getFactureList().remove(factureListNewFacture);
                        oldIdUtilisateurOfFactureListNewFacture = em.merge(oldIdUtilisateurOfFactureListNewFacture);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = utilisateur.getIdUtilisateur();
                if (findUtilisateur(id) == null) {
                    throw new NonexistentEntityException("The utilisateur with id " + id + " no longer exists.");
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
            Utilisateur utilisateur;
            try {
                utilisateur = em.getReference(Utilisateur.class, id);
                utilisateur.getIdUtilisateur();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The utilisateur with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<BonCommande> bonCommandeListOrphanCheck = utilisateur.getBonCommandeList();
            for (BonCommande bonCommandeListOrphanCheckBonCommande : bonCommandeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Utilisateur (" + utilisateur + ") cannot be destroyed since the BonCommande " + bonCommandeListOrphanCheckBonCommande + " in its bonCommandeList field has a non-nullable idUtilisateur field.");
            }
            List<Facture> factureListOrphanCheck = utilisateur.getFactureList();
            for (Facture factureListOrphanCheckFacture : factureListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Utilisateur (" + utilisateur + ") cannot be destroyed since the Facture " + factureListOrphanCheckFacture + " in its factureList field has a non-nullable idUtilisateur field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(utilisateur);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Utilisateur> findUtilisateurEntities() {
        return findUtilisateurEntities(true, -1, -1);
    }

    public List<Utilisateur> findUtilisateurEntities(int maxResults, int firstResult) {
        return findUtilisateurEntities(false, maxResults, firstResult);
    }

    private List<Utilisateur> findUtilisateurEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Utilisateur.class));
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

    public Utilisateur findUtilisateur(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Utilisateur.class, id);
        } finally {
            em.close();
        }
    }

    public int getUtilisateurCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Utilisateur> rt = cq.from(Utilisateur.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
