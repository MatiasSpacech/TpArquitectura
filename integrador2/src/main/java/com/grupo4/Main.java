package com.grupo4;

import com.grupo4.utils.CargarDatos;

public class Main {
    public static void main(String[] args) {
        CargarDatos cd = new CargarDatos();
        // Ruta correcta para el archivo CSV en resources
        cd.cargarEstudiantes("src/main/resources/csv/estudiantes.csv");
    }
}