package com.grupo4.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReporteCarreraDTO {
    private String nombreCarrera;
    private Long cantidadInscriptos;
    private Integer anio;
    private long cantEgresados;


    // Constructor para el reporte de carreras con más inscriptos (String, Long)
    public ReporteCarreraDTO(String nombreCarrera, Long cantidadInscriptos) {
        this.nombreCarrera = nombreCarrera;
        this.cantidadInscriptos = cantidadInscriptos;
        this.anio = 0; // Valor por defecto
        this.cantEgresados = 0L; // Valor por defecto
    }

}
