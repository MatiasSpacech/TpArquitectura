package com.integrador3.servicios;

import java.util.List;

import com.integrador3.servicios.exceptions.NotFoundException;
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
    public List<CarreraDTO> findCarrerasConEstudiantes() {
        return carreraRepositorio.findCarrerasConEstudiantes();
    }

    @Transactional(readOnly = true)
    public List<CarreraDTO> findAll() {
        return carreraRepositorio.findAll().stream().map(CarreraDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public CarreraDTO findById(int id) {
        return carreraRepositorio.findById(id).map(CarreraDTO::new)
                .orElseThrow(() -> new NotFoundException("Carrera",id));
    }

    @Transactional(readOnly = true)
    public Carrera findByIdEntity(int id){
        return carreraRepositorio.findById(id)
                .orElseThrow(() -> new NotFoundException("Carrera",id));
    }
}
