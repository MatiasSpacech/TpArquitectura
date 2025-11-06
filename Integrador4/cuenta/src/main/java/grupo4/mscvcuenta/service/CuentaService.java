package grupo4.mscvcuenta.service;

import feign.FeignException;
import grupo4.mscvcuenta.dto.UsuarioDto;
import grupo4.mscvcuenta.entity.Cuenta;
import grupo4.mscvcuenta.entity.Estado;
import grupo4.mscvcuenta.exceptions.NotFoundException;
import grupo4.mscvcuenta.feignClients.UsuarioClient;
import grupo4.mscvcuenta.repository.CuentaRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private UsuarioClient usuarioClient;

    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Transactional(readOnly = true)
    public Cuenta getCuentaById(String id){ return cuentaRepository.findById(id).get();}
    @Transactional(readOnly = true)
    public List<Cuenta> findAll(){ return cuentaRepository.findAll();}
    @Transactional
    public Cuenta createCuenta(Cuenta cuenta){ return cuentaRepository.save(cuenta); }
    @Transactional
    public void deleteCuenta(String id) { cuentaRepository.deleteById(id); }
    @Transactional
    public Cuenta updateCuenta(Cuenta cuenta){ return cuentaRepository.save(cuenta);}

    @Transactional(readOnly = true)
    public List<UsuarioDto> obtenerTodosLosUsuarios() {
        try {
            return usuarioClient.obtenerUsuarios();
        } catch (FeignException e) {
            throw new RuntimeException("FEIGN_ERROR: Fallo al comunicarse con el servicio de usuarios. Status: " + e.status());
        }
    }

    //asociar cuenta a usuario
    @Transactional
    public Cuenta asociarUsuario(String nroCuenta, Long idUsuario) {
        // 1. Buscar la cuenta
        Cuenta cuenta = cuentaRepository.findById(nroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        // 2. Validar que el usuario existe (Feign Client se encarga de lanzar excepción si no)
        usuarioClient.obtenerUsuarioPorId(idUsuario);

        // 3. Añadir el ID del usuario a la lista si no está ya presente
        if (!cuenta.getIdUsuarios().contains(idUsuario)) {
            cuenta.getIdUsuarios().add(idUsuario);
        }

        // 4. Actualizar el estado de la cuenta a ACTIVA
        cuenta.setEstado(Estado.ACTIVA);

        // 5. Actualizar el campo 'usuario' con los nombres
        // Obtenemos los nombres de todos los usuarios asociados
        String nombresUsuarios = cuenta.getIdUsuarios().stream()
                .map(uid -> usuarioClient.obtenerUsuarioPorId(uid).getName())
                .collect(Collectors.joining(", "));
        cuenta.setUsuarios(nombresUsuarios);

        // 6. Guardar la cuenta actualizada
        return cuentaRepository.save(cuenta);
    }

    @Transactional
    public Cuenta desasociarUsuario(String nroCuenta, Long idUsuario) {
        // No es necesario validar si el usuario existe para eliminarlo de la lista
        Cuenta cuenta = cuentaRepository.findById(nroCuenta)
                .orElseThrow(() -> new NotFoundException("Cuenta", nroCuenta));

        cuenta.getIdUsuarios().remove(idUsuario);

        return cuentaRepository.save(cuenta);
    }

    @Transactional
    public Cuenta cambiarEstado(String idCuenta, Estado nuevoEstado) {
        // 1. Buscamos la cuenta en la base de datos por su ID (String).
        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la cuenta con ID: " + idCuenta));

        // 2. Actualizamos el estado del objeto en memoria.
        cuenta.setEstado(nuevoEstado);

        // 3. Guardamos la cuenta actualizada en la base de datos.
        return cuentaRepository.save(cuenta);
    }

}
