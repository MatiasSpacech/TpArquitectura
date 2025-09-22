package dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
// Este dto lo usamos par a retornar los datos del producto que más recaudó
public class ProductoRecaudacionDTO {
    private int idProducto;
    private String nombre;
    private Double recaudacion;
}
