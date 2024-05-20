# Authentication and Authorization Techniques

## JSON Web Tokens (JWT)

**Definición:**
JSON Web Tokens (JWT) es un estándar abierto (RFC 7519) para la creación de tokens de acceso que permiten la transmisión de datos entre dos partes de manera segura y compacta. Estos tokens se utilizan comúnmente para autenticación y autorización en aplicaciones web.

### Estructura de un JWT

Un JWT está compuesto por tres partes, separadas por puntos (`.`):
1. **Header (Encabezado):**
   - Contiene información sobre el tipo de token y el algoritmo de cifrado utilizado.
   - **Ejemplo:**
     ```json
     {
       "alg": "HS256",
       "typ": "JWT"
     }
     ```

2. **Payload (Carga Útil):**
   - Contiene las declaraciones (claims), que son las informaciones que se desean transmitir. Pueden ser datos predefinidos como `iss` (emisor), `exp` (expiración), y datos personalizados.
   - **Ejemplo:**
     ```json
     {
       "sub": "1234567890",
       "name": "John Doe",
       "admin": true
     }
     ```

3. **Signature (Firma):**
   - Se crea al codificar el encabezado y la carga útil y firmarlos con un secreto o una clave privada utilizando el algoritmo especificado en el encabezado.
   - **Ejemplo:**
     ```
     HMACSHA256(
       base64UrlEncode(header) + "." + base64UrlEncode(payload),
       secret
     )
     ```

### Ejemplo de un JWT

```plaintext
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

### Uso de JWT

1. **Autenticación:**
   - Al iniciar sesión, el servidor genera un JWT y lo envía al cliente.
   - El cliente almacena el JWT (normalmente en localStorage o una cookie) y lo envía en las solicitudes posteriores (por ejemplo, en el encabezado `Authorization: Bearer <token>`).
   - El servidor verifica la firma del JWT para autenticar al usuario.

2. **Autorización:**
   - JWT se utiliza para verificar si el usuario tiene permisos para acceder a ciertos recursos o realizar ciertas acciones.
```javascript
const jwt = require("jsonwebtoken");
try {
  const payload = jwt.verify(token, "your-256-bit-secret");
  console.log(payload);
} catch (e) {
  console.error("Invalid Token");
}

