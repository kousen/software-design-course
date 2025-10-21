---
theme: seriph
background: https://source.unsplash.com/1920x1080/?strategy,chess,planning
class: text-center
highlighter: shiki
lineNumbers: false
info: |
  ## Week 7: Design Patterns - Behavioral

  CPSC 310: Software Design
  Trinity College, Fall 2025

  Kenneth Kousen
drawings:
  persist: false
transition: slide-left
title: "Week 7: Behavioral Design Patterns"
mdc: true
---

# Week 7: Behavioral Design Patterns

## Strategy, Command & Template Method

<div class="pt-12">
  <span @click="$slidev.nav.next" class="px-2 py-1 rounded cursor-pointer" hover="bg-white bg-opacity-10">
    Patterns for flexible behavior and algorithms <carbon:arrow-right class="inline"/>
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

## Session 13 (Today)
- What are Design Patterns?
- Strategy Pattern
- Modern Java Implementation
- Strategy vs Lambdas

## Session 14 (Next Class)
- Command Pattern
- Template Method Pattern
- Combining Patterns

</v-clicks>

::right::

<div class="mt-12">
<v-clicks>

### Key Concepts
- Gang of Four (GoF) patterns
- Behavioral pattern category
- Open-Closed Principle in action
- Function interfaces replace classes

### Examples Today
- Payroll calculation strategies
- Shipping cost strategies
- Custom lambda strategies

</v-clicks>
</div>

---

# What Are Design Patterns?

<v-clicks>

Reusable solutions to commonly occurring problems in software design

- **Not** code you can copy-paste
- **Not** frameworks or libraries
- **Are** templates for solving problems
- **Are** shared vocabulary for developers

</v-clicks>

---

# The Gang of Four (GoF)

<v-clicks>

Published in 1994 by:
- Erich Gamma
- Richard Helm
- Ralph Johnson
- John Vlissides

23 classic patterns in 3 categories:
- **Creational** - Object creation
- **Structural** - Object composition
- **Behavioral** - Object interaction

</v-clicks>

---

# Why Learn Design Patterns?

<v-clicks>

## Communication

"Use a Strategy pattern here" conveys more than explaining the entire approach

## Problem Recognition

You'll recognize situations where patterns apply

## Career Value

Patterns are a common language in technical interviews and code reviews

</v-clicks>

---

# Modern Java Simplifications

Many patterns are simpler with modern Java features:

<v-clicks>

- **Strategy Pattern** → Lambda expressions
- **Factory Pattern** → Static factory methods (`List.of()`, `Optional.of()`)
- **Observer Pattern** → Functional reactive programming
- **Command Pattern** → Method references

But the **concepts** remain essential!

</v-clicks>

---

# Design Patterns in This Course

<div class="grid grid-cols-3 gap-4">

<div>

### Week 7: Behavioral
- **Strategy**
- **Command**
- **Template Method**

Focus: Algorithm variation

</div>

<div>

### Week 8: Creational
- **Singleton**
- **Factory**
- **Builder**

Focus: Object creation

</div>

<div>

### Week 9: Structural
- **Decorator**
- **Adapter**
- **Proxy**

Focus: Object composition

</div>

</div>

<v-click>

### Week 10: Integration
- Observer & Reactive patterns
- Pattern combinations
- Refactoring to patterns
- Anti-patterns to avoid

</v-click>

---
layout: center
---

# Strategy Pattern

## Defining a family of algorithms, encapsulating each one, and making them interchangeable

---

# Strategy Pattern: The Problem

## Without Strategy Pattern

```java
public class OrderProcessor {
    public double calculateShipping(Order order, String type) {
        if (type.equals("standard")) {
            return 5.0 + order.weight() * 0.50;
        } else if (type.equals("express")) {
            return 12.0 + order.weight() * 0.75;
        } else if (type.equals("overnight")) {
            return 25.0 + order.weight() * 1.00;
        }
        throw new IllegalArgumentException("Unknown type");
    }
}
```

---

