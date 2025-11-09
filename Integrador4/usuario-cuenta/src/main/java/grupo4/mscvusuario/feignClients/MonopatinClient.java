package grupo4.mscvusuario.feignClients;

import grupo4.mscvusuario.dto.MonopatinDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "monopatin-service", url = "${monopatin.service.url}")
public interface MonopatinClient {
    @GetMapping("/monopatines")
    List<MonopatinDto> obtenerMonopatiness();
}

