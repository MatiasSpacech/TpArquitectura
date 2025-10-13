package com.integrador3.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.integrador3.dto.CarreraDTO;
import com.integrador3.model.Carrera;
import com.integrador3.repositorios.CarreraRepositorio;

@Service
public class CarreraService {

    @Autowired
    private CarreraRepositorio carreraRepositorio;

    @Transactional(readOnly = true)
    public List<CarreraDTO> findCarrerasConEstudiantes()
    {
        return carreraRepositorio.findCarrerasConEstudiantes();
    }
    @Transactional(readOnly = true)
    public List<Carrera> findAll() {
        return carreraRepositorio.findAll();
    }
    @Transactional(readOnly = true)
    public Carrera findById(int id) {
        return carreraRepositorio.findById(id).orElse(null);
    }
}
