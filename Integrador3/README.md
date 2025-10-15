# Sistema de Gestión Universitaria - Integrador 3

## 📋 Descripción

Sistema de gestión universitaria desarrollado con Spring Boot que permite administrar estudiantes, carreras y matrículas. Proporciona una API RESTful completa para realizar operaciones CRUD, consultas avanzadas y generar reportes estadísticos.

## 🚀 Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.3.0**
  - Spring Data JPA
  - Spring Web
  - Spring Boot DevTools
- **Hibernate 6.5.2** (con MySQL Dialect)
- **MySQL 8.0+**
- **Maven** - Gestión de dependencias
- **Lombok** - Reducción de código boilerplate
- **Apache Commons CSV 1.8** - Procesamiento de archivos CSV
- **Arquitectura REST** - API RESTful con ResponseEntity

## 📁 Estructura del Proyecto

```
Integrador3/
├── src/main/java/com/integrador3/
│   ├── Integrador3Application.java    # Clase principal
│   ├── controllers/                    # Controladores REST
│   │   ├── EstudianteController.java
│   │   ├── CarreraController.java
│   │   └── EstudianteCarreraController.java
│   ├── model/                          # Entidades JPA
│   │   ├── Estudiante.java
│   │   ├── Carrera.java
│   │   └── EstudianteCarrera.java
│   ├── dto/                            # Data Transfer Objects
│   │   ├── EstudianteDTO.java
│   │   ├── CarreraDTO.java
│   │   └── ReporteCarreraDTO.java
│   ├── repositorios/                   # Repositorios JPA
│   │   ├── EstudianteRepositorio.java
│   │   ├── CarreraRepositorio.java
│   │   └── EstudianteCarreraRepositorio.java
│   ├── servicios/                      # Capa de servicios
│   │   ├── EstudianteService.java
│   │   ├── CarreraService.java
│   │   ├── EstudianteCarreraService.java
│   │   └── exceptions/
│   │       └── NotFoundException.java
│   └── utils/                          # Utilidades
│       └── CargarDatos.java           # Carga inicial de CSV
├── src/main/resources/
│   ├── application.properties
│   └── csv/                            # Datos iniciales
│       ├── estudiantes.csv
│       ├── carreras.csv
│       └── estudianteCarrera.csv
├── src/test/java/                      # Tests
├── docker-compose.yml                  # Configuración Docker
└── pom.xml                             # Dependencias Maven
```

## ⚙️ Configuración

### 1. Base de Datos MySQL

Configura la conexión en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/integrador3?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=tu_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

**Nota:** `ddl-auto=create-drop` eliminará y recreará las tablas cada vez que inicies la aplicación. Para producción, usa `update` o `validate`.

### 2. Archivos CSV

Coloca tus archivos CSV en `src/main/resources/csv/` para carga automática de datos:

**estudiantes.csv**
```csv
dni,nombre,apellido,edad,genero,ciudad,nroLibreta
12345678,Juan,Pérez,22,Masculino,Buenos Aires,1001
87654321,María,González,21,Femenino,Tandil,1002
```

**carreras.csv**
```csv
idCarrera,carrera,duracion
1,Ingeniería en Sistemas,5
2,TUDAI,3
```

**estudianteCarrera.csv**
```csv
id,id_estudiante,id_carrera,inscripcion,graduacion,antiguedad
1,12345678,1,2020,0,5
2,87654321,2,2021,2024,4
```



## 📚 API Endpoints

### 👨‍🎓 Estudiantes

| Método | Endpoint | Descripción | Parámetros |
|--------|----------|-------------|------------|
| **GET** | `/estudiantes/` | Obtener todos los estudiantes | - |
| **GET** | `/estudiantes/{id}` | Obtener estudiante por DNI | `id`: DNI del estudiante |
| **GET** | `/estudiantes/ordenar` | Obtener estudiantes ordenados | `criterio`: nombre, apellido, edad, dni, ciudad, etc. |
| **GET** | `/estudiantes/nroLibreta/{nroLibreta}` | Buscar por número de libreta | `nroLibreta`: número único |
| **GET** | `/estudiantes/genero/{genero}` | Filtrar por género | `genero`: Masculino/Femenino |
| **GET** | `/estudiantes/filtro` | Filtrar por carrera y ciudad | `carrera`, `ciudad` |
| **POST** | `/estudiantes/` | Crear nuevo estudiante | JSON body |

### 🎓 Carreras

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| **GET** | `/carreras/` | Obtener todas las carreras |
| **GET** | `/carreras/con-estudiantes` | Carreras ordenadas por cantidad de inscriptos |

### 📝 Matrículas (Estudiante-Carrera)

