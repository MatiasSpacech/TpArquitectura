package grupo4.facturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TarifaDTO {
    private double monto;
    private double montoExtra;
    private Date date;

    @Override
    public String toString() {
        return "TarifaDTO [monto=" + monto +
                ", montoExtra=" + montoExtra +
                ", date=" + date + "]";
    }
}

