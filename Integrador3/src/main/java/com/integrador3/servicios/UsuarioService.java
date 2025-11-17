package com.integrador3.servicios;


import com.integrador3.dto.ReporteVentaDto;
import com.integrador3.model.Usuario;
import com.integrador3.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
      private UsuarioRepositorio usuarioRepositorio;


    public List<ReporteVentaDto>obtenerReporteVentas(int edad){
        return usuarioRepositorio.obtenerReporteVentas(edad);
    }
}
