Las especificaciones OpenAPI han evolucionado a lo largo del tiempo, con varias versiones lanzadas para mejorar la funcionalidad, la flexibilidad y la facilidad de uso. A continuación, se detallan las diferencias clave entre las versiones más significativas: 2.0 (anteriormente conocida como Swagger) y 3.0.

### OpenAPI 2.0 (Swagger)
**Características Principales:**
1. **Formato de Documento:**
    - **Formato:** JSON o YAML.
    - **Campos Clave:** `swagger`, `info`, `host`, `basePath`, `schemes`, `paths`, `definitions`, `parameters`, `responses`, `securityDefinitions`.

2. **Estructura:**
    - **`swagger`:** Versión de la especificación (siempre "2.0").
    - **`info`:** Información sobre la API, como el título, la descripción y la versión.
    - **`host`:** Host (dominio) de la API.
    - **`basePath`:** Ruta base para todos los endpoints.
    - **`schemes`:** Protocolos de transferencia soportados (http, https, ws, wss).
    - **`paths`:** Rutas y operaciones de la API.
    - **`definitions`:** Esquemas reutilizables para objetos de datos.
    - **`parameters`:** Parámetros reutilizables.
    - **`responses`:** Respuestas reutilizables.
    - **`securityDefinitions`:** Esquemas de seguridad que pueden ser usados en la API.

3. **Desventajas:**
    - Limitaciones en la descripción de respuestas anidadas y tipos de contenido.
    - Soporte limitado para composición y reutilización de esquemas.
    - Menos flexibilidad en la definición de seguridad y autenticación.

### OpenAPI 3.0
**Características Principales:**
1. **Formato de Documento:**
    - **Formato:** JSON o YAML.
    - **Campos Clave:** `openapi`, `info`, `servers`, `paths`, `components`, `security`, `tags`, `externalDocs`.

2. **Estructura:**
    - **`openapi`:** Versión de la especificación (por ejemplo, "3.0.0").
    - **`info`:** Información sobre la API, como el título, la descripción y la versión.
    - **`servers`:** Lista de servidores base para la API.
    - **`paths`:** Rutas y operaciones de la API.
    - **`components`:** Contenedores para definiciones reutilizables (esquemas, respuestas, parámetros, ejemplos, requestBodies, headers, securitySchemes, links, callbacks).
    - **`security`:** Configuración de seguridad aplicable globalmente.
    - **`tags`:** Metadatos para organizar las operaciones.
    - **`externalDocs`:** Información adicional externa.

3. **Mejoras y Nuevas Funcionalidades:**
    - **Soporte Mejorado para Content Types:**
        - Descripciones detalladas de múltiples tipos de contenido para solicitudes y respuestas.
    - **Request Bodies:**
        - Separación clara de parámetros y cuerpos de solicitudes (`requestBody`).
    - **Components:**
        - Unificación de elementos reutilizables (`schemas`, `responses`, `parameters`, `examples`, `requestBodies`, `headers`, `securitySchemes`, `links`, `callbacks`).
    - **Callbacks:**
        - Definición de callbacks para describir solicitudes asincrónicas.
    - **Links:**
        - Habilidad para describir relaciones entre operaciones (enlaces entre diferentes endpoints).
    - **Mejor Descripción de Seguridad:**
        - Soporte para OAuth2, JWT, y más configuraciones de seguridad.
    - **Descripciones de Servidores:**
        - Definición de múltiples servidores con variables para la configuración dinámica de la URL.

4. **Ventajas:**
    - Mayor flexibilidad y poder descriptivo.
    - Mejor reutilización de componentes y consistencia en la documentación.
    - Mejora en la descripción de seguridad y autenticación.
    - Mejor soporte para especificaciones de APIs modernas y RESTful.

### Comparación entre OpenAPI 2.0 y 3.0

| Característica                  | OpenAPI 2.0 (Swagger)           | OpenAPI 3.0                       |
|---------------------------------|---------------------------------|-----------------------------------|
| **Formato del Documento**       | JSON o YAML                     | JSON o YAML                       |
| **Campo de Versión**            | `swagger`                       | `openapi`                         |
| **Información de API**          | `info`                          | `info`                            |
| **Host/BasePath**               | `host`, `basePath`              | `servers`                         |
| **Protocolos**                  | `schemes`                       | `servers`                         |
| **Rutas y Operaciones**         | `paths`                         | `paths`                           |
| **Esquemas Reutilizables**      | `definitions`                   | `components.schemas`              |
| **Parámetros Reutilizables**    | `parameters`                    | `components.parameters`           |
| **Respuestas Reutilizables**    | `responses`                     | `components.responses`            |
| **Seguridad**                   | `securityDefinitions`           | `components.securitySchemes`      |
| **Cuerpos de Solicitud**        | Mezclado con parámetros         | `requestBody` separado            |
| **Callbacks**                   | No soportado                    | Soportado (`callbacks`)           |
| **Enlaces entre Operaciones**   | No soportado                    | Soportado (`links`)               |
| **Documentación Externa**       | Limitado                        | `externalDocs`                    |

