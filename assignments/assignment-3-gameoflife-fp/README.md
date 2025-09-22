# Assignment 3: Game of Life - Functional Programming & TDD

## Overview

In this assignment, you will apply Test-Driven Development (TDD) and functional programming concepts to extend Conway's Game of Life implementation. You'll work with streams, lambdas, Optional, and method references while following the red-green-refactor cycle.

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

## Part 1: Pattern Detection (40 points)

Using TDD, implement pattern detection functionality in `PatternDetector.java`.

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

**Required Tests** (write these FIRST):
- Block pattern (2x2) is a still life
- Beehive pattern is a still life
- Boat pattern is a still life
- Blinker pattern is NOT a still life
- Empty grid is a still life

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

**Required Tests**:
- Blinker has period 2
- Toad has period 2
- Beacon has period 2
- Pulsar has period 3
- Still life has period 1
- Glider returns empty (it's a spaceship, not an oscillator)

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

Implement these operations using streams, lambdas, and functional programming.

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

Implement different Game of Life variants using functional programming.

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

**Test Cases**:
- Standard Conway rules (B3/S23)
- HighLife rules (B36/S23)
- Seeds rules (B2/S)
- Custom rules

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
2. **TDD Approach**:
   - Write test first (it should fail)
   - Write minimal code to pass
   - Refactor if needed
   - Commit after each red-green-refactor cycle

3. **Test Organization**:
   ```java
   @Nested
   @DisplayName("Pattern Detection")
   class PatternDetectionTests {
       @Test
       void detectsStillLife() { /* ... */ }

       @ParameterizedTest
       @MethodSource("oscillatorPatterns")
       void detectsOscillators(Pattern pattern, int expectedPeriod) { /* ... */ }
   }
   ```

## Functional Programming Requirements

Your implementation MUST use:

1. **Streams** for collection processing (no traditional for loops for collections)
2. **Lambda expressions** or method references where appropriate
3. **Optional** for potentially absent values (no null returns)
4. **Functional interfaces** (Predicate, Function, etc.)
5. **Immutable data** where possible (don't modify existing grids)

## Grading Rubric

| Category | Points | Criteria |
|----------|---------|----------|
| **Functionality** | 40 | All features work correctly |
| **Testing** | 25 | Comprehensive tests, TDD approach evident |
| **Functional Style** | 20 | Proper use of streams, lambdas, Optional |
| **Code Quality** | 10 | Clean, readable, well-organized |
| **Documentation** | 5 | Clear JavaDoc, meaningful variable names |

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