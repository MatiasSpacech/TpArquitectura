package grupo4.viajes.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ReporteViajePeriodoDTO {
    private String idMonopatin;
    private int cantidadViajes;
    private int anio;
}
