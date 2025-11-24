package grupo4.viajes.controllers;

import grupo4.viajes.dtos.ReporteViajePeriodoDTO;
import grupo4.viajes.dtos.ReporteViajeUsuariosDTO;
import grupo4.viajes.dtos.ViajeDTO;
import grupo4.viajes.model.Viaje;
import grupo4.viajes.services.ViajeService;
import grupo4.viajes.services.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/viajes")
@Tag(name = "Viajes", description = "API para gestión de viajes y reportes")
public class ViajeController {
    private final ViajeService service;

    @GetMapping
    @Operation(summary = "Obtener todos los viajes", description = "Retorna la lista completa de viajes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de viajes obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay viajes registrados")
    })
    public ResponseEntity<?> findAll() {
        List<ViajeDTO> viajes = service.findAll();

        if(viajes.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(viajes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener viaje por ID", description = "Retorna un viaje específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Viaje encontrado"),
        @ApiResponse(responseCode = "404", description = "Viaje no encontrado")
    })
    public ResponseEntity<ViajeDTO> findById(
            @Parameter(description = "ID del viaje") @PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        }
        catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // REPORTES ADMINISTRATIVOS
    @GetMapping("/reportes")
    @Operation(summary = "Obtener reportes de viajes",
               description = "Genera diferentes tipos de reportes según los parámetros proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay datos para el reporte"),
        @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
        @ApiResponse(responseCode = "500", description = "Error al generar el reporte")
    })
    public ResponseEntity<?> getReporteViajes(
            @Parameter(description = "Año específico para el reporte") @RequestParam(required = false, name = "anio") Integer anio,
            @Parameter(description = "Cantidad mínima de viajes") @RequestParam(required = false, name = "cantidad") Integer cantidad,
            @Parameter(description = "Año de inicio del período") @RequestParam(required = false, name = "anioDesde") Integer anioDesde,
            @Parameter(description = "Año de fin del período") @RequestParam(required = false, name = "anioHasta") Integer anioHasta,
            @Parameter(description = "Rol del usuario") @RequestParam(required = false, name = "rol") String rol) {

        try {
            // Reporte por año específico
            if (anio != null && cantidad != null) {
                List<ReporteViajePeriodoDTO> reportes = service.getReporteViajeAnio(anio, cantidad);
                return reportes.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(reportes);
            }

            // Reporte general por período
            if (anioDesde != null && anioHasta != null) {
                List<ReporteViajeUsuariosDTO> reportes = service.getReporteViajesPorUsuariosPeriodo(anioDesde, anioHasta, rol);
                return reportes.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(reportes);
            }

            return ResponseEntity.badRequest()
                .body("Parámetros inválidos. Opciones: (anio+cantidad) | (anioDesde+anioHasta+idUsuario) | (anioDesde+anioHasta)");
                
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al generar reporte: " + e.getMessage());
        }
    }

    // REPORTES A SOLICITAR POR USUARIOS
    @GetMapping("/reportes-usuario")
    @Operation(summary = "Obtener reporte de viajes por usuario",
               description = "Genera un reporte de viajes de un usuario específico en un período")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay datos para el reporte"),
        @ApiResponse(responseCode = "500", description = "Error al generar el reporte")
    })
    public ResponseEntity<?> getReporteViajesUsuario(
            @Parameter(description = "Año de inicio del período", required = true) @RequestParam(required = true, name = "anioDesde") Integer anioDesde,
            @Parameter(description = "Año de fin del período", required = true) @RequestParam(required = true, name = "anioHasta") Integer anioHasta,
            @Parameter(description = "ID del usuario", required = true) @RequestParam(required = true, name = "idUsuario") Long idUsuario){
        try {
            // Reporte de usuario en período y cuentas asociadas
            Map<String, Object> reportes = service.getReportesUsuarioYasociadosPerido(idUsuario, anioDesde, anioHasta);
            return reportes.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(reportes);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al generar reporte: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Viaje body) {
        try {
            ViajeDTO nuevoViaje = service.save(body);
            return new ResponseEntity<>(nuevoViaje, HttpStatus.CREATED);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/finalizar-viaje/{id}")
    public ResponseEntity<?> finalizarViaje(@PathVariable Long id){
        try {
            Map<String,Object> retorno = service.finalizarViaje(id);
            return new ResponseEntity<>(retorno, HttpStatus.OK);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.noContent().build();
        }
        catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
