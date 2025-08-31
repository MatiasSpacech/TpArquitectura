package entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Cliente {
    private int id;
    private String nombre;
    private String email;
}
