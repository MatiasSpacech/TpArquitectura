package grupo4.viajes.feignModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarifa {
    private Long id;
    private double monto; // precio por km
    private double montoExtra; // tarifa fija adicional
    private Date fecha;

    // este debe de recibirse para calcular recargos por pausas en el viaje desde el microservicio de viajes
    private Integer tiempoMaximoPausaMinutos; // tiempo máximo de pausa sin recargo (ej: 10 minutos)
    private Double porcentajeRecargoPausa; // porcentaje de recargo por exceso de pausa (ej: 0.20 = 20%)
    private Double cuotaMensualPremium;

    public Tarifa(double monto, double montoExtra, Date fecha) {
        this.monto = monto;
        this.montoExtra = montoExtra;
        this.fecha = fecha;
    }
}
