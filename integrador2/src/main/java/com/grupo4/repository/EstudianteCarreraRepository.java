package com.grupo4.repository;

import com.grupo4.dto.ReporteCarreraDTO;
import com.grupo4.model.EstudianteCarrera;

import java.util.List;

public interface EstudianteCarreraRepository {
    void matricularEstudiante(EstudianteCarrera estudianteCarrera);
    List<ReporteCarreraDTO> getReportesCarreras();
    void matricularEstudiantes(List<EstudianteCarrera> estudiantesCarrera);
}
