package grupo4.parada.controllers;

import grupo4.parada.dtos.ParadaDTO;
import grupo4.parada.dtos.ParadaPatchDTO;
import grupo4.parada.model.Parada;
import grupo4.parada.services.ParadaService;
import grupo4.parada.services.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/paradas")
@RequiredArgsConstructor
@Tag(name = "Paradas", description = "API para gestión de paradas de monopatines")
public class ParadaController {

    private final ParadaService service;

    @GetMapping
    @Operation(summary = "Obtener todas las paradas", description = "Retorna la lista completa de paradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de paradas obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay paradas registradas")
    })
    public ResponseEntity<List<ParadaDTO>> findALl() {
        List<ParadaDTO> paradas = service.findAll();

        if(paradas.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(paradas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener parada por ID", description = "Retorna una parada específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Parada encontrada"),
        @ApiResponse(responseCode = "404", description = "Parada no encontrada")
    })
    public ResponseEntity<ParadaDTO> findById(
            @Parameter(description = "ID de la parada") @PathVariable Long id){
        try {
            return ResponseEntity.ok(service.findById(id));
        }
        catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cercanas")
    @Operation(summary = "Buscar paradas cercanas",
               description = "Busca paradas cercanas a una ubicación específica (latitud y longitud)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paradas encontradas"),
        @ApiResponse(responseCode = "404", description = "No hay paradas cercanas"),
        @ApiResponse(responseCode = "500", description = "Error al buscar paradas")
    })
    public ResponseEntity<?> findParadasCercanas(
                @Parameter(description = "Latitud de la ubicación") @RequestParam(name = "latitud") Double latitud,
                @Parameter(description = "Longitud de la ubicación") @RequestParam(name = "longitud") Double longitud) {
        try {
            List<ParadaDTO> paradas = service.findParadasCercanas(latitud, longitud);
            if(paradas.isEmpty()){
                return new ResponseEntity<>("No hay paradas cercanas", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(paradas);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar paradas cercanas: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/monopatines/{id}")
    @Operation(summary = "Obtener monopatines en parada",
               description = "Retorna los monopatines disponibles en una parada específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Información obtenida exitosamente"),
        @ApiResponse(responseCode = "404", description = "Parada no encontrada o sin monopatines")
    })
    public ResponseEntity<?> findMonopatinesByParada(
            @Parameter(description = "ID de la parada") @PathVariable Long id,
            @Parameter(description = "Estado del monopatín (ej: 'libre')") @RequestParam(name= "estado", required = false) String estado){
        try {
            Map<String,Object> retorno;
            if(estado != null && !estado.trim().isEmpty()){
                retorno = service.findParadaConMonopatinesLibres(id);
            } else {
                retorno = service.findParadaConMonopatines(id);
            }

            if(retorno.get("monopatines").getClass().equals(String.class) ||
               retorno.get("monopatinesLibres") != null && retorno.get("monopatinesLibres").getClass().equals(String.class)){
                return new ResponseEntity<>("Esta parada no tiene monopatines o no están libres",
                        HttpStatus.NOT_FOUND);
            }

            return ResponseEntity.ok(retorno);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/monopatines-libres/{id}")
    @Operation(summary = "Obtener monopatines libres en parada",
               description = "Retorna únicamente los monopatines libres de una parada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Monopatines libres obtenidos"),
        @ApiResponse(responseCode = "204", description = "No hay monopatines libres"),
        @ApiResponse(responseCode = "404", description = "Parada no encontrada")
    })
    public ResponseEntity<?> findMonopatinesLibresByParada(
            @Parameter(description = "ID de la parada") @PathVariable Long id){
        try {
            Map<String,Object> retorno = service.findParadaConMonopatinesLibres(id);

            if(retorno.get("monopatinesLibres").getClass().equals(String.class)){
                return new ResponseEntity<>(retorno.get("monopatinesLibres")
                        ,HttpStatus.NO_CONTENT);
            }

            return ResponseEntity.ok(retorno);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ParadaDTO> save(@RequestBody Parada parada) {
        try {
            ParadaDTO nuevaParada = service.save(parada);
            return new ResponseEntity<>(nuevaParada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
        catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdate(@PathVariable Long id, @RequestBody ParadaPatchDTO nuevaEditada) {
        try {
            ParadaDTO paradaEditada = service.patch(id,nuevaEditada);
            return ResponseEntity.ok(paradaEditada);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
