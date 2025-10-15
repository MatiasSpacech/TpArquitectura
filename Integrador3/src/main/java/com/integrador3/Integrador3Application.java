package com.integrador3;

import com.integrador3.utils.CargarDatos;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Integrador3Application {
    // Los datos los cargo desde CargarDatos ya que es @Component que implementa CommandLineRunner
    // Y ejecuta el metodo run cuando inicia la aplicacion que se hereda de CommandLineRunner


    public static void main(String[] args) {
        SpringApplication.run(Integrador3Application.class, args);
    }

}
