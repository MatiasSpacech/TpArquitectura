package com.integrador3.repositorios;

import com.integrador3.model.Estudiante;
import com.integrador3.dto.EstudianteDTO;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("EstudianteRepositorio")
public interface EstudianteRepositorio extends JpaRepository<Estudiante, Long> {    
    
    @Query("SELECT e " +
           "FROM Estudiante e ORDER BY :criterio")
    List<Estudiante> getEstudiantesOrderBy(String criterio);

    @Query("SELECT e " +
           "FROM Estudiante e WHERE e.nroLibreta=:nroLibreta")
    Estudiante findEstudianteByNroLibreta(Long nroLibreta);

    @Query("SELECT e " +
           "FROM Estudiante e WHERE LOWER(e.genero) = LOWER(:genero)")
    List<Estudiante> findEstudiantesByGenero(String genero);

    @Query("SELECT e " +
           "FROM Estudiante e JOIN e.listCarreras m JOIN m.carrera c " +
           "WHERE LOWER(c.nombre) = LOWER(:carrera) AND LOWER(e.ciudad) = LOWER(:ciudad)")
    List<Estudiante> findEstudiantesByCarreraAndCiudad(String carrera, String ciudad);

}
