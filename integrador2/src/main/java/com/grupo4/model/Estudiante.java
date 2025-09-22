package com.grupo4.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

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
    private LocalDate fechaNacimiento;
    @Column(nullable = false)
    private Genero genero;
    @Column(nullable = false)
    private String ciudad;
    @Column(nullable = false, unique = true)
    private int nroLibreta;

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<EstudianteCarrera> estudianteCarreraList;

    public Estudiante(String nombre, String apellido, LocalDate fechaNacimiento, Genero genero, String ciudad, int nroLibreta) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.ciudad = ciudad;
        this.nroLibreta = nroLibreta;
        this.estudianteCarreraList = new ArrayList<EstudianteCarrera>();
    }

    public int getEdad() {
        return LocalDate.now().getYear() - fechaNacimiento.getYear();
    }
}
