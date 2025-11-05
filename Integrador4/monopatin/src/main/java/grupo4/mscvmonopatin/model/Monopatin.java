package grupo4.mscvmonopatin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "monopatines")
public class Monopatin {
    private String id;
    private Estado estado;
    private int latitud;
    private int longitud;
    private Long kmRecorridos;
}
