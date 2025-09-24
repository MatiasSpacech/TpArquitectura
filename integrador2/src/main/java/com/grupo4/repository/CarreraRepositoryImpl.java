package com.grupo4.repository;

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
}
