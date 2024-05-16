# Leccion Tecnicas Efectivas Para Escribir Pruebas

## Objetivos

- Entender los principios basicos de como escribir pruebas (tests) y de como las pruebas representan una documentacion ejecutable del sistema
  - Claridad
  - Simplicidad

- Implementar conceptos como
  - La parametrizacion de las pruebas
  - Mocks
  - Stubs
- Modificar pruebas existentes para incrementar su 
  - Efectividad
  - Claridad 
  - Cobertura de codigo

## Introduccion

Tener una buena metodologia para escribir pruebas es vital ya que facilitan:
- El mantenimiento del sistema
- Nuevas funcionalidades del sistema
Sin introducir regresiones, errores o bugs.

Las caracteristicas que deben de tener los componentes, o conjunto de pruebas para
considerarse efectivas son:
- Claridad y simplicidad: Las pruebas deben de ser directas y enfocadas. Una prueba bien escrita debe de ser : 
  - Facil de **entender** 
  - Facil de **mantener**
  - Estas Caracteristicas nos aseguran que las pruebas puedan modificarse o ajustarse de una manera rapida.
- Documentacion Ejecutable: En su conjunto, las pruebas bien escritas nos dan una vision de los que el sistema intenta resolver, dandonos informacion de como funciona el sistema en ciertos casos de uso, esta caracteristica es invaluable para otros desarroladores que no estan familiarizados con el codigo base.

## Resumen

En resumen, una buena metodología de pruebas no solo mejora la calidad del software, sino que también acelera el proceso de desarrollo y mantenimiento, proporcionando un valor inestimable al equipo de desarrollo.

### Ejemplo Base Analisis

```javascript
// GOOD TEST
function testEmailVerification_ShouldPassWithValidEmail() {
    const result = emailVerifier.verify("user@example.com");
    assertTrue(result, "Valid email should pass verification.");
}
```

### Buenas Prácticas
1. **Nombres Descriptivos:** Usa nombres de funciones de prueba que describan claramente el caso de prueba.
2. **Pruebas Claras y Simples:** Cada prueba debe enfocarse en un solo comportamiento específico.
3. **Mensajes de Error Claros:** Proporciona mensajes de error descriptivos para facilitar la depuración.
4. **Pruebas Negativas:** Asegúrate de probar también los casos en los que la función debe fallar.
5. **Preparación y Limpieza:** Configura y limpia los datos necesarios para la prueba (aunque en este caso sencillo no es necesario).

### Ejemplos Adicionales

#### Prueba de un Email Válido
```javascript
function testEmailVerification_ShouldPassWithValidEmail() {
    const result = emailVerifier.verify("user@example.com");
    assertTrue(result, "Valid email should pass verification.");
}
```

#### Prueba de un Email Inválido (Sin Dominio)
```javascript
function testEmailVerification_ShouldFailWithoutDomain() {
    const result = emailVerifier.verify("user@");
    assertFalse(result, "Email without domain should fail verification.");
}
```

#### Prueba de un Email Inválido (Sin Arroba)
```javascript
function testEmailVerification_ShouldFailWithoutAtSymbol() {
    const result = emailVerifier.verify("userexample.com");
    assertFalse(result, "Email without '@' should fail verification.");
}
```

#### Prueba de un Email Inválido (Sin Usuario)
```javascript
function testEmailVerification_ShouldFailWithoutUser() {
    const result = emailVerifier.verify("@example.com");
    assertFalse(result, "Email without user should fail verification.");
}
```

#### Prueba de un Email Inválido (Con Caracteres Especiales)
```javascript
function testEmailVerification_ShouldFailWithSpecialCharacters() {
    const result = emailVerifier.verify("user@exa!mple.com");
    assertFalse(result, "Email with special characters should fail verification.");
}
```

#### Prueba de un Email Inválido (Con Espacios)
```javascript
function testEmailVerification_ShouldFailWithSpaces() {
    const result = emailVerifier.verify("user @example.com");
    assertFalse(result, "Email with spaces should fail verification.");
}
```

