package org.example.ia.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "parada", url = "${monopatin.service.url:http://localhost:8085}")
public interface ParadaFeignClient {

    @GetMapping("/api/paradas")
    List<Map<String, Object>> findAll();

    @GetMapping("/api/paradas/cercanas")
    List<Map<String, Object>> findParadasCercanas(@RequestParam("latitud") Double latitud,
                                                  @RequestParam("longitud") Double longitud);

    @GetMapping("/api/paradas/monopatines/{id}")
    Map<String, Object> findMonopatinesByParada(@PathVariable("id") Long id);
}