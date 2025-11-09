package grupo4.mscvadmin.feignClients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="mscv-tarifas", url="http://localhost:8086/api/tarifas")
public interface TarifasFeignClient {

}
