package com.grupo4.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique = true)
    private long dni;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    @Column(nullable = false)
    private int fechaNacimiento;
    @Column(nullable = false)
    private String genero;
    @Column(nullable = false)
    private String ciudad;
    @Column(nullable = false, unique = true)
    private int nroLibreta;

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<EstudianteCarrera> estudianteCarreraList;




}
