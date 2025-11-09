package grupo4.facturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPremiumDTO {
    private Long id;
    private String nombre;
    private boolean esPremium; // true si es usuario premium
    private Double cupoMensualKm; // ej: 100 km gratis por mes
    private Double kmConsumidosMes; // km ya consumidos en el mes actual
    private Integer mesActual; // mes del cupo (1-12)
    private Integer anioActual; // año del cupo
}
