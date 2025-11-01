package grupo4.facturacion.controller;

import grupo4.facturacion.entity.Factura;
import grupo4.facturacion.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping("/")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(facturaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facturaService.findById(id));
    }

    @GetMapping("/usuario/id/{id}")
    public ResponseEntity<?> findByUsuarioId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facturaService.findByUsuarioId(id));
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Factura factura) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facturaService.save(factura));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        facturaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/usuario/name/{usuario}")
    public ResponseEntity<?> findByUsuario(@PathVariable String usuario) {
        return ResponseEntity.status(HttpStatus.OK).body(facturaService.findByUsuario(usuario));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> findByEstado(@PathVariable String estado) {
        return ResponseEntity.status(HttpStatus.OK).body(facturaService.findByEstado(estado));
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<?> findByFecha(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fecha) {
        return ResponseEntity.status(HttpStatus.OK).body(facturaService.findByFecha(fecha));
    }

    @GetMapping("/fechaBetween")
    public ResponseEntity<?>findByFechaBetween(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)Date fecha1,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)Date fecha2) {
        return ResponseEntity.status(HttpStatus.OK).body(facturaService.findByFechaBetween(fecha1, fecha2));
    }
}
