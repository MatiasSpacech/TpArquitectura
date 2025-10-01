package com.grupo4;

import com.grupo4.dto.CarreraDTO;
import com.grupo4.dto.EstudianteDTO;
import com.grupo4.dto.ReporteCarreraDTO;
import com.grupo4.model.Carrera;
import com.grupo4.model.Estudiante;
import com.grupo4.model.EstudianteCarrera;
import com.grupo4.repository.*;
import com.grupo4.utils.CargarDatos;

import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        CargarDatos cd = new CargarDatos();
        cd.run();

        // REPOSITORYS
        CarreraRepository cr = new CarreraRepositoryImpl();
        EstudianteRepository er = new EstudianteRepositoryImpl();
        EstudianteCarreraRepository ecr = new EstudianteCarreraRepositoryImpl();        

        // Menu de opciones
        boolean condition = true;
        while (condition) {

            System.out.println("____                          _  _   ");
            System.out.println(" / ___|_ __ _   _ _ __   ___   | || |  ");
            System.out.println("| |  _| '__| | | | '_ \\ / _ \\  | || |_ ");
            System.out.println("| |_| | |  | |_| | |_) | (_) | |__   _|");
            System.out.println(" \\____|_|   \\__,_| .__/ \\___/     |_|  ");
            System.out.println("                 |_|                   ");
            System.out.println("\n");

            System.out.println("1. Dar de alta un estudiante");
            System.out.println("2. Matricular un estudiante en una carrera");
            System.out
                    .println("3. Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple");
            System.out.println("4. Recuperar un estudiante, en base a su número de libreta universitaria");
            System.out.println("5. Recuperar todos los estudiantes, en base a su género");
            System.out.println(
                    "6. Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos");
            System.out.println(
                    "7. Recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia");
            System.out.println("8. Generar reporte de carreras");
            System.out.println("9. Salir");
            System.out.println("\n");
            System.out.print("Ingrese una opción: ");
            Scanner scanner = new Scanner(System.in);
            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    // Dar de alta un estudiante
                    System.out.println("Ingrese los datos del estudiante:");
                    System.out.println("ingrese Nombre: ");
                    String nombre = scanner.next();
                    System.out.println("ingrese Apellido: ");
                    String apellido = scanner.next();
                    System.out.println("ingrese DNI: ");
                    Long dni = scanner.nextLong();
                    System.out.println("ingrese Edad: ");
                    int edad = scanner.nextInt();
                    System.out.println("ingrese Genero: ");
                    String genero = scanner.next();
                    System.out.println("ingrese Ciudad: ");
                    String ciudad = scanner.next();
                    System.out.println("ingrese Nro Libreta: ");
                    int nroLibreta = scanner.nextInt();
                    Estudiante estudiante = new Estudiante(dni, nombre, apellido, edad, genero, ciudad, nroLibreta);
                    er.addEstudiante(estudiante);
                    System.out.println("Estudiante agregado: " + estudiante);
                    System.out.println("\n");

                    break;
                case 2:
                    // Matricular un estudiante en una carrera
                    System.out.println("Ingrese el nro de DNI del estudiante a matricular: ");
                    Long DNI = scanner.nextLong();
                    System.out.println("Ingrese el id de la carrera: ");
                    int idCarrera = scanner.nextInt();
                    Estudiante estudianteMatricula = er.findById(DNI);
                    Carrera carreraMatricula = cr.findById(idCarrera);
                    if (estudianteMatricula != null && carreraMatricula != null) {
                        Estudiante estudianteEntity = er.findById(estudianteMatricula.getDni());
                        EstudianteCarrera estudianteCarrera = new EstudianteCarrera();
                        estudianteCarrera.setId(System.currentTimeMillis()); // Generar un ID único
                        estudianteCarrera.setFechaIngreso(2025); // Año actual como fecha de ingreso
                        estudianteCarrera.setFechaGraduacion(2025 + carreraMatricula.getDuracion()); // Año de
                                                                                                     // graduación
                                                                                                     // estimado
                        estudianteCarrera.setAntiguedad(0); // Inicialmente 0
                        estudianteCarrera.setEstudiante(estudianteEntity);
                        estudianteCarrera.setCarrera(carreraMatricula);
                        try {
                            ecr.matricularEstudiante(estudianteCarrera);
                            System.out.println("Estudiante matriculado en la carrera: " + carreraMatricula.getNombre());
                        } catch (Exception ex) {
                            System.out.println("El estudiante ya esta inscrito en la carrera: " + carreraMatricula.getNombre() + " " + ex.getMessage());
                        }                        
                    } else {
                        System.out.println("Estudiante o carrera no encontrada.");
                    }
                    System.out.println("\n");
                    break;
                case 3:
                    // Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento
                    // simple
                    System.out.println("Ingrese el criterio de ordenamiento (nombre, apellido, ciudad): ");
                    String criterio = scanner.next();
                    try {
                        List<EstudianteDTO> estudiantes = er.getEstudiantesSorted(criterio);
                        System.out.println("--------------Estudiantes ordenados por " + criterio + ": ");
                        for (EstudianteDTO est : estudiantes) {
                            System.out.println(est);
                        }

                    } catch (Exception e) {
                        System.out.println("Criterio de ordenamiento no válido.");
                    }
                   
                    System.out.println("\n");
                    break;
                case 4:
                    // Recuperar un estudiante, en base a su número de libreta universitaria
                    System.out.println("Ingrese el nro de libreta del estudiante a buscar: ");
                    int nroLibretaBuscar = scanner.nextInt();
                    EstudianteDTO estudianteBuscar = er.getEstudianteLU(nroLibretaBuscar);
                    if (estudianteBuscar != null) {
                        System.out.println("Estudiante encontrado: " + estudianteBuscar);
                    } else {
                        System.out.println("Estudiante no encontrado.");
                    }
                    System.out.println("\n");
                    break;
                case 5:
                    // Recuperar todos los estudiantes, en base a su género
                    System.out.println("Ingrese el género de los estudiantes a buscar: ");
                    String generoBuscar = scanner.next();
                    List<EstudianteDTO> estudiantesGenero = er.getEstudiantesByGenero(generoBuscar);
                    if (estudiantesGenero != null) {
                        System.out.println("--------------Estudiantes de género " + generoBuscar + ": ");
                        for (EstudianteDTO estGenero : estudiantesGenero) {
                            System.out.println(estGenero);
                        }
                    } else {
                        System.out.println("No se encontraron estudiantes de ese género.");
                    }
                    System.out.println("\n");
                    break;
                case 6:
                    // Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de
                    // inscriptos
                    List<CarreraDTO> carreras = cr.getCarrerasConEstudiantes();
                    System.out.println("--------------Carreras con estudiantes inscriptos: ");
                    for (CarreraDTO carrera : carreras) {
                        System.out.println(carrera);
                    }
                    System.out.println("\n");
                    break;
                case 7:
                    // Recuperar los estudiantes de una determinada carrera, filtrado por ciudad de
                    // residencia
                    System.out.println("Ingrese el nombre de la carrera: ");
                    String nombreCarrera = scanner.next();
                    System.out.println("Ingrese la ciudad de residencia: ");
                    String ciudadResidencia = scanner.next();
                    List<EstudianteDTO> estudiantesCarrera = er.getEstudiantesByCarreraAndCiudad(nombreCarrera,
                            ciudadResidencia);
                    if (estudiantesCarrera != null) {
                        System.out.println("--------------Estudiantes de la carrera " + nombreCarrera + " y ciudad "
                                + ciudadResidencia + ": ");
                        for (EstudianteDTO estCarrera : estudiantesCarrera) {
                            System.out.println(estCarrera);
                        }
                    } else {
                        System.out.println("No se encontraron estudiantes para esa carrera y ciudad.");
                    }
                    System.out.println("\n");
                    break;
                case 8:
                    // Generar reporte de carreras
                    System.out.println("Reporte de Carreras:");
                    List<ReporteCarreraDTO> reportes = ecr.getReportesCarreras();
                    System.out.println("-------------Reporte de Carreras: ");
                    for (ReporteCarreraDTO reporte : reportes) {
                        System.out.println(reporte);
                    }
                    System.out.println("\n");
                    break;
                case 9:
                    scanner.close();
                    System.out.println("Saliendo...");
                    condition = false;
                    break;
                default:
                    System.out.println("Opción no válida");

            }

        }

    }
}