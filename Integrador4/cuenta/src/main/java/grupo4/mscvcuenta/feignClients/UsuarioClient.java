package grupo4.mscvcuenta.feignClients;


import grupo4.mscvcuenta.dto.UsuarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "usuario-service", url = "${usuario.service.url}")
public interface UsuarioClient {

    //trer todos los usuario para probar el cliente feign
    @GetMapping("/usuarios")
    List<UsuarioDto> obtenerUsuarios();
}
