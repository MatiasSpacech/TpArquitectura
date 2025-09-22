# Trabajo Práctico Integrador 1
## Grupo 4
### Integrantes:
- Agustín Van Waarde
- Matias Spacech
- Daniel Teruggi
- Osvaldo Olea
## Estructura del Proyecto

El proyecto está organizado en los siguientes paquetes principales:

- `dao`: Interfaces para el acceso a datos
- `dto`: Objetos de transferencia de datos
- `entity`: Entidades del modelo de negocio
- `factory`: Fábricas para la creación de DAOs y conexiones
- `mysql`: Implementaciones MySQL de los DAOs
- `utils`: Utilidades para carga y borrado de datos

## Funcionalidades Principales

1. **Gestión de Datos**
   - Carga de datos desde archivos CSV
   - Borrado de datos existentes
   - Persistencia en base de datos MySQL

2. **Análisis de Ventas**
   - Obtención del producto con mayor recaudación
   - Listado de clientes ordenados por facturación

3. **Entidades Principales**
   - Clientes
   - Productos
   - Facturas
   - Relación Factura-Producto

## Tecnologías Utilizadas

- Java
- JDBC
- MySQL
- Docker (para la base de datos)
- Maven (gestión de dependencias)

## Configuración del Proyecto

1. El proyecto utiliza Docker Compose para la gestión de la base de datos
2. Maven para la gestión de dependencias
3. Los datos de prueba se encuentran en archivos CSV en la carpeta `resources/archivos`

## Patrones de Diseño

- Patrón DAO (Data Access Object)
- Patrón Factory
- Patrón DTO (Data Transfer Object)
- Singleton (para la fábrica de DAOs)

## Base de Datos

La aplicación utiliza MySQL como sistema de gestión de base de datos, implementando las siguientes entidades:
- Clientes
- Productos
- Facturas
- Factura_Producto (tabla de relación)

## Ejemplo de Uso

```java
// Inicialización
DaoFactory daoFactory = DaoFactory.getInstance(DBType.MYSQL);

// Obtener producto con mayor recaudación
ProductoDao productoDao = daoFactory.createProductoDao();
ProductoRecaudacionDTO productoRecaudacionDTO = productoDao.productoMasRecaudo();

// Listar clientes por facturación
ClienteDao clienteDao = daoFactory.createClienteDao();
List<ClienteFacturacionDTO> clientesXfacturacion = clienteDao.listarClientesXFacturacion();
```
