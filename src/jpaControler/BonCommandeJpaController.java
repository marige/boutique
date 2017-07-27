/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpaControler;

import entities.BonCommande;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Utilisateur;
import entities.Fournisseur;
import entities.DetailBonCommande;
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
public class BonCommandeJpaController implements Serializable {

    public BonCommandeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BonCommande bonCommande) {
        if (bonCommande.getDetailBonCommandeList() == null) {
            bonCommande.setDetailBonCommandeList(new ArrayList<DetailBonCommande>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Utilisateur idUtilisateur = bonCommande.getIdUtilisateur();
            if (idUtilisateur != null) {
                idUtilisateur = em.getReference(idUtilisateur.getClass(), idUtilisateur.getIdUtilisateur());
                bonCommande.setIdUtilisateur(idUtilisateur);
            }
            Fournisseur fournisseuridFournisseur = bonCommande.getFournisseuridFournisseur();
            if (fournisseuridFournisseur != null) {
                fournisseuridFournisseur = em.getReference(fournisseuridFournisseur.getClass(), fournisseuridFournisseur.getIdFournisseur());
                bonCommande.setFournisseuridFournisseur(fournisseuridFournisseur);
            }
            List<DetailBonCommande> attachedDetailBonCommandeList = new ArrayList<DetailBonCommande>();
            for (DetailBonCommande detailBonCommandeListDetailBonCommandeToAttach : bonCommande.getDetailBonCommandeList()) {
                detailBonCommandeListDetailBonCommandeToAttach = em.getReference(detailBonCommandeListDetailBonCommandeToAttach.getClass(), detailBonCommandeListDetailBonCommandeToAttach.getIdDetailBonCommande());
                attachedDetailBonCommandeList.add(detailBonCommandeListDetailBonCommandeToAttach);
            }
            bonCommande.setDetailBonCommandeList(attachedDetailBonCommandeList);
            em.persist(bonCommande);
            if (idUtilisateur != null) {
                idUtilisateur.getBonCommandeList().add(bonCommande);
                idUtilisateur = em.merge(idUtilisateur);
            }
            if (fournisseuridFournisseur != null) {
                fournisseuridFournisseur.getBonCommandeList().add(bonCommande);
                fournisseuridFournisseur = em.merge(fournisseuridFournisseur);
            }
            for (DetailBonCommande detailBonCommandeListDetailBonCommande : bonCommande.getDetailBonCommandeList()) {
                BonCommande oldBonCommandeidBonCommandeOfDetailBonCommandeListDetailBonCommande = detailBonCommandeListDetailBonCommande.getBonCommandeidBonCommande();
                detailBonCommandeListDetailBonCommande.setBonCommandeidBonCommande(bonCommande);
                detailBonCommandeListDetailBonCommande = em.merge(detailBonCommandeListDetailBonCommande);
                if (oldBonCommandeidBonCommandeOfDetailBonCommandeListDetailBonCommande != null) {
                    oldBonCommandeidBonCommandeOfDetailBonCommandeListDetailBonCommande.getDetailBonCommandeList().remove(detailBonCommandeListDetailBonCommande);
                    oldBonCommandeidBonCommandeOfDetailBonCommandeListDetailBonCommande = em.merge(oldBonCommandeidBonCommandeOfDetailBonCommandeListDetailBonCommande);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BonCommande bonCommande) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BonCommande persistentBonCommande = em.find(BonCommande.class, bonCommande.getIdBonCommande());
            Utilisateur idUtilisateurOld = persistentBonCommande.getIdUtilisateur();
            Utilisateur idUtilisateurNew = bonCommande.getIdUtilisateur();
            Fournisseur fournisseuridFournisseurOld = persistentBonCommande.getFournisseuridFournisseur();
            Fournisseur fournisseuridFournisseurNew = bonCommande.getFournisseuridFournisseur();
            List<DetailBonCommande> detailBonCommandeListOld = persistentBonCommande.getDetailBonCommandeList();
            List<DetailBonCommande> detailBonCommandeListNew = bonCommande.getDetailBonCommandeList();
            List<String> illegalOrphanMessages = null;
            for (DetailBonCommande detailBonCommandeListOldDetailBonCommande : detailBonCommandeListOld) {
                if (!detailBonCommandeListNew.contains(detailBonCommandeListOldDetailBonCommande)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetailBonCommande " + detailBonCommandeListOldDetailBonCommande + " since its bonCommandeidBonCommande field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idUtilisateurNew != null) {
                idUtilisateurNew = em.getReference(idUtilisateurNew.getClass(), idUtilisateurNew.getIdUtilisateur());
                bonCommande.setIdUtilisateur(idUtilisateurNew);
            }
            if (fournisseuridFournisseurNew != null) {
                fournisseuridFournisseurNew = em.getReference(fournisseuridFournisseurNew.getClass(), fournisseuridFournisseurNew.getIdFournisseur());
                bonCommande.setFournisseuridFournisseur(fournisseuridFournisseurNew);
            }
            List<DetailBonCommande> attachedDetailBonCommandeListNew = new ArrayList<DetailBonCommande>();
            for (DetailBonCommande detailBonCommandeListNewDetailBonCommandeToAttach : detailBonCommandeListNew) {
                detailBonCommandeListNewDetailBonCommandeToAttach = em.getReference(detailBonCommandeListNewDetailBonCommandeToAttach.getClass(), detailBonCommandeListNewDetailBonCommandeToAttach.getIdDetailBonCommande());
                attachedDetailBonCommandeListNew.add(detailBonCommandeListNewDetailBonCommandeToAttach);
            }
            detailBonCommandeListNew = attachedDetailBonCommandeListNew;
            bonCommande.setDetailBonCommandeList(detailBonCommandeListNew);
            bonCommande = em.merge(bonCommande);
            if (idUtilisateurOld != null && !idUtilisateurOld.equals(idUtilisateurNew)) {
                idUtilisateurOld.getBonCommandeList().remove(bonCommande);
                idUtilisateurOld = em.merge(idUtilisateurOld);
            }
            if (idUtilisateurNew != null && !idUtilisateurNew.equals(idUtilisateurOld)) {
                idUtilisateurNew.getBonCommandeList().add(bonCommande);
                idUtilisateurNew = em.merge(idUtilisateurNew);
            }
            if (fournisseuridFournisseurOld != null && !fournisseuridFournisseurOld.equals(fournisseuridFournisseurNew)) {
                fournisseuridFournisseurOld.getBonCommandeList().remove(bonCommande);
                fournisseuridFournisseurOld = em.merge(fournisseuridFournisseurOld);
            }
            if (fournisseuridFournisseurNew != null && !fournisseuridFournisseurNew.equals(fournisseuridFournisseurOld)) {
                fournisseuridFournisseurNew.getBonCommandeList().add(bonCommande);
                fournisseuridFournisseurNew = em.merge(fournisseuridFournisseurNew);
            }
            for (DetailBonCommande detailBonCommandeListNewDetailBonCommande : detailBonCommandeListNew) {
                if (!detailBonCommandeListOld.contains(detailBonCommandeListNewDetailBonCommande)) {
                    BonCommande oldBonCommandeidBonCommandeOfDetailBonCommandeListNewDetailBonCommande = detailBonCommandeListNewDetailBonCommande.getBonCommandeidBonCommande();
                    detailBonCommandeListNewDetailBonCommande.setBonCommandeidBonCommande(bonCommande);
                    detailBonCommandeListNewDetailBonCommande = em.merge(detailBonCommandeListNewDetailBonCommande);
                    if (oldBonCommandeidBonCommandeOfDetailBonCommandeListNewDetailBonCommande != null && !oldBonCommandeidBonCommandeOfDetailBonCommandeListNewDetailBonCommande.equals(bonCommande)) {
                        oldBonCommandeidBonCommandeOfDetailBonCommandeListNewDetailBonCommande.getDetailBonCommandeList().remove(detailBonCommandeListNewDetailBonCommande);
                        oldBonCommandeidBonCommandeOfDetailBonCommandeListNewDetailBonCommande = em.merge(oldBonCommandeidBonCommandeOfDetailBonCommandeListNewDetailBonCommande);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bonCommande.getIdBonCommande();
                if (findBonCommande(id) == null) {
                    throw new NonexistentEntityException("The bonCommande with id " + id + " no longer exists.");
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
            BonCommande bonCommande;
            try {
                bonCommande = em.getReference(BonCommande.class, id);
                bonCommande.getIdBonCommande();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bonCommande with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetailBonCommande> detailBonCommandeListOrphanCheck = bonCommande.getDetailBonCommandeList();
            for (DetailBonCommande detailBonCommandeListOrphanCheckDetailBonCommande : detailBonCommandeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This BonCommande (" + bonCommande + ") cannot be destroyed since the DetailBonCommande " + detailBonCommandeListOrphanCheckDetailBonCommande + " in its detailBonCommandeList field has a non-nullable bonCommandeidBonCommande field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Utilisateur idUtilisateur = bonCommande.getIdUtilisateur();
            if (idUtilisateur != null) {
                idUtilisateur.getBonCommandeList().remove(bonCommande);
                idUtilisateur = em.merge(idUtilisateur);
            }
            Fournisseur fournisseuridFournisseur = bonCommande.getFournisseuridFournisseur();
            if (fournisseuridFournisseur != null) {
                fournisseuridFournisseur.getBonCommandeList().remove(bonCommande);
                fournisseuridFournisseur = em.merge(fournisseuridFournisseur);
            }
            em.remove(bonCommande);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BonCommande> findBonCommandeEntities() {
        return findBonCommandeEntities(true, -1, -1);
    }

    public List<BonCommande> findBonCommandeEntities(int maxResults, int firstResult) {
        return findBonCommandeEntities(false, maxResults, firstResult);
    }

    private List<BonCommande> findBonCommandeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BonCommande.class));
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

    public BonCommande findBonCommande(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BonCommande.class, id);
        } finally {
            em.close();
        }
    }

    public int getBonCommandeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BonCommande> rt = cq.from(BonCommande.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
