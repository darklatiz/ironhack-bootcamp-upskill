# Day 4 Guia - part 2

### Aplicación de Patrones de Diseño en las Pruebas

Los patrones de diseño son soluciones probadas a problemas comunes en el desarrollo de software y pueden aplicarse de manera efectiva en las pruebas para mejorar la mantenibilidad, la claridad y la eficiencia de las mismas. A continuación, se presentan algunos patrones de diseño comunes y cómo pueden aplicarse en el contexto de las pruebas.

### 1. **Patrón de Fábrica (Factory Pattern)**

### Fábrica en Java

#### Interfaz `Role`

```java
public interface Role {
    void performRole();
}
```

#### Clases de Usuario

```java
// Clase base User
public abstract class User implements Role {
    protected String username;
    protected String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}

// Clase Customer (Cliente)
public class Customer extends User {
    private String customerLoyaltyNumber;

    public Customer(String username, String email, String customerLoyaltyNumber) {
        super(username, email);
        this.customerLoyaltyNumber = customerLoyaltyNumber;
    }

    @Override
    public void performRole() {
        System.out.println("Performing customer role tasks.");
    }
}

// Clase Admin (Administrador)
public class Admin extends User {
    private String adminKey;

    public Admin(String username, String email, String adminKey) {
        super(username, email);
        this.adminKey = adminKey;
    }

    @Override
    public void performRole() {
        System.out.println("Performing admin role tasks.");
    }
}

// Clase Supplier (Proveedor)
public class Supplier extends User {
    private String supplierCode;

    public Supplier(String username, String email, String supplierCode) {
        super(username, email);
        this.supplierCode = supplierCode;
    }

    @Override
    public void performRole() {
        System.out.println("Performing supplier role tasks.");
    }
}
```

#### Fábrica de Usuarios

```java
public class UserFactory {
    public static User createUser(String userType, String username, String email, String extraInfo) {
        switch (userType.toLowerCase()) {
            case "customer":
                return new Customer(username, email, extraInfo);
            case "admin":
                return new Admin(username, email, extraInfo);
            case "supplier":
                return new Supplier(username, email, extraInfo);
            default:
                throw new IllegalArgumentException("Invalid user type: " + userType);
        }
    }
}
```

#### Uso en Pruebas

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserFactoryTest {

    @Test
    public void testCreateCustomer() {
        User user = UserFactory.createUser("customer", "cust123", "customer@example.com", "LOYALTY123");

        assertTrue(user instanceof Customer);
        assertEquals("cust123", user.username);
        assertEquals("customer@example.com", user.email);
    }

    @Test
    public void testCreateAdmin() {
        User user = UserFactory.createUser("admin", "admin123", "admin@example.com", "ADMKEY123");

        assertTrue(user instanceof Admin);
        assertEquals("admin123", user.username);
        assertEquals("admin@example.com", user.email);
    }

    @Test
    public void testCreateSupplier() {
        User user = UserFactory.createUser("supplier", "sup123", "supplier@example.com", "SUPCODE123");

        assertTrue(user instanceof Supplier);
        assertEquals("sup123", user.username);
        assertEquals("supplier@example.com", user.email);
    }

    @Test
    public void testCreateInvalidUserType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            UserFactory.createUser("invalidType", "user123", "user@example.com", "INFO123");
        });

        assertEquals("Invalid user type: invalidType", exception.getMessage());
    }
}
```
### Fábrica en Node.js

### Clases de Usuario

```javascript
// Clase base User
class User {
  constructor(username, email) {
    this.username = username;
    this.email = email;
  }

  performRole() {
    throw new Error('Method performRole() must be implemented.');
  }
}

// Clase Customer (Cliente)
class Customer extends User {
  constructor(username, email, customerLoyaltyNumber) {
    super(username, email);
    this.customerLoyaltyNumber = customerLoyaltyNumber;
  }

  performRole() {
    console.log('Performing customer role tasks.');
  }
}

// Clase Admin (Administrador)
class Admin extends User {
  constructor(username, email, adminKey) {
    super(username, email);
    this.adminKey = adminKey;
  }

