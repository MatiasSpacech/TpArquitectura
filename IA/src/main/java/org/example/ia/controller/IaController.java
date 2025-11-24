package org.example.ia.controller;

import org.example.ia.service.IaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ia")
public class IaController {

    /** 🔑 FLUJO DE LA APLICACIÓN:
     *  1. IaController recibe el prompt y el Token JWT.
     *  2. IaService valida el token contra ms-usuario y verifica si es PREMIUM.
     *  3. IaService construye un prompt con herramientas y lo envía a Groq.
     *  4. Groq responde con un comando JSON (Function Calling simulado).
     *  5. IaService ejecuta el comando usando Feign Clients hacia los otros microservicios.
     *  6. Se devuelve el resultado final.
     */

    @Autowired
    private IaService iaService;

    @PostMapping(value = "/prompt", produces = "application/json")
    public ResponseEntity<?> procesarPrompt(
            @RequestHeader("Authorization") String token,
            @RequestBody String prompt) {
        try {
            // Delegamos al servicio pasando el token para validación de seguridad
            return iaService.procesarPrompt(prompt, token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar el prompt: " + e.getMessage());
        }
    }
}