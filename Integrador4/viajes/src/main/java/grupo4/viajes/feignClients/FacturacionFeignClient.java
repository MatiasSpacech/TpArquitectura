package grupo4.viajes.feignClients;

import grupo4.viajes.feignModels.Factura;
import grupo4.viajes.feignModels.FacturaEmitida;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient (name = "facturacion", url = "http://localhost:8083/api/facturas")
public interface FacturacionFeignClient {

    @PostMapping("/viaje")
    FacturaEmitida generarFactura(@RequestBody Factura factura);
}
