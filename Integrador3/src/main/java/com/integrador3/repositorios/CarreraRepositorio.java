package com.integrador3.repositorios;

import com.integrador3.dto.CarreraDTO;
import com.integrador3.model.Carrera;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("CarreraRepositorio")
public interface CarreraRepositorio extends JpaRepository<Carrera, Integer> {
    //f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
    @Query("SELECT new com.integrador3.dto.CarreraDTO(c.nombre, c.duracion, COUNT(c)) " +
            "FROM Carrera c JOIN  c.estudiantes e " +
            "GROUP BY c.id, c.nombre " +
            "ORDER BY COUNT(c) DESC")
    List<CarreraDTO> findCarrerasConEstudiantes();
}

