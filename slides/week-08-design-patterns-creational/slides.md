---
theme: seriph
background: https://source.unsplash.com/1920x1080/?blueprint,architecture,construction
class: text-center
highlighter: shiki
lineNumbers: false
info: |
  ## Week 8: Design Patterns - Creational

  CPSC 310: Software Design
  Trinity College, Fall 2025

  Kenneth Kousen
drawings:
  persist: false
transition: slide-left
title: "Week 8: Creational Design Patterns"
mdc: true
---

# Week 8: Creational Design Patterns

## Singleton, Factory & Builder

<div class="pt-12">
  <span @click="$slidev.nav.next" class="px-2 py-1 rounded cursor-pointer" hover="bg-white bg-opacity-10">
    Patterns for flexible object creation <carbon:arrow-right class="inline"/>
  </span>
</div>

<div class="abs-br m-6 flex gap-2">
  <span class="text-sm opacity-50">CPSC 310 | Fall 2025</span>
</div>

---
layout: two-cols
---

# This Week's Plan

<v-clicks>

## Session 14 (Today)
- Creational Patterns Overview
- Singleton Pattern
- Factory Method Pattern
- Modern Java Simplifications

## Session 15 (Next Class)
- Builder Pattern Deep Dive
- Pattern Combinations
- Assignment 5 Walkthrough

</v-clicks>

::right::

<div class="mt-12">
<v-clicks>

### Key Concepts
- Object creation flexibility
- Encapsulating construction
- Open-Closed Principle
- Modern Java static factories

### Examples Today
- Configuration management
- Game character creation
- Complex object building

</v-clicks>
</div>

---

# What Are Creational Patterns?

<v-clicks>

Patterns focused on **how objects are created**

## Why Do We Need Them?

Simple object creation is easy:
```java
var warrior = new Character("Conan", CharacterType.WARRIOR, ...);
```

But what if:
- Construction is complex (many parameters)?
- You need to ensure only one instance exists?
- Creation logic needs to vary by type?
- Clients shouldn't know concrete classes?

Creational patterns solve these problems

</v-clicks>

---

# The Three Creational Patterns

<div class="grid grid-cols-3 gap-4">

<div>

### Singleton
**One instance only**

Ensures a class has only one instance and provides global access

Use for: Configuration, logging, connection pools

</div>

<div>

### Factory Method
**Delegate creation**

Creates objects without specifying exact class

Use for: Multiple types with shared interface

</div>

<div>

### Builder
**Step-by-step construction**

Builds complex objects incrementally

Use for: Objects with many parameters

</div>

</div>

<v-click>

<div class="mt-8 p-4 bg-blue-50 rounded">

💡 **All three patterns**: Decouple object creation from usage

</div>

</v-click>

---
layout: center
---

# Singleton Pattern

## Ensuring only one instance exists

---

# Singleton: The Problem

<v-clicks>

## Scenario: Application Configuration

Multiple parts of your application need access to configuration:

```java
var config1 = new AppConfig();
var config2 = new AppConfig();
// Two different instances! Changes to config1 don't affect config2
```

## Problems
- Multiple instances waste memory
- Inconsistent state across instances
- No single source of truth

## Solution
Ensure **only one instance** can be created

</v-clicks>

---

# Singleton: Classic Implementation

```java
public class AppConfig {
    private static AppConfig instance;

    // Private constructor prevents external instantiation
    private AppConfig() {
        // Load configuration
    }

    // Global access point
    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }
}
```

Usage: `AppConfig config = AppConfig.getInstance();`

---

# Problem: Thread Safety

## Classic singleton isn't thread-safe

```java
public static AppConfig getInstance() {
    if (instance == null) {  // Thread A and B both see null
        instance = new AppConfig();  // Both create instance!
    }
    return instance;
}
```

Two threads could create two instances!

---

# Singleton: Thread-Safe (Eager)

## Eager Initialization

```java
public class AppConfig {
    // Created when class loads - thread-safe
    private static final AppConfig INSTANCE = new AppConfig();

    private AppConfig() {
        // Load configuration
    }

    public static AppConfig getInstance() {
        return INSTANCE;
    }
}
```

<v-clicks>

✓ Thread-safe
✓ Simple
✗ Always created (even if never used)

</v-clicks>

---

# Singleton: Thread-Safe (Lazy)

