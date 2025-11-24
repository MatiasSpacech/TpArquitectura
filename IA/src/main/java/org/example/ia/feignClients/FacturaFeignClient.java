package org.example.ia.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "factura", url = "${facturacion.service.url:http://localhost:8083}")
public interface FacturaFeignClient {

    @GetMapping("/api/facturas/total-facturado")
    Object getTotalFacturado(@RequestParam("anio") int anio,
                             @RequestParam("mesDesde") int mesDesde,
                             @RequestParam("mesHasta") int mesHasta);
}