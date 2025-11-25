package grupo4.tarifas.controller;

import grupo4.tarifas.dto.TarifaDTO;
import grupo4.tarifas.entity.Tarifa;
import grupo4.tarifas.service.TarifaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/tarifas") //http://localhost:8080/api/tarifas/
@Tag(name = "Tarifas", description = "API para gestión de tarifas y ajuste de precios")
public class TarifaController {

    @Autowired
    private TarifaService tarifaService;

    /**
     * Ajuste de precios desde una fecha
     * POST /api/tarifas/ajustar-precio
     * Body: { "monto": 60.0, "montoExtra": 150.0, "fecha": "2025-12-01", ... }
     */
    @PostMapping("/ajustar-precio")
    @Operation(summary = "Ajustar precio desde una fecha",
               description = "Crea una nueva tarifa que entrará en vigencia desde una fecha específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tarifa creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error al crear la tarifa")
    })
    public ResponseEntity<?> ajustarPrecioDesde(@RequestBody Tarifa nuevaTarifa) {
        try {
            TarifaDTO tarifaCreada = tarifaService.crearTarifa(nuevaTarifa);
            return ResponseEntity.status(HttpStatus.CREATED).body(tarifaCreada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al ajustar precio: " + e.getMessage());
        }
    }

    @PostMapping("/actualizar-desde-fecha")
    @Operation(summary = "Actualizar tarifa desde fecha",
               description = "Actualiza una tarifa existente con un porcentaje de incremento desde una fecha")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarifa actualizada exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error al actualizar la tarifa")
    })
    public ResponseEntity<?> actualizarTarifaDesdeFecha(
            @Parameter(description = "Fecha de vigencia (formato ISO)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fecha,
            @Parameter(description = "Porcentaje de incremento") @RequestParam double porcentajeIncremento) {
        try {
            System.out.println("Fecha recibida: " + fecha);
            System.out.println("Porcentaje recibido: " + porcentajeIncremento);
            TarifaDTO resultado = tarifaService.actualizarTarifaDesdeFecha(fecha, porcentajeIncremento);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage() + " - Causa: " + (e.getCause() != null ? e.getCause().getMessage() : "N/A"));
        }
    }

    @PostMapping("/actualizar")
    @Operation(summary = "Actualizar tarifa", description = "Actualiza los datos de una tarifa existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarifa actualizada exitosamente")
    })
    public ResponseEntity<?> actualizarTarifa(@RequestBody Tarifa tarifa) {
        return ResponseEntity.ok(tarifaService.actualizarTarifa(tarifa));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tarifa", description = "Elimina una tarifa del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Tarifa eliminada exitosamente")
    })
    public ResponseEntity<?> eliminarTarifa(
            @Parameter(description = "ID de la tarifa a eliminar") @PathVariable Long id) {
        tarifaService.eliminarTarifa(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/crear")
    @Operation(summary = "Crear tarifa", description = "Registra una nueva tarifa en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tarifa creada exitosamente")
    })
    public ResponseEntity<?> crearTarifa(@RequestBody Tarifa tarifa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tarifaService.crearTarifa(tarifa));
    }

    @GetMapping("/")
    @Operation(summary = "Obtener todas las tarifas", description = "Retorna la lista completa de tarifas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tarifas obtenida exitosamente")
    })
    public ResponseEntity<?> findAllTarifas() {
        return ResponseEntity.status(HttpStatus.OK).body(tarifaService.findAllTarifas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener tarifa por ID", description = "Retorna una tarifa específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarifa encontrada")
    })
    public ResponseEntity<?> findTarifaById(
            @Parameter(description = "ID de la tarifa") @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(tarifaService.findTarifaById(id));
    }
}
