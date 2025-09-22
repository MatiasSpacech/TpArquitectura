package com.grupo4.model;

import com.grupo4.model.EstudianteCarrera;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="carrera")
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false, length = 100)
    private String nombre;
    @Column(unique = true, nullable = false)
    private int duracion;
    @OneToMany(mappedBy = "carrera",fetch = FetchType.LAZY)
    private List<EstudianteCarrera> estudiantes;

    public Carrera(String nombre){
        this.nombre = nombre;
        this.estudiantes = new ArrayList<>();
    }
}

