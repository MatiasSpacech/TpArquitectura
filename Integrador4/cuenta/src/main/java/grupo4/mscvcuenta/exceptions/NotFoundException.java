package grupo4.mscvcuenta.exceptions;

public class NotFoundException extends RuntimeException {
    private final String mensaje;

    public NotFoundException(String entity, String id) {
        this.mensaje = String.format("La entidad %s con id %s no fue encontrada.", entity, id);
    }
}
