---
theme: seriph
background: https://source.unsplash.com/1920x1080/?design,architecture
class: text-center
highlighter: shiki
lineNumbers: false
info: |
  ## SOLID Principles
  
  CPSC 310: Software Design
  Trinity College, Fall 2025
  
  Kenneth Kousen
drawings:
  persist: false
transition: slide-left
title: "SOLID Principles: Foundation of Good Design"
mdc: true
---

# SOLID Principles: Foundation of Good Design

<div class="pt-12">
  <span @click="$slidev.nav.next" class="px-2 py-1 rounded cursor-pointer" hover="bg-white bg-opacity-10">
    Building maintainable software with SOLID design principles <carbon:arrow-right class="inline"/>
  </span>
</div>

---

# What is SOLID?

<div class="flex items-center justify-between">
<div>

<v-clicks>

- **S** - Single Responsibility Principle
- **O** - Open-Closed Principle  
- **L** - Liskov Substitution Principle
- **I** - Interface Segregation Principle
- **D** - Dependency Inversion Principle

</v-clicks>

</div>
<div class="w-1/3">
<img src="https://images.unsplash.com/photo-1557804506-669a67965ba0?w=400&h=300&fit=crop&brightness=1.2" alt="Building blocks" class="rounded-lg opacity-50" />
</div>
</div>

---

# Why SOLID Matters

<v-clicks>

- Makes code more maintainable
- Reduces coupling between components
- Improves testability
- Enables easier extension and modification
- Creates more flexible architectures

</v-clicks>

---

# Single Responsibility Principle (SRP)

> A class should have only one reason to change

<v-clicks>

- Each class should have one responsibility
- Separation of concerns
- High cohesion within classes
- Low coupling between classes

</v-clicks>

---

# SRP: Before

```java
public class Employee {
    private String name;
    private double salary;
    
    public void calculatePay() {
        // Complex payroll calculation logic
    }
    
    public void saveToDatabase() {
        // Database connection and SQL logic
    }
    
    public void sendEmail() {
        // Email sending logic
    }
    
    public String generateReport() {
        // Report generation logic
    }
}
```

Too many responsibilities!

---

# SRP: After

```java
public class Employee {
    private String name;
    private double salary;
    // Employee data only
}

public class PayrollCalculator {
    public double calculatePay(Employee employee) {
        // Payroll logic
    }
}

public class EmployeeRepository {
    public void save(Employee employee) {
        // Database logic
    }
}

public class EmailService {
    public void sendPayslip(Employee employee, double pay) {
        // Email logic
    }
}
```

---

# SRP: Real-World Example

```java
// Bad: Mixed responsibilities
public class UserService {
    public User createUser(String email, String password) {
        // Validation logic
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
        
        // Password hashing
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        
        // Database save
        Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO users (email, password) VALUES (?, ?)"
        );
        // ... SQL execution
        
        // Send welcome email
        EmailSender.send(email, "Welcome!", "Thanks for joining!");
        
        return new User(email);
    }
}
```

---

# SRP: Refactored

```java
public class UserService {
    private final UserValidator validator;
    private final PasswordEncoder encoder;
    private final UserRepository repository;
    private final NotificationService notifier;
    
    public User createUser(String email, String password) {
        validator.validate(email, password);
        String encoded = encoder.encode(password);
        User user = new User(email, encoded);
        repository.save(user);
        notifier.sendWelcomeEmail(user);
        return user;
    }
}
```

Each component has a single responsibility!

---

# Open-Closed Principle (OCP)

> Software entities should be open for extension but closed for modification

<v-clicks>

- Add new functionality without changing existing code
- Use abstraction and polymorphism
- Rely on interfaces and abstract classes
- Strategy pattern is a perfect example

</v-clicks>

---

# OCP: Before

```java
public class DiscountCalculator {
    public double calculateDiscount(Customer customer, double amount) {
        if (customer.getType() == CustomerType.REGULAR) {
            return amount * 0.05;
        } else if (customer.getType() == CustomerType.PREMIUM) {
            return amount * 0.10;
        } else if (customer.getType() == CustomerType.VIP) {
            return amount * 0.20;
        }
        return 0;
    }
}
```

What happens when we add a new customer type?

---

# OCP: After

```java
public interface DiscountStrategy {
    double calculateDiscount(double amount);
}

public class RegularDiscount implements DiscountStrategy {
    public double calculateDiscount(double amount) {
        return amount * 0.05;
    }
}

public class PremiumDiscount implements DiscountStrategy {
    public double calculateDiscount(double amount) {
        return amount * 0.10;
    }
}

public class VIPDiscount implements DiscountStrategy {
    public double calculateDiscount(double amount) {
        return amount * 0.20;
    }
}
```

