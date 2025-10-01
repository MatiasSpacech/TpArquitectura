package com.grupo4.model;

import lombok.*;
import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "estudianteCarrera")
public class EstudianteCarrera {
    @Id
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

    public EstudianteCarrera(long id, int fechaIngreso, int fechaGraduacion, int antiguedad, Estudiante estudiante,
            Carrera carrera) {
        this.id = id;
        this.fechaIngreso = fechaIngreso;
        this.fechaGraduacion = fechaGraduacion;
        this.antiguedad = antiguedad;
        this.estudiante = estudiante;
        this.carrera = carrera;
    }

}
