package grupo4.mscvmonopatin.controller;


import grupo4.mscvmonopatin.dto.MonopatinDTO;
import grupo4.mscvmonopatin.entity.Monopatin;
import grupo4.mscvmonopatin.service.MonopatinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monopatines")
public class MonopatinController {

    @Autowired
    private MonopatinService monopatinService;

    @GetMapping("/hola")
    public String hola() {
        return "Como estas desde MonopatinController";
    }

    @GetMapping
    public ResponseEntity<List<MonopatinDTO>> obtenerTodos() {
        try {
            List<MonopatinDTO> monopatines = monopatinService.findAll();
            return ResponseEntity.ok(monopatines);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonopatinDTO> obtenerPorId(@PathVariable Long id) {
        MonopatinDTO monopatin = monopatinService.findById(id);
        if (monopatin != null) {
            return ResponseEntity.ok(monopatin);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crearMonopatin(@RequestBody MonopatinDTO monopatinDTO) {
        try {
            MonopatinDTO nuevoMonopatin = monopatinService.crearMonopatin(monopatinDTO);
            return new ResponseEntity<>(nuevoMonopatin, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<MonopatinDTO> actualizarMonopatin(@PathVariable Long id, @RequestBody MonopatinDTO monopatinDTO) {
        try {
            MonopatinDTO actualizado = monopatinService.update(id, monopatinDTO);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMonopatin(@PathVariable Long id) {
        try {
            monopatinService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


}
