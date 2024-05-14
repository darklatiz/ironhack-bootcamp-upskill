# DAY 2 - GUIA

Las habilidades blandas, también conocidas como habilidades interpersonales o habilidades sociales, son los atributos personales que permiten a una persona interactuar de manera efectiva y armoniosa con otras personas en el ambiente laboral y en la vida cotidiana. Estas habilidades son cruciales para los profesionales en todas las industrias, incluyendo el desarrollo de software, donde la capacidad técnica debe complementarse con competencias que faciliten la comunicación y la colaboración.

Aquí te detallo algunas de las habilidades blandas más importantes para un desarrollador senior:

### 1. **Comunicación Efectiva**

*   **Descripción:** Capacidad de expresar ideas de manera clara y concisa, tanto verbalmente como por escrito, adaptándose a distintos públicos.
*   **Importancia:** Facilita la transmisión de ideas, la resolución de problemas y la gestión de proyectos de manera eficiente.


### 2. **Trabajo en Equipo**

*   **Descripción:** Habilidad para colaborar con otros, respetando las opiniones ajenas y contribuyendo al éxito del grupo.
*   **Importancia:** Esencial para el éxito en entornos multidisciplinarios donde la colaboración es clave para alcanzar objetivos comunes.

### 3. **Resolución de Conflictos**

*   **Descripción:** Capacidad para manejar y resolver disputas de manera efectiva, buscando soluciones que beneficien a todas las partes involucradas.
*   **Importancia:** Previene y minimiza las interrupciones y tensiones en el equipo, manteniendo un ambiente laboral positivo.


### 4. **Liderazgo**

*   **Descripción:** Aptitud para guiar a otros, tomar decisiones estratégicas y motivar al equipo hacia el logro de metas.
*   **Importancia:** Impulsa el progreso del equipo y modela comportamientos positivos en proyectos de cualquier escala.


### 5. **Gestión del Tiempo**

*   **Descripción:** Habilidad para organizar y priorizar tareas de manera que se maximice la eficiencia personal y del equipo.
*   **Importancia:** Fundamental para cumplir con plazos y gestionar múltiples proyectos sin comprometer la calidad.


### 6. **Adaptabilidad**

*   **Descripción:** Capacidad para ajustarse a cambios y desafíos rápidamente, manteniendo la eficacia bajo presión.
*   **Importancia:** Permite a los profesionales manejar las rápidas evoluciones en tecnología y metodologías de proyecto.


### 7. **Empatía**

*   **Descripción:** Habilidad para entender y compartir los sentimientos de otros, lo que facilita una mejor comunicación y relaciones interpersonales.
*   **Importancia:** Ayuda a crear un ambiente de trabajo respetuoso y comprensivo, donde todos los miembros se sientan valorados.


El desarrollo de estas habilidades blandas es esencial no solo para quienes aspiran a posiciones senior, sino para cualquier profesional que busque tener éxito en un ambiente colaborativo y en constante cambio. Integrar el desarrollo de estas habilidades en tu formación y práctica profesional puede marcar la diferencia en tu carrera.

## SOLID 

Es un acrónimo que representa cinco principios fundamentales de diseño orientado a objetos, los cuales ayudan a los desarrolladores a crear sistemas de software más entendibles, flexibles y mantenibles. Estos principios fueron promovidos por Robert C. Martin, también conocido como Uncle Bob, y son ampliamente aceptados en la comunidad de desarrollo de software. Aquí están los cinco principios de SOLID:

## **SRP - Principio de Responsabilidad Única (Single Responsibility Principle)**
  *   **Definición:** Una clase debe tener una, y solo una, razón para cambiar, lo que significa que una clase debe tener solo una tarea o responsabilidad.
  *   **Objetivo:** Minimizar la interdependencia y aumentar la cohesión entre los módulos del software.


### Ejemplo
Supongamos que estamos trabajando en un sistema empresarial para la gestión de inventarios que no solo maneja productos, 
sino también las categorías de los productos y su relación con los proveedores. 
La gestión del inventario es crítica y necesita integrarse con otros sistemas, como sistemas de contabilidad y CRM.

