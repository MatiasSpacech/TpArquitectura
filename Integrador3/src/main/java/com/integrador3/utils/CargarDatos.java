package com.integrador3.utils;

import com.integrador3.model.Carrera;
import com.integrador3.model.Estudiante;
import com.integrador3.model.EstudianteCarrera;
import com.integrador3.repositorios.CarreraRepositorio;
import com.integrador3.repositorios.EstudianteCarreraRepositorio;
import com.integrador3.repositorios.EstudianteRepositorio;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.ArrayList;

@Component
public class CargarDatos {

    private final String rutaCsv="src/main/resources/csv/";

    @Autowired
    private EstudianteRepositorio estudianteRepositorio;

    @Autowired
    private CarreraRepositorio carreraRepositorio;

    @Autowired
    private EstudianteCarreraRepositorio estudianteCarreraRepositorio;


    public void cargarDatos() {
            cargarEstudiantes(rutaCsv+"estudiantes.csv");
            cargarCarreras(rutaCsv+"carreras.csv");
            cargarEstudianteCarrera(rutaCsv+"estudianteCarrera.csv");
    }

    private void cargarEstudiantes(String ubicacion){
        try{
            ArrayList<Estudiante> estudiantes = new ArrayList<>();
            CSVParser registros = CSVFormat.DEFAULT.withHeader().parse(new FileReader(ubicacion));
            for(CSVRecord registro:registros){
                Estudiante estudiante=new Estudiante(
                        Long.parseLong(registro.get(0)),
                        registro.get(1),
                        registro.get(2),
                        Integer.parseInt(registro.get(3)),
                        registro.get(4),
                        registro.get(5),
                        Integer.parseInt(registro.get(6))
                );
                estudiantes.add(estudiante);
            }
            if (estudiantes.isEmpty()){
                System.out.println("No se pudieron cargar los estudiantes");
                
            }else {
                estudianteRepositorio.saveAll(estudiantes);
                System.out.println("Estudiantes cargados correctamente");
            }

        }catch(Exception e){
            System.err.println("Error al cargar estudiantes: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void cargarCarreras(String ubicacion){
        try {
            ArrayList<Carrera> carreras = new ArrayList<>();
            CSVParser registros = CSVFormat.DEFAULT.withHeader().parse(new FileReader(ubicacion));
            for(CSVRecord registro:registros){
                Carrera carrera = new Carrera(
                        Integer.parseInt(registro.get(0)),
                        registro.get(1),
                        Integer.parseInt(registro.get(2))
                );
                carreras.add(carrera);
            }
            carreraRepositorio.saveAll(carreras);
        }catch(Exception e){
            System.err.println("Error al cargar carreras: " + e.getMessage());
            e.printStackTrace();
        }

    }
    private void cargarEstudianteCarrera(String ubicacion){
        try{
            ArrayList<EstudianteCarrera> matriculas = new ArrayList<>();
            CSVParser registros = CSVFormat.DEFAULT.withHeader().parse(new FileReader(ubicacion));
            for(CSVRecord registro:registros){
                Carrera carrera = carreraRepositorio.findById( Integer.parseInt(registro.get(2))).orElse(null);
                Estudiante estudiante = estudianteRepositorio.findById( Long.parseLong(registro.get(1))).orElse(null);
                EstudianteCarrera matricula = new EstudianteCarrera(
                        Long.parseLong(registro.get("id")),
                        Integer.parseInt(registro.get("inscripcion")),
                        Integer.parseInt(registro.get("graduacion")),
                        Integer.parseInt(registro.get("antiguedad")),
                        estudiante,
                        carrera
                );
                matriculas.add(matricula);
                estudianteCarreraRepositorio.save(matricula);
            }
        }catch(Exception e){
            System.err.println("Error al cargar estudiante-carrera: " + e.getMessage());
            e.printStackTrace();
        }
    }




}
