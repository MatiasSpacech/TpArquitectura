package com.integrador3.servicios;


import com.integrador3.dto.ReportePersonDto;
import com.integrador3.repositorios.PhoneBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneBookServices {

    @Autowired
    private PhoneBookRepository phoneBookRepository;

    public List<ReportePersonDto>obtenerReportePersonas(){
        return phoneBookRepository.obtenerReportePersonas();
}
}
