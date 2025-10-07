# Week 6: SOLID Principles

## Session 11 & 12: October 7-9, 2025

### Overview
This presentation covers the five SOLID principles of object-oriented design, with practical examples from the course codebase.

### Topics Covered

#### Session 11 (Oct 7)
- **Single Responsibility Principle (SRP)**
  - One class, one responsibility
  - Examples: Employee, PayrollCalculator, EmployeeRepository

- **Open-Closed Principle (OCP)**
  - Open for extension, closed for modification
  - Strategy pattern with discount calculations

- **Liskov Substitution Principle (LSP)**
  - Substitutability of subtypes
  - Rectangle/Square problem and solutions
  - Bird hierarchy example

#### Session 12 (Oct 9)
- **Interface Segregation Principle (ISP)**
  - Focused, cohesive interfaces
  - Printer and Worker examples

- **Dependency Inversion Principle (DIP)**
  - Depend on abstractions
  - Repository pattern
  - Dependency injection

- **SOLID in Spring Boot**
  - Practical application of all principles
  - Testing with SOLID
  - Common violations in AI-generated code

### Code Examples
All examples are in `examples/design-patterns/src/main/java/edu/trincoll/solid/`:

```
solid/
├── srp/           # Single Responsibility
│   ├── Employee.java
│   ├── PayrollCalculator.java
│   └── EmployeeRepository.java
├── ocp/           # Open-Closed
│   ├── DiscountStrategy.java
│   ├── Order.java
│   └── *CustomerDiscount.java
├── lsp/           # Liskov Substitution
│   ├── Shape hierarchy
│   └── Bird hierarchy
├── isp/           # Interface Segregation
│   ├── Printable.java
│   └── Worker interfaces
└── dip/           # Dependency Inversion
    ├── MessageService hierarchy
    └── UserRepository hierarchy
```

### Running the Presentation

```bash
cd slides/week-06-solid-principles
npm install
npm run dev
```

Visit http://localhost:3030 to view the slides.

### Export to PDF

```bash
npm run export
```

### Learning Objectives

By the end of this week, students should be able to:

1. **Identify** SOLID principle violations in code
2. **Apply** SOLID principles when designing new classes
3. **Refactor** existing code to follow SOLID principles
4. **Explain** how SOLID principles work together
5. **Evaluate** AI-generated code for SOLID compliance
6. **Implement** SOLID principles in Spring Boot applications

### Key Takeaways

- **SRP**: Each class should have only one reason to change
- **OCP**: Extend behavior without modifying existing code
- **LSP**: Subtypes must be behaviorally compatible with base types
- **ISP**: Prefer small, focused interfaces over fat ones
- **DIP**: Depend on abstractions, not concrete implementations

### Assignments

#### Team Assignment 6 (Due Oct 23)
Refactor your existing project code to follow SOLID principles:
- Identify at least 3 SOLID violations
- Refactor the code to fix them
- Document your design decisions
- Ensure tests still pass (or improve them)

#### Milestone 2 (Due Oct 16)
Complete CRUD API demonstrating:
- SOLID principles throughout
- 80% test coverage
- Clean architecture with proper separation of concerns

### Additional Resources

- **Books**:
  - "Clean Architecture" by Robert C. Martin
  - "Design Patterns" by Gang of Four
  - "Effective Java" by Joshua Bloch

- **Articles**:
  - [SOLID Principles Explained](https://www.baeldung.com/solid-principles)
  - [Uncle Bob's SOLID Principles](https://blog.cleancoder.com/)

- **Videos**:
  - Robert C. Martin on SOLID Principles
  - Refactoring to SOLID (various tutorials)

### Notes for Instructors

- Emphasize that SOLID are **guidelines**, not rigid rules
- Show both violations and corrections
- Use real-world examples from students' projects
- Encourage discussion of trade-offs
- Live coding refactoring exercises work well
- Connect to design patterns in upcoming weeks

### Common Student Questions

**Q: Do I need to apply all SOLID principles to every class?**
A: No. Apply them where they add value. Don't over-engineer simple code.

**Q: Doesn't SOLID lead to more classes and complexity?**
A: It leads to more classes, but each is simpler and easier to understand. The overall system becomes more maintainable.

**Q: How do I know which principle to apply?**
A: Look for pain points: hard to test? (DIP), hard to extend? (OCP), doing too much? (SRP)

**Q: What about performance?**
A: SOLID rarely impacts performance. Maintainability is usually more important. Profile first.

**Q: How does this relate to Spring Boot?**
A: Spring Boot is built on SOLID principles. Dependency injection is DIP in action. Service/Repository layers follow SRP.
