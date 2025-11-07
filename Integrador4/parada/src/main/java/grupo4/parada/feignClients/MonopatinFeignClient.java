package grupo4.parada.feignClients;

import grupo4.parada.feignModel.Monopatin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "monopatin", url = "http://localhost:8084/monopatines")
public interface MonopatinFeignClient {

    @GetMapping("/monopatin/idParada/{idParada}")
    List<Monopatin> findMonopatinesByIdParada(@PathVariable Long idParada);

}
