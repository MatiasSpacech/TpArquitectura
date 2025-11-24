package org.example.ia.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI iaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Microservicio IA")
                        .description("API REST para procesamiento de consultas con IA (Groq) - Acceso a múltiples bases de datos")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Grupo 4")
                                .email("grupo4@example.com")));
    }
}
