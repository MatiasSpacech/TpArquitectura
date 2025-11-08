package grupo4.viajes.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "viajes")
@Getter
@Setter
@Data
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idCuenta; // Quién paga

    private Long idMonopatin; // Qué se usa

    private LocalDateTime fechaHoraInicio;

    private LocalDateTime fechaHoraFin; // Null hasta que termine

    private double kilometrosRecorridos;

    @Enumerated(EnumType.STRING)
    private EstadoViaje estado; // Ej: EN_CURSO, FINALIZADO
}
