package grupo4.viajes.services.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotFoundException extends RuntimeException {
    private final String message;

    public NotFoundException(String entity, Object id) {
        this.message = String.format("La entidad %s con id %s no encontrado", entity, id);
    }
}
