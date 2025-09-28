package com.grupo4.repository;

import com.grupo4.dto.CarreraDTO;
import com.grupo4.model.Carrera;

import java.util.List;

public interface CarreraRepository {
    void addCarreras(List<Carrera> carreras);
    List<CarreraDTO> getCarrerasConEstudiantes();
    Carrera findById(int id);
}
