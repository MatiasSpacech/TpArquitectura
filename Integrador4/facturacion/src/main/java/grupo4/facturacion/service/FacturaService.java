package grupo4.facturacion.service;

import grupo4.facturacion.client.TarifaFeignClient;
import grupo4.facturacion.entity.Factura;

import grupo4.facturacion.repository.FacturaRepository;
import grupo4.mscvadmin.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private TarifaFeignClient tarifaFeignClient;

    @Transactional(readOnly = true)
    public List<Factura> findAll(){
        return facturaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Factura findById(Long id){
        return facturaRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Factura> findByUsuarioId(Long id){
        return facturaRepository.findByUsuarioId(id);
    }


    @Transactional
    public Factura save(Factura factura){
        return facturaRepository.save(factura);
    }

    @Transactional
    public void delete(Long id){
        facturaRepository.deleteById(id);
    }

    @Transactional
    public List<Factura> findByUsuario(String usuario) {
        return facturaRepository.findByUsuario(usuario);
    }

    @Transactional
    public List<Factura> findByEstado(String estado){
        return facturaRepository.findByEstado(estado);
    }

    @Transactional
    public List<Factura> findByFecha(Date fecha){
        return facturaRepository.findByFecha(fecha);
    }

    @Transactional
    public List<Factura> findByFechaBetween(Date fecha1, Date fecha2){
        return facturaRepository.findByFechaBetween(fecha1,fecha2);
    }

    // Aquí puedes usar tarifaFeignClient cuando necesites consultar tarifas
    // Ejemplo: TarifaDTO tarifa = tarifaFeignClient.findTarifaById(id).getBody();
}
