package grupo4.viajes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI viajesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Microservicio Viajes")
                        .description("API REST para gestión de viajes y reportes")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Grupo 4")
                                .email("grupo4@example.com")));
    }
}

