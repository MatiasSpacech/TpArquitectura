package grupo4.facturacion.entity;

import grupo4.mscvusuario.entity.Usuario;
import grupo4.tarifas.entity.Tarifa;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cloud.openfeign.EnableFeignClients;


import java.util.Date;
import java.util.List;

@EnableFeignClients


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


    @OneToOne
    private Usuario usuario;

    @OneToMany (mappedBy = "factura")
    private List<Tarifa> tarifas;


}
