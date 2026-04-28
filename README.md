# Task API

🚀 Proyecto backend de portfolio enfocado en arquitectura limpia, testing y buenas prácticas de desarrollo.

![Java](https://img.shields.io/badge/Java-17-007396?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-build-C71A36?style=flat-square&logo=apachemaven&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-persistence-4479A1?style=flat-square&logo=mysql&logoColor=white)

API REST para gestionar tareas, desarrollada con Java 17 y Spring Boot. Este proyecto está diseñado como pieza de portfolio backend, priorizando código limpio, arquitectura por capas, validación de datos y pruebas automatizadas.

---

## 🎯 Problema y enfoque

Este proyecto simula un backend real para la gestión de tareas.

El objetivo no era solo implementar un CRUD, sino construir una API mantenible y preparada para crecer:

- Separación clara entre la API y el modelo de persistencia mediante DTOs.
- Validación robusta de datos de entrada.
- Manejo centralizado de errores.
- Arquitectura por capas bien definida.
- Cobertura con tests unitarios y de integración.

El foco principal ha sido escribir código backend claro, escalable y entendible.

---

## 🚀 Features

- CRUD completo de tareas: crear, leer, actualizar y eliminar.
- Validación de datos con Jakarta Validation.
- Manejo global de errores con respuestas estructuradas.
- Uso de DTOs para desacoplar API y persistencia.
- Persistencia en MySQL mediante Spring Data JPA.
- Tests unitarios para la capa de servicio.
- Tests de integración para controladores con MockMvc y H2.

---

## 🧱 Stack

| Tecnología | Uso |
| --- | --- |
| Java 17 | Lenguaje principal |
| Spring Boot 3.5 | Base de la aplicación |
| Spring Web | Exposición de endpoints REST |
| Spring Data JPA | Acceso a datos |
| Jakarta Validation | Validación de requests |
| MySQL | Base de datos principal |
| H2 | Base de datos en memoria para tests |
| Maven | Gestión de dependencias y build |
| JUnit 5, Mockito, MockMvc | Testing |

---

## ⚙️ Funcionalidades

- Crear, listar, consultar, actualizar y eliminar tareas.
- Listado de tareas ordenado por fecha de creación descendente.
- Validación de campos obligatorios y tamaños máximos.
- DTOs de entrada y salida.
- Mapeo entre entidades y respuestas de API.
- Manejo global de excepciones.
- Tests unitarios y de integración.

---

## 📦 Modelo de tarea

| Campo | Tipo | Reglas |
| --- | --- | --- |
| `id` | `Long` | Generado automáticamente |
| `title` | `String` | Obligatorio, máximo 120 caracteres |
| `description` | `String` | Opcional, máximo 500 caracteres |
| `completed` | `Boolean` | Obligatorio |
| `dueDate` | `LocalDate` | Opcional, formato `YYYY-MM-DD` |
| `createdAt` | `LocalDateTime` | Generado automáticamente |

---

## 📁 Estructura del proyecto

```text
src
+-- main
|   +-- java/com/portfolio/taskapi
|   |   +-- controller
|   |   +-- dto
|   |   +-- entity
|   |   +-- exception
|   |   +-- mapper
|   |   +-- repository
|   |   +-- service
|   +-- resources
+-- test
    +-- java/com/portfolio/taskapi
    |   +-- controller
    |   +-- service
    +-- resources
```

---

## ▶️ Ejecutar en local

### Requisitos

- Java 17 o superior.
- MySQL en local.
- Maven Wrapper incluido en el proyecto.

### Configuración

Variables por defecto:

| Variable | Valor |
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

API disponible en:

```text
http://localhost:8080
```

---

## 🌐 Endpoints

| Método | Endpoint | Descripción | Respuesta |
| --- | --- | --- | --- |
| `GET` | `/tasks` | Listar tareas | `200 OK` |
| `GET` | `/tasks/{id}` | Obtener una tarea | `200 OK` / `404 Not Found` |
| `POST` | `/tasks` | Crear una tarea | `201 Created` / `400 Bad Request` |
| `PUT` | `/tasks/{id}` | Actualizar una tarea | `200 OK` / `400 Bad Request` / `404 Not Found` |
| `DELETE` | `/tasks/{id}` | Eliminar una tarea | `204 No Content` / `404 Not Found` |

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

---

## 🧯 Ejemplos de errores

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

---

## 🧪 Tests

El proyecto incluye:

- Tests unitarios para la capa de servicio.
- Tests de integración para controladores con MockMvc.
- Base de datos H2 en memoria para ejecutar la suite sin depender de MySQL.

```bash
./mvnw test
```

En Windows:

```powershell
.\mvnw.cmd test
```

---

## 🔮 Mejoras futuras

- Documentación con Swagger / OpenAPI.
- Paginación, filtros por estado y búsqueda por título.
- Dockerización con Docker Compose.
- Autenticación con Spring Security y JWT.
- Migraciones con Flyway o Liquibase.
- CI/CD con GitHub Actions.

---

## 👨‍💻 Autor

Iván Martínez<br>
Junior Backend Developer

👉 https://github.com/ivanml705-cell
