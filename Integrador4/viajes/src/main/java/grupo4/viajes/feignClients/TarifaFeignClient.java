package grupo4.viajes.feignClients;

import grupo4.viajes.feignModels.Tarifa;
import lombok.Getter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tarifas", url = "http://localhost:8082/tarifas")
public interface TarifaFeignClient {

    @GetMapping("/{id}")
    Tarifa findById(@PathVariable Long id);
}
