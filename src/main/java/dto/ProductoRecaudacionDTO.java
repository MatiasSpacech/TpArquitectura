package dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductoRecaudacionDTO {
    private int idProducto;
    private String nombre;
    private Double recaudacion;

}
