package com.integrador3.servicios.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
// RequiredArgsConstructor: Genera un constructor con todos los atributos de tipo final(obligatorio)
@RequiredArgsConstructor
public class NotFoundException extends RuntimeException{
    private final String message;

    public NotFoundException(String entity, int id){
        this.message = String.format("La entidad %s con id %d no encontrado", entity, id);
    }
}
