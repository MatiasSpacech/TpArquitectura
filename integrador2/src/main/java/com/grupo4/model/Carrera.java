package com.grupo4.model;

import com.grupo4.model.EstudianteCarrera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.ConstructorParameters;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="carrera")
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false, length = 100)
    private String nombre;
    @Column(nullable = false)
    private int duracion;
    @OneToMany(mappedBy = "carrera",fetch = FetchType.LAZY)
    private List<EstudianteCarrera> estudiantes;

    public Carrera(String nombre,int duracion){
        this.nombre = nombre;
        this.duracion = duracion;
        this.estudiantes = new ArrayList<>();
    }

    //metodo para agregar un estudiante a la lista de la carrera

    //metodo para quitar un estudiante de la lista de la carrera
}

