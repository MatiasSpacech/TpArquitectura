package com.grupo4.repository;

import com.grupo4.model.EstudianteCarrera;

import java.util.List;

public interface EstudiantaCarreraRepository {
    public void matricularEstudiante(EstudianteCarrera estudianteCarrera);
    public void matricularEstudiantes(List<EstudianteCarrera> estudiantesCarrera);
}