### Recomendaciones Generales

1. **Uso de un Framework de Pruebas:** Considera usar un framework de pruebas como Jest o Mocha para estructurar tus pruebas.
2. **Separación de Pruebas:** Mantén cada prueba independiente de las demás para evitar dependencias no deseadas.
3. **Pruebas Automatizadas:** Asegúrate de que las pruebas puedan ejecutarse automáticamente en un entorno de integración continua (CI).

Implementar estas prácticas no solo asegura que tus pruebas sean efectivas y fáciles de mantener, sino que también mejora la calidad general del software y facilita la colaboración entre desarrolladores.

### Ejemplo de Prueba Mal Escrita

Aquí tienes el ejemplo de una prueba mal escrita para el registro de un usuario. A continuación, se explican los problemas y se proporcionan ejemplos mejorados siguiendo buenas prácticas.

```javascript
// Bad Test for User Registration
function testUserRegistration() {
    newUser.register("user@example.com", "username", "password");
    assertTrue(database.has("username"), "User should be in database");
    assertTrue(emailSystem.received("user@example.com"), "Verification email sent");
}
```

### Problemas en la Prueba Mal Escrita

1. **Nombre de la Prueba Poco Descriptivo:** El nombre `testUserRegistration` es genérico y no describe claramente el comportamiento que está probando.
2. **Falta de Preparación y Limpieza:** No hay preparación del estado inicial ni limpieza después de la prueba.
3. **Múltiples Afirmaciones en una Sola Prueba:** La prueba verifica múltiples cosas (registro en la base de datos y envío de correo electrónico), lo que dificulta identificar el fallo específico.
4. **Dependencia de Estados Globales:** La prueba puede depender de estados globales del `database` y `emailSystem` que pueden ser modificados por otras pruebas, lo que lleva a resultados inconsistentes.
5. **Falta de Claridad en los Mensajes de Error:** Los mensajes de error no proporcionan suficiente contexto.

### Ejemplos de Pruebas Mejoradas

#### Prueba de Registro de Usuario Exitoso
```javascript
function testUserRegistration_ShouldRegisterUserSuccessfully() {
    // Preparación
    const email = "user@example.com";
    const username = "username";
    const password = "password";
    database.clear();
    emailSystem.clear();

    // Acción
    newUser.register(email, username, password);

    // Verificación
    assertTrue(database.has(username), `User '${username}' should be in database.`);
    assertTrue(emailSystem.received(email), `Verification email should be sent to '${email}'.`);
    
    // Limpieza
    database.clear();
    emailSystem.clear();
}
```

#### Prueba de Registro de Usuario con Email Duplicado
```javascript
function testUserRegistration_ShouldFailWithDuplicateEmail() {
    // Preparación
    const email = "user@example.com";
    const username1 = "username1";
    const username2 = "username2";
    const password = "password";
    database.clear();
    emailSystem.clear();
    newUser.register(email, username1, password);

    // Acción
    const result = newUser.register(email, username2, password);

    // Verificación
    assertFalse(result, "Registration should fail with duplicate email.");
    assertFalse(database.has(username2), `User '${username2}' should not be in database.`);
    
    // Limpieza
    database.clear();
    emailSystem.clear();
}
```

#### Prueba de Registro de Usuario con Nombre de Usuario Duplicado
```javascript
function testUserRegistration_ShouldFailWithDuplicateUsername() {
    // Preparación
    const email1 = "user1@example.com";
    const email2 = "user2@example.com";
    const username = "username";
    const password = "password";
    database.clear();
    emailSystem.clear();
    newUser.register(email1, username, password);

    // Acción
    const result = newUser.register(email2, username, password);

    // Verificación
    assertFalse(result, "Registration should fail with duplicate username.");
    assertEquals(emailSystem.count(email2), 0, `No verification email should be sent to '${email2}'.`);
    
    // Limpieza
    database.clear();
    emailSystem.clear();
}
```

### Recomendaciones Generales

