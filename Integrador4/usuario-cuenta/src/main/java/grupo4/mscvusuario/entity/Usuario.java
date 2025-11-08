package grupo4.mscvusuario.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    // INVERSA DE LA RELACIÓN con Cuenta
    // 'mappedBy' debe apuntar al nombre del campo en la entidad dueña (Cuenta).
    @ManyToMany(mappedBy = "usuarios")
    private Set<Cuenta> cuentas = new HashSet<>();

    @Column()
    private Double latitud; // "LATITUD"

    @Column()
    private Double longitud; // "LONGITUD"

}
