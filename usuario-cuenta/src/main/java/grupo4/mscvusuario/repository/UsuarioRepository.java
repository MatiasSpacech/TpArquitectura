package grupo4.mscvusuario.repository;

import grupo4.mscvusuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = """
        SELECT CASE WHEN EXISTS (
            SELECT c 
            FROM Usuario u JOIN u.cuentas c
            WHERE u.id = :idUsuario AND c.id = :idCuenta
        ) 
        THEN TRUE ELSE FALSE END
        """)
    boolean existsCuentaInUsuario(Long idUsuario, Long idCuenta);


    @Query("""
            SELECT u.id
            FROM Usuario u
            WHERE u.rol = UPPER(:rol) 
            """)
    Set<Long> getUsuariosByRol(String rol);

    // ASUMIMOS que el 'username' del LoginDTO es el 'email' en la Entidad Usuario.
    // Usamos JOIN FETCH para traer las cuentas de una vez y evitar errores
    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.cuentas WHERE u.email = :email")
    Optional<Usuario> findByEmail(@Param("email") String email);
}
