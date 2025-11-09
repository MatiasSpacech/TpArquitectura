package grupo4.mscvusuario.service;

import grupo4.mscvusuario.entity.Cuenta;
import grupo4.mscvusuario.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    // En el archivo 'usuario-cuenta/src/main/java/grupo4/mscvusuario/service/CuentaService.java'

    @Transactional
    public Optional<Cuenta> editar(Long id, Cuenta cuenta) {
        Optional<Cuenta> o = cuentaRepository.findById(id);
        if (o.isPresent()) {
            Cuenta cuentaDB = o.get();

            // Solo actualiza el saldo si se proporciona en la solicitud
            if (cuenta.getSaldo() != null) {
                cuentaDB.setSaldo(cuenta.getSaldo());
            }

            // Solo actualiza el estado si se proporciona en la solicitud
            if (cuenta.getEstado() != null) {
                cuentaDB.setEstado(cuenta.getEstado());
            }

            // Agrega validaciones similares para otros campos que quieras poder actualizar

            return Optional.of(cuentaRepository.save(cuentaDB));
        }
        return Optional.empty();
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return cuentaRepository.existsById(id);
    }


}