package com.grupo4.repository;

import com.grupo4.dto.ReporteCarreraDTO;
import com.grupo4.factory.JPAutil;
import com.grupo4.model.Carrera;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarreraRepositoryImpl implements CarreraRepository{
    @Override
    public void cargarCarreras(List<Carrera> carreras) {
        EntityManager em = JPAutil.getEntityManager();
        try{
            em.getTransaction().begin();
            for(Carrera carrera:carreras){
                em.persist(carrera);
            }
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
            throw new RuntimeException("error al cargar las carreras",e);
        } finally {
            em.close();
        }
    }

    @Override
    public Carrera findById(long id) {
        EntityManager em = JPAutil.getEntityManager();
        try {
            return em.find(Carrera.class, id);
        } finally {
            em.close();
        }
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
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
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
        } finally {
            em.close();
        }
    }

    @Override
    public List<ReporteCarreraDTO> getReporteCarreras() {
        EntityManager em = JPAutil.getEntityManager();
        try {
            String jpql = "SELECT new com.grupo4.dto.ReporteCarreraDTO(c.nombre, COUNT(ec.id)) " +
                    "FROM Carrera c JOIN c.estudiantes ec " +
                    "GROUP BY c.nombre " +
                    "ORDER BY COUNT(ec.id) DESC";
            return em.createQuery(jpql, ReporteCarreraDTO.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<ReporteCarreraDTO> getInscriptosYEgresadosPorAnio() {
        EntityManager em = JPAutil.getEntityManager();
        try {
            String jpqlInscriptos = "SELECT c.nombre, ec.fechaIngreso, COUNT(ec) " +
                    "FROM EstudianteCarrera ec JOIN ec.carrera c " +
                    "GROUP BY c.nombre, ec.fechaIngreso";
            List<Object[]> inscripciones = em.createQuery(jpqlInscriptos).getResultList();

            String jpqlEgresados = "SELECT c.nombre, ec.fechaGraduacion, COUNT(ec) " +
                    "FROM EstudianteCarrera ec JOIN ec.carrera c " +
                    "WHERE ec.graduado = true " +
                    "GROUP BY c.nombre, ec.fechaGraduacion";
            List<Object[]> egresados = em.createQuery(jpqlEgresados).getResultList();

            java.util.Map<String, java.util.Map<Integer, ReporteCarreraDTO>> reporteMap = new java.util.TreeMap<>();

            for (Object[] result : inscripciones) {
                String nombreCarrera = (String) result[0];
                Integer anio = (Integer) result[1];
                Long cantInscriptos = (Long) result[2];
                Long cantEgresados = 0L;

                reporteMap.computeIfAbsent(nombreCarrera, k -> new java.util.TreeMap<>())
                        .put(anio, new ReporteCarreraDTO(nombreCarrera, cantInscriptos, anio, cantEgresados));
            }

            for (Object[] result : egresados) {
                String nombreCarrera = (String) result[0];
                Integer anio = (Integer) result[1];
                Long cantInscriptos = (Long) result[2];
                Long cantEgresados = 0L;

                java.util.Map<Integer, ReporteCarreraDTO> anioMap = reporteMap.computeIfAbsent(nombreCarrera, k -> new java.util.TreeMap<>());
                ReporteCarreraDTO reporteAnual = anioMap.get(anio);
                if (reporteAnual != null) {
                    reporteAnual.setCantEgresados(cantEgresados);
                } else {
                    anioMap.put(anio, new ReporteCarreraDTO(nombreCarrera, cantInscriptos, anio, cantEgresados));
                }
            }

            return reporteMap.values().stream()
                    .flatMap(map -> map.values().stream())
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Error al generar reporte de carreras: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
}
