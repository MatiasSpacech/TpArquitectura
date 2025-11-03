package grupo4.mscvmonopatin.dto;

import lombok.Data;

@Data
public class MonopatinDTO {
    private Long id;
    private String codigo;
    private String estado;
    private double latitud;
    private double longitud;
    private double kmRecorridos;
    private long tiempoDeUsoTotal;
    private Long idParada;


}
