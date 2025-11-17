package com.integrador3.repositorios;

import com.integrador3.dto.ReportePersonDto;
import com.integrador3.model.PhoneBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhoneBookRepository extends JpaRepository<PhoneBook, Long> {

    @Query("SELECT new com.integrador3.dto.ReportePersonDto(" +
            "CONCAT(p.lastName, ', ', p.firstName), " +
            "MIN(pb.phoneNumber), " +
            "COUNT(pb) > 1) " +
            "FROM Person p JOIN PhoneBook pb ON p.lastName = pb.lastName " +
            "GROUP BY p.id, p.firstName, p.lastName " +
            "ORDER BY p.lastName")
    List<ReportePersonDto> obtenerReportePersonas();

}
