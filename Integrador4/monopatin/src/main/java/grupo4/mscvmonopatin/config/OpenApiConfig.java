package grupo4.mscvmonopatin.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI monopatinOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Microservicio Monopatín")
                        .description("API REST para gestión de monopatines (MongoDB)")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Grupo 4")
                                .email("grupo4@example.com")));
    }
}

