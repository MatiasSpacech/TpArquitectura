package org.example.ia.feignClients;


import org.example.ia.dto.RespuestaApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient(name = "usuario", url = "${usuario.service.url:http://localhost:8082}")

public interface UsuarioFeingCliente {



    @GetMapping("/api/usuario")
    List<Map<String, Object>> findAll();

    @GetMapping("/api/usuario/{id}")
    Map<String, Object> findById(@PathVariable("id") Long id);

    @GetMapping("/api/usuario/cuentas/{id}")
    Set<Map<String, Object>> obtenerCuentasUsuario(@PathVariable("id") Long id);

    @GetMapping("/api/usuario/tipo/{rol}")
    Set<Long> getUsuariosByRol(@PathVariable("rol") String rol);

    @PostMapping("/api/usuario")
    Map<String, Object> save(@RequestBody Map<String, Object> usuario);

    @PutMapping("/api/usuario/{id}")
    Map<String, Object> update(@RequestBody Map<String, Object> usuario, @PathVariable("id") Long id);

    @DeleteMapping("/api/usuario/{id}")
    void delete(@PathVariable("id") Long id);

    @PutMapping("/api/usuario/{idUsuario}/asignar-cuenta/{idCuenta}")
    Map<String, Object> asignarCuenta(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idCuenta") Long idCuenta);

    @GetMapping("/api/usuario/info-actual")
    Map<String, Object> getUsuarioActual(@RequestHeader("Authorization") String token);

}


