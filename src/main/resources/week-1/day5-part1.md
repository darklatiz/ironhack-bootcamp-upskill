Los OWASP Top 10 son una lista de las diez principales vulnerabilidades de seguridad web, elaborada por la Open Web Application Security Project (OWASP). Esta lista se actualiza periódicamente para reflejar los riesgos más críticos que enfrentan las aplicaciones web. A continuación se detallan los OWASP Top 10:

## Broken Authentication

**Definición:**
Broken Authentication ocurre cuando los mecanismos de autenticación de una aplicación son vulnerables, permitiendo a atacantes eludir el proceso de autenticación y acceder a cuentas de usuario.

### ¿Por qué es un problema?

Las fallas en la autenticación pueden resultar en accesos no autorizados a cuentas de usuario, robo de datos personales, suplantación de identidad, y otras actividades maliciosas.

### Ejemplos Comunes:

1. **Credenciales Predeterminadas:**
   Las aplicaciones o dispositivos utilizan credenciales predeterminadas (como "admin/admin"), que los usuarios no cambian.
   - **Ejemplo:** Un router que sigue utilizando el usuario "admin" y la contraseña "admin".

2. **Gestión de Sesiones Insegura:**
   Las sesiones no se gestionan de manera segura, permitiendo a atacantes secuestrar sesiones válidas.
   - **Ejemplo:** Tokens de sesión que no expiran o que pueden ser fácilmente adivinados.

3. **Recuperación de Contraseña Insegura:**
   Procesos de recuperación de contraseñas que son fáciles de explotar.
   - **Ejemplo:** Preguntas de seguridad obvias como "¿Cuál es tu color favorito?"

4. **Fuerza Bruta:**
   No se implementan protecciones adecuadas contra ataques de fuerza bruta.
   - **Ejemplo:** No hay limitación de intentos fallidos de inicio de sesión.

### Cómo Prevenirlo:

1. **Credenciales Seguras:**
   - Obligar a los usuarios a cambiar las credenciales predeterminadas al primer inicio de sesión.
   - Utilizar contraseñas fuertes y políticas de renovación de contraseñas.

2. **Gestión Segura de Sesiones:**
   - Generar tokens de sesión aleatorios y seguros.
   - Establecer tiempos de expiración para las sesiones y cerrar sesiones inactivas.
   - Utilizar cookies seguras (HttpOnly y Secure).

3. **Recuperación de Contraseña Robusta:**
   - Utilizar métodos de recuperación de contraseñas seguros como correos electrónicos de recuperación con enlaces de un solo uso y tokens de tiempo limitado.
   - Evitar preguntas de seguridad fáciles de adivinar.

4. **Protección contra Fuerza Bruta:**
   - Implementar límites de intentos de inicio de sesión fallidos.
   - Utilizar captchas para prevenir ataques automatizados.
   - Monitorear y bloquear IPs sospechosas.

5. **Autenticación Multifactor (MFA):**
   - Implementar MFA para agregar una capa adicional de seguridad, requiriendo más de un método de verificación.

### Resumen:

Broken Authentication es una vulnerabilidad crítica que puede comprometer la seguridad de las cuentas de usuario y la integridad de la aplicación. Implementar prácticas seguras de autenticación, junto con mecanismos de protección y monitoreo, es esencial para prevenir estas fallas.

Espero que esta explicación te sea útil para tu clase. ¿Hay algo más específico que te gustaría añadir o alguna otra duda que tengas?

