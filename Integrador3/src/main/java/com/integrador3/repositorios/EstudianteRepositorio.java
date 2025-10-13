package com.integrador3.repositorios;

import com.integrador3.model.Estudiante;
import com.integrador3.dto.EstudianteDTO;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("EstudianteRepositorio")
public interface EstudianteRepositorio extends JpaRepository<Estudiante, Long> {    
    
    @Query("SELECT new com.integrador3.dto.EstudianteDTO(e.dni, e.nombre, e.apellido, e.edad, e.genero, e.ciudad, e.nroLibreta) " +
           "FROM Estudiante e ORDER BY :criterio")
    List<EstudianteDTO> getEstudiantesOrderBy(String criterio);

    @Query("SELECT new com.integrador3.dto.EstudianteDTO(e.dni,e.nombre,e.apellido,e.edad,e.genero,e.ciudad,e.nroLibreta) " +
           "FROM Estudiante e WHERE e.nroLibreta=:nroLibreta")
    EstudianteDTO findEstudianteByNroLibreta(Long nroLibreta);

    @Query("SELECT new com.integrador3.dto.EstudianteDTO(e.dni,e.nombre,e.apellido,e.edad,e.genero,e.ciudad,e.nroLibreta) " +
           "FROM Estudiante e WHERE LOWER(e.genero) = LOWER(:genero)")
    List<EstudianteDTO> findEstudiantesByGenero(String genero);

    @Query("SELECT new com.integrador3.dto.EstudianteDTO(e.dni,e.nombre,e.apellido,e.edad,e.genero,e.ciudad,e.nroLibreta) " +
           "FROM Estudiante e JOIN e.listCarreras m JOIN m.carrera c " +
           "WHERE LOWER(c.nombre) = LOWER(:carrera) AND LOWER(e.ciudad) = LOWER(:ciudad)")
    List<EstudianteDTO> findEstudiantesByCarreraAndCiudad(String carrera, String ciudad);

}
