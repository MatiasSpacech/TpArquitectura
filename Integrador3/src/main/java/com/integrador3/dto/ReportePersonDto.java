package com.integrador3.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class ReportePersonDto {
    private String nombreCompleto;
    private String telefono;
    private boolean multiplesTelefonos;

    public ReportePersonDto(String nombreCompleto, String telefono, boolean multiplesTelefonos){
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.multiplesTelefonos = multiplesTelefonos;
    }



}
