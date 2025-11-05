# Guía: Cliente Feign entre Facturación y Tarifa

## ✅ IMPLEMENTACIÓN COMPLETA

### **Archivos creados:**

1. **TarifaFeignClient.java** - `facturacion/src/main/java/grupo4/facturacion/client/`
   - Interfaz que define los métodos para comunicarse con el microservicio de Tarifas
   - Configurado con `@FeignClient(name = "tarifas", url = "http://localhost:8086")`

2. **TarifaDTO.java** - `facturacion/src/main/java/grupo4/facturacion/dto/`
   - DTO para recibir datos del microservicio de Tarifas

### **Archivos modificados:**

3. **FacturaService.java**
   - Inyección del `TarifaFeignClient` con `@Autowired`
   - Listo para usar en cualquier método del servicio

## 🎯 CÓMO USAR EL FEIGN CLIENT

El `TarifaFeignClient` está inyectado en `FacturaService` y listo para usar. Aquí tienes ejemplos:

### **Ejemplo 1: Obtener tarifa por ID**
```java
@Transactional
public Factura crearFacturaConTarifa(Long tarifaId, Factura factura) {
    // Obtener tarifa desde el microservicio de Tarifas
    TarifaDTO tarifa = tarifaFeignClient.findTarifaById(tarifaId).getBody();
    
    // Usar la tarifa para calcular el monto
    if (tarifa != null) {
        double montoTotal = tarifa.getMonto() + tarifa.getMontoExtra();
        // ... lógica de negocio
    }
    
    return facturaRepository.save(factura);
}
```

### **Ejemplo 2: Obtener tarifa por fecha**
```java
@Transactional
public double calcularMontoSegunFecha(Date fecha) {
    TarifaDTO tarifa = tarifaFeignClient.findTarifaByFecha(fecha).getBody();
    
    if (tarifa != null) {
        return tarifa.getMonto() + tarifa.getMontoExtra();
    }
    return 0.0;
}
```

### **Ejemplo 3: Listar todas las tarifas**
```java
@Transactional(readOnly = true)
public void procesarTodasLasTarifas() {
    List<TarifaDTO> tarifas = tarifaFeignClient.findAllTarifas().getBody();
    
    if (tarifas != null) {
        // Procesar las tarifas
        tarifas.forEach(tarifa -> {
            // ... lógica de negocio
        });
    }
}
```

## 📋 MÉTODOS DISPONIBLES EN TarifaFeignClient

```java
// Obtener todas las tarifas
ResponseEntity<List<TarifaDTO>> findAllTarifas()

// Obtener tarifa por ID
ResponseEntity<TarifaDTO> findTarifaById(Long id)

// Obtener tarifa por fecha
ResponseEntity<TarifaDTO> findTarifaByFecha(Date fecha)

// Obtener tarifa por monto
ResponseEntity<TarifaDTO> findTarifaByMonto(double monto)
```

## 🚀 PARA PROBAR

1. **Iniciar MySQL** (puerto 3306) para el microservicio de Tarifas
2. **Iniciar PostgreSQL** (puerto 5432) para el microservicio de Facturación
3. **Iniciar microservicio Tarifas** (puerto 8086)
4. **Iniciar microservicio Facturación** (puerto 8083)

## ⚠️ IMPORTANTE

- El `TarifaFeignClient` está inyectado en `FacturaService`, **NO** en el controlador
- Usa el cliente dentro de tu lógica de negocio en los servicios
- Ambos microservicios deben estar corriendo para que funcione
- Siempre verifica que `.getBody()` no sea null antes de usarlo

## 🔧 CONFIGURACIÓN

**Microservicio de Tarifas:**
- Puerto: 8086
- Base de datos: MySQL (localhost:3306)
- Nombre de DB: tarifas

**Microservicio de Facturación:**
- Puerto: 8083
- Base de datos: PostgreSQL (localhost:5432)
- Nombre de DB: facturas

## 💡 BUENAS PRÁCTICAS

1. **Manejo de errores**: Siempre verifica si la respuesta es null
   ```java
   TarifaDTO tarifa = tarifaFeignClient.findTarifaById(id).getBody();
   if (tarifa == null) {
       throw new RuntimeException("Tarifa no encontrada");
   }
   ```

2. **Timeouts**: Puedes configurar timeouts en el application.properties
   ```properties
   feign.client.config.tarifas.connectTimeout=5000
   feign.client.config.tarifas.readTimeout=5000
   ```

3. **Logs**: Activa logs de Feign para debugging
   ```properties
   logging.level.grupo4.facturacion.client.TarifaFeignClient=DEBUG
   ```

## ✅ RESUMEN

Tu implementación Feign está lista. Solo tienes que:
1. Usar `tarifaFeignClient` dentro de tus métodos de `FacturaService`
2. Llamar a los métodos disponibles según necesites
3. Manejar las respuestas apropiadamente

¡El cliente Feign se encarga automáticamente de hacer las llamadas HTTP al microservicio de Tarifas!
