package com.grupo4.dto;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarreraDTO {
    private long id;
    private int duracion;
    private List<EstudianteCarrera> estudiantes;
}
