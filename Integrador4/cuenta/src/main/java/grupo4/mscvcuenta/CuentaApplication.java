package grupo4.mscvcuenta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients(basePackages = "grupo4.mscvcuenta.feignClients")
public class CuentaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CuentaApplication.class, args);
    }

}
