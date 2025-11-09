package grupo4.mscvusuario.service;

import grupo4.mscvusuario.entity.Cuenta;
import grupo4.mscvusuario.entity.EstadoCuenta;
import grupo4.mscvusuario.entity.Usuario;
import grupo4.mscvusuario.repository.CuentaRepository;
import grupo4.mscvusuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    @Transactional
    public Optional<Cuenta> desasignarCuenta(Long usuarioId, Long cuentaId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        Optional<Cuenta> cuentaOpt = cuentaRepository.findById(cuentaId);

        if (usuarioOpt.isPresent() && cuentaOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Cuenta cuenta = cuentaOpt.get();

            cuenta.getUsuarios().remove(usuario);
            cuentaRepository.save(cuenta);
            return Optional.of(cuenta);
        }
        return Optional.empty();
    }

    @Transactional
    public void cambiarEstadoCuentas(Long idUsuario, EstadoCuenta nuevoEstado) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }

        int updated = cuentaRepository.updateEstadoByUsuarioId(idUsuario, nuevoEstado);

        // Forzar flush para que la actualización se envíe inmediatamente al DB y el EntityManager quede consistente.
        cuentaRepository.flush();

        // updated == 0 significa que el usuario no tenía cuentas; no se lanza error por si es válido.
    }
}