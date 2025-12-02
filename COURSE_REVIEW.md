# CPSC 310: Software Design - Course Review

## Fall 2025 | Trinity College

This document summarizes the key concepts, patterns, and skills covered throughout the semester.

---

## Week 1: Java Foundations & Team Formation

### Core Concepts
- **Course Philosophy:** Embrace AI as a tool while maintaining critical thinking
- **Java vs Python:** Static typing, explicit declarations, compile-time safety
- **Classes & Objects:** Constructors, instance variables, encapsulation
- **Access Modifiers:** `public`, `private`, `protected`, package-private
- **Null Safety:** Understanding `NullPointerException` and defensive coding

### Key Skills
- Creating classes with proper encapsulation
- Using collections (List, Set, Map)
- Basic exception handling (try-catch-finally)
- Understanding Java's type system

### Remember
- Java is verbose by design—explicitness aids readability
- AI generates code; you verify and understand it
- Tests validate behavior before implementation

---

## Week 2: Object-Oriented Programming

### Inheritance & Polymorphism
- **extends:** Creates parent-child relationship
- **super():** Calls parent constructor (must be first statement)
- **@Override:** Signals intentional method override
- **Polymorphism:** Parent type references can hold child instances

### Abstract Classes vs Interfaces
| Feature | Abstract Class | Interface |
|---------|---------------|-----------|
| Methods | Can have concrete methods | Default methods (Java 8+) |
| Fields | Can have instance fields | Only constants |
| Inheritance | Single inheritance | Multiple inheritance |
| Use when | Sharing code among related classes | Defining contracts |

### Template Method Pattern
```java
public abstract class PaymentProcessor {
    public final void process(Payment p) {  // Template method
        validate(p);
        executePayment(p);  // Abstract - subclass implements
        sendConfirmation(p);
    }
    protected abstract void executePayment(Payment p);
}
```

### Repository Pattern
- Interface defines contract for data access
- Implementation handles storage details
- Enables easy testing with mock implementations

---

## Week 3: Spring Boot & REST APIs

### Layered Architecture
```
Controller (handles HTTP)
    ↓
Service (business logic)
    ↓
Repository (data access)
```

### HTTP Methods & CRUD
| Operation | HTTP Method | Typical Status Codes |
|-----------|-------------|---------------------|
| Create | POST | 201 Created |
| Read | GET | 200 OK, 404 Not Found |
| Update | PUT/PATCH | 200 OK, 204 No Content |
| Delete | DELETE | 204 No Content |

### Key Annotations
- `@RestController` - Combines @Controller + @ResponseBody
- `@RequestMapping` - Base path for controller
- `@GetMapping`, `@PostMapping`, etc. - HTTP method handlers
- `@PathVariable` - Extract from URL path
- `@RequestBody` - Deserialize JSON body
- `@Valid` - Trigger validation

### ResponseEntity
```java
return ResponseEntity
    .status(HttpStatus.CREATED)
    .body(createdTask);
```

### Testing with @WebMvcTest
- Slice test—only loads web layer
- Use MockMvc for HTTP simulation
- jsonPath() for response assertions

---

## Week 4: Functional Programming & TDD

### Test-Driven Development Cycle
1. **Red:** Write a failing test
2. **Green:** Write minimal code to pass
3. **Refactor:** Improve design, keep tests green

### Lambda Expressions
```java
// Traditional anonymous class
Comparator<String> c1 = new Comparator<String>() {
    public int compare(String a, String b) { return a.length() - b.length(); }
};

// Lambda
Comparator<String> c2 = (a, b) -> a.length() - b.length();

// Method reference
Comparator<String> c3 = Comparator.comparingInt(String::length);
```

### Stream API Operations
| Type | Operations | Returns |
|------|-----------|---------|
| Intermediate | filter, map, flatMap, distinct, sorted | Stream |
| Terminal | collect, count, forEach, anyMatch, findFirst | Result |

```java
List<String> names = people.stream()
    .filter(p -> p.getAge() >= 18)
    .map(Person::getName)
    .sorted()
    .collect(Collectors.toList());
```

