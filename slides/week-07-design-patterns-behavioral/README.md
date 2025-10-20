# Week 7: Behavioral Design Patterns

## Overview

This week introduces students to the Gang of Four (GoF) design patterns, focusing on **Behavioral Patterns** that define how objects interact and distribute responsibility.

## Sessions

### Session 13: Strategy Pattern
- **Date**: Week 7, Day 1
- **Topics**:
  - Introduction to Design Patterns
  - Gang of Four (GoF) patterns overview
  - Strategy Pattern fundamentals
  - Modern Java implementation with lambdas
  - Function interfaces vs. traditional classes
  - Relationship to SOLID principles (especially OCP)

### Session 14: Command & Template Method Patterns
- **Date**: Week 7, Day 2
- **Topics**:
  - Command Pattern for encapsulating requests
  - Undo/redo functionality
  - Template Method Pattern for algorithm skeletons
  - Combining patterns in practice

## Learning Objectives

By the end of this week, students will be able to:

1. **Understand** the purpose and history of design patterns
2. **Recognize** when the Strategy pattern is appropriate
3. **Implement** Strategy pattern using modern Java features (lambdas, Function interface)
4. **Differentiate** between traditional OOP implementations and functional approaches
5. **Connect** design patterns to SOLID principles
6. **Apply** Strategy pattern to real-world problems

## Code Examples

All code examples are in `examples/design-patterns/src/main/java/edu/trincoll/patterns/strategy/`

### Strategy Pattern Examples

1. **Shipping Calculator**
   - Multiple shipping strategies (Standard, Express, Overnight, International)
   - Custom lambda strategies
   - Bulk discount example
   - Files: `ShippingData.java`, `ShippingStrategies.java`, `ShippingCalculator.java`

2. **Payroll Processor**
   - Multiple payment calculation strategies (Hourly, Salaried, Commission)
   - Overtime calculations
   - Tiered commission rates
   - Files: `Employee.java`, `PayrollData.java`, `PayrollStrategies.java`, `PayrollProcessor.java`

3. **Demonstration**
   - Complete working examples
   - Batch processing with parallel streams
   - Strategy maps for runtime selection
   - File: `StrategyPatternDemo.java`

## Tests

Comprehensive test suites with **55+ tests** covering:
- All predefined strategies
- Custom lambda strategies
- Strategy switching at runtime
- Data validation
- Edge cases
- Summary generation

Test files:
- `ShippingCalculatorTest.java` (30+ tests)
- `PayrollProcessorTest.java` (20+ tests)
- `ShippingDataTest.java` (validation tests)
- `EmployeeTest.java` (validation tests)

Run tests:
```bash
cd examples/design-patterns
./gradlew test --tests "edu.trincoll.patterns.strategy.*"
```

## Key Concepts

### Strategy Pattern
- **Definition**: Define a family of algorithms, encapsulate each one, and make them interchangeable
- **When to Use**: Multiple algorithms for the same task that need to be selected at runtime
- **Modern Java**: Use `Function<T, R>` instead of custom strategy interfaces

### Traditional vs. Modern Implementation

**Traditional** (Pre-Java 8):
```java
interface Strategy {
    double calculate(Data data);
}

class ConcreteStrategyA implements Strategy {
    public double calculate(Data data) { /* ... */ }
}
```

**Modern** (Java 8+):
```java
Function<Data, Double> STRATEGY_A = data -> /* calculation */;
```

### Benefits of Modern Approach
- ✓ No boilerplate strategy classes
- ✓ Lambda expressions for inline strategies
- ✓ Method references for existing methods
- ✓ Easy composition and chaining
- ✓ Works seamlessly with Streams API

## Connection to SOLID Principles

The Strategy pattern directly implements the **Open-Closed Principle**:
- **Open for extension**: Add new strategies without modifying existing code
- **Closed for modification**: Context class doesn't change when adding strategies

Also supports:
- **Single Responsibility**: Each strategy has one job
- **Dependency Inversion**: Context depends on abstraction (Function interface)

## Resources

- **Slides**: `slides.md` - Slidev presentation with 30+ slides
- **Examples**: `examples/design-patterns/src/main/java/edu/trincoll/patterns/strategy/`
- **Tests**: `examples/design-patterns/src/test/java/edu/trincoll/patterns/strategy/`

## Running the Slides

```bash
cd slides/week-07-design-patterns-behavioral
npm install  # First time only
npx slidev slides.md
```

Export to PDF:
```bash
npx slidev export slides.md --format pdf
```

## Assignment Ideas

1. **Temperature Converter** (In-class exercise)
   - Implement conversion strategies for Celsius/Fahrenheit/Kelvin
   - Create composite strategies that chain conversions

2. **Payment Processor**
   - Multiple payment methods (Credit Card, PayPal, Bitcoin)
   - Validation strategies
   - Fee calculation strategies

3. **Sorting Algorithms**
   - Implement different sorting strategies
   - Performance comparison
   - Strategy selection based on data characteristics

4. **Discount Calculator**
   - Holiday discounts
   - Loyalty program discounts
   - Bulk purchase discounts
   - Combine multiple discount strategies

## Notes for Instructors

### Teaching Tips
1. Start with the problem (if/else chains) before showing the pattern
2. Show traditional OOP implementation first, then modern approach
3. Connect back to Week 6 SOLID principles (they've already seen this!)
4. Emphasize that many patterns are simpler with modern Java
5. Live code the shipping calculator example

### Common Student Questions
- **Q**: "Why not just use if/else statements?"
  - **A**: Show how it violates Open-Closed Principle; adding strategies requires modifying existing code

- **Q**: "When should I use a pattern vs. a simple lambda?"
  - **A**: Patterns provide structure and vocabulary for complex cases; simple lambdas are fine for one-off uses

- **Q**: "Are design patterns still relevant with modern Java?"
  - **A**: Yes! Patterns are about recognizing problems, not just solutions. Modern Java makes them easier to implement.

### Live Coding Demo
1. Start with a problematic if/else chain for shipping costs
2. Refactor to Strategy pattern step by step
3. Show how easy it is to add new strategies
4. Demonstrate custom lambda strategy
5. Add batch processing with streams

### Code to Avoid Showing on One Slide
The slides are designed to avoid code overflow, but be careful with:
- The full `PayrollProcessor` class (split across multiple slides)
- The batch processing examples (show simplified version first)
- Test classes (reference them, don't show full code)

## Next Week Preview

**Week 8: Creational Patterns**
- Singleton Pattern (enum approach)
- Factory Pattern (static factory methods)
- Builder Pattern (for immutable objects)

Focus on how modern Java features like records, sealed types, and static methods simplify these patterns.