1. **Nombres Descriptivos:** Usa nombres de prueba que describan claramente el comportamiento específico que se está verificando.
2. **Preparación y Limpieza:** Asegúrate de preparar el estado inicial y limpiar después de cada prueba para evitar interferencias entre pruebas.
3. **Pruebas Focalizadas:** Cada prueba debe enfocarse en un solo aspecto del comportamiento para facilitar la identificación de fallos.
4. **Mensajes de Error Claros:** Proporciona mensajes de error claros y descriptivos que ayuden a identificar rápidamente la causa del fallo.

## Mas pruebas mal escritas

### Ejemplo 1: Prueba de Autenticación de Usuario

**Prueba Mal Escrita**
```javascript
function testUserAuthentication() {
    const user = new User("username", "password");
    const authResult = authService.authenticate(user);
    assertTrue(authResult.isAuthenticated, "User should be authenticated");
    assertFalse(authService.authenticate(new User("username", "wrongpassword")).isAuthenticated, "User should not be authenticated with wrong password");
}
```

**Problemas:**
1. **Múltiples Afirmaciones en una Sola Prueba:** Verifica tanto una autenticación exitosa como una fallida, lo que complica la identificación del fallo específico.
2. **Falta de Preparación y Limpieza:** No se asegura de que el estado inicial esté limpio ni se limpia después de la prueba.
3. **Nombres Poco Descriptivos:** El nombre `testUserAuthentication` no especifica claramente el comportamiento que se está probando.

**Mejora:**
```javascript
function testUserAuthentication_ShouldAuthenticateWithValidCredentials() {
    const user = new User("username", "password");
    const authResult = authService.authenticate(user);
    assertTrue(authResult.isAuthenticated, "User should be authenticated with valid credentials.");
}

function testUserAuthentication_ShouldNotAuthenticateWithInvalidCredentials() {
    const user = new User("username", "wrongpassword");
    const authResult = authService.authenticate(user);
    assertFalse(authResult.isAuthenticated, "User should not be authenticated with invalid credentials.");
}
```

### Ejemplo 2: Prueba de Cálculo de Descuentos

**Prueba Mal Escrita**
```javascript
function testDiscountCalculation() {
    const discount = discountService.calculateDiscount(100, 10);
    assertEquals(discount, 10, "Discount should be 10%");
    assertEquals(discountService.calculateDiscount(200, 5), 10, "Discount should be 5%");
}
```

**Problemas:**
1. **Múltiples Afirmaciones en una Sola Prueba:** Verifica múltiples escenarios de descuento en una sola prueba.
2. **Falta de Claridad en los Mensajes de Error:** Los mensajes de error no son suficientemente descriptivos.
3. **Nombres Poco Descriptivos:** El nombre `testDiscountCalculation` no especifica claramente los casos específicos que se están probando.

**Mejora:**
```javascript
function testDiscountCalculation_ShouldCalculateCorrectDiscountForTenPercent() {
    const discount = discountService.calculateDiscount(100, 10);
    assertEquals(discount, 10, "Discount for 100 with 10% should be 10.");
}

function testDiscountCalculation_ShouldCalculateCorrectDiscountForFivePercent() {
    const discount = discountService.calculateDiscount(200, 5);
    assertEquals(discount, 10, "Discount for 200 with 5% should be 10.");
}
```

### Ejemplo 3: Prueba de Creación de Cuenta

**Prueba Mal Escrita**
```javascript
function testAccountCreation() {
    accountService.createAccount("user", "password", "email@example.com");
    assertTrue(database.contains("user"), "User should be in database");
    assertTrue(emailService.sent("email@example.com"), "Email should be sent");
}
```

**Problemas:**
1. **Múltiples Afirmaciones en una Sola Prueba:** Verifica la creación de la cuenta en la base de datos y el envío del correo electrónico en una sola prueba.
2. **Dependencia de Estados Globales:** La prueba puede depender de estados globales de `database` y `emailService`.
3. **Nombres Poco Descriptivos:** El nombre `testAccountCreation` es demasiado genérico.

