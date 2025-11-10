package grupo4.viajes.services.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MonopatinNoDisponible extends RuntimeException {
    private final String message;

    public MonopatinNoDisponible(String id, String estado) {
        this.message = String.format("El monopatin con id %s no esta disponible, se encuentra %s", id, estado);
    }
}
