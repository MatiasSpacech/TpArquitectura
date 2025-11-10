package grupo4.facturacion.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table (name = "factura")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_factura", nullable = false, unique = true)
    private String numeroFactura;

    @Column(name = "fecha_emision")
    private Date fechaEmision;
    
    private double importe;
    
    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "tarifa_id")
    private Long tarifaId;
}
