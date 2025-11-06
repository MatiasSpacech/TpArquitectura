package grupo4.mscvmonopatin.services;

import grupo4.mscvmonopatin.dtos.MonopatinDTO;
import grupo4.mscvmonopatin.dtos.MonopatinPatchDTO;
import grupo4.mscvmonopatin.model.Estado;
import grupo4.mscvmonopatin.model.Monopatin;
import grupo4.mscvmonopatin.repository.MonopatinRepository;
import grupo4.mscvmonopatin.services.exceptions.InvalidEstadoException;
import grupo4.mscvmonopatin.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MonopatinService {

    @Autowired
    private MonopatinRepository repository;

    @Transactional(readOnly = true)
    public List<MonopatinDTO> findAll() {
        List<Monopatin> monopatines = repository.findAll();
        return monopatines.stream().map(MonopatinDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public MonopatinDTO findById(String id){
        return repository.findById(id).map(MonopatinDTO::new)
                .orElseThrow( () -> new NotFoundException("Monopatin",id));
    }

    @Transactional
    public MonopatinDTO save(Monopatin monopatin){
        String estado = monopatin.getEstado().toString();
        // Si el estado no existe en el enum,tiro la exception
        if(Estado.perteneceAlEnum(estado)==null){
            throw new InvalidEstadoException(estado);
        }

        return new MonopatinDTO(repository.save(monopatin));
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


}
