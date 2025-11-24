package grupo4.facturacion.controller;

import grupo4.facturacion.dto.TotalFacturadoDTO;
import grupo4.facturacion.dto.ViajeFacturaRequestDTO;
import grupo4.facturacion.entity.Factura;
import grupo4.facturacion.service.FacturaService;
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
@RequestMapping("/api/facturas")
@Tag(name = "Facturas", description = "API para gestión de facturas y facturación de viajes")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping("/")
    @Operation(summary = "Obtener todas las facturas", description = "Retorna la lista completa de facturas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de facturas obtenida exitosamente")
    })
    public ResponseEntity<?> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(facturaService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener factura por ID", description = "Retorna una factura específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Factura encontrada")
    })
    public ResponseEntity<?> findById(
            @Parameter(description = "ID de la factura") @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facturaService.findById(id));
    }

    @GetMapping("/entre-fechas")
    @Operation(summary = "Buscar facturas entre fechas",
               description = "Retorna las facturas emitidas entre dos fechas específicas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Facturas encontradas")
    })
    public ResponseEntity<?> findByFechaBetween(
            @Parameter(description = "Fecha de inicio (formato ISO)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
            @Parameter(description = "Fecha de fin (formato ISO)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin) {
        return ResponseEntity.status(HttpStatus.OK).body(facturaService.findByFechaBetween(fechaInicio, fechaFin));
    }

    @PostMapping("/")
    @Operation(summary = "Crear factura", description = "Registra una nueva factura en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Factura creada exitosamente")
    })
    public ResponseEntity<?> save(@RequestBody Factura factura) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facturaService.save(factura));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar factura", description = "Elimina una factura del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Factura eliminada exitosamente")
    })
    public ResponseEntity<?> delete(
            @Parameter(description = "ID de la factura a eliminar") @PathVariable Long id) {
        facturaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Consultar total facturado en rango de meses
     * GET /api/facturas/total-facturado?anio=2025&mesDesde=1&mesHasta=6
     */
    @GetMapping("/total-facturado")
    @Operation(summary = "Obtener total facturado",
               description = "Consulta el total facturado en un rango de meses de un año específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Total calculado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    public ResponseEntity<?> getTotalFacturado(
            @Parameter(description = "Año a consultar") @RequestParam int anio,
            @Parameter(description = "Mes de inicio (1-12)") @RequestParam int mesDesde,
            @Parameter(description = "Mes de fin (1-12)") @RequestParam int mesHasta) {

        // Validación
        if (mesDesde < 1 || mesDesde > 12 || mesHasta < 1 || mesHasta > 12 || mesDesde > mesHasta) {
            return ResponseEntity.badRequest().body("Meses inválidos. Deben estar entre 1-12 y mesDesde <= mesHasta");
        }

        TotalFacturadoDTO total = facturaService.getTotalFacturadoPorRangoMeses(anio, mesDesde, mesHasta);
        return ResponseEntity.ok(total);
    }

    /**
     * Facturar viaje con usuarios premium y recargo por pausa
     * POST /api/facturas/viaje
     * Body: {
     *   "viajeId": 1,
     *   "usuarioId": 1,
     *   "distanciaKm": 15.5,
     *   "tarifaId": 1,
     *   "tiempoPausaMinutos": 20
     * }
     */
    @PostMapping("/viaje")
    @Operation(summary = "Facturar viaje",
               description = "Crea una factura para un viaje considerando tarifas, usuarios premium y recargos por pausa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Factura de viaje creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error al crear la factura")
    })
    public ResponseEntity<?> facturarViaje(@RequestBody ViajeFacturaRequestDTO request) {
        try {
            Factura factura = facturaService.crearFacturaDesdeViaje(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(factura);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear factura: " + e.getMessage());
        }
    }
}
