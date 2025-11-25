package org.example.ia.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.ia.service.IaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


    @RestController
    @RequestMapping("/api/ia")
    @Tag(name = "IA", description = "API para procesamiento de consultas con IA (Groq) sobre múltiples bases de datos")
    public class IaController {    // IaController exponene el endpoint REST que recibe prompts y delega a IaService.


        /**🔑 que va a hacer mi app en conjunto
         *  IaController recibe prompt →
         *  IaService añade esquema + manda a Ollama →
         *  OllamaClient se conecta a la API →
         *  Respuesta: IA devuelve consulta SQL →
         *  IaService la ejecuta →
         *  Respuesta JSON con resultados.
         */

        @Autowired
        private IaService iaService;

        @PostMapping(value = "/prompt", produces = "application/json") // 👉 Define endpoint POST /api/ia/prompt que recibe un prompt como cuerpo JSON.
        // http://localhost:8080/api/ia/prompt
        // Ejemplo de uso con curl:
        // curl -X POST http://localhost:8080/api/ia/prompt -H "Content-Type: application/json" -d "¿Cuáles son los nombres y correos electrónicos
        @Operation(summary = "Procesar prompt con IA",
                   description = "Envía un prompt en lenguaje natural que será procesado por IA para generar y ejecutar consultas SQL en las bases de datos del sistema")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prompt procesado exitosamente y consulta ejecutada"),
            @ApiResponse(responseCode = "500", description = "Error al procesar el prompt o ejecutar la consulta")
        })
        public ResponseEntity<?> procesarPrompt(
                @Parameter(description = "Consulta en lenguaje natural (ej: '¿Cuántos monopatines hay disponibles?')",
                          required = true,
                          schema = @Schema(type = "string", example = "¿Cuántos usuarios hay registrados?"))
                @RequestBody String prompt) {
            try {
                return iaService.procesarPrompt(prompt);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el prompt: " + e.getMessage());
            }
        }
    }
