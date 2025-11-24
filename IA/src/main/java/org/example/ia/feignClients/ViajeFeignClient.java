package org.example.ia.feignClients;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// Asegúrate de configurar 'viajes.service.url' en tu application.properties
// (Por defecto asumo puerto 8084, ajustalo al real de tu microservicio de viajes)
@FeignClient(name = "viajes", url = "${viajes.service.url:http://localhost:8087}")
public interface ViajeFeignClient {

    @GetMapping("/api/viajes")
    List<Map<String, Object>> findAll();

    @GetMapping("/api/viajes/{id}")
    Map<String, Object> findById(@PathVariable("id") Long id);

    /**
     * Endpoint complejo de reportes.
     * Mapeamos todos los @RequestParam como no requeridos (required = false)
     * Retorna Object porque el controller puede devolver List o Map según el caso.
     */
    @GetMapping("/api/viajes/reportes")
    Object getReporteViajes(
            @RequestParam(value = "anio", required = false) Integer anio,
            @RequestParam(value = "cantidad", required = false) Integer cantidad,
            @RequestParam(value = "anioDesde", required = false) Integer anioDesde,
            @RequestParam(value = "anioHasta", required = false) Integer anioHasta,
            @RequestParam(value = "idUsuario", required = false) Long idUsuario,
            @RequestParam(value = "rol", required = false) String rol
    );

    @PostMapping("/api/viajes")
    Map<String, Object> save(@RequestBody Map<String, Object> viaje);

    @PatchMapping("/api/viajes/finalizar-viaje/{id}")
    Map<String, Object> finalizarViaje(@PathVariable("id") Long id);

    @DeleteMapping("/api/viajes/{id}")
    void delete(@PathVariable("id") Long id);
}
