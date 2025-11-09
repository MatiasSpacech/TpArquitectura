package grupo4.mscvusuario.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String idMercadoPago;

    @Column
    private BigDecimal saldo;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCuenta estado; // Enum con ACTIVA, SUSPENDIDA

    @Column(nullable = false)
    private LocalDate fechaAlta;


    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta", nullable = false)
    private TipoCuenta tipoCuenta; // Enum con BASICA, PREMIUM

    // ¡ESTA ES LA DUEÑA DE LA RELACIÓN!
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // Usar CascadeType.ALL (o REMOVE) en una relación @ManyToMany es generalmente una mala idea. Significaría que si borras una Cuenta, ¡también se borrarían todos los Usuarios asociados a ella!, incluso si esos usuarios están asociados a otras cuentas. La configuración que tienes (PERSIST, MERGE) es mucho más segura.
    @JoinTable(
            name = "cuenta_usuario",
            joinColumns = @JoinColumn(name = "id_cuenta"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    @JsonIgnoreProperties("cuentas")
    private Set<Usuario> usuarios = new HashSet<>();

    // Campos específicos para la lógica de cuentas PREMIUM
    @Column(name = "km_consumidos_mes")
    private Double kmConsumidosMes; // Se usa solo si tipoCuenta es PREMIUM

    @Column(name = "fecha_renovacion_cupo")
    private LocalDate fechaRenovacionCupo; // Se usa solo si tipoCuenta es PREMIUM

    // Usamos @PrePersist para establecer valores por defecto de forma segura
    @PrePersist
    public void prePersist() {
        if (this.fechaAlta == null) this.fechaAlta = LocalDate.now();
        if (this.estado == null) this.estado = EstadoCuenta.ACTIVA;
        if (this.tipoCuenta == null) this.tipoCuenta = TipoCuenta.BASICA;
        if (this.saldo == null) this.saldo = BigDecimal.ZERO;
        this.kmConsumidosMes = 0.0;
    }

}

