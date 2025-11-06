package grupo4.mscvcuenta.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleFeignException(RuntimeException ex, WebRequest request) {
        if (ex.getMessage() != null && ex.getMessage().startsWith("FEIGN_ERROR")) {
            Map<String, String> body = Map.of(
                    "error", "Error de comunicación con servicio externo",
                    "message", ex.getMessage()
            );
            return new ResponseEntity<>(body, HttpStatus.BAD_GATEWAY); // 502 Bad Gateway
        }
        // Para cualquier otra RuntimeException no controlada
        Map<String, String> body = Map.of(
                "error", "Error Interno del Servidor",
                "message", "Ocurrió un error inesperado."
        );
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