# SAMPLE
Claro, vamos a diseñar un ejemplo más complejo para una API tipo Lyft utilizando Swagger/OpenAPI 3.0. Este ejemplo incluirá múltiples endpoints y operaciones para gestionar viajes, usuarios y vehículos.

### Definicion inicial del API
Claro, aquí tienes la definición del API tipo Lyft en texto simple:

### Información General de la API
- **Título:** API de Tipo Lyft
- **Descripción:** Esta es una API para un servicio de transporte similar a Lyft.
- **Versión:** 1.0.0
- **Servidores:**
    - **Servidor Principal de Producción:** https://api.tu-empresa.com/v1
    - **Servidor de Sandbox para Pruebas:** https://sandbox.tu-empresa.com/v1

### Componentes Reutilizables

#### Esquema de Viaje (Ride)
- **id:** Identificador del viaje (ejemplo: "ride_123")
- **userId:** Identificador del usuario (ejemplo: "user_456")
- **driverId:** Identificador del conductor (ejemplo: "driver_789")
- **vehicleId:** Identificador del vehículo (ejemplo: "vehicle_101")
- **startLocation:** Ubicación de inicio (ver Esquema de Ubicación)
- **endLocation:** Ubicación de destino (ver Esquema de Ubicación)
- **status:** Estado del viaje (valores posibles: requested, in_progress, completed, cancelled; ejemplo: "requested")
- **fare:** Tarifa del viaje (formato float; ejemplo: 25.75)

#### Esquema de Usuario (User)
- **id:** Identificador del usuario (ejemplo: "user_456")
- **name:** Nombre del usuario (ejemplo: "Juan Perez")
- **email:** Correo electrónico del usuario (ejemplo: "juan.perez@example.com")
- **phone:** Número de teléfono del usuario (ejemplo: "+521234567890")

#### Esquema de Conductor (Driver)
- **id:** Identificador del conductor (ejemplo: "driver_789")
- **name:** Nombre del conductor (ejemplo: "Carlos Lopez")
- **rating:** Calificación del conductor (formato float; ejemplo: 4.9)

#### Esquema de Vehículo (Vehicle)
- **id:** Identificador del vehículo (ejemplo: "vehicle_101")
- **make:** Marca del vehículo (ejemplo: "Toyota")
- **model:** Modelo del vehículo (ejemplo: "Camry")
- **year:** Año del vehículo (formato integer; ejemplo: 2020)
- **licensePlate:** Placa del vehículo (ejemplo: "ABC1234")

#### Esquema de Ubicación (Location)
- **latitude:** Latitud (formato float; ejemplo: 37.7749)
- **longitude:** Longitud (formato float; ejemplo: -122.4194)

### Endpoints y Operaciones

#### Endpoints para Gestionar Viajes (Rides)

**Solicitar un Nuevo Viaje**
- **Método:** POST
- **URL:** /rides
- **Cuerpo de la Solicitud:**
    - **userId:** Identificador del usuario (ejemplo: "user_456")
    - **startLocation:** Ubicación de inicio (ver Esquema de Ubicación)
    - **endLocation:** Ubicación de destino (ver Esquema de Ubicación)
- **Respuesta:**
    - **Código 201:** Viaje creado exitosamente (ver Esquema de Viaje)

**Obtener Todos los Viajes**
- **Método:** GET
- **URL:** /rides
- **Respuesta:**
    - **Código 200:** Lista de viajes (array de Esquema de Viaje)

**Obtener un Viaje por ID**
- **Método:** GET
- **URL:** /rides/{rideId}
- **Parámetro de Ruta:**
    - **rideId:** Identificador del viaje (ejemplo: "ride_123")
- **Respuesta:**
    - **Código 200:** Detalles del viaje (ver Esquema de Viaje)

