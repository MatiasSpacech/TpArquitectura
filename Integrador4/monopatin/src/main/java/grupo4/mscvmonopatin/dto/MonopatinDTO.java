package grupo4.mscvmonopatin.dto;

import grupo4.mscvmonopatin.entity.Monopatin;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MonopatinDTO {
    private Long id;
    private String codigo;
    private String estado;
    private double latitud;
    private double longitud;
    private double kmRecorridos;
    private long tiempoDeUsoTotal;
    private Long idParada;

    public MonopatinDTO(Monopatin monopatin) {
        this.id = monopatin.getId();
        this.codigo = monopatin.getCodigo();
        this.estado = monopatin.getEstado().name();
        this.latitud = monopatin.getLatitud();
        this.longitud = monopatin.getLongitud();
        this.kmRecorridos = monopatin.getKmRecorridos();
        this.tiempoDeUsoTotal = monopatin.getTiempoDeUsoTotal();
        this.idParada = monopatin.getIdParada();
    }


}
