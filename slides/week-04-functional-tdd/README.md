# Week 4: Functional Programming & TDD with Game of Life

## Overview

This week introduces functional programming concepts in Java through the lens of Test-Driven Development, using Conway's Game of Life as a practical example.

## Topics Covered

- Test-Driven Development (Red-Green-Refactor cycle)
- Lambda expressions and functional interfaces
- Stream API operations
- Optional for null safety
- Method references
- Pure functions and immutability
- Property-based testing concepts
- Practical application with Game of Life patterns

## Slide Presentation

- **File**: `slides.md`
- **Slides**: 46 slides
- **Format**: Slidev presentation

## Learning Objectives

By the end of this session, students will:
1. Understand and apply TDD methodology
2. Write and use lambda expressions
3. Process collections with streams
4. Handle null values safely with Optional
5. Recognize benefits of functional programming
6. Combine TDD with functional programming techniques

## Running the Slides

```bash
npx slidev slides.md
```

Or to export to PDF:
```bash
npx slidev export slides.md
```

## Related Resources

- Assignment 4: Game of Life Functional Programming Exercise
- Game of Life repository: `~/Documents/java/gameoflife`
- [Java Stream API Documentation](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/stream/package-summary.html)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)

## Key Concepts Demonstrated

### Test-Driven Development
- Writing tests before implementation
- Using tests to drive design decisions
- Red-Green-Refactor cycle

### Functional Programming
- Lambda expressions: `(params) -> expression`
- Stream operations: `filter()`, `map()`, `collect()`
- Method references: `Class::method`
- Optional: Safe handling of potentially null values

### Game of Life Rules
1. Underpopulation: Live cell with < 2 neighbors dies
2. Survival: Live cell with 2-3 neighbors survives
3. Overpopulation: Live cell with > 3 neighbors dies
4. Reproduction: Dead cell with exactly 3 neighbors becomes alive

## Teaching Notes

- Start with visual demonstration of Game of Life patterns
- Emphasize writing tests first
- Show refactoring from imperative to functional style
- Live code simple examples before showing complex ones
- Use debugger to show stream pipeline execution
- Demonstrate both sequential and parallel stream processing

## Common Student Challenges

1. **Lambda syntax**: Practice with simple examples first
2. **Stream debugging**: Show intermediate operations with `peek()`
3. **Optional misuse**: Emphasize when to use and when not to use
4. **Test design**: Start with simple assertions, build complexity
5. **Functional thinking**: Show side-by-side imperative vs functional

## In-Class Activities

1. Live coding: Implement Game of Life rules using TDD
2. Refactoring exercise: Convert loops to streams
3. Pattern detection: Identify still lifes and oscillators
4. Performance comparison: Sequential vs parallel streams

## Homework

See Assignment 4 for detailed exercises on:
- Pattern detection algorithms
- Functional grid operations
- Game variants implementation
- Performance optimization