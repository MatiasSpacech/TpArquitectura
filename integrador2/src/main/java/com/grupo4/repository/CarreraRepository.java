package com.grupo4.repository;

import com.grupo4.model.Carrera;

import java.util.List;

public interface CarreraRepository {
    public void cargarCarreras(List<Carrera> carreras);
    public Carrera findById(long id);
}
