package grupo4.mscvmonopatin.service;

import grupo4.mscvmonopatin.dto.MonopatinDTO;
import grupo4.mscvmonopatin.entity.EstadoMonopatin;
import grupo4.mscvmonopatin.entity.Monopatin;
import grupo4.mscvmonopatin.repository.MonopatinRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonopatinService {

    @Autowired
    private MonopatinRepository monopatinRepository;

    @Transactional(readOnly = true)
    public List<MonopatinDTO> findAll() {
        List<Monopatin> monopatines = monopatinRepository.findAll();
        return monopatines.stream()
                .map(MonopatinDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MonopatinDTO findById(Long id) {
        Monopatin monopatin = monopatinRepository.findById(id).orElse(null);
        if (monopatin != null) {
            return new MonopatinDTO(monopatin);
        }
        return null;
    }

    @Transactional
    public MonopatinDTO crearMonopatin(MonopatinDTO monopatinDTO) {
        Monopatin monopatin = new Monopatin();
        monopatin.setLatitud(monopatinDTO.getLatitud());
        monopatin.setLongitud(monopatinDTO.getLongitud());

        try {
            EstadoMonopatin estado = EstadoMonopatin.valueOf(monopatinDTO.getEstado().toUpperCase());
            monopatin.setEstado(estado);
        } catch (Exception e) {
            throw new IllegalArgumentException("El estado '" + monopatinDTO.getEstado() + "' no es válido.");
        }

        Monopatin guardado = monopatinRepository.save(monopatin);
        return new MonopatinDTO(guardado);
    }



    @Transactional
    public MonopatinDTO update(Long id, MonopatinDTO monopatinDTO) {
        Monopatin monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Monopatin no encontrado con id: " + id));

        monopatin.setLatitud(monopatinDTO.getLatitud());
        monopatin.setLongitud(monopatinDTO.getLongitud());
        try {
            EstadoMonopatin estado = EstadoMonopatin.valueOf(monopatinDTO.getEstado().toUpperCase());
            monopatin.setEstado(estado);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("El estado '" + monopatinDTO.getEstado() + "' no es válido.");
        }
        monopatin.setKmRecorridos(monopatinDTO.getKmRecorridos());
        monopatin.setTiempoDeUsoTotal(monopatinDTO.getTiempoDeUsoTotal());
        monopatin.setIdParada(monopatinDTO.getIdParada());

        Monopatin actualizado = monopatinRepository.save(monopatin);
        return new MonopatinDTO(actualizado);
    }

    @Transactional
    public void delete(Long id) {
        if (!monopatinRepository.existsById(id)) {
            throw new EntityNotFoundException("Monopatin no encontrado con id: " + id);
        }
        monopatinRepository.deleteById(id);
    }
}
