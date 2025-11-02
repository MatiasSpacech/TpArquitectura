package grupo4.mscvmonopatin.controller;


import grupo4.mscvmonopatin.entity.Monopatin;
import grupo4.mscvmonopatin.service.MonopatinService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<Monopatin>>obtenerTodos(){
        try{
            List<Monopatin>monopatins = monopatinService.findAll();
            return ResponseEntity.ok(monopatins);
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<Monopatin>crearMonopatin(@RequestBody Monopatin monopatin){
        try{
            Monopatin nuevoMonopatin = monopatinService.crearMonopatin(monopatin);
            return ResponseEntity.ok(nuevoMonopatin);
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }


}
