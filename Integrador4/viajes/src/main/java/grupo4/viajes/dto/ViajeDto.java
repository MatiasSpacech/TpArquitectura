package grupo4.viajes.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ViajeDto {
    private Long idCuenta;
    private Long idMonopatin;
    private double latitudInicio;
    private double longitudInicio;
    private Double kilometrosRecorridos;

    public ViajeDto(Long idCuenta, Long idMonopatin, double latitudInicio, double longitudInicio, Double kilometrosRecorridos) {
        this.idCuenta = idCuenta;
        this.idMonopatin = idMonopatin;
        this.latitudInicio = latitudInicio;
        this.longitudInicio = longitudInicio;
        this.kilometrosRecorridos = kilometrosRecorridos;
    }
}
