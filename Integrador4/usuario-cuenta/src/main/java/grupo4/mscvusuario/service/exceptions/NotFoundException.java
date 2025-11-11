package grupo4.mscvusuario.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="La entidad buscada no existe")
public class NotFoundException extends RuntimeException{
    public NotFoundException(String entity, Long id) {
        super(String.format("La entidad %s con id %s no existe", entity, id));
    }
}
