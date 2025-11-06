package grupo4.mscvmonopatin.controllers;

import grupo4.mscvmonopatin.dtos.MonopatinDTO;
import grupo4.mscvmonopatin.dtos.MonopatinPatchDTO;
import grupo4.mscvmonopatin.model.Monopatin;
import grupo4.mscvmonopatin.services.MonopatinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monopatines")
public class MonopatinController {

    private final MonopatinService service;

    public MonopatinController(MonopatinService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MonopatinDTO>> findAll() {
        List<MonopatinDTO> monopatines = service.findAll();

        if (monopatines.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(monopatines);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonopatinDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<MonopatinDTO> save(@RequestBody Monopatin monopatin) {
        try {
            MonopatinDTO nuevoMonopatin = service.save(monopatin);
            return new ResponseEntity<>(nuevoMonopatin, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        // Retorna 204 (No Content) si se elimino correctamente
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MonopatinDTO> partialUpdate(@PathVariable String id, @RequestBody MonopatinPatchDTO edit) {
        MonopatinDTO monopatinEditado = service.patch(id, edit);
        return ResponseEntity.ok(monopatinEditado);
    }

    @PatchMapping("/{id}/estado/{estado}")
    public ResponseEntity<MonopatinDTO> setEstado(@PathVariable String id, @PathVariable String estado) {
        MonopatinDTO monopatinEditado = service.setEstadoMonopatin(id, estado);
        return ResponseEntity.ok(monopatinEditado);
    }
}