---

# OCP: Using the Strategy

```java
public class Customer {
    private final DiscountStrategy discountStrategy;
    
    public Customer(DiscountStrategy strategy) {
        this.discountStrategy = strategy;
    }
    
    public double getDiscount(double amount) {
        return discountStrategy.calculateDiscount(amount);
    }
}

// Adding new discount types doesn't change existing code
public class StudentDiscount implements DiscountStrategy {
    public double calculateDiscount(double amount) {
        return amount * 0.15;
    }
}
```

---

# OCP: Shape Example

```java
// Bad: Violates OCP
public class AreaCalculator {
    public double calculateArea(Shape shape) {
        if (shape instanceof Rectangle) {
            Rectangle r = (Rectangle) shape;
            return r.width * r.height;
        } else if (shape instanceof Circle) {
            Circle c = (Circle) shape;
            return Math.PI * c.radius * c.radius;
        }
        // Need to modify this method for each new shape!
        return 0;
    }
}
```

---

# OCP: Shape Example Fixed

```java
public interface Shape {
    double calculateArea();
}

public class Rectangle implements Shape {
    private double width, height;
    
    public double calculateArea() {
        return width * height;
    }
}

public class Circle implements Shape {
    private double radius;
    
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

// New shapes can be added without modifying existing code
public class Triangle implements Shape {
    private double base, height;
    
    public double calculateArea() {
        return 0.5 * base * height;
    }
}
```

---

# Liskov Substitution Principle (LSP)

> Objects of a superclass should be replaceable with objects of its subclasses without breaking the application

<v-clicks>

- Subtypes must be substitutable for their base types
- Derived classes must not violate base class behavior
- About behavioral compatibility, not just syntax
- "Is-a" relationship must be preserved

</v-clicks>

---

# LSP: Classic Violation

```java
public class Rectangle {
    protected int width, height;
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getArea() {
        return width * height;
    }
}

public class Square extends Rectangle {
    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width;  // Maintains square invariant
    }
    
    @Override
    public void setHeight(int height) {
        this.width = height;
        this.height = height;
    }
}
```

---

# LSP: Why It's Broken

```java
public void testRectangle(Rectangle rect) {
    rect.setWidth(5);
    rect.setHeight(4);
    
    // This assertion fails for Square!
    assert rect.getArea() == 20;
}

Rectangle rect = new Rectangle();
testRectangle(rect);  // Works fine

Rectangle square = new Square();
testRectangle(square);  // Fails! Area is 16, not 20
```

Square changes the expected behavior of Rectangle

---

# LSP: Better Design

```java
public interface Shape {
    double getArea();
}

public class Rectangle implements Shape {
    private final double width, height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    public double getArea() {
        return width * height;
    }
}

public class Square implements Shape {
    private final double side;
    
    public Square(double side) {
        this.side = side;
    }
    
    public double getArea() {
        return side * side;
    }
}
```

---

# LSP: Real Example - Collections

```java
// Bad: Violates LSP
public class ReadOnlyList<T> extends ArrayList<T> {
    @Override
    public boolean add(T element) {
        throw new UnsupportedOperationException("Read-only list");
    }
    
    @Override
    public void clear() {
        throw new UnsupportedOperationException("Read-only list");
    }
}

// Code expecting ArrayList behavior will break
public void processItems(ArrayList<String> items) {
    items.add("New Item");  // Throws exception with ReadOnlyList!
}
```

---

# LSP: Correct Approach

```java
public interface ReadableList<T> {
    T get(int index);
    int size();
    boolean contains(T item);
}

public interface ModifiableList<T> extends ReadableList<T> {
    void add(T item);
    void remove(int index);
    void clear();
}

public class ArrayList<T> implements ModifiableList<T> {
    // Full implementation
}

public class ReadOnlyList<T> implements ReadableList<T> {
    // Only implements read operations
}
```

---

# Interface Segregation Principle (ISP)

> Clients should not be forced to depend on interfaces they don't use

<v-clicks>

- Prefer small, focused interfaces
- Avoid "fat" interfaces
- Interface pollution leads to unnecessary dependencies
- Split large interfaces into smaller, cohesive ones

</v-clicks>

---

# ISP: Before

