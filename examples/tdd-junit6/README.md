# TDD Deep Dive: JUnit 6 & AssertJ

This module contains example code for the TDD Deep Dive presentation, demonstrating JUnit testing patterns and AssertJ fluent assertions.

## Contents

### Source Code (`src/main/java/edu/trincoll/`)

- **Calculator.java** - Simple calculator for TDD demonstrations
- **BankAccount.java** - Bank account with state management and validation
- **InsufficientFundsException.java** - Custom exception for withdrawals
- **Person.java** - Record for demonstrating AssertJ extracting

### Test Code (`src/test/java/edu/trincoll/`)

- **CalculatorTDDTest.java** - TDD with nested tests and parameterized tests
- **BankAccountTest.java** - Hierarchical nested test organization
- **AssertJExamplesTest.java** - Comprehensive AssertJ assertion examples
- **ParameterizedTestExamplesTest.java** - All parameter source types

## Running Tests

```bash
./gradlew test
```

## Key Concepts Demonstrated

### TDD (Red-Green-Refactor)
- Write failing test first
- Implement minimal code to pass
- Refactor with confidence

### Nested Tests
- Organize tests hierarchically with `@Nested`
- Share setup with `@BeforeEach` at each level
- Build context as you nest deeper

### Parameterized Tests
- `@ValueSource` - Simple single values
- `@CsvSource` - Multiple arguments from CSV
- `@MethodSource` - Complex objects from methods
- `@EnumSource` - Test with enum values
- `@NullAndEmptySource` - Edge case testing

### AssertJ Fluent Assertions
- String assertions (contains, startsWith, etc.)
- Collection assertions (contains, hasSize, filteredOn)
- Exception assertions (assertThatThrownBy)
- Object extraction (extracting)
- Soft assertions (assertSoftly)

## JUnit Version Note

This module currently uses JUnit 5.11.0. When upgrading to JUnit 6:

1. Update the BOM version to 6.0.0
2. Ensure Java 17+ is configured
3. All test patterns work unchanged
