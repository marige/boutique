/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpaController;

import entitie.BonCommande;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entitie.Fournisseur;
import entitie.DetailBonCommande;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import jpaController.exceptions.IllegalOrphanException;
import jpaController.exceptions.NonexistentEntityException;
import superpackage.SuperClass;

/**
 *
 * @author geres
 */
public class BonCommandeJpaController extends SuperClass implements Serializable {
    EntityManager em=null;
    
    public void create(BonCommande bonCommande) {
        //verificztion si bon de commande a ses details renseignés
        //dans le cas contraire il le renseigne avec du vide
        if (bonCommande.getDetailBonCommandeList() == null) {
            bonCommande.setDetailBonCommandeList(new ArrayList<DetailBonCommande>());
        }
        
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fournisseur idFournisseur = bonCommande.getIdFournisseur();
            //verification si le bon de commande a de fournisseur
            // au cas ou renseigné il recherche les info du fournisseur dans la bdd
            if (idFournisseur != null) {
                idFournisseur = em.getReference(idFournisseur.getClass(), idFournisseur.getIdFournisseur());
                bonCommande.setIdFournisseur(idFournisseur);
            }
            //il prend la liste du detail bon de commande 
            
            List<DetailBonCommande> attachedDetailBonCommandeList = new ArrayList<DetailBonCommande>();
            for (DetailBonCommande detailBonCommandeListDetailBonCommandeToAttach : bonCommande.getDetailBonCommandeList()) {
                //il recherche chaque ligne de detailbon dans la bdd qu'il ajoute attachedDetailBonCommandeList
                detailBonCommandeListDetailBonCommandeToAttach = em.getReference(detailBonCommandeListDetailBonCommandeToAttach.getClass(), detailBonCommandeListDetailBonCommandeToAttach.getIdDetailBonCommande());
                attachedDetailBonCommandeList.add(detailBonCommandeListDetailBonCommandeToAttach);
            }
            bonCommande.setDetailBonCommandeList(attachedDetailBonCommandeList);
            em.persist(bonCommande);
            if (idFournisseur != null) {
                //si le fournisseur existe aussi il ajoute la list des commandes au fournisseur
                idFournisseur.getBonCommandeList().add(bonCommande);
                //mise a jour des modifications du fournisseur 
                idFournisseur = em.merge(idFournisseur);
            }
            //parcours de detailbon et mise a jour dans la bdd
            for (DetailBonCommande detailBonCommandeListDetailBonCommande : bonCommande.getDetailBonCommandeList()) {
                BonCommande oldIdBonCommandeOfDetailBonCommandeListDetailBonCommande = detailBonCommandeListDetailBonCommande.getIdBonCommande();
                detailBonCommandeListDetailBonCommande.setIdBonCommande(bonCommande);
                detailBonCommandeListDetailBonCommande = em.merge(detailBonCommandeListDetailBonCommande);
                if (oldIdBonCommandeOfDetailBonCommandeListDetailBonCommande != null) {
                    oldIdBonCommandeOfDetailBonCommandeListDetailBonCommande.getDetailBonCommandeList().remove(detailBonCommandeListDetailBonCommande);
                    oldIdBonCommandeOfDetailBonCommandeListDetailBonCommande = em.merge(oldIdBonCommandeOfDetailBonCommandeListDetailBonCommande);
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
            Fournisseur idFournisseurOld = persistentBonCommande.getIdFournisseur();
            Fournisseur idFournisseurNew = bonCommande.getIdFournisseur();
            List<DetailBonCommande> detailBonCommandeListOld = persistentBonCommande.getDetailBonCommandeList();
            List<DetailBonCommande> detailBonCommandeListNew = bonCommande.getDetailBonCommandeList();
            List<String> illegalOrphanMessages = null;
            for (DetailBonCommande detailBonCommandeListOldDetailBonCommande : detailBonCommandeListOld) {
                if (!detailBonCommandeListNew.contains(detailBonCommandeListOldDetailBonCommande)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetailBonCommande " + detailBonCommandeListOldDetailBonCommande + " since its idBonCommande field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idFournisseurNew != null) {
                idFournisseurNew = em.getReference(idFournisseurNew.getClass(), idFournisseurNew.getIdFournisseur());
                bonCommande.setIdFournisseur(idFournisseurNew);
            }
            List<DetailBonCommande> attachedDetailBonCommandeListNew = new ArrayList<DetailBonCommande>();
            for (DetailBonCommande detailBonCommandeListNewDetailBonCommandeToAttach : detailBonCommandeListNew) {
                detailBonCommandeListNewDetailBonCommandeToAttach = em.getReference(detailBonCommandeListNewDetailBonCommandeToAttach.getClass(), detailBonCommandeListNewDetailBonCommandeToAttach.getIdDetailBonCommande());
                attachedDetailBonCommandeListNew.add(detailBonCommandeListNewDetailBonCommandeToAttach);
            }
            detailBonCommandeListNew = attachedDetailBonCommandeListNew;
            bonCommande.setDetailBonCommandeList(detailBonCommandeListNew);
            bonCommande = em.merge(bonCommande);
            if (idFournisseurOld != null && !idFournisseurOld.equals(idFournisseurNew)) {
                idFournisseurOld.getBonCommandeList().remove(bonCommande);
                idFournisseurOld = em.merge(idFournisseurOld);
            }
            if (idFournisseurNew != null && !idFournisseurNew.equals(idFournisseurOld)) {
                idFournisseurNew.getBonCommandeList().add(bonCommande);
                idFournisseurNew = em.merge(idFournisseurNew);
            }
            for (DetailBonCommande detailBonCommandeListNewDetailBonCommande : detailBonCommandeListNew) {
                if (!detailBonCommandeListOld.contains(detailBonCommandeListNewDetailBonCommande)) {
                    BonCommande oldIdBonCommandeOfDetailBonCommandeListNewDetailBonCommande = detailBonCommandeListNewDetailBonCommande.getIdBonCommande();
                    detailBonCommandeListNewDetailBonCommande.setIdBonCommande(bonCommande);
                    detailBonCommandeListNewDetailBonCommande = em.merge(detailBonCommandeListNewDetailBonCommande);
                    if (oldIdBonCommandeOfDetailBonCommandeListNewDetailBonCommande != null && !oldIdBonCommandeOfDetailBonCommandeListNewDetailBonCommande.equals(bonCommande)) {
                        oldIdBonCommandeOfDetailBonCommandeListNewDetailBonCommande.getDetailBonCommandeList().remove(detailBonCommandeListNewDetailBonCommande);
                        oldIdBonCommandeOfDetailBonCommandeListNewDetailBonCommande = em.merge(oldIdBonCommandeOfDetailBonCommandeListNewDetailBonCommande);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (IllegalOrphanException ex) {
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
                illegalOrphanMessages.add("This BonCommande (" + bonCommande + ") cannot be destroyed since the DetailBonCommande " + detailBonCommandeListOrphanCheckDetailBonCommande + " in its detailBonCommandeList field has a non-nullable idBonCommande field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Fournisseur idFournisseur = bonCommande.getIdFournisseur();
            if (idFournisseur != null) {
                idFournisseur.getBonCommandeList().remove(bonCommande);
                idFournisseur = em.merge(idFournisseur);
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
