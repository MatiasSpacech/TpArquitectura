package grupo4.viajes.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "msvc-cuenta", url = "${cuenta.service.url}")
public interface CuentaClient {

    Cu
}
