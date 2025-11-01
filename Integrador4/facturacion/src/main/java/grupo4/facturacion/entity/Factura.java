package grupo4.facturacion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;


import java.util.Date;

@EnableFeignClients


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "factura")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "numero_factura", nullable = false, unique = true)
    private String numeroFactura;
    private String usuario; //
    @Column(name = "fecha_emision")
    private Date fechaEmision;
    private double monto;
    private String estado; // Pagada o Pendiente de pago

    @Column(name = "usuario_id")
    private Long usuarioId;

    public Factura(String numeroFactura, String usuario, Date fechaEmision,
                   double monto, String estado, Long usuarioId) {
        this.numeroFactura = numeroFactura;
        this.usuario = usuario;
        this.fechaEmision = fechaEmision;
        this.monto = monto;
        this.estado = estado;
        this.usuarioId = usuarioId;
    }
}