```java
public interface Worker {
    void work();
    void eat();
    void sleep();
    void attendMeeting();
    void writeCode();
    void testCode();
    void deployCode();
    void fixBugs();
}

public class Developer implements Worker {
    // Must implement all methods, even irrelevant ones
}

public class Robot implements Worker {
    public void eat() {
        throw new UnsupportedOperationException("Robots don't eat");
    }
    
    public void sleep() {
        throw new UnsupportedOperationException("Robots don't sleep");
    }
    // ... forced to implement human-specific methods
}
```

---

# ISP: After

```java
public interface Workable {
    void work();
}

public interface Eatable {
    void eat();
}

public interface Sleepable {
    void sleep();
}

public interface Codeable {
    void writeCode();
    void testCode();
    void fixBugs();
}

public class Developer implements Workable, Eatable, Sleepable, Codeable {
    // Implements all relevant interfaces
}

public class Robot implements Workable {
    // Only implements what makes sense for robots
}
```

---

# ISP: Real Example - Document Processing

```java
// Bad: Fat interface
public interface Document {
    void open();
    void save();
    void print();
    void fax();
    void scan();
    void email();
    void encrypt();
    void compress();
}

// Modern printer might not support fax
public class ModernPrinter implements Document {
    public void fax() {
        throw new UnsupportedOperationException("No fax support");
    }
    // ... forced to implement all methods
}
```

---

# ISP: Segregated Interfaces

```java
public interface Readable {
    void open();
}

public interface Saveable {
    void save();
}

public interface Printable {
    void print();
}

public interface Scannable {
    void scan();
}

public class SimplePrinter implements Printable {
    public void print() {
        // Just printing functionality
    }
}

public class MultiFunctionPrinter implements Printable, Scannable {
    public void print() { /* ... */ }
    public void scan() { /* ... */ }
}
```

---

# Dependency Inversion Principle (DIP)

> High-level modules should not depend on low-level modules. Both should depend on abstractions.

<v-clicks>

- Depend on abstractions, not concretions
- Interfaces define contracts
- Implementation details are hidden
- Enables dependency injection

</v-clicks>

---

# DIP: Before

```java
public class EmailService {
    private SmtpServer smtpServer;
    
    public EmailService() {
        // Direct dependency on concrete class
        this.smtpServer = new SmtpServer("mail.example.com", 587);
    }
    
    public void sendEmail(String to, String subject, String body) {
        smtpServer.connect();
        smtpServer.authenticate("user", "password");
        smtpServer.send(to, subject, body);
        smtpServer.disconnect();
    }
}
```

Tightly coupled to SMTP implementation!

---

# DIP: After

```java
public interface EmailSender {
    void send(String to, String subject, String body);
}

public class SmtpEmailSender implements EmailSender {
    private final String host;
    private final int port;
    
    public SmtpEmailSender(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    public void send(String to, String subject, String body) {
        // SMTP implementation
    }
}

public class EmailService {
    private final EmailSender emailSender;
    
    public EmailService(EmailSender emailSender) {
        this.emailSender = emailSender;
    }
    
    public void sendWelcomeEmail(User user) {
        emailSender.send(user.getEmail(), "Welcome!", "Thanks for joining!");
    }
}
```

---

# DIP: Benefits

```java
// Easy to test with mock
EmailSender mockSender = new MockEmailSender();
EmailService service = new EmailService(mockSender);

// Easy to switch implementations
EmailSender awsSender = new AwsSesEmailSender();
EmailService service = new EmailService(awsSender);

// Can use different senders for different environments
EmailSender sender = isDevelopment() 
    ? new ConsoleEmailSender() 
    : new SmtpEmailSender(host, port);
```

---

# DIP: Repository Pattern

```java
// Domain layer (high-level)
public interface UserRepository {
    User findById(Long id);
    void save(User user);
    List<User> findByEmail(String email);
}

// Infrastructure layer (low-level)
public class JpaUserRepository implements UserRepository {
    private EntityManager entityManager;
    
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }
    
    public void save(User user) {
        entityManager.persist(user);
    }
    
    public List<User> findByEmail(String email) {
        return entityManager.createQuery(
            "SELECT u FROM User u WHERE u.email = :email", User.class)
            .setParameter("email", email)
            .getResultList();
    }
}
```

---

# SOLID Working Together

```java
// SRP: Each class has one responsibility
// OCP: Easy to add new notification types
// LSP: All notifiers are substitutable
// ISP: Focused interfaces
// DIP: Depends on abstractions

public interface NotificationSender {
    void send(String message, String recipient);
}

public class EmailNotificationSender implements NotificationSender {
    private final EmailService emailService;
    
    public EmailNotificationSender(EmailService emailService) {
        this.emailService = emailService;
    }
    
    public void send(String message, String recipient) {
        emailService.send(recipient, "Notification", message);
    }
}

public class NotificationService {
    private final List<NotificationSender> senders;
    
    public NotificationService(List<NotificationSender> senders) {
        this.senders = senders;
    }
    
    public void notifyAll(String message, List<String> recipients) {
        recipients.forEach(recipient -> 
            senders.forEach(sender -> sender.send(message, recipient))
        );
    }
}
```

