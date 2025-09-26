package com.grupo4;

import com.grupo4.dto.EstudianteDTO;
import com.grupo4.dto.ReporteCarreraDTO;
import com.grupo4.model.Carrera;
import com.grupo4.model.Estudiante;
import com.grupo4.model.EstudianteCarrera;
import com.grupo4.repository.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Dar de alta un estudiante
        EstudianteRepository estudianteRepository = new EstudianteRepositoryImpl();
        Estudiante estudiante = new Estudiante(12345678L, "Ana", "García", 22, "F", "Córdoba", 1001);
        estudianteRepository.cargarEstudiante(estudiante);
        // Imprimimos solo los datos del estudiante para evitar LazyInitializationException
        System.out.println("Estudiante creado en la base de datos: " + estudiante.getNombre() + " " + estudiante.getApellido());

        //Agregar más estudiantes si es necesario
        Estudiante estudiante2 = new Estudiante(87654321L, "Luis", "Martínez", 24, "M", "Mendoza", 1002);
        estudianteRepository.cargarEstudiante(estudiante2);
        System.out.println("Estudiante creado en la base de datos: " + estudiante2.getNombre() + " " + estudiante2.getApellido());

        Estudiante estudiante3 = new Estudiante(11223344L, "María", "López", 21, "F", "Buenos Aires", 1003);
        estudianteRepository.cargarEstudiante(estudiante3);
        System.out.println("Estudiante creado en la base de datos: " + estudiante3.getNombre() + " " + estudiante3.getApellido());

        // Matricular un estudiante en una carrera
        CarreraRepository carreraRepository = new CarreraRepositoryImpl();
        EstudiantaCarreraRepository estudianteCarreraRepository = new EstudianteCarreraRepositoryImpl();

        // 1. Crear y persistir carreras
        String nombreCarrera = "Ingeniería en Sistemas";
        carreraRepository.crearCarrera(nombreCarrera, 5);
        carreraRepository.crearCarrera("Licenciatura en Administración", 4);
        carreraRepository.crearCarrera("Arquitectura", 5);
        System.out.println("Carreras creadas en la base de datos.");

        // 2. Buscar las entidades que ya están persistidas
        Estudiante estudianteMatricular = estudianteRepository.findById(12345678L);
        // Buscar la carrera por nombre para obtener la entidad gestionada por JPA
        Carrera carreraMatricular = carreraRepository.getCarreraByName(nombreCarrera);

        if (estudianteMatricular != null) {
            System.out.println("Estudiante encontrado: " + estudianteMatricular.getNombre() + " " + estudianteMatricular.getApellido());
        } else {
            System.out.println("No se encontró el estudiante con DNI 12345678.");
        }

        if (carreraMatricular != null) {
            System.out.println("Carrera encontrada: " + carreraMatricular.getNombre());
        } else {
            System.out.println("No se encontró la carrera: " + nombreCarrera);
        }

        // 3. Crear la relación EstudianteCarrera (matriculación)
        if (estudianteMatricular != null && carreraMatricular != null) {
            EstudianteCarrera matricula = new EstudianteCarrera();
            matricula.setEstudiante(estudianteMatricular);
            matricula.setCarrera(carreraMatricular); // Usar la entidad gestionada
            matricula.setFechaIngreso(2024);
            matricula.setAntiguedad(0);
            matricula.setGraduado(false);
            matricula.setFechaGraduacion(0); // Un valor por defecto para no graduado

            estudianteCarreraRepository.matricularEstudiante(matricula);
            System.out.println("Estudiante " + estudianteMatricular.getNombre() + " matriculado en " + carreraMatricular.getNombre());
        } else {
            System.out.println("No se pudo realizar la matriculación por falta de estudiante o carrera.");
        }



        //recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.
        System.out.println("\n--- Lista de Estudiantes Ordenados por Apellido ---");
        // Cambia el tipo de la lista a EstudianteDTO
        List<com.grupo4.dto.EstudianteDTO> estudiantesDTO = estudianteRepository.getAllEstudiantesSortedByApellido();
        // Itera sobre la lista de DTOs
        for(com.grupo4.dto.EstudianteDTO est : estudiantesDTO){
            System.out.println(est.getApellido() + ", " + est.getNombre());
        }

        //recuperar un estudiante, en base a su número de libreta universitaria.
        System.out.println("\n--- Buscar estudiante por número de libreta ---");
        int libretaABuscar = 1001;
        try {
            EstudianteDTO estudiantePorLibreta = estudianteRepository.getEstudianteByNroLibreta(libretaABuscar);
            System.out.println("Estudiante encontrado con libreta " + libretaABuscar + ":");
            System.out.println(estudiantePorLibreta);
        } catch (javax.persistence.NoResultException e) {
            System.out.println("No se encontró ningún estudiante con el número de libreta: " + libretaABuscar);
        }

        //recuperar todos los estudiantes, en base a su género.
        System.out.println("\n--- Lista de Estudiantes en base a su género. ---");
        // Cambia el tipo de la lista a EstudianteDTO
        List<com.grupo4.dto.EstudianteDTO> estudiantesGenero = estudianteRepository.getEstudianteByGenero("M");
        // Itera sobre la lista de DTOs
        for(com.grupo4.dto.EstudianteDTO est : estudiantesGenero){
            System.out.println(est.getApellido() + ", " + est.getNombre());
        }

        //recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
        System.out.println("\n--- Lista de las carreras con estudiantes inscriptos, ordenanadas por cantidad de inscriptos. ---");
        // Cambia el tipo de la lista a EstudianteDTO
        List<ReporteCarreraDTO> reporte = carreraRepository.getReporteCarreras();

        if (reporte.isEmpty()) {
            System.out.println("No hay carreras con estudiantes inscriptos.");
        } else {
            for(ReporteCarreraDTO carreraDTO : reporte){
                System.out.println("Carrera: " + carreraDTO.getNombreCarrera() + " - Cantidad de Inscriptos: " + carreraDTO.getCantidadInscriptos());
            }
        }

        //recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
        //recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
        System.out.println("\n--- Estudiantes de 'Ingeniería en Sistemas' que viven en 'Córdoba' ---");
        String carreraFiltro = "Ingeniería en Sistemas";
        String ciudadFiltro = "Córdoba";
        List<EstudianteDTO> estudiantesFiltrados = estudianteRepository.getEstudiantesByCarreraAndCiudad(carreraFiltro, ciudadFiltro);

        if (estudiantesFiltrados.isEmpty()) {
            System.out.println("No se encontraron estudiantes de la carrera '" + carreraFiltro + "' en la ciudad '" + ciudadFiltro + "'.");
        } else {
            for (EstudianteDTO est : estudiantesFiltrados) {
                System.out.println(est.getApellido() + ", " + est.getNombre() + " - DNI: " + est.getDni());
            }
        }



    }

}

