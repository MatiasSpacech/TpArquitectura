package grupo4.mscvusuario.service;

import grupo4.mscvmonopatin.dto.MonopatinDTO;
import grupo4.mscvusuario.entity.Usuario;
import grupo4.mscvusuario.feignClients.MonopatinClient;
import grupo4.mscvusuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MonopatinClient monopatinClient;

    @Transactional(readOnly = true)
    public List<MonopatinDTO> obtenerTodosLosMonopatines() {
        // ¡Magia! Feign hace la llamada REST por nosotros.
        // Parece que estamos llamando a un metodo local.
        return monopatinClient.obtenerMonopatiness();
    }

    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario crearUsuario(Usuario usuario) {
        return  usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
}
