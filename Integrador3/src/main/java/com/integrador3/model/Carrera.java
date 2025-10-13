package com.integrador3.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="carrera")
public class Carrera {
    @Id
    private int id;
    @Column(unique = true, nullable = false, length = 100)
    private String nombre;
    @Column(nullable = false)
    private int duracion;
    @OneToMany(mappedBy = "carrera",fetch = FetchType.LAZY)
    private List<EstudianteCarrera> estudiantes;

    public Carrera(int id,String nombre,int duracion){
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
        this.estudiantes = new ArrayList<>();
    }
}

