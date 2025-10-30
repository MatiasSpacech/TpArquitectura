package grupo4.mscvadmin.services;

import grupo4.mscvadmin.feignClients.UsuarioFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioFeignClient usuarioFeignClient;


    public String getUsuario() {
        return usuarioFeignClient.holaMundo();
    }
}
