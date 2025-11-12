package grupo4.facturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalFacturadoDTO {
    private int anio;
    private int mesDesde;
    private int mesHasta;
    private double totalFacturado;

}
