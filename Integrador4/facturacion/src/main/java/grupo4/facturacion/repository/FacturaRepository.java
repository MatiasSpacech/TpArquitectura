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



    // Buscar entre fechas
    @Query("SELECT f FROM Factura f WHERE f.fechaEmision BETWEEN :desde AND :hasta")
    List<Factura> findByFechaBetween(@Param("desde") Date fecha1, @Param("hasta") Date fecha2);


    // reporte total facturado por rango de meses en un año
    @Query("SELECT COALESCE(SUM(f.importe), 0.0) FROM Factura f " +
            "WHERE YEAR(f.fechaEmision) = :anio " +
            "AND MONTH(f.fechaEmision) BETWEEN :mesDesde AND :mesHasta")
    Double getTotalFacturadoPorRangoMeses(@Param("anio") int anio,
                                          @Param("mesDesde") int mesDesde,
                                          @Param("mesHasta") int mesHasta);



//    SUM(f.monto) suma todos los montos de las facturas
//    COALESCE(..., 0.0) retorna 0 si no hay resultados
//    YEAR(f.fechaEmision) extrae el año de la fecha
//    MONTH(f.fechaEmision) extrae el mes (1-12)
//    BETWEEN filtra el rango de meses




}
