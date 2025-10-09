package com.integrador3;

import com.integrador3.utils.CargarDatos;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Integrador3Application {
    @Autowired
    private CargarDatos cargarDatos;

    @PostConstruct
    public void init(){
        cargarDatos.cargarDatos();
    }

    public static void main(String[] args) {
        SpringApplication.run(Integrador3Application.class, args);
    }

}
