package grupo4.mscvmonopatin.entity;



import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "monopatin")
@Data
public class Monopatin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMonopatin estado;
    private double latitud;
    private double longitud;
    private double kmRecorridos;
    private long tiempoDeUsoTotal;
    private Long idParada;


}
