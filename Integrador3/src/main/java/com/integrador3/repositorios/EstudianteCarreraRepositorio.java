package com.integrador3.repositorios;

import com.integrador3.model.EstudianteCarrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteCarreraRepositorio extends JpaRepository<EstudianteCarrera, Long> {

}
