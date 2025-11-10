package grupo4.viajes.feignClients;

import grupo4.viajes.feignModels.Cuenta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cuentas", url = "http://localhost:8082/api/cuenta")
public interface CuentaFeignClient {

    @GetMapping("/{id}")
    Cuenta findById(@PathVariable Long id);
}
