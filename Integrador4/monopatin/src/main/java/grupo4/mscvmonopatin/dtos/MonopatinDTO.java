package grupo4.mscvmonopatin.dtos;

import grupo4.mscvmonopatin.model.Estado;
import grupo4.mscvmonopatin.model.Monopatin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class MonopatinDTO {
    private Estado estado;
    private double latitud;
    private double longitud;
    private int kmRecorridos;

    public MonopatinDTO(Monopatin monopatin) {
        this.estado = monopatin.getEstado();
        this.latitud = monopatin.getLatitud();
        this.longitud = monopatin.getLongitud();
        this.kmRecorridos = monopatin.getKmRecorridos();
    }
}
