# Documentación API con Swagger/OpenAPI

Este proyecto cuenta con documentación interactiva Swagger/OpenAPI en todos los microservicios.

## 📚 Acceso a la Documentación Swagger

Cada microservicio tiene su propia documentación Swagger UI accesible cuando el servicio está en ejecución.

### URLs de Acceso por Módulo

| Microservicio | Puerto | Swagger UI | API Docs JSON |
|--------------|--------|------------|---------------|
| **Gateway** | 8080 | http://localhost:8080/swagger-ui.html | http://localhost:8080/v3/api-docs |
| **Admin** | 8081 | http://localhost:8081/docs-admin | http://localhost:8081/docs-admin/api-docs |
| **Usuario-Cuenta** | 8082 | http://localhost:8082/docs-usuario | http://localhost:8082/docs-usuario/api-docs |
| **Facturación** | 8083 | http://localhost:8083/docs-facturacion | http://localhost:8083/docs-facturacion/api-docs |
| **Monopatín** | 8084 | http://localhost:8084/docs-monopatin | http://localhost:8084/docs-monopatin/api-docs |
| **Paradas** | 8085 | http://localhost:8085/docs-parada | http://localhost:8085/docs-parada/api-docs |
| **Tarifas** | 8086 | http://localhost:8086/docs-tarifas | http://localhost:8086/docs-tarifas/api-docs |
| **Viajes** | 8087 | http://localhost:8087/docs-viajes | http://localhost:8087/docs-viajes/api-docs |
| **IA** | 8089 | http://localhost:8089/docs-ia | http://localhost:8089/docs-ia/api-docs |

## 🚀 Cómo Usar Swagger UI

1. **Iniciar el microservicio** que deseas documentar
2. **Abrir el navegador** y acceder a la URL correspondiente
3. **Explorar los endpoints** organizados por tags/categorías
4. **Probar las APIs** directamente desde la interfaz:
   - Click en el endpoint deseado
   - Click en "Try it out"
   - Completar los parámetros requeridos
   - Click en "Execute"
   - Ver la respuesta en tiempo real

## 🔐 Autenticación (Gateway)

El módulo Gateway incluye autenticación JWT. Para usar endpoints protegidos:

1. Obtener token desde `/api/token` con credenciales válidas
2. Click en el botón **"Authorize"** en la parte superior de Swagger UI
3. Ingresar: `Bearer <tu-token-jwt>`
4. Click en "Authorize"
5. Ahora puedes usar los endpoints protegidos

## 📋 Características de la Documentación

Cada endpoint incluye:
- ✅ **Descripción detallada** de funcionalidad
- ✅ **Parámetros** con tipos y descripciones
- ✅ **Códigos de respuesta** HTTP con explicaciones
- ✅ **Modelos de datos** (schemas)
- ✅ **Ejemplos** de request/response
- ✅ **Pruebas interactivas** en vivo

## 📦 Dependencia Utilizada

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.6.0</version>
</dependency>
```

## 🔧 Configuración

Cada módulo tiene una clase `OpenApiConfig` que configura la información de la API:
- Título del servicio
- Descripción
- Versión
- Información de contacto

## 📝 Exportar Documentación

Para obtener la especificación OpenAPI en formato JSON:
```
GET http://localhost:{puerto}/v3/api-docs
```

Para exportar en formato YAML:
```
GET http://localhost:{puerto}/v3/api-docs.yaml
```

## 🎯 Descripción de Módulos

### Gateway (8080)
- Autenticación JWT
- Enrutamiento a microservicios
- Seguridad con tokens Bearer

### Admin (8081)
- Reportes de monopatines
- Gestión de usuarios y cuentas
- Consultas administrativas

### Usuario-Cuenta (8082)
- CRUD de usuarios
- CRUD de cuentas
- Gestión de saldos

### Facturación (8083)
- Gestión de facturas
- Facturación de viajes
- Reportes financieros

### Tarifas (8084)
- Gestión de tarifas
- Ajustes de precios
- Histórico de tarifas

### Paradas (8085)
- CRUD de paradas
- Búsqueda de paradas cercanas
- Monopatines por parada

### Viajes (8087)
- Gestión de viajes
- Reportes de uso
- Estadísticas

### Monopatín (8087)
- CRUD de monopatines (MongoDB)
- Estados y ubicaciones
- Reportes de mantenimiento

### IA (8089)
- Procesamiento de consultas con IA
- Integración con Groq
- Consultas SQL generadas por IA

## 💡 Consejos

1. **Usar Swagger UI** para entender la estructura de datos antes de integrar
2. **Exportar la especificación** para generar clientes automáticamente
3. **Probar endpoints** directamente sin necesidad de Postman
4. **Revisar los modelos** en la sección "Schemas" al final de la página

---

**Nota**: Asegúrate de que el microservicio esté corriendo antes de intentar acceder a su documentación Swagger.
