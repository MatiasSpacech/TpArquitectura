package com.grupo4.repository;

import com.grupo4.dto.EstudianteDTO;
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

    //punto a)
    @Override
    public void addEstudiante(Estudiante estudiante) {
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

    //metodo para obtener todos los estudiantes ordenados por un campo de la lista camposOrdenados,PUNTO c)
    @Override
    public List<EstudianteDTO> getEstudiantesSorted(String campo) {
        //si el campo no existe en camposOrdenados lanzar excepcion
        if(!camposOrdenados.contains(campo.toLowerCase())){
            throw new IllegalArgumentException("el campo a ordenar no existe");
        }

        EntityManager em = JPAutil.getEntityManager();
        String jpql = "SELECT new com.grupo4.dto.EstudianteDTO(e.dni,e.nombre,e.apellido,e.edad,e.genero,e.ciudad,e.nroLibreta) " +
                    "FROM Estudiante e " +
                    "ORDER BY LOWER(:campo)";
        List<EstudianteDTO> estudiantes = em.createQuery(jpql,EstudianteDTO.class)
                    .setParameter("campo",campo)
                    .getResultList();
        em.close();

        return estudiantes;
    }

    //PUNTO d
    @Override
    public EstudianteDTO getEstudianteLU(int nroLibreta) {
        try {
            EntityManager em = JPAutil.getEntityManager();
            String jpql = "SELECT new com.grupo4.dto.EstudianteDTO(e.dni,e.nombre,e.apellido,e.edad,e.genero,e.ciudad,e.nroLibreta) " +
                        "FROM Estudiante e " +
                        "WHERE e.nroLibreta=:nroLibreta";
            EstudianteDTO estudiante = em.createQuery(jpql,EstudianteDTO.class
            ).setParameter("nroLibreta",nroLibreta).getSingleResult();
            em.close();

            return estudiante;
        }catch (Exception e){
            System.err.println("no se encontro el estudiante con nroLibreta: "+nroLibreta);
            return null;
        }
    }

    //PUNTO e)
    @Override
    public List<EstudianteDTO> getEstudiantesByGenero(String genero) {
        EntityManager em = JPAutil.getEntityManager();
        String jpql = "SELECT new com.grupo4.dto.EstudianteDTO(e.dni,e.nombre,e.apellido,e.edad,e.genero,e.ciudad,e.nroLibreta) " +
                    "FROM Estudiante e " +
                    "WHERE LOWER(e.genero) = LOWER(:genero)";
        List<EstudianteDTO> estudiantes = em.createQuery(jpql,EstudianteDTO.class
        ).setParameter("genero",genero).getResultList();
        em.close();

        if(estudiantes.isEmpty()){
            return null;
        }

        return estudiantes;
    }

    //Cargar estudiantes csv
    @Override
    public void addEstudiantes(List<Estudiante> estudiantes) {
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

    //PUNTO g)
    @Override
    public List<EstudianteDTO> getEstudiantesByCarreraAndCiudad(String carrera, String ciudad) {
        EntityManager em = JPAutil.getEntityManager();
        String jpql = "SELECT new com.grupo4.dto.EstudianteDTO(e.dni,e.nombre,e.apellido,e.edad,e.genero,e.ciudad,e.nroLibreta) " +
                    "FROM Estudiante e JOIN e.listCarreras m " +
                                        "JOIN m.carrera c " +
                    "WHERE LOWER(c.nombre) = LOWER(:carrera) AND LOWER(e.ciudad) = LOWER(:ciudad)";
        List<EstudianteDTO> estudiantes = em.createQuery(jpql,EstudianteDTO.class)
                .setParameter("carrera",carrera)
                .setParameter("ciudad",ciudad)
                .getResultList();
        em.close();
        return estudiantes;
    }

    //Buscar estudiante por id
    @Override
    public Estudiante findById(long id) {
        EntityManager em = JPAutil.getEntityManager();
        //String jpql = "SELECT e FROM Estudiante e JOIN FETCH e.listCarreras WHERE e.dni=:id";
        //Estudiante estudiante = em.createQuery(jpql,Estudiante.class).setParameter("id",id).getSingleResult();
        Estudiante estudiante = em.find(Estudiante.class,id);
        em.close();
        return estudiante;
    }

}
