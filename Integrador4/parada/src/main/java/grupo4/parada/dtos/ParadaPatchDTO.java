package grupo4.parada.dtos;

import jakarta.persistence.Column;

public record ParadaPatchDTO(
        String nombre,
        String ciudad,
        String direccion,
        Double latitud,
        Double longitud
) {
}
