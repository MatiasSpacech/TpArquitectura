package grupo4.mscvcuenta.service;

import feign.FeignException;
import grupo4.mscvcuenta.dto.UsuarioDto;
import grupo4.mscvcuenta.entity.Cuenta;
import grupo4.mscvcuenta.feignClients.UsuarioClient;
import grupo4.mscvcuenta.repository.CuentaRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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


}