# The Problem with If/Else Chains

## Issues

<v-clicks>

- Violates **Open-Closed Principle** (must modify to add strategies)
- Violates **Single Responsibility** (logic mixed with selection)
- Hard to test individual strategies
- Cannot reuse strategies elsewhere
- Grows complex as more options are added

</v-clicks>

---

# Strategy Pattern: Traditional Solution

## Strategy Interface

```java
interface ShippingStrategy {
    double calculate(Order order);
}
```

## Concrete Strategies

```java
class StandardShipping implements ShippingStrategy {
    public double calculate(Order order) {
        return 5.0 + order.weight() * 0.50;
    }
}

class ExpressShipping implements ShippingStrategy {
    public double calculate(Order order) {
        return 12.0 + order.weight() * 0.75;
    }
}
```

---

# Traditional Strategy: Context Class

```java
class OrderProcessor {
    private ShippingStrategy strategy;

    public void setStrategy(ShippingStrategy strategy) {
        this.strategy = strategy;
    }

    public double processOrder(Order order) {
        return strategy.calculate(order);
    }
}
```

Context class delegates to the strategy interface

---

# Traditional Strategy: Usage & Benefits

## Usage

```java
var processor = new OrderProcessor();
processor.setStrategy(new StandardShipping());
double cost = processor.processOrder(order);
```

## Benefits
✓ Open-Closed Principle
✓ Easy to test
✓ Reusable strategies

---

# Modern Java Strategy Pattern

## Using Function Interface

```java
import java.util.function.Function;

// Define strategies as Function constants
class ShippingStrategies {
    public static final Function<Order, Double> STANDARD =
        order -> 5.0 + order.weight() * 0.50;

    public static final Function<Order, Double> EXPRESS =
        order -> 12.0 + order.weight() * 0.75;

    public static final Function<Order, Double> OVERNIGHT =
        order -> 25.0 + order.weight() * 1.00;
}
```

No custom interface needed - use `Function<T, R>` from `java.util.function`

---

# Modern Strategy: Context Class

```java
class OrderProcessor {
    private Function<Order, Double> strategy = ShippingStrategies.STANDARD;

    public void setStrategy(Function<Order, Double> strategy) {
        this.strategy = strategy;
    }

    public double processOrder(Order order) {
        return strategy.apply(order);
    }
}
```

Context holds a strategy and delegates calculation

---

# Using Strategies: Three Ways

```java
// 1. Predefined strategies
processor.setStrategy(ShippingStrategies.EXPRESS);

// 2. Custom lambda
processor.setStrategy(order ->
    order.weight() > 10 ? 10.0 * 0.8 : 10.0);

// 3. Method reference
processor.setStrategy(Calculator::calculateBulk);
```

Strategies can be swapped at runtime!

---

# Strategy Pattern: Structure

```mermaid
classDiagram
    class Context {
        -strategy: Function~Data, Result~
        +setStrategy(Function)
        +execute(Data)
    }

    class Function~Data, Result~ {
        <<interface>>
        +apply(Data) Result
    }

    class PredefinedStrategies {
        +STRATEGY_A: Function
        +STRATEGY_B: Function
        +STRATEGY_C: Function
    }

    class CustomLambda {
        data -> calculation
    }

    class MethodReference {
        ClassName::methodName
    }

    Context --> Function
    PredefinedStrategies ..|> Function
    CustomLambda ..|> Function
    MethodReference ..|> Function
```

---

# Real Example: Payroll Calculation

## Data Structure

```java
record PayrollData(
    Employee employee,
    Integer hoursWorked,
    Double hourlyRate,
    Double annualSalary,
    Double salesAmount,
    Double baseSalary,
    Double commissionRate
) {}
```

Modern Java record for immutable data

---

# Payroll Strategies

