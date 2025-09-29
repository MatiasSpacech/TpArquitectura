package com.grupo4.repository;

import com.grupo4.dto.ReporteCarreraDTO;
import com.grupo4.factory.JPAutil;
import com.grupo4.model.EstudianteCarrera;

import javax.persistence.EntityManager;
import java.util.List;

public class EstudianteCarreraRepositoryImpl implements EstudianteCarreraRepository {

    //PUNTO b)
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
    public List<ReporteCarreraDTO> getReportesCarreras() {
        EntityManager em = JPAutil.getEntityManager();
        String jpql = "SELECT new com.grupo4.dto.ReporteCarreraDTO(ec.fechaGraduacion,c.nombre,COUNT(ec),(SELECT COUNT(ec2) FROM EstudianteCarrera ec2 WHERE ec2.carrera.nombre = c.nombre) ) " +
                    "FROM EstudianteCarrera ec " +
                    "JOIN ec.carrera c " +
                    "WHERE ec.fechaGraduacion != 0 " +
                    "GROUP BY ec.fechaGraduacion,c.nombre " +
                    "ORDER BY c.nombre,ec.fechaGraduacion";
        List<ReporteCarreraDTO> reportes = em.createQuery(jpql,ReporteCarreraDTO.class).getResultList();
        em.close();
        return reportes;
    }

    //Cargar matriculas csv
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