### Optional
```java
Optional<User> user = repository.findById(id);

// Safe patterns
String name = user.map(User::getName).orElse("Unknown");
user.ifPresent(u -> sendEmail(u));

// Avoid
user.get();  // Throws if empty!
```

---

## Week 5: Spring Data JPA & AI Evaluation

### JPA Entities
```java
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    private User assignee;
}
```

### Repository Query Methods
```java
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(Status status);
    List<Task> findByTitleContainingIgnoreCase(String title);
    @Query("SELECT t FROM Task t WHERE t.dueDate < :date")
    List<Task> findOverdue(@Param("date") LocalDate date);
}
```

### AI Code Red Flags
- Field injection instead of constructor injection
- `.get()` on Optional without checking
- Catching generic `Exception`
- Missing input validation
- Deprecated APIs or patterns
- Over-engineered solutions

### The 4 C's of AI Prompting
1. **Context:** Provide background and constraints
2. **Constraints:** Specify requirements and limitations
3. **Clarity:** Be precise about expected behavior
4. **Code Examples:** Show desired patterns

---

## Week 6: SOLID Principles

### Single Responsibility Principle (SRP)
> A class should have only one reason to change.

**Violation:** Employee class handles data, payroll calculation, AND persistence.
**Solution:** Separate into Employee, PayrollCalculator, EmployeeRepository.

### Open-Closed Principle (OCP)
> Open for extension, closed for modification.

**Implementation:** Use Strategy pattern—add new strategies without changing existing code.

```java
public interface DiscountStrategy {
    double apply(double price);
}
// Add new discounts by implementing interface, not modifying existing code
```

### Liskov Substitution Principle (LSP)
> Subtypes must be substitutable for their base types.

**Classic Violation:** Square extends Rectangle (setWidth breaks invariants).
**Solution:** Use interfaces that reflect actual behavior contracts.

### Interface Segregation Principle (ISP)
> Clients should not depend on interfaces they don't use.

**Violation:** `Worker` interface with `work()`, `eat()`, `sleep()` forces robots to implement `eat()`.
**Solution:** Split into `Workable`, `Eatable`, `Sleepable`.

### Dependency Inversion Principle (DIP)
> Depend on abstractions, not concretions.

```java
// Bad: NotificationService depends on EmailSender (concrete)
// Good: NotificationService depends on MessageService (interface)
public NotificationService(MessageService messenger) {
    this.messenger = messenger;
}
```

### DRY (Don't Repeat Yourself)
- Extract common validation logic
- Centralize constants and configuration
- Create reusable utility methods
- Single source of truth for business rules

---

## Week 7: Behavioral Design Patterns

### Strategy Pattern
**Purpose:** Define a family of algorithms, encapsulate each one, make them interchangeable.

```java
public interface PaymentStrategy {
    void pay(BigDecimal amount);
}

public class ShoppingCart {
    public void checkout(PaymentStrategy strategy) {
        strategy.pay(total);
    }
}
```

**When to use:** Multiple algorithms for same task, algorithm selection at runtime.

### Command Pattern
**Purpose:** Encapsulate a request as an object.

```java
public interface Command {
    void execute();
    void undo();
}
```

**When to use:** Undo/redo, queuing operations, macro recording.

### Template Method Pattern
**Purpose:** Define skeleton of algorithm, let subclasses override specific steps.

**When to use:** Common algorithm structure with varying implementations.

---

## Week 8: Creational Design Patterns

### Singleton Pattern
**Purpose:** Ensure only one instance exists.

```java
public class ConfigManager {
    private static final ConfigManager INSTANCE = new ConfigManager();
    private ConfigManager() {}
    public static ConfigManager getInstance() { return INSTANCE; }
}
```

**Caution:** Often overused; consider dependency injection instead.

### Factory Method Pattern
**Purpose:** Let subclasses decide which class to instantiate.

```java
public interface NotificationFactory {
    Notification createNotification();
}
```

**When to use:** Object creation logic is complex or varies by context.

### Builder Pattern
**Purpose:** Construct complex objects step by step.

