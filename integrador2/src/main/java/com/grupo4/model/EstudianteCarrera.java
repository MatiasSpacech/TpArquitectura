package com.grupo4.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class EstudianteCarrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private int fechaIngreso;
    @Column(nullable = false)
    private int fechaGraduacion;
    @Column(nullable = false)
    private int antiguedad;
    @Column
    private boolean graduado;

    // Relación muchos a uno con Carrera
    @ManyToOne
    @JoinColumn(name = "id_estudiante",nullable = false) // no debes usar unique = true, porque en una relación muchos a uno varios registros pueden compartir el mismo estudiante.
    private Estudiante estudiante;

    // Relación muchos a uno con Estudiante
    @ManyToOne
    @JoinColumn(name = "id_carrera",nullable = false) //unique = true: impide que varias filas compartan la misma carrera, lo cual no es correcto para una relación muchos a uno.
    private Carrera carrera;

    public EstudianteCarrera(int fechaIngreso, int fechaGraduacion, int antiguedad, Estudiante estudiante, Carrera carrera) {
        this.fechaIngreso = fechaIngreso;
        this.fechaGraduacion = fechaGraduacion;
        this.antiguedad = antiguedad;
        this.estudiante = estudiante;
        this.carrera = carrera;
    }

}
