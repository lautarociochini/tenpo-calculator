## Tenpo Challange

## Proyecto
- SpringBoot framework 
- Java 11.0.10
- PostgreSQL 14.2
- Spring Security y Spring Session

### Implementación
- Sign up, login y logout de usuarios
- Suma de 2 números
- Historial de llamados a la API

### Instalacion

#### Requerimientos
- _Instalar docker ([Desktop](https://www.docker.com/products/docker-desktop/))_
- _Instalar docker compose_

1. Clonar repositorio
2. Ejecutar servicio en consola con los comandos ``./mvnw clean package -DskipTests=true`` && ``docker-compose up --build``

### Uso 
#### Postman
Se adjunta una colección de postman para consumir la API.

### Documentación
#### Swagger
Doc del servicio: http://localhost:8080/swagger-ui.html

### Docker
Imagen de docker de la aplicación: https://hub.docker.com/r/lciochini92/challange

### Aclaraciones
- Se planteó una arquitectura en capas tradicional (Controller, Service, Repository).
- Contiene manejo de sesiones de usuario.
- Para manejar la autenticación del usuario se utiliza el header `X-Auth-Token` que es obtenido al realizar el login.
