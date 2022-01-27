/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ies.javafxdiccionario2.controller;

import com.ies.javafxdiccionario2.controller.exceptions.NonexistentEntityException;
import com.ies.javafxdiccionario2.model.Palabra;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author usuario
 */
public class PalabraJpaController implements Serializable {

    public PalabraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Palabra palabra) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(palabra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public void darBaja(Palabra palabra) {
    
    }
    
    public Palabra findPalabraNombre(String nombre) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Palabra.class, nombre);
        } finally {
            em.close();
        }
    }
    
    public void edit(Palabra palabra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            palabra = em.merge(palabra);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = palabra.getId();
                if (findPalabra(id) == null) {
                    throw new NonexistentEntityException("The palabra with id " + id + " no longer exists.");
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
            Palabra palabra;
            try {
                palabra = em.getReference(Palabra.class, id);
                palabra.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The palabra with id " + id + " no longer exists.", enfe);
            }
            em.remove(palabra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Palabra> findPalabraEntities() {
        return findPalabraEntities(true, -1, -1);
    }

    public List<Palabra> findPalabraEntities(int maxResults, int firstResult) {
        return findPalabraEntities(false, maxResults, firstResult);
    }

    private List<Palabra> findPalabraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Palabra.class));
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

    public Palabra findPalabra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Palabra.class, id);
        } finally {
            em.close();
        }
    }


    public int getPalabraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Palabra> rt = cq.from(Palabra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
