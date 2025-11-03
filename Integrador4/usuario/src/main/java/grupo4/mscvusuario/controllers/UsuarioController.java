package grupo4.mscvusuario.controllers;

        import grupo4.mscvusuario.entity.Usuario;
        import grupo4.mscvusuario.service.UsuarioService;
        import grupo4.mscvmonopatin.dto.MonopatinDTO;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

        import java.util.List;

        @RestController
        @RequestMapping("/usuario")
        public class UsuarioController {

            @Autowired
            private UsuarioService usuarioService;

            // GET /usuario/hola
            @GetMapping("/hola")
            public String holaMundo() {
                return "Hello World Usuario!";
            }

            // GET /usuario  -> listar todos
            @GetMapping
            public ResponseEntity<List<Usuario>> obtenerTodosUsuarios() {
                try {
                    List<Usuario> usuarios = usuarioService.findAll();
                    return ResponseEntity.ok(usuarios);
                } catch (Exception e) {
                    return ResponseEntity.status(500).build();
                }
            }

            // POST /usuario  -> crear
            @PostMapping
            public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
                Usuario creado = usuarioService.crearUsuario(usuario);
                return ResponseEntity.status(201).body(creado);
            }

            // GET /usuario/{id}  -> obtener por id
            @GetMapping("/{id}")
            public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
                Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
                if (usuario != null) {
                    return ResponseEntity.ok(usuario);
                } else {
                    return ResponseEntity.notFound().build();
                }
            }

            // /usuario/monopatines
            @GetMapping("/monopatines")
            public ResponseEntity<List<MonopatinDTO>> obtenerTodosLosMonopatines() {
                return ResponseEntity.ok(usuarioService.obtenerTodosLosMonopatines());
            }

        }