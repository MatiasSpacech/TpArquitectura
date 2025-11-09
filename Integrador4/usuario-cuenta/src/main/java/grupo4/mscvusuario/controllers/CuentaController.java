package grupo4.mscvusuario.controllers;

import grupo4.mscvusuario.entity.Cuenta;
import grupo4.mscvusuario.entity.EstadoCuenta;
import grupo4.mscvusuario.repository.CuentaRepository;
import grupo4.mscvusuario.repository.UsuarioRepository;
import grupo4.mscvusuario.service.CuentaService; // Deberás crear este servicio
import grupo4.mscvusuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cuenta")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Cuenta> findAll() {
        return cuentaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> findById(@PathVariable Long id) {
        Cuenta cuenta = cuentaService.findById(id);
        if (cuenta != null) {
            return ResponseEntity.ok(cuenta);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Cuenta> save(@RequestBody Cuenta cuenta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaService.save(cuenta));
    }

// En tu clase CuentaController.java

    // En el archivo 'usuario-cuenta/src/main/java/grupo4/mscvusuario/controllers/CuentaController.java'

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Cuenta cuenta, @PathVariable Long id) {
        Optional<Cuenta> cuentaOptional = cuentaService.editar(id, cuenta);

        if (cuentaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(cuentaOptional.get());
        }
        return ResponseEntity.notFound().build();
    }


    // En el archivo 'usuario-cuenta/src/main/java/grupo4/mscvusuario/controllers/CuentaController.java'

    // En el archivo 'usuario-cuenta/src/main/java/grupo4/mscvusuario/controllers/CuentaController.java'
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (cuentaService.existsById(id)) {
            cuentaService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(path = "/usuario/{idUsuario}/cambiar-estado-cuentas", params = "nuevoEstado")
    public ResponseEntity<Void> cambiarEstadoCuentas(
            @PathVariable Long idUsuario,
            @RequestParam("nuevoEstado") EstadoCuenta nuevoEstado) {
        usuarioService.cambiarEstadoCuentas(idUsuario, nuevoEstado);
        return ResponseEntity.noContent().build();
    }



}