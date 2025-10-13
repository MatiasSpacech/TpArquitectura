package com.integrador3.servicios;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.integrador3.model.Carrera;
import com.integrador3.model.Estudiante;
import com.integrador3.model.EstudianteCarrera;
import com.integrador3.repositorios.EstudianteCarreraRepositorio;

import jakarta.transaction.Transactional;

@Service
public class EstudianteCarreraService {
    @Autowired
    private EstudianteCarreraRepositorio estudianteCarreraRepositorio;
    @Autowired
    private CarreraService carreraService;
    @Autowired
    private EstudianteService estudianteService;

    @Transactional
    public void matricularEstudiante(Long estudianteId, int carreraId) {
        Estudiante estudiante = estudianteService.findById(estudianteId);
        Carrera carrera = carreraService.findById(carreraId);            
        if (estudiante != null && carrera != null) {
            EstudianteCarrera estudianteCarrera = new EstudianteCarrera();
            estudianteCarrera.setEstudiante(estudiante);
            estudianteCarrera.setCarrera(carrera);
            estudianteCarreraRepositorio.save(estudianteCarrera);
        } else {
            throw new IllegalArgumentException("Estudiante o Carrera no encontrados");
        }
    }

}
