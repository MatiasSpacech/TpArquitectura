package com.integrador3.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.integrador3.model.Carrera;
import com.integrador3.model.Estudiante;
import com.integrador3.model.EstudianteCarrera;
import com.integrador3.repositorios.CarreraRepositorio;
import com.integrador3.repositorios.EstudianteCarreraRepositorio;
import com.integrador3.repositorios.EstudianteRepositorio;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/estudiantes-carreras")
public class EstudianteCarreraController {

    @Autowired
    private EstudianteCarreraRepositorio estudianteCarreraRepositorio;
    @Autowired
    private CarreraRepositorio carreraRepositorio;
    @Autowired
    private EstudianteRepositorio estudianteRepositorio;

    @PostMapping("/matricular")
    // Endpoint para matricular un estudiante en una carrera
    // Ejemplo de uso: /estudiantes-carreras/matricular?estudianteId=1&carreraId=2
    public ResponseEntity<String> matricularEstudiante(@RequestParam Long estudianteId, @RequestParam int carreraId) {
        try {
            Estudiante estudiante = estudianteRepositorio.findById(estudianteId).orElse(null);
            Carrera carrera = carreraRepositorio.findById(carreraId).orElse(null);
            if (estudiante == null || carrera == null) {
                return ResponseEntity.status(404).body("Estudiante o carrera no encontrado");
            }
            EstudianteCarrera estudianteCarrera = new EstudianteCarrera();
            estudianteCarrera.setEstudiante(estudiante);
            estudianteCarrera.setCarrera(carrera);
            estudianteCarreraRepositorio.save(estudianteCarrera);   
            return ResponseEntity.status(201).body("Estudiante matriculado en la carrera exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al matricular el estudiante en la carrera");
        }

    }
}
