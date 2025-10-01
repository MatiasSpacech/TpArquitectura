# Trabajo integrador 2

## Descripción
Este proyecto implementa un sistema de gestión académica universitaria que permite administrar estudiantes, carreras y la relación entre ambos (matrículas). El sistema realiza operaciones CRUD, reportes estadísticos y permite consultas específicas sobre la información académica.

## Estructura del Proyecto
El proyecto sigue una arquitectura multicapa con separación de responsabilidades:

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── grupo4/
│   │           ├── dto/            # Objetos de transferencia de datos
│   │           ├── factory/        # Fábricas para objetos complejos
│   │           ├── model/          # Entidades JPA
│   │           ├── repository/     # Repositorios para acceso a datos
│   │           └── utils/          # Utilidades, incluyendo carga de datos
│   └── resources/
│       ├── csv/                    # Archivos CSV con datos de prueba
│       └── META-INF/
│           └── persistence.xml     # Configuración de JPA
```

## Tecnologías Utilizadas

- **Java**: Lenguaje de programación principal (JDK 21)
- **JPA/Hibernate**: Framework ORM para mapeo objeto-relacional
- **MySQL**: Sistema de gestión de bases de datos relacional
- **Maven**: Gestión de dependencias y construcción del proyecto
- **Docker**: Contenerización de la base de datos
- **Lombok**: Reducción de código boilerplate (getters, setters, constructores)
- **Apache Commons CSV**: Biblioteca para procesamiento de archivos CSV

## Patrones de Diseño Implementados

1. **Patrón Repository**: Separación de la lógica de acceso a datos del resto de la aplicación.
   
2. **Patrón DTO (Data Transfer Object)**: Transferencia de datos entre capas sin exponer detalles de implementación.

3. **Patrón Factory**: Implementado en `JPAutil` para la creación de `EntityManager`.

4. **Patrón MVC (Modelo-Vista-Controlador)**: Separación de responsabilidades entre modelo (entidades), vista (salida por consola) y controlador (Main y utils).

## Modelo de Datos

### Entidades Principales
- **Estudiante**: Representa a un estudiante universitario con sus datos personales.
- **Carrera**: Representa una carrera universitaria con su nombre y duración.
- **EstudianteCarrera**: Entidad asociativa que representa la matrícula de un estudiante en una carrera.

### Diagrama Entidad-Relación
El sistema implementa una relación muchos a muchos entre Estudiante y Carrera, mediante la tabla asociativa EstudianteCarrera.

## Configuración y Uso

### Requisitos Previos
- Java JDK 21
- Maven
- Docker (opcional, para la base de datos)

### Configuración de la Base de Datos
1. Asegúrate de tener MySQL corriendo en localhost:3306
2. Crea una base de datos llamada `integrador2`
3. Configura el usuario y contraseña en `persistence.xml`

Alternativamente, puedes usar Docker:
```bash
docker-compose up -d
```

### Ejecución del Proyecto
1. Clona este repositorio
2. Compila el proyecto:
   ```bash
   mvn clean install
   ```
3. Ejecuta la aplicación:
   ```bash
   mvn exec:java -Dexec.mainClass="com.grupo4.Main"
   ```

## Funcionalidades Principales

1. **Gestión de Estudiantes**:
   - Dar de alta estudiantes
   - Recuperar estudiantes por varios criterios
   - Ordenar estudiantes por diferentes campos

2. **Gestión de Carreras**:
   - Registrar nuevas carreras
   - Obtener carreras con inscriptos
   - Generar reportes por carrera

3. **Gestión de Matrículas**:
   - Matricular estudiantes en carreras
   - Registrar fechas de graduación
   - Calcular antigüedad

## Consultas Implementadas

- Recuperar estudiantes ordenados por varios criterios
- Recuperar estudiantes por género
- Recuperar carreras con estudiantes inscriptos ordenados por cantidad
- Recuperar estudiantes de una determinada carrera filtrados por ciudad
- Generar reportes estadísticos de carreras

## Contribuciones
El proyecto ha sido desarrollado por el Grupo 4 como parte del trabajo integrador para la materia de Arquitecturas Web.

## Licencia
Este proyecto es de uso educativo y está disponible bajo la licencia MIT.

---

© 2025 Grupo 4 - Arquitecturas Web