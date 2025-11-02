package grupo4.mscvmonopatin.repository;

import grupo4.mscvmonopatin.entity.Monopatin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonopatinRepository extends JpaRepository<Monopatin, Long> {
}
