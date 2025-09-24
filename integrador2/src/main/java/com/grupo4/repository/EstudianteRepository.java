package com.grupo4.repository;

import com.grupo4.model.Estudiante;

import java.util.List;

public interface EstudianteRepository {
    public void cargarEstudiantes(List<Estudiante> estudiantes);
}
