package grupo4.mscvusuario.service;

import grupo4.mscvusuario.entity.Cuenta;
import grupo4.mscvusuario.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Transactional(readOnly = true)
    public List<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Cuenta findById(Long id) {
        return cuentaRepository.findById(id).orElse(null);
    }

    @Transactional
    public Cuenta save(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Transactional
    public void delete(Long id) {
        cuentaRepository.deleteById(id);
    }

    @Transactional
    public Cuenta cambiarEstadoCuenta(Long id){
        Cuenta cuenta = cuentaRepository.findById(id).orElse(null);
        if(cuenta != null){
            if(cuenta.getEstado() == grupo4.mscvusuario.entity.EstadoCuenta.ACTIVA){
                cuenta.setEstado(grupo4.mscvusuario.entity.EstadoCuenta.SUSPENDIDA);
            } else if (cuenta.getEstado() == grupo4.mscvusuario.entity.EstadoCuenta.SUSPENDIDA) {
                cuenta.setEstado(grupo4.mscvusuario.entity.EstadoCuenta.ACTIVA);
            }
            return cuentaRepository.save(cuenta);
        }
        return null;
    }
}