```java
class PayrollStrategies {
    // Hourly with overtime
    public static final Function<PayrollData, Double> HOURLY = data -> {
        int hours = data.hoursWorked();
        double rate = data.hourlyRate();
        if (hours <= 40) return hours * rate;
        return (40 * rate) + ((hours - 40) * rate * 1.5);
    };

    // Salaried (bi-weekly)
    public static final Function<PayrollData, Double> SALARIED =
        data -> data.annualSalary() / 26;

    // Commission
    public static final Function<PayrollData, Double> COMMISSION =
        data -> data.salesAmount() * data.commissionRate();

    // Base + commission
    public static final Function<PayrollData, Double> BASE_PLUS_COMMISSION =
        data -> data.baseSalary() +
               (data.salesAmount() * data.commissionRate());
}
```

---

# Payroll Processor

## Context Class

```java
class PayrollProcessor {
    private Function<PayrollData, Double> calculator =
        PayrollStrategies.SALARIED;

    public void setCalculator(Function<PayrollData, Double> calculator) {
        this.calculator = calculator;
    }

    public double processPayroll(PayrollData data) {
        return calculator.apply(data);
    }
}
```

Same pattern, different domain!

---

# Using the Payroll Strategy

## Predefined Strategy

```java
var processor = new PayrollProcessor();

processor.setCalculator(PayrollStrategies.HOURLY);
var hourlyData = new PayrollData(employee, 45, 25.0,
    null, null, null, null);
double pay = processor.processPayroll(hourlyData);
// Result: (40 * $25) + (5 * $25 * 1.5) = $1,187.50
```

## Custom Lambda

```java
processor.setCalculator(data -> {
    double sales = data.salesAmount();
    double commission = sales <= 20000 ?
        sales * 0.05 :
        (20000 * 0.05) + ((sales - 20000) * 0.08);
    return data.baseSalary() + commission;
});
```

---

# Batch Processing with Strategies

```java
Map<String, Function<PayrollData, Double>> employeeStrategies = Map.of(
    "Alice", PayrollStrategies.HOURLY,
    "Bob", PayrollStrategies.SALARIED,
    "Carol", customCommissionStrategy
);

List<Double> payments = employees.parallelStream()
    .map(emp -> employeeStrategies.get(emp.name()).apply(emp.data()))
    .toList();
```

Strategy map + parallel streams = powerful batch processing!

---

# Strategy Pattern Benefits

<v-clicks>

## Core Advantages

- **Open-Closed Principle**: Add new strategies without modifying code
- **Testability**: Each strategy tested independently
- **Runtime Flexibility**: Switch strategies based on context
- **Code Reuse**: Use strategies across different contexts

## Modern Java Benefits

- **No Boilerplate**: Lambdas eliminate strategy classes
- **Type Safety**: Compile-time checking with `Function<T, R>`
- **Parallel Processing**: Easy integration with streams
- **Method References**: Reuse existing methods as strategies
- **Functional Composition**: Chain and combine strategies

</v-clicks>

---

# When to Use Strategy Pattern

## Perfect Fit ✓

<v-clicks>

- Multiple algorithms for the same task
- Need to switch algorithms at runtime
- Want to isolate algorithm implementation
- Complex conditional logic that's hard to maintain

</v-clicks>

---

# Real-World Strategy Examples

<v-clicks>

## Common Use Cases

- **Payment processing**: Credit card, PayPal, Bitcoin
- **Compression**: ZIP, GZIP, RAR, 7z
- **Sorting**: Different algorithms based on data size
- **Validation**: Email, phone, credit card, password
- **Pricing**: Standard, discount, promotional, tiered
- **Routing**: Fastest, shortest, scenic, avoid tolls

## When to Avoid

- Only one algorithm that rarely changes
- Algorithm is extremely simple
- No need for runtime flexibility

</v-clicks>

---

# Strategy Pattern & Open-Closed Principle

## Remember from Week 6?

```java
// OCP Example - DiscountStrategy
interface DiscountStrategy {
    double calculate(double amount);
}

class RegularCustomerDiscount implements DiscountStrategy {
    public double calculate(double amount) {
        return amount * 0.90; // 10% off
    }
}

class VIPCustomerDiscount implements DiscountStrategy {
    public double calculate(double amount) {
        return amount * 0.75; // 25% off
    }
}
```

