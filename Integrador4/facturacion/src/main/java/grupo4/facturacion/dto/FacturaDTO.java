package grupo4.facturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FacturaDTO {
    private Long id;
    private String numeroFactura;
    private String usuario;
    private String fechaEmision;
    private double monto;
    private String estado;
    private Long usuarioId;
}