## Bill Pugh Singleton (Holder Pattern)

```java
public class AppConfig {
    private AppConfig() {}

    // Inner class loaded only when getInstance() called
    private static class Holder {
        private static final AppConfig INSTANCE = new AppConfig();
    }

    public static AppConfig getInstance() {
        return Holder.INSTANCE;
    }
}
```

<v-clicks>

✓ Thread-safe (JVM guarantees)
✓ Lazy initialization
✓ No synchronization overhead

</v-clicks>

---

# Singleton: Modern Java Enum

## The Best Approach

```java
public enum AppConfig {
    INSTANCE;

    private final Properties properties;

    AppConfig() {
        properties = new Properties();
        // Load configuration
    }

    public String get(String key) {
        return properties.getProperty(key);
    }
}
```

Usage: `String value = AppConfig.INSTANCE.get("database.url");`

---

# Why Enum Singleton is Best

<v-clicks>

## Advantages

- **Thread-safe**: JVM guarantees single instance
- **Serialization-safe**: Enum handles serialization correctly
- **Reflection-safe**: Cannot create instances via reflection
- **Simplest**: Minimal boilerplate code

## Joshua Bloch (Effective Java)
> "A single-element enum type is often the best way to implement a singleton"

</v-clicks>

---

# Singleton Real-World Examples

<v-clicks>

## Common Uses

- **Configuration Management**: `AppConfig`, `Settings`
- **Logging**: `Logger.getInstance()`
- **Connection Pools**: `DatabaseConnectionPool`
- **Caches**: `CacheManager.getInstance()`
- **Thread Pools**: `ExecutorService` management

## Spring Framework
Spring beans are singletons by default!

```java
@Service  // Singleton by default
public class UserService { }
```

</v-clicks>

---

# When NOT to Use Singleton

<v-clicks>

## Drawbacks

- **Global state**: Hard to test
- **Hidden dependencies**: Not obvious from method signature
- **Tight coupling**: Everything depends on one instance
- **Testing challenges**: Shared state between tests

## Alternatives

- **Dependency Injection**: Pass dependencies explicitly
- **Spring Framework**: Let container manage lifecycle
- **Functional Approach**: Pass configuration as parameters

Modern advice: **Use dependency injection instead**

</v-clicks>

---

# Singleton: Key Takeaways

<v-clicks>

## Core Concept
Ensure a class has only one instance with global access point

## Implementation Choices
1. **Enum** - Best for most cases
2. **Holder Pattern** - When enum doesn't fit
3. **Eager** - Simple, acceptable for lightweight objects

## Modern Approach
Prefer dependency injection over singleton when possible

</v-clicks>

---
layout: center
---

# Factory Method Pattern

## Delegating object creation to subclasses or methods

---

# Factory Method: The Problem

## Without Factory

```java
public Character createCharacter(String type, String name) {
    if (type.equals("warrior")) {
        return new Character(name, 150, 40, 30,
            new MeleeAttack(), new HeavyArmor());
    } else if (type.equals("mage")) {
        return new Character(name, 80, 60, 10,
            new MagicAttack(), new StandardDefense());
    }
    // More types...
}
```

<v-clicks>

## Problems
- Violates Open-Closed Principle
- Complex parameter lists
- Creation logic scattered

</v-clicks>

---

# Factory Method: Solution

## Encapsulate creation in dedicated methods

```java
public class CharacterFactory {
    public static Character createWarrior(String name) {
        return new Character(
            name,
            CharacterType.WARRIOR,
            CharacterStats.create(150, 40, 30, 0),
            new MeleeAttackStrategy(),
            new HeavyArmorDefenseStrategy()
        );
    }
}
```

Each factory method knows how to create one type

---

# Factory Method Pattern Structure

```java
public class CharacterFactory {
    // Specific factory methods
    public static Character createWarrior(String name) { }
    public static Character createMage(String name) { }
    public static Character createArcher(String name) { }
    public static Character createRogue(String name) { }

    // Generic factory method
    public static Character createCharacter(
            String name, CharacterType type) {
        return switch(type) {
            case WARRIOR -> createWarrior(name);
            case MAGE -> createMage(name);
            case ARCHER -> createArcher(name);
            case ROGUE -> createRogue(name);
        };
    }
}
```

---

# Factory Method Benefits

<v-clicks>

## Advantages

