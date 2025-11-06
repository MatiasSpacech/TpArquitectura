package grupo4.tarifas.service;

import grupo4.tarifas.dto.TarifaDTO;
import grupo4.tarifas.entity.Tarifa;
import grupo4.tarifas.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class TarifaService {

    @Autowired
    private TarifaRepository tarifaRepository;

    @Transactional
    public TarifaDTO actualizarTarifaDesdeFecha(Date fecha, double montoExtra) {
        Tarifa tarifa = tarifaRepository.findTarifaVigenteDesde(fecha);
        if (tarifa == null) {
            throw new RuntimeException("No se encontró tarifa vigente para la fecha");
        }
        double nuevoMonto = tarifa.getMonto() * montoExtra;
        return new TarifaDTO(nuevoMonto, montoExtra, tarifa.getFecha());
    }

    @Transactional
    public TarifaDTO actualizarTarifa(Tarifa tarifa) {
        Tarifa tarifaGuardada = tarifaRepository.save(tarifa);
        return new TarifaDTO(tarifaGuardada.getMonto(), tarifaGuardada.getMontoExtra(),
                tarifaGuardada.getFecha());
    }

    @Transactional
    public void eliminarTarifa(Long id) {
        tarifaRepository.deleteById(id);
    }

    @Transactional
    public TarifaDTO crearTarifa(Tarifa tarifa) {
        Tarifa tarifaGuardada = tarifaRepository.save(tarifa);
        return new TarifaDTO(tarifaGuardada.getMonto(), tarifaGuardada.getMontoExtra(),
                tarifaGuardada.getFecha());
    }

    @Transactional
    public List<Tarifa> findAllTarifas() {
        return tarifaRepository.findAll();
    }

    @Transactional
    public TarifaDTO findTarifaById(Long id) {
        Tarifa tarifa = tarifaRepository.findById(id).orElse(null);
        if (tarifa == null) {
            return null;
        }
        return new TarifaDTO(tarifa.getMonto(), tarifa.getMontoExtra(), tarifa.getFecha());
    }

    @Transactional
    public TarifaDTO findTarifaByFecha(Date fecha) {
        Tarifa tarifa = tarifaRepository.findByFecha(fecha);
        if (tarifa == null) {
            return null;
        }
        return new TarifaDTO(tarifa.getMonto(), tarifa.getMontoExtra(), tarifa.getFecha());
    }

    @Transactional
    public List<TarifaDTO> findTarifaByMonto(Double monto) {
        List<Tarifa> tarifas = Collections.singletonList(tarifaRepository.findByMonto(monto));
        return tarifas.stream()
                .map(t -> new TarifaDTO(t.getMonto(), t.getMontoExtra(), t.getFecha()))
                .toList();
    }
}
