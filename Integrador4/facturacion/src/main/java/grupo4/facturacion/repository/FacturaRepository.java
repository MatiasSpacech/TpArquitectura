package grupo4.facturacion.repository;

import grupo4.facturacion.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("FacturaRepository")
public interface FacturaRepository extends JpaRepository<Factura,Long> {

    // Buscar por ID de usuario
    @Query("SELECT f FROM Factura f WHERE f.usuarioId = :id")
    List<Factura> findByUsuarioId(@Param("id") Long id);

    // Buscar por nombre de usuario
    @Query("SELECT f FROM Factura f WHERE f.usuario = :usuario")
    List<Factura> findByUsuario(@Param("usuario") String usuario);

    // Buscar por estado
    @Query("SELECT f FROM Factura f WHERE f.estado = :estado")
    List<Factura> findByEstado(@Param("estado") String estado);

    // Buscar por fecha exacta
    @Query("SELECT f FROM Factura f WHERE f.fechaEmision = :fecha")
    List<Factura> findByFecha(@Param("fecha") Date fecha);

    // Buscar entre fechas
    @Query("SELECT f FROM Factura f WHERE f.fechaEmision BETWEEN :desde AND :hasta")
    List<Factura> findByFechaBetween(@Param("desde") Date fecha1, @Param("hasta") Date fecha2);
}