**Mejora:**
```javascript
function testAccountCreation_ShouldStoreUserInDatabase() {
    accountService.createAccount("user", "password", "email@example.com");
    assertTrue(database.contains("user"), "User 'user' should be in database.");
}

function testAccountCreation_ShouldSendEmail() {
    accountService.createAccount("user", "password", "email@example.com");
    assertTrue(emailService.sent("email@example.com"), "Email should be sent to 'email@example.com'.");
}
```

### Ejemplo 4: Prueba de Actualización de Perfil

**Prueba Mal Escrita**
```javascript
function testProfileUpdate() {
    const user = userService.getUser("user");
    user.setEmail("newemail@example.com");
    userService.updateUser(user);
    assertEquals(user.getEmail(), "newemail@example.com", "Email should be updated");
    user.setPassword("newpassword");
    userService.updateUser(user);
    assertEquals(user.getPassword(), "newpassword", "Password should be updated");
}
```

**Problemas:**
1. **Múltiples Afirmaciones en una Sola Prueba:** Verifica la actualización de correo electrónico y contraseña en una sola prueba.
2. **Nombres Poco Descriptivos:** El nombre `testProfileUpdate` no especifica claramente los aspectos que se están probando.
3. **Falta de Separación de Responsabilidades:** No separa las pruebas de diferentes funcionalidades (correo electrónico y contraseña).

**Mejora:**
```javascript
function testProfileUpdate_ShouldUpdateEmail() {
    const user = userService.getUser("user");
    user.setEmail("newemail@example.com");
    userService.updateUser(user);
    assertEquals(user.getEmail(), "newemail@example.com", "Email should be updated.");
}

function testProfileUpdate_ShouldUpdatePassword() {
    const user = userService.getUser("user");
    user.setPassword("newpassword");
    userService.updateUser(user);
    assertEquals(user.getPassword(), "newpassword", "Password should be updated.");
}
```

```java
@Test
    public void testCalculateSquareRoot_ShouldThrowExceptionForNegativeNumbers() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            MathUtils.calculateSquareRoot(-1);
        });
        assertEquals("Cannot calculate square root of a negative number.", exception.getMessage());
    }
```

### Resumen de Buenas Prácticas

- **Nombres Descriptivos:** Los nombres de las pruebas deben ser específicos y describir claramente el comportamiento que se está verificando.
- **Pruebas Focalizadas:** Cada prueba debe enfocarse en un solo aspecto del comportamiento para facilitar la identificación de fallos.
- **Preparación y Limpieza:** Asegúrate de preparar el estado inicial y limpiar después de cada prueba para evitar interferencias entre pruebas.
- **Mensajes de Error Claros:** Proporciona mensajes de error claros y descriptivos que ayuden a identificar rápidamente la causa del fallo.
- **Uso de Frameworks de Pruebas:** Considera usar frameworks de pruebas como JUnit, Mockito, Jest, o Mocha para estructurar y gestionar tus pruebas de manera eficiente.
- **Manejo de Excepciones:** Agregar pruebas para el manejo de las excepciones que podria provocar la funcionalidad que esta bajo prueba
- **Manejo de casos limite:** Asegúrate de probar los casos extremos, como cadenas vacías, valores nulos y límites máximos de enteros.



Implementar estas buenas prácticas mejorará la claridad, mantenibilidad y fiabilidad de las pruebas, contribuyendo significativamente a la calidad general del software.

## Mocks y Stubs

### Definiciones

#### Mocks
**Mocks** son objetos simulados que imitan el comportamiento de objetos reales en pruebas de software. Además de simular comportamientos, los mocks permiten verificar las interacciones entre objetos, es decir, aseguran que ciertos métodos se llamen con los parámetros esperados durante la prueba. Los mocks son especialmente útiles para probar la lógica de negocio que depende de interacciones con otros componentes o servicios.

