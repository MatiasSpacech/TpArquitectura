package grupo4.tarifas.dto;

import grupo4.tarifas.entity.Tarifa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TarifaDTO {
    private Long id;
    private Double monto; // precio por km
    private Double montoExtra; // tarifa fija adicional
    private Date fecha;

    // este debe de recibirse para calcular recargos por pausas en el viaje desde el microservicio de viajes
    private Integer tiempoMaximoPausaMinutos; // tiempo máximo de pausa sin recargo (ej: 10 minutos)
    private Double porcentajeRecargoPausa; // porcentaje de recargo por exceso de pausa (ej: 0.20 = 20%)
    private Double cuotaMensualPremium;


    public TarifaDTO(Double monto, Double montoExtra, Date fecha) {
        this.monto = monto;
        this.montoExtra = montoExtra;
        this.fecha = fecha;
    }


    public TarifaDTO(Tarifa tarifa) {
        this.id = tarifa.getId();
        this.monto = tarifa.getMonto();
        this.montoExtra = tarifa.getMontoExtra();
        this.fecha = tarifa.getFecha();
        this.tiempoMaximoPausaMinutos = tarifa.getTiempoMaximoPausaMinutos();
        this.porcentajeRecargoPausa = tarifa.getPorcentajeRecargoPausa();
        this.cuotaMensualPremium = tarifa.getCuotaMensualPremium();
    }

    @Override
    public String toString() {
        return "TarifaDTO [monto=" + monto +
                ", montoExtra=" + montoExtra +
                ", tiempoMaximoPausaMinutos=" + tiempoMaximoPausaMinutos +
                ", porcentajeRecargoPausa=" + porcentajeRecargoPausa +
                ", cuotaMensualPremium=" + cuotaMensualPremium +
                ", fecha=" + fecha + "]";
    }
}
