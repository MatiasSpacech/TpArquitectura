package com.integrador3.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Person {

    @Id
    private Long id;
    @Column()
    private String firstName;
    @Column()
    private String lastName;



}
