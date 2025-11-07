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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MonopatinService {

    @Autowired
    private ParadaFeignClient paradaFeignClient;

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

//    @Transactional
//    public Monopatin save(Monopatin monopatin){
//        System.out.println("entro al service");
//        String estado = monopatin.getEstado().toString();
//        // Si el estado no existe en el enum,tiro la exception
//        if(Estado.perteneceAlEnum(estado)==null){
//            System.out.println("no pertenece al enum");
//            throw new InvalidEstadoException(estado);
//        }
//
//        // Verifico que la parada exista
//        Parada parada = paradaFeignClient.findById(monopatin.getIdParada());
//        if(parada==null){
//            System.out.println("parada no existe");
//            throw new NotFoundException("Parada",monopatin.getIdParada().toString());
//        }
//        Monopatin monoNuevo = repository.save(monopatin);
////        return new MonopatinDTO(repository.save(monopatin));
//        System.out.println(monoNuevo);
////        return new MonopatinDTO(monoNuevo);
//        return monoNuevo;
//    }

    @Transactional
    public MonopatinDTO save (Monopatin monopatin) {
        // Verifico el estado pasado
        String estadoPasado = monopatin.getEstado().toString();
        if(Estado.perteneceAlEnum(estadoPasado)==null){
            throw new InvalidEstadoException(estadoPasado);
        }

        // Verifico que la parada exista
        Parada parada = paradaFeignClient.findById(monopatin.getIdParada());
        if (parada == null) {
            throw new NotFoundException("Parada",monopatin.getIdParada().toString());
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


}
