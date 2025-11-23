package org.example.ia.client;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GroqClient {
    private static final Logger log = (Logger) LoggerFactory.getLogger(GroqClient.class);
    private final RestTemplate rest;
    private final String baseUrl;
    private final String apiKey;
    private final String model;

    public GroqClient(
            @Value("${groq.base-url:https://api.groq.com/openai}") String baseUrl,
            @Value("${groq.api-key:#{null}}") String apiKey,
            @Value("${groq.model:llama-3.1-8b-instant}") String model
    ) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.model = model;

        // Solo advertir si no está configurado, pero permitir arrancar
        if (apiKey == null || apiKey.isBlank()) {
            log.warn("⚠️ GROQ API key NO configurada. El servicio arrancará pero las llamadas a la IA fallarán.");
            log.warn("   Para configurar: export GROQ_API_KEY=tu_clave o agrega -Dgroq.api-key=tu_clave");
        } else if (!apiKey.startsWith("gsk_")) {
            log.error("❌ GROQ API key con formato inválido. Debe comenzar con gsk_.");
        } else {
            log.info("✅ Groq listo. baseUrl={}, model={}, apiKey=gsk_********", baseUrl, model);
        }

        var factory = new org.springframework.http.client.SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10_000);
        factory.setReadTimeout(60_000);

        this.rest = new RestTemplate(factory);
        this.rest.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                // Deja que el 4xx/5xx suba con el cuerpo para loguearlo
                if (response.getStatusCode().isError()) {
                    String body = new String(response.getBody().readAllBytes());
                    throw new HttpClientErrorException(
                            response.getStatusCode(),
                            "Groq error: " + body
                    );
                }
            }
        });
    }

    public String preguntar (String prompt) {
        // Validar que tengamos API key antes de hacer la llamada
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("No se puede hacer consultas a Groq: API key no configurada. " +
                    "Configure la variable de entorno GROQ_API_KEY");
        }

        String url = baseUrl + "/v1/chat/completions";

        Map<String, Object> body = new HashMap<>();
        body.put("model", model);
        body.put("temperature", 0.3);
        body.put("messages", List.of(
                Map.of("role", "user", "content", prompt)
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey); // <-- CLAVE AQUÍ

        HttpEntity<Map<String, Object>> req = new HttpEntity<>(body, headers);

        var resp = rest.exchange(url, HttpMethod.POST, req, Map.class);
        // Extrae el texto: choices[0].message.content
        List<?> choices = (List<?>) ((Map<?, ?>) resp.getBody()).get("choices");
        Map<?, ?> choice0 = (Map<?, ?>) choices.get(0);
        Map<?, ?> message = (Map<?, ?>) choice0.get("message");
        return (String) message.get("content");
    }


}