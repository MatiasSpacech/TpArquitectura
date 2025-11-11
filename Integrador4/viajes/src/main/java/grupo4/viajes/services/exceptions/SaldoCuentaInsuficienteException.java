package grupo4.viajes.services.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SaldoCuentaInsuficienteException extends RuntimeException {
    private final String message;

    public SaldoCuentaInsuficienteException(Long id) {
        this.message = String.format("La cuenta con id: %s, no tiene salgo suficiente",id);
    }
}
