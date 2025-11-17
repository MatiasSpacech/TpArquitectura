package com.integrador3.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;


@Data
@Entity
public class Usuario {

    @Id
    private Long id;
    @Column()
    private String nombre;

    @Column()
    private int edad;

    @OneToMany(mappedBy = "usuario")
    private List<Transacciones> transacciones;
}