#### Stubs
**Stubs** son objetos simulados que proporcionan respuestas predefinidas a llamadas durante una prueba. Los stubs no se centran en verificar interacciones, sino en aislar el componente bajo prueba y proporcionar datos controlados necesarios para la prueba. Los stubs son útiles cuando necesitas simular el comportamiento de dependencias y no estás interesado en cómo se utilizan esas dependencias dentro de la prueba.

### Diferencias Clave

| Característica             | Mocks                               | Stubs                              |
|----------------------------|--------------------------------------|------------------------------------|
| **Propósito**              | Verificar interacciones y comportamientos entre objetos. | Proporcionar respuestas predefinidas a llamadas durante la prueba. |
| **Verificación de Interacción** | Sí, pueden verificar que ciertos métodos se llamen con parámetros específicos. | No, no verifican interacciones. |
| **Comportamiento**         | Simulan el comportamiento y registran las interacciones para la verificación posterior. | Proporcionan datos controlados necesarios para la prueba sin registrar interacciones. |
| **Uso Común**              | Pruebas de lógica de negocio compleja y dependencias entre componentes. | Pruebas de componentes aislados que requieren datos controlados. |
| **Flexibilidad**           | Alta flexibilidad para definir comportamientos y verificar interacciones. | Menor flexibilidad, se centran en proporcionar respuestas fijas. |
| **Configuración**          | Normalmente requieren configuración para definir expectativas y verificar llamadas. | Normalmente se configuran con respuestas fijas para solicitudes específicas. |

### Ejemplo de Uso en Java

Es cierto, en Mockito no existe un concepto explícito de "Stub" como en otros frameworks de pruebas, pero podemos simular el comportamiento de un stub usando mocks con respuestas predefinidas. Aquí, el mock puede actuar como un stub proporcionando datos controlados para nuestras pruebas.

### Ejemplo de Uso de Mockito para Simular Stubs y Mocks

#### Clase a Probar
```java
public class UserService {
    private VerificationService verificationService;

    public UserService(VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    public boolean verifyUser(String userId) {
        return verificationService.isVerified(userId);
    }
}
```

#### Interfaz del Servicio de Verificación
```java
public interface VerificationService {
    boolean isVerified(String userId);
}
```

### Prueba Usando Mockito como Stub

En este ejemplo, usaremos Mockito para simular un stub que siempre devuelve una respuesta predefinida.

#### Prueba
```java
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserServiceTest {

    @Test
    public void testVerifyUser_ShouldReturnTrueForVerifiedUser() {
        // Crear un mock del servicio de verificación que actuará como stub
        VerificationService verificationService = mock(VerificationService.class);

        // Definir el comportamiento del mock para que actúe como un stub
        when(verificationService.isVerified("user123")).thenReturn(true);

        UserService userService = new UserService(verificationService);

        // Ejecutar la prueba
        boolean result = userService.verifyUser("user123");

        // Verificar el resultado
        assertTrue(result, "Verified user should return true.");
    }
}
```

### Prueba Usando Mockito como Mock

En este ejemplo, usaremos Mockito para verificar que el método `isVerified` fue llamado con el parámetro correcto.

#### Prueba
```java
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserServiceTest {

    @Test
    public void testVerifyUser_ShouldCallVerificationService() {
        // Crear un mock del servicio de verificación
        VerificationService verificationServiceMock = mock(VerificationService.class);

        // Definir el comportamiento del mock
        when(verificationServiceMock.isVerified("user123")).thenReturn(true);

        UserService userService = new UserService(verificationServiceMock);

        // Ejecutar la prueba
        boolean result = userService.verifyUser("user123");

        // Verificar que el método fue llamado con el ID de usuario correcto
        verify(verificationServiceMock).isVerified("user123");

        // Verificar el resultado
        assertTrue(result);
    }
}
```

### Resumen

- **Stub con Mockito:**
    - **Propósito:** Proporcionar respuestas predefinidas a llamadas durante la prueba.
    - **Ejemplo:** `when(verificationService.isVerified("user123")).thenReturn(true);`

