package com.grupo4.repository;

import com.grupo4.model.Estudiante;

import java.util.List;

public interface EstudianteRepository {
    public void cargarEstudiante(Estudiante estudiante);
    public void cargarEstudiantes(List<Estudiante> estudiantes);
    public List<Estudiante> obtenerEstudiantesOrdenados(String campo);
    public List<Estudiante> obtenerEstudiantesPorLibreta(int nroLibreta);
    public List<Estudiante> obtenerEstudiantesPorGenero(String genero);
    public Estudiante findById(long id);
}
