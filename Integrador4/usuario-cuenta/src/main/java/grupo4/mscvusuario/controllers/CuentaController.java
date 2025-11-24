package grupo4.mscvusuario.controllers;

import grupo4.mscvusuario.entity.Cuenta;
import grupo4.mscvusuario.service.CuentaService;
import grupo4.mscvusuario.service.exceptions.NotFoundException;
import grupo4.mscvusuario.service.exceptions.SaldoInsuficenteException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/cuenta") // http://localhost:8080/api/cuenta
@Tag(name = "Cuentas", description = "API para gestión de cuentas de usuario")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    @Operation(summary = "Obtener todas las cuentas", description = "Retorna la lista completa de cuentas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de cuentas obtenida exitosamente")
    })
    public List<Cuenta> findAll() {
        return cuentaService.findAll();
    }

    @GetMapping("/{id}") //http://localhost:8080/api/cuenta/1
    @Operation(summary = "Obtener cuenta por ID", description = "Retorna una cuenta específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cuenta encontrada"),
        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    public ResponseEntity<Cuenta> findById(
            @Parameter(description = "ID de la cuenta") @PathVariable Long id) {
        Cuenta cuenta = cuentaService.findById(id);
        if (cuenta != null) {
            return ResponseEntity.ok(cuenta);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crear cuenta", description = "Registra una nueva cuenta en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cuenta creada exitosamente")
    })
    public ResponseEntity<Cuenta> save(@RequestBody Cuenta cuenta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaService.save(cuenta));
    }

    @PatchMapping("/{id}/cambiar-estado-cuenta") // http://localhost:8080/api/cuenta/1/cambiar-estado-cuenta
    @Operation(summary = "Cambiar estado de cuenta",
               description = "Habilita o deshabilita una cuenta de usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado de cuenta actualizado"),
        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    public ResponseEntity<Cuenta> cambiarEstadoCuenta(
            @Parameter(description = "ID de la cuenta") @PathVariable Long id) {
        Cuenta cuentaActualizada = cuentaService.cambiarEstadoCuenta(id);
        if (cuentaActualizada != null) {
            return ResponseEntity.ok(cuentaActualizada);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/restar-saldo/{id}")
    @Operation(summary = "Restar saldo de cuenta",
               description = "Descuenta un monto específico del saldo de la cuenta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Saldo actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada"),
        @ApiResponse(responseCode = "409", description = "Saldo insuficiente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<?> restarSaldoCuenta(
            @Parameter(description = "ID de la cuenta") @PathVariable Long id,
            @Parameter(description = "Monto a restar") @RequestParam(name="montoRestar") BigDecimal montoArestar) {
        try {
            Cuenta cuentaActualizada = cuentaService.restarSaldoCuenta(id, montoArestar);
            return ResponseEntity.ok(cuentaActualizada);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (SaldoInsuficenteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}