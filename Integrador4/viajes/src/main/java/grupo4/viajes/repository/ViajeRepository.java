package grupo4.viajes.repository;

import grupo4.viajes.dtos.ReporteViajePeriodoDTO;
import grupo4.viajes.model.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ViajeRepository")
public interface ViajeRepository extends JpaRepository<Viaje, Long> {

    @Query("""
            SELECT new grupo4.viajes.dtos.ReporteViajePeriodoDTO(v.idMonopatin,COUNT(v),:anio)
            FROM Viaje v
            WHERE FUNCTION('YEAR', v.fechaFin) = :anio 
            GROUP BY v.idMonopatin
            HAVING COUNT(v) > :xViajes
            ORDER BY COUNT(v) DESC
            """)
    List<ReporteViajePeriodoDTO> getReporteViajeAnio(Integer anio,Integer xViajes);
}
