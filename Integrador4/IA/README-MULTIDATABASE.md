# Configuración de Múltiples Bases de Datos en el Módulo IA

## 📋 Resumen

El módulo IA ahora tiene acceso a **todas las bases de datos** de los demás microservicios:

### Bases de Datos MySQL:
- ✅ **paradas** (BD principal/por defecto)
- ✅ **usuarios** (del microservicio usuario-cuenta)
- ✅ **viajes** (del microservicio viajes)
- ✅ **facturas** (del microservicio facturacion)
- ✅ **tarifas** (del microservicio tarifas)

### Base de Datos MongoDB:
- ✅ **db_monopatines** (del microservicio monopatin)

---

## 🔧 Componentes Creados

### 1. `MultiDataSourceConfig.java`
Configura y registra todas las conexiones a las bases de datos MySQL.
- Crea un `DataSource` y un `JdbcTemplate` para cada base de datos
- Marca `paradas` como el DataSource principal (`@Primary`)

### 2. `DataSourceManager.java`
Gestor centralizado de conexiones que:
- Almacena todos los `JdbcTemplate` y `DataSource`
- **Detecta automáticamente** qué base de datos usar según las tablas mencionadas en la SQL
- Provee acceso programático a cualquier base de datos

### 3. `IaService.java` (actualizado)
Ahora:
- Usa `DataSourceManager` en lugar de `EntityManager`
- Detecta automáticamente la base de datos correcta
- Ejecuta consultas SELECT y DML (INSERT/UPDATE/DELETE) en la base de datos apropiada
- Devuelve resultados en formato `Map<String, Object>` más legible

---

## 🚀 Cómo Funciona

### Detección Automática de Base de Datos

El servicio analiza la consulta SQL y detecta automáticamente qué base de datos usar:

```java
// Si la SQL menciona "usuario" o "cuenta" → usa BD usuarios
// Si menciona "viaje" → usa BD viajes
// Si menciona "factura" → usa BD facturas
// Si menciona "tarifa" → usa BD tarifas
// Si menciona "parada" → usa BD paradas
// Por defecto → usa BD paradas
```

### Ejemplos de Uso

#### Consulta en BD de Usuarios:
```http
POST http://localhost:8089/api/ia/prompt
Content-Type: application/json

"Dame todos los usuarios registrados"
```
✅ Detecta automáticamente la BD `usuarios` y ejecuta la consulta allí.

#### Consulta en BD de Viajes:
```http
POST http://localhost:8089/api/ia/prompt
Content-Type: application/json

"Muéstrame los viajes que duraron más de 30 minutos"
```
✅ Detecta automáticamente la BD `viajes` y ejecuta la consulta allí.

#### Consulta en BD de Facturas:
```http
POST http://localhost:8089/api/ia/prompt
Content-Type: application/json

"Dame el total facturado hoy"
```
✅ Detecta automáticamente la BD `facturas` y ejecuta la consulta allí.

#### Consulta Cross-Database (JOIN entre BDs):
```http
POST http://localhost:8089/api/ia/prompt
Content-Type: application/json

"Dame los nombres de usuarios y sus viajes"
```
⚠️ **Nota**: Los JOINs entre diferentes bases de datos requieren usar nombres completos:
`usuarios.usuario`, `viajes.viaje`, etc.

---

## 📊 Formato de Respuesta

### Para SELECT:
```json
{
  "success": true,
  "message": "Consulta SELECT ejecutada con éxito en BD: usuarios",
  "data": [
    {
      "id": 1,
      "nombre": "Juan Pérez",
      "email": "juan@example.com"
    },
    {
      "id": 2,
      "nombre": "María García",
      "email": "maria@example.com"
    }
  ]
}
```

### Para INSERT/UPDATE/DELETE:
```json
{
  "success": true,
  "message": "Sentencia DML ejecutada con éxito en BD: usuarios",
  "data": {
    "rowsAffected": 1
  }
}
```

---

## 🔒 Seguridad

- ✅ Bloquea operaciones DDL peligrosas: `DROP`, `TRUNCATE`, `ALTER`, `CREATE`, `GRANT`, `REVOKE`
- ✅ Solo permite DML seguro: `SELECT`, `INSERT`, `UPDATE`, `DELETE`
- ✅ Validación de SQL antes de ejecutar
- ✅ Transacciones solo para operaciones DML (evita el error rollback-only)

---

## ⚙️ Configuración

Ver archivo `application.properties` para todas las conexiones configuradas.

Cada base de datos tiene su propia configuración con el formato:
```properties
spring.datasource.<nombre>.url=jdbc:mysql://localhost:3306/<database>
spring.datasource.<nombre>.username=root
spring.datasource.<nombre>.password=
spring.datasource.<nombre>.driver-class-name=com.mysql.cj.jdbc.Driver
```

---

## 🧪 Testing

Usa el archivo `ejemplos-prompts.http` para probar diferentes consultas en diferentes bases de datos.

**Importante**: Antes de ejecutar, configura la variable de entorno:
```bash
export GROQ_API_KEY="tu_clave_aqui"
```

O en IntelliJ: Run > Edit Configurations > Environment variables > `GROQ_API_KEY=tu_clave`

