package com.grupo4.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
public class Estudiante {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)----------->>>>>lo comenté porque no funciona como id desde el csv,
    //private int id;                                                    estan los dni como id,si te pones a ver el csv de estudianteCarrera
    @Id
    private long dni;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    @Column(nullable = false)
    private int edad;
    @Column(nullable = false)
    private String genero;
    @Column(nullable = false)
    private String ciudad;
    @Column(nullable = false, unique = true)
    private int nroLibreta;

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<EstudianteCarrera> estudianteCarreraList;

    public Estudiante(long dni, String nombre, String apellido, int edad, String genero, String ciudad, int nroLibreta) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.ciudad = ciudad;
        this.nroLibreta = nroLibreta;
        this.estudianteCarreraList = new ArrayList<>();
    }

    //metodo para agregar una carrera a la lista del estudiante

    //metodo para quitar una carrera de la lista del estudiante



}

