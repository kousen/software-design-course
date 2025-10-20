# Week 7: Behavioral Design Patterns - Complete Materials

## Overview

Week 7 introduces students to **Behavioral Design Patterns** from the Gang of Four (GoF), focusing on three essential patterns:

1. **Strategy Pattern** (Session 13)
2. **Command Pattern** (Session 14)
3. **Template Method Pattern** (Session 14)

All implementations use **modern Java 21 features** to show how classic patterns are simpler and more elegant with current language capabilities.

---

## 📚 Session 13: Strategy Pattern

### Topics Covered
- Introduction to Design Patterns and Gang of Four
- Strategy Pattern fundamentals
- Modern implementation with `Function<T, R>` interface
- Comparison: Traditional OOP vs. Functional approach
- Connection to SOLID principles (especially Open-Closed)

### Code Examples
**Location**: `examples/design-patterns/src/main/java/edu/trincoll/patterns/strategy/`

1. **Shipping Calculator** - Multiple shipping cost strategies
   - Standard, Express, Overnight, International, Bulk Discount, Tiered pricing
   - Runtime strategy switching
   - Custom lambda strategies

2. **Payroll Processor** - Complex payroll calculations
   - Hourly with overtime, Salaried, Commission, Base+Commission, Tiered Commission
   - Batch processing with parallel streams
   - Strategy registry pattern

### Tests
**Location**: `examples/design-patterns/src/test/java/edu/trincoll/patterns/strategy/`

- `ShippingCalculatorTest.java` - 30+ tests for shipping strategies
- `PayrollProcessorTest.java` - 25+ tests for payroll calculations
- `ShippingDataTest.java` - Validation tests
- `EmployeeTest.java` - Validation tests

**Total**: 55+ tests, all passing

### Slides
- `slides.md` - Main presentation (30+ slides)
- Covers: Pattern intro, traditional vs modern, real examples, SOLID connection

---

## 📚 Session 14: Command & Template Method Patterns

### Topics Covered

#### Command Pattern
- Encapsulating requests as objects
- Undo/redo implementation
- Command history management
- Macro commands (Composite pattern integration)
- Real-world applications

#### Template Method Pattern
- Algorithm skeleton definition
- Abstract vs concrete methods
- Hook methods for customization
- Framework design patterns
- Comparison with Strategy pattern

### Code Examples

#### Command Pattern
**Location**: `examples/design-patterns/src/main/java/edu/trincoll/patterns/command/`

- **TextEditor** - Receiver class for edit operations
- **Command Interface** - With execute() and undo()
- **Concrete Commands**: InsertCommand, DeleteCommand, ReplaceCommand
- **CommandHistory** - Invoker with undo/redo stacks
- **MacroCommand** - Composite command for macros
- **CommandPatternDemo** - Complete working demonstration

#### Template Method Pattern
**Location**: `examples/design-patterns/src/main/java/edu/trincoll/patterns/template/`

- **DataProcessor** - Abstract base class with template method
- **Concrete Processors**: CsvDataProcessor, JsonDataProcessor, XmlDataProcessor
- **ProcessingResult** - Result record
- **TemplateMethodDemo** - Polymorphic processing demonstration

### Tests

#### Command Pattern Tests
**Location**: `examples/design-patterns/src/test/java/edu/trincoll/patterns/command/`

- `TextEditorTest.java` - 20+ tests for editor operations
- `CommandTest.java` - 15+ tests for all command types
- `CommandHistoryTest.java` - 25+ tests for undo/redo
- `MacroCommandTest.java` - 10+ tests for composite commands

**Subtotal**: 70+ tests

#### Template Method Tests
**Location**: `examples/design-patterns/src/test/java/edu/trincoll/patterns/template/`

- `DataProcessorTest.java` - 15+ tests for all processors and template method pattern

**Subtotal**: 15+ tests

**Total Week 7 Tests**: 140+ tests, all passing ✅

### Slides
- `session-14-slides.md` - Command & Template Method (40+ slides)
- Covers: Both patterns, structure, examples, when to use, comparisons

---

## 🎯 Learning Objectives

By the end of Week 7, students will be able to:

1. **Understand** the purpose and history of design patterns
2. **Recognize** when each pattern is appropriate:
   - Strategy: Multiple algorithms for same task
   - Command: Undo/redo, transactions, macros
   - Template Method: Fixed algorithm structure with customizable steps
3. **Implement** all three patterns using modern Java features
4. **Differentiate** between traditional OOP and functional implementations
5. **Connect** design patterns to SOLID principles
6. **Apply** patterns to real-world problems
7. **Combine** patterns when appropriate

---

## 🏗️ Project Structure

```
examples/design-patterns/
├── src/main/java/edu/trincoll/patterns/
│   ├── strategy/
│   │   ├── ShippingData.java
│   │   ├── ShippingStrategies.java
│   │   ├── ShippingCalculator.java
│   │   ├── Employee.java
│   │   ├── PayrollData.java
│   │   ├── PayrollStrategies.java
│   │   ├── PayrollProcessor.java
│   │   ├── StrategyPatternDemo.java
│   │   └── package-info.java
│   ├── command/
│   │   ├── Command.java
│   │   ├── TextEditor.java
│   │   ├── InsertCommand.java
│   │   ├── DeleteCommand.java
│   │   ├── ReplaceCommand.java
│   │   ├── CommandHistory.java
│   │   ├── MacroCommand.java
│   │   ├── CommandPatternDemo.java
│   │   └── package-info.java
│   └── template/
│       ├── DataProcessor.java
│       ├── ProcessingResult.java
│       ├── CsvDataProcessor.java
│       ├── JsonDataProcessor.java
│       ├── XmlDataProcessor.java
│       ├── TemplateMethodDemo.java
│       └── package-info.java
├── src/test/java/edu/trincoll/patterns/
│   ├── strategy/ (4 test classes, 55+ tests)
│   ├── command/  (4 test classes, 70+ tests)
│   └── template/ (1 test class, 15+ tests)

slides/week-07-design-patterns-behavioral/
├── slides.md (Session 13 - Strategy Pattern)
├── session-14-slides.md (Session 14 - Command & Template Method)
├── README.md (Teaching guide)
└── COMPLETE-README.md (This file)
```

