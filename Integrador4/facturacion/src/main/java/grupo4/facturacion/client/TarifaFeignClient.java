package grupo4.facturacion.client;

import grupo4.facturacion.dto.TarifaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tarifas", url = "http://localhost:8086")
public interface TarifaFeignClient {

    @GetMapping("/api/tarifas/")
    ResponseEntity<TarifaDTO[]> findAllTarifas();

    @GetMapping("/api/tarifas/tarifa/id/{id}")
    ResponseEntity<TarifaDTO> findTarifaById(@PathVariable Long id);
}
