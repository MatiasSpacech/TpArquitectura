package com.integrador3.repositorios;


import com.integrador3.model.Person;
import com.integrador3.model.PhoneBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Person, Long> {

//    @Query("SELECT p FROM Person p WHERE p.lastName LIKE 'Dade'")
//    List<Person> obtenerReporte();

    @Query("""
    SELECT CONCAT(p.firstName, ' ', p.lastName) AS nombreCompleto,
           COUNT(pb.phoneNumber) AS cantidadTelefonos,
           GROUP_CONCAT(pb.phoneNumber) AS numerosTelefonicos
    FROM Person p
    JOIN PhoneBook pb ON p.lastName = pb.lastName
    GROUP BY p.id, p.firstName, p.lastName
    ORDER BY p.lastName DESC
""")
    List<String> obtenerReporte();


    @Query("SELECT p FROM Person p WHERE p.firstName = :firstName")
    List<Person>obtnerpersonaporNombre(@Param("firstName") String nombre);


}