#### Clase Product Manager
```java
public class ProductManager {
    private DatabaseConnection database;
    private EmailService emailService;
    private LogService logService;

    public ProductManager(DatabaseConnection database, EmailService emailService, LogService logService) {
        this.database = database;
        this.emailService = emailService;
        this.logService = logService;
    }

    public void addProduct(Product product, User createdBy) {
        if (validateProduct(product)) {
            saveProductToDatabase(product);
            logService.log("Product added: " + product.getId());
            emailService.sendEmail(createdBy.getEmail(), "Product Added", "A new product has been added: " + product.getName());
        }
    }

    private boolean validateProduct(Product product) {
        return product.getPrice() > 0 && product.getQuantity() >= 0 && product.getCategory() != null;
    }

    private void saveProductToDatabase(Product product) {
        database.execute("INSERT INTO products ...");
        // Assume more complex SQL handling, possibly involving transactions or multiple tables
    }

    // Additional methods for removing products, updating products, etc.
}

```
### Problemas con Este Diseño
1. Violación de SRP: La clase ProductManager maneja la lógica de negocio de productos, la interacción con la base de datos, logging, y notificaciones por correo electrónico.
2. Alta dependencia: Cualquier cambio en las dependencias (como EmailService o LogService) puede requerir cambios en ProductManager.
3. Pruebas difíciles: Mockear todas las dependencias y asegurar que interactúan correctamente entre sí complica las pruebas.

### Refactorización Avanzada Utilizando SRP y Patrones de Diseño
1. Dividir `ProductManager` en múltiples clases:
- `ProductService`: Maneja solo la lógica de negocio relacionada con productos.
- `ProductRepository`: Encargado exclusivamente de la interacción con la base de datos para productos.
- `LoggingService`: Servicio independiente para manejar el logging.
- `NotificationService`: Servicio independiente para manejar notificaciones, como correos electrónicos.

```java
public class ProductService {
    private ProductRepository repository;
    private LoggingService logger;
    private NotificationService notifier;

    public ProductService(ProductRepository repository, LoggingService logger, NotificationService notifier) {
        this.repository = repository;
        this.logger = logger;
        this.notifier = notifier;
    }

    public void addProduct(Product product, User createdBy) {
        if (repository.validateProduct(product)) {
            repository.saveProduct(product);
            logger.log("Product added: " + product.getId());
            notifier.sendNotification(createdBy.getEmail(), "Product Added", "A new product has been added: " + product.getName());
        }
    }
}

```

### Beneficios de Esta Refactorización

*   **Cohesión Mejorada:** Cada servicio o clase tiene una única responsabilidad.

*   **Facilidad de Mantenimiento y Escalabilidad:** Los cambios en un área específica del código afectan solo a esa área.

*   **Mejor capacidad de prueba:** Cada componente puede ser probado de forma aislada.

*   **Flexibilidad:** Los servicios pueden ser reemplazados o modificados independientemente unos de otros.


Este enfoque no solo cumple con SRP, sino que también introduce un diseño más modular y escalable, ideal para sistemas complejos y entornos de desarrollo que involucran múltiples equipos o servicios.


### **OCP - Principio de Abierto/Cerrado (Open/Closed Principle)**
  *   **Definición:** Las entidades de software (clases, módulos, funciones, etc.) deben estar abiertas para extensión, pero cerradas para modificación.
  *   **Objetivo:** Permitir que el comportamiento de una entidad pueda ser extendido sin modificar su código fuente.

Principio de Abierto/Cerrado (Open/Closed Principle, OCP) con un ejemplo más complejo, adecuado para desarrolladores de nivel mid-senior a senior. Este principio es fundamental en diseño de software y sostiene que las entidades de software (como clases, módulos, funciones, etc.) deben estar abiertas para la extensión, pero cerradas para la modificación. Esto significa que deberías poder agregar nuevas funcionalidades sin cambiar el código existente.

### Contexto

Consideremos un sistema de procesamiento de pagos donde diferentes tipos de pagos deben ser procesados, tales como tarjetas de crédito, PayPal, y transferencias bancarias. Inicialmente, se podría tener una implementación que verifica el tipo de pago y ejecuta el código correspondiente basado en esa verificación. Sin embargo, este diseño viola el OCP porque cada vez que se añade un nuevo método de pago, el código existente debe modificarse.

### Ejemplo Original Violando OCP

