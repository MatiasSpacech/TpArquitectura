package grupo4.tarifas.repository;

import grupo4.tarifas.entity.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    //Actualizar Tarifa por desde una fecha dada
    @Query("UPDATE Tarifa t SET t.monto = :monto WHERE t.fecha = :fecha")
    void actualizarTarifaDesdeFecha(@Param("monto") double monto,
                                    @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fecha);

    // Actualizar Tarifa
    @Query("UPDATE Tarifa t SET t.monto = :monto, t.montoExtra = :montoExtra, t.fecha = :fecha WHERE t.id = :id")
    void actualizarTarifa(@Param("id") Long id,
                          @Param("monto") double monto,
                          @Param("montoExtra") double montoExtra,
                          @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fecha);

    // Eliminar Tarifa
    @Query("DELETE FROM Tarifa t WHERE t.id = :id")
    void eliminarTarifa(@Param("id") Long id);

    // Crear Tarifa
    @Query("INSERT INTO Tarifa (monto, montoExtra, fecha) VALUES (:monto, :montoExtra, :fecha)")
    void crearTarifa(@Param("monto") double monto,
                     @Param("montoExtra") double montoExtra,
                     @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fecha);

    // Buscar la tarifa vigente desde una fecha dada
    @Query("SELECT t FROM Tarifa t WHERE t.fecha <= :fecha ORDER BY t.fecha DESC")
    Tarifa findTarifaVigenteDesde(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fecha);

    // Buscar tarifa por fecha
    @Query("SELECT t FROM Tarifa t WHERE t.fecha = :fecha")
    Tarifa findByFecha(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fecha);

    // Buscar Tarifa por monto
    @Query("SELECT t FROM Tarifa t WHERE t.monto = :monto")
    Tarifa findByMonto(@Param("monto") double monto);


}
