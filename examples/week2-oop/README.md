# Week 2 OOP Examples

This module contains comprehensive examples demonstrating Object-Oriented Programming concepts in Java, aligned with Week 2 of CPSC 310.

## Structure

### 1. Inheritance (`edu.trincoll.inheritance`)
- **Employee** - Base class with common employee attributes
- **Manager** - Extends Employee, adds team management
- **Developer** - Extends Employee, adds technical skills tracking
- **Executive** - Extends Manager, adds executive perks

Demonstrates:
- Class inheritance with `extends`
- Method overriding
- Constructor chaining with `super()`
- Protected vs private access
- Polymorphic behavior

### 2. Abstract Classes (`edu.trincoll.abstractclasses`)
- **PaymentProcessor** - Abstract base with template method pattern
- **CreditCardProcessor** - Concrete implementation for credit cards
- **PayPalProcessor** - Concrete implementation for PayPal

Demonstrates:
- Abstract methods requiring implementation
- Template method pattern
- Concrete methods in abstract classes
- Polymorphism through abstract types

### 3. Interfaces (`edu.trincoll.interfaces`)
- **Repository<T, ID>** - Generic repository interface
- **TaskRepository** - Extends Repository with task-specific methods
- **InMemoryTaskRepository** - Concrete implementation
- **Task** - Domain entity class

Demonstrates:
- Interface definition and implementation
- Generic interfaces
- Interface inheritance
- Repository pattern
- Spring `@Repository` annotation

### 4. Collections (`edu.trincoll.collections`)
- **ListExamples** - ArrayList and LinkedList operations
- **SetExamples** - HashSet and TreeSet with set operations
- **MapExamples** - HashMap operations and stream collectors
- **QueueExamples** - PriorityQueue and ArrayDeque
- **CollectionUtilities** - Helper methods and immutable collections

Demonstrates:
- List, Set, Map, Queue interfaces
- Stream operations on collections
- Defensive copying
- Immutable collections
- Collection utilities

## Running the Examples

### Build the module:
```bash
./gradlew :examples:week2-oop:build
```

### Run all tests:
```bash
./gradlew :examples:week2-oop:test
```

### Run specific test class:
```bash
./gradlew :examples:week2-oop:test --tests InheritanceTest
```

## Key Learning Points

### Inheritance
- Use inheritance for "is-a" relationships
- Prefer composition over deep inheritance hierarchies
- Always use `@Override` annotation
- Understand method overriding vs overloading

### Abstract Classes
- Use when you have shared implementation
- Can have state (instance variables)
- Single inheritance only
- Template method pattern for algorithms

### Interfaces
- Use for contracts and "can-do" relationships
- Support multiple inheritance
- Prefer interfaces to abstract classes
- Program to interfaces, not implementations

### Collections
- Choose the right collection for your use case:
  - `List` for ordered, indexed access
  - `Set` for unique elements
  - `Map` for key-value pairs
  - `Queue` for FIFO/priority processing
- Use generics for type safety
- Understand performance characteristics

## Test Coverage

The module includes comprehensive test suites:
- **InheritanceTest** - 20+ tests covering all inheritance scenarios
- **PaymentProcessorTest** - Template method and polymorphism tests
- **RepositoryTest** - Generic repository and interface tests
- **CollectionExamplesTest** - Collection operations and utilities

## Integration with Spring Boot

Examples use Spring annotations where appropriate:
- `@Repository` for repository implementations
- `@Service` could be added to service classes
- Demonstrates dependency injection patterns
- Shows how OOP principles apply in Spring context

## Common Pitfalls to Avoid

1. **Deep inheritance hierarchies** - Keep it shallow (3-4 levels max)
2. **Violating LSP** - Subclasses should be substitutable
3. **Not using generics** - Always use typed collections
4. **Modifying collections while iterating** - Use iterators or streams
5. **Exposing internal state** - Return defensive copies

## Further Reading

- Effective Java by Joshua Bloch
- Spring in Action
- Java Collections Framework documentation
- SOLID principles documentation in course materials