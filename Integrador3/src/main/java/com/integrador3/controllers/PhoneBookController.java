package com.integrador3.controllers;


import com.integrador3.dto.ReportePersonDto;
import com.integrador3.servicios.PhoneBookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PhoneBookController {

    @Autowired
    private PhoneBookServices phoneBookServices;

    @GetMapping("/obtenerReportePersonas") //http://localhost:8080/obtenerReportePersonas
    public ResponseEntity<?>obtenerReportePersonas(){
        try{
            return ResponseEntity.ok(phoneBookServices.obtenerReportePersonas());
        }catch (Exception e){
            return new ResponseEntity<>("Error al obtener el reporte de personas: " + e.getMessage(), null, 404);
        }
    }
}
