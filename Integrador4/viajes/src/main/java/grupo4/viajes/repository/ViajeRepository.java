package grupo4.viajes.repository;

import grupo4.viajes.dtos.ReporteViajePeriodoDTO;
import grupo4.viajes.dtos.ReporteViajeUsuariosDTO;
import grupo4.viajes.feignModels.Cuenta;
import grupo4.viajes.model.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository("ViajeRepository")
public interface ViajeRepository extends JpaRepository<Viaje, Long> {

    @Query("""
            SELECT new grupo4.viajes.dtos.ReporteViajePeriodoDTO(v.idMonopatin,COUNT(v),:anioBuscado)
            FROM Viaje v
            WHERE FUNCTION('YEAR', v.fechaFin) = :anioBuscado\s
                       AND v.activo = false
            GROUP BY v.idMonopatin
            HAVING COUNT(v) > :xViajes
            ORDER BY COUNT(v) DESC
           \s""")
    List<ReporteViajePeriodoDTO> getReporteViajeAnio(Integer anioBuscado,Integer xViajes);


    @Query("""
            SELECT new grupo4.viajes.dtos.ReporteViajeUsuariosDTO(v.idUsuario,COUNT(v),SUM(v.kilometrosRecorridos),SUM(v.tiempoTotalMinutos))
            FROM Viaje v 
            WHERE v.activo = false AND 
                    FUNCTION('YEAR', v.fechaFin) BETWEEN :aniDesde AND :anioHasta
            GROUP BY v.idUsuario
            ORDER BY COUNT(v) DESC 
            """)
    List<ReporteViajeUsuariosDTO> getReportesViajesPorUsuariosPeriodo(Integer aniDesde, Integer anioHasta);

    @Query("""
            SELECT grupo4.viajes.dtos.ReporteViajeUsuariosDTO(v.idUsuario,COUNT(v),SUM(v.kilometrosRecorridos),SUM(v.tiempoTotalMinutos))
            FROM Viaje v
            WHERE v.activo = false AND 
                    FUNCTION('YEAR', v.fechaFin) BETWEEN :aniDesde AND :anioHasta AND 
                    v.idCuenta IN :cuentas
            GROUP BY v.idUsuario
            """)
    List<ReporteViajeUsuariosDTO> getReportesViajesPorUsuarioYcuentasAsociadasPeriodo(
            Set<Cuenta> cuentas, Integer aniDesde, Integer anioHasta);
}
