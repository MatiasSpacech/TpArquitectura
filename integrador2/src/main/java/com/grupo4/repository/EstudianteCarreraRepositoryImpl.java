package com.grupo4.repository;

import com.grupo4.factory.JPAutil;
import com.grupo4.model.EstudianteCarrera;

import javax.persistence.EntityManager;
import java.util.List;

public class EstudianteCarreraRepositoryImpl implements EstudiantaCarreraRepository{
    @Override
    public void matricularEstudiante(EstudianteCarrera estudianteCarrera) {
        try {
            EntityManager em = JPAutil.getEntityManager();
            em.getTransaction().begin();
            em.persist(estudianteCarrera);
            em.getTransaction().commit();
            em.close();
        }catch (Exception e){
            throw new RuntimeException("error al matricular el estudiante",e);
        }
    }

    @Override
    public void matricularEstudiantes(List<EstudianteCarrera> estudiantesCarrera) {
        try {
            EntityManager em = JPAutil.getEntityManager();
            em.getTransaction().begin();
            for(EstudianteCarrera estudianteCarrera:estudiantesCarrera){
                em.persist(estudianteCarrera);
            }
            em.getTransaction().commit();
            em.close();
        }catch (Exception e){
            throw new RuntimeException("error al matricular los estudiantes",e);
        }
    }
}
