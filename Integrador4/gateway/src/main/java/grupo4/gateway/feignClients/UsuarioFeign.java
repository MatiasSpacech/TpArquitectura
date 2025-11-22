package grupo4.gateway.feignClients;

import grupo4.gateway.feignModel.UserResponse;
import grupo4.gateway.service.dto.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// SE UTILIZA PARA OBTENER USUARIO POR EMAIL Y GUARDAR UN USUARIO NUEVO
@FeignClient(name = "usuario", url = "http://localhost:8082/api/usuario")
public interface UsuarioFeign {

    @GetMapping("/username")
    UserResponse findUserByUsername(@RequestParam String username);

    @PostMapping
    UserResponse createUser(UserDTO user);
}
