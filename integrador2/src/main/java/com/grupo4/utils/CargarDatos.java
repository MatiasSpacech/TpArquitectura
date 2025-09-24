package com.grupo4.utils;
import com.grupo4.model.Carrera;
import com.grupo4.model.Estudiante;
import com.grupo4.model.EstudianteCarrera;
import com.grupo4.repository.CarreraRepositoryImpl;
import com.grupo4.repository.EstudianteCarreraRepositoryImpl;
import com.grupo4.repository.EstudianteRepository;
import com.grupo4.repository.EstudianteRepositoryImpl;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.util.ArrayList;

public class CargarDatos {

    public void run(){
        cargarEstudiantes("src/main/resources/csv/estudiantes.csv");
        cargarMatriculas("src/main/resources/csv/estudianteCarrera.csv");
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
            EstudianteRepository er = new EstudianteRepositoryImpl();
            er.cargarEstudiantes(estudiantes);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void cargarMatriculas(String ubicacion){
        try{
            EstudianteCarreraRepositoryImpl ecr = new EstudianteCarreraRepositoryImpl();
            EstudianteRepositoryImpl er = new EstudianteRepositoryImpl();
            CarreraRepositoryImpl cr = new CarreraRepositoryImpl();
            ArrayList<EstudianteCarrera> matriculas = new ArrayList<>();
            CSVParser registros = CSVFormat.DEFAULT.withHeader().parse(new FileReader(ubicacion));
            for(CSVRecord registro:registros){
                Carrera carrera = cr.findById(Long.parseLong(registro.get(2)));
                Estudiante estudiante = er.findById(Long.parseLong(registro.get(1)));
                EstudianteCarrera matricula = new EstudianteCarrera(
                        Integer.parseInt(registro.get(3)),
                        Integer.parseInt(registro.get(4)),
                        Integer.parseInt(registro.get(5)),
                        estudiante,
                        carrera
                );
                matriculas.add(matricula);
            }
            ecr.matricularEstudiantes(matriculas);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

