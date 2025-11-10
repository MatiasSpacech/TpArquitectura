package grupo4.mscvmonopatin.dtos;

import grupo4.mscvmonopatin.model.Monopatin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ReporteMantenimientoDTO {
    private String id;
    private int kmTotales;

    public ReporteMantenimientoDTO(Monopatin monopatin) {
        this.id = monopatin.getId();
        this.kmTotales = monopatin.getKmRecorridos();
    }
}
