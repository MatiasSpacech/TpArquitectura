package com.grupo4.repository;

import com.grupo4.dto.EstudianteDTO;
import com.grupo4.factory.JPAutil;
import com.grupo4.model.Estudiante;
import com.ibm.icu.text.CaseMap;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EstudianteRepositoryImpl implements EstudianteRepository{
    //Lista de campos de ordenamiento por el metodo obtenerEstudiantesOrdenados
    private final ArrayList<String> camposOrdenados = new ArrayList<>(
            Arrays.asList("nombre","apellido","dni","edad","genero","ciudad")
    );

    //pasar lista de alumnos para cargar
    @Override
    public void cargarEstudiante(Estudiante estudiante) {
        try{
            EntityManager em = JPAutil.getEntityManager();
            em.getTransaction().begin();
            em.persist(estudiante);
            em.getTransaction().commit();
            em.close();
        }catch (Exception e){
            throw new RuntimeException("error al cargar el estudiante",e);
        }
    }

    //metodo para obtener todos los estudiantes ordenados por un campo de la lista camposOrdenados
    @Override
    public List<Estudiante> obtenerEstudiantesOrdenados(String campo) {
        //si el campo no existe en camposOrdenados lanzar excepcion
        if(!camposOrdenados.contains(campo.toLowerCase())){
            throw new IllegalArgumentException("el campo a ordenar no existe");
        }

        EntityManager em = JPAutil.getEntityManager();
        List<Estudiante> estudiantes = em.createQuery("SELECT e FROM Estudiante e ORDER BY LOWER(:campo)",Estudiante.class)
                                                        .setParameter("campo",campo)
                                                        .getResultList();
        return estudiantes;
    }

    @Override
    public List<Estudiante> obtenerEstudiantesPorLibreta(int nroLibreta) {
        EntityManager em = JPAutil.getEntityManager();
        List<Estudiante> estudiantes = em.createQuery(
                "SELECT e FROM Estudiante e WHERE e.nroLibreta=:nroLibreta",Estudiante.class
        ).setParameter("nroLibreta",nroLibreta).getResultList();

        if(estudiantes.isEmpty()){
            return null;
        }

        return estudiantes;
    }

    @Override
    public List<Estudiante> obtenerEstudiantesPorGenero(String genero) {
        EntityManager em = JPAutil.getEntityManager();
        List<Estudiante> estudiantes = em.createQuery(
                "SELECT e FROM Estudiante e WHERE LOWER(e.genero) = LOWER(:genero)",Estudiante.class
        ).setParameter("genero",genero).getResultList();

        if(estudiantes.isEmpty()){
            return null;
        }

        return estudiantes;
    }

    @Override
    public void cargarEstudiantes(List<Estudiante> estudiantes) {
        try{
            EntityManager em = JPAutil.getEntityManager();
            em.getTransaction().begin();
            for(Estudiante estudiante:estudiantes){
                em.persist(estudiante);
            }
            em.getTransaction().commit();
            em.close();
        }catch (Exception e){
            throw new RuntimeException("error al cargar los estudiantes",e);
        }
    }

    @Override
    public Estudiante findById(long id) {
        EntityManager em = JPAutil.getEntityManager();
        Estudiante estudiante = em.find(Estudiante.class,id);
        em.close();
        return estudiante;
    }

    @Override
    public List<EstudianteDTO> getAllEstudiantesSortedByApellido() {
        EntityManager em = JPAutil.getEntityManager();
        try {
            // Se usa una consulta de constructor para crear DTOs directamente.
            // La ruta del DTO debe ser completa (paquete + clase).
            String jpql = "SELECT new com.grupo4.dto.EstudianteDTO(e.nombre, e.apellido, e.dni, e.edad, e.genero, e.ciudad, e.nroLibreta) " +
                    "FROM Estudiante e " +
                    "ORDER BY LOWER(e.apellido)";
            return em.createQuery(jpql, EstudianteDTO.class).getResultList();

        } finally {
            em.close();
        }
    }

    @Override
    public EstudianteDTO getEstudianteByNroLibreta(int nroLibreta) {
        EntityManager em = JPAutil.getEntityManager();
        try{
            String jpql = "SELECT new com.grupo4.dto.EstudianteDTO(e.nombre, e.apellido, e.dni, e.edad, e.genero, e.ciudad, e.nroLibreta)" +
                    "FROM Estudiante e WHERE  e.nroLibreta = :nroLibreta";
            return em.createQuery(jpql, EstudianteDTO.class)
                    .setParameter("nroLibreta", nroLibreta)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public List<EstudianteDTO> getEstudianteByGenero(String genero) {
        EntityManager em = JPAutil.getEntityManager();
        try {
            String jpql = "SELECT new com.grupo4.dto.EstudianteDTO(e.nombre, e.apellido, e.dni, e.edad, e.genero, e.ciudad, e.nroLibreta) " +
                            "FROM Estudiante e " +
                            "WHERE LOWER(e.genero) = LOWER(:genero)";
            return em.createQuery(jpql, EstudianteDTO.class)
                    .setParameter("genero", genero)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    @Override
    public List<EstudianteDTO> getEstudiantesByCarreraAndCiudad(String nombreCarrera, String ciudadFiltro) {
        EntityManager em = JPAutil.getEntityManager();
        try{
        String jpql = "SELECT new com.grupo4.dto.EstudianteDTO(e.nombre, e.apellido, e.dni, e.edad, e.genero, e.ciudad, e.nroLibreta) " +
                      "FROM EstudianteCarrera ec JOIN ec.estudiante e JOIN ec.carrera c " +
                      "WHERE LOWER(c.nombre) = LOWER(:nombreCarrera) AND LOWER(e.ciudad) = LOWER(:ciudad)";
        TypedQuery<EstudianteDTO> query = em.createQuery(jpql, EstudianteDTO.class);
        query.setParameter("nombreCarrera", nombreCarrera);
        query.setParameter("ciudad", ciudadFiltro);
        List<EstudianteDTO> estudiantesFiltrados = query.getResultList();
        em.close();
        return estudiantesFiltrados;

    } finally {
            em.close();
        }
}
}
