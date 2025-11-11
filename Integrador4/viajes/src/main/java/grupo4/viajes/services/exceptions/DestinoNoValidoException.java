package grupo4.viajes.services.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DestinoNoValidoException extends RuntimeException {
    private final String message;

    public DestinoNoValidoException(Long id) {
        this.message = String.format("El destino con id %s no concuerda con la ubicacion del monopatin",id);
    }
}
