# Assignment 3: Game of Life - Functional Programming (Simplified)

## Overview

In this assignment, you will apply functional programming concepts to extend Conway's Game of Life implementation. You'll work with streams, lambdas, and Optional while following test-first development. This simplified version provides starter code and example implementations to help you learn functional programming patterns.

## Learning Objectives

- Practice Test-Driven Development methodology
- Use Java's functional programming features (lambdas, streams, Optional)
- Implement pattern detection algorithms
- Work with higher-order functions
- Write clean, testable, functional code

## Prerequisites

- Understanding of Conway's Game of Life rules
- Basic knowledge of JUnit 5 testing
- Familiarity with Java 21 features covered in class

## Getting Started

1. Fork this repository to your GitHub account
2. Clone your fork locally
3. Create a new branch: `git checkout -b assignment-4-solution`
4. Run initial tests: `./gradlew test` (they should fail)
5. Implement features using TDD
6. Commit frequently with meaningful messages

## What's Provided

### Fully Implemented Infrastructure Classes
- `Grid.java` - Complete grid implementation with evolution logic
- `Cell.java` - Record class for cell coordinates
- `CellState.java` - Enum for cell states (ALIVE/DEAD)
- `GameRules.java` - Functional interface with Conway's rules and variants
- `Pattern.java` - Enum with common Game of Life patterns (Block, Glider, etc.)

### Test Files (Complete)
All test files are provided with comprehensive test cases:
- `PatternDetectorTest.java` - 16 tests for pattern detection
- `GridAnalyzerTest.java` - Tests for grid analysis operations
- `PatternTransformerTest.java` - Tests for transformations
- `PopulationStatsTest.java` - Tests for population statistics
- `GameVariantsTest.java` - Tests for game rule variants

### What You Need to Implement

#### Core Requirements (80% of grade)
These classes have partial implementations and clear guidance:

1. **PatternDetector.java** - Pattern detection logic
   - `isStillLife()` - PROVIDED as example
   - `findPeriod()` - Simple version: only check return to initial state
   - `classifyPattern()` - Only need STILL_LIFE, OSCILLATOR, and UNKNOWN

2. **GridAnalyzer.java** - Grid analysis operations
   - `groupByNeighborCount()` - PROVIDED as example
   - `findChangingCells()` - Partial implementation provided
   - `findIsolatedCells()` - Simple filter operation
   - `calculateDensity()` - Simple calculation

3. **PopulationStats.java** - Population tracking
   - `trackPopulation()` - PROVIDED as example
   - `findPopulationPeak()` - Partial implementation provided
   - `calculateAveragePopulation()` - Use trackPopulation result
   - `findStabilizationPoint()` - Simplified version

4. **GameVariants.java** - Custom game rule variants
   - `createVariant()` - Partial implementation provided
   - `dayAndNight()` - PROVIDED as example
   - `lifeWithoutDeath()` - Simple use of createVariant

#### Bonus/Optional (20% extra credit)
5. **PatternTransformer.java** - All methods are optional for extra credit
6. **GameVariants.probabilistic()** - Optional advanced feature

## Development Approach

This assignment uses a **test-first development** approach:
1. All tests are pre-written and will fail initially
2. Run tests for a specific feature: `./gradlew test --tests PatternDetectorTest`
3. Implement just enough code to make the test pass
4. Refactor using functional programming best practices
5. Repeat for the next test

## Part 1: Pattern Detection (40 points)

Implement pattern detection functionality in `PatternDetector.java`. Tests are provided in `PatternDetectorTest.java`.

### 1.1 Still Life Detection (15 points)

A still life is a pattern that doesn't change between generations.

```java
public class PatternDetector {
    /**
     * Determines if a grid represents a still life pattern.
     * A still life remains unchanged after one evolution.
     *
     * @param grid The grid to check
     * @param rules The game rules to apply
     * @return true if the grid is a still life
     */
    public boolean isStillLife(Grid grid, GameRules rules) {
        // TODO: Implement using functional programming
        throw new UnsupportedOperationException("Not implemented");
    }
}
```

**Tests Provided** (in PatternDetectorTest.java):
- Block pattern (2x2) is a still life
- Beehive pattern is a still life
- Boat pattern is a still life
- Blinker pattern is NOT a still life
- Empty grid is a still life
- Single cell dies (not a still life)

### 1.2 Oscillator Detection (15 points)

An oscillator is a pattern that returns to its initial state after a fixed number of generations (the period).

