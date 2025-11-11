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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double monto; // precio por km
    private Double montoExtra; // tarifa fija adicional
    private Date fecha;

    private Integer tiempoMaximoPausaMinutos; // ej: 10 minutos

    private Double porcentajeRecargoPausa; // ej: 0.20 (= 20%)

    private Double cuotaMensualPremium; // ej: 5000.0
}
