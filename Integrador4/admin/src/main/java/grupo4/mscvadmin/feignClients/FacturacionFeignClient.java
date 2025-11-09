package grupo4.mscvadmin.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="mscv-facturacion", url="http://localhost:8083/api/facturacion")
public interface FacturacionFeignClient {

    @GetMapping("/reportes")
    String generarReporteFacturacion();

}