- **Mock con Mockito:**
    - **Propósito:** Verificar que ciertas interacciones ocurran, como llamadas a métodos con parámetros específicos.
    - **Ejemplo:** `verify(verificationServiceMock).isVerified("user123");`

En resumen, aunque Mockito no tiene una clase específica llamada `Stub`, podemos usar mocks configurados con respuestas predefinidas para lograr el mismo efecto que un stub. Esto permite que las pruebas sean más controladas y específicas, asegurando que el código se comporte de la manera esperada en diferentes escenarios.

- **Mocks** se utilizan para verificar que ciertas interacciones ocurran durante la prueba, asegurando que los métodos correctos se llamen con los parámetros correctos.
- **Stubs** se utilizan para proporcionar datos controlados a las pruebas sin preocuparse por las interacciones entre objetos.
- Los **mocks** son ideales para probar la lógica de negocio compleja y las dependencias, mientras que los **stubs** son útiles para pruebas más simples que requieren datos específicos y no verifican interacciones.

Estas definiciones y ejemplos deberían ayudarte a entender claramente las diferencias y los usos adecuados de mocks y stubs en las pruebas de software.

### Ejemplos Adicionales

#### `OrderService`
```java
public class OrderService {
    private InventoryService inventoryService;
    private PaymentService paymentService;

    public OrderService(InventoryService inventoryService, PaymentService paymentService) {
        this.inventoryService = inventoryService;
        this.paymentService = paymentService;
    }

    public boolean placeOrder(String productId, int quantity, String paymentDetails) {
        if (inventoryService.isProductAvailable(productId, quantity)) {
            boolean paymentProcessed = paymentService.processPayment(paymentDetails);
            if (paymentProcessed) {
                inventoryService.reserveProduct(productId, quantity);
                return true;
            }
        }
        return false;
    }
}
```

#### Interfaces Dependientes
```java
public interface InventoryService {
    boolean isProductAvailable(String productId, int quantity);
    void reserveProduct(String productId, int quantity);
}

public interface PaymentService {
    boolean processPayment(String paymentDetails);
}
```


#### Prueba

##### Prueba del Método `placeOrder` con Stubs y Mocks

```java
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class OrderServiceTest {

    @Test
    public void testPlaceOrder_ShouldReturnTrueWhenOrderIsPlacedSuccessfully() {
        // Crear mocks de los servicios dependientes
        InventoryService inventoryService = mock(InventoryService.class);
        PaymentService paymentService = mock(PaymentService.class);

        // Definir el comportamiento del mock de InventoryService
        when(inventoryService.isProductAvailable("product123", 2)).thenReturn(true);

        // Definir el comportamiento del mock de PaymentService
        when(paymentService.processPayment("validPaymentDetails")).thenReturn(true);

        // Crear la instancia del servicio a probar
        OrderService orderService = new OrderService(inventoryService, paymentService);

        // Ejecutar la prueba
        boolean result = orderService.placeOrder("product123", 2, "validPaymentDetails");

        // Verificar el resultado
        assertTrue(result, "Order should be placed successfully.");

        // Verificar que los métodos se llamaron con los parámetros correctos
        verify(inventoryService).isProductAvailable("product123", 2);
        verify(paymentService).processPayment("validPaymentDetails");
        verify(inventoryService).reserveProduct("product123", 2);
    }

    @Test
    public void testPlaceOrder_ShouldReturnFalseWhenProductIsNotAvailable() {
        // Crear mocks de los servicios dependientes
        InventoryService inventoryService = mock(InventoryService.class);
        PaymentService paymentService = mock(PaymentService.class);

        // Definir el comportamiento del mock de InventoryService
        when(inventoryService.isProductAvailable("product123", 2)).thenReturn(false);

        // Crear la instancia del servicio a probar
        OrderService orderService = new OrderService(inventoryService, paymentService);

        // Ejecutar la prueba
        boolean result = orderService.placeOrder("product123", 2, "validPaymentDetails");

        // Verificar el resultado
        assertFalse(result, "Order should not be placed when product is not available.");

        // Verificar que los métodos se llamaron con los parámetros correctos
        verify(inventoryService).isProductAvailable("product123", 2);
        verify(paymentService, never()).processPayment(anyString());
        verify(inventoryService, never()).reserveProduct(anyString(), anyInt());
    }

    @Test
    public void testPlaceOrder_ShouldReturnFalseWhenPaymentFails() {
        // Crear mocks de los servicios dependientes
        InventoryService inventoryService = mock(InventoryService.class);
        PaymentService paymentService = mock(PaymentService.class);

        // Definir el comportamiento del mock de InventoryService
        when(inventoryService.isProductAvailable("product123", 2)).thenReturn(true);

        // Definir el comportamiento del mock de PaymentService
        when(paymentService.processPayment("invalidPaymentDetails")).thenReturn(false);

        // Crear la instancia del servicio a probar
        OrderService orderService = new OrderService(inventoryService, paymentService);

        // Ejecutar la prueba
        boolean result = orderService.placeOrder("product123", 2, "invalidPaymentDetails");

        // Verificar el resultado
        assertFalse(result, "Order should not be placed when payment fails.");

        // Verificar que los métodos se llamaron con los parámetros correctos
        verify(inventoryService).isProductAvailable("product123", 2);
        verify(paymentService).processPayment("invalidPaymentDetails");
        verify(inventoryService, never()).reserveProduct(anyString(), anyInt());
    }
}
```

