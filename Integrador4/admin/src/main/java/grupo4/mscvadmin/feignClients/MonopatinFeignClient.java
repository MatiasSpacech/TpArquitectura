package grupo4.mscvadmin.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="mscv-monopatin", url="http://localhost:8084/api/monopatines")
public interface MonopatinFeignClient {

    //@GetMapping

}
