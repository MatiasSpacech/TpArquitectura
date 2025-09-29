package com.grupo4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
//@ToString
public class ReporteCarreraDTO {
    private int anioEgreso;
    private String nombreCarrera;
    private long cantEgresados;
    private long inscriptos;

    @Override
    public String toString() {
        return "{" +
                "Año de egreso=" + anioEgreso +
                ", Carrera='" + nombreCarrera + '\'' +
                ", Cantidad de egresados=" + cantEgresados +
                ", Inscriptos=" + inscriptos +
                '}';
    }
}
