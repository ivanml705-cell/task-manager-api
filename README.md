# Task API

REST API for task management built with Java 17, Spring Boot, Maven and MySQL.

## Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Validation
- MySQL
- Maven

## Project structure

- `controller`
- `entity`
- `repository`
- `service`
- `service.impl`

## Endpoints

- `GET /tasks`
- `GET /tasks/{id}`
- `POST /tasks`
- `PUT /tasks/{id}`
- `DELETE /tasks/{id}`

## Run locally

1. Create a MySQL database named `task_management` or use the default URL with `createDatabaseIfNotExist=true`.
2. Configure `DB_USERNAME` and `DB_PASSWORD` if needed.
3. Run `mvnw.cmd spring-boot:run` on Windows or `./mvnw spring-boot:run` on Unix systems.

Tests use H2 in memory so the project can be validated without a local MySQL instance.
