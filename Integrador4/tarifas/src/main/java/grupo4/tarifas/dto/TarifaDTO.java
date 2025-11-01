package grupo4.tarifas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;


@NoArgsConstructor
@Getter
public class TarifaDTO {
    private double monto;
    private double montoExtra;
    private Date date;

    public TarifaDTO(double monto, double montoExtra, Date date) {
        this.monto = monto;
        this.montoExtra = montoExtra;
        this.date = date;
    }

    @Override
    public String toString() {
        return "TarifaDTO [monto=" + monto +
                ", montoExtra=" + montoExtra +
                ", date=" + date + "]";
    }
}
