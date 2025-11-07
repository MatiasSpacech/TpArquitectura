package grupo4.mscvmonopatin.feignClients;

import grupo4.mscvmonopatin.feignModel.Parada;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "parada", url = "http://localhost:8085/paradas")
public interface ParadaFeignClient {

    @GetMapping("/{id}")
    Parada findById(@PathVariable Long id);
}
