package grupo4.mscvcuenta.feignClients;


import grupo4.mscvcuenta.dto.UsuarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "usuario-service", url = "${usuario.service.url}")
public interface UsuarioClient {

    //trer todos los usuario para probar el cliente feign
    @GetMapping("/usuarios")
    List<UsuarioDto> obtenerUsuarios();

    // Nuevo metodo para obtener un usuario por su ID
    @GetMapping("/usuarios/{id}")
    UsuarioDto obtenerUsuarioPorId(@PathVariable Long id);
}
