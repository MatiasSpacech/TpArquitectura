package grupo4.mscvusuario.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Saldo insuficiente para completar la operación.")
public class SaldoInsuficenteException extends RuntimeException{
    public SaldoInsuficenteException(BigDecimal saldoRequerido, BigDecimal saldoActual) {
        super("Fallo de débito. Saldo actual: " + saldoActual + ", requerido: " + saldoRequerido);
    }
}
