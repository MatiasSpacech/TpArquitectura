package grupo4.facturacion.client;

import grupo4.facturacion.dto.TarifaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@FeignClient(name = "tarifas", url = "http://localhost:8086")
public interface TarifaFeignClient {

    @GetMapping("/tarifas/")
    ResponseEntity<List<TarifaDTO>> findAllTarifas();

    @GetMapping("/tarifas/tarifa/id/{id}")
    ResponseEntity<TarifaDTO> findTarifaById(@PathVariable Long id);

    @GetMapping("/tarifas/buscar/fecha/{fecha}")
    ResponseEntity<TarifaDTO> findTarifaByFecha(@PathVariable Date fecha);

    @GetMapping("/tarifas/tarifa/monto/{monto}")
    ResponseEntity<TarifaDTO> findTarifaByMonto(@PathVariable double monto);
}

