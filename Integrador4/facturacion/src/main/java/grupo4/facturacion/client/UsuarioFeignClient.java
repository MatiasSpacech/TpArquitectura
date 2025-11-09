package grupo4.facturacion.client;

import grupo4.facturacion.dto.UsuarioPremiumDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "usuarios", url = "http://localhost:8081")
public interface UsuarioFeignClient {

    @GetMapping("/api/cuenta-usuario/{id}/premium")
    ResponseEntity<UsuarioPremiumDTO> getUsuarioPremium(@PathVariable Long id);

    @PutMapping("/api/cuenta-usuario/{id}/consumir-km")
    ResponseEntity<Void> actualizarKmConsumidos(@PathVariable Long id, @RequestParam Double kmConsumidos);
}

