package grupo4.viajes.feignClients;

import grupo4.viajes.feignModels.Cuenta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "cuentas", url = "http://localhost:8082/api/cuenta")
public interface CuentaFeignClient {

    @GetMapping("/{id}")
    Cuenta findById(@PathVariable Long id);

    @PutMapping("/restar-saldo/{id}")
    Cuenta restarSaldo(@PathVariable Long id, @RequestParam(name = "montoRestar") BigDecimal resta);
}
