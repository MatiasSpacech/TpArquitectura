package com.integrador3.controllers;

import com.integrador3.model.Estudiante;
import com.integrador3.repositorios.EstudianteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {
    @Autowired
    private EstudianteRepositorio estudianteRepositorio;

    @GetMapping("")
    public ResponseEntity<List<Estudiante>> getEstudiantes() {
        try {
            return  new ResponseEntity<>(estudianteRepositorio.findAll(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> getEstudianteById(@PathVariable Long id) {
        try {
            Estudiante estudiante = estudianteRepositorio.findById(id).orElse(null);
            if (estudiante == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(estudiante);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }
}