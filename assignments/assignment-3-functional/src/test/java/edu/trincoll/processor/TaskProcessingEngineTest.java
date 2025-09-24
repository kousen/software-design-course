package edu.trincoll.processor;

import edu.trincoll.functional.TaskPredicate;
import edu.trincoll.functional.TaskProcessor;
import edu.trincoll.functional.TaskTransformer;
import edu.trincoll.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class TaskProcessingEngineTest {
    private TaskProcessingEngine engine;
    private List<Task> testTasks;

    @BeforeEach
    void setUp() {
        engine = new TaskProcessingEngine();
        testTasks = createTestTasks();
    }

    private List<Task> createTestTasks() {
        return Arrays.asList(
            new Task(1L, "Task 1", "Description 1",
                Task.Priority.HIGH, Task.Status.TODO,
                Set.of("urgent"), LocalDateTime.now(),
                LocalDateTime.now().plusDays(1), 4),

            new Task(2L, "Task 2", "Description 2",
                Task.Priority.LOW, Task.Status.IN_PROGRESS,
                Set.of("backend"), LocalDateTime.now(),
                LocalDateTime.now().plusDays(3), 2),

            new Task(3L, "Task 3", "Description 3",
                Task.Priority.MEDIUM, Task.Status.TODO,
                Set.of("frontend"), LocalDateTime.now(),
                LocalDateTime.now().plusDays(2), 6)
        );
    }

    @Nested
    @DisplayName("Pipeline Processing")
    class PipelineTests {

        @Test
        @DisplayName("Should process tasks through pipeline")
        void processPipeline() {
            List<Function<List<Task>, List<Task>>> operations = List.of(
                // Filter high priority
                tasks -> tasks.stream()
                    .filter(t -> t.priority() == Task.Priority.HIGH)
                    .collect(Collectors.toList()),
                // Sort by due date
                tasks -> tasks.stream()
                    .sorted(Comparator.comparing(Task::dueDate))
                    .collect(Collectors.toList())
            );

            List<Task> result = engine.processPipeline(testTasks, operations);

            assertThat(result)
                .hasSize(1)
                .first()
                .extracting(Task::id)
                .isEqualTo(1L);
        }

        @Test
        @DisplayName("Should handle empty pipeline")
        void emptyPipeline() {
            List<Task> result = engine.processPipeline(testTasks, List.of());

            assertThat(result).isEqualTo(testTasks);
        }
    }

    @Nested
    @DisplayName("Supplier Operations")
    class SupplierTests {

        @Test
        @DisplayName("Should use existing task from Optional")
        void getOrCreateDefaultExisting() {
            Task existingTask = testTasks.get(0);
            Optional<Task> taskOpt = Optional.of(existingTask);

            Task result = engine.getOrCreateDefault(taskOpt,
                () -> new Task(999L, "Default", "Default task",
                    Task.Priority.LOW, Task.Status.TODO,
                    Set.of(), LocalDateTime.now(), null, 1));

            assertThat(result).isEqualTo(existingTask);
        }

        @Test
        @DisplayName("Should create default task when Optional is empty")
        void getOrCreateDefaultEmpty() {
            Optional<Task> taskOpt = Optional.empty();
            Task defaultTask = new Task(999L, "Default", "Default task",
                Task.Priority.LOW, Task.Status.TODO,
                Set.of(), LocalDateTime.now(), null, 1);

            Task result = engine.getOrCreateDefault(taskOpt, () -> defaultTask);

            assertThat(result).isEqualTo(defaultTask);
        }

        @Test
        @DisplayName("Should generate task stream")
        void generateTaskStream() {
            AtomicInteger counter = new AtomicInteger(0);
            Supplier<Task> taskSupplier = () -> new Task(
                (long) counter.incrementAndGet(),
                "Generated Task " + counter.get(),
                "Auto-generated",
                Task.Priority.MEDIUM,
                Task.Status.TODO,
                Set.of("generated"),
                LocalDateTime.now(),
                null,
                1
            );

            Stream<Task> stream = engine.generateTaskStream(taskSupplier);

            List<Task> generated = stream.limit(5).collect(Collectors.toList());

            assertThat(generated)
                .hasSize(5)
                .extracting(Task::title)
                .containsExactly(
                    "Generated Task 1",
                    "Generated Task 2",
                    "Generated Task 3",
                    "Generated Task 4",
                    "Generated Task 5"
                );
        }
    }

    @Nested
    @DisplayName("Consumer Operations")
    class ConsumerTests {

        @Test
        @DisplayName("Should process tasks with side effects")
        void processTasksWithSideEffects() {
            List<String> processedTitles = new ArrayList<>();
            Consumer<Task> sideEffect = task -> processedTitles.add(task.title());

            engine.processTasksWithSideEffects(testTasks, sideEffect);

            assertThat(processedTitles)
                .hasSize(3)
                .containsExactly("Task 1", "Task 2", "Task 3");
        }

        @Test
        @DisplayName("Should handle empty list with consumer")
        void processEmptyListWithSideEffects() {
            AtomicInteger counter = new AtomicInteger(0);
            Consumer<Task> sideEffect = task -> counter.incrementAndGet();

            engine.processTasksWithSideEffects(List.of(), sideEffect);

            assertThat(counter.get()).isZero();
        }
    }

    @Nested
    @DisplayName("BiFunction Operations")
    class BiFunctionTests {

        @Test
        @DisplayName("Should merge two tasks")
        void mergeTasks() {
            Task task1 = testTasks.get(0);
            Task task2 = testTasks.get(1);

            BiFunction<Task, Task, Task> merger = (t1, t2) -> new Task(
                t1.id(),
                t1.title() + " & " + t2.title(),
                t1.description() + " | " + t2.description(),
                t1.priority().getWeight() >= t2.priority().getWeight() ?
                    t1.priority() : t2.priority(),
                t1.status(),
                Stream.concat(t1.tags().stream(), t2.tags().stream())
                    .collect(Collectors.toSet()),
                t1.createdAt(),
                t1.dueDate(),
                (t1.estimatedHours() != null && t2.estimatedHours() != null) ?
                    t1.estimatedHours() + t2.estimatedHours() : null
            );

            Task merged = engine.mergeTasks(task1, task2, merger);

            assertThat(merged.title()).isEqualTo("Task 1 & Task 2");
            assertThat(merged.tags()).containsExactlyInAnyOrder("urgent", "backend");
            assertThat(merged.estimatedHours()).isEqualTo(6);
        }
    }

    @Nested
    @DisplayName("UnaryOperator Operations")
    class UnaryOperatorTests {

        @Test
        @DisplayName("Should transform all tasks")
        void transformAll() {
            UnaryOperator<Task> markAsInProgress = task -> new Task(
                task.id(),
                task.title(),
                task.description(),
                task.priority(),
                Task.Status.IN_PROGRESS,
                task.tags(),
                task.createdAt(),
                task.dueDate(),
                task.estimatedHours()
            );

            List<Task> transformed = engine.transformAll(testTasks, markAsInProgress);

            assertThat(transformed)
                .hasSize(3)
                .extracting(Task::status)
                .containsOnly(Task.Status.IN_PROGRESS);
        }
    }

    @Nested
    @DisplayName("Custom Functional Interface Operations")
    class CustomInterfaceTests {

        @Test
        @DisplayName("Should filter and transform with custom interfaces")
        void filterAndTransform() {
            TaskPredicate filter = TaskPredicate.byStatus(Task.Status.TODO);
            TaskTransformer transformer = TaskTransformer.withPriority(Task.Priority.CRITICAL);

            List<Task> result = engine.filterAndTransform(testTasks, filter, transformer);

            assertThat(result)
                .hasSize(2)
                .extracting(Task::priority)
                .containsOnly(Task.Priority.CRITICAL);

            assertThat(result)
                .extracting(Task::id)
                .containsExactlyInAnyOrder(1L, 3L);
        }

        @Test
        @DisplayName("Should process in batches")
        void batchProcess() {
            List<Integer> batchSizes = new ArrayList<>();
            TaskProcessor processor = tasks -> batchSizes.add(tasks.size());

            engine.batchProcess(testTasks, 2, processor);

            assertThat(batchSizes)
                .hasSize(2)
                .containsExactly(2, 1);
        }
    }

    @Nested
    @DisplayName("Optional Chaining")
    class OptionalChainingTests {

        @Test
        @DisplayName("Should get highest priority task title")
        void getHighestPriorityTaskTitle() {
            Optional<String> title = engine.getHighestPriorityTaskTitle(testTasks);

            assertThat(title)
                .isPresent()
                .hasValue("Task 1");
        }

        @Test
        @DisplayName("Should return empty for empty list")
        void getHighestPriorityTaskTitleEmpty() {
            Optional<String> title = engine.getHighestPriorityTaskTitle(List.of());

            assertThat(title).isEmpty();
        }
    }

    @Nested
    @DisplayName("Comparator Composition")
    class ComparatorTests {

        @Test
        @DisplayName("Should sort by multiple criteria")
        void sortByMultipleCriteria() {
            List<Comparator<Task>> comparators = List.of(
                Comparator.comparing(Task::priority).reversed(),
                Comparator.comparing(Task::dueDate),
                Comparator.comparing(Task::title)
            );

            List<Task> sorted = engine.sortByMultipleCriteria(testTasks, comparators);

            assertThat(sorted)
                .extracting(Task::id)
                .containsExactly(1L, 3L, 2L);
        }

        @Test
        @DisplayName("Should handle single comparator")
        void sortBySingleCriterion() {
            List<Comparator<Task>> comparators = List.of(
                Comparator.comparing(Task::title)
            );

            List<Task> sorted = engine.sortByMultipleCriteria(testTasks, comparators);

            assertThat(sorted)
                .extracting(Task::title)
                .containsExactly("Task 1", "Task 2", "Task 3");
        }
    }
}