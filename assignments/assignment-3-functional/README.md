# Assignment 3: Functional Programming with Java

**Due:** Thursday, September 25 at 11:59 PM
**Points:** 100
**Submission:** Via GitHub (one per team)

## Overview

After mastering OOP principles in Assignments 1 and 2, you'll now apply functional programming concepts in Java. This assignment focuses on streams, lambda expressions, Optional, method references, and functional interfaces to process and analyze task data.

## Learning Objectives

- Master Java Stream API for data processing
- Use Optional for null-safe operations
- Apply lambda expressions and method references effectively
- Create and use custom functional interfaces
- Implement functional composition and higher-order functions
- Understand default and static methods in interfaces

## Assignment Structure

You're provided with a task management system that needs its core processing logic implemented using functional programming techniques. The domain model is complete, but all the functional operations need to be implemented.

## Part 1: Stream Operations (40 points)

### TaskAnalyzer Class
Implement all TODO methods in `TaskAnalyzer` using streams and functional programming:

#### Basic Stream Operations
- `filterTasks(Predicate<Task>)` - Filter using stream().filter()
- `getTaskTitles()` - Map tasks to titles using method references
- `getTopPriorityTasks(int)` - Sort by priority and limit results

#### Grouping and Collecting
- `groupByStatus()` - Use Collectors.groupingBy()
- `partitionByOverdue()` - Use Collectors.partitioningBy()
- `countTasksByPriority()` - Group and count with collectors

#### Reduction and Aggregation
- `getTotalEstimatedHours()` - Use reduce() or mapToInt().sum()
- `getAverageEstimatedHours()` - Calculate average with streams
- `getAllUniqueTags()` - Collect unique values with flatMap()

#### Match Operations
- `hasOverdueTasks()` - Use anyMatch()
- `areAllTasksAssigned()` - Use allMatch() or noneMatch()

## Part 2: Optional Operations (15 points)

Master the Optional API for null-safe operations:

- `findTaskById(Long)` - Return Optional<Task>
- `getTotalEstimatedHours()` - Handle null hours with Optional
- `getTaskSummary(Long)` - Chain Optional operations with map() and orElse()

Example Optional chaining:
```java
return tasks.stream()
    .filter(t -> t.id().equals(id))
    .findFirst()
    .map(task -> String.format("%s - %s", task.title(), task.status()))
    .orElse("Task not found");
```

## Part 3: Custom Functional Interfaces (25 points)

### TaskPredicate Interface
Extends `Predicate<Task>` with default methods:
- `and(TaskPredicate)` - Combine predicates
- `or(TaskPredicate)` - Alternative conditions
- `negate()` - Invert predicate
- Static factory methods for common filters

### TaskTransformer Interface
Extends `Function<Task, Task>` for transformations:
- `andThen(TaskTransformer)` - Compose transformations
- Static factory methods for common transformations

### TaskProcessor Interface
Custom interface for batch processing:
- `process(List<Task>)` - Process task collections
- `andThen(TaskProcessor)` - Chain processors

## Part 4: Higher-Order Functions (20 points)

### TaskProcessingEngine Class
Implement advanced functional programming patterns:

#### Function Composition
- `processPipeline()` - Chain multiple operations
- `filterAndTransform()` - Combine predicates and transformers

#### Lambda and Method References
- `transformAll()` - Apply UnaryOperator to all tasks
- `processTasksWithSideEffects()` - Use Consumer for side effects

#### Supplier and Factory Patterns
- `getOrCreateDefault()` - Use Supplier for lazy evaluation
- `generateTaskStream()` - Create infinite streams with generate()

#### BiFunction and Merging
- `mergeTasks()` - Combine two tasks using BiFunction
- `sortByMultipleCriteria()` - Compose multiple comparators

## Provided Classes

### Task Model (Complete)
```java
public record Task(
    Long id,
    String title,
    String description,
    Priority priority,
    Status status,
    Set<String> tags,
    LocalDateTime createdAt,
    LocalDateTime dueDate,
    Integer estimatedHours
)
```

### Test Suites (Complete)
- `TaskAnalyzerTest` - 22 tests for stream operations
- `TaskProcessingEngineTest` - 14 tests for functional patterns

