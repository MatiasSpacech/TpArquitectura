package com.grupo4.repository;

import com.grupo4.dto.ReporteCarreraDTO;
import com.grupo4.model.Carrera;

import java.util.List;

public interface CarreraRepository {
    public void cargarCarreras(List<Carrera> carreras);
    public Carrera findById(long id);
    public void crearCarrera(String nombre, int duracion);
    public Carrera getCarreraByName(String nombreCarrera);
    List<ReporteCarreraDTO> getReporteCarreras();

//    List<ReporteCarreraDTO> getReporteInscripciones();
//    List<ReporteCarreraDTO>getReporteEgresados();
    List<ReporteCarreraDTO> getInscriptosYEgresadosPorAnio();


}
