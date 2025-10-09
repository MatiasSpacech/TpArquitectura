package com.integrador3.repositorios;

import com.integrador3.model.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("CarreraRepositorio")
public interface CarreraRepositorio extends JpaRepository<Carrera, Integer> {
}
