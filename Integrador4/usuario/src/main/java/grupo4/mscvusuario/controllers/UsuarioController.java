package grupo4.mscvusuario.controllers;

import grupo4.mscvusuario.entity.Usuario;
import grupo4.mscvusuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

@Autowired
private UsuarioService usuarioService;

    @GetMapping("/")
    public String holaMundo() {
        return "Hello World Usuario!";
    }

    @GetMapping
    public ResponseEntity<List<Usuario>>obtenerTodosUsuarios(){
        try {
            List<Usuario> usuarios = usuarioService.findAll();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<Usuario>crearUsuario(@RequestBody Usuario usuario){
        // Lógica para crear un nuevo usuario
        return ResponseEntity.ok(usuarioService.crearUsuario(usuario));
    }

    @GetMapping
    public ResponseEntity<Usuario>obtenerUsuarioPorId(@PathVariable Long id){
        // Lógica para obtener un usuario por su ID
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
