# Sistema de Gestión Universitaria - Integrador 3

## 📋 Descripción

Sistema de gestión universitaria desarrollado con Spring Boot que permite administrar estudiantes, carreras y matrículas. Proporciona una API RESTful para realizar operaciones CRUD y generar reportes .

## 🚀 Tecnologías Utilizadas

- **Java 17+**
- **Spring Boot 3.3.0**
  - Spring Data JPA
  - Spring Web
  - Spring Boot DevTools
- **Hibernate 6.5.2**
- **MySQL 8.0+**
- **Maven** - Gestión de dependencias
- **Lombok** - Reducción de código boilerplate
- **Apache Commons CSV** - Procesamiento de archivos CSV
- **Arquitectura REST** - API RESTful

## 📁 Estructura del Proyecto

```
Integrador3/
├── src/main/java/com/integrador3/
│   ├── controllers/          # Controladores REST
│   │   ├── EstudianteController.java
│   │   ├── CarreraController.java
│   │   └── EstudianteCarreraController.java
│   ├── model/                # Entidades JPA
│   │   ├── Estudiante.java
│   │   ├── Carrera.java
│   │   └── EstudianteCarrera.java
│   ├── dto/                  # Data Transfer Objects
│   │   ├── EstudianteDTO.java
│   │   ├── CarreraDTO.java
│   │   ├── EstudianteCarreraDTO.java
│   │   └── ReporteCarreraDTO.java
│   ├── repositorios/         # Repositorios JPA
│   │   ├── EstudianteRepositorio.java
│   │   ├── CarreraRepositorio.java
│   │   └── EstudianteCarreraRepositorio.java
│   └── utils/                # Utilidades
│       └── CargarDatos.java
├── src/main/resources/
│   ├── application.properties
│   └── csv/                  # Archivos CSV de datos iniciales
│       ├── estudiantes.csv
│       ├── carreras.csv
│       └── estudianteCarrera.csv
└── pom.xml
```

## ⚙️ Configuración

### 1. Base de Datos

Configura MySQL en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/integrador3?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```

### 2. Archivos CSV

Coloca tus archivos CSV en `src/main/resources/csv/`:

**estudiantes.csv**
```csv
dni,nombre,apellido,edad,genero,ciudad,nroLibreta
12345678,Juan,Pérez,22,Masculino,Buenos Aires,1001
```

**carreras.csv**
```csv
idCarrera,carrera,duracion
1,Ingeniería,5
```

**estudianteCarrera.csv**
```csv
id,id_estudiante,id_carrera,inscripcion,graduacion,antiguedad
1,12345678,1,2020,0,4
```

### 3. Ejecutar la Aplicación

```bash
# Clonar el repositorio
git clone <url-repositorio>
cd Integrador3

# Compilar y ejecutar
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## 📚 API Endpoints

### Estudiantes

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/estudiantes` | Obtener todos los estudiantes |
| GET | `/estudiantes/{id}` | Obtener estudiante por ID |
| GET | `/estudiantes/ordenar?criterio=nombre` | Obtener estudiantes ordenados |
| GET | `/estudiantes/nroLibreta/{nroLibreta}` | Buscar por número de libreta |
| GET | `/estudiantes/genero/{genero}` | Filtrar por género |
| GET | `/estudiantes/filtro?carrera=X&ciudad=Y` | Filtrar por carrera y ciudad |
| POST | `/estudiantes` | Crear nuevo estudiante |

### Carreras

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/carreras` | Obtener todas las carreras |
| GET | `/carreras/con-estudiantes` | Carreras ordenadas por cantidad de inscriptos |
| POST | `/carreras` | Crear nueva carrera |

### Matrículas

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/estudiantes-carreras` | Obtener todas las matrículas |
| POST | `/estudiantes-carreras/matricular?estudianteId=1&carreraId=2` | Matricular estudiante |

## 🔧 Ejemplos de Uso

### 1. Obtener estudiantes ordenados por nombre

```bash
curl http://localhost:8080/estudiantes/ordenar?criterio=nombre
```

### 2. Buscar estudiante por libreta

```bash
curl http://localhost:8080/estudiantes/nroLibreta/1001
```

### 3. Filtrar estudiantes por carrera y ciudad

```bash
curl "http://localhost:8080/estudiantes/filtro?carrera=Ingenieria&ciudad=Tandil"
```

### 4. Matricular un estudiante

```bash
curl -X POST "http://localhost:8080/estudiantes-carreras/matricular?estudianteId=12345678&carreraId=1"
```

### 5. Crear nuevo estudiante

```bash
curl -X POST http://localhost:8080/estudiantes \
  -H "Content-Type: application/json" \
  -d '{
    "dni": 87654321,
    "nombre": "María",
    "apellido": "González",
    "edad": 21,
    "genero": "Femenino",
    "ciudad": "Córdoba",
    "nroLibreta": 2001
  }'
```

### 6. Obtener carreras con estudiantes

```bash
curl http://localhost:8080/carreras/con-estudiantes
```

## 📊 Características Principales

### ✅ Funcionalidades Implementadas

- **a)** Dar de alta un estudiante
- **b)** Matricular un estudiante en una carrera
- **c)** Recuperar todos los estudiantes con criterio de ordenamiento
- **d)** Recuperar estudiante por número de libreta universitaria
- **e)** Recuperar estudiantes filtrados por género
- **f)** Recuperar carreras con estudiantes inscriptos (ordenadas por cantidad)
- **g)** Recuperar estudiantes por carrera y ciudad de residencia
- **h)** Generar reporte de carreras con inscriptos y egresados por año

### 🎯 Características Técnicas

- **Arquitectura REST** con controladores específicos
- **Patrón DTO** para transferencia de datos
- **Consultas JPQL personalizadas** con proyecciones
- **Relaciones JPA** bidireccionales (One-to-Many, Many-to-One)
- **Validación de datos** en entidades
- **Carga automática de datos** desde archivos CSV
- **Manejo de errores** con ResponseEntity


## 🗃️ Modelo de Datos

### Estudiante
- DNI (PK)
- Nombre
- Apellido
- Edad
- Género
- Ciudad
- Número de Libreta (Unique)

### Carrera
- ID (PK)
- Nombre
- Duración (años)

### EstudianteCarrera (Tabla intermedia)
- ID (PK)
- Estudiante (FK)
- Carrera (FK)
- Año de Inscripción
- Año de Graduación
- Antigüedad


## 👥 Autor

Desarrollado como proyecto integrador de Arquitecturas Web

## 📄 Licencia

Este proyecto es de uso académico.