```java
User user = User.builder()
    .name("Alice")
    .email("alice@example.com")
    .role(Role.ADMIN)
    .build();
```

**When to use:** Many optional parameters, immutable objects, readable construction.

---

## Week 9: Structural Design Patterns

### Adapter Pattern
**Purpose:** Convert interface of one class to another clients expect.

**When to use:** Integrating legacy code, third-party libraries with different interfaces.

### Decorator Pattern
**Purpose:** Attach additional responsibilities dynamically.

```java
Coffee coffee = new Espresso();
coffee = new MilkDecorator(coffee);
coffee = new SugarDecorator(coffee);
```

**When to use:** Adding features without subclass explosion.

### Facade Pattern
**Purpose:** Provide simplified interface to complex subsystem.

**When to use:** Simplifying complex APIs, reducing coupling to subsystems.

### Composite Pattern
**Purpose:** Treat individual objects and compositions uniformly.

**When to use:** Tree structures, part-whole hierarchies.

---

## Week 12: Spring AI Integration

### ChatClient Basics
```java
@Autowired
private ChatClient chatClient;

public String ask(String question) {
    return chatClient.prompt()
        .user(question)
        .call()
        .content();
}
```

### Key Considerations
- Configure API keys securely (environment variables)
- Handle rate limits and errors gracefully
- Consider response streaming for long outputs
- Test AI interactions with mocks

---

## Week 13: TDD Deep Dive

### JUnit 5 Features
- `@Nested` - Organize related tests hierarchically
- `@ParameterizedTest` - Run same test with different inputs
- `@DisplayName` - Readable test names
- `assertAll()` - Group related assertions

### AssertJ Fluent Assertions
```java
assertThat(list)
    .hasSize(3)
    .contains("Alice", "Bob")
    .doesNotContain("Charlie");

assertThat(person)
    .extracting(Person::getName, Person::getAge)
    .containsExactly("Alice", 30);
```

### Property-Based Testing (jqwik)
```java
@Property
void additionIsCommutative(@ForAll int a, @ForAll int b) {
    assertThat(a + b).isEqualTo(b + a);
}
```

---

## Cross-Cutting Themes

### Testing Philosophy
- **Test First:** Write tests before implementation (TDD)
- **Test at All Layers:** Unit, integration, slice tests
- **80%+ Coverage:** Target for assignments
- **Tests as Documentation:** Tests show how code should behave

### AI as Development Partner
- AI generates; you verify
- Always review AI code for:
  - Security vulnerabilities
  - SOLID violations
  - Edge cases
  - Outdated patterns
- Use AI to accelerate, not replace understanding

### Spring Boot Patterns
- Constructor injection over field injection
- DTOs for API boundaries, entities for persistence
- Slice tests (@WebMvcTest, @DataJpaTest) over @SpringBootTest
- Validation at system boundaries

---

## Quick Reference: When to Use What

| Problem | Solution |
|---------|----------|
| Multiple algorithms, runtime selection | Strategy Pattern |
| Complex object construction | Builder Pattern |
| Single instance needed | Singleton (or DI scope) |
| Incompatible interfaces | Adapter Pattern |
| Add behavior without inheritance | Decorator Pattern |
| Simplify complex subsystem | Facade Pattern |
| Undo/redo functionality | Command Pattern |
| Tree/hierarchy structures | Composite Pattern |
| Shared algorithm, varying steps | Template Method |

---

## Key Technologies & Versions

| Technology | Version |
|------------|---------|
| Java | 21 LTS |
| Spring Boot | 3.5.5 |
| JUnit | 5.11.0 |
| AssertJ | 3.26.3 |
| jqwik | 1.9.1 |
| Gradle | 8.11.1 |

---

## Final Reminders

1. **SOLID principles are guidelines, not laws**—apply judgment
2. **Prefer composition over inheritance**—more flexible
3. **Test behavior, not implementation**—tests survive refactoring
4. **Keep it simple**—don't over-engineer
5. **AI assists; you decide**—maintain critical thinking

Good luck on your exams!
