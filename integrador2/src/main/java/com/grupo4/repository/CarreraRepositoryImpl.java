package com.grupo4.repository;

import com.grupo4.dto.CarreraDTO;
import com.grupo4.factory.JPAutil;
import com.grupo4.model.Carrera;

import javax.persistence.EntityManager;
import java.util.List;

public class CarreraRepositoryImpl implements CarreraRepository{

    //Cargar carreras csv
    @Override
    public void addCarreras(List<Carrera> carreras) {
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

    //PUNTO f)
    @Override
    public List<CarreraDTO> getCarrerasConEstudiantes() {
        EntityManager em = JPAutil.getEntityManager();
        String jpql = "SELECT new com.grupo4.dto.CarreraDTO(c.nombre, c.duracion) " +
                      "FROM Carrera c " +
                      "WHERE c.estudiantes IS NOT EMPTY " +
                      "ORDER BY SIZE(c.estudiantes) ASC";
        List<CarreraDTO> carreras = em.createQuery(jpql, CarreraDTO.class).getResultList();
        em.close();

        return carreras;
    }

    //Buscar carrera por id
    @Override
    public Carrera findById(int id) {
        EntityManager em = JPAutil.getEntityManager();
        Carrera carrera = em.find(Carrera.class,id);
        em.close();
        return carrera;
    }
}