**Actualizar el Estado de un Viaje**
- **Método:** PUT
- **URL:** /rides/{rideId}
- **Parámetro de Ruta:**
    - **rideId:** Identificador del viaje (ejemplo: "ride_123")
- **Cuerpo de la Solicitud:**
    - **status:** Estado del viaje (valores posibles: requested, in_progress, completed, cancelled; ejemplo: "in_progress")
- **Respuesta:**
    - **Código 200:** Viaje actualizado exitosamente (ver Esquema de Viaje)

#### Endpoints para Gestionar Usuarios (Users)

**Crear un Nuevo Usuario**
- **Método:** POST
- **URL:** /users
- **Cuerpo de la Solicitud:**
    - Ver Esquema de Usuario
- **Respuesta:**
    - **Código 201:** Usuario creado exitosamente (ver Esquema de Usuario)

**Obtener Todos los Usuarios**
- **Método:** GET
- **URL:** /users
- **Respuesta:**
    - **Código 200:** Lista de usuarios (array de Esquema de Usuario)

**Obtener un Usuario por ID**
- **Método:** GET
- **URL:** /users/{userId}
- **Parámetro de Ruta:**
    - **userId:** Identificador del usuario (ejemplo: "user_456")
- **Respuesta:**
    - **Código 200:** Detalles del usuario (ver Esquema de Usuario)

**Actualizar un Usuario**
- **Método:** PUT
- **URL:** /users/{userId}
- **Parámetro de Ruta:**
    - **userId:** Identificador del usuario (ejemplo: "user_456")
- **Cuerpo de la Solicitud:**
    - Ver Esquema de Usuario
- **Respuesta:**
    - **Código 200:** Usuario actualizado exitosamente (ver Esquema de Usuario)

#### Endpoints para Gestionar Conductores (Drivers) y Vehículos (Vehicles)

**Crear un Nuevo Conductor**
- **Método:** POST
- **URL:** /drivers
- **Cuerpo de la Solicitud:**
    - Ver Esquema de Conductor
- **Respuesta:**
    - **Código 201:** Conductor creado exitosamente (ver Esquema de Conductor)

**Obtener Todos los Conductores**
- **Método:** GET
- **URL:** /drivers
- **Respuesta:**
    - **Código 200:** Lista de conductores (array de Esquema de Conductor)

**Obtener un Conductor por ID**
- **Método:** GET
- **URL:** /drivers/{driverId}
- **Parámetro de Ruta:**
    - **driverId:** Identificador del conductor (ejemplo: "driver_789")
- **Respuesta:**
    - **Código 200:** Detalles del conductor (ver Esquema de Conductor)

**Actualizar un Conductor**
- **Método:** PUT
- **URL:** /drivers/{driverId}
- **Parámetro de Ruta:**
    - **driverId:** Identificador del conductor (ejemplo: "driver_789")
- **Cuerpo de la Solicitud:**
    - Ver Esquema de Conductor
- **Respuesta:**
    - **Código 200:** Conductor actualizado exitosamente (ver Esquema de Conductor)

**Crear un Nuevo Vehículo**
- **Método:** POST
- **URL:** /vehicles
- **Cuerpo de la Solicitud:**
    - Ver Esquema de Vehículo
- **Respuesta:**
    - **Código 201:** Vehículo creado exitosamente (ver Esquema de Vehículo)

**Obtener Todos los Vehículos**
- **Método:** GET
- **URL:** /vehicles
- **Respuesta:**
    - **Código 200:** Lista de vehículos (array de Esquema de Vehículo)

**Obtener un Vehículo por ID**
- **Método:** GET
- **URL:** /vehicles/{vehicleId}
- **Parámetro de Ruta:**
    - **vehicleId:** Identificador del vehículo (ejemplo: "vehicle_101")
- **Respuesta:**
    - **Código 200:** Detalles del vehículo (ver Esquema de Vehículo)

**Actualizar un Vehículo**
- **Método:** PUT
- **URL:** /vehicles/{vehicleId}
- **Parámetro de Ruta:**
    - **vehicleId:** Identificador del vehículo (ejemplo: "vehicle_101")
- **Cuerpo de la Solicitud:**
    - Ver Esquema de Vehículo
- **Respuesta:**
    - **Código 200:** Vehículo actualizado exitosamente (ver Esquema de Vehículo)

### Especificación de la API Lyft con Swagger/OpenAPI 3.0

