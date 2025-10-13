package com.integrador3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class CarreraDTO {
    private String nombre;
    private int duracion;
    private long cantEstudiantes;

    @Override
    public String toString() {
        return "{" +
                "nombre='" + nombre + '\'' +
                ", duracion=" + duracion +
                ", cantEstudiantes=" + cantEstudiantes +
                '}';
    }
}