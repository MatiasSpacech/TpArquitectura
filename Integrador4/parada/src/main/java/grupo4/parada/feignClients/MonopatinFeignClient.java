package grupo4.parada.feignClients;

import grupo4.parada.feignModel.Monopatin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "monopatin", url = "http://localhost:8084/api/monopatines")
public interface MonopatinFeignClient {

    @GetMapping("/parada/{idParada}")
    List<Monopatin> findMonopatinesByIdParada(@PathVariable Long idParada);

    @GetMapping("/parada/{idParada}?estado=LIBRE")
    List<Monopatin> findMonopatinesLibresByIdParada(@PathVariable Long idParada);
}