Aquí tienes cómo podría verse el código inicial que viola OCP:
```java
public class PaymentProcessor {
    public void processPayment(Payment payment) {
        if (payment.getType() == PaymentType.CREDIT_CARD) {
            processCreditCardPayment(payment);
        } else if (payment.getType() == PaymentType.PAYPAL) {
            processPayPalPayment(payment);
        }
        // Cada vez que se añade un nuevo método de pago, se modifica este método.
    }

    private void processCreditCardPayment(Payment payment) {
        // Lógica específica de tarjeta de crédito
    }

    private void processPayPalPayment(Payment payment) {
        // Lógica específica de PayPal
    }
}

```
### Refactorización Cumpliendo OCP

Para adherir al OCP, podemos refactorizar este diseño utilizando el patrón de estrategia, que permite cambiar el comportamiento de un algoritmo en tiempo de ejecución. Vamos a definir una interfaz de procesamiento de pago y clases concretas para cada tipo de pago.

```java
public interface PaymentProcessor {
    void processPayment(Payment payment);
}

public class CreditCardPaymentProcessor implements PaymentProcessor {
    public void processPayment(Payment payment) {
        // Lógica específica de tarjeta de crédito
    }
}

public class PayPalPaymentProcessor implements PaymentProcessor {
    public void processPayment(Payment payment) {
        // Lógica específica de PayPal
    }
}

public class PaymentProcessorFactory {
    public static PaymentProcessor getPaymentProcessor(PaymentType type) {
        switch (type) {
            case CREDIT_CARD:
                return new CreditCardPaymentProcessor();
            case PAYPAL:
                return new PayPalPaymentProcessor();
            default:
                throw new IllegalArgumentException("Unsupported payment type");
        }
    }
}

```

```java
public class PaymentService {
    public void processPayment(Payment payment) {
        PaymentProcessor processor = PaymentProcessorFactory.getPaymentProcessor(payment.getType());
        processor.processPayment(payment);
    }
}

```
### Beneficios de esta Refactorización

*   **Extensibilidad:** Se pueden agregar nuevos procesadores de pagos sin modificar el código existente, solo añadiendo nuevas clases que implementen **PaymentProcessor**.

*   **Cumplimiento de OCP:** La clase **PaymentService** y los procesadores de pagos ahora cumplen con el OCP, ya que las modificaciones futuras no requieren cambios en estas clases.

*   **Separación de preocupaciones:** Cada clase tiene una sola responsabilidad, facilitando la mantenibilidad y las pruebas.

### Codificacion en vivo
```java
package com.activemesa.solid.ocp;

import java.util.List;
import java.util.stream.Stream;

enum Color
{
  RED, GREEN, BLUE
}

enum Size
{
  SMALL, MEDIUM, LARGE, YUGE
}

class Product
{
  public String name;
  public Color color;
  public Size size;

  public Product(String name, Color color, Size size) {
    this.name = name;
    this.color = color;
    this.size = size;
  }
}

class ProductFilter
{
  public Stream<Product> filterByColor(List<Product> products, Color color)
  {
    return products.stream().filter(p -> p.color == color);
  }

  public Stream<Product> filterBySize(List<Product> products, Size size)
  {
    return products.stream().filter(p -> p.size == size);
  }

  public Stream<Product> filterBySizeAndColor(List<Product> products, Size size, Color color)
  {
    return products.stream().filter(p -> p.size == size && p.color == color);
  }
  // state space explosion
  // 3 criteria = 7 methods
}

// we introduce two new interfaces that are open for extension
interface Specification<T>
{
  boolean isSatisfied(T item);
}

interface Filter<T>
{
  Stream<T> filter(List<T> items, Specification<T> spec);
}

class ColorSpecification implements Specification<Product>
{
  private Color color;

  public ColorSpecification(Color color) {
    this.color = color;
  }

  @Override
  public boolean isSatisfied(Product p) {
    return p.color == color;
  }
}

class SizeSpecification implements Specification<Product>
{
  private Size size;

  public SizeSpecification(Size size) {
    this.size = size;
  }

  @Override
  public boolean isSatisfied(Product p) {
    return p.size == size;
  }
}

class AndSpecification<T> implements Specification<T>
{
  private Specification<T> first, second;

  public AndSpecification(Specification<T> first, Specification<T> second) {
    this.first = first;
    this.second = second;
  }

  @Override
  public boolean isSatisfied(T item) {
    return first.isSatisfied(item) && second.isSatisfied(item);
  }

}

class BetterFilter implements Filter<Product>
{
  @Override
  public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
    return items.stream().filter(p -> spec.isSatisfied(p));
  }
}

class OCPDemo
{
  public static void main(String[] args) {
    Product apple = new Product("Apple", Color.GREEN, Size.SMALL);
    Product tree = new Product("Tree", Color.GREEN, Size.LARGE);
    Product house = new Product("House", Color.BLUE, Size.LARGE);

    List<Product> products = List.of(apple, tree, house);

    ProductFilter pf = new ProductFilter();
    System.out.println("Green products (old):");
    pf.filterByColor(products, Color.GREEN)
      .forEach(p -> System.out.println(" - " + p.name + " is green"));

    // ^^ BEFORE

    // vv AFTER
    BetterFilter bf = new BetterFilter();
    System.out.println("Green products (new):");
    bf.filter(products, new ColorSpecification(Color.GREEN))
      .forEach(p -> System.out.println(" - " + p.name + " is green"));

    System.out.println("Large products:");
    bf.filter(products, new SizeSpecification(Size.LARGE))
      .forEach(p -> System.out.println(" - " + p.name + " is large"));

    System.out.println("Large blue items:");
    bf.filter(products,
      new AndSpecification<>(
        new ColorSpecification(Color.BLUE),
        new SizeSpecification(Size.LARGE)
      ))
      .forEach(p -> System.out.println(" - " + p.name + " is large and blue"));
  }
}
```

