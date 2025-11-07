package grupo4.mscvmonopatin.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@ToString
//@AllArgsConstructor

@Document(collection = "monopatines")
public class Monopatin {
    private String id;
    private Estado estado;
    private double latitud;
    private double longitud;
    private int kmRecorridos;
    private int tiempoUsado;
    private Long idParada;

    public Monopatin(Estado estado, double latitud, double longitud,Long idParada) {
        this.estado = estado;
        this.latitud = latitud;
        this.longitud = longitud;
        this.kmRecorridos = 0;
        this.tiempoUsado = 0;
        this.idParada = idParada;
    }
}
