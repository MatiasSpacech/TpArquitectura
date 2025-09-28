package com.grupo4;

import com.grupo4.dto.EstudianteDTO;
import com.grupo4.dto.ReporteCarreraDTO;
import com.grupo4.model.Carrera;
import com.grupo4.model.Estudiante;
import com.grupo4.repository.*;
import com.grupo4.utils.CargarDatos;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CargarDatos cd = new CargarDatos();
        //cd.run();


        //REPOSITORYS
        CarreraRepository cr = new CarreraRepositoryImpl();
        EstudianteRepository er = new EstudianteRepositoryImpl();
        EstudianteCarreraRepository ecr = new EstudianteCarreraRepositoryImpl();


        //PRUEBA DE METODOS

        //estudiantes ordenados
        List<EstudianteDTO> estudiantes = er.getEstudiantesSorted("ciudad");
        for(EstudianteDTO estudiante:estudiantes){
            System.out.println(estudiante);
        }
        System.out.println("\n");

        //estudiante por nroLibreta
        EstudianteDTO estudiante = er.getEstudianteLU(6160722);
        System.out.println(estudiante);
        System.out.println();

        //estudiantes por genero
        List<EstudianteDTO> estudiantesGenero = er.getEstudiantesByGenero("MasCuLIno");
        for(EstudianteDTO estudianteGenero:estudiantesGenero){
            System.out.println(estudianteGenero);
        }
        System.out.println("\n");

        //estudiantes por carrera y ciudad
        List<EstudianteDTO> estudiantesCarrera = er.getEstudiantesByCarreraAndCiudad("aRtE", "Jiaoyuan");
        for(EstudianteDTO estudianteCarrera:estudiantesCarrera){
            System.out.println(estudianteCarrera);
        }
        System.out.println("\n");

        //reportes de carrera por graduacion y nombre
        List<ReporteCarreraDTO> reportes = ecr.getReportesCarreras();
        for(ReporteCarreraDTO reporte:reportes){
            System.out.println(reporte);
        }

        //prueba para buscar un estudiante por id y traer sus carreras,se debe implementar toString en ambas entidades,estudiante y matriculas
        //Estudiante es=er.findById(71779527);
        //System.out.println(es);

    }
}