### Demo Juice Shop
- [Juice Shop](http://localhost:3000/#/score-board?categories=Broken%20Access%20Control)
- Reto View Basket
- Login as admin 
  - usuario: admin@juice-sh.op
  - pwd: admin123

## Broken Access Control

**Definición:**
Broken Access Control ocurre cuando una aplicación no implementa correctamente las restricciones de acceso, permitiendo a usuarios no autorizados realizar acciones o acceder a recursos que deberían estar restringidos.

### ¿Por qué es un problema?

Las fallas en el control de acceso pueden llevar a varios problemas de seguridad, incluyendo:
- Acceso no autorizado a datos sensibles.
- Modificación o eliminación de datos.
- Ejecución de acciones administrativas sin permisos adecuados.

### Ejemplos Comunes:

1. **Bypass de Autenticación:**
   Un usuario malintencionado modifica una URL o parámetros de una solicitud para acceder a una página o funcionalidad restringida.
   - **Ejemplo:** `/admin/dashboard` en lugar de `/user/dashboard`.

2. **Escalada de Privilegios:**
   Un usuario con privilegios limitados encuentra una manera de elevar sus permisos y realizar acciones administrativas.
   - **Ejemplo:** Un usuario con permisos de lectura que puede cambiar su rol a administrador mediante una vulnerabilidad en la aplicación.

3. **Acceso Directo a URLs:**
   Recursos protegidos que pueden ser accedidos directamente si se conoce la URL, sin verificar los permisos del usuario.
   - **Ejemplo:** Archivos confidenciales accesibles directamente a través de una URL predecible.

### Demo Juice Shop
- [Juice Shop](http://localhost:3000/#/score-board?categories=Broken%20Access%20Control)
- Admin Section
  - #/administration

### Cómo Prevenirlo:

1. **Implementar Controles de Acceso Robustas:**
   - Asegúrate de que cada solicitud verifique si el usuario tiene los permisos adecuados.
   - Utiliza roles y permisos claramente definidos.

2. **Utilizar Listas de Control de Acceso (ACL):**
   - Implementa ACLs para verificar si un usuario tiene permiso para acceder a un recurso específico.

3. **Validar en el Lado del Servidor:**
   - No confíes en validaciones de acceso realizadas en el lado del cliente, ya que pueden ser manipuladas.

4. **Pruebas de Seguridad:**
   - Realiza pruebas de penetración y auditorías de seguridad para identificar y corregir problemas de control de acceso.

### Resumen:

Broken Access Control es una vulnerabilidad crítica que puede comprometer la seguridad de una aplicación al permitir accesos no autorizados. La implementación adecuada de controles de acceso, junto con pruebas regulares de seguridad, es esencial para prevenir estas fallas.

### Sensitive Data Exposure

**Definición:**
Sensitive Data Exposure ocurre cuando una aplicación no protege adecuadamente la información sensible, lo que permite que sea accesible a usuarios no autorizados. Esta información puede incluir datos personales, financieros, de salud, y credenciales de usuario.

### ¿Por qué es un problema?

La exposición de datos sensibles puede llevar a robo de identidad, fraude financiero, pérdidas económicas y daños a la reputación de una empresa. Además, puede tener implicaciones legales y regulatorias, especialmente en industrias que manejan información altamente confidencial.

### Ejemplos Comunes:

1. **Transmisión de Datos No Segura:**
   Transmitir datos sensibles sin cifrar a través de la red.
   - **Ejemplo:** Enviar información de tarjetas de crédito a través de HTTP en lugar de HTTPS.

2. **Almacenamiento Inseguro de Datos:**
   Almacenar datos sensibles en texto plano en bases de datos o archivos.
   - **Ejemplo:** Guardar contraseñas de usuarios sin cifrar en la base de datos.

3. **Falta de Cifrado Adecuado:**
   Usar algoritmos de cifrado débiles o mal implementados para proteger datos sensibles.
   - **Ejemplo:** Usar MD5 o SHA-1 para cifrar contraseñas en lugar de algoritmos más seguros como bcrypt o Argon2.

4. **Exposición de Datos en Registros:**
   Incluir datos sensibles en registros de aplicaciones o archivos de log sin protegerlos.
   - **Ejemplo:** Escribir números de tarjetas de crédito en logs de transacciones.

### Cómo Prevenirlo:

1. **Cifrado en Tránsito:**
   - Utilizar HTTPS para todas las comunicaciones que incluyan datos sensibles.
   - Implementar Transport Layer Security (TLS) para asegurar la transmisión de datos.

2. **Cifrado en Reposo:**
   - Cifrar datos sensibles almacenados en bases de datos y sistemas de archivos.
   - Utilizar cifrado fuerte como AES-256 para datos en reposo.

3. **Gestión Segura de Claves:**
   - Almacenar y gestionar claves de cifrado de manera segura.
   - Utilizar soluciones de gestión de claves (KMS) y hardware de seguridad (HSM) si es posible.

4. **Manejo Seguro de Contraseñas:**
   - Cifrar contraseñas utilizando algoritmos seguros y con sal (salt), como bcrypt, Argon2, o PBKDF2.
   - Implementar políticas de contraseñas fuertes y de renovación periódica.

5. **Minimización de Datos:**
   - Recopilar y almacenar solo la información necesaria.
   - Anonimizar o seudonimizar datos cuando sea posible.

6. **Prácticas de Desarrollo Seguro:**
   - Revisar y auditar regularmente el código para identificar y corregir exposiciones de datos.
   - Realizar pruebas de penetración y evaluaciones de seguridad para identificar vulnerabilidades.

### Juice Shop
[demo](http://localhost:3000/#/score-board?categories=Sensitive%20Data%20Exposure)
- Confidential Document


### Resumen:

Sensitive Data Exposure es una vulnerabilidad crítica que puede comprometer la seguridad de la información sensible de los usuarios. Implementar prácticas de cifrado robustas, asegurar la transmisión y el almacenamiento de datos, y gestionar las claves de manera segura son medidas esenciales para proteger los datos sensibles y evitar exposiciones.

### XML External Entities (XXE)

**Definición:**
XML External Entities (XXE) es una vulnerabilidad de seguridad que ocurre cuando una aplicación procesa entradas XML que incluyen referencias a entidades externas. Estas entidades pueden ser utilizadas por atacantes para leer archivos arbitrarios en el sistema, realizar solicitudes HTTP a sistemas internos, o incluso ejecutar código malicioso.

### ¿Por qué es un problema?

XXE puede llevar a la exposición de información sensible, acceso no autorizado a sistemas internos, denegación de servicio (DoS) y otras actividades maliciosas que comprometen la seguridad y la integridad de la aplicación y los datos.

### Ejemplos Comunes:

1. **Lectura de Archivos Locales:**
   Un atacante puede leer archivos sensibles en el servidor al incluir referencias a entidades externas en el XML.
   - **Ejemplo:**
     ```xml
     <!DOCTYPE foo [ <!ENTITY xxe SYSTEM "file:///etc/passwd"> ]>
     <foo>&xxe;</foo>
     ```

2. **Solicitud HTTP a Sistemas Internos:**
   Los atacantes pueden utilizar XXE para enviar solicitudes HTTP a sistemas internos que no son accesibles externamente.
   - **Ejemplo:**
     ```xml
     <!DOCTYPE foo [ <!ENTITY xxe SYSTEM "http://internal-system.local/"> ]>
     <foo>&xxe;</foo>
     ```

3. **Denegación de Servicio (DoS):**
   XXE puede ser explotado para consumir recursos excesivos del servidor, causando una denegación de servicio.
   - **Ejemplo:** Utilizando una entidad ampliada de forma exponencial (Billion Laughs Attack):
```xml
     <!DOCTYPE lolz [
     <!ENTITY lol "lol">
     <!ENTITY lol1 "&lol;&lol;&lol;&lol;&lol;&lol;&lol;&lol;&lol;&lol;">
     <!ENTITY lol2 "&lol1;&lol1;&lol1;&lol1;&lol1;&lol1;&lol1;&lol1;&lol1;&lol1;">
     <!ENTITY lol3 "&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;">
     <!ENTITY lol4 "&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;">
     <!ENTITY lol5 "&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;">
     <!ENTITY lol6 "&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;">
     <!ENTITY lol7 "&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;">
     <!ENTITY lol8 "&lol7;&lol7;&lol7;&lol7;&lol7;&lol.7;&lol7;&lol7;&lol7;&lol7;">
     <!ENTITY lol9 "&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;">]>
     <lolz>&lol9;</lolz>
```

### Cómo Prevenirlo:

1. **Desactivar Entidades Externas en XML Parsers:**
   - Configura el analizador XML para deshabilitar la resolución de entidades externas.
   - En Java, por ejemplo:
     ```java
     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
     dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
     dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
     dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
     ```

2. **Validar y Sanitizar la Entrada XML:**
   - Asegúrate de que solo se acepten entradas XML esperadas y válidas.
   - Implementa validaciones estrictas y saneamiento de datos para evitar inyecciones maliciosas.

3. **Utilizar Parsers Seguros:**
   - Utiliza bibliotecas y parsers XML que sean conocidos por su seguridad y que tengan configuraciones adecuadas para prevenir XXE.

4. **Monitoreo y Auditoría:**
   - Implementa monitoreo y auditoría de las entradas XML procesadas para detectar y responder a posibles ataques.

### Resumen:

XML External Entities (XXE) es una vulnerabilidad crítica que puede comprometer la seguridad de una aplicación al permitir la exposición de datos sensibles, acceso no autorizado a sistemas internos, y otros ataques. Desactivar entidades externas en los parsers XML, validar y sanitizar la entrada XML, y utilizar parsers seguros son medidas esenciales para prevenir XXE.

### Juice Shop

- [demo XXE](http://localhost:3000/#/complain)
- Retrieve the content of a file
```xml
<!DOCTYPE STRUCTURE [
<!ELEMENT SPECIFICATIONS (#PCDATA)>
<!ENTITY VERSION “1.1”>
<!ENTITY file SYSTEM “file:///c:/Windows/system.ini” >
]>
```

## Injection Flaws

**Definición:**
Las Injection Flaws (fallos de inyección) ocurren cuando un atacante puede enviar datos maliciosos a una aplicación, que luego son interpretados como comandos o consultas por el intérprete subyacente. Los tipos más comunes incluyen SQL injection, NoSQL injection, OS command injection, y LDAP injection.

### ¿Por qué es un problema?

Las fallas de inyección pueden permitir a los atacantes:
- Acceder a datos no autorizados.
- Modificar o eliminar datos.
- Ejecutar comandos arbitrarios en el servidor.
- Escalar privilegios y comprometer completamente la aplicación y el sistema subyacente.

### Ejemplos Comunes:

1. **SQL Injection:**
   Ocurre cuando un atacante inserta código SQL malicioso en una consulta.
   - **Ejemplo:**
     ```sql
     SELECT * FROM users WHERE username = 'admin' AND password = 'password';
     ```
     Si un atacante ingresa `' OR '1'='1`, la consulta se convierte en:
     ```sql
     SELECT * FROM users WHERE username = '' OR '1'='1' AND password = '';
     ```
     Lo que siempre será verdadero y permitirá el acceso no autorizado.

2. **OS Command Injection:**
   Ocurre cuando un atacante puede ejecutar comandos del sistema operativo.
   - **Ejemplo:**
     ```php
     $file = $_GET['file'];
     system("cat " . $file);
     ```
     Si un atacante ingresa `; ls`, ejecutará un comando adicional:
     ```sh
     cat ; ls
     ```

3. **LDAP Injection:**
   Ocurre cuando un atacante manipula consultas LDAP.
   - **Ejemplo:**
     ```ldap
     searchFilter = "(uid=" + userInput + ")";
     ```
     Si un atacante ingresa `*))(|(uid=*))(|(uid=*`, la consulta se convierte en:
     ```ldap
     searchFilter = "((uid=*))(|(uid=*))(|(uid=*))";
     ```

### Cómo Prevenirlo:

1. **Usar Consultas Parametrizadas:**
   - Para SQL, utiliza consultas preparadas y ORM que manejan la parametrización automáticamente.
   - **Ejemplo en Java:**
     ```java
     String query = "SELECT * FROM users WHERE username = ? AND password = ?";
     PreparedStatement pstmt = connection.prepareStatement(query);
     pstmt.setString(1, username);
     pstmt.setString(2, password);
     ResultSet rs = pstmt.executeQuery();
     ```

2. **Escapar Caracteres Especiales:**
   - Asegúrate de escapar adecuadamente los caracteres especiales en los datos de entrada antes de usarlos en comandos o consultas.

3. **Validar y Sanitizar la Entrada:**
   - Implementa validaciones estrictas en los datos de entrada para asegurarte de que solo se acepten valores esperados.
   - Utiliza listas blancas de valores permitidos.

4. **Usar APIs Seguras:**
   - Utiliza bibliotecas y funciones diseñadas para manejar de forma segura la interacción con el sistema operativo y otros servicios externos.

5. **Principio de Privilegios Mínimos:**
   - Asegúrate de que las aplicaciones y las cuentas de usuario tengan los menores privilegios posibles necesarios para realizar sus funciones.

6. **Pruebas de Seguridad:**
   - Realiza pruebas de penetración y auditorías de seguridad para identificar y corregir posibles vulnerabilidades de inyección.

### Demo Juice Shop
- [Injection](http://localhost:3000/#/score-board?categories=Injection)
- Primeramente usar: `'`
  - Verificar en network tab de developer tools ERROR SQL
- Usar: `' OR true`
  - Verificar network tab para ver error SQL
- Usar: `' OR true--`
  - En esta ocasion la inyeccion de SQL tiene efecto


### Resumen:

Las Injection Flaws son vulnerabilidades críticas que pueden comprometer la seguridad de una aplicación y sus datos. Utilizar consultas parametrizadas, escapar caracteres especiales, validar y sanitizar la entrada, y seguir prácticas seguras de desarrollo son esenciales para prevenir estos fallos.

## Security Misconfigurations

**Definición:**
Las Security Misconfigurations (configuraciones de seguridad incorrectas) ocurren cuando una aplicación o un sistema no está configurado adecuadamente, dejando abiertas vulnerabilidades que pueden ser explotadas por atacantes. Esto puede incluir configuraciones por defecto, errores en la configuración del servidor, permisos inapropiados y más.

### ¿Por qué es un problema?

Las configuraciones incorrectas pueden permitir a los atacantes:
- Acceder a datos sensibles.
- Ejecutar comandos no autorizados.
- Escalar privilegios.
- Comprometer el sistema completo.

### Ejemplos Comunes:

1. **Configuraciones por Defecto:**
   - Uso de credenciales predeterminadas para bases de datos, servidores o aplicaciones.
   - **Ejemplo:** `admin/admin` como usuario y contraseña.

2. **Directivas de Seguridad Relajadas:**
   - Deshabilitar controles de seguridad importantes como la autenticación multifactor (MFA).
   - **Ejemplo:** No requerir MFA para acceso a cuentas administrativas.

3. **Errores en la Configuración del Servidor:**
   - Directorios expuestos que deberían estar restringidos.
   - **Ejemplo:** Exposición del directorio `/admin/` a usuarios no autorizados.

4. **Permisos Inadecuados:**
   - Permisos de archivos y directorios mal configurados, permitiendo acceso no autorizado.
   - **Ejemplo:** Archivos de configuración sensibles accesibles para todos los usuarios del sistema.

5. **Falta de Actualización y Parches:**
   - No aplicar parches y actualizaciones de seguridad, dejando el sistema vulnerable a ataques conocidos.
   - **Ejemplo:** Uso de versiones obsoletas de software con vulnerabilidades conocidas.

### Cómo Prevenirlo:

1. **Revisar y Ajustar Configuraciones por Defecto:**
   - Cambiar las credenciales predeterminadas inmediatamente después de la instalación.
   - Deshabilitar o eliminar cuentas predeterminadas no necesarias.

2. **Implementar Directivas de Seguridad Estrictas:**
   - Habilitar y reforzar controles de seguridad como MFA, políticas de contraseñas fuertes y cifrado de datos.
   - Asegurarse de que las configuraciones de seguridad estén alineadas con las mejores prácticas y estándares de la industria.

3. **Configurar Correctamente Servidores y Servicios:**
   - Restringir el acceso a directorios y archivos sensibles.
   - Utilizar firewalls y listas de control de acceso (ACL) para limitar el acceso a recursos críticos.

4. **Gestionar Permisos de Forma Adecuada:**
   - Asegurar que los permisos de archivos y directorios sean mínimos y necesarios para la operación.
   - Revisar regularmente los permisos y realizar auditorías de seguridad.

5. **Mantener Actualizado el Software:**
   - Aplicar parches y actualizaciones de seguridad de manera oportuna.
   - Utilizar herramientas de gestión de parches para automatizar y asegurar la aplicación de actualizaciones.

6. **Automatizar y Monitorear la Configuración:**
   - Utilizar herramientas de gestión de configuraciones y seguridad para automatizar la aplicación de configuraciones seguras.
   - Monitorear y auditar continuamente la configuración del sistema para detectar y corregir desviaciones.

### Casos Famosos
[HeatMap](https://www.youtube.com/watch?v=cQkQpbkhzIo&t=12s)

### Resumen:

Las Security Misconfigurations son vulnerabilidades críticas que pueden comprometer la seguridad de una aplicación o sistema. Revisar y ajustar configuraciones por defecto, implementar directivas de seguridad estrictas, configurar correctamente servidores y servicios, gestionar permisos adecuadamente y mantener el software actualizado son medidas esenciales para prevenir configuraciones incorrectas.

## Cross-Site Scripting (XSS)

**Definición:**
Cross-Site Scripting (XSS) es una vulnerabilidad de seguridad que permite a los atacantes inyectar scripts maliciosos en páginas web vistas por otros usuarios. Estos scripts pueden ejecutar en el navegador del usuario víctima, robando datos, realizando acciones en su nombre, o redirigiéndolo a sitios maliciosos.

### ¿Por qué es un problema?

Las vulnerabilidades XSS pueden ser explotadas para:
- Robar cookies de sesión y datos de autenticación.
- Realizar ataques de phishing.
- Manipular contenido de la página web.
- Redirigir usuarios a sitios maliciosos.
- Ejecutar keyloggers y otras cargas útiles maliciosas.

### Tipos Comunes de XSS:

1. **XSS Reflejado:**
   - Ocurre cuando un script malicioso es inyectado a través de una solicitud HTTP y se refleja inmediatamente en la respuesta de la aplicación.
   - **Ejemplo:** Un atacante envía un enlace malicioso que, al ser abierto, ejecuta un script en el navegador del usuario.
     ```html
     http://example.com/search?q=<script>alert('XSS')</script>
     ```

2. **XSS Persistente (o Almacenado):**
   - Ocurre cuando un script malicioso se almacena en el servidor y se sirve a otros usuarios.
   - **Ejemplo:** Un atacante publica un comentario con código malicioso en un foro, y ese código se ejecuta cada vez que alguien ve el comentario.
     ```html
     <script>alert('XSS')</script>
     ```

3. **XSS Basado en DOM:**
   - Ocurre cuando el script malicioso manipula el Document Object Model (DOM) en el navegador, sin pasar por el servidor.
   - **Ejemplo:** Un script en la página que toma entradas del usuario y las inserta en el DOM sin sanitización.
     ```javascript
     document.write(location.hash);
     ```

### Cómo Prevenirlo:

1. **Escapar y Sanitizar Entradas:**
   - Escapa todos los datos de entrada antes de insertarlos en el HTML, JavaScript, CSS o URL.
   - Utiliza funciones de escape específicas para el contexto:
      - HTML: `&lt;`, `&gt;`, `&amp;`, `&quot;`
      - JavaScript: `\`, `\'`, `\"`
      - URL: `%20`, `%3A`

2. **Validar y Sanitizar Entradas:**
   - Implementa validaciones estrictas en el lado del servidor para asegurarte de que solo se acepten entradas válidas y esperadas.
   - Usa bibliotecas de sanitización para limpiar las entradas de los usuarios.

3. **Usar Content Security Policy (CSP):**
   - Implementa CSP para restringir las fuentes desde las cuales se pueden cargar scripts.
   - **Ejemplo:**
     ```http
     Content-Security-Policy: script-src 'self'
     ```

4. **Evitar la Inserción Directa de Datos en el DOM:**
   - Usa APIs seguras como `textContent` y `innerText` en lugar de `innerHTML` para insertar datos en el DOM.
   - **Ejemplo:**
     ```javascript
     document.getElementById('output').textContent = userInput;
     ```

5. **Bibliotecas y Frameworks:**
   - Utiliza bibliotecas y frameworks que manejan automáticamente la sanitización de datos, como React, Angular, y Vue.js.

### Demo
- [XSS](http://localhost:3000/#/score-board?categories=XSS)
- Introducir en la barra de busqueda: `owasp`
- Introducir en la barra de busqueda: `<iframe src="javascript:alert(`xss`)">`

### Resumen:

Cross-Site Scripting (XSS) es una vulnerabilidad crítica que puede comprometer la seguridad de una aplicación web y los datos de sus usuarios. Escapar y sanitizar entradas, implementar CSP, y utilizar APIs seguras son prácticas esenciales para prevenir XSS y proteger tanto la aplicación como sus usuarios.

## Insecure Deserialization

**Definición:**
Insecure Deserialization ocurre cuando una aplicación deserializa datos no confiables que pueden ser manipulados por un atacante para ejecutar código arbitrario, escalar privilegios, o realizar otras acciones maliciosas.

### ¿Por qué es un problema?

La deserialización insegura puede permitir a los atacantes:
- Ejecutar código arbitrario en el servidor.
- Realizar ataques de denegación de servicio (DoS).
- Escalar privilegios y comprometer el sistema.
- Modificar o eliminar datos sensibles.

### Ejemplos Comunes:

1. **Ejecución Remota de Código:**
   Un atacante puede inyectar un objeto malicioso en los datos que se van a deserializar, lo que puede llevar a la ejecución de comandos arbitrarios.
   - **Ejemplo en Java:**
     ```java
     ByteArrayInputStream bis = new ByteArrayInputStream(serializedObject);
     ObjectInputStream ois = new ObjectInputStream(bis);
     MyObject obj = (MyObject) ois.readObject(); // Deserialización insegura
     ```

2. **Ataques de Denegación de Servicio (DoS):**
   Un atacante puede enviar datos manipulados que causen un consumo excesivo de recursos, provocando un DoS.
   - **Ejemplo:** Enviar un objeto masivo que consuma toda la memoria del servidor al deserializarse.

3. **Modificación de Datos:**
   Un atacante puede modificar el estado interno de un objeto deserializado, llevando a comportamientos no deseados.
   - **Ejemplo:** Cambiar los permisos de un usuario de "regular" a "administrador".

### Cómo Prevenirlo:

1. **Evitar la Deserialización de Datos No Confiables:**
   - No deserialices datos de fuentes no confiables o no verificadas.
   - Utiliza formatos de intercambio de datos seguros y más simples como JSON o XML con validaciones estrictas.

2. **Validar y Sanitizar Datos Deserializados:**
   - Implementa validaciones estrictas en los datos deserializados para asegurarte de que sean válidos y seguros.
   - Usa patrones de validación para confirmar la integridad y legitimidad de los datos.

3. **Utilizar Bibliotecas Seguras:**
   - Emplea bibliotecas de deserialización que sean conocidas por su seguridad y que ofrezcan mecanismos de protección.
   - Algunas bibliotecas proporcionan opciones para validar o filtrar clases durante la deserialización.

4. **Aplicar Controles de Seguridad Adicionales:**
   - Implementa controles de acceso y limitaciones estrictas sobre los datos deserializados.
   - Utiliza mecanismos como la lista blanca para permitir solo clases específicas en el proceso de deserialización.

5. **Deserialización en Entornos Aislados:**
   - Realiza la deserialización en entornos aislados o en un sandbox para limitar el impacto de posibles ataques.
   - Restringe los permisos del proceso que realiza la deserialización.

### Ejemplo de Prevención en Java:

Para evitar deserialización insegura en Java, se puede implementar una validación explícita de clases permitidas:
```java
ObjectInputStream ois = new ObjectInputStream(bis) {
    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        if (!allowedClasses.contains(desc.getName())) {
            throw new InvalidClassException("Unauthorized deserialization attempt", desc.getName());
        }
        return super.resolveClass(desc);
    }
};
```

### Resumen:

Insecure Deserialization es una vulnerabilidad crítica que puede comprometer la seguridad de una aplicación al permitir la ejecución de código arbitrario, ataques DoS y modificación de datos. Evitar la deserialización de datos no confiables, validar y sanitizar datos, utilizar bibliotecas seguras y aplicar controles de seguridad adicionales son medidas esenciales para prevenir estos riesgos.

## Usar componentes con vulnerabilidades conocidas

**Definición:**
Usar componentes con vulnerabilidades conocidas se refiere a la práctica de incluir bibliotecas, frameworks, módulos u otros componentes de software que contienen fallas de seguridad ya identificadas y documentadas en el desarrollo de aplicaciones.

### ¿Por qué es un problema?

El uso de componentes con vulnerabilidades conocidas puede permitir a los atacantes:
- Comprometer la seguridad de la aplicación.
- Acceder a datos sensibles.
- Ejercer control no autorizado sobre el sistema.
- Realizar ataques de denegación de servicio (DoS).

### Ejemplos Comunes:

1. **Bibliotecas y Frameworks Desactualizados:**
   - Usar versiones antiguas de bibliotecas o frameworks que tienen vulnerabilidades conocidas.
   - **Ejemplo:** Una aplicación que utiliza una versión obsoleta de la biblioteca Apache Struts con una vulnerabilidad que permite la ejecución remota de código.

2. **Plugins y Módulos de Terceros:**
   - Incluir plugins o módulos de terceros que no se mantienen y contienen vulnerabilidades.
   - **Ejemplo:** Un sitio web que utiliza un plugin de Wordpress desactualizado con vulnerabilidades que pueden ser explotadas por atacantes.

3. **Dependencias Transitivas:**
   - Las dependencias indirectas que se incluyen a través de otras bibliotecas también pueden contener vulnerabilidades.
   - **Ejemplo:** Una biblioteca de JavaScript que incluye otra biblioteca vulnerable como dependencia.

### Cómo Prevenirlo:

1. **Mantener Actualizados los Componentes:**
   - Actualiza regularmente todas las bibliotecas, frameworks y otros componentes a sus versiones más recientes.
   - Utiliza herramientas de gestión de dependencias que alerten sobre nuevas versiones y vulnerabilidades conocidas (por ejemplo, npm, Maven, pip).

2. **Monitorear Vulnerabilidades:**
   - Monitorea continuamente las vulnerabilidades conocidas en los componentes utilizados.
   - Utiliza bases de datos de vulnerabilidades como el National Vulnerability Database (NVD) y servicios como Dependabot, Snyk, o OWASP Dependency-Check.

3. **Evaluar Componentes Antes de Usar:**
   - Antes de incorporar un nuevo componente, evalúa su historial de seguridad, la frecuencia de actualizaciones y la reputación de sus mantenedores.
   - Prefiere componentes que tengan una comunidad activa y un ciclo de actualización rápido.

4. **Escaneo Regular de Seguridad:**
   - Realiza escaneos de seguridad regulares en tu códigobase para identificar componentes vulnerables.
   - Utiliza herramientas automatizadas para escanear las dependencias y detectar vulnerabilidades.

5. **Implementar Políticas de Seguridad:**
   - Establece políticas internas que definan cómo se seleccionan y mantienen los componentes de terceros.
   - Exige revisiones de seguridad antes de introducir nuevos componentes y como parte del proceso de actualización.

### Ejemplo de Prevención:

En un proyecto JavaScript utilizando npm, puedes utilizar `npm audit` para escanear las dependencias y detectar vulnerabilidades:
```sh
npm audit
```
Esto generará un informe de las vulnerabilidades encontradas en las dependencias del proyecto y proporcionará recomendaciones para actualizar o solucionar los problemas.

### Resumen:

Usar componentes con vulnerabilidades conocidas es un riesgo significativo para la seguridad de cualquier aplicación. Mantener los componentes actualizados, monitorear vulnerabilidades, evaluar componentes antes de usarlos, realizar escaneos de seguridad regulares y establecer políticas de seguridad son prácticas esenciales para mitigar este riesgo.

## Falta de registros (logs) adecuados y de sistemas de monitoreo (Insufficient Logging and Monitoring)

**Definición:**
Insufficient Logging and Monitoring se refiere a la falta de registros (logs) adecuados y de sistemas de monitoreo efectivos para detectar, alertar y responder a actividades maliciosas o anomalías en una aplicación o sistema.

### ¿Por qué es un problema?

La falta de registros y monitoreo adecuados puede:
- Impedir la detección temprana de ataques y brechas de seguridad.
- Dificultar la investigación y respuesta a incidentes de seguridad.
- Permitir que actividades maliciosas pasen desapercibidas durante períodos prolongados, aumentando el daño potencial.
- Complicar el cumplimiento de normativas y auditorías de seguridad.

### Ejemplos Comunes:

1. **Falta de Registros de Seguridad:**
   - No registrar intentos de inicio de sesión fallidos, cambios en configuraciones críticas o acceso a datos sensibles.
   - **Ejemplo:** Una aplicación que no registra los intentos fallidos de autenticación, dificultando la detección de ataques de fuerza bruta.

2. **Registros Insuficientes:**
   - Registrar información insuficiente que no permite entender completamente un incidente de seguridad.
   - **Ejemplo:** Registros que solo contienen mensajes de error genéricos sin detalles sobre el contexto o la fuente del problema.

3. **Ausencia de Monitoreo Activo:**
   - No tener sistemas de monitoreo en tiempo real que alerten sobre actividades sospechosas o anómalas.
   - **Ejemplo:** No utilizar un sistema de detección de intrusos (IDS) para monitorear el tráfico de red en busca de patrones maliciosos.

4. **Retención Inadecuada de Registros:**
   - No conservar los registros durante un período de tiempo suficiente para realizar investigaciones forenses.
   - **Ejemplo:** Eliminar registros después de unos pocos días, impidiendo el análisis de incidentes que se descubren tardíamente.

### Cómo Prevenirlo:

1. **Implementar un Registro Completo:**
   - Asegúrate de que todos los eventos importantes se registren, incluyendo accesos, cambios de configuración, errores, y eventos de seguridad.
   - Incluye detalles como la fecha, hora, usuario involucrado, y contexto del evento.

2. **Monitoreo en Tiempo Real:**
   - Implementa sistemas de monitoreo en tiempo real para detectar y alertar sobre actividades sospechosas.
   - Utiliza herramientas de monitoreo y detección de intrusos (IDS/IPS) para supervisar el tráfico de red y los eventos del sistema.

3. **Centralizar y Analizar Registros:**
   - Utiliza una solución de gestión de registros centralizada (como ELK Stack, Splunk, o Graylog) para agregar, almacenar y analizar los registros.
   - Configura alertas automáticas para detectar patrones inusuales o indicios de posibles incidentes de seguridad.

4. **Políticas de Retención de Registros:**
   - Establece políticas claras para la retención de registros, asegurando que se conserven durante el tiempo necesario para cumplir con las normativas y permitir análisis forenses.
   - Asegúrate de que los registros sean accesibles pero estén protegidos contra alteraciones o accesos no autorizados.

5. **Auditorías y Revisiones Periódicas:**
   - Realiza auditorías y revisiones periódicas de los registros y sistemas de monitoreo para asegurar que están funcionando correctamente y capturando la información necesaria.
   - Ajusta y mejora continuamente el proceso de registro y monitoreo en base a los resultados de estas auditorías.

### Ejemplo de Configuración de Logs:

En una aplicación web basada en Java, puedes utilizar Log4j para implementar un sistema de registro adecuado:
```xml
<Configuration status="WARN">
  <Appenders>
    <Console name="Console" target="SYSTEM_OUT">
      <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
    </Console>
    <File name="File" fileName="logs/app.log">
      <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss} [%t] %-5p %c{1} - %m%n"/>
    </File>
  </Appenders>
  <Loggers>
    <Root level="debug">
      <AppenderRef ref="Console"/>
      <AppenderRef ref="File"/>
    </Root>
  </Loggers>
</Configuration>
```

### Herramientas para Logging y Monitoring

Para implementar un sistema robusto de logging y monitoring, es esencial utilizar herramientas que permitan la recopilación, análisis y visualización de registros en tiempo real. Algunas de las herramientas más populares incluyen:

1. **Splunk:**
   - **Descripción:** Splunk es una plataforma poderosa para buscar, monitorear y analizar datos de máquina generados por aplicaciones, sistemas y dispositivos.
   - **Características:**
      - Ingesta de datos en tiempo real.
      - Búsqueda y análisis de registros.
      - Dashboards y visualizaciones personalizadas.
      - Alertas y notificaciones automatizadas.
   - **Uso:** Ideal para entornos empresariales que necesitan una solución completa para el monitoreo y análisis de registros.
   - **Ejemplo:**
     ```spl
     index=web_logs | stats count by status_code
     ```

2. **Logstash (parte de ELK Stack):**
   - **Descripción:** Logstash es una herramienta de procesamiento de datos en tiempo real que puede ingerir, transformar y enviar datos a diversos destinos, incluido Elasticsearch.
   - **Características:**
      - Recopilación de datos de múltiples fuentes.
      - Filtrado y transformación de datos.
      - Envío de datos a Elasticsearch para su almacenamiento y análisis.
   - **Uso:** Parte de la ELK Stack (Elasticsearch, Logstash, Kibana) para crear una solución de logging y monitoreo robusta.

5. **Prometheus y Grafana:**
   - **Descripción:** Prometheus es una herramienta de monitoreo y alerta de código abierto, mientras que Grafana es una plataforma de análisis y visualización.
   - **Características:**
      - Recopilación y almacenamiento de métricas.
      - Alertas basadas en reglas personalizadas.
      - Visualización de métricas en dashboards interactivos.
   - **Uso:** Ideal para monitorear la salud y el rendimiento de aplicaciones y sistemas.

### Resumen:

Incorporar herramientas como Splunk, Logstash, Graylog, Prometheus y Grafana puede mejorar significativamente la capacidad de una organización para registrar, monitorear y responder a incidentes de seguridad. Implementar un sistema robusto de logging y monitoring utilizando estas herramientas es esencial para mantener la seguridad y la integridad de las aplicaciones y sistemas.

Insufficient Logging and Monitoring es una vulnerabilidad crítica que puede impedir la detección y respuesta efectiva a incidentes de seguridad. Implementar un sistema de registro completo, monitoreo en tiempo real, centralización y análisis de registros, políticas de retención adecuadas, y auditorías periódicas son medidas esenciales para mitigar este riesgo.
