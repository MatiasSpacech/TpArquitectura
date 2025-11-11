package grupo4.viajes.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReporteViajeUsuariosDTO {
    private Long idUsuario;
    private Long cantViajes,cantKmTotales,tiempoTotal;
}
