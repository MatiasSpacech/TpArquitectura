package com.grupo4.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="estudianteCarrera")
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
    @ManyToOne
    @JoinColumn(name = "id_estudiante")
    private Estudiante estudiante;
    @ManyToOne
    @JoinColumn(name = "id_carrera")
    private Carrera carrera;

    public EstudianteCarrera(int fechaIngreso, int fechaGraduacion, int antiguedad, Estudiante estudiante, Carrera carrera) {
        this.fechaIngreso = fechaIngreso;
        this.fechaGraduacion = fechaGraduacion;
        this.antiguedad = antiguedad;
        this.estudiante = estudiante;
        this.carrera = carrera;
    }

}
