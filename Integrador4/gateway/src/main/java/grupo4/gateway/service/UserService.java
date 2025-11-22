package grupo4.gateway.service;

import grupo4.gateway.feignClients.UsuarioFeign;
import grupo4.gateway.feignModel.UserResponse;
import grupo4.gateway.service.dto.user.UserDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioFeign usuarioFeign;

    public long saveUser(UserDTO request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        UserResponse userGuardado = usuarioFeign.createUser(request);
        return userGuardado.getId();
    }
}