### Explicaciones

1. **Prueba de Pedido Exitoso:**
    - **Objetivo:** Verificar que un pedido se coloque correctamente cuando el producto está disponible y el pago se procesa exitosamente.
    - **Mock del `InventoryService`:** Simula que el producto está disponible.
    - **Mock del `PaymentService`:** Simula que el pago se procesa correctamente.
    - **Verificaciones:**
        - La orden se coloca exitosamente (`assertTrue(result)`).
        - Los métodos del servicio de inventario y de pago se llaman con los parámetros correctos.
        - El producto se reserva en el inventario.

2. **Prueba de Producto No Disponible:**
    - **Objetivo:** Verificar que un pedido no se coloque cuando el producto no está disponible.
    - **Mock del `InventoryService`:** Simula que el producto no está disponible.
    - **Verificaciones:**
        - La orden no se coloca (`assertFalse(result)`).
        - El método de procesamiento de pago no se llama.
        - El método de reserva de producto no se llama.

3. **Prueba de Fallo en el Pago:**
    - **Objetivo:** Verificar que un pedido no se coloque cuando el pago falla.
    - **Mock del `InventoryService`:** Simula que el producto está disponible.
    - **Mock del `PaymentService`:** Simula que el pago falla.
    - **Verificaciones:**
        - La orden no se coloca (`assertFalse(result)`).
        - El producto no se reserva en el inventario.

### Resumen

Estos ejemplos muestran cómo usar Mockito para crear mocks que actúan como stubs (proporcionando respuestas predefinidas) y también para verificar interacciones específicas. Esta combinación permite pruebas más robustas y detalladas, asegurando que el código se comporte correctamente en diversos escenarios.

## Mas ejemplos
Stubs y mocks en Node.js

### Código de Ejemplo

#### `OrderService.js`
```javascript
class OrderService {
  constructor(inventoryService, paymentService) {
    this.inventoryService = inventoryService;
    this.paymentService = paymentService;
  }

  async placeOrder(productId, quantity, paymentDetails) {
    if (await this.inventoryService.isProductAvailable(productId, quantity)) {
      const paymentProcessed = await this.paymentService.processPayment(paymentDetails);
      if (paymentProcessed) {
        await this.inventoryService.reserveProduct(productId, quantity);
        return true;
      }
    }
    return false;
  }
}

module.exports = OrderService;
```

#### `InventoryService.js`
```javascript
class InventoryService {
  async isProductAvailable(productId, quantity) {
    // Lógica real
  }

  async reserveProduct(productId, quantity) {
    // Lógica real
  }
}

module.exports = InventoryService;
```