```
### Ventajas de JWT

1. **Compacto:**
   - Los JWT son pequeños y se pueden transmitir fácilmente a través de URLs, encabezados HTTP o dentro de un cuerpo de POST.

2. **Autocontenido:**
   - Contienen toda la información necesaria sobre el usuario, eliminando la necesidad de consultas adicionales a la base de datos en cada solicitud.

3. **Seguro:**
   - La firma asegura que el contenido no ha sido alterado. Solo el servidor que tiene la clave secreta puede generar y validar la firma.

### Buenas Prácticas de Seguridad con JWT

1. **Cifrado:**
   - Asegura la confidencialidad de los datos sensibles cifrando el JWT (JWE - JSON Web Encryption).

2. **Almacenamiento Seguro:**
   - Almacena el JWT de manera segura en el cliente. Evita almacenarlo en localStorage si el contenido es sensible. Considera usar cookies con atributos `HttpOnly` y `Secure`.

3. **Expiración y Renovación:**
   - Configura tiempos de expiración (`exp`) para los tokens y proporciona mecanismos para su renovación.

4. **Verificación del Emisor y el Público:**
   - Verifica los claims `iss` (emisor) y `aud` (audiencia) para asegurarte de que el token fue emitido por una fuente confiable y es para el público esperado.

5. **Algoritmos Seguros:**
   - Usa algoritmos de cifrado seguros como `HS256` o `RS256`.

### Resumen:

JSON Web Tokens (JWT) son una forma compacta, autocontenida y segura de transmitir información entre dos partes. Son ampliamente utilizados para autenticación y autorización en aplicaciones web. Implementar buenas prácticas de seguridad al utilizar JWT es esencial para proteger la aplicación y los datos del usuario.

## Usando Servicios de Directorio y Soluciones de Autenticación Gestionada

**Definición:**
Los servicios de directorio y las soluciones de autenticación gestionada proporcionan una forma centralizada de gestionar usuarios, autenticación y autorización en una organización. Estas soluciones permiten a las organizaciones implementar políticas de seguridad consistentes, simplificar la administración de usuarios y mejorar la seguridad general.

### ¿Por qué es importante?

1. **Centralización:**
   - Facilita la administración de usuarios y permisos desde un único punto.
   - Simplifica la gestión de contraseñas y políticas de seguridad.

2. **Seguridad Mejorada:**
   - Implementa políticas de seguridad consistentes en toda la organización.
   - Mejora la protección contra accesos no autorizados.

3. **Escalabilidad:**
   - Facilita la adición o eliminación de usuarios a medida que la organización crece o cambia.
   - Proporciona una solución escalable para grandes organizaciones.

### Servicios de Directorio Comunes

1. **Active Directory (AD):**
   - Proporcionado por Microsoft, AD es una solución popular para la gestión de identidades y acceso.
   - Permite la autenticación y autorización centralizada en redes basadas en Windows.

2. **LDAP (Lightweight Directory Access Protocol):**
   - Un protocolo estándar para acceder y gestionar servicios de directorio.
   - Utilizado por diversas implementaciones, incluyendo OpenLDAP y AD.

### Soluciones de Autenticación Gestionada

1. **Azure Active Directory (Azure AD):**
   - Un servicio de gestión de identidades y acceso basado en la nube proporcionado por Microsoft.
   - Integra autenticación multifactor (MFA), inicio de sesión único (SSO) y gestión de identidades.

2. **Okta:**
   - Una plataforma de gestión de identidades y acceso que ofrece autenticación multifactor, SSO y gestión de usuarios.
   - Facilita la integración con diversas aplicaciones y servicios en la nube.

3. **Auth0:**
   - Un servicio de autenticación y autorización que proporciona autenticación multifactor, SSO y gestión de identidades.
   - Permite la integración con múltiples plataformas y lenguajes de programación.

4. **AWS Cognito:**
   - Un servicio de autenticación, autorización y gestión de usuarios proporcionado por Amazon Web Services.
   - Permite añadir registro de usuario, inicio de sesión y control de acceso a aplicaciones web y móviles.
   - Soporta MFA, SSO y la federación con otros proveedores de identidad (por ejemplo, Google, Facebook, SAML).

### Ejemplos de Uso

1. **Autenticación Centralizada:**
   - Utilizar AD, Azure AD, o AWS Cognito para gestionar la autenticación de usuarios en todas las aplicaciones y servicios de una organización.
   - Los usuarios inician sesión una vez y obtienen acceso a múltiples recursos sin necesidad de autenticarse nuevamente.

2. **Autenticación Multifactor (MFA):**
   - Implementar MFA con Azure AD, Okta, Auth0, o AWS Cognito para agregar una capa adicional de seguridad.
   - Requerir un segundo factor de autenticación, como un código enviado al teléfono móvil del usuario, además de la contraseña.

3. **Inicio de Sesión Único (SSO):**
   - Utilizar SSO para permitir a los usuarios acceder a múltiples aplicaciones con una sola autenticación.
   - Configurar SSO en Okta o AWS Cognito para aplicaciones en la nube como Salesforce, Google Workspace y Office 365.

### Beneficios

1. **Seguridad Mejorada:**
   - Implementar políticas de seguridad uniformes y efectivas en toda la organización.
   - Reducir el riesgo de brechas de seguridad mediante MFA y SSO.

2. **Eficiencia Administrativa:**
   - Reducir la carga administrativa al gestionar usuarios y permisos desde un único punto.
   - Facilitar la incorporación y baja de empleados mediante la gestión centralizada de identidades.

3. **Mejora de la Experiencia del Usuario:**
   - Simplificar el proceso de inicio de sesión para los usuarios mediante SSO.
   - Aumentar la satisfacción del usuario al reducir la necesidad de recordar múltiples contraseñas.

### Resumen

Usar servicios de directorio y soluciones de autenticación gestionada, como AWS Cognito, mejora la seguridad, simplifica la administración de usuarios y permite políticas de seguridad consistentes en una organización. Implementar autenticación centralizada, MFA y SSO proporciona una mejor experiencia para los usuarios y refuerza la protección contra accesos no autorizados.

## Mejores Prácticas de Gestión de Sesiones

**Definición:**
La gestión de sesiones es el proceso de manejar y proteger las interacciones de un usuario con una aplicación web durante un periodo de tiempo. Las sesiones permiten a las aplicaciones recordar a los usuarios y sus acciones mientras navegan por el sitio web.

### ¿Por qué es importante?

Una gestión de sesiones inadecuada puede llevar a varias vulnerabilidades de seguridad, tales como el secuestro de sesiones, las fijaciones de sesión, y otros tipos de ataques que comprometen la confidencialidad y la integridad de la información del usuario.

### Mejores Prácticas de Gestión de Sesiones

1. **Generación Segura de Identificadores de Sesión:**
   - Utiliza identificadores de sesión largos y aleatorios para evitar que sean adivinados por atacantes.
   - Genera los identificadores de sesión utilizando un generador de números aleatorios criptográficamente seguro.

2. **Almacenamiento Seguro de Identificadores de Sesión:**
   - Almacena los identificadores de sesión de forma segura en el navegador del cliente, preferiblemente en cookies con los atributos `HttpOnly` y `Secure`.
   - Evita exponer los identificadores de sesión en URLs, cookies o almacenamiento local.

3. **Configuración de Cookies de Sesión:**
   - Configura las cookies de sesión con los atributos `HttpOnly` para prevenir el acceso de JavaScript a la cookie.
   - Utiliza el atributo `Secure` para asegurar que las cookies solo se envíen a través de conexiones HTTPS.
   - Configura el atributo `SameSite` para mitigar ataques CSRF (Cross-Site Request Forgery).

#### Atributo `SameSite` para Cookies

**Definición:**
El atributo `SameSite` es una opción de seguridad para las cookies que permite controlar cuándo las cookies se envían con solicitudes de navegación y cómo se manejan en diferentes contextos de sitio. Este atributo ayuda a mitigar ciertos tipos de ataques, como el Cross-Site Request Forgery (CSRF).

##### Valores del Atributo `SameSite`
- **`SameSite=Strict`:**
   - Las cookies con este atributo no se envían en absoluto con solicitudes de terceros. Solo se envían si la solicitud se origina desde el mismo sitio que creó la cookie.
   - **Ejemplo:** Si un usuario está navegando en `https://example.com`, las cookies con `SameSite=Strict` no se enviarán cuando el usuario haga clic en un enlace en un sitio diferente que lo lleve a `https://example.com`.