| Método | Endpoint | Descripción | Parámetros |
|--------|----------|-------------|------------|
| **POST** | `/estudiantes-carreras/matricular` | Matricular estudiante en carrera | `estudianteId`, `carreraId` |
| **GET** | `/estudiantes-carreras/reportes` | Reporte de carreras con inscriptos/egresados por año | - |



## 📊 Características Principales

### ✅ Funcionalidades Implementadas

- **a)** ✔️ Dar de alta un estudiante
- **b)** ✔️ Matricular un estudiante en una carrera
- **c)** ✔️ Recuperar todos los estudiantes con criterio de ordenamiento dinámico
- **d)** ✔️ Recuperar estudiante por número de libreta universitaria
- **e)** ✔️ Recuperar estudiantes filtrados por género
- **f)** ✔️ Recuperar carreras con estudiantes inscriptos (ordenadas por cantidad)
- **g)** ✔️ Recuperar estudiantes por carrera y ciudad de residencia
- **h)** ✔️ Generar reporte de carreras con inscriptos y egresados por año

### 🎯 Características Técnicas

- **Arquitectura en Capas**: Controllers → Services → Repositories
- **Patrón DTO**: Separación entre entidades y objetos de transferencia
- **Consultas JPQL**: Queries personalizadas con proyecciones
- **Spring Data JPA Sort**: Ordenamiento dinámico seguro
- **Relaciones JPA**: Bidireccionales (OneToMany, ManyToOne)
- **Validación**: Control de errores con ResponseEntity y códigos HTTP apropiados
- **Carga Automática**: Datos iniciales desde archivos CSV con `CommandLineRunner`
- **Exception Handling**: Manejo centralizado de excepciones personalizadas
- **Lombok**: Reducción de boilerplate con anotaciones (@Data, @AllArgsConstructor, etc.)

### 📡 Códigos de Estado HTTP Utilizados

- **200 OK**: Solicitud exitosa
- **201 CREATED**: Recurso creado exitosamente
- **204 NO CONTENT**: Solicitud exitosa pero sin contenido para devolver
- **404 NOT FOUND**: Recurso no encontrado
- **500 INTERNAL SERVER ERROR**: Error del servidor

## 🗃️ Modelo de Datos

### Estudiante
- **DNI** (Long, PK) - Documento Nacional de Identidad
- **Nombre** (String)
- **Apellido** (String)
- **Edad** (int)
- **Género** (String)
- **Ciudad** (String)
- **Número de Libreta** (Long, Unique) - Identificador universitario único
- **Carreras** (List<EstudianteCarrera>) - Relación con carreras matriculadas

### Carrera
- **ID** (int, PK)
- **Nombre** (String)
- **Duración** (int) - Años de duración
- **Estudiantes** (List<EstudianteCarrera>) - Relación con estudiantes inscriptos

### EstudianteCarrera (Tabla Intermedia)
- **ID** (Long, PK)
- **Estudiante** (FK) - Referencia a Estudiante
- **Carrera** (FK) - Referencia a Carrera
- **Inscripción** (int) - Año de inscripción
- **Graduación** (int) - Año de graduación (0 si no se graduó)
- **Antigüedad** (int) - Años desde la inscripción

### Relaciones
- **Estudiante ↔ EstudianteCarrera**: OneToMany / ManyToOne
- **Carrera ↔ EstudianteCarrera**: OneToMany / ManyToOne

## 🛠️ Tecnologías y Patrones

### Anotaciones Spring Utilizadas
- `@RestController` - Controladores REST
- `@Service` / `@Transactional` - Capa de servicios
- `@Repository` - Repositorios JPA
- `@Component` - Componentes Spring
- `@Autowired` - Inyección de dependencias
- `@Query` - Consultas JPQL personalizadas
- `@JsonIgnore` - Evitar referencias circulares en JSON

### Anotaciones Lombok
- `@Data` - Getters, setters, toString, equals, hashCode
- `@AllArgsConstructor` - Constructor con todos los parámetros
- `@NoArgsConstructor` - Constructor sin parámetros
- `@RequiredArgsConstructor` - Constructor con campos final/non-null

## 📝 Notas Importantes

- La aplicación carga datos automáticamente desde archivos CSV al iniciar (clase `CargarDatos` con `CommandLineRunner`)
- El modo `ddl-auto=create-drop` elimina y recrea las tablas en cada inicio
- Las consultas SQL generadas se muestran en la consola gracias a `show-sql=true`
- Los DTOs previenen referencias circulares y exponen solo los datos necesarios
- El ordenamiento dinámico usa `Sort` de Spring Data para prevenir SQL injection

## 👥 Autores

Desarrollado como Trabajo Práctico Integrador para la materia Arquitecturas Web TUDAI.

**Grupo 4**

## 📄 Licencia

Este proyecto es de uso académico - TUDAI 2025.

---

**Última actualización:** Octubre 2025