  performRole() {
    console.log('Performing admin role tasks.');
  }
}

// Clase Supplier (Proveedor)
class Supplier extends User {
  constructor(username, email, supplierCode) {
    super(username, email);
    this.supplierCode = supplierCode;
  }

  performRole() {
    console.log('Performing supplier role tasks.');
  }
}

module.exports = { User, Customer, Admin, Supplier };
```

### Fábrica de Usuarios

```javascript
const { Customer, Admin, Supplier } = require('./User');

class UserFactory {
  static createUser(userType, username, email, extraInfo) {
    switch (userType.toLowerCase()) {
      case 'customer':
        return new Customer(username, email, extraInfo);
      case 'admin':
        return new Admin(username, email, extraInfo);
      case 'supplier':
        return new Supplier(username, email, extraInfo);
      default:
        throw new Error(`Invalid user type: ${userType}`);
    }
  }
}

module.exports = UserFactory;
```

### Uso en Pruebas

```javascript
const { expect } = require('chai');
const UserFactory = require('./UserFactory');
const { Customer, Admin, Supplier } = require('./User');

describe('UserFactory', () => {
  it('should create a Customer', () => {
    const user = UserFactory.createUser('customer', 'cust123', 'customer@example.com', 'LOYALTY123');
    expect(user).to.be.instanceOf(Customer);
    expect(user.username).to.equal('cust123');
    expect(user.email).to.equal('customer@example.com');
  });

  it('should create an Admin', () => {
    const user = UserFactory.createUser('admin', 'admin123', 'admin@example.com', 'ADMKEY123');
    expect(user).to.be.instanceOf(Admin);
    expect(user.username).to.equal('admin123');
    expect(user.email).to.equal('admin@example.com');
  });

  it('should create a Supplier', () => {
    const user = UserFactory.createUser('supplier', 'sup123', 'supplier@example.com', 'SUPCODE123');
    expect(user).to.be.instanceOf(Supplier);
    expect(user.username).to.equal('sup123');
    expect(user.email).to.equal('supplier@example.com');
  });

  it('should throw an error for an invalid user type', () => {
    expect(() => {
      UserFactory.createUser('invalidType', 'user123', 'user@example.com', 'INFO123');
    }).to.throw('Invalid user type: invalidType');
  });
});
```

### Conclusión

El uso del patrón de fábrica en un contexto más complejo, como la creación de diferentes tipos de usuarios con configuraciones específicas, muestra cómo este patrón puede ayudar a manejar la complejidad y mejorar la organización y la mantenibilidad del código. Al aplicar el patrón de fábrica, las pruebas también se vuelven más claras y estructuradas, facilitando la creación de objetos necesarios para las pruebas sin repetir código innecesario. Este enfoque mejora la eficiencia y la calidad general del software.

### 2. **Patrón de Singleton (Singleton Pattern)**

**Descripción:** El patrón singleton asegura que una clase tenga una única instancia y proporciona un punto de acceso global a ella.

**Aplicación en Pruebas:**
- **Propósito:** Compartir el mismo objeto de configuración o recurso común entre todas las pruebas para evitar la repetición y mejorar la eficiencia.
- **Ejemplo:** Utilizar un singleton para administrar la conexión a una base de datos simulada.

**Ejemplo en Java:**
```java
public class DatabaseConnection {
    private static DatabaseConnection instance;

