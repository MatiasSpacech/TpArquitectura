package grupo4.tarifas.repository;

import grupo4.tarifas.entity.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    // Actualizar Tarifa desde una fecha dada
    @Modifying
    @Query("UPDATE Tarifa t SET t.monto = :monto WHERE t.fecha = :fecha")
    void actualizarTarifaDesdeFechaQuery(@Param("monto") double monto, @Param("fecha") Date fecha);

    // Buscar tarifa vigente desde una fecha
    @Query("SELECT t FROM Tarifa t WHERE t.fecha >= :fecha ORDER BY t.fecha ASC")
    Tarifa findTarifaVigenteDesde(@Param("fecha") Date fecha);

    // Buscar tarifa por fecha exacta
    Tarifa findByFecha(Date fecha);

    // Buscar tarifa por monto
    Tarifa findByMonto(Double monto);
}
