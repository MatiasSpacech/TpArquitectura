package grupo4.mscvcuenta.controller;

    import grupo4.mscvcuenta.dto.UsuarioDto;
    import grupo4.mscvcuenta.entity.Cuenta;
    import grupo4.mscvcuenta.entity.Estado;
    import grupo4.mscvcuenta.service.CuentaService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.net.URI;
    import java.util.List;

    @RestController
    @RequestMapping("/cuentas")
    public class CuentaController {

        @Autowired
        private CuentaService cuentaService;

        @GetMapping("/hola")
        public String hola() {
            return "Como estas desde CuentaController";
        }

        @GetMapping
        public ResponseEntity<List<Cuenta>> obtenerTodos(){
            try{
                List<Cuenta> cuentas = cuentaService.findAll();
                return ResponseEntity.ok(cuentas);
            } catch (Exception e){
                return ResponseEntity.status(500).build();
            }
        }

        @GetMapping("/{id}")
        public ResponseEntity<Cuenta> obtenerPorId(@PathVariable String id){
            try{
                Cuenta cuenta = cuentaService.getCuentaById(id);
                if(cuenta != null){
                    return ResponseEntity.ok(cuenta);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (Exception e){
                return ResponseEntity.status(500).build();
            }
        }

        @PostMapping
        public ResponseEntity<Cuenta> crearCuenta(@RequestBody Cuenta cuenta){
            try{
                Cuenta nuevaCuenta = cuentaService.createCuenta(cuenta);
                // si existe un campo identificador en la entidad, ajustar la URI según corresponda
                return ResponseEntity.created(URI.create("/cuentas/" + nuevaCuenta.getNroCuenta())).body(nuevaCuenta);
            } catch (Exception e){
                return ResponseEntity.status(500).build();
            }
        }

        @PutMapping("/{id}")
        public ResponseEntity<Cuenta> actualizarCuenta(@PathVariable String id, @RequestBody Cuenta cuenta){
            try{
                // asegurarse de que la entidad tenga el id correcto antes de actualizar
                cuenta.setNroCuenta(id);
                Cuenta cuentaActualizada = cuentaService.updateCuenta(cuenta);
                return ResponseEntity.ok(cuentaActualizada);
            } catch (Exception e){
                return ResponseEntity.status(500).build();
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminarCuenta(@PathVariable String id){
            try{
                cuentaService.deleteCuenta(id);
                return ResponseEntity.noContent().build();
            } catch (Exception e){
                return ResponseEntity.status(500).build();
            }
        }

        // /cuenta/usuario
//        @GetMapping("/usuarios")
//        public ResponseEntity<List<UsuarioDto>> obtenerTodosLosUsuarios() {
//            try {
//                List<UsuarioDto> usuarios = cuentaService.obtenerTodosLosUsuarios();
//                return ResponseEntity.ok(usuarios);
//            } catch (Exception e) {
//                return ResponseEntity.status(500).build();
//            }
//        }

        @GetMapping("/usuarios")
        public ResponseEntity<List<UsuarioDto>> obtenerTodosLosUsuarios() {
            return ResponseEntity.ok(cuentaService.obtenerTodosLosUsuarios());
        }

        //asociar cuenta a usuario
        // Nuevo endpoint para asociar un usuario a una cuenta
        @PutMapping("/{nroCuenta}/usuarios/{idUsuario}")
        public ResponseEntity<Cuenta> asociarUsuario(@PathVariable String nroCuenta, @PathVariable Long idUsuario) {
            Cuenta cuentaActualizada = cuentaService.asociarUsuario(nroCuenta, idUsuario);
            return ResponseEntity.ok(cuentaActualizada);
        }

        // Nuevo endpoint para desasociar un usuario de una cuenta
        @DeleteMapping("/{nroCuenta}/usuarios/{idUsuario}")
        public ResponseEntity<Cuenta> desasociarUsuario(@PathVariable String nroCuenta, @PathVariable Long idUsuario) {
            // Usar DELETE es más semántico para eliminar una asociación
            Cuenta cuentaActualizada = cuentaService.desasociarUsuario(nroCuenta, idUsuario);
            return ResponseEntity.ok(cuentaActualizada);
        }


        /**
         * Endpoint para cambiar el estado de una cuenta (ej. anularla).
         * El nuevo estado se pasa como un parámetro en la URL.
         * Ejemplo de llamada: PUT http://localhost:8082/cuentas/1/estado?nuevoEstado=SUSPENDIDA
         */
        @PutMapping("/{idCuenta}/estado")
        public ResponseEntity<?> cambiarEstadoDeCuenta(@PathVariable String idCuenta, @RequestParam Estado nuevoEstado) {
            try {
                // Llama al servicio para ejecutar la lógica de negocio
                Cuenta cuentaActualizada = cuentaService.cambiarEstado(idCuenta, nuevoEstado);
                return ResponseEntity.ok(cuentaActualizada);
            } catch (IllegalArgumentException e) {
                // Si el servicio lanza una excepción (porque la cuenta no existe),
                // devolvemos un error 404 Not Found.
                return ResponseEntity.notFound().build();
            }
        }
    }