- **`SameSite=Lax`:**
   - Las cookies con este atributo se envían con solicitudes de navegación de primer nivel y con solicitudes GET iniciadas por terceros, pero no se envían en otros tipos de solicitudes de terceros (como POST).
   - **Ejemplo:** Si un usuario hace clic en un enlace en un correo electrónico que lo lleva a `https://example.com`, las cookies con `SameSite=Lax` se enviarán con esa solicitud.

- **`SameSite=None`:**
   - Las cookies con este atributo se envían con todas las solicitudes, independientemente del contexto del sitio.
   - **Nota Importante:** Para utilizar `SameSite=None`, las cookies deben tener también el atributo `Secure`, lo que significa que solo se enviarán a través de conexiones HTTPS.
   - **Ejemplo:** Las cookies con `SameSite=None` se enviarán con todas las solicitudes, ya sean de primer nivel o de terceros, siempre que se utilice HTTPS.

4. **Expiración de Sesiones:**
   - Establece un tiempo de expiración para las sesiones inactivas, obligando a los usuarios a volver a autenticarse después de un período de inactividad.
   - Implementa la expiración de sesiones absolutas para finalizar la sesión después de un tiempo determinado, independientemente de la actividad.

5. **Regeneración de Identificadores de Sesión:**
   - Regenera el identificador de sesión después de iniciar sesión y al realizar operaciones sensibles, como cambios de contraseña o actualizaciones de perfil, para prevenir ataques de fijación de sesión.

6. **Validación y Control de Sesiones:**
   - Implementa controles para validar la autenticidad de las sesiones, como la verificación de la IP y el agente de usuario del cliente.
   - Monitorea y registra las actividades de la sesión para detectar comportamientos anómalos o sospechosos.

