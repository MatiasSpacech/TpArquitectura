package grupo4.parada.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "paradas")
@Getter
@Setter
public class Parada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double latitud;

    @Column(nullable = false)
    private double longitud;

    // Un nombre es útil para la gestión en la app web del admin
    @Column(nullable = true)
    private String nombre;
}
