package grupo4.mscvusuario.repository;

import grupo4.mscvusuario.entity.Cuenta;
import grupo4.mscvusuario.entity.EstadoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CuentaRepository extends JpaRepository<Cuenta,Long> {

    @Modifying
    @Query("UPDATE Cuenta c SET c.estado = :nuevoEstado WHERE c IN (" +
            "SELECT cu FROM Usuario u JOIN u.cuentas cu WHERE u.id = :idUsuario" +
            ")")
    int updateEstadoByUsuarioId(@Param("idUsuario") Long idUsuario, @Param("nuevoEstado") EstadoCuenta nuevoEstado);

}
