package grupo4.mscvcuenta.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nro_cuenta", nullable = false)
    private String numeroCuenta;

    @Column(name = "usuario")
    private String usuarios;

    @Column(name = "monto", nullable = false)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta", nullable = false)
    private TipoCuenta tipoCuenta;

    @Column(name = "fecha_alta", nullable = false)
    LocalDate fechaCreacion;

    // Esto creará una tabla llamada "cuenta_usuarios_ids" con las columnas "id_cuenta" y "id_usuario"
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "cuenta_usuarios_ids", joinColumns = @JoinColumn(name = "id_cuenta"))
    @Column(name = "id_usuario")
    private List<Long> idUsuarios = new ArrayList<>();


    public String getNroCuenta() {
        return numeroCuenta;
    }

    public void setNroCuenta(String id) {
        this.numeroCuenta = id;
    }

}
