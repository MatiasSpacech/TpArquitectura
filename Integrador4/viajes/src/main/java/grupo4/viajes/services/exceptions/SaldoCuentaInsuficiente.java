package grupo4.viajes.services.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SaldoCuentaInsuficiente extends RuntimeException {
    private final String message;

    public SaldoCuentaInsuficiente(Long id) {
        this.message = String.format("La cuenta con id: %s, no tiene salgo suficiente",id);
    }
}