7. **Protección contra Secuestro de Sesiones:**
   - Utiliza autenticación multifactor (MFA) para agregar una capa adicional de seguridad a las sesiones.
   - Implementa mecanismos para detectar y prevenir el uso concurrente de la misma sesión desde diferentes ubicaciones.

8. **Cierre Seguro de Sesiones:**
   - Proporciona una funcionalidad para que los usuarios cierren sesión de manera segura.
   - Invalida la sesión en el servidor al cerrar sesión y elimina las cookies de sesión en el cliente. 

### Resumen

Implementar las mejores prácticas de gestión de sesiones es esencial para proteger la seguridad y la privacidad de los usuarios en aplicaciones web. Esto incluye la generación y almacenamiento seguro de identificadores de sesión, la configuración adecuada de cookies, la expiración y regeneración de sesiones, y la protección contra secuestro de sesiones. Seguir estas prácticas ayuda a asegurar que las sesiones sean manejadas de manera segura y eficiente.

## SAST (Static Application Security Testing) y DAST (Dynamic Application Security Testing)

**Definición:**
SAST y DAST son dos enfoques complementarios para probar la seguridad de las aplicaciones. Ambos se utilizan para identificar vulnerabilidades, pero lo hacen de manera diferente y en diferentes etapas del ciclo de vida del desarrollo de software.

### SAST (Static Application Security Testing)

#### ¿Qué es SAST?
SAST es una metodología de prueba de seguridad que analiza el código fuente, el bytecode o el binario de una aplicación para encontrar vulnerabilidades de seguridad sin ejecutarla. Se realiza en una etapa temprana del ciclo de desarrollo, generalmente durante la codificación o la construcción.

#### ¿Cómo Funciona SAST?
1. **Análisis Estático:**
   - Examina el código fuente, bytecode o binario en busca de patrones que indiquen posibles vulnerabilidades.
2. **Identificación de Vulnerabilidades:**
   - Detecta problemas como inyecciones SQL, desbordamientos de búfer, exposición de datos sensibles, y más.
3. **Reportes Detallados:**
   - Genera reportes que indican las líneas de código donde se encuentran las vulnerabilidades y su gravedad.

#### Ventajas de SAST
- **Detección Temprana:**
   - Identifica vulnerabilidades en las primeras etapas del desarrollo, lo que permite corregirlas antes de que se integren en el producto final.
- **Cobertura Completa:**
   - Analiza todo el código, incluyendo partes que no se ejecutan durante las pruebas dinámicas.
- **Integración en CI/CD:**
   - Puede integrarse en los procesos de integración y entrega continua para análisis automático.

#### Desventajas de SAST
- **Falsos Positivos:**
   - Puede generar falsos positivos que requieren revisión manual.
- **Tiempo de Análisis:**
   - El análisis completo puede ser lento, especialmente en proyectos grandes.

#### Herramientas Comunes de SAST
- **SonarQube**
- **Fortify Static Code Analyzer**
- **Checkmarx**
- **Veracode**

### DAST (Dynamic Application Security Testing)

#### ¿Qué es DAST?
DAST es una metodología de prueba de seguridad que analiza una aplicación en ejecución para encontrar vulnerabilidades. Se realiza en una etapa posterior del ciclo de desarrollo, generalmente durante las pruebas o en un entorno de staging.

#### ¿Cómo Funciona DAST?
1. **Análisis Dinámico:**
   - Interactúa con la aplicación en ejecución y analiza su comportamiento para identificar vulnerabilidades.
2. **Simulación de Ataques:**
   - Envía solicitudes maliciosas para probar cómo la aplicación maneja diferentes tipos de entradas y comportamientos anómalos.
3. **Reportes Detallados:**
   - Genera reportes que describen las vulnerabilidades encontradas y las solicitudes específicas que las desencadenaron.

#### Ventajas de DAST
- **Cobertura de Funcionalidades:**
   - Identifica vulnerabilidades en las funcionalidades que se ejecutan en tiempo de ejecución.
- **Descubrimiento de Vulnerabilidades en Tiempo Real:**
   - Detecta problemas de seguridad que solo se manifiestan cuando la aplicación está en funcionamiento.
