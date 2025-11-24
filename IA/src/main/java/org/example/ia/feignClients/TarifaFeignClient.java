package org.example.ia.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "tarifa", url = "${facturacion.service.url:http://localhost:8086}")
public interface TarifaFeignClient {

    @GetMapping("/api/tarifas/")
    Object findAllTarifas();

    @PostMapping("/api/tarifas/crear")
    Map<String, Object> crearTarifa(@RequestBody Map<String, Object> tarifa);
}