package com.grupo4.repository;

import com.grupo4.dto.EstudianteDTO;
import com.grupo4.model.Estudiante;

import java.util.List;

public interface EstudianteRepository {
    public void cargarEstudiante(Estudiante estudiante);
    public void cargarEstudiantes(List<Estudiante> estudiantes);
    public List<Estudiante> obtenerEstudiantesOrdenados(String campo);
    public List<Estudiante> obtenerEstudiantesPorLibreta(int nroLibreta);
    public List<Estudiante> obtenerEstudiantesPorGenero(String genero);
    public Estudiante findById(long id);

    //recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.
    List<EstudianteDTO> getAllEstudiantesSortedByApellido();

    //recuperar un estudiante, en base a su número de libreta universitaria.
    EstudianteDTO getEstudianteByNroLibreta(int nroLibreta);

    //recuperar todos los estudiantes, en base a su género.
    List<EstudianteDTO> getEstudianteByGenero(String genero);

    List<EstudianteDTO> getEstudiantesByCarreraAndCiudad(String carreraFiltro, String ciudadFiltro);
}
