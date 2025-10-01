package com.grupo4.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="estudiante")
public class Estudiante {                                              
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
    private List<EstudianteCarrera> listCarreras;

    public Estudiante(long dni, String nombre, String apellido, int edad, String genero, String ciudad, int nroLibreta) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.ciudad = ciudad;
        this.nroLibreta = nroLibreta;
        this.listCarreras = new ArrayList<>();
    }
}

