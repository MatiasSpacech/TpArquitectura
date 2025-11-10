package grupo4.tarifas.controller;

import grupo4.tarifas.dto.TarifaDTO;
import grupo4.tarifas.entity.Tarifa;
import grupo4.tarifas.service.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/tarifas")
public class TarifaController {

    @Autowired
    private TarifaService tarifaService;

    /**
     * Ajuste de precios desde una fecha
     * POST /api/tarifas/ajustar-precio
     * Body: { "monto": 60.0, "montoExtra": 150.0, "fecha": "2025-12-01", ... }
     */
    @PostMapping("/ajustar-precio")
    public ResponseEntity<?> ajustarPrecioDesde(@RequestBody Tarifa nuevaTarifa) {
        try {
            TarifaDTO tarifaCreada = tarifaService.crearTarifa(nuevaTarifa);
            return ResponseEntity.status(HttpStatus.CREATED).body(tarifaCreada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al ajustar precio: " + e.getMessage());
        }
    }

    @PostMapping("/actualizar-desde-fecha")
    public ResponseEntity<?> actualizarTarifaDesdeFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fecha,
            @RequestParam double porcentajeIncremento) {
        return ResponseEntity.ok(tarifaService.actualizarTarifaDesdeFecha(fecha, porcentajeIncremento));
    }

    @PostMapping("/actualizar")
    public ResponseEntity<?> actualizarTarifa(@RequestBody Tarifa tarifa) {
        return ResponseEntity.ok(tarifaService.actualizarTarifa(tarifa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTarifa(@PathVariable Long id) {
       tarifaService.eliminarTarifa(id);
       return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearTarifa(@RequestBody Tarifa tarifa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tarifaService.crearTarifa(tarifa));
    }

    @GetMapping("/")
    public ResponseEntity<?> findAllTarifas() {
        return ResponseEntity.status(HttpStatus.OK).body(tarifaService.findAllTarifas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findTarifaById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(tarifaService.findTarifaById(id));
    }
}
