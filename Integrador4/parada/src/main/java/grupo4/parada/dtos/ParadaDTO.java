package grupo4.parada.dtos;

import grupo4.parada.model.Parada;
import grupo4.parada.feignModel.Monopatin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ParadaDTO {
    private Long id;
    private String nombre;
    private String ciudad;
    private String direccion;
    private Double latitud;
    private Double longitud;

    // Lista de monopatines libres en esta parada (se rellenará desde el service)
    private List<Monopatin> monopatinesLibres;

    public ParadaDTO(Parada parada) {
        this.id = parada.getId();
        this.nombre = parada.getNombre();
        this.ciudad = parada.getCiudad();
        this.direccion = parada.getDireccion();
        this.latitud = parada.getLatitud();
        this.longitud = parada.getLongitud();
    }
}