---

## 🚀 Running the Code

### Run All Tests
```bash
cd examples/design-patterns
./gradlew test --tests "edu.trincoll.patterns.*"
```

### Run Specific Pattern Tests
```bash
./gradlew test --tests "edu.trincoll.patterns.strategy.*"
./gradlew test --tests "edu.trincoll.patterns.command.*"
./gradlew test --tests "edu.trincoll.patterns.template.*"
```

### Run Demonstrations
The demo classes can be run directly from your IDE:
- `StrategyPatternDemo.java`
- `CommandPatternDemo.java`
- `TemplateMethodDemo.java`

---

## 📖 Key Concepts Summary

### Strategy Pattern
- **Purpose**: Define a family of algorithms, encapsulate each one, make them interchangeable
- **Modern Java**: Use `Function<T, R>` instead of custom interfaces
- **When to Use**: Multiple algorithms for same task, need runtime selection
- **SOLID**: Embodies Open-Closed Principle

### Command Pattern
- **Purpose**: Encapsulate a request as an object
- **Key Feature**: Undo/redo support through command history
- **When to Use**: Need undo/redo, transactions, macros, or request queuing
- **Advanced**: Macro commands combine Composite pattern

### Template Method Pattern
- **Purpose**: Define algorithm skeleton, defer steps to subclasses
- **Key Feature**: Final template method prevents structure changes
- **When to Use**: Multiple classes share algorithm structure
- **Difference from Strategy**: Structure vs. behavior focus

---

## 🎓 Teaching Notes

### Session 13 (Strategy Pattern)
**Duration**: 75 minutes

1. **Introduction** (10 min)
   - What are design patterns?
   - Gang of Four history
   - Why learn patterns today?

2. **Strategy Pattern** (40 min)
   - Problem: if/else chains violate OCP
   - Traditional solution with interfaces
   - Modern solution with lambdas
   - Live coding: Shipping calculator
   - Connection to SOLID (OCP)

3. **Exercises** (20 min)
   - Temperature converter strategy
   - Custom shipping strategies

4. **Wrap-up** (5 min)
   - Preview Command & Template Method

### Session 14 (Command & Template Method)
**Duration**: 75 minutes

1. **Command Pattern** (35 min)
   - Problem: No undo/redo in typical code
   - Command interface with execute/undo
   - CommandHistory for undo/redo
   - MacroCommand for composite operations
   - Live coding: Text editor

2. **Template Method Pattern** (30 min)
   - Problem: Duplicate algorithm structure
   - Abstract class with template method
   - Abstract vs hook methods
   - Live coding: Data processors
   - Comparison with Strategy

3. **Pattern Combinations** (5 min)
   - How patterns work together
   - Real-world examples

4. **Wrap-up** (5 min)
   - Week summary
   - Next week preview (Creational patterns)

---

## 📝 Assignments

### Assignment Options

**Option 1: Command Pattern - Drawing Application**
- Implement draw commands (Circle, Rectangle, Line)
- Full undo/redo support
- Macro for composite shapes
- 80% test coverage

**Option 2: Template Method - File Converter**
- Abstract conversion template
- Multiple format conversions (CSV↔JSON↔XML)
- Validation and transformation steps
- 80% test coverage

**Option 3: Combined Patterns**
- Use Template Method for conversion lifecycle
- Use Command for undo/redo
- Strategy for different conversion algorithms

---

## 🔗 Connections to Other Weeks

### Week 6: SOLID Principles
- Strategy Pattern **is** OCP in action
- All patterns support Single Responsibility
- Command Pattern enables Dependency Inversion

### Week 8: Creational Patterns (Next)
- Factory Pattern for creating strategies
- Builder Pattern for complex commands
- Singleton for command registry

### Week 9: Structural Patterns
- Composite Pattern already seen (MacroCommand)
- Decorator can wrap strategies
- Proxy can log command execution

---

## 📚 Additional Resources

### Books
- "Design Patterns" by Gang of Four (original)
- "Head First Design Patterns" (accessible intro)
- "Effective Java" by Joshua Bloch (modern Java patterns)

### Online
- Refactoring.Guru - Design Patterns
- Oracle Java Tutorials
- Baeldung - Design Patterns in Java

### Modern Java
- JEP 394: Pattern Matching for instanceof
- JEP 395: Records
- JEP 409: Sealed Classes

---

## ✅ Completion Checklist

- [x] Session 13 slides (Strategy Pattern)
- [x] Session 14 slides (Command & Template Method)
- [x] Strategy Pattern implementation
- [x] Command Pattern implementation
- [x] Template Method Pattern implementation
- [x] 140+ comprehensive tests (all passing)
- [x] Demo applications for each pattern
- [x] Package documentation
- [x] Teaching README
- [x] Student exercises
- [x] Assignment specifications

---

## 🎉 Week 7 Complete!

All materials are ready for teaching. The examples are production-quality code with comprehensive tests, the slides are designed to avoid overflow, and the patterns are demonstrated using modern Java 21 features while teaching timeless design principles.

Students will learn not just how to implement patterns, but **when and why** to use them, and how modern language features make classic patterns more elegant and practical.

**Next**: Week 8 - Creational Patterns (Singleton, Factory, Builder)