### **L - Principio de Sustitución de Liskov (Liskov Substitution Principle)**
  *   **Definición:** Los objetos de una superclase deben ser sustituibles por objetos de sus subclases sin alterar la corrección del programa.
  *   **Objetivo:** Promover la consistencia y evitar errores al reemplazar una instancia de superclase con una de sus subclases.

### Contexto

Imagina un sistema de gestión de contenido digital donde los usuarios pueden cargar, visualizar y modificar diferentes tipos de medios. Estos medios podrían incluir imágenes, videos y, potencialmente, archivos de audio.

### Diseño Original que Podría Violar LSP

En una primera implementación, podríamos tener una clase base **Media** con métodos comunes y algunas subclases que implementan comportamientos específicos de cada tipo de medio:
```java
public abstract class Media {
    public abstract void display();
    public abstract void resize(int width, int height);
}

public class Image extends Media {
    @Override
    public void display() {
        System.out.println("Displaying image");
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("Resizing image to width: " + width + ", height: " + height);
    }
}

public class Video extends Media {
    @Override
    public void display() {
        System.out.println("Playing video");
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("Changing video resolution to width: " + width + ", height: " + height);
    }
}
```
### Problema

Aunque a primera vista parece que no hay problema, si introducimos un tipo de medio que naturalmente no debería ser redimensionable, como un archivo de audio, podríamos enfrentar una violación del LSP si forzamos a **Audio** a implementar **resize**, lo que no tiene sentido para ese tipo de medio:

```java
public class Audio extends Media {
    @Override
    public void display() {
        System.out.println("Playing audio");
    }

    @Override
    public void resize(int width, int height) {
        throw new UnsupportedOperationException("Cannot resize audio");
    }
}

```

### Refactorización para Cumplir con LSP

Para adherir a LSP, necesitamos asegurarnos de que cada subclase pueda ser utilizada en lugar de la clase base sin requerir cambios en el comportamiento esperado. Podemos refactorizar utilizando interfaces para separar los comportamientos que no son comunes a todos los tipos de medios:

```java
public interface Displayable {
    void display();
}

public interface Resizable {
    void resize(int width, int height);
}

public class Image implements Displayable, Resizable {
    public void display() {
        System.out.println("Displaying image");
    }

    public void resize(int width, int height) {
        System.out.println("Resizing image to width: " + width + ", height: " + height);
    }
}

public class Video implements Displayable, Resizable {
    public void display() {
        System.out.println("Playing video");
    }

    public void resize(int width, int height) {
        System.out.println("Changing video resolution to width: " + width + ", height: " + height);
    }
}

public class Audio implements Displayable {
    public void display() {
        System.out.println("Playing audio");
    }
}

```

### Beneficios de la Refactorización