    private DatabaseConnection() {
        // Configuración de conexión
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}

// Uso en pruebas
DatabaseConnection dbConnection = DatabaseConnection.getInstance();
```

**Ejemplo en Node.js:**
```javascript
class DatabaseConnection {
  constructor() {
    if (DatabaseConnection.instance == null) {
      // Configuración de conexión
      DatabaseConnection.instance = this;
    }
    return DatabaseConnection.instance;
  }
}

// Uso en pruebas
const dbConnection = new DatabaseConnection();
Object.freeze(dbConnection);
```

### 3. **Patrón de Objeto Nulo (Null Object Pattern)**

El patrón de objeto nulo (Null Object Pattern) puede parecer similar a un stub, ya que ambos proporcionan un comportamiento predeterminado para simplificar las pruebas. Sin embargo, el patrón de objeto nulo es un patrón de diseño formal que puede utilizarse tanto en producción como en pruebas, mientras que los stubs son típicamente usados solo en el contexto de pruebas para simular dependencias.

A continuación, describo cómo se puede usar un stub para simplificar las pruebas de `OrderService`, y luego explico cómo el patrón de objeto nulo puede ir más allá del uso de un stub.

### Usando Stubs en Pruebas

#### Clases en Java

##### Interfaz `PaymentService`
```java
public interface PaymentService {
    boolean processPayment(String paymentDetails);
}
```

##### Stub de `PaymentService`
```java
public class PaymentServiceStub implements PaymentService {
    @Override
    public boolean processPayment(String paymentDetails) {
        // Comportamiento predeterminado del stub
        return true;
    }
}
```

##### Clase a Probar `OrderService`
```java
public class OrderService {
    private PaymentService paymentService;

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public boolean placeOrder(String paymentDetails) {
        return paymentService.processPayment(paymentDetails);
    }
}
```

##### Pruebas para `OrderService` Usando Stubs
```java
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class OrderServiceTest {

    @Test
    public void testPlaceOrderWithPaymentServiceStub() {
        PaymentService paymentService = mock(PaymentService.class);
        OrderService orderService = new OrderService(paymentService);
        
        when(paymentService.processPayment("anyPaymentDetails")).thenReturn(true);
        
        boolean result = orderService.placeOrder("anyPaymentDetails");
        assertTrue(result, "Payment should always succeed with PaymentServiceStub.");
    }
}
```

#### Clases en Node.js

##### Stub de `PaymentService`
```javascript
class PaymentServiceStub {
  processPayment(paymentDetails) {
    // Comportamiento predeterminado del stub
    return true;
  }
}
```

##### Clase a Probar `OrderService`
```javascript
class OrderService {
  constructor(paymentService) {
    this.paymentService = paymentService;
  }

  placeOrder(paymentDetails) {
    return this.paymentService.processPayment(paymentDetails);
  }
}

module.exports = { OrderService, PaymentServiceStub };
```

##### Pruebas para `OrderService` Usando Stubs
```javascript
const { expect } = require('chai');
const { OrderService, PaymentServiceStub } = require('./OrderService');

describe('OrderService', () => {
  it('should always succeed payment with PaymentServiceStub', () => {
    const paymentService = new PaymentServiceStub();
    const orderService = new OrderService(paymentService);

    const result = orderService.placeOrder('anyPaymentDetails');

    expect(result).to.be.true;
  });
});
```

### Diferencia Entre Stubs y el Patrón de Objeto Nulo

**Stubs:**
- **Contexto:** Normalmente se usan solo en pruebas.
- **Propósito:** Simular el comportamiento de dependencias externas para pruebas específicas.
- **Alcance:** Limitado al contexto de pruebas y no suele usarse en producción.

**Patrón de Objeto Nulo:**
- **Contexto:** Puede usarse tanto en producción como en pruebas.
- **Propósito:** Proporcionar una implementación "neutra" o predeterminada que cumpla con la interfaz requerida, evitando la necesidad de comprobaciones de valores nulos.
- **Alcance:** Amplio, ya que se puede usar para simplificar el código de producción y hacerlo más robusto frente a la ausencia de ciertas dependencias.

### Ejemplo Completo de Patrón de Objeto Nulo en Producción

Para ilustrar cómo el patrón de objeto nulo puede ser más útil en un entorno de producción, consideremos un escenario donde `PaymentService` podría no estar disponible en ciertos contextos (por ejemplo, en modo de demostración).

#### Clases en Java

##### Implementación del Objeto Nulo de `PaymentService`
```java
public class NullPaymentService implements PaymentService {
    @Override
    public boolean processPayment(String paymentDetails) {
        // No hace nada y siempre devuelve true
        return true;
    }
}
```

##### Uso del Objeto Nulo en `OrderService`
```java
public class OrderService {
    private PaymentService paymentService;

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public boolean placeOrder(String paymentDetails) {
        return paymentService.processPayment(paymentDetails);
    }
}

// En el código de producción
PaymentService paymentService = isDemoMode ? new NullPaymentService() : new RealPaymentService();
OrderService orderService = new OrderService(paymentService);
```

#### Clases en Node.js

##### Implementación del Objeto Nulo de `PaymentService`
```javascript
class NullPaymentService {
  processPayment(paymentDetails) {
    // No hace nada y siempre devuelve true
    return true;
  }
}
```

##### Uso del Objeto Nulo en `OrderService`
```javascript
class OrderService {
  constructor(paymentService) {
    this.paymentService = paymentService;
  }

