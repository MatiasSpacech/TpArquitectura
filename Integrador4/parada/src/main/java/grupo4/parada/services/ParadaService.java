package grupo4.parada.services;

import grupo4.parada.dtos.ParadaDTO;
import grupo4.parada.dtos.ParadaPatchDTO;
import grupo4.parada.feignClients.MonopatinFeignClient;
import grupo4.parada.model.Parada;
import grupo4.parada.repository.ParadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ParadaService {
    @Autowired
    private ParadaRepository repository;

    @Autowired
    private MonopatinFeignClient monopatinFeignClient;

    public List<ParadaDTO> findAll(){
        return repository.findAll().stream().map(ParadaDTO::new).toList();
    }

    public ParadaDTO findById(Long id){
        return repository.findById(id).map(ParadaDTO::new).orElse(null);
    }
//
//    public Map<String, Object> findParadaByMonopatines(Long id){
//
//    }

    public ParadaDTO save(Parada parada){
        return new ParadaDTO(repository.save(parada));
    }

//    public void delete(Long id){
//
//    }
//
//    public ParadaDTO patch(Long id, ParadaPatchDTO paradaEditada){
//
//    }
}
