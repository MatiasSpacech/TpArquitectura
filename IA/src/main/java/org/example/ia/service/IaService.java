package org.example.ia.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ia.client.GroqClient;
import org.example.ia.dto.RespuestaApi;
import org.example.ia.feignClients.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IaService {

    @Autowired private GroqClient groqChatClient;

    // Clientes Feign
    @Autowired private UsuarioFeingCliente usuarioClient;
    @Autowired private CuentaFeignClient cuentaClient;
    @Autowired private MonopatinFeignClient monopatinClient;
    @Autowired private ParadaFeignClient paradaClient;
    @Autowired private AdminFeignClient adminClient;
    @Autowired private TarifaFeignClient tarifaClient;
    @Autowired private FacturaFeignClient facturaClient;

    private final ObjectMapper mapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(IaService.class);
    private static final Pattern JSON_PATTERN = Pattern.compile("\\{.*\\}", Pattern.DOTALL);

    // --- DEFINICIÓN DE HERRAMIENTAS ---
    private static final String API_TOOLS_DEF = """
            ERES UN ORQUESTADOR SUPREMO DE UN SISTEMA DE MONOPATINES. Tu trabajo es traducir lenguaje natural a comandos JSON.
            
            TIENES ACCESO A LOS SIGUIENTES MÓDULOS:
            
            1. USUARIOS:
               - USER_LISTAR, USER_BUSCAR_ID, USER_CREAR, USER_BORRAR, USER_ASIGNAR_CUENTA
            
            2. CUENTAS (Saldo/Dinero):
               - CUENTA_LISTAR, CUENTA_BUSCAR_ID, CUENTA_CREAR
               - CUENTA_CAMBIAR_ESTADO (requiere id)
               - CUENTA_RESTAR_SALDO (requiere id, monto)
            
            3. MONOPATINES (Vehículos):
               - MONO_LISTAR, MONO_BUSCAR_ID, MONO_CREAR, MONO_BORRAR
               - MONO_REPORTE_MANTENIMIENTO (requiere kmMaximo)
               - MONO_CAMBIAR_ESTADO (requiere id, estado)
               - MONO_POR_PARADA (requiere idParada)
            
            4. PARADAS (Ubicaciones):
               - PARADA_LISTAR, PARADA_CERCANAS (requiere latitud, longitud)
               - PARADA_VER_MONOPATINES (requiere id)
            
            5. ADMINISTRACIÓN (Reportes globales):
               - ADMIN_REPORTE_USO_KM
               - ADMIN_ANULAR_USUARIO (requiere id usuario)
               - ADMIN_MONOPATINES_VIAJES (requiere anio, cantidadViajes)
               - ADMIN_FACTURACION_TOTAL (requiere anio, mesInicio, mesFin)
               - ADMIN_USUARIOS_TOP (requiere periodo, tipoUsuario)
               - ADMIN_AJUSTE_PRECIOS (requiere nuevosPrecios, fechaInicio formato YYYY-MM-DD)
            
            6. FACTURACIÓN Y TARIFAS:
               - FACTURA_TOTAL_RANGO (requiere anio, mesDesde, mesHasta)
               - TARIFA_LISTAR
               - TARIFA_CREAR (requiere body)

            FORMATO DE RESPUESTA OBLIGATORIO (JSON):
            {
                "accion": "NOMBRE_ACCION",
                "params": {
                    "id": 1,
                    "monto": 50.5,
                    "latitud": -34.0,
                    "anio": 2024,
                    "body": { ... }
                }
            }
            
            SI NO ENTIENDES, RESPONDE: { "accion": "UNKNOWN" }
            """;

    public ResponseEntity<?> procesarPrompt(String promptUsuario, String token) {
        try {
            // =================================================================================
            // 1. OBTENER INFORMACIÓN DEL USUARIO (Validación de Token)
            // =================================================================================
            Map<String, Object> usuarioInfo;
            try {
                // Llamamos al endpoint que creamos en ms-usuario pasando el token
                usuarioInfo = usuarioClient.getUsuarioActual(token);
            } catch (Exception e) {
                log.error("Fallo al obtener usuario del token", e);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado.");
            }

            // =================================================================================
            // 2. VALIDACIÓN PREMIUM (Regla de Negocio)
            // =================================================================================
            boolean esPremium = false;
            if (usuarioInfo.get("cuentas") != null) {
                List<Map<String, Object>> cuentas = (List<Map<String, Object>>) usuarioInfo.get("cuentas");
                for (Map<String, Object> c : cuentas) {
                    // Verificamos si tiene al menos una cuenta PREMIUM y ACTIVA
                    String tipo = (String) c.get("tipoCuenta");
                    String estado = (String) c.get("estado");
                    if ("PREMIUM".equals(tipo) && "ACTIVA".equals(estado)) {
                        esPremium = true;
                        break;
                    }
                }
            }

            if (!esPremium) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("ACCESO DENEGADO: Este servicio es exclusivo para usuarios con cuenta PREMIUM activa.");
            }

            // =================================================================================
            // 3. INYECCIÓN DE CONTEXTO (Para que la IA sepa quién es "yo")
            // =================================================================================
            Long userId = ((Number) usuarioInfo.get("id")).longValue();

            String promptContexto = """
                    CONTEXTO DE SEGURIDAD:
                    - El usuario autenticado es ID: %d
                    - Si el usuario dice "mi cuenta", "mis datos", usa el ID %d en los parámetros JSON.
                    
                    """.formatted(userId, userId);

            // Combinamos todo: Definición de Herramientas + Contexto ID + Pregunta Usuario
            String promptFinal = API_TOOLS_DEF + "\n" + promptContexto + "\nSolicitud del usuario: \"" + promptUsuario + "\"";

            log.info("Prompt enviado (User ID {}): {}", userId, promptFinal);

            // =================================================================================
            // 4. LLAMADA A GROQ Y EJECUCIÓN
            // =================================================================================
            String respuestaIa = groqChatClient.preguntar(promptFinal);
            log.info("Respuesta IA: {}", respuestaIa);

            String jsonLimpio = extraerJson(respuestaIa);
            if (jsonLimpio == null) return ResponseEntity.badRequest().body("Error: La IA no generó JSON válido.");

            Map<String, Object> commandMap = mapper.readValue(jsonLimpio, Map.class);
            return ejecutarComandoFeign(commandMap);

        } catch (Exception e) {
            log.error("Error general en IaService", e);
            return ResponseEntity.internalServerError().body("Error interno: " + e.getMessage());
        }
    }

    // --- DISPATCHER GIGANTE (Sin cambios) ---
    private ResponseEntity<?> ejecutarComandoFeign(Map<String, Object> command) {
        String accion = (String) command.getOrDefault("accion", "UNKNOWN");
        Map<String, Object> p = (Map<String, Object>) command.getOrDefault("params", Collections.emptyMap());

        log.info("Ejecutando acción: {}", accion);

        try {
            Object resultado = null;

            switch (accion) {
                // --- USUARIOS ---
                case "USER_LISTAR": resultado = usuarioClient.findAll(); break;
                case "USER_BUSCAR_ID": resultado = usuarioClient.findById(getLong(p,"id")); break;
                case "USER_CREAR": resultado = usuarioClient.save((Map) p.get("body")); break;
                case "USER_ASIGNAR_CUENTA": resultado = usuarioClient.asignarCuenta(getLong(p,"idUsuario"), getLong(p,"idCuenta")); break;

                // --- CUENTAS ---
                case "CUENTA_LISTAR": resultado = cuentaClient.findAll(); break;
                case "CUENTA_BUSCAR_ID": resultado = cuentaClient.findById(getLong(p,"id")); break;
                case "CUENTA_CREAR": resultado = cuentaClient.save((Map) p.get("body")); break;
                case "CUENTA_CAMBIAR_ESTADO": resultado = cuentaClient.cambiarEstadoCuenta(getLong(p,"id")); break;
                case "CUENTA_RESTAR_SALDO":
                    BigDecimal monto = new BigDecimal(p.get("monto").toString());
                    resultado = cuentaClient.restarSaldoCuenta(getLong(p,"id"), monto);
                    break;

                // --- MONOPATINES ---
                case "MONO_LISTAR": resultado = monopatinClient.findAll(); break;
                case "MONO_BUSCAR_ID": resultado = monopatinClient.findById(String.valueOf(p.get("id"))); break;
                case "MONO_CREAR": resultado = monopatinClient.save((Map) p.get("body")); break;
                case "MONO_REPORTE_MANTENIMIENTO": resultado = monopatinClient.getReportesMantenimiento((Integer) p.get("kmMaximo")); break;
                case "MONO_CAMBIAR_ESTADO": resultado = monopatinClient.setEstado(String.valueOf(p.get("id")), (String) p.get("estado")); break;
                case "MONO_POR_PARADA": resultado = monopatinClient.findMonopatinesByIdParada(getLong(p,"idParada"), null); break;

                // --- PARADAS ---
                case "PARADA_LISTAR": resultado = paradaClient.findAll(); break;
                case "PARADA_CERCANAS":
                    resultado = paradaClient.findParadasCercanas((Double) p.get("latitud"), (Double) p.get("longitud"));
                    break;
                case "PARADA_VER_MONOPATINES": resultado = paradaClient.findMonopatinesByParada(getLong(p,"id")); break;

                // --- ADMIN ---
                case "ADMIN_REPORTE_USO_KM": resultado = adminClient.generarReporteMonopatines(); break;
                case "ADMIN_ANULAR_USUARIO": resultado = adminClient.anularUsuario(String.valueOf(p.get("id"))); break;
                case "ADMIN_MONOPATINES_VIAJES":
                    resultado = adminClient.consultarMonopatinesPorViajes((Integer) p.get("cantidadViajes"), (Integer) p.get("anio"));
                    break;
                case "ADMIN_FACTURACION_TOTAL":
                    resultado = adminClient.consultarTotalFacturado((Integer) p.get("anio"), (Integer) p.get("mesInicio"), (Integer) p.get("mesFin"));
                    break;
                case "ADMIN_USUARIOS_TOP":
                    resultado = adminClient.verUsuariosTop((String) p.get("periodo"), (String) p.get("tipoUsuario"));
                    break;
                case "ADMIN_AJUSTE_PRECIOS":
                    Double precio = Double.valueOf(p.get("nuevosPrecios").toString());
                    resultado = adminClient.hacerAjustePrecios(precio, (String) p.get("fechaInicio"));
                    break;

                // --- FACTURACION Y TARIFAS ---
                case "FACTURA_TOTAL_RANGO":
                    resultado = facturaClient.getTotalFacturado((Integer) p.get("anio"), (Integer) p.get("mesDesde"), (Integer) p.get("mesHasta"));
                    break;
                case "TARIFA_LISTAR": resultado = tarifaClient.findAllTarifas(); break;
                case "TARIFA_CREAR": resultado = tarifaClient.crearTarifa((Map) p.get("body")); break;

                default: return ResponseEntity.badRequest().body("Acción desconocida o no implementada: " + accion);
            }

            return ResponseEntity.ok(new RespuestaApi<>(true, "Ejecución exitosa: " + accion, resultado));

        } catch (Exception e) {
            log.error("Error ejecutando Feign para accion " + accion, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RespuestaApi<>(false, "Fallo al llamar al microservicio: " + e.getMessage(), null));
        }
    }

    private Long getLong(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val instanceof Number) return ((Number) val).longValue();
        if (val instanceof String) return Long.parseLong((String) val);
        return null;
    }

    private String extraerJson(String texto) {
        Matcher matcher = JSON_PATTERN.matcher(texto);
        return matcher.find() ? matcher.group() : null;
    }
}