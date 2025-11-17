package com.integrador3.model;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class Transacciones {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @Column()
    private double valorTicket;

    private int cantidad;

    private LocalDate fecha;
}
