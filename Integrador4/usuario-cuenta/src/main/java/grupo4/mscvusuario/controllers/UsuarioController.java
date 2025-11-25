package grupo4.mscvusuario.controllers;

import grupo4.mscvusuario.dtos.LoginDTO;
import grupo4.mscvusuario.entity.Cuenta;
import grupo4.mscvusuario.entity.EstadoCuenta;
import grupo4.mscvusuario.entity.Usuario;
import grupo4.mscvusuario.service.UsuarioService;
import grupo4.mscvusuario.service.exceptions.NotFoundException;
import java.util.Set;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.ServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/usuario")
@Tag(name = "Usuarios", description = "API para gestión de usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios", description = "Retorna la lista completa de usuarios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay usuarios registrados")
    })
    public ResponseEntity<List<Usuario>> findAll(){
        List<Usuario> usuarios = usuarioService.findAll();
        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Retorna un usuario específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> findById(
            @Parameter(description = "ID del usuario") @PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cuentas/{id}")
    @Operation(summary = "Obtener cuentas de usuario", description = "Retorna todas las cuentas asociadas a un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cuentas encontradas"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Set<Cuenta>> obtenerCuentasUsuario(
            @Parameter(description = "ID del usuario") @PathVariable Long id) {
        try {
            Set<Cuenta> cuentas = usuarioService.getCuentasByUsuario(id);
            return ResponseEntity.ok(cuentas);
        }
        catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/username")
    @Operation(summary = "Login de usuario", description = "Obtiene información de usuario por nombre de usuario para login")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<LoginDTO> getUsuarioByUsername(
            @Parameter(description = "Nombre de usuario") @RequestParam String username) {
        try {
            return ResponseEntity.ok(usuarioService.login(username));
        }
        catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{idUsuario}/cuenta-asociada/{idCuenta}")
    @Operation(summary = "Verificar cuenta asociada",
               description = "Verifica si una cuenta está asociada a un usuario específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Verificación exitosa")
    })
    public ResponseEntity<Boolean> verificarCuentaAsociada(
            @Parameter(description = "ID del usuario") @PathVariable Long idUsuario,
            @Parameter(description = "ID de la cuenta") @PathVariable Long idCuenta) {

        // El service ejecuta la lógica en la BD
        boolean esValido = usuarioService.cuentaAsociada(idUsuario, idCuenta);

        // Retorna 200 OK con el booleano en el cuerpo
        return ResponseEntity.ok(esValido);
    }

    @GetMapping("/tipo/{rol}")
    @Operation(summary = "Obtener usuarios por rol", description = "Retorna los IDs de usuarios con un rol específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados"),
        @ApiResponse(responseCode = "400", description = "Error en la consulta")
    })
    public ResponseEntity<Set<Long>> getUsuariosByRol(
            @Parameter(description = "Rol del usuario") @PathVariable String rol) {
        try {
            Set<Long> usuarios = usuarioService.getUsuarioByRol(rol);
            return ResponseEntity.ok(usuarios);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente")
    })
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> update(
            @RequestBody Usuario usuario,
            @Parameter(description = "ID del usuario") @PathVariable Long id) {
        Usuario usuarioActual = usuarioService.findById(id);
        if (usuarioActual != null) {
            usuarioActual.setNombre(usuario.getNombre());
            usuarioActual.setApellido(usuario.getApellido());
            // Actualizar otros campos necesarios...
            return ResponseEntity.ok(usuarioService.save(usuarioActual));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (usuarioService.findById(id) != null) {
            usuarioService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{idUsuario}/asignar-cuenta/{idCuenta}")
    @Operation(summary = "Asignar cuenta a usuario", description = "Asigna una cuenta existente a un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cuenta asignada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario o cuenta no encontrados")
    })
    public ResponseEntity<?> asignarCuenta(@PathVariable Long idUsuario, @PathVariable Long idCuenta) {
        Optional<Usuario> o = usuarioService.asignarCuenta(idUsuario, idCuenta);
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }
}