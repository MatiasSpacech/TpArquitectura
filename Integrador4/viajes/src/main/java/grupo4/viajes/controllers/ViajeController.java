package grupo4.viajes.controllers;

import grupo4.viajes.dtos.ReporteViajePeriodoDTO;
import grupo4.viajes.dtos.ViajeDTO;
import grupo4.viajes.model.Viaje;
import grupo4.viajes.services.ViajeService;
import grupo4.viajes.services.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/viajes")
public class ViajeController {
    private final ViajeService service;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<ViajeDTO> viajes = service.findAll();

        if(viajes.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(viajes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViajeDTO> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        }
        catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/reporte")
    public ResponseEntity<?> getReporteViajes(@RequestParam(required = false, name="anio") Integer anio,
                                              @RequestParam(required = false, name="cantidad") Integer cantidad) {
        try {
            List<ReporteViajePeriodoDTO> reportes = service.getReporteViajeAnio(anio,cantidad);

            if(reportes.isEmpty()){
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(reportes);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error en los parametros");
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Viaje body) {
        try {
            ViajeDTO nuevoViaje = service.save(body);
            return new ResponseEntity<>(nuevoViaje, HttpStatus.CREATED);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.noContent().build();
        }
        catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
