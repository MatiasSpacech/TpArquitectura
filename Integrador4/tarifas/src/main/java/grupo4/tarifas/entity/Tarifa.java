package grupo4.tarifas.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "tarifa")
public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double monto;
    @Column(name = "monto_extra")
    private double montoExtra;
    private Date fecha;

    public Tarifa(double monto, double montoExtra, Date date) {
        this.monto = monto;
        this.montoExtra = montoExtra;
        this.fecha = date;
    }
}