## Grading Rubric

| Component | Points | Requirements |
|-----------|--------|--------------|
| **Stream Operations** | 40 | All TaskAnalyzer methods using streams correctly |
| **Optional Usage** | 15 | Proper Optional handling and chaining |
| **Custom Interfaces** | 25 | TaskPredicate, TaskTransformer, TaskProcessor implementation |
| **Higher-Order Functions** | 20 | TaskProcessingEngine with composition and lambdas |

## Getting Started

### 1. Setup
```bash
# Clone the assignment repository
git clone [assignment-repo-url]
cd assignment-3-functional

# Run tests to see what needs implementation
./gradlew test
```

### 2. Implementation Order

1. **Start with TaskAnalyzer** (easier methods first):
   - `filterTasks()` - Basic filter operation
   - `getTaskTitles()` - Simple map operation
   - `findTaskById()` - Introduction to Optional

2. **Move to grouping operations**:
   - `groupByStatus()` - Collectors.groupingBy()
   - `getAllUniqueTags()` - flatMap() and collect()

3. **Implement custom functional interfaces**:
   - Complete default methods in TaskPredicate
   - Add static factory methods

4. **Finish with TaskProcessingEngine**:
   - Start with simple transformations
   - Build up to complex compositions

### 3. Running Tests

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests TaskAnalyzerTest

# Run with detailed output
./gradlew test --info
```

## Tips and Examples

### Stream Pipeline Example
```java
return tasks.stream()
    .filter(task -> task.priority() == Priority.HIGH)
    .sorted(Comparator.comparing(Task::dueDate))
    .map(Task::title)
    .collect(Collectors.toList());
```

### Optional Chaining Example
```java
return findTaskById(id)
    .filter(Task::isActive)
    .map(Task::title)
    .orElse("No active task found");
```

### Custom Predicate Usage
```java
TaskPredicate urgent = TaskPredicate.byPriority(Priority.HIGH)
    .or(TaskPredicate.isOverdue())
    .and(TaskPredicate.isActive());
```

### Function Composition
```java
Function<List<Task>, List<Task>> pipeline =
    filterByPriority
    .andThen(sortByDueDate)
    .andThen(limitToTop10);
```

## Common Pitfalls to Avoid

❌ **DON'T:**
- Use traditional for loops when streams are more appropriate
- Ignore null checks - use Optional instead
- Modify state in lambda expressions
- Use get() on Optional without checking isPresent()
- Create unnecessarily complex stream pipelines

✅ **DO:**
- Use appropriate collectors (toList(), toSet(), groupingBy())
- Chain Optional operations instead of nested if statements
- Keep lambdas simple and readable
- Use method references where possible (Task::getTitle)
- Test edge cases (empty lists, null values)

## AI Collaboration

Document your AI usage in a comment at the top of `TaskAnalyzer.java`:

```java
/**
 * AI Collaboration Report:
 * Tool: [ChatGPT/Claude/Copilot/Gemini]
 *
 * Most Helpful Prompts:
 * 1. "How do I use Collectors.groupingBy with an enum?"
 * 2. "Explain Optional.flatMap vs Optional.map"
 *
 * Concepts Learned:
 * - [What you learned about functional programming]
 *
 * Team: [Member names]
 */
```

## Submission Requirements

1. All tests must pass: `./gradlew test`
2. Push completed code to GitHub
3. Submit repository URL to Moodle
4. Include team member names in submission

## Bonus Challenges (Optional, +10 points)

1. **Parallel Streams** - Add parallel processing where beneficial
2. **Custom Collector** - Create a custom Collector for task statistics
3. **Memoization** - Implement caching for expensive operations
4. **Reactive Streams** - Add CompletableFuture for async operations
5. **Performance Analysis** - Compare stream vs loop performance

## Questions?

- Office hours: Wednesdays 1:30-3:00 PM
- Email: kkousen@trincoll.edu

---

**Note:** This assignment emphasizes functional thinking. Embrace immutability, pure functions, and declarative style. The tests are comprehensive - let them guide your implementation!