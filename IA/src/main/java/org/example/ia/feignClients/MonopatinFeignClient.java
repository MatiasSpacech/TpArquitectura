package org.example.ia.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "monopatin", url = "${monopatin.service.url:http://localhost:8084}")
public interface MonopatinFeignClient {

    @GetMapping("/api/monopatines")
    List<Map<String, Object>> findAll();

    @GetMapping("/api/monopatines/{id}")
    Map<String, Object> findById(@PathVariable("id") String id);

    @PostMapping("/api/monopatines")
    Map<String, Object> save(@RequestBody Map<String, Object> monopatin);

    @GetMapping("/api/monopatines/reportes-mantenimiento/{kmMaximo}")
    List<Map<String, Object>> getReportesMantenimiento(@PathVariable("kmMaximo") Integer kmMaximo);

    @PutMapping("/api/monopatines/{id}/estado/{estado}")
    Map<String, Object> setEstado(@PathVariable("id") String id, @PathVariable("estado") String estado);

    // El parámetro 'estado' es opcional en el controller, aquí lo permitimos pasar nulo
    @GetMapping("/api/monopatines/parada/{idParada}")
    List<Map<String, Object>> findMonopatinesByIdParada(@PathVariable("idParada") Long idParada,
                                                        @RequestParam(value = "estado", required = false) String estado);
}