This **is** the Strategy pattern!

---

# Strategy Pattern IS OCP

Strategy pattern is the primary way to achieve the Open-Closed Principle

<v-clicks>

## OCP States
"Software entities should be open for extension but closed for modification"

## Strategy Achieves This By
- Define strategy interface (**closed**)
- Create new strategy implementations (**open**)
- Context uses any strategy via interface
- Add strategies without changing context

</v-clicks>

---

# You've Already Seen Strategy!

Week 6 OCP examples were Strategy patterns:

<v-clicks>

- `DiscountStrategy` for orders
- `MessageService` implementations (DIP)

Strategy pattern **is** the Open-Closed Principle in action

</v-clicks>

---

# Live Coding: Shipping Strategy

Let's implement a complete shipping calculator with multiple strategies

<v-clicks>

## Requirements
1. Calculate shipping costs for different service levels
2. Support standard, express, overnight
3. Allow custom strategies for bulk discounts
4. Process batch shipments efficiently

</v-clicks>

---

# What We'll Build

<v-clicks>

- `ShippingData` record
- `ShippingStrategies` with Function constants
- `ShippingCalculator` context class
- Custom lambda strategies
- Batch processing with streams

</v-clicks>

<div v-click class="mt-8 p-4 bg-blue-50 rounded">

💡 **Follow along**: Code is in `examples/design-patterns/src/main/java/edu/trincoll/patterns/strategy/`

</div>

---

# Strategy Pattern: Key Takeaways

<v-clicks>

## Core Concept
Define a family of algorithms, encapsulate each one, make them interchangeable

## Modern Implementation
- Use `Function<T, R>` instead of custom interfaces
- Lambdas replace concrete strategy classes
- Method references for existing methods
- Easy to compose and combine

</v-clicks>

---

# Strategy Pattern & SOLID

<v-clicks>

## Relationship to SOLID
- **Embodies** Open-Closed Principle
- **Supports** Single Responsibility (separate algorithms)
- **Enables** Dependency Inversion (depend on Function interface)

## Real-World Usage
Essential pattern in production systems for:
- Payment processing
- Business rules that vary
- Algorithm selection
- Testable, maintainable code

</v-clicks>

---

# Exercise: Temperature Converter Strategy

Implement a temperature converter with multiple conversion strategies.

<div class="text-sm">

```java
record Temperature(double value, String unit) {}

// TODO: Implement these strategies as Function<Temperature, Temperature>
class ConversionStrategies {
    // Celsius to Fahrenheit: F = C * 9/5 + 32
    public static final Function<Temperature, Temperature> CELSIUS_TO_FAHRENHEIT = null;

    // Fahrenheit to Celsius: C = (F - 32) * 5/9
    public static final Function<Temperature, Temperature> FAHRENHEIT_TO_CELSIUS = null;

    // Celsius to Kelvin: K = C + 273.15
    public static final Function<Temperature, Temperature> CELSIUS_TO_KELVIN = null;
}
```

</div>

---

# Exercise: Temperature Converter (continued)

<div class="text-sm">

```java
class TemperatureConverter {
    private Function<Temperature, Temperature> strategy;

    public void setStrategy(Function<Temperature, Temperature> strategy) {
        this.strategy = strategy;
    }

    public Temperature convert(Temperature temp) {
        return strategy.apply(temp);
    }
}
```

**Bonus**: Create a composite strategy that converts Fahrenheit → Celsius → Kelvin

Hint: Use `Function.andThen()` to chain strategies together!

</div>

---
layout: center
class: text-center
---

# Questions?

## Strategy Pattern

<div class="pt-8">
  <p class="text-xl">Next class: Command & Template Method patterns</p>
  <p class="text-sm opacity-75">Assignment: Implement pattern examples with tests</p>
</div>

<div class="abs-br m-6 text-sm opacity-50">
  Week 7, Session 13
</div>