#### 1. Información General de la API
```yaml
openapi: 3.0.0
info:
  title: API de Tipo Lyft
  description: Esta es una API para un servicio de transporte similar a Lyft.
  version: 1.0.0
servers:
  - url: https://api.tu-empresa.com/v1
    description: Servidor principal de producción
  - url: https://sandbox.tu-empresa.com/v1
    description: Servidor de sandbox para pruebas
```

#### 2. Definiciones de Componentes Reutilizables
```yaml
components:
  schemas:
    Ride:
      type: object
      properties:
        id:
          type: string
          example: "ride_123"
        userId:
          type: string
          example: "user_456"
        driverId:
          type: string
          example: "driver_789"
        vehicleId:
          type: string
          example: "vehicle_101"
        startLocation:
          $ref: '#/components/schemas/Location'
        endLocation:
          $ref: '#/components/schemas/Location'
        status:
          type: string
          enum: [requested, in_progress, completed, cancelled]
          example: "requested"
        fare:
          type: number
          format: float
          example: 25.75
    User:
      type: object
      properties:
        id:
          type: string
          example: "user_456"
        name:
          type: string
          example: "Juan Perez"
        email:
          type: string
          example: "juan.perez@example.com"
        phone:
          type: string
          example: "+521234567890"
    Driver:
      type: object
      properties:
        id:
          type: string
          example: "driver_789"
        name:
          type: string
          example: "Carlos Lopez"
        rating:
          type: number
          format: float
          example: 4.9
    Vehicle:
      type: object
      properties:
        id:
          type: string
          example: "vehicle_101"
        make:
          type: string
          example: "Toyota"
        model:
          type: string
          example: "Camry"
        year:
          type: integer
          example: 2020
        licensePlate:
          type: string
          example: "ABC1234"
    Location:
      type: object
      properties:
        latitude:
          type: number
          format: float
          example: 37.7749
        longitude:
          type: number
          format: float
          example: -122.4194
```

#### 3. Endpoints y Operaciones

##### Rutas para Gestionar Viajes (Rides)
```yaml
paths:
  /rides:
    post:
      summary: Solicitar un nuevo viaje
      requestBody:
        required: true
        content:
          application/json:
            schema:
              type: object
              properties:
                userId:
                  type: string
                  example: "user_456"
                startLocation:
                  $ref: '#/components/schemas/Location'
                endLocation:
                  $ref: '#/components/schemas/Location'
      responses:
        '201':
          description: Viaje creado exitosamente
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Ride'
    get:
      summary: Obtener todos los viajes
      responses:
        '200':
          description: Lista de viajes
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Ride'
  /rides/{rideId}:
    get:
      summary: Obtener un viaje por ID
      parameters:
        - name: rideId
          in: path
          required: true
          schema:
            type: string
            example: "ride_123"
      responses:
        '200':
          description: Detalles del viaje
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Ride'
    put:
      summary: Actualizar el estado de un viaje
      parameters:
        - name: rideId
          in: path
          required: true
          schema:
            type: string
            example: "ride_123"
      requestBody:
        required: true
        content:
          application/json:
            schema:
              type: object
              properties:
                status:
                  type: string
                  enum: [requested, in_progress, completed, cancelled]
                  example: "in_progress"
      responses:
        '200':
          description: Viaje actualizado exitosamente
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Ride'
```

##### Rutas para Gestionar Usuarios (Users)
```yaml
  /users:
    post:
      summary: Crear un nuevo usuario
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/User'
      responses:
        '201':
          description: Usuario creado exitosamente
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/User'
    get:
      summary: Obtener todos los usuarios
      responses:
        '200':
          description: Lista de usuarios
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/User'
  /users/{userId}:
    get:
      summary: Obtener un usuario por ID
      parameters:
        - name: userId
          in: path
          required: true
          schema:
            type: string
            example: "user_456"
      responses:
        '200':
          description: Detalles del usuario
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/User'
    put:
      summary: Actualizar un usuario
      parameters:
        - name: userId
          in: path
          required: true
          schema:
            type: string
            example: "user_456"
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/User'
      responses:
        '200':
          description: Usuario actualizado exitosamente
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/User'
```

