# Task API

![Java](https://img.shields.io/badge/Java-17-007396?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-build-C71A36?style=flat-square&logo=apachemaven&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-persistence-4479A1?style=flat-square&logo=mysql&logoColor=white)

API REST para gestionar tareas, desarrollada con Java 17 y Spring Boot. El proyecto está pensado como una pieza de portfolio backend: mantiene una arquitectura por capas, usa DTOs para separar la API del modelo de persistencia, valida las entradas y devuelve errores con una estructura consistente.

## Stack

| Tecnología | Uso |
| --- | --- |
| Java 17 | Lenguaje principal |
| Spring Boot 3.5 | Base de la aplicación |
| Spring Web | Exposición de endpoints REST |
| Spring Data JPA | Acceso a datos y repositorios |
| Jakarta Validation | Validación de requests |
| MySQL | Base de datos en ejecución local |
| H2 | Base de datos en memoria para tests |
| Maven | Gestión de dependencias y ciclo de build |
| JUnit 5, Mockito y MockMvc | Tests unitarios e integración |

## Funcionalidades

- Crear, listar, consultar, actualizar y eliminar tareas.
- Persistencia en MySQL mediante Spring Data JPA.
- Listado de tareas ordenado por fecha de creación descendente.
- Validación de campos obligatorios y tamaños máximos.
- DTOs específicos para entrada y salida de datos.
- Gestión centralizada de errores para recursos no encontrados y errores de validación.
- Tests unitarios de la capa de servicio.
- Tests de integración del controlador con MockMvc y H2.

## Modelo de tarea

| Campo | Tipo | Reglas |
| --- | --- | --- |
| `id` | `Long` | Generado automáticamente |
| `title` | `String` | Obligatorio, máximo 120 caracteres |
| `description` | `String` | Opcional, máximo 500 caracteres |
| `completed` | `Boolean` | Obligatorio |
| `dueDate` | `LocalDate` | Opcional, formato `YYYY-MM-DD` |
| `createdAt` | `LocalDateTime` | Generado automáticamente al crear la tarea |

## Estructura del proyecto

```text
src
+-- main
|   +-- java/com/portfolio/taskapi
|   |   +-- controller      # Endpoints REST
|   |   +-- dto             # Contratos de entrada y salida
|   |   +-- entity          # Entidades JPA
|   |   +-- exception       # Manejo centralizado de errores
|   |   +-- mapper          # Conversión entre entidades y DTOs
|   |   +-- repository      # Repositorios Spring Data JPA
|   |   +-- service         # Lógica de negocio
|   +-- resources
|       +-- application.properties
+-- test
    +-- java/com/portfolio/taskapi
    |   +-- controller      # Tests de integración
    |   +-- service         # Tests unitarios
    +-- resources
        +-- application.properties
```

## Ejecutar en local

### Requisitos

- Java 17 o superior.
- MySQL en local.
- Maven Wrapper incluido en el proyecto (`mvnw` / `mvnw.cmd`).

### Configuración

La aplicación usa estas variables de entorno, con valores por defecto para facilitar la ejecución local:

| Variable | Valor por defecto |
| --- | --- |
| `SERVER_PORT` | `8080` |
| `DB_URL` | `jdbc:mysql://localhost:3306/task_management?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC` |
| `DB_USERNAME` | `root` |
| `DB_PASSWORD` | `root` |

La base de datos `task_management` se crea automáticamente si el usuario de MySQL tiene permisos para ello.

### Comandos

Ejecutar tests:

```bash
./mvnw test
```

En Windows:

```powershell
.\mvnw.cmd test
```

Levantar la aplicación:

```bash
./mvnw spring-boot:run
```

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

La API quedará disponible en:

```text
http://localhost:8080
```

## Endpoints

| Método | Endpoint | Descripción | Respuesta |
| --- | --- | --- | --- |
| `GET` | `/tasks` | Lista todas las tareas | `200 OK` |
| `GET` | `/tasks/{id}` | Obtiene una tarea por id | `200 OK` / `404 Not Found` |
| `POST` | `/tasks` | Crea una tarea | `201 Created` / `400 Bad Request` |
| `PUT` | `/tasks/{id}` | Actualiza una tarea existente | `200 OK` / `400 Bad Request` / `404 Not Found` |
| `DELETE` | `/tasks/{id}` | Elimina una tarea | `204 No Content` / `404 Not Found` |

### Crear una tarea

```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Preparar portfolio",
    "description": "Publicar API REST de tareas en GitHub",
    "completed": false,
    "dueDate": "2026-05-10"
  }'
```

Respuesta:

```json
{
  "id": 1,
  "title": "Preparar portfolio",
  "description": "Publicar API REST de tareas en GitHub",
  "completed": false,
  "dueDate": "2026-05-10",
  "createdAt": "2026-04-28T10:30:00"
}
```

### Listar tareas

```bash
curl http://localhost:8080/tasks
```

Respuesta:

```json
[
  {
    "id": 1,
    "title": "Preparar portfolio",
    "description": "Publicar API REST de tareas en GitHub",
    "completed": false,
    "dueDate": "2026-05-10",
    "createdAt": "2026-04-28T10:30:00"
  }
]
```

### Consultar una tarea

```bash
curl http://localhost:8080/tasks/1
```

### Actualizar una tarea

```bash
curl -X PUT http://localhost:8080/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Preparar API para portfolio",
    "description": "Documentar endpoints y ejecutar tests",
    "completed": true,
    "dueDate": "2026-05-15"
  }'
```

### Eliminar una tarea

```bash
curl -X DELETE http://localhost:8080/tasks/1
```

Si la eliminación se realiza correctamente, la respuesta no devuelve cuerpo y usa el estado `204 No Content`.

## Ejemplos de errores

### Recurso no encontrado

```json
{
  "timestamp": "2026-04-28T10:35:00",
  "status": 404,
  "error": "Not Found",
  "message": "Task not found with id 99",
  "path": "/tasks/99",
  "errors": {}
}
```

### Error de validación

```json
{
  "timestamp": "2026-04-28T10:36:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/tasks",
  "errors": {
    "title": "Title is required",
    "completed": "Completed is required"
  }
}
```

## Tests

El proyecto incluye tests unitarios y de integración:

```bash
./mvnw test
```

Los tests usan H2 en memoria, por lo que se pueden ejecutar sin tener MySQL levantado.

## Mejoras futuras

- Añadir documentación OpenAPI/Swagger.
- Incorporar paginación, filtros por estado y búsqueda por título.
- Añadir Docker Compose para levantar la API junto con MySQL.
- Gestionar migraciones de base de datos con Flyway o Liquibase.
- Añadir autenticación con Spring Security y JWT.
- Configurar CI con GitHub Actions.
