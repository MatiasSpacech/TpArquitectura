package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ClienteFacturacionDTO {
    private int idCliente;
    private String nombre;
    private double totalFacturado;
}
