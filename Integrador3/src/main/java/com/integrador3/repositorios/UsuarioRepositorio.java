package com.integrador3.repositorios;

import com.integrador3.dto.ReporteVentaDto;
import com.integrador3.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {


//    @Query("SELECT u FROM Usuario u WHERE u.edad < :edad")
//    List<Usuario>obtenerUsuarioPorEdadMenorA(@Param("edad") int edad);


    @Query("SELECT new com.integrador3.dto.ReporteVentaDto(" +
            "u.id, u.nombre, u.edad, COUNT(t), AVG(t.valorTicket), p.id, p.descripcion, SUM(t.cantidad)) " +
            "FROM Usuario u JOIN u.transacciones t JOIN t.producto p " +
            "WHERE u.edad < :edad AND p.rubro = 'Golosinas' " +
            "GROUP BY u.id, u.nombre, p.id, p.descripcion " +
            "HAVING COUNT(t) >= 3")
    List<ReporteVentaDto> obtenerReporteVentas(@Param("edad") int edad);

}
