package com.grupo4.repository;

import com.grupo4.factory.JPAutil;
import com.grupo4.model.Estudiante;
import com.ibm.icu.text.CaseMap;

import javax.persistence.EntityManager;
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
}
