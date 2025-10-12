package com.integrador3.repositorios;

import com.integrador3.dto.ReporteCarreraDTO;
import com.integrador3.model.EstudianteCarrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteCarreraRepositorio extends JpaRepository<EstudianteCarrera, Long> {

    @Query("SELECT new com.integrador3.dto.ReporteCarreraDTO(ec.fechaGraduacion, c.nombre, COUNT(ec), (SELECT COUNT(ec2) FROM EstudianteCarrera ec2 WHERE ec2.carrera.nombre = c.nombre)) " +
            "FROM EstudianteCarrera ec " +
            "JOIN ec.carrera c " +
            "WHERE ec.fechaGraduacion != 0 " +
            "GROUP BY ec.fechaGraduacion, c.nombre " +
            "ORDER BY c.nombre, ec.fechaGraduacion")
    List<ReporteCarreraDTO> getReportesCarreras();

}
