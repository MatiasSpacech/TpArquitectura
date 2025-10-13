package com.integrador3.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Estudiante> findAll() {
        return estudianteRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Estudiante findById(Long id) {
        return estudianteRepositorio.findById(id).orElse(null);
    }

    @Transactional
    public Estudiante save(Estudiante estudiante) {
        return estudianteRepositorio.save(estudiante);
    }

    @Transactional
    public void deleteById(Long id) {
        estudianteRepositorio.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<EstudianteDTO> getEstudiantesOrderBy(String criterio) {
        return estudianteRepositorio.getEstudiantesOrderBy(criterio);
    }
    @Transactional(readOnly = true)
    public EstudianteDTO findEstudianteByNroLibreta(Long nroLibreta) {
        return estudianteRepositorio.findEstudianteByNroLibreta(nroLibreta);
    }
    @Transactional(readOnly = true)
    public List<EstudianteDTO> findEstudiantesByGenero(String genero) {
        return estudianteRepositorio.findEstudiantesByGenero(genero);
    }
    @Transactional(readOnly = true)
    public List<EstudianteDTO> findEstudiantesByCarreraAndCiudad(String carrera,
            String ciudad) {
        return estudianteRepositorio.findEstudiantesByCarreraAndCiudad(carrera, ciudad);
    }       
}
