package grupo4.mscvcuenta.dto;

import lombok.Getter;
import lombok.Setter;

public class UsuarioDto {
    private Long id;
    @Setter
    @Getter
    private String userName;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String lastName;
    @Setter
    @Getter
    private String email;
    @Setter
    @Getter
    private String rol;
    @Setter
    @Getter
    private Double latitud;
    @Setter
    @Getter
    private Double longitud;

    public UsuarioDto(String userName, String name, String lastName, String email, String rol, Double latitud, Double longitud) {
        this.userName = userName;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.rol = rol;
        this.latitud = latitud;
        this.longitud = longitud;
    }

}

