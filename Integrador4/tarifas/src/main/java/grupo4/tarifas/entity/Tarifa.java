package grupo4.tarifas.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "tarifa")
public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private double monto; // precio por km
    private double montoExtra; // tarifa fija adicional
    private Date fecha;

    @Column(name = "tiempo_maximo_pausa_minutos")
    private Integer tiempoMaximoPausaMinutos; // ej: 10 minutos

    @Column(name = "porcentaje_recargo_pausa")
    private Double porcentajeRecargoPausa; // ej: 0.20 (= 20%)

    @Column(name = "cuota_mensual_premium")
    private Double cuotaMensualPremium; // ej: 5000.0


}