  placeOrder(paymentDetails) {
    return this.paymentService.processPayment(paymentDetails);
  }
}

// En el código de producción
const paymentService = isDemoMode ? new NullPaymentService() : new RealPaymentService();
const orderService = new OrderService(paymentService);
```

### Conclusión

Tanto los stubs como el patrón de objeto nulo pueden simplificar las pruebas y la implementación del código. Sin embargo, el patrón de objeto nulo tiene la ventaja adicional de ser útil también en el código de producción, proporcionando una forma clara y robusta de manejar la ausencia de ciertas dependencias sin necesidad de comprobaciones adicionales. Esto lo hace particularmente valioso en escenarios donde algunas dependencias pueden no estar disponibles o no deberían realizar ninguna acción.

### 4. **Patrón de Construcción (Builder Pattern)**

**Descripción:** El patrón de construcción se utiliza para construir un objeto complejo paso a paso.

**Aplicación en Pruebas:**
- **Propósito:** Facilitar la creación de objetos de prueba complejos con múltiples configuraciones.
- **Ejemplo:** Crear usuarios con diferentes propiedades sin necesidad de múltiples constructores.

### Patrón de Construcción (Builder Pattern) en Java

#### Clase `User` con el Patrón Builder

```java
public class User {
    private String username;
    private String password;
    private String email;

    private User(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
    }