```java
/**
 * Finds the period of an oscillating pattern.
 *
 * @param grid The initial grid
 * @param rules The game rules to apply
 * @param maxGenerations Maximum generations to check
 * @return Optional containing the period, or empty if not an oscillator
 */
public Optional<Integer> findPeriod(Grid grid, GameRules rules,
                                   int maxGenerations) {
    // TODO: Implement using streams and Optional
    throw new UnsupportedOperationException("Not implemented");
}
```

**Tests Provided**:
- Blinker has period 2
- Toad has period 2
- Beacon has period 2
- Still life has period 1
- Glider returns empty (it's a spaceship, not an oscillator)
- Pattern that dies returns empty

### 1.3 Pattern Classification (10 points)

```java
public enum PatternType {
    STILL_LIFE,
    OSCILLATOR,
    SPACESHIP,
    UNKNOWN
}

/**
 * Classifies a pattern based on its behavior.
 *
 * @param grid The pattern to classify
 * @param rules The game rules
 * @param generations How many generations to observe
 * @return The pattern classification
 */
public PatternType classifyPattern(Grid grid, GameRules rules,
                                  int generations) {
    // TODO: Implement using functional approach
    throw new UnsupportedOperationException("Not implemented");
}
```

## Part 2: Functional Grid Operations (30 points)

Implement these operations using streams, lambdas, and functional programming. Tests are provided in `GridAnalyzerTest.java` and `PatternTransformerTest.java`.

### 2.1 Grid Analysis (10 points)

```java
public class GridAnalyzer {
    /**
     * Finds all cells that will change state in the next generation.
     * Use streams to identify cells that will flip from alive to dead
     * or dead to alive.
     *
     * @return Stream of cells that will change
     */
    public Stream<Cell> findChangingCells(Grid grid, GameRules rules) {
        // TODO: Implement
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Groups cells by their number of live neighbors.
     *
     * @return Map from neighbor count to list of cells
     */
    public Map<Integer, List<Cell>> groupByNeighborCount(Grid grid) {
        // TODO: Use Collectors.groupingBy
        throw new UnsupportedOperationException("Not implemented");
    }
}
```

### 2.2 Pattern Transformations (10 points)

```java
public class PatternTransformer {
    /**
     * Rotates a pattern 90 degrees clockwise.
     * Use functional operations to transform coordinates.
     *
     * @param grid The grid to rotate
     * @return New rotated grid
     */
    public Grid rotate90(Grid grid) {
        // TODO: Implement with streams
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Finds all positions where a pattern could be placed
     * in a larger grid without overlapping existing live cells.
     *
     * @param target The target grid
     * @param pattern The pattern to place
     * @return Stream of valid positions (top-left corners)
     */
    public Stream<Cell> findValidPlacements(Grid target, Grid pattern) {
        // TODO: Implement
        throw new UnsupportedOperationException("Not implemented");
    }
}
```

### 2.3 Population Statistics (10 points)

```java
public class PopulationStats {
    /**
     * Tracks population changes over multiple generations.
     * Returns a list of population counts for each generation.
     *
     * @param initial Starting grid
     * @param generations Number of generations to simulate
     * @return List of population counts
     */
    public List<Integer> trackPopulation(Grid initial, int generations) {
        // TODO: Use Stream.iterate or similar
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Finds the generation with maximum population
     * within the given range.
     *
     * @return Optional containing the generation number and count
     */
    public Optional<PopulationPeak> findPopulationPeak(
            Grid initial, int maxGenerations) {
        // TODO: Implement
        throw new UnsupportedOperationException("Not implemented");
    }

    public record PopulationPeak(int generation, int population) {}
}
```

## Part 3: Game Variants (20 points)

Implement different Game of Life variants using functional programming. Tests are provided in `GameVariantsTest.java`.

### 3.1 Rule Variants (10 points)

```java
public class GameVariants {
    /**
     * Creates a custom game variant with specified birth/survival rules.
     * Birth rules: dead cells with this many neighbors become alive
     * Survival rules: living cells with this many neighbors survive
     *
     * Example: B3/S23 is standard Conway's Life
     *         B36/S23 is HighLife
     *
     * @param birthCounts Set of neighbor counts for birth
     * @param survivalCounts Set of neighbor counts for survival
     * @return GameRules function
     */
    public static BiFunction<CellState, Integer, CellState>
            createVariant(Set<Integer> birthCounts,
                         Set<Integer> survivalCounts) {
        // TODO: Return a function that implements these rules
        throw new UnsupportedOperationException("Not implemented");
    }
}
```

**Tests Provided**:
- Standard Conway rules (B3/S23)
- HighLife rules (B36/S23)
- Seeds rules (B2/S)
- Conway survival rules for all neighbor counts (0-8)
- Predefined variants (Day and Night, Life Without Death)

### 3.2 Boundary Conditions (10 points)

```java
public class BoundaryVariants {
    /**
     * Creates a toroidal (wrapping) boundary condition.
     * Cells on edges wrap around to opposite side.
     *
     * @return Function that wraps cell coordinates
     */
    public static Function<Cell, Optional<Cell>>
            createToroidal(int rows, int cols) {
        // TODO: Implement wrapping logic
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Creates a reflecting boundary condition.
     * Cells at edges see themselves as neighbors.
     *
     * @return Function that reflects coordinates
     */
    public static Function<Cell, Optional<Cell>>
            createReflecting(int rows, int cols) {
        // TODO: Implement
        throw new UnsupportedOperationException("Not implemented");
    }
}
```

## Part 4: Performance Optimization (10 points)

Implement parallel processing for better performance on large grids. Tests are provided in `PopulationStatsTest.java`.

### 4.1 Parallel Evolution

```java
public class ParallelEvolution {
    /**
     * Evolves a grid using parallel streams for better performance
     * on large grids.
     *
     * @param grid Grid to evolve
     * @param rules Rules to apply
     * @return Evolved grid
     */
    public Grid evolveParallel(Grid grid, GameRules rules) {
        // TODO: Use parallelStream()
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Benchmark parallel vs sequential evolution.
     * Return the speedup factor (parallel time / sequential time).
     *
     * @param size Grid size to test
     * @param iterations Number of evolution steps
     * @return Speedup factor
     */
    public double benchmarkParallel(int size, int iterations) {
        // TODO: Implement benchmarking
        throw new UnsupportedOperationException("Not implemented");
    }
}
```

## Testing Requirements

1. **Test Coverage**: Minimum 80% code coverage
2. **Test-First Development Approach**:
   - Tests are already provided and will fail initially
   - Write minimal code to make each test pass
   - Refactor using functional programming principles
   - Commit after each red-green-refactor cycle

3. **Running Tests**:
   ```bash
   # Run all tests
   ./gradlew test

   # Run specific test class
   ./gradlew test --tests PatternDetectorTest

   # Run with coverage report
   ./gradlew jacocoTestReport
   ```

4. **Test Organization** (already implemented):
   - Tests use JUnit 5 with nested test classes
   - Parameterized tests for testing multiple patterns
   - AssertJ for fluent assertions

## Implementation Guidance

### Key Functional Programming Patterns

#### 1. Pattern Detection (PatternDetector.java)
```java
// Still Life Detection Hint:
// Compare grid with grid.evolveWith(rules)
// Use Grid.equals() to check if they're the same

// Period Detection Hint:
// Use Stream.iterate() to generate sequence of evolved grids
// Keep track of seen states to find repetition
// IntStream.range() for limiting generations

// Pattern Classification Hint:
// Reuse your isStillLife() and findPeriod() methods
// Check if pattern moves by comparing cell positions
```

#### 2. Grid Analysis (GridAnalyzer.java)
```java
// Finding Changing Cells Hint:
Grid evolved = grid.evolveWith(rules);
// Stream through all cells and filter where:
// grid.getCellState(cell) != evolved.getCellState(cell)

// Group by Neighbor Count Hint:
grid.getAllCells().stream()
    .collect(Collectors.groupingBy(
        cell -> grid.countLiveNeighbors(cell)
    ));
```

#### 3. Pattern Transformations (PatternTransformer.java)
```java
// Rotation Hint:
// For 90° clockwise: new_row = col, new_col = (rows - 1 - row)
// Use streams to transform all live cells

// Valid Placements Hint:
// Stream through all possible positions
// Filter where pattern doesn't overlap existing live cells
```

#### 4. Population Statistics (PopulationStats.java)
```java
// Track Population Hint:
Stream.iterate(initial, grid -> grid.evolveWith(rules))
    .limit(generations + 1)
    .map(Grid::countLiveCells)
    .toList();

// Find Peak Hint:
// Use IntStream.range() with trackPopulation
// Find max using Comparator
```

#### 5. Game Variants (GameVariants.java)
```java
// Create Variant Hint:
return (current, neighbors) -> {
    if (current.isAlive()) {
        return survivalCounts.contains(neighbors)
            ? CellState.ALIVE : CellState.DEAD;
    } else {
        return birthCounts.contains(neighbors)
            ? CellState.ALIVE : CellState.DEAD;
    }
};
```

### Common Stream Operations You'll Need

- **Stream.iterate()** - Generate infinite sequences
- **IntStream.range()** - Create numeric ranges
- **Stream.limit()** - Limit stream size
- **Collectors.groupingBy()** - Group elements by key
- **Collectors.toMap()** - Collect to Map
- **Optional.of() / Optional.empty()** - Create Optionals
- **flatMap()** - Flatten nested streams
- **distinct()** - Remove duplicates
- **allMatch() / anyMatch()** - Check conditions

### Tips for Success

1. **Start Simple**: Get a basic working solution first, then refactor to be more functional
2. **Use the Grid API**: Methods like `evolveWith()`, `countLiveNeighbors()`, `getAllCells()` are already implemented
3. **Avoid State Mutation**: Create new objects rather than modifying existing ones
4. **Chain Operations**: Use method chaining with streams for cleaner code
5. **Test Incrementally**: Run specific tests as you implement each method

### Example Implementation to Get Started

Here's a complete implementation of `isStillLife` to show the expected style:

```java
public boolean isStillLife(Grid grid, GameRules rules) {
    // A still life doesn't change when evolved
    Grid nextGeneration = grid.evolveWith(rules);
    return grid.equals(nextGeneration);
}
```

And here's `trackPopulation` to demonstrate Stream usage:

```java
public List<Integer> trackPopulation(Grid initial, GameRules rules, int generations) {
    return Stream.iterate(initial, grid -> grid.evolveWith(rules))
        .limit(generations + 1)  // +1 to include initial state
        .map(Grid::countLiveCells)
        .toList();
}
```

Use these as templates for the functional style expected in other methods.

## Functional Programming Requirements

Your implementation MUST use:

1. **Streams** for collection processing (no traditional for loops for collections)
2. **Lambda expressions** or method references where appropriate
3. **Optional** for potentially absent values (no null returns)
4. **Functional interfaces** (Predicate, Function, etc.)
5. **Immutable data** where possible (don't modify existing grids)

## Grading Rubric (Simplified)

### Base Credit (100 points)

| Category | Points | Criteria |
|----------|---------|----------|
| **PatternDetector** | 20 | findPeriod and classifyPattern work |
| **GridAnalyzer** | 20 | findChangingCells, findIsolatedCells, calculateDensity work |
| **PopulationStats** | 20 | findPopulationPeak, calculateAveragePopulation, findStabilizationPoint work |
| **GameVariants** | 20 | createVariant and lifeWithoutDeath work |
| **Functional Style** | 15 | Proper use of streams, lambdas, Optional |
| **Code Passes Tests** | 5 | All non-bonus tests pass |

### Bonus Credit (20 points)
| Category | Points | Criteria |
|----------|---------|----------|
| **PatternTransformer** | 10 | Any 2 methods implemented correctly |
| **Advanced Features** | 10 | probabilistic variant or spaceship detection |

## Submission

1. Ensure all tests pass: `./gradlew test`
2. Check test coverage: `./gradlew jacocoTestReport`
3. Commit and push your solution branch
4. Create a pull request to your main branch
5. Submit the PR link on Moodle

## Tips

- Start with the simplest test cases
- Use the debugger to understand grid evolution
- Refer to the slides for functional programming patterns
- Don't try to make everything functional at once - refactor incrementally
- Ask for help during office hours if stuck

## Bonus Challenge (Optional, +10 points)

Implement a pattern library that can:
1. Load patterns from RLE (Run Length Encoded) format
2. Save patterns to RLE format
3. Search for patterns within a larger grid
4. Generate random patterns with specific properties

```java
public class PatternLibrary {
    public Grid loadRLE(String rleString) { /* ... */ }
    public String saveRLE(Grid grid) { /* ... */ }
    public Stream<Cell> findPattern(Grid haystack, Grid needle) { /* ... */ }
    public Grid generateRandom(int size, double density, long seed) { /* ... */ }
}
```

## Resources

- [Functional Programming in Java](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html)
- [Stream API Tutorial](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html)
- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Conway's Game of Life Patterns](https://conwaylife.com/wiki/Main_Page)

Good luck! Remember: Red → Green → Refactor! 🚦