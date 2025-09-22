package com.grupo4.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class EstudianteCarrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private LocalDate fechaIngreso;
    @Column(nullable = false)
    private boolean graduado;
    @ManyToOne
    @JoinColumn(name = "id_estudiante",unique = true)
    private Estudiante estudiante;
    @ManyToOne
    @JoinColumn(name = "id_carrera",unique = true)
    private Carrera carrera;
}
