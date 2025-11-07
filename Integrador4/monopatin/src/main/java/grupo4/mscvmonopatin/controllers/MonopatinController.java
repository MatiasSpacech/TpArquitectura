package grupo4.mscvmonopatin.controllers;

import grupo4.mscvmonopatin.dtos.MonopatinDTO;
import grupo4.mscvmonopatin.dtos.MonopatinPatchDTO;
import grupo4.mscvmonopatin.model.Monopatin;
import grupo4.mscvmonopatin.repository.MonopatinRepository;
import grupo4.mscvmonopatin.services.MonopatinService;
import grupo4.mscvmonopatin.services.exceptions.InvalidEstadoException;
import grupo4.mscvmonopatin.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        try {
            return ResponseEntity.ok(service.findById(id));
        }
        catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/parada")
    public ResponseEntity<Map<String, Object>> getMonopatinConParada(@PathVariable String id) {
        return ResponseEntity.ok(service.getMonopatinConParada(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Monopatin monopatin) {
        try {
            MonopatinDTO nuevoMonopatin = service.save(monopatin);
            return new ResponseEntity<>(nuevoMonopatin, HttpStatus.CREATED);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            service.delete(id);
            // Retorna 204 (No Content) si se elimino correctamente
            return ResponseEntity.noContent().build();
        }
        catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdate(@PathVariable String id, @RequestBody MonopatinPatchDTO edit) {
        try {
            MonopatinDTO monopatinEditado = service.patch(id, edit);
            return ResponseEntity.ok(monopatinEditado);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PatchMapping("/{id}/estado/{estado}")
    public ResponseEntity<?> setEstado(@PathVariable String id, @PathVariable String estado) {
        try {
            MonopatinDTO monopatinEditado = service.setEstadoMonopatin(id, estado);
            return ResponseEntity.ok(monopatinEditado);
        }
        catch (InvalidEstadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/parada/{idParada}")
    public ResponseEntity<List<MonopatinDTO>> findMonopatinesByIdParada(@PathVariable Long idParada){
        List<MonopatinDTO> monopatinesEnParada = service.findMonopatinesByIdParada(idParada);

        if (monopatinesEnParada.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(monopatinesEnParada);
    }
}
