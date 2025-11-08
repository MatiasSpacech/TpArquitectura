package grupo4.parada.controller;

import grupo4.parada.entity.Parada;
import grupo4.parada.service.ParadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParadaController {

    @Autowired
    private ParadaService paradaService;

    //hola desde parada controller
    @GetMapping("/hola")
    public String hola() {
        return "Hola desde Parada Controller";
    }

    @PostMapping("")
    public ResponseEntity<Parada> registrarParada(@RequestBody Parada parada) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paradaService.registrar(parada));
    }

    @GetMapping("")
    public List<Parada> obtenerParadas() {
        return paradaService.buscarTodas();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> quitarParada(@PathVariable Long id) {
        paradaService.quitar(id);
        return ResponseEntity.noContent().build();
    }

    // ¡ENDPOINT CLAVE! El que llamará MSVC-VIAJE
//    @GetMapping("/validar")
//    public ResponseEntity<Boolean> esParadaValida(@RequestParam double latitud, @RequestParam double longitud) {
//        boolean esValida = paradaService.esParadaValida(latitud, longitud);
//        return ResponseEntity.ok(esValida);
//    }
}
