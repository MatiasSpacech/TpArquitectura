package grupo4.mscvusuario.service;

import grupo4.mscvusuario.dtos.LoginDTO;
import grupo4.mscvusuario.entity.Cuenta;
import grupo4.mscvusuario.entity.EstadoCuenta;
import grupo4.mscvusuario.entity.Rol;
import grupo4.mscvusuario.entity.Usuario;
import grupo4.mscvusuario.repository.CuentaRepository;
import grupo4.mscvusuario.repository.UsuarioRepository;
import grupo4.mscvusuario.service.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CuentaRepository cuentaRepository; // Necesario para la asociación

    @Transactional(readOnly = true)
    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario findById(Long id){
        return usuarioRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public LoginDTO login(String username) {
        return usuarioRepository.getUsuarioByUsername(username)
                .orElseThrow(() -> new NotFoundException("Usuario",username));
    }

    @Transactional(readOnly = true)
    public Set<Cuenta> getCuentasByUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new NotFoundException("Usuario",idUsuario));

        return usuario.getCuentas();
    }

    @Transactional
    public Set<Long> getUsuarioByRol(String rol) {
        if(Rol.perteneceAlEnum(rol) == null){
            return new HashSet<>();
        }
        return usuarioRepository.getUsuariosByRol(rol);
    }


    @Transactional
    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void delete(Long id){
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public Optional<Usuario> asignarCuenta(Long idUsuario, Long idCuenta) {
        Optional<Usuario> oUsuario = usuarioRepository.findById(idUsuario);
        if (oUsuario.isPresent()) {
            Optional<Cuenta> oCuenta = cuentaRepository.findById(idCuenta);
            if (oCuenta.isPresent()) {
                Usuario usuario = oUsuario.get();
                Cuenta cuenta = oCuenta.get();
                usuario.addCuenta(cuenta); // Asumiendo que tienes un metodo addCuenta en tu entidad Usuario
                usuarioRepository.save(usuario);
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }

    @Transactional(readOnly = true)
    public boolean cuentaAsociada(Long idUsuario, Long idCuenta) {
        if (idUsuario == null || idCuenta == null) {
            return false;
        }

        return usuarioRepository.existsCuentaInUsuario(idUsuario,idCuenta);
    }
}