#### `PaymentService.js`
```javascript
class PaymentService {
  async processPayment(paymentDetails) {
    // Lógica real
  }
}

module.exports = PaymentService;
```

### Pruebas Usando Mocha y Sinon

#### `OrderService.test.js`
```javascript
const { expect } = require('chai');
const sinon = require('sinon');
const OrderService = require('./OrderService');
const InventoryService = require('./InventoryService');
const PaymentService = require('./PaymentService');

describe('OrderService', () => {
  let inventoryService;
  let paymentService;
  let orderService;

  beforeEach(() => {
    inventoryService = sinon.createStubInstance(InventoryService);
    paymentService = sinon.createStubInstance(PaymentService);
    orderService = new OrderService(inventoryService, paymentService);
  });

  afterEach(() => {
    sinon.restore();
  });

  it('should place order successfully when product is available and payment is processed', async () => {
    inventoryService.isProductAvailable.resolves(true);
    paymentService.processPayment.resolves(true);

    const result = await orderService.placeOrder('product123', 2, 'validPaymentDetails');

    expect(result).to.be.true;
    sinon.assert.calledWith(inventoryService.isProductAvailable, 'product123', 2);
    sinon.assert.calledWith(paymentService.processPayment, 'validPaymentDetails');
    sinon.assert.calledWith(inventoryService.reserveProduct, 'product123', 2);
  });

  it('should not place order when product is not available', async () => {
    inventoryService.isProductAvailable.resolves(false);

    const result = await orderService.placeOrder('product123', 2, 'validPaymentDetails');

    expect(result).to.be.false;
    sinon.assert.calledWith(inventoryService.isProductAvailable, 'product123', 2);
    sinon.assert.notCalled(paymentService.processPayment);
    sinon.assert.notCalled(inventoryService.reserveProduct);
  });

  it('should not place order when payment fails', async () => {
    inventoryService.isProductAvailable.resolves(true);
    paymentService.processPayment.resolves(false);

    const result = await orderService.placeOrder('product123', 2, 'invalidPaymentDetails');

    expect(result).to.be.false;
    sinon.assert.calledWith(inventoryService.isProductAvailable, 'product123', 2);
    sinon.assert.calledWith(paymentService.processPayment, 'invalidPaymentDetails');
    sinon.assert.notCalled(inventoryService.reserveProduct);
  });
});
```

### Explicaciones

1. **Prueba de Pedido Exitoso:**
    - **Objetivo:** Verificar que un pedido se coloque correctamente cuando el producto está disponible y el pago se procesa exitosamente.
    - **Stub del `InventoryService`:** Simula que el producto está disponible.
    - **Stub del `PaymentService`:** Simula que el pago se procesa correctamente.
    - **Verificaciones:**
        - La orden se coloca exitosamente (`expect(result).to.be.true`).
        - Los métodos del servicio de inventario y de pago se llaman con los parámetros correctos.
        - El producto se reserva en el inventario.

2. **Prueba de Producto No Disponible:**
    - **Objetivo:** Verificar que un pedido no se coloque cuando el producto no está disponible.
    - **Stub del `InventoryService`:** Simula que el producto no está disponible.
    - **Verificaciones:**
        - La orden no se coloca (`expect(result).to.be.false`).
        - El método de procesamiento de pago no se llama.
        - El método de reserva de producto no se llama.

3. **Prueba de Fallo en el Pago:**
    - **Objetivo:** Verificar que un pedido no se coloque cuando el pago falla.
    - **Stub del `InventoryService`:** Simula que el producto está disponible.
    - **Stub del `PaymentService`:** Simula que el pago falla.
    - **Verificaciones:**
        - La orden no se coloca (`expect(result).to.be.false`).
        - El producto no se reserva en el inventario.

### Resumen

Estos ejemplos muestran cómo usar Mocha y Sinon para crear stubs y mocks en Node.js, permitiendo pruebas más robustas y detalladas. Al seguir estas prácticas, se puede asegurar que el código se comporte correctamente en diversos escenarios, mejorando así la calidad del software.