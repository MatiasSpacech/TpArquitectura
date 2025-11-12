package grupo4.viajes.feignClients;

import grupo4.viajes.feignModels.Cuenta;
import grupo4.viajes.feignModels.Monopatin;
import grupo4.viajes.feignModels.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient(name = "usuarios", url = "http://localhost:8082/api/usuario")
public interface UsuarioFeignClient {

    @GetMapping("/{id}")
    Usuario findById(@PathVariable Long id);

    @GetMapping("/{idUsuario}/cuenta-asociada/{idCuenta}")
    Boolean cuentaAsociada(@PathVariable Long idUsuario, @PathVariable Long idCuenta);

    @GetMapping("/cuentas/{id}")
    Set<Cuenta> getCuentasByUsuario(@PathVariable Long id);
}
