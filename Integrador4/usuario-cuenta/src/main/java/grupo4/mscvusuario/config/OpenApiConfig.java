package grupo4.mscvusuario.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI usuarioOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Microservicio Usuario y Cuenta")
                        .description("API REST para gestión de usuarios y cuentas")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Grupo 4")
                                .email("grupo4@example.com")));
    }
}

