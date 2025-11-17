package com.integrador3.dto;

import com.integrador3.model.Usuario;
import lombok.Data;


@Data
public class ReporteVentaDto {

    private Long usuarioId;
    private String nombreUsuario;
    private int edadUsuario;
    private long cantidadTransacciones;
    private double valorTicketPromedio;

    private Long idProducto;
    private String descripcion;
    private long cantUnidadesVendidas;

    public ReporteVentaDto(Long usuarioId, String nombreUsuario, int edadUsuario, long cantidadTransacciones, double valorTicketPromedio, Long idProducto, String descripcion, long cantUnidadesVendidas) {
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
        this.edadUsuario = edadUsuario;
        this.cantidadTransacciones = cantidadTransacciones;
        this.valorTicketPromedio = valorTicketPromedio;
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.cantUnidadesVendidas = cantUnidadesVendidas;
    }



}
