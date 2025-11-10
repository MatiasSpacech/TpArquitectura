package grupo4.mscvmonopatin.services;

import grupo4.mscvmonopatin.dtos.MonopatinDTO;
import grupo4.mscvmonopatin.dtos.MonopatinPatchDTO;
import grupo4.mscvmonopatin.feignClients.ParadaFeignClient;
import grupo4.mscvmonopatin.feignModel.Parada;
import grupo4.mscvmonopatin.model.Estado;
import grupo4.mscvmonopatin.model.Monopatin;
import grupo4.mscvmonopatin.repository.MonopatinRepository;
import grupo4.mscvmonopatin.services.exceptions.InvalidEstadoException;
import grupo4.mscvmonopatin.services.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MonopatinService {
    private final ParadaFeignClient paradaFeignClient;

    private final MonopatinRepository repository;

    @Transactional(readOnly = true)
    public List<MonopatinDTO> findAll() {
        List<Monopatin> monopatines = repository.findAll();
        return monopatines.stream().map(MonopatinDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public MonopatinDTO findById(String id){
        Monopatin monopatin = repository.findById(id)
                .orElseThrow( () -> new NotFoundException("Monopatin",id));
        return new MonopatinDTO(monopatin);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getMonopatinConParada(String id) {
        Monopatin monopatin = repository.findById(id)
                .orElseThrow( () -> new NotFoundException("Monopatin",id) );

        Map<String, Object> map = new HashMap<>();

        map.put("monopatin", new MonopatinDTO(monopatin));

        if(monopatin.getIdParada()==null)
            map.put("parada", "Este monopatin no tiene parada asignada");
        else {
            map.put("parada", paradaFeignClient.findById(monopatin.getIdParada()));
        }

        return map;
    }

    @Transactional
    public MonopatinDTO save (Monopatin monopatin) {
        // Verifico el estado pasado
        String estadoPasado = monopatin.getEstado().toString().toUpperCase();
        if(Estado.perteneceAlEnum(estadoPasado)==null){
            throw new InvalidEstadoException(estadoPasado);
        }

        // Verifico que la parada exista
        Parada parada = paradaFeignClient.findById(monopatin.getIdParada());
        if (parada == null) {
            throw new NotFoundException("Parada",monopatin.getIdParada().toString());
        }

        Monopatin monopatinNuevo = repository.save(monopatin);
        return new MonopatinDTO(monopatinNuevo);
    }

    @Transactional
    public void delete(String id){
        Monopatin monopatin = repository.findById(id)
                .orElseThrow( () -> new NotFoundException("Monopatin",id));

        repository.delete(monopatin);
    }

    @Transactional
    public MonopatinDTO patch(String id, MonopatinPatchDTO monopatinPatchDTO) {
        Monopatin monopatin = repository.findById(id)
                .orElseThrow( () -> new NotFoundException("Monopatin",id));

        if(monopatinPatchDTO.estado()!=null)
            monopatin.setEstado(monopatinPatchDTO.estado());
        if(monopatinPatchDTO.latitud()!=null)
            monopatin.setLatitud(monopatinPatchDTO.latitud());
        if (monopatinPatchDTO.longitud()!=null)
            monopatin.setLongitud(monopatinPatchDTO.longitud());
        if (monopatinPatchDTO.kmRecorridos()!=null)
            monopatin.setKmRecorridos(monopatinPatchDTO.kmRecorridos());
        if (monopatinPatchDTO.tiempoUsado()!=null)
            monopatin.setTiempoUsado(monopatinPatchDTO.tiempoUsado());

        if(monopatinPatchDTO.idParada()!=null) {
            Parada paradaNueva = paradaFeignClient.findById(monopatinPatchDTO.idParada());
            if(paradaNueva==null)
                throw new NotFoundException("Parada",monopatinPatchDTO.idParada().toString());

            monopatin.setIdParada(monopatinPatchDTO.idParada());
        }


        return new MonopatinDTO(repository.save(monopatin));
    }

    @Transactional
    public MonopatinDTO setEstadoMonopatin(String id, String estado) {
        Monopatin monopatin = this.repository.findById(id)
                .orElseThrow( () -> new NotFoundException("Monopatin",id));

        // Si el estado no existe en el enum,tiro la exception
        if(Estado.perteneceAlEnum(estado)==null){
           throw new InvalidEstadoException(estado);
        }
        // Seteo el estado del monopatin
        else{
            monopatin.setEstado(Estado.perteneceAlEnum(estado));
            return new MonopatinDTO(repository.save(monopatin));
        }
    }

    @Transactional(readOnly = true)
    public List<MonopatinDTO> findMonopatinesByIdParada(Long idParada){
        return repository.findByIdParada(idParada).stream().map(MonopatinDTO::new).toList();
    }

    @Transactional(readOnly=true)
    public List<MonopatinDTO> findMonopatinesPorEstadoByIdParada(Long idParada,String estado) {
        estado = estado.toUpperCase();

        if(Estado.perteneceAlEnum(estado)==null){
            throw new InvalidEstadoException(estado);
        }

        return repository.findByEstadoStringAndIdParada(estado,idParada)
                .stream().map(MonopatinDTO::new)
                .toList();
    }


}