    public static class Builder {
        private String username;
        private String password;
        private String email;

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
```

#### Uso en Pruebas

##### Clase a Probar `UserService`

```java
public class UserService {
    public boolean registerUser(User user) {
        // Lógica de registro de usuario
        return true;
    }
}
```

##### Pruebas para `UserService` Usando el Patrón Builder

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Test
    public void testRegisterUserWithBuilder() {
        User user = new User.Builder()
            .setUsername("user1")
            .setPassword("password1")
            .setEmail("user1@example.com")
            .build();

        UserService userService = new UserService();
        boolean result = userService.registerUser(user);

        assertTrue(result, "User should be registered successfully.");
        assertEquals("user1", user.getUsername());
        assertEquals("password1", user.getPassword());
        assertEquals("user1@example.com", user.getEmail());
    }

    @Test
    public void testRegisterUserWithPartialBuilder() {
        User user = new User.Builder()
            .setUsername("user2")
            .setEmail("user2@example.com")
            .build();

        UserService userService = new UserService();
        boolean result = userService.registerUser(user);

        assertTrue(result, "User should be registered successfully with partial data.");
        assertEquals("user2", user.getUsername());
        assertNull(user.getPassword()); // password was not set
        assertEquals("user2@example.com", user.getEmail());
    }
}
```

### Patrón de Construcción (Builder Pattern) en Node.js

#### Clase `User` con el Patrón Builder

```javascript
class User {
  constructor(builder) {
    this.username = builder.username;
    this.password = builder.password;
    this.email = builder.email;
  }

  static get Builder() {
    class Builder {
      setUsername(username) {
        this.username = username;
        return this;
      }

      setPassword(password) {
        this.password = password;
        return this;
      }

      setEmail(email) {
        this.email = email;
        return this;
      }

      build() {
        return new User(this);
      }
    }
    return Builder;
  }
}

module.exports = User;
```

#### Uso en Pruebas

##### Clase a Probar `UserService`

```javascript
class UserService {
  registerUser(user) {
    // Lógica de registro de usuario
    return true;
  }
}

module.exports = UserService;
```

##### Pruebas para `UserService` Usando el Patrón Builder

```javascript
const { expect } = require('chai');
const User = require('./User');
const UserService = require('./UserService');

describe('UserService', () => {
  it('should register user with builder', () => {
    const user = new User.Builder()
      .setUsername('user1')
      .setPassword('password1')
      .setEmail('user1@example.com')
      .build();

    const userService = new UserService();
    const result = userService.registerUser(user);

    expect(result).to.be.true;
    expect(user.username).to.equal('user1');
    expect(user.password).to.equal('password1');
    expect(user.email).to.equal('user1@example.com');
  });

  it('should register user with partial builder', () => {
    const user = new User.Builder()
      .setUsername('user2')
      .setEmail('user2@example.com')
      .build();

    const userService = new UserService();
    const result = userService.registerUser(user);

    expect(result).to.be.true;
    expect(user.username).to.equal('user2');
    expect(user.password).to.be.undefined; // password was not set
    expect(user.email).to.equal('user2@example.com');
  });
});
```

### Conclusión

El patrón de construcción (Builder Pattern) facilita la creación de objetos complejos con múltiples configuraciones de manera clara y concisa. Este patrón es particularmente útil en pruebas, ya que permite crear objetos de prueba con diferentes combinaciones de propiedades sin necesidad de múltiples constructores o métodos de inicialización. 

### 5. **Patrón Decorador (Decorator Pattern)**

**Descripción:** El patrón decorador se utiliza para añadir funcionalidad a un objeto de manera dinámica sin alterar su estructura.

**Aplicación en Pruebas:**
- **Propósito:** Añadir comportamientos adicionales a objetos de prueba sin cambiar su código base.
- **Ejemplo:** Añadir validaciones adicionales a un servicio de pago en las pruebas.

Ejemplos de cómo se pueden escribir pruebas para el patrón de diseño decorador (Decorator Pattern) tanto en Java como en Node.js.

### Patrón Decorador (Decorator Pattern) en Java

#### Interfaz `PaymentService`

```java
public interface PaymentService {
    boolean processPayment(String paymentDetails);
}
```

#### Implementación Real de `PaymentService`

```java
public class RealPaymentService implements PaymentService {
    @Override
    public boolean processPayment(String paymentDetails) {
        // Lógica real de procesamiento de pagos
        return true; // Suponiendo que el pago fue procesado exitosamente
    }
}
```

#### Implementación del Decorador de `PaymentService`

```java
public class LoggingPaymentServiceDecorator implements PaymentService {
    private PaymentService wrapped;

    public LoggingPaymentServiceDecorator(PaymentService wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public boolean processPayment(String paymentDetails) {
        System.out.println("Processing payment with details: " + paymentDetails);
        return wrapped.processPayment(paymentDetails);
    }
}
```

#### Clase a Probar `OrderService`

```java
public class OrderService {
    private PaymentService paymentService;

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public boolean placeOrder(String paymentDetails) {
        return paymentService.processPayment(paymentDetails);
    }
}
```

#### Pruebas para `OrderService` Usando el Patrón Decorador

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class OrderServiceTest {

    @Test
    public void testPlaceOrderWithLoggingDecorator() {
        // Redirigir la salida estándar para capturar el log
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        PaymentService paymentService = new LoggingPaymentServiceDecorator(new RealPaymentService());
        OrderService orderService = new OrderService(paymentService);

        boolean result = orderService.placeOrder("validPaymentDetails");

        // Verificar el resultado del método
        assertTrue(result, "Payment should be processed successfully.");

        // Verificar el log
        String expectedLog = "Processing payment with details: validPaymentDetails";
        assertTrue(outContent.toString().contains(expectedLog), "Log should contain the expected message.");
    }
}
```

### Patrón Decorador (Decorator Pattern) en Node.js

#### Clase `PaymentService`

```javascript
class PaymentService {
  processPayment(paymentDetails) {
    throw new Error("Method 'processPayment()' must be implemented.");
  }
}
```

#### Implementación Real de `PaymentService`

```javascript
class RealPaymentService extends PaymentService {
  processPayment(paymentDetails) {
    // Lógica real de procesamiento de pagos
    return true; // Suponiendo que el pago fue procesado exitosamente
  }
}
```

#### Implementación del Decorador de `PaymentService`

```javascript
class LoggingPaymentServiceDecorator extends PaymentService {
  constructor(wrapped) {
    super();
    this.wrapped = wrapped;
  }

