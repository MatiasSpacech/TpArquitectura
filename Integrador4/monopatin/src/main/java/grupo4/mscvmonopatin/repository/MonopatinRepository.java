package grupo4.mscvmonopatin.repository;

import grupo4.mscvmonopatin.model.Estado;
import grupo4.mscvmonopatin.model.Monopatin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MonopatinRepository")
public interface MonopatinRepository extends MongoRepository<Monopatin, String> {

    // Spring data los traduce a la query en mongo db.monopatines.find({ idParada: ? })
    List<Monopatin> findByIdParada(Long idParada);

    @Query("{ 'estado' : ?0, 'idParada': ?1 }")
    List<Monopatin> findByEstadoStringAndIdParada(String estado, Long idParada);

    // Traer todos los monopatines que sus
    @Query("{ 'kmRecorridos' : { $gte: ?0 } }")
    List<Monopatin> getReportesMantenimiento(Integer kmRecorridos);

}
