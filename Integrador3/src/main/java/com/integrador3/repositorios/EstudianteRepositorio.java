package com.integrador3.repositorios;

import com.integrador3.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("EstudianteRepositorio")
public interface EstudianteRepositorio extends JpaRepository<Estudiante, Long> {
}
