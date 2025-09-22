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
    @ManyToOne
    @JoinColumn(name = "id_estudiante",unique = true)
    private Estudiante estudiante;
    @ManyToOne
    @JoinColumn(name = "id_carrera",unique = true)
    private Carrera carrera;

    //comentario de commit


}
