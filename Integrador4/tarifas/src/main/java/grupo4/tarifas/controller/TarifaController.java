package grupo4.tarifas.controller;

import grupo4.tarifas.entity.Tarifa;
import grupo4.tarifas.repository.TarifaRepository;
import grupo4.tarifas.service.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/tarifas")
public class TarifaController {

    @Autowired
    private TarifaService tarifaService;

    @PostMapping("/actualizar-desde-fecha")
    public ResponseEntity<?> actualizarTarifaDesdeFecha(@RequestParam Date fecha, @RequestParam double montoExtra) {
      return ResponseEntity.ok(tarifaService.actualizarTarifaDesdeFecha(fecha, montoExtra));
    }

    @PostMapping("/actualizar")
    public ResponseEntity<?> actualizarTarifa(@RequestParam Tarifa tarifa) {
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

    @GetMapping("/tarifa/id/{id}")
    public ResponseEntity<?> findTarifaById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(tarifaService.findTarifaById(id));
    }

    @GetMapping("/buscar/fecha/{fecha}")
    public ResponseEntity<?> findTarifaByFecha(@PathVariable @RequestParam Date fecha) {
        return ResponseEntity.status(HttpStatus.OK).body(tarifaService.findTarifaByFecha(fecha));
    }


    @GetMapping("/tarifa/monto/{monto}")
    public ResponseEntity<?> findTarifaByMonto(@PathVariable double monto) {
        return ResponseEntity.status(HttpStatus.OK).body(tarifaService.findTarifaByMonto(monto));
    }
}

