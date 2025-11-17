package com.integrador3.controllers;


import com.integrador3.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

//    @GetMapping("/obtenerUsuarioPorEdadMenorA") //http://localhost:8080/obtenerUsuarioPorEdadMenorA?edad=30
//    public ResponseEntity<?>obtenerUsuarioPorEdadMenorA(@RequestParam int edad){
//        try {
//            return ResponseEntity.ok(usuarioService.obtenerUsuarioPorEdadMenorA(edad));
//        }catch (Exception e){
//            return new ResponseEntity<>("Error al obtener los usuarios por edad: " + e.getMessage(), null, 404);
//        }
//    }

    @GetMapping("/obtenerReporteVentas") //http://localhost:8080/obtenerReporteVentas
    public ResponseEntity<?>obtenerReporteVentas(@RequestParam int edad){
        try{
            return ResponseEntity.ok(usuarioService.obtenerReporteVentas(edad));

        }catch (Exception e){
            return new ResponseEntity<>("Error al obtener los usuarios por reporte: " + e.getMessage(), null, 404);
        }
    }

}