- **Encapsulation**: Complex creation hidden
- **Open-Closed**: Add types without modifying code
- **Single Responsibility**: Creation separate from usage
- **Type Safety**: Return specific types

## Usage

```java
// Clear, readable, correct
Character warrior = CharacterFactory.createWarrior("Conan");
Character mage = CharacterFactory.createMage("Gandalf");
```

No need to remember parameter order or types!

</v-clicks>

---

# Modern Java Static Factories

Java's built-in factory methods:

<v-clicks>

## Collections

```java
List<String> list = List.of("a", "b", "c");
Set<Integer> set = Set.of(1, 2, 3);
Map<String, Integer> map = Map.of("one", 1, "two", 2);
```

## Optional

```java
Optional<String> present = Optional.of("value");
Optional<String> empty = Optional.empty();
Optional<String> maybe = Optional.ofNullable(getValue());
```

## Others
- `String.valueOf()`, `Integer.valueOf()`
- `LocalDate.of()`, `Instant.now()`

</v-clicks>

---

# Factory vs Constructor

## When to use factory methods instead of constructors?

<v-clicks>

### Use Factory When:
- **Name adds clarity**: `createWarrior()` vs `new Character(...)`
- **Complex construction**: Multiple steps required
- **Caching**: Return existing instances
- **Subtype selection**: Return different implementations
- **Validation**: Check parameters before construction

### Use Constructor When:
- Simple object creation
- No special logic needed
- Parameters self-explanatory

</v-clicks>

---

# Factory Method Example: Warrior

From Assignment 5:

```java
public static Character createWarrior(String name) {
    return Character.builder()
        .name(name)
        .type(CharacterType.WARRIOR)
        .stats(CharacterStats.create(150, 40, 30, 0))
        .attackStrategy(new MeleeAttackStrategy())
        .defenseStrategy(new HeavyArmorDefenseStrategy())
        .build();
}
```

Factory combines:
- Appropriate stats for warrior class
- Correct strategies for warrior behavior
- All required fields set properly

---

# Factory Method Example: Mage

```java
public static Character createMage(String name) {
    return Character.builder()
        .name(name)
        .type(CharacterType.MAGE)
        .stats(CharacterStats.create(80, 60, 10, 100))
        .attackStrategy(new MagicAttackStrategy())
        .defenseStrategy(new StandardDefenseStrategy())
        .build();
}
```

<v-clicks>

## Notice:
- Different stats (high mana, low health)
- Different strategies (magic attack)
- Same construction pattern
- Client code stays simple

</v-clicks>

---

# Factory with Switch Expression

## Generic Factory Method (Java 21)

```java
public static Character createCharacter(
        String name, CharacterType type) {
    return switch(type) {
        case WARRIOR -> createWarrior(name);
        case MAGE -> createMage(name);
        case ARCHER -> createArcher(name);
        case ROGUE -> createRogue(name);
    };
}
```

<v-clicks>

✓ Exhaustiveness checking (compiler ensures all cases)
✓ No default needed with enum
✓ Clean delegation to specific factories

</v-clicks>

---

# Factory Method & Open-Closed

## Adding a New Character Type

<v-clicks>

### Step 1: Add to enum
```java
enum CharacterType { WARRIOR, MAGE, ARCHER, ROGUE, PALADIN }
```

### Step 2: Add factory method
```java
public static Character createPaladin(String name) {
    return Character.builder()
        .name(name)
        .type(CharacterType.PALADIN)
        // ...
        .build();
}
```

### Step 3: Update switch
```java
case PALADIN -> createPaladin(name);
```

**No existing code modified!**

</v-clicks>

---

# Factory Method: Key Takeaways

<v-clicks>

## Core Concept
Define interface for creating objects, let methods decide which class to instantiate

## Benefits
- Encapsulates complex creation
- Open-Closed Principle
- Clear, named methods
- Type-safe

## Modern Java
Static factory methods everywhere: `List.of()`, `Optional.of()`, etc.

</v-clicks>

---
layout: center
---

# Builder Pattern

## Constructing complex objects step by step

---

# Builder Pattern: The Problem

## Constructor with Many Parameters

```java
public Character(
    String name,
    CharacterType type,
    CharacterStats stats,
    AttackStrategy attackStrategy,
    DefenseStrategy defenseStrategy
) {
    // ...
}
```

<v-clicks>

