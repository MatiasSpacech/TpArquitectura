package org.example.ia.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "admin", url = "${admin.service.url:http://localhost:8081}")
public interface AdminFeignClient {

    @GetMapping("/api/admin/reportes/monopatines")
    String generarReporteMonopatines();

    // Corregí la doble barra "//" que tenías en el comentario del controller por una simple
    @GetMapping("/api/admin/{id}/anular")
    String anularUsuario(@PathVariable("id") String id);

    @GetMapping("/api/admin/monopatines-viajes")
    String consultarMonopatinesPorViajes(@RequestParam("cantidadViajes") int cantidadViajes,
                                         @RequestParam("anio") int anio);

    @GetMapping("/api/admin/facturacion")
    String consultarTotalFacturado(@RequestParam("anio") int anio,
                                   @RequestParam("mesInicio") int mesInicio,
                                   @RequestParam("mesFin") int mesFin);

    @GetMapping("/api/admin/usuarios/mas-utilizan")
    String verUsuariosTop(@RequestParam("periodo") String periodo,
                          @RequestParam("tipoUsuario") String tipoUsuario);

    // El controller recibe Date, pero por HTTP GET conviene mandar String ISO
    @GetMapping("/api/admin/ajuste-precios")
    String hacerAjustePrecios(@RequestParam("nuevosPrecios") double nuevosPrecios,
                              @RequestParam("fechaInicio") String fechaInicio);
}