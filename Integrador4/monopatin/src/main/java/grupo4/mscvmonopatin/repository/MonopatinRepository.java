package grupo4.mscvmonopatin.repository;

import grupo4.mscvmonopatin.model.Monopatin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MonopatinRepository")
public interface MonopatinRepository extends MongoRepository<Monopatin, String> {

    // Spring data los traduce a la query en mongo db.monopatines.find({ idParada: ? })
    List<Monopatin> findByIdParada(Long idParada);
}
