package com.grupo4.dto;

//anotacion lombok
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EstudianteDTO {
    private String nombre;
    private String apellido;
    private long dni;
    private int edad;
    private String genero;
    private String ciudad;
    private int nroLibreta;

}

