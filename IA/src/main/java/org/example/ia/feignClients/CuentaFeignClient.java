package org.example.ia.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@FeignClient(name = "cuenta", url = "${usuario.service.url:http://localhost:8082}")
public interface CuentaFeignClient {

    @GetMapping("/api/cuenta")
    List<Map<String, Object>> findAll();

    @GetMapping("/api/cuenta/{id}")
    Map<String, Object> findById(@PathVariable("id") Long id);

    @PostMapping("/api/cuenta")
    Map<String, Object> save(@RequestBody Map<String, Object> cuenta);

    // OJO: El controller usa @PatchMapping
    @PatchMapping("/api/cuenta/{id}/cambiar-estado-cuenta")
    Map<String, Object> cambiarEstadoCuenta(@PathVariable("id") Long id);

    // OJO: El controller usa @PutMapping y el param se llama "montoRestar"
    @PutMapping("/api/cuenta/restar-saldo/{id}")
    Map<String, Object> restarSaldoCuenta(@PathVariable("id") Long id,
                                          @RequestParam("montoRestar") BigDecimal montoRestar);
}