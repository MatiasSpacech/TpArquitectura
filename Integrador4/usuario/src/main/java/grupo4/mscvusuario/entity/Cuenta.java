package grupo4.mscvusuario.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean estado;
    private LocalDate fechaAlta;
    private String tipoCuenta;

    @ManyToMany( mappedBy = "cuentas", cascade = CascadeType.ALL)
    private List<Usuario> usuarios;
}
