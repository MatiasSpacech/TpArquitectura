package grupo4.mscvusuario.controllers;

import grupo4.mscvusuario.entity.Cuenta;
import grupo4.mscvusuario.entity.EstadoCuenta;
import grupo4.mscvusuario.entity.Usuario;
import grupo4.mscvusuario.service.UsuarioService;
import grupo4.mscvusuario.service.exceptions.NotFoundException;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping // http://localhost:8080/api/usuario
    public ResponseEntity<List<Usuario>> findAll(){
        List<Usuario> usuarios = usuarioService.findAll();
        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}") // http://localhost:8080/api/usuario/1
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cuentas/{id}")
    public ResponseEntity<Set<Cuenta>> obtenerCuentasUsuario(@PathVariable Long id) {
        try {
            Set<Cuenta> cuentas = usuarioService.getCuentasByUsuario(id);
            return ResponseEntity.ok(cuentas);
        }
        catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{idUsuario}/cuenta-asociada/{idCuenta}")
    public ResponseEntity<Boolean> verificarCuentaAsociada(
                                @PathVariable Long idUsuario,
                                @PathVariable Long idCuenta) {

        // El service ejecuta la lógica en la BD
        boolean esValido = usuarioService.cuentaAsociada(idUsuario, idCuenta);

        // Retorna 200 OK con el booleano en el cuerpo
        return ResponseEntity.ok(esValido);
    }

    @PostMapping // http://localhost:8080/api/usuario
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }

    @PutMapping("/{id}") // http://localhost:8080/api/usuario/1
    public ResponseEntity<Usuario> update(@RequestBody Usuario usuario, @PathVariable Long id) {
        Usuario usuarioActual = usuarioService.findById(id);
        if (usuarioActual != null) {
            usuarioActual.setNombre(usuario.getNombre());
            usuarioActual.setApellido(usuario.getApellido());
            // Actualizar otros campos necesarios...
            return ResponseEntity.ok(usuarioService.save(usuarioActual));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")  // http://localhost:8080/api/usuario/1
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (usuarioService.findById(id) != null) {
            usuarioService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{idUsuario}/asignar-cuenta/{idCuenta}") // http://localhost:8080/api/usuario/1/asignar-cuenta/2
    public ResponseEntity<?> asignarCuenta(@PathVariable Long idUsuario, @PathVariable Long idCuenta) {
        Optional<Usuario> o = usuarioService.asignarCuenta(idUsuario, idCuenta);
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{idUsuario}/cambiar-estado-cuentas")  // http://localhost:8080/api/usuario/1/cambiar-estado-cuentas?nuevoEstado=INACTIVA
    public ResponseEntity<Void> cambiarEstadoCuentas(@PathVariable Long idUsuario, @RequestParam EstadoCuenta nuevoEstado) {
        usuarioService.cambiarEstadoCuentas(idUsuario, nuevoEstado);
        return ResponseEntity.noContent().build();
    }
}