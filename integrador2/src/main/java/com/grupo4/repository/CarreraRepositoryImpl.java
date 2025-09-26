package com.grupo4.repository;

import com.grupo4.dto.ReporteCarreraDTO;
import com.grupo4.factory.JPAutil;
import com.grupo4.model.Carrera;

import javax.persistence.EntityManager;
import java.util.List;

public class CarreraRepositoryImpl implements CarreraRepository{
    @Override
    public void cargarCarreras(List<Carrera> carreras) {
        try{
            EntityManager em = JPAutil.getEntityManager();
            em.getTransaction().begin();
            for(Carrera carrera:carreras){
                em.persist(carrera);
            }
            em.getTransaction().commit();
            em.close();
        }catch (Exception e){
            throw new RuntimeException("error al cargar las carreras",e);
        }
    }

    @Override
    public Carrera findById(long id) {
        EntityManager em = JPAutil.getEntityManager();
        Carrera carrera = em.find(Carrera.class,id);
        em.close();
        return carrera;
    }

    @Override
    public void crearCarrera(String nombre, int duracion) {
        EntityManager em = JPAutil.getEntityManager();
        try {
            em.getTransaction().begin();
            Carrera carrera = new Carrera(nombre, duracion);
            em.persist(carrera);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error al crear la carrera", e);
        } finally {
            em.close();
        }


    }

    @Override
    public Carrera getCarreraByName(String nombreCarrera) {
        EntityManager em = JPAutil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Carrera c WHERE c.nombre = :nombre", Carrera.class)
                    .setParameter("nombre", nombreCarrera)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la carrera por nombre", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<ReporteCarreraDTO> getReporteCarreras() {
        EntityManager em = JPAutil.getEntityManager();
        try {
            // Corrección: Usar COUNT(ec.id) para ser explícito
            String jpql = "SELECT new com.grupo4.dto.ReporteCarreraDTO(c.nombre, COUNT(ec.id)) " +
                    "FROM Carrera c JOIN c.estudiantes ec " +
                    "GROUP BY c.nombre " +
                    "ORDER BY COUNT(ec.id) DESC";
            return em.createQuery(jpql, ReporteCarreraDTO.class).getResultList();
        } finally {
            em.close();
        }
    }
}
