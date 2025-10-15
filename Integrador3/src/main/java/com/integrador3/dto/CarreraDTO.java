package com.integrador3.dto;

import com.integrador3.model.Carrera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@AllArgsConstructor
@Getter
@RequiredArgsConstructor
public class CarreraDTO {
    private String nombre;
    private int duracion;
    private long cantEstudiantes;

    public CarreraDTO(Carrera carrera) {
        this.nombre = carrera.getNombre();
        this.duracion = carrera.getDuracion();
        this.cantEstudiantes = carrera.getEstudiantes().size();
    }

    @Override
    public String toString() {
        return "{" +
                "nombre='" + nombre + '\'' +
                ", duracion=" + duracion +
                ", cantEstudiantes=" + cantEstudiantes +
                '}';
    }
}