package grupo4.facturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViajeFacturaRequestDTO {
    private Long viajeId;
    private Long usuarioId;
    private Double distanciaKm;
    private Long tarifaId;

    // Nuevo campo para tiempo total de pausa en minutos
    private Integer tiempoPausaMinutos; // tiempo total que el viaje estuvo en pausa
}