## Problems
- Easy to mix up parameter order
- Hard to read: `new Character("Bob", WARRIOR, stats, atk, def)`
- Can't make some parameters optional
- Adding parameters breaks all clients

</v-clicks>

---

# Builder Pattern: Solution

## Fluent API for construction

```java
Character warrior = Character.builder()
    .name("Conan")
    .type(CharacterType.WARRIOR)
    .stats(CharacterStats.create(150, 40, 30, 0))
    .attackStrategy(new MeleeAttackStrategy())
    .defenseStrategy(new HeavyArmorDefenseStrategy())
    .build();
```

<v-clicks>

✓ Clear what each value represents
✓ Any order
✓ Validation in `build()` method
✓ Optional parameters easy to add

</v-clicks>

---

# Builder Pattern Implementation

## Inner Static Builder Class

```java
public class Character {
    // Fields (private final)
    private final String name;
    private final CharacterType type;
    // ...

    // Private constructor
    private Character(String name, CharacterType type, ...) {
        this.name = name;
        this.type = type;
        // ...
    }

    // Static factory for builder
    public static Builder builder() {
        return new Builder();
    }
}
```

---

# Builder Implementation (Part 2)

```java
public static class Builder {
    private String name;
    private CharacterType type;
    private CharacterStats stats;
    private AttackStrategy attackStrategy;
    private DefenseStrategy defenseStrategy;

    public Builder name(String name) {
        this.name = name;
        return this;
    }

    public Builder type(CharacterType type) {
        this.type = type;
        return this;
    }

    // ... more setters
}
```

Each method returns `this` for chaining

---

# Builder: The build() Method

```java
public Character build() {
    // Validate required fields
    if (name == null) {
        throw new IllegalStateException("name is required");
    }
    if (type == null) {
        throw new IllegalStateException("type is required");
    }
    if (stats == null) {
        throw new IllegalStateException("stats is required");
    }
    // ... validate other fields

    // Construct and return
    return new Character(name, type, stats,
        attackStrategy, defenseStrategy);
}
```

Validation ensures object is complete and valid

---

# Builder vs Telescoping Constructors

<div class="grid grid-cols-2 gap-4">

<div>

## Telescoping Constructors

```java
public Person(String name) {
    this(name, 0);
}

public Person(String name, int age) {
    this(name, age, null);
}

public Person(String name,
              int age,
              String email) {
    this(name, age, email, null);
}

// Usage
new Person("Bob", 25, null, "USA");
```

</div>

<div>

## Builder Pattern

```java
Person person = Person.builder()
    .name("Bob")
    .age(25)
    .country("USA")
    .build();
```

<v-clicks>

✓ Clear
✓ Flexible
✓ Maintainable
✓ Type-safe

</v-clicks>

</div>

</div>

---

# Builder Pattern Benefits

<v-clicks>

## Advantages

- **Readability**: Clear what each value means
- **Flexibility**: Set parameters in any order
- **Validation**: Check completeness in `build()`
- **Immutability**: Object created in one step
- **Optional Parameters**: Easy to add/remove

## When to Use

- 4+ constructor parameters
- Many optional parameters
- Need validation before construction
- Want immutable objects

</v-clicks>

---

# Real-World Builder Examples

<v-clicks>

## Java Built-in Builders

```java
// StringBuilder
String result = new StringBuilder()
    .append("Hello")
    .append(" ")
    .append("World")
    .toString();

// ProcessBuilder
Process process = new ProcessBuilder()
    .command("ls", "-la")
    .directory(new File("/tmp"))
    .start();
```

</v-clicks>

---

# Modern Java: Records + Builder

## Using Records with Builder

```java
public record User(String name, String email, int age) {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String email;
        private int age;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public User build() {
            return new User(name, email, age);
        }
    }
}
```

---

# Builder Pattern: Key Takeaways

<v-clicks>

## Core Concept
Separate construction from representation - build complex objects step by step

## Benefits
- Readable, maintainable code
- Flexible parameter handling
- Validation before construction
- Immutable result objects

## When to Use
Objects with 4+ parameters, especially with optional fields

</v-clicks>

---

# Factory + Builder: Best of Both

## They Work Together!

