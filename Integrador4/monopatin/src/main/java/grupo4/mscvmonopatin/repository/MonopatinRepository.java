package grupo4.mscvmonopatin.repository;

import grupo4.mscvmonopatin.model.Monopatin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("MonopatinRepository")
public interface MonopatinRepository extends MongoRepository<Monopatin, String> {

}
