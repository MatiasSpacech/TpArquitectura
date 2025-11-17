package com.integrador3.servicios;


import com.integrador3.dto.ReportePersonDto;
import com.integrador3.model.Person;
import com.integrador3.repositorios.PersonaRepository;
import com.integrador3.repositorios.PhoneBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private PhoneBookRepository phoneBookRepository;

    public List<Person>findAll(){
        return personaRepository.findAll();

    }

    public List<String>obtenerReporte(){
        return personaRepository.obtenerReporte();
    }

    public List<ReportePersonDto>obtenerPersonasPorNombre(String nombre){
        return phoneBookRepository.obtenerReportePersonas();
    }

}
