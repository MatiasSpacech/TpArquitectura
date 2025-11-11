package grupo4.viajes.services.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CuentaNoAsociadaException extends RuntimeException {
    private final String message;

    public CuentaNoAsociadaException(Long id) {
       this.message = String.format("La cuenta no esta asociada a este usuario: %s",id);
    }
}
