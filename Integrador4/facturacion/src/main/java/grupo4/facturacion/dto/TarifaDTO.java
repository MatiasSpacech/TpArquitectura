package grupo4.facturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TarifaDTO {
    private Long id;
    private double monto; // precio por km
    private double montoExtra; // tarifa fija adicional
    private Date date;

    // Campos para recargo por pausa
    private Integer tiempoMaximoPausaMinutos;
    private Double porcentajeRecargoPausa;

    // cuota mensual para usuarios premium
    private Double cuotaMensualPremium; // ej: 5000.0 (valor fijo mensual que paga el premium)

    @Override
    public String toString() {
        return "TarifaDTO [monto=" + monto +
                ", montoExtra=" + montoExtra +
                ", tiempoMaximoPausaMinutos=" + tiempoMaximoPausaMinutos +
                ", porcentajeRecargoPausa=" + porcentajeRecargoPausa +
                ", cuotaMensualPremium=" + cuotaMensualPremium +
                ", date=" + date + "]";
    }
}
