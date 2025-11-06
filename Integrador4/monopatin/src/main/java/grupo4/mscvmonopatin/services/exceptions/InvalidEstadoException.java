package grupo4.mscvmonopatin.services.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.service.annotation.GetExchange;

@Getter
public class InvalidEstadoException extends IllegalArgumentException {
    private final String message;

    public InvalidEstadoException(String estado) {
        this.message = String.format("El estado pasado no es valido: %s",estado);
    }
}
