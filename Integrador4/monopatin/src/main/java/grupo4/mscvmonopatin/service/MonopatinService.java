package grupo4.mscvmonopatin.service;

import grupo4.mscvmonopatin.entity.Monopatin;
import grupo4.mscvmonopatin.repository.MonopatinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MonopatinService {

    @Autowired
    private MonopatinRepository monopatinRepository;

    @Transactional(readOnly = true)
    public List<Monopatin> findAll() {
        return monopatinRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Monopatin findById(Long id) {
        return monopatinRepository.findById(id).orElse(null);
    }

    @Transactional
    public Monopatin crearMonopatin(Monopatin monopatin) {
        return monopatinRepository.save(monopatin);
    }

    @Transactional
    public void deleteMonopatin(Monopatin monopatin) {
        monopatinRepository.delete(monopatin);
    }
}
