package grupo4.viajes.feignClients;

import grupo4.viajes.feignModels.Monopatin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "monopatin", url = "http://localhost:8084/api/monopatines")
public interface MonopatinFeignClient {

    @GetMapping("/{id}")
    Monopatin findById(@PathVariable String id);

    @PutMapping("/{id}/estado/{estado}")
    Monopatin updateEstado(@PathVariable String id, @PathVariable String estado);
}