*   **Cumplimiento de LSP:** Cada clase ahora solo implementa las interfaces que corresponden a las operaciones que realmente puede realizar, evitando el problema de tener que implementar métodos que no tienen sentido para ciertos tipos de medios.

*   **Mayor Flexibilidad y Escalabilidad:** Nuevos tipos de medios pueden ser añadidos fácilmente, implementando solo las interfaces pertinentes sin afectar las expectativas de las operaciones de otros tipos de medios.

*   **Claridad y Mantenibilidad:** Separando las capacidades de visualización y redimensionamiento, el sistema se vuelve más claro y más fácil de mantener.

## **I - Principio de Segregación de la Interfaz (Interface Segregation Principle)**
  *   **Definición:** Los clientes no deben ser forzados a depender de interfaces que no usan.
  *   **Objetivo:** Promover interfaces delgadas y específicas que no sobrecarguen al implementador con métodos que no necesitan.


El Principio de Segregación de Interfaces (ISP) es uno de los cinco principios SOLID de diseño de software orientado a objetos y establece que ninguna clase debería verse forzada a implementar interfaces que no utiliza. Este principio promueve interfaces más pequeñas y específicas en lugar de una interfaz grande que sirve múltiples propósitos. Veamos cómo aplicar ISP a través de un ejemplo práctico.

### Contexto

Imaginemos que estamos desarrollando un sistema para un centro educativo que maneja tanto empleados como recursos didácticos. En este sistema, necesitamos tanto gestionar las actividades de los empleados (profesores y personal administrativo) como los recursos didácticos (libros, equipos de laboratorio, etc.).

### Diseño Original que Podría Violar ISP

Inicialmente, podríamos tener una interfaz grande que incluya métodos tanto para la gestión de empleados como para la gestión de recursos didácticos:
```java
public interface SchoolManagement {
    void addEmployee(Employee employee);
    void removeEmployee(String employeeId);
    void addResource(Resource resource);
    void removeResource(String resourceId);
    void printEmployeeDetails(String employeeId);
    void printResourceDetails(String resourceId);
}
```

En este diseño, cualquier clase que implemente **SchoolManagement** necesitaría implementar todos estos métodos, incluso si solo necesita manejar empleados o solo recursos, lo que viola ISP.

### Problema

Un objeto que solo necesita gestionar recursos, como **ResourceCoordinator**, no debería tener que implementar métodos relacionados con empleados, y viceversa para un **HumanResources** que solo gestiona empleados.

### Refactorización para Cumplir con ISP

Para adherir a ISP, debemos dividir esta interfaz grande en varias interfaces más pequeñas y especializadas:
```java
public interface EmployeeManagement {
    void addEmployee(Employee employee);
    void removeEmployee(String employeeId);
    void printEmployeeDetails(String employeeId);
}

public interface ResourceManagement {
    void addResource(Resource resource);
    void removeResource(String resourceId);
    void printResourceDetails(String resourceId);
}

```

### Implementación de las Interfaces

Con las interfaces segregadas, diferentes clases pueden implementar solo las interfaces que necesitan:

```java
public class HumanResources implements EmployeeManagement {
    @Override
    public void addEmployee(Employee employee) {
        System.out.println("Adding employee");
    }

    @Override
    public void removeEmployee(String employeeId) {
        System.out.println("Removing employee");
    }

    @Override
    public void printEmployeeDetails(String employeeId) {
        System.out.println("Printing employee details");
    }
}

public class ResourceCoordinator implements ResourceManagement {
    @Override
    public void addResource(Resource resource) {
        System.out.println("Adding resource");
    }

    @Override
    public void removeResource(String resourceId) {
        System.out.println("Removing resource");
    }

    @Override
    public void printResourceDetails(String resourceId) {
        System.out.println("Printing resource details");
    }
}

```

### Beneficios de la Refactorización

*   **Adherencia a ISP:** Las clases ahora solo necesitan implementar las interfaces que utilizan, evitando la necesidad de implementar métodos irrelevantes.

*   **Claridad y Mantenibilidad Mejoradas:** Cada interfaz tiene un propósito claro y bien definido, lo que mejora la legibilidad y la mantenibilidad del código.

*   **Flexibilidad y Escalabilidad:** Con interfaces más pequeñas, es más fácil gestionar cambios y extensiones futuras, ya que los cambios en una interfaz no afectan a las clases que dependen de otras interfaces.

