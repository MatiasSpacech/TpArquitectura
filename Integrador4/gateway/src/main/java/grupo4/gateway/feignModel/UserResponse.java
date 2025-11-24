package grupo4.gateway.feignModel;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
// LO QUE ME DEVUELVE CADA PETICION EN EL FEIGN
public class UserResponse {
    private Long id;
    private String username;
    private String password;
    private String rol;
    private boolean premium;
}
