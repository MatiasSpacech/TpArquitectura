package grupo4.parada.dtos;



public record ParadaPatchDTO(
        String nombre,
        String ciudad,
        String direccion,
        Double latitud,
        Double longitud
) {
}
