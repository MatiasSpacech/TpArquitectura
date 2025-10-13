package com.integrador3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.integrador3.servicios.EstudianteCarreraService;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/estudiantes-carreras")
public class EstudianteCarreraController {
    @Autowired
    private EstudianteCarreraService estudianteCarreraService;

    @PostMapping("/matricular")
    // Endpoint para matricular un estudiante en una carrera
    // Ejemplo de uso: /estudiantes-carreras/matricular?estudianteId=1&carreraId=2
    public ResponseEntity<String> matricularEstudiante(@RequestParam Long estudianteId, @RequestParam int carreraId) {
        try {
            estudianteCarreraService.matricularEstudiante(estudianteId, carreraId);
            return ResponseEntity.status(201).body("Estudiante matriculado en la carrera exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al matricular el estudiante en la carrera");
        }
    }
}
