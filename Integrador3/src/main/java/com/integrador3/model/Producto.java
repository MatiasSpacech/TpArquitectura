package com.integrador3.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Producto {

    @Id
    private Long id;
    private String nombre;
    private String descripcion;
    private String rubro;
    private int precio;
}
