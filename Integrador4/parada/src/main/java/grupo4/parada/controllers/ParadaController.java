package grupo4.parada.controllers;

import grupo4.parada.dtos.ParadaDTO;
import grupo4.parada.model.Parada;
import grupo4.parada.services.ParadaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paradas")
public class ParadaController {

    private final ParadaService service;

    public ParadaController(ParadaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ParadaDTO>> findALl() {
        List<ParadaDTO> paradas = service.findAll();

        if(paradas.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(paradas);
    }

    @PostMapping
    public ResponseEntity<ParadaDTO> save(@RequestBody Parada parada) {
        try {
            ParadaDTO nuevaParada = service.save(parada);
            return new ResponseEntity<>(nuevaParada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParadaDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }
}