##### Rutas para Gestionar Conductores (Drivers) y Vehículos (Vehicles)
```yaml
  /drivers:
    post:
      summary: Crear un nuevo conductor
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Driver'
      responses:
        '201':
          description: Conductor creado exitosamente
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Driver'
    get:
      summary: Obtener todos los conductores
      responses:
        '200':
          description: Lista de conductores
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Driver'
  /drivers/{driverId}:
    get:
      summary: Obtener un conductor por ID
      parameters:
        - name: driverId
          in: path
          required: true
          schema:
            type: string
            example: "driver_789"
      responses:
        '200':
          description: Detalles del conductor
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Driver'
    put:
      summary: Actualizar un conductor
      parameters:
        - name: driverId
          in: path
          required: true
          schema:
            type: string
            example: "driver_789"
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Driver'
      responses:
        '200':
          description: Conductor actualizado exitosamente
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Driver'
  /vehicles:
    post:
      summary: Crear un nuevo vehículo
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Vehicle'
      responses:
        '201':
          description: Vehículo creado exitosamente
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Vehicle'
    get:
      summary: Obtener todos los vehículos
      responses:
        '200':
          description: Lista de vehículos
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Vehicle'
  /vehicles/{vehicleId}:
    get:
      summary: Obtener un vehículo por ID
      parameters:
        - name: vehicleId
          in: path
          required: true
          schema:
            type: string
            example: "vehicle_101"
      responses:
        '200':
          description: Detalles del vehículo
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Vehicle'
    put:
      summary

: Actualizar un vehículo
      parameters:
        - name: vehicleId
          in: path
          required: true
          schema:
            type: string
            example: "vehicle_101"
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Vehicle'
      responses:
        '200':
          description: Vehículo actualizado exitosamente
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Vehicle'
```
## Versionado
Para agregar una versión 2 (`/v2/`) para los endpoints relacionados con `/rides`, puedes definir un nuevo servidor en la sección `servers` y especificar las rutas específicas para `/v2/rides`. Aquí está la diferencia que deberías agregar:

### Diferencia para Agregar la Versión 2

#### Actualización en la Sección `servers`
```yaml
servers:
  - url: https://api.tu-empresa.com/v1
    description: Servidor principal de producción v1
  - url: https://sandbox.tu-empresa.com/v1
    description: Servidor de sandbox para pruebas v1
  - url: https://api.tu-empresa.com/v2
    description: Servidor principal de producción v2
  - url: https://sandbox.tu-empresa.com/v2
    description: Servidor de sandbox para pruebas v2
```

#### Endpoints para la Versión 2 de `rides`
```yaml
paths:
  /v2/rides:
    post:
      summary: Solicitar un nuevo viaje
      requestBody:
        required: true
        content:
          application/json:
            schema:
              type: object
              properties:
                userId:
                  type: string
                  example: "user_456"
                startLocation:
                  $ref: '#/components/schemas/Location'
                endLocation:
                  $ref: '#/components/schemas/Location'
      responses:
        '201':
          description: Viaje creado exitosamente
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Ride'
    get:
      summary: Obtener todos los viajes
      responses:
        '200':
          description: Lista de viajes
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Ride'
  /v2/rides/{rideId}:
    get:
      summary: Obtener un viaje por ID
      parameters:
        - name: rideId
          in: path
          required: true
          schema:
            type: string
            example: "ride_123"
      responses:
        '200':
          description: Detalles del viaje
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Ride'
    put:
      summary: Actualizar el estado de un viaje
      parameters:
        - name: rideId
          in: path
          required: true
          schema:
            type: string
            example: "ride_123"
      requestBody:
        required: true
        content:
          application/json:
            schema:
              type: object
              properties:
                status:
                  type: string
                  enum: [requested, in_progress, completed, cancelled]
                  example: "in_progress"
      responses:
        '200':
          description: Viaje actualizado exitosamente
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Ride'
```

### Explicación

1. **Servidores:**
    - Añadir nuevos servidores para la versión 2 (`/v2`), tanto para producción como para pruebas (`sandbox`).

2. **Paths:**
    - Definir los endpoints bajo la nueva ruta `/v2/rides` y `/v2/rides/{rideId}`.
    - Puedes copiar la estructura de los endpoints de `/v1/rides` y adaptarlos según sea necesario para la nueva versión.
### Codegen

[Swagger CodeGen SDK](https://github.com/swagger-api/swagger-codegen)

```shell
java -jar swagger-codegen-cli.jar help
```

```shell
java -jar swagger-codegen-cli.jar langs
```
### Local
```shell
java -jar swagger-codegen-cli.jar generate -i lyfto-1-resolved.yaml -l php -o php_api_client
```

### Remoto
```shell
java -jar swagger-codegen-cli.jar generate -i https://petstore.swagger.io/v2/swagger.json \
   -l php \
   -o /var/tmp/php_api_client
```