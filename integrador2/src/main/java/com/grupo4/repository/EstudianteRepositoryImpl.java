package com.grupo4.repository;

import com.grupo4.factory.JPAutil;
import com.grupo4.model.Estudiante;

import javax.persistence.EntityManager;
import java.util.List;

public class EstudianteRepositoryImpl implements EstudianteRepository{
    @Override
    public void cargarEstudiantes(List<Estudiante> estudiantes) {
        EntityManager em = JPAutil.getEntityManager();
        em.getTransaction().begin();
        for(Estudiante estudiante:estudiantes){
            em.persist(estudiante);
        }
        em.getTransaction().commit();
        em.close();
    }
}
