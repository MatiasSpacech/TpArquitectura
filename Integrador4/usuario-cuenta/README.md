# Microservicio de Usuarios y Cuentas (msvc-usuario)

Este microservicio es el responsable de gestionar toda la información relacionada con los usuarios y sus cuentas asociadas.

## Requisitos Previos

*   Java 21 (o la versión especificada en `pom.xml`)
*   Maven 3.x
*   Docker y Docker Compose
*   Un cliente de API como Postman o `curl`.

## Puesta en Marcha (Desarrollo Local)

Para ejecutar este microservicio de forma local, sigue estos pasos:

### 1. Levantar la Base de Datos

Este servicio requiere una instancia de MySQL. Se proporciona un archivo `docker-compose.yml` para levantarla fácilmente.

Desde la raíz de este proyecto, ejecuta el siguiente comando en tu terminal:

```bash
docker-compose up -d
```

Esto levantará un contenedor Docker con una base de datos MySQL en el puerto 3306, creando automáticamente el esquema usuarios.
2. Ejecutar la Aplicación Spring Boot
   Puedes ejecutar la aplicación de dos maneras:
   Desde tu IDE:
   Abre el proyecto en tu IDE (IntelliJ, Eclipse, etc.).
   Busca la clase MscvUsuarioApplication.java y ejecútala.
   Desde la terminal:
```bash
./mvnw spring-boot:run
```

La aplicación se ejecutará en el puerto 8082, como se especifica en application.properties.
API Endpoints
A continuación se detallan los endpoints principales para probar la funcionalidad del servicio.
<hr></hr>
🏛️ Gestión de Cuentas
1. Obtener todas las cuentas
Devuelve una lista de todas las cuentas.
Método: GET
URL: /api/cuenta

```bash
curl --location 'http://localhost:8082/api/cuenta'
```
Respuesta Exitosa (200 OK): Una lista en formato JSON de todas las cuentas.
2. Obtener una cuenta por ID
   Busca y devuelve una cuenta específica por su ID.
   Método: GET
   URL: /api/cuenta/{id}
   Comando curl (Ejemplo para ID 1):
3. ```bash
   curl --location 'http://localhost:8082/api/cuenta/1'
    ```

Respuesta Exitosa (200 OK): El objeto de la cuenta encontrada.
Respuesta de Error (404 Not Found): Si no se encuentra una cuenta con ese ID.
3. Crear una nueva cuenta
   Crea una nueva cuenta en el sistema.
   Método: POST
   URL: /api/cuenta
   Body (JSON):
```bash
{
    "idMercadoPago": "MP-1234567890",
    "tipoCuenta": "PREMIUM",
    "saldo": 0.0
}
```
Comando curl:
```bash
curl --location --request POST 'http://localhost:8082/api/cuenta' \
--header 'Content-Type: application/json' \
--data '{
    "idMercadoPago": "MP-1234567890",
    "tipoCuenta": "PREMIUM",
    "saldo": 0.0
}'
```

Respuesta Exitosa (201 Created): El objeto de la cuenta recién creada.
<hr></hr>
👤 Gestión de Usuarios
4. Obtener todos los usuarios
Devuelve una lista de todos los usuarios.
Método: GET
URL: /api/usuario
Comando curl:

```bash
curl --location 'http://localhost:8082/api/usuario'
```

Respuesta Exitosa (200 OK): Una lista en formato JSON de todos los usuarios.
5. Crear un nuevo usuario
   Registra un nuevo usuario en el sistema.
   Método: POST
   URL: /api/usuario
   Body (JSON):

```bash
{
    "nombre": "Marta",
    "apellido": "Sanchez",
    "email": "marta@example.com",
    "rol": "USUARIO",
    "latitud": -34.5889,
    "longitud": -58.3977
}
```
Comando curl:
```bash
curl --location --request POST 'http://localhost:8082/api/usuario' \
--header 'Content-Type: application/json' \
--data '{
    "nombre": "Marta",
    "apellido": "Sanchez",
    "email": "marta@example.com",
    "rol": "USUARIO",
    "latitud": -34.5889,
    "longitud": -58.3977
}'
```

Respuesta Exitosa (201 Created): El objeto del usuario recién creado.

#### 6. Actualizar un usuario

Modifica los datos de un usuario existente.

*   **Método:** `PUT`
*   **URL:** `/api/usuario/{id}`
*   **Body (JSON):**
    Los campos que deseas actualizar. Por ejemplo, para cambiar el nombre y apellido de un usuario.

    ```json
    {
        "nombre": "Martita",
        "apellido": "Sanchez",
        "email": "marta@example.com",
        "rol": "USUARIO",
        "latitud": -34.5889,
        "longitud": -58.3977
    }
    ```

*   **Comando `curl` (Ejemplo para actualizar usuario con ID 6):**

    ```bash
    curl --location --request PUT 'http://localhost:8082/api/usuario/6' \
    --header 'Content-Type: application/json' \
    --data '{
        "nombre": "Martita",
        "apellido": "Sanchez"
    }'
    ```

*   **Respuesta Exitosa (200 OK):** El objeto del usuario con los datos actualizados.
*   **Respuesta de Error (404 Not Found):** Si no se encuentra un usuario con el ID especificado.


#### 7. Eliminar un usuario

Elimina un usuario existente del sistema por su ID.

*   **Método:** `DELETE`
*   **URL:** `/api/usuario/{id}`
*   **Comando `curl` (Ejemplo para eliminar usuario con ID 6):**

    ```bash
    curl --location --request DELETE 'http://localhost:8082/api/usuario/6'
    ```

*   **Respuesta Exitosa (204 No Content):** La petición se procesó correctamente y el usuario fue eliminado. No se devuelve contenido en el cuerpo de la respuesta.
*   **Respuesta de Error (404 Not Found):** Si no se encuentra un usuario con el ID especificado.
<hr>

🔗 Gestión de Asociaciones
(Nota: Los siguientes endpoints para asociar/desasociar usuarios y cuentas aún no están implementados en los controladores actuales).

6. Asociar un usuario a una cuenta
   Vincula un usuario existente a una cuenta existente.
   Método: POST
   URL: /api/cuenta/{idCuenta}/usuario/{idUsuario}
   Comando curl (Ejemplo):

```bash
curl --location --request POST 'http://localhost:8082/api/cuenta/1/usuario/1'
```




