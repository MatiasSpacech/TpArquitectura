package com.grupo4;

import com.grupo4.dto.CarreraDTO;
import com.grupo4.dto.EstudianteDTO;
import com.grupo4.dto.ReporteCarreraDTO;
import com.grupo4.model.Carrera;
import com.grupo4.model.Estudiante;
import com.grupo4.repository.*;
import com.grupo4.utils.CargarDatos;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CargarDatos cd = new CargarDatos();
        cd.run();


        //REPOSITORYS
        CarreraRepository cr = new CarreraRepositoryImpl();
        EstudianteRepository er = new EstudianteRepositoryImpl();
        EstudianteCarreraRepository ecr = new EstudianteCarreraRepositoryImpl();



        // Menu de opciones
        boolean condition = true;
           while (condition) {
            System.out.println("Seleccione una opción:");
            /*a) dar de alta un estudiante
b) matricular un estudiante en una carrera
c) recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.
d) recuperar un estudiante, en base a su número de libreta universitaria.
e) recuperar todos los estudiantes, en base a su género.
f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia. */
            System.out.println("1. Dar de alta un estudiante");
            System.out.println("2. Matricular un estudiante en una carrera");
            System.out.println("3. Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple");
            System.out.println("4. Recuperar un estudiante, en base a su número de libreta universitaria");
            System.out.println("5. Recuperar todos los estudiantes, en base a su género");
            System.out.println("6. Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos");
            System.out.println("7. Recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia");
            System.out.println("8. Generar reporte de carreras");
            System.out.println("9. Salir");
            System.out.println("\n");
            Scanner scanner = new Scanner(System.in);
            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    //Dar de alta un estudiante
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
                    //Matricular un estudiante en una carrera
                    //implementar
                    break;
                case 3:
                    //Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple
                    //implementar
                    break;
                case 4:
                    //Recuperar un estudiante, en base a su número de libreta universitaria
                    //implementar
                    break;
                case 5:
                    //Recuperar todos los estudiantes, en base a su género
                    //implementar
                    break;
                case 6:
                    //Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos
                    System.out.println("Carreras con estudiantes inscriptos:");
                    List<CarreraDTO> carreras = cr.getCarrerasConEstudiantes();
                    for(CarreraDTO carrera:carreras){
                        System.out.println(carrera);
                    }
                    System.out.println("\n");
                    break;
                case 7:
                    //Recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia
                    //implementar
                    break;
                case 8:
                    //Generar reporte de carreras
                    System.out.println("Reporte de Carreras:");
                    List<ReporteCarreraDTO> reportes = ecr.getReportesCarreras();
                    for(ReporteCarreraDTO reporte:reportes){
                        System.out.println(reporte);
                    }
                    System.out.println("\n");
                    break;
                case 9:
                    condition = false;
                    break;
                default:
                    System.out.println("Opción no válida");
               
            }
         
        }

       
        //PRUEBA DE METODOS






        

        //prueba para buscar un estudiante por id y traer sus carreras,se debe implementar toString en ambas entidades,estudiante y matriculas
        //Estudiante es=er.findById(71779527);
        //System.out.println(es);

    }
}