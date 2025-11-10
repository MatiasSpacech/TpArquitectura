package grupo4.viajes.services.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DestinoNoValido extends RuntimeException {
    private final String message;

    public DestinoNoValido(Long id) {
        this.message = String.format("El destino con id %s no concuerda con la ubicacion del monopatin",id);
    }
}
