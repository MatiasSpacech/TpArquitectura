package grupo4.parada.repository;

import grupo4.parada.model.Parada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParadaRepository extends JpaRepository<Parada, Long> {

    /*La fórmula del Haversine calcula la distancia más corta (aérea) entre dos puntos
    en una esfera (la Tierra) utilizando sus coordenadas de latitud y longitud.
    Convierte la distancia angular calculada con trigonometría en una distancia lineal
    (kilómetros o millas) multiplicándola por el radio de la Tierra.*/
    @Query(value =
            "SELECT * " +
            "FROM parada " +
            "WHERE ( " +
            "  6371 * acos( " +
            "    LEAST(1.0, GREATEST(-1.0, " +
            "      cos(radians(:latitudUsuario)) * cos(radians(latitud)) " +
            "      * cos(radians(longitud) - radians(:longitudUsuario)) + " +
            "      sin(radians(:latitudUsuario)) * sin(radians(latitud)) " +
            "    )) " +
            "  ) " +
            ") < :radio " +
            "ORDER BY ( " +
            "  6371 * acos( " +
            "    LEAST(1.0, GREATEST(-1.0, " +
            "      cos(radians(:latitudUsuario)) * cos(radians(latitud)) " +
            "      * cos(radians(longitud) - radians(:longitudUsuario)) + " +
            "      sin(radians(:latitudUsuario)) * sin(radians(latitud)) " +
            "    )) " +
            "  ) " +
            ")",
            nativeQuery = true)
    List<Parada> findParadasCercanas(Double latitudUsuario, Double longitudUsuario,
                                     Double radio);
}
