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

    public FacturaDTO(String numeroFactura, String usuario, String fechaEmision,
                      double monto, String estado, Long usuarioId) {
        this.numeroFactura = numeroFactura;
        this.usuario = usuario;
        this.fechaEmision = fechaEmision;
        this.monto = monto;
        this.estado = estado;
        this.usuarioId = usuarioId;
    }


    @Override
    public String toString() {
        return "FacturaDTO{" +
                "id=" + id +
                ", numeroFactura='" + numeroFactura + '\'' +
                ", usuario='" + usuario + '\'' +
                ", fechaEmision='" + fechaEmision + '\'' +
                ", monto=" + monto +
                ", estado='" + estado + '\'' +
                ", usuarioId=" + usuarioId +
                '}';
    }

}
