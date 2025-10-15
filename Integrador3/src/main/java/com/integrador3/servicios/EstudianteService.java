package com.integrador3.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.integrador3.dto.EstudianteDTO;
import com.integrador3.model.Estudiante;
import com.integrador3.repositorios.EstudianteRepositorio;



@Service
public class EstudianteService {
    // Inyectar el repositorio de estudiantes
    @Autowired
    private EstudianteRepositorio estudianteRepositorio;

    @Transactional(readOnly = true)
    public List<EstudianteDTO> findAll() {
        return estudianteRepositorio.findAll().stream().map(EstudianteDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public EstudianteDTO findById(Long id) {
        return estudianteRepositorio.findById(id).map(EstudianteDTO::new)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }

    @Transactional(readOnly = true)
    public Estudiante findByIdEntity(Long id){
        return estudianteRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }

    @Transactional
    public EstudianteDTO save(Estudiante estudiante) {
        Estudiante esSave = estudianteRepositorio.save(estudiante);
        return new EstudianteDTO(esSave);
    }

    @Transactional
    public void deleteById(Long id) {
        estudianteRepositorio.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<EstudianteDTO> getEstudiantesOrderBy(String criterio) {
        criterio = criterio.toLowerCase();

        List<String> camposOrdenados = List.of("dni", "nombre","apellido", "nroLibreta", "genero", "edad", "ciudad");
        if(!camposOrdenados.contains(criterio)){
            return new ArrayList<>();
        }

        return estudianteRepositorio.findAll(Sort.by(criterio))
                .stream().map(EstudianteDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public EstudianteDTO findEstudianteByNroLibreta(Long nroLibreta) {
        Estudiante estudiante = estudianteRepositorio.findEstudianteByNroLibreta(nroLibreta);
        if (estudiante == null) {
            throw new RuntimeException("Estudiante con libreta " + nroLibreta + " no encontrado");
        }
        return new EstudianteDTO(estudiante);   
    }

    @Transactional(readOnly = true)
    public List<EstudianteDTO> findEstudiantesByGenero(String genero) {
        return estudianteRepositorio.findEstudiantesByGenero(genero)
                .stream().map(EstudianteDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<EstudianteDTO> findEstudiantesByCarreraAndCiudad(String carrera,
            String ciudad) {
        return estudianteRepositorio.findEstudiantesByCarreraAndCiudad(carrera, ciudad)
                .stream().map(EstudianteDTO::new).toList();
    }       
}
