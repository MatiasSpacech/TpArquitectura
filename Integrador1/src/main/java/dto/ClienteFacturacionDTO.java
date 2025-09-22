package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
// este DTO lo usamos para devolver datos de clientes, ordenada por a cuál se le facturó más.
public class ClienteFacturacionDTO {
    private int idCliente;
    private String nombre;
    private double totalFacturado;
}
