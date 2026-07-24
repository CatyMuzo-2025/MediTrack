## Universida de las Fuerzas Armadas- ESPE
## Programación Avanzada
## Examen Práctico Segundo Parcial
## Tarea Práctica: MediTrack — Servicio Reactivo de Citas Médicas con Pruebas Unitarias

MediTrack es una API REST desarrollada con *Spring Boot* y *Spring WebFlux* para la gestión de citas médicas. El proyecto implementa programación reactiva utilizando Mono y Flux, siguiendo una arquitectura por capas.

## Tecnologías

- Java 17
- Spring Boot
- Spring WebFlux
- Maven
- JUnit 5

## Funcionalidades

- Obtener todas las citas médicas.
- Buscar una cita por su identificador.
- Validar que una cita tenga un costo mayor a cero y al menos un correo de notificación.
- Manejo de excepciones mediante una excepción personalizada.

## Ejecución

1. Clonar el repositorio.
2. Abrir el proyecto en IntelliJ IDEA o cualquier IDE compatible.
3. Ejecutar la clase principal de Spring Boot o usar:

bash
mvn spring-boot:run


La aplicación estará disponible en:

http://localhost:8080


## Endpoints

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/appointments | Lista todas las citas. |
| GET | /api/appointments/{id} | Obtiene una cita por su ID. |

## Autor
Caterine Rocío Muzo Samueza
