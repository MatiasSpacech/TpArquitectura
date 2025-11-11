package grupo4.viajes.services.exceptions;

public class ViajeFinalizadoException extends RuntimeException {
    public ViajeFinalizadoException(Long id) {
        super("El viaje con id: " + id + " ya ha sido finalizado");
    }
}
