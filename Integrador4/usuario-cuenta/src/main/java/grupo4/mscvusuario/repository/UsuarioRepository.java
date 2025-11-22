package grupo4.mscvusuario.repository;

import grupo4.mscvusuario.dtos.LoginDTO;
import grupo4.mscvusuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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

    @Query(value= """
                 SELECT new grupo4.mscvusuario.dtos.LoginDTO(u.id,u.email,u.password,u.rol)
                 FROM Usuario u
                 WHERE LOWER(u.email) = LOWER(:username)
                 """)
    Optional<LoginDTO> getUsuarioByUsername(String username);
}
