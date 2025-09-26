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

}
