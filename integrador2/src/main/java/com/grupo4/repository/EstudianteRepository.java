package com.grupo4.repository;

import com.grupo4.dto.EstudianteDTO;
import com.grupo4.model.Estudiante;

import java.util.List;

public interface EstudianteRepository {
    void addEstudiante(Estudiante estudiante);
    void addEstudiantes(List<Estudiante> estudiantes);
    List<EstudianteDTO> getEstudiantesSorted(String campo);
    List<EstudianteDTO> getEstudiantesByCarreraAndCiudad(String carrera,String ciudad);
    EstudianteDTO getEstudianteLU(int nroLibreta);
    List<EstudianteDTO> getEstudiantesByGenero(String genero);
    Estudiante findById(long id);
}