---

# SOLID in Modern Java

```java
// Records respect SRP - data carriers only
public record Customer(String name, String email) {}

// Sealed classes help with LSP and OCP
public sealed interface PaymentMethod 
    permits CreditCard, DebitCard, PayPal {}

// Pattern matching supports OCP
public double calculateFee(PaymentMethod method) {
    return switch (method) {
        case CreditCard(var number, var fee) -> fee;
        case DebitCard() -> 0.0;
        case PayPal(var email) -> 2.5;
    };
}
```

---

# Testing with SOLID

```java
// DIP makes testing easy
@Test
void shouldSendWelcomeEmail() {
    // Arrange
    EmailSender mockSender = mock(EmailSender.class);
    UserService service = new UserService(mockSender);
    
    // Act
    User user = service.createUser("test@example.com", "password");
    
    // Assert
    verify(mockSender).send(
        eq("test@example.com"), 
        eq("Welcome!"), 
        anyString()
    );
}
```

---

# SOLID Violations in the Wild

<v-clicks>

- **God Classes**: Violate SRP (do everything)
- **Rigid Hierarchies**: Violate OCP (require modification)
- **Broken Inheritance**: Violate LSP (surprise behavior)
- **Kitchen Sink Interfaces**: Violate ISP (too many methods)
- **Concrete Dependencies**: Violate DIP (hard to test/change)

</v-clicks>

---

# Refactoring to SOLID

1. **Identify violations** in existing code
2. **Extract interfaces** from concrete classes
3. **Split responsibilities** into separate classes
4. **Introduce abstractions** between layers
5. **Use dependency injection** for flexibility
6. **Write tests** to ensure behavior is preserved

---

# SOLID and Design Patterns

<v-clicks>

- **Strategy Pattern**: Perfect example of OCP
- **Factory Pattern**: Supports DIP
- **Decorator Pattern**: Follows OCP and SRP
- **Observer Pattern**: Uses DIP and ISP
- **Repository Pattern**: Classic DIP implementation

</v-clicks>

---

# Common Misconceptions

<v-clicks>

- "SOLID means more interfaces" - No, it means the *right* interfaces
- "SOLID makes code complex" - It makes code *organized*
- "SOLID is only for large projects" - Benefits apply at any scale
- "SOLID is outdated" - Principles are timeless, implementation evolves

</v-clicks>

---

# SOLID with AI-Generated Code

When reviewing AI code, check for:

<v-clicks>

- Classes doing too many things (SRP violation)
- Hard-coded dependencies (DIP violation)
- Switch statements that will need modification (OCP violation)
- Interfaces with too many methods (ISP violation)
- Inheritance that doesn't make sense (LSP violation)

</v-clicks>

---

# Exercise: Identify SOLID Violations

```java
public class OrderService {
    public void processOrder(Order order) {
        // Validate order
        if (order.getItems().isEmpty()) {
            throw new IllegalArgumentException("Empty order");
        }
        
        // Calculate total
        double total = 0;
        for (OrderItem item : order.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }
        
        // Apply discount
        if (order.getCustomer().getType() == "PREMIUM") {
            total *= 0.9;
        }
        
        // Save to database
        Connection conn = DriverManager.getConnection("jdbc:...");
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders...");
        // ... save logic
        
        // Send email
        EmailSender.send(order.getCustomer().getEmail(), 
            "Order confirmed", "Your order total: " + total);
    }
}
```

---

# Key Takeaways

<v-clicks>

- SOLID principles guide us toward better design
- Each principle addresses specific design problems
- They work together to create maintainable systems
- Apply them pragmatically, not dogmatically
- Use them as a lens to evaluate code quality

</v-clicks>

---

# Next Session: ISP and DIP Deep Dive

We'll explore:
- Advanced dependency injection techniques
- Interface design strategies
- Real-world refactoring examples
- Spring Framework and SOLID

---

# Questions?

<div class="text-center">

Ready to build more maintainable software with SOLID principles!

<div class="mt-8">
<img src="https://images.unsplash.com/photo-1516981442399-a91139e20ff8?w=600&h=200&fit=crop" alt="Building blocks" class="rounded-lg mx-auto opacity-60" />
</div>

</div>