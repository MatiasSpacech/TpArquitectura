package com.integrador3.controllers;

import com.integrador3.servicios.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/personas")
public class PersonaController {

    @Autowired //http://localhost:8080/obtenerTodasLasPersonas
    private PersonaService personaService;

    @GetMapping("/obtenerTodasLasPersonas")
    public ResponseEntity<?>obtenerTodasLasPersonas(){
        try {
            return new ResponseEntity<>(personaService.findAll(), HttpStatusCode.valueOf(200));

        }catch (Exception e){
            return new ResponseEntity<>("Error al obtener las personas: " + e.getMessage(), HttpStatusCode.valueOf(404));
        }
    }

    @GetMapping("/obtenerReporter") //http://localhost:8080/personas/obtenerReporter
    public ResponseEntity<?>obtenerReporte(){
        try{
            return ResponseEntity.ok(personaService.obtenerReporte());
    }catch (Exception e){
        return new ResponseEntity<>("Error al obtener el reporte: " + e.getMessage(), HttpStatusCode.valueOf(404));}
    }

    @GetMapping("/obtenerPersonasPorNombre") //http://localhost:8080/personas/obtenerPersonasPorNombre?nombre=Juan

    public ResponseEntity<?>obtenerPersonasPorNOmbre(@RequestParam String nombre){
        try {
            return ResponseEntity.ok(personaService.obtenerPersonasPorNombre(nombre));
        }
        catch (Exception e){
            return new ResponseEntity<>("Error al obtener las personas por nombre: " + e.getMessage(), HttpStatusCode.valueOf(404));
        }
    }

}
