package grupo4.mscvmonopatin.controllers;

import grupo4.mscvmonopatin.dtos.MonopatinDTO;
import grupo4.mscvmonopatin.dtos.MonopatinPatchDTO;
import grupo4.mscvmonopatin.dtos.ReporteMantenimientoDTO;
import grupo4.mscvmonopatin.model.Monopatin;
import grupo4.mscvmonopatin.repository.MonopatinRepository;
import grupo4.mscvmonopatin.services.MonopatinService;
import grupo4.mscvmonopatin.services.exceptions.InvalidEstadoException;
import grupo4.mscvmonopatin.services.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/monopatines")
@RequiredArgsConstructor
@Tag(name = "Monopatines", description = "API para gestión de monopatines (MongoDB)")
public class MonopatinController {

    private final MonopatinService service;

    @GetMapping
    @Operation(summary = "Obtener todos los monopatines", description = "Retorna la lista completa de monopatines")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de monopatines obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay monopatines registrados")
    })
    public ResponseEntity<List<MonopatinDTO>> findAll() {
        List<MonopatinDTO> monopatines = service.findAll();

        if (monopatines.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(monopatines);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener monopatín por ID", description = "Retorna un monopatín específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Monopatín encontrado"),
        @ApiResponse(responseCode = "404", description = "Monopatín no encontrado")
    })
    public ResponseEntity<MonopatinDTO> findById(
            @Parameter(description = "ID del monopatín") @PathVariable String id) {
        try {
            MonopatinDTO monopatin = service.findById(id);
            return ResponseEntity.ok(monopatin);
        }
        catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/parada")
    @Operation(summary = "Obtener monopatín con parada",
               description = "Retorna un monopatín junto con información de su parada asociada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Información obtenida exitosamente")
    })
    public ResponseEntity<Map<String, Object>> getMonopatinConParada(
            @Parameter(description = "ID del monopatín") @PathVariable String id) {
        return ResponseEntity.ok(service.getMonopatinConParada(id));
    }

    @GetMapping("/reportes-mantenimiento/{kmMaximo}")
    @Operation(summary = "Reporte de mantenimiento",
               description = "Obtiene monopatines que requieren mantenimiento según kilómetros máximos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay monopatines que requieran mantenimiento")
    })
    public ResponseEntity<List<ReporteMantenimientoDTO>> getMonopatinesManetinimiento(
            @Parameter(description = "Kilómetros máximos antes de mantenimiento") @PathVariable Integer kmMaximo) {
        List<ReporteMantenimientoDTO> reportes = service.getReportesMantenimiento(kmMaximo);

        if(reportes.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reportes);
    }

    @PostMapping
    @Operation(summary = "Crear monopatín", description = "Registra un nuevo monopatín en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Monopatín creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Parada no encontrada")
    })
    public ResponseEntity<?> save(@RequestBody Monopatin monopatin) {
        try {
            MonopatinDTO nuevoMonopatin = service.save(monopatin);
            return new ResponseEntity<>(nuevoMonopatin, HttpStatus.CREATED);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar monopatín", description = "Elimina un monopatín del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Monopatín eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Monopatín no encontrado")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del monopatín a eliminar") @PathVariable String id) {
        try {
            service.delete(id);
            // Retorna 204 (No Content) si se elimino correctamente
            return ResponseEntity.noContent().build();
        }
        catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar monopatín parcialmente",
               description = "Actualiza campos específicos de un monopatín")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Monopatín actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Monopatín no encontrado"),
        @ApiResponse(responseCode = "400", description = "Estado inválido")
    })
    public ResponseEntity<?> partialUpdate(
            @Parameter(description = "ID del monopatín") @PathVariable String id,
            @RequestBody MonopatinPatchDTO edit) {
        try {
            MonopatinDTO monopatinEditado = service.patch(id, edit);
            return ResponseEntity.ok(monopatinEditado);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/{id}/estado/{estado}")
    public ResponseEntity<?> setEstado(@PathVariable String id, @PathVariable String estado) {
        try {
            MonopatinDTO monopatinEditado = service.setEstadoMonopatin(id, estado);
            return ResponseEntity.ok(monopatinEditado);
        }
        catch (InvalidEstadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/parada/{idParada}")
    public ResponseEntity<List<MonopatinDTO>> findMonopatinesByIdParada(@PathVariable Long idParada,
                                                                        @RequestParam(name= "estado", required = false) String estado){
        List<MonopatinDTO> monopatinesEnParada = new ArrayList<>();
        if(estado!=null && !estado.trim().isEmpty()){
            monopatinesEnParada.addAll(
                    service.findMonopatinesPorEstadoByIdParada(idParada,estado));
        }
        else{
            monopatinesEnParada.addAll(service.findMonopatinesByIdParada(idParada));
        }

        return ResponseEntity.ok(monopatinesEnParada);
    }

}
