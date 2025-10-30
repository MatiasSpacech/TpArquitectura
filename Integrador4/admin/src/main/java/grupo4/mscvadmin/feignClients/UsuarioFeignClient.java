package grupo4.mscvadmin.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="mscv-usuario", url="http://localhost:8087/usuario")
public interface UsuarioFeignClient {

    @GetMapping("/")
    String holaMundo();
}
