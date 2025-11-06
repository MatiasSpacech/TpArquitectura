package grupo4.mscvcuenta.controller;

    import grupo4.mscvcuenta.dto.UsuarioDto;
    import grupo4.mscvcuenta.entity.Cuenta;
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
    }