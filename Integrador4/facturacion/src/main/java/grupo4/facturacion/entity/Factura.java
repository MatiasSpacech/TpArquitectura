package grupo4.facturacion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;


import java.util.Date;

@EnableFeignClients

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private Long UsuarioId;


}