  processPayment(paymentDetails) {
    console.log("Processing payment with details: " + paymentDetails);
    return this.wrapped.processPayment(paymentDetails);
  }
}

module.exports = LoggingPaymentServiceDecorator;
```

#### Clase a Probar `OrderService`

```javascript
class OrderService {
  constructor(paymentService) {
    this.paymentService = paymentService;
  }

  placeOrder(paymentDetails) {
    return this.paymentService.processPayment(paymentDetails);
  }
}

module.exports = OrderService;
```

#### Pruebas para `OrderService` Usando el Patrón Decorador

```javascript
const { expect } = require('chai');
const sinon = require('sinon');
const OrderService = require('./OrderService');
const RealPaymentService = require('./RealPaymentService');
const LoggingPaymentServiceDecorator = require('./LoggingPaymentServiceDecorator');

describe('OrderService', () => {
  it('should process payment with logging decorator', () => {
    const logSpy = sinon.spy(console, 'log');

    const paymentService = new LoggingPaymentServiceDecorator(new RealPaymentService());
    const orderService = new OrderService(paymentService);

    const result = orderService.placeOrder('validPaymentDetails');

    expect(result).to.be.true;
    expect(logSpy.calledWith('Processing payment with details: validPaymentDetails')).to.be.true;

    logSpy.restore();
  });
});
```

El patrón decorador (Decorator Pattern) permite añadir funcionalidades adicionales a un objeto de manera dinámica sin alterar su estructura. En los ejemplos proporcionados, el decorador de `PaymentService` añade funcionalidad de logging antes de delegar la llamada al objeto `PaymentService` real. Las pruebas aseguran que tanto la funcionalidad original como la añadida por el decorador funcionan correctamente. Este patrón mejora la flexibilidad y la extensibilidad del código, y su uso en pruebas asegura que las nuevas funcionalidades no introduzcan errores.


## 6. Patrón Estrategia (Strategy Pattern)

El patrón Estrategia (Strategy Pattern) permite definir una familia de algoritmos, encapsular cada uno de ellos y hacerlos intercambiables. Este patrón permite que los algoritmos varíen independientemente del cliente que los utiliza.

### Ejemplo Completo del Patrón Estrategia en Java

#### Interfaz `PaymentStrategy`

```java
public interface PaymentStrategy {
    boolean processPayment(String paymentDetails);
}
```

#### Implementaciones de `PaymentStrategy`

##### `CreditCardPaymentStrategy`

```java
public class CreditCardPaymentStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment(String paymentDetails) {
        // Lógica de procesamiento de pago con tarjeta de crédito
        System.out.println("Processing credit card payment with details: " + paymentDetails);
        return true; // Suponiendo que el pago fue procesado exitosamente
    }
}
```

##### `PayPalPaymentStrategy`

```java
public class PayPalPaymentStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment(String paymentDetails) {
        // Lógica de procesamiento de pago con PayPal
        System.out.println("Processing PayPal payment with details: " + paymentDetails);
        return true; // Suponiendo que el pago fue procesado exitosamente
    }
}
```

#### Clase a Probar `OrderService`

```java
public class OrderService {
    private PaymentStrategy paymentStrategy;

