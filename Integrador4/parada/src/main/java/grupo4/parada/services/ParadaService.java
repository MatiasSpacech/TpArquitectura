package grupo4.parada.services;

import grupo4.parada.dtos.ParadaDTO;
import grupo4.parada.dtos.ParadaPatchDTO;
import grupo4.parada.feignClients.MonopatinFeignClient;
import grupo4.parada.feignModel.Monopatin;
import grupo4.parada.model.Parada;
import grupo4.parada.repository.ParadaRepository;
import grupo4.parada.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParadaService {
    private static final Double RADIO_MAX_BUSQUEDA = 100.0;

    @Autowired
    private ParadaRepository repository;

    @Autowired
    private MonopatinFeignClient monopatinFeignClient;

    public List<ParadaDTO> findAll(){
        return repository.findAll().stream().map(ParadaDTO::new).toList();
    }

    public ParadaDTO findById(Long id){
        return repository.findById(id).map(ParadaDTO::new)
                .orElseThrow( () -> new NotFoundException("Parada",id));
    }

    public List<ParadaDTO> findParadasCercanas(Double latitud, Double longitud){
        return repository.findParadasCercanas(latitud, longitud, RADIO_MAX_BUSQUEDA).stream()
                .map(ParadaDTO::new).toList();
    }

    public Map<String, Object> findParadaConMonopatines(Long id){
        Parada parada = repository.findById(id)
                .orElseThrow( () -> new NotFoundException("Parada",id));

        Map<String,Object> retorno = new HashMap<>();

        retorno.put("parada", parada);

        List<Monopatin> monopatines = monopatinFeignClient.findMonopatinesByIdParada(id);
        if(monopatines.isEmpty()){
            retorno.put("monopatines", "No hay monopatines en esta parada");
        }
        else{
            retorno.put("monopatines", monopatines);
        }

        return retorno;
    }

    public Map<String, Object> findParadaConMonopatinesLibres(Long id) {
        Parada parada = repository.findById(id)
                .orElseThrow( () -> new NotFoundException("Parada",id));

        Map<String,Object> retorno = new HashMap<>();
        retorno.put("parada", parada);

        List<Monopatin> monopatinesLibres = monopatinFeignClient.findMonopatinesLibresByIdParada(id);
        if(monopatinesLibres.isEmpty()){
            retorno.put("monopatinesLibres", "No hay monopatines libres en esta parada");
        }
        else{
            retorno.put("monopatinesLibres", monopatinesLibres);
        }

        return retorno;
    }

    public ParadaDTO save(Parada parada){
        return new ParadaDTO(repository.save(parada));
    }

    public void delete(Long id){
        Parada parada = repository.findById(id)
                .orElseThrow( () -> new NotFoundException("Parada",id));

        repository.delete(parada);
    }

    public ParadaDTO patch(Long id, ParadaPatchDTO paradaEditada){
        Parada parada = repository.findById(id)
                .orElseThrow( () -> new NotFoundException("Parada",id));

        if(paradaEditada.nombre()!=null)
            parada.setNombre(paradaEditada.nombre());
        if(paradaEditada.ciudad()!=null)
            parada.setCiudad(paradaEditada.ciudad());
        if(paradaEditada.direccion()!=null)
            parada.setDireccion(paradaEditada.direccion());
        if(paradaEditada.latitud()!=null)
            parada.setLatitud(paradaEditada.latitud());
        if(paradaEditada.longitud()!=null)
            parada.setLongitud(paradaEditada.longitud());

        return new ParadaDTO(repository.save(parada));
    }
}