## **D - Principio de Inversión de Dependencias (Dependency Inversion Principle)**
  *   **Definición:** Los módulos de alto nivel no deben depender de módulos de bajo nivel. Ambos deben depender de abstracciones. Además, las abstracciones no deben depender de detalles; los detalles deben depender de abstracciones.
  *   **Objetivo:** Disminuir la dependencia directa entre los módulos de bajo nivel y los de alto nivel, aumentando la reusabilidad y la flexibilidad del código.


El Principio de Inversión de Dependencias (Dependency Inversion Principle, DIP) es otro componente esencial de los principios SOLID de la programación orientada a objetos. DIP aborda las relaciones entre los módulos de software de alto y bajo nivel, proponiendo que ambos deberían depender de abstracciones en lugar de detalles concretos. Este principio busca reducir la dependencia directa entre los componentes del software, facilitando la mantenibilidad y la escalabilidad.

### Dos partes clave del DIP:

1.  **Los módulos de alto nivel no deben depender de los módulos de bajo nivel. Ambos deberían depender de abstracciones.**

2.  **Las abstracciones no deben depender de los detalles. Los detalles deben depender de las abstracciones.**


Esto significa que la comunicación entre diferentes partes del software debe ser gestionada a través de interfaces o clases abstractas en lugar de clases concretas. Esto permite que el comportamiento específico pueda cambiar sin afectar a los módulos que dependen de él, lo que es crucial para la construcción de sistemas flexibles y desacoplados.

### Ejemplo Práctico

Imaginemos un sistema de comercio electrónico donde se necesitan notificaciones para diferentes eventos (por ejemplo, cuando se realiza un pedido). La implementación típica sin DIP podría tener un módulo de alto nivel directamente dependiente de módulos específicos de bajo nivel, como un sistema de envío de emails.

#### Diseño sin DIP
```java
class OrderProcessor {
    private EmailNotification emailNotifier = new EmailNotification();

    public void processOrder(Order order) {
        // Lógica para procesar el pedido
        emailNotifier.sendEmail(order.getUserEmail(), "Your order has been processed");
    }
}

class EmailNotification {
    public void sendEmail(String email, String message) {
        // Envío de correo electrónico
    }
}

```
Este diseño viola DIP porque **OrderProcessor** depende directamente de **EmailNotification**, un detalle concreto.

#### Aplicando DIP

Para aplicar DIP, introducimos una abstracción entre **OrderProcessor** y el sistema de notificaciones.

```java
interface NotificationService {
    void sendNotification(String to, String message);
}

class EmailNotification implements NotificationService {
    public void sendNotification(String to, String message) {
        // Envío de correo electrónico
    }
}

class OrderProcessor {
    private NotificationService notifier;

    public OrderProcessor(NotificationService notifier) {
        this.notifier = notifier;
    }

    public void processOrder(Order order) {
        // Lógica para procesar el pedido
        notifier.sendNotification(order.getUserEmail(), "Your order has been processed");
    }
}

```

En este diseño, **OrderProcessor** (un módulo de alto nivel) no depende directamente de **EmailNotification** (un detalle de bajo nivel). En cambio, ambos dependen de la abstracción **NotificationService**. Esto significa que la implementación de notificaciones puede cambiar (por ejemplo, a notificaciones por SMS) sin que afecte al **OrderProcessor**.

### Beneficios del DIP

*   **Flexibilidad:** Cambiar o añadir formas de notificación es mucho más fácil y no requiere cambios en **OrderProcessor**.

*   **Facilidad de Prueba:** **OrderProcessor** puede ser probado fácilmente mediante el uso de mock de **NotificationService** en lugar de depender de la implementación real de envío de emails.

*   **Desacoplamiento:** Reduce el acoplamiento entre diferentes partes del sistema, lo que facilita el mantenimiento y la escalabilidad.


Aplicando DIP, el diseño del software no solo se vuelve más limpio y modular, sino también mucho más adaptable y fácil de mantener en el largo plazo.

Aplicar estos principios de SOLID puede ayudar a los desarrolladores a crear software que sea más fácil de entender, mantener y expandir a lo largo del tiempo, contribuyendo significativamente a la calidad y la durabilidad del diseño del software.