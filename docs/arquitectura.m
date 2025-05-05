# arquitectura del rpoyecto - Prueba DoubleV

##descripción General
Este proyecto es una API desarrollada con **Spring Boot** que permite la gestión de usuarios y tickets, incluyendo autenticación con **JWT**, persistencia en **H2** y ejecución en **Docker**.

## Estructura del Código
**Principales componentes del backend:**
- `UsuarioController.java` → Maneja las operaciones CRUD de usuarios.
- `TicketController.java` → Maneja las operaciones CRUD de tickets.
- `Usuario.java` → Entidad de usuario con `UUID`, `nombres`, `apellidos`, y fechas de creación.
- `Ticket.java` → Entidad de ticket con `UUID`, `descripcion`, `usuarioId`, `status`, y fechas.
- `JwtUtil.java` → Manejo de tokens JWT para autenticación.
- `SecurityConfig.java` → Configuración de seguridad para proteger endpoints.
- `Dockerfile` → Configuración para ejecutar la API en un contenedor Docker.

##Base de Datos
**use `H2` como base de datos en memoria para la prueba técnica.**
```yaml
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