    public OrderService(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public boolean placeOrder(String paymentDetails) {
        return paymentStrategy.processPayment(paymentDetails);
    }
}
```

#### Pruebas para `OrderService` Usando el Patrón Estrategia

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {

    @Test
    public void testPlaceOrderWithCreditCardPaymentStrategy() {
        PaymentStrategy paymentStrategy = new CreditCardPaymentStrategy();
        OrderService orderService = new OrderService(paymentStrategy);

        boolean result = orderService.placeOrder("creditCardDetails");

        assertTrue(result, "Payment should be processed successfully with credit card.");
    }

    @Test
    public void testPlaceOrderWithPayPalPaymentStrategy() {
        PaymentStrategy paymentStrategy = new PayPalPaymentStrategy();
        OrderService orderService = new OrderService(paymentStrategy);

        boolean result = orderService.placeOrder("paypalDetails");

        assertTrue(result, "Payment should be processed successfully with PayPal.");
    }
}
```

### Patrón Estrategia (Strategy Pattern) en Node.js

#### Interfaz `PaymentStrategy`

No es obligatorio tener una interfaz en JavaScript, pero vamos a simular la estructura.

```javascript
class PaymentStrategy {
  processPayment(paymentDetails) {
    throw new Error("Method 'processPayment()' must be implemented.");
  }
}

module.exports = PaymentStrategy;
```

#### Implementaciones de `PaymentStrategy`

##### `CreditCardPaymentStrategy`

```javascript
const PaymentStrategy = require('./PaymentStrategy');

class CreditCardPaymentStrategy extends PaymentStrategy {
  processPayment(paymentDetails) {
    // Lógica de procesamiento de pago con tarjeta de crédito
    console.log("Processing credit card payment with details: " + paymentDetails);
    return true; // Suponiendo que el pago fue procesado exitosamente
  }
}

module.exports = CreditCardPaymentStrategy;
```

##### `PayPalPaymentStrategy`

```javascript
const PaymentStrategy = require('./PaymentStrategy');

class PayPalPaymentStrategy extends PaymentStrategy {
  processPayment(paymentDetails) {
    // Lógica de procesamiento de pago con PayPal
    console.log("Processing PayPal payment with details: " + paymentDetails);
    return true; // Suponiendo que el pago fue procesado exitosamente
  }
}

module.exports = PayPalPaymentStrategy;
```

#### Clase a Probar `OrderService`

```javascript
class OrderService {
  constructor(paymentStrategy) {
    this.paymentStrategy = paymentStrategy;
  }

  placeOrder(paymentDetails) {
    return this.paymentStrategy.processPayment(paymentDetails);
  }
}

module.exports = OrderService;
```

#### Pruebas para `OrderService` Usando el Patrón Estrategia

```javascript
const { expect } = require('chai');
const OrderService = require('./OrderService');
const CreditCardPaymentStrategy = require('./CreditCardPaymentStrategy');
const PayPalPaymentStrategy = require('./PayPalPaymentStrategy');

describe('OrderService', () => {
  it('should process payment with credit card strategy', () => {
    const paymentStrategy = new CreditCardPaymentStrategy();
    const orderService = new OrderService(paymentStrategy);

    const result = orderService.placeOrder('creditCardDetails');

    expect(result).to.be.true;
  });

  it('should process payment with PayPal strategy', () => {
    const paymentStrategy = new PayPalPaymentStrategy();
    const orderService = new OrderService(paymentStrategy);

    const result = orderService.placeOrder('paypalDetails');

    expect(result).to.be.true;
  });
});
```

### Conclusión

El patrón Estrategia (Strategy Pattern) permite intercambiar algoritmos de manera flexible y dinámica sin cambiar el código cliente que los usa. En los ejemplos proporcionados, hemos implementado dos estrategias de pago (`CreditCardPaymentStrategy` y `PayPalPaymentStrategy`) y las hemos utilizado en `OrderService`. Las pruebas aseguran que ambas estrategias funcionen correctamente. Este patrón mejora la extensibilidad del código y facilita la adición de nuevas estrategias en el futuro sin modificar el código existente.


### Conclusión

Aplicar patrones de diseño en las pruebas de software puede mejorar significativamente la calidad y la mantenibilidad de las pruebas. Los patrones de diseño como el patrón de fábrica, singleton, objeto nulo, construcción y decorador pueden ayudar a crear pruebas más claras, organizadas y reutilizables. Al adoptar estos patrones, los desarrolladores pueden escribir pruebas más efectivas y fáciles de mantener, lo que a su vez mejora la calidad general del software.