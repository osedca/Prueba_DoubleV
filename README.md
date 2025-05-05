# Prueba_DoubleV
# Backend para Gestión de Tickets

Este proyecto es una API REST desarrollada **Spring Boot** que permite gestionar **Usuarios** y **Tickets**, incorporando autenticación con **JWT**, seguridad con **Spring Security**, y ejecución con **Docker**.

## Tecnologías usadas
**Java Spring Boot** -desarrollo del backen 
**JWT** -autenticación y segurida 
**H2 Database** -Base de datos en memori 
**Docker** -ontenedor para despliegu 
**Swaggr/OpenAPI** -Documentación automátic 

### Inicio instelacion - Clonar el repositorio**
```bash
git clone https://github.com/osedca/Prueba_DoubleV.git
cd backend-springboot

### Como instalar localmente y ejecutar localmente**
mvn clean install
mvn spring-boot:run

### Como icorrer con docker
docker build -t prueba_doublev_springboot .
docker run -p 8081:8081 prueba_doublev_springboot

### Listado de Ednpoints

###Usuarios (`UsuarioController.java`)**
**Crear Usuario (`POST /usuario`)**  
```json
POST http://localhost:8081/usuario
{
  "nombres": "Oscar",
  "apellidos": "Camacho",
  "password": "123456"
}

###obtener listado usuaarios 
GET http://localhost:8081/usuario
Authorization: Bearer <TOKEN>

###Obtener usuario por ID
GET http://localhost:8081/usuario/bfc1c3e3-4a0a-4c6d-bf6f-29a8ff964d23
Authorization: Bearer <TOKEN>

### actualizar usuario con PUT
GET http://localhost:8081/usuario/bfc1c3e3-4a0a-4c6d-bf6f-29a8ff964d23
Authorization: Bearer <TOKEN>

PUT http://localhost:8081/usuario/bfc1c3e3-4a0a-4c6d-bf6f-29a8ff964d23
{
  "nombres": "Oscar E",
  "apellidos": "Camacho",
  "password": "nueva_clave"
}

### TICKETS
###Creacion
POST http://localhost:8081/ticket
{
  "descripcion": "Se requiere ayuda en reserva",
  "usuarioId": "bfc1c3e3-4a0a-4c6d-bf6f-29a8ff964d23",
  "status": "ABIERTO"
}

###Obtener lista tickets paginada
GET http://localhost:8081/ticket?page=1&size=10
Authorization: Bearer <TOKEN>

###Obtener ticket por id
GET http://localhost:8081/ticket/a72bb56b-2e38-487a-a3c8-b5874e8f6c90
Authorization: Bearer <TOKEN>

###Actualiza Ticket
PUT http://localhost:8081/ticket/a72bb56b-2e38-487a-a3c8-b5874e8f6c90
{
  "descripcion": "Actualizn del ticket.",
  "status": "CERRADO"
}

###Elimina ticket
DELETE http://localhost:8081/ticket/a72bb56b-2e38-487a-a3c8-b5874e8f6c90
Authorization: Bearer <TOKEN>

###Filtrar tickest Status
GET http://localhost:8081/ticket?status=ABIERTO
Authorization: Bearer <TOKEN>

###por usuario tickets
GET http://localhost:8081/ticket?usuarioId=bfc1c3e3-4a0a-4c6d-bf6f-29a8ff964d23
Authorization: Bearer <TOKEN>

###filtro combinando cirterios 
GET http://localhost:8081/ticket?status=CERRADO&usuarioId=bfc1c3e3-4a0a-4c6d-bf6f-29a8ff964d23
Authorization: Bearer <TOKEN>

###inclusion de tokens en solicitudes protegidas
Authorization: Bearer <TOKEN_GENERADO>

## Acceso a Swagger
http://localhost:8081/swagger-ui.html