```java
// Factory uses Builder internally
public static Character createWarrior(String name) {
    return Character.builder()  // Builder for flexibility
        .name(name)
        .type(CharacterType.WARRIOR)
        .stats(CharacterStats.create(150, 40, 30, 0))
        .attackStrategy(new MeleeAttackStrategy())
        .defenseStrategy(new HeavyArmorDefenseStrategy())
        .build();
}
```

<v-clicks>

- **Factory**: Provides sensible defaults
- **Builder**: Validates and constructs
- **Client**: Gets simple API

</v-clicks>

---

# Creational Patterns: Comparison

| Pattern | Purpose | When to Use |
|---------|---------|-------------|
| **Singleton** | One instance only | Configuration, shared resources |
| **Factory Method** | Delegate creation | Multiple types, sensible defaults |
| **Builder** | Step-by-step construction | Many parameters, validation needed |

<v-click>

## They Can Combine!

- Factory can use Builder internally
- Singleton can use Factory for creation
- Builder can create different types (like Factory)

</v-click>

---

# Assignment 5 Overview

## Design Patterns Game System

You'll implement all these patterns:

<v-clicks>

### Creational Patterns (This Week)
- **Factory Method**: Create characters (Warrior, Mage, Archer, Rogue)
- **Builder**: Validate character construction

### Behavioral Patterns (Last Week)
- **Strategy**: Attack and defense behaviors
- **Command**: Undoable game actions
- **Template Method**: Battle sequences

Code is in `assignments/assignment-5-design-patterns/`

</v-clicks>

---

# Live Coding: Factory + Builder

Let's implement a simple example together

<v-clicks>

## Requirements
1. Create a `Weapon` class with multiple properties
2. Implement Builder pattern for flexible construction
3. Add Factory methods for common weapon types
4. Validate required fields

</v-clicks>

<div v-click class="mt-8 p-4 bg-blue-50 rounded">

💡 **Follow along**: We'll build this from scratch

</div>

---

# Exercise: Pet Builder

Implement a Builder pattern for a Pet class.

```java
record Pet(String name, String species, int age,
           String color, boolean vaccinated) {

    // TODO: Implement builder() method

    public static class Builder {
        // TODO: Implement Builder class
        // - Fields for all parameters
        // - Fluent setter methods
        // - build() with validation
        //   (name and species required)
    }
}
```

Usage: `Pet pet = Pet.builder().name("Fluffy").species("Cat").build();`

---

# Exercise: Vehicle Factory

Implement factory methods for vehicles.

```java
class VehicleFactory {
    // TODO: Create factory methods for:
    // - createSedan(String make, String model)
    //   • 4 doors, gasoline, standard transmission
    // - createTruck(String make, String model)
    //   • 2 doors, diesel, 4WD
    // - createSUV(String make, String model)
    //   • 4 doors, hybrid, AWD

    public static Vehicle createSedan(String make, String model) {
        // TODO: Implement
    }
}
```

---

# Creational Patterns & SOLID

<v-clicks>

## How They Support SOLID

### Single Responsibility
- Factory: Only creates objects
- Builder: Only constructs objects
- Class: Only domain logic

### Open-Closed Principle
- Factory: Add new types without modifying existing code
- Builder: Add optional parameters easily

### Dependency Inversion
- Clients depend on factory interface, not concrete classes

</v-clicks>

---

# Common Mistakes

<v-clicks>

## Singleton
❌ Using singleton when dependency injection would be better
❌ Not making thread-safe
✓ Use enum singleton

## Factory
❌ God factory that creates everything
❌ Factory with too much logic
✓ One factory per domain concept

## Builder
❌ Builder for simple objects (2-3 parameters)
❌ Not validating in `build()`
✓ Use for 4+ parameters

</v-clicks>

---

# Next Class: Deep Dive

## Session 15 Preview

<v-clicks>

- Builder pattern advanced techniques
- Combining patterns effectively
- Assignment 5 walkthrough
- Common pitfalls and solutions
- Testing creational patterns

### Preparation
Read Assignment 5 requirements
Review TODO comments in starter code

</v-clicks>

---
layout: center
class: text-center
---

# Questions?

## Creational Design Patterns

<div class="pt-8">
  <p class="text-xl">Next class: Builder deep dive & Assignment 5 walkthrough</p>
  <p class="text-sm opacity-75">Assignment 5: Design Patterns Game System</p>
</div>

<div class="abs-br m-6 text-sm opacity-50">
  Week 8, Session 14
</div>