- **Pruebas de Caja Negra:**
   - No requiere acceso al código fuente, lo que permite evaluar la aplicación como lo haría un atacante externo.

#### Desventajas de DAST
- **Cobertura Incompleta:**
   - No puede detectar vulnerabilidades en partes del código que no se ejecutan durante las pruebas.
- **Falsos Negativos:**
   - Algunas vulnerabilidades pueden pasar desapercibidas si no se prueban todas las rutas posibles de la aplicación.
- **Configuración Compleja:**
   - Requiere configurar correctamente el entorno de prueba para simular condiciones de producción.

#### Herramientas Comunes de DAST
- **OWASP ZAP (Zed Attack Proxy)**
- **Burp Suite**
- **Netsparker**
- **Acunetix**

### Comparación entre SAST y DAST

| Característica             | SAST                                             | DAST                                            |
|----------------------------|--------------------------------------------------|-------------------------------------------------|
| **Momento de Ejecución**   | Etapas tempranas (codificación, construcción)    | Etapas posteriores (pruebas, staging)           |
| **Método de Análisis**     | Análisis estático del código                     | Análisis dinámico de la aplicación en ejecución |
| **Tipo de Prueba**         | Caja blanca (requiere acceso al código fuente)   | Caja negra (no requiere acceso al código fuente)|
| **Cobertura**              | Cobertura completa del código                    | Cobertura de funcionalidades en ejecución       |
| **Tiempo de Análisis**     | Puede ser lento en proyectos grandes             | Rápido, pero depende de la complejidad de la aplicación |
| **Detección Temprana**     | Sí, durante el desarrollo                        | No, durante las pruebas o staging               |

### Ejemplo de Pseudocódigo para Integrar SAST y DAST

#### Integración de SAST en CI/CD

```pseudocode
pipeline {
    stages {
        stage('Code Analysis') {
            steps {
                // Ejecutar herramienta SAST
                runSASTTool()
            }
            post {
                always {
                    archiveArtifacts 'sast-reports/*'
                }
            }
        }
        stage('Build') {
            steps {
                // Construir la aplicación
                buildApp()
            }
        }
    }
}
```

#### Ejemplo de Prueba DAST

```pseudocode
runDASTTool(applicationUrl):
    iniciar herramienta DAST
    configurar objetivo como applicationUrl
    ejecutar escaneo dinámico
    generar reporte de vulnerabilidades
    analizar reporte y tomar acciones necesarias
```

### Resumen

SAST y DAST son metodologías esenciales para la seguridad de aplicaciones, cada una con sus fortalezas y debilidades. SAST analiza el código fuente y es efectivo para detectar vulnerabilidades en una etapa temprana del ciclo de desarrollo, mientras que DAST prueba la aplicación en ejecución y puede identificar problemas que solo se manifiestan durante el funcionamiento. Utilizar ambos enfoques proporciona una cobertura de seguridad más completa y robusta.

## Introducción al Cifrado y Hashing

**Definición:**
El cifrado y el hashing son técnicas fundamentales en la seguridad de la información que protegen la confidencialidad e integridad de los datos. Aunque a menudo se mencionan juntas, tienen propósitos y mecanismos distintos.

### Cifrado

#### ¿Qué es el Cifrado?
El cifrado es el proceso de convertir datos legibles (texto plano) en un formato codificado (texto cifrado) que solo puede ser leído por alguien que posea una clave de descifrado adecuada. El propósito del cifrado es proteger la confidencialidad de los datos.

#### Tipos de Cifrado

1. **Cifrado Simétrico:**
   - Usa la misma clave para cifrar y descifrar datos.
   - **Ejemplo:** Advanced Encryption Standard (AES), Data Encryption Standard (DES).
   - **Ventajas:**
      - Rápido y eficiente para grandes cantidades de datos.
   - **Desventajas:**
      - La distribución segura de la clave es un desafío.

2. **Cifrado Asimétrico:**
   - Usa un par de claves: una clave pública para cifrar y una clave privada para descifrar.
   - **Ejemplo:** RSA, ElGamal.
   - **Ventajas:**
      - La clave pública puede ser distribuida libremente, y solo el propietario de la clave privada puede descifrar los datos.
   - **Desventajas:**
      - Más lento y computacionalmente costoso que el cifrado simétrico.

