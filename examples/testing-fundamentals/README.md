# Testing Fundamentals

This module demonstrates modern Java testing practices using JUnit 5, AssertJ, and jqwik property-based testing.

## Overview

This module supports the "Testing Fundamentals" lecture and provides practical examples of:

- JUnit 5 basic and advanced features
- AssertJ fluent assertions
- Parameterized testing with multiple sources
- Property-based testing with jqwik
- Test-Driven Development (TDD) methodology
- Test organization with nested tests
- Test lifecycle management

## Contents

### Basic Testing
- `FirstTest.java` - Your first JUnit 5 test
- `LifecycleTest.java` - Test lifecycle methods demonstration

### AssertJ Examples
- `assertj/SimpleAssertionsTest.java` - Fluent assertions with AssertJ
- Demonstrates string, collection, numerical, and object assertions

### Parameterized Testing
- `ParameterizedTests.java` - Comprehensive parameterized testing examples
- Value sources, method sources, CSV sources, enum sources
- Custom test names and complex parameter combinations

### Property-Based Testing
- `FizzBuzzTest.java` - jqwik property-based testing
- Custom arbitrary generators
- Property verification across many test cases

### TDD Examples
- `tdd/CalculatorTDDTest.java` - Test-Driven Development demonstration
- Red-Green-Refactor cycle examples
- Arrange-Act-Assert pattern

### Test Organization
- `NestedTestsExample.java` - Hierarchical test organization
- Demonstrates proper test structure and context

## Running the Tests

From the project root:

```bash
./gradlew :examples:testing-fundamentals:test
```

## Key Learning Objectives

After working through these examples, students should understand:

1. **JUnit 5 Fundamentals**
   - Basic test structure and assertions
   - Test lifecycle management
   - Exception testing

2. **AssertJ Benefits**
   - Fluent, readable assertions
   - Better error messages
   - Extensive assertion library

3. **Parameterized Testing**
   - Multiple data sources
   - Test case generation
   - Custom test naming

4. **Property-Based Testing**
   - Testing properties vs. examples
   - Arbitrary value generation
   - Shrinking to minimal failing cases

5. **TDD Methodology**
   - Red-Green-Refactor cycle
   - Test-first development
   - Continuous improvement

6. **Test Organization**
   - Nested test structure
   - Logical grouping
   - Maintainable test suites

## Dependencies

This module uses:
- JUnit 5.11.0 (Platform BOM)
- AssertJ 3.26.3 (fluent assertions)
- jqwik 1.9.1 (property-based testing, compatible with JUnit Platform 1.11.0)
- Apache Commons Validator 1.7 (for ISBN/URL validation examples)
- Mockito 5.8.0 (for mocking examples - future use)

## Teaching Notes

These examples are designed to be presented progressively:
1. Start with basic JUnit 5 concepts
2. Introduce AssertJ for better assertions
3. Show parameterized testing for data-driven tests
4. Demonstrate property-based testing concepts
5. Emphasize TDD methodology throughout
6. Use nested tests for organization

Each example builds on previous concepts while introducing new testing patterns and best practices.