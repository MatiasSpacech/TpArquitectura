package grupo4.mscvusuario.repository;

import grupo4.mscvusuario.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta,Long> {
}