#### Ejemplo de Uso del Cifrado

**Cifrado Simétrico con AES (pseudocódigo):**
```pseudocode
funcion cifrarAES(textoPlano, clave):
    inicializar algoritmo AES con clave
    cifrar textoPlano
    devolver textoCifrado

funcion descifrarAES(textoCifrado, clave):
    inicializar algoritmo AES con clave
    descifrar textoCifrado
    devolver textoPlano
```

**Cifrado Asimétrico con RSA (pseudocódigo):**
```pseudocode
funcion cifrarRSA(textoPlano, clavePublica):
    inicializar algoritmo RSA con clavePublica
    cifrar textoPlano
    devolver textoCifrado

funcion descifrarRSA(textoCifrado, clavePrivada):
    inicializar algoritmo RSA con clavePrivada
    descifrar textoCifrado
    devolver textoPlano
```

### Hashing

#### ¿Qué es el Hashing?
El hashing es el proceso de convertir datos de cualquier tamaño en un valor fijo de longitud, que se conoce como hash o resumen. Este proceso es irreversible, lo que significa que no se puede obtener el dato original a partir del hash. El propósito del hashing es garantizar la integridad de los datos y la verificación de autenticidad.

#### Propiedades del Hashing

1. **Determinístico:**
   - El mismo mensaje siempre produce el mismo hash.

2. **Rápido:**
   - El cálculo del hash es rápido, incluso para grandes cantidades de datos.

3. **Irreversible:**
   - No es posible revertir el proceso de hashing para obtener el mensaje original.

4. **Sensibilidad a Cambios:**
   - Un pequeño cambio en el mensaje original produce un hash completamente diferente.

5. **Colisiones Raras:**
   - Es improbable que dos mensajes diferentes produzcan el mismo hash.

#### Ejemplos Comunes de Algoritmos de Hashing
- **MD5 (Message Digest Algorithm 5):** Aunque es rápido, ya no se considera seguro debido a sus vulnerabilidades a colisiones.
- **SHA-1 (Secure Hash Algorithm 1):** Más seguro que MD5, pero también ha sido comprometido y es desaconsejado.
- **SHA-256 (Secure Hash Algorithm 256-bit):** Parte de la familia SHA-2, se considera seguro y ampliamente utilizado.

#### Ejemplo de Uso del Hashing

**Hashing con SHA-256 (pseudocódigo):**
```pseudocode
funcion calcularHashSHA256(mensaje):
    inicializar algoritmo SHA-256
    calcular hash de mensaje
    devolver hash
```

### Diferencias entre Cifrado y Hashing

| Característica            | Cifrado                                                | Hashing                                                  |
|---------------------------|--------------------------------------------------------|----------------------------------------------------------|
| **Propósito**             | Proteger la confidencialidad de los datos              | Garantizar la integridad y autenticidad de los datos     |
| **Reversible**            | Sí (con la clave adecuada)                             | No                                                       |
| **Usos Comunes**          | Transmisión segura de datos, almacenamiento seguro     | Verificación de integridad, almacenamiento de contraseñas|
| **Tipos**                 | Simétrico y Asimétrico                                 | MD5, SHA-1, SHA-256                                      |

### Aplicaciones Prácticas

1. **Cifrado de Datos en Tránsito:**
   - Usar cifrado simétrico (AES) para proteger datos durante la transmisión entre clientes y servidores.

2. **Almacenamiento Seguro de Contraseñas:**
   - Usar hashing (SHA-256) para almacenar contraseñas de forma segura en una base de datos.

3. **Firma Digital:**
   - Usar cifrado asimétrico (RSA) y hashing (SHA-256) para crear y verificar firmas digitales.

### Resumen

La cifrado y el hashing son técnicas esenciales para la seguridad de la información. El cifrado asegura la confidencialidad de los datos, permitiendo que solo las partes autorizadas puedan leerlos. El hashing garantiza la integridad y autenticidad de los datos, permitiendo verificar que no han sido alterados. Utilizar estas técnicas de manera adecuada es crucial para proteger la información sensible en aplicaciones y sistemas.
