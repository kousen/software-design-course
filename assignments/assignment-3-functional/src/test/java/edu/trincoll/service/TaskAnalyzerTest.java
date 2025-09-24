package edu.trincoll.service;

import edu.trincoll.functional.TaskPredicate;
import edu.trincoll.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class TaskAnalyzerTest {
    private TaskAnalyzer analyzer;
    private List<Task> testTasks;

    @BeforeEach
    void setUp() {
        testTasks = createTestTasks();
        analyzer = new TaskAnalyzer(testTasks);
    }

    private List<Task> createTestTasks() {
        return List.of(
            new Task(1L, "Write unit tests", "Create comprehensive test suite",
                Task.Priority.HIGH, Task.Status.IN_PROGRESS,
                Set.of("testing", "development"),
                LocalDateTime.now().minusDays(2),
                LocalDateTime.now().plusDays(1), 8),

            new Task(2L, "Code review", "Review team's pull requests",
                Task.Priority.MEDIUM, Task.Status.TODO,
                Set.of("review", "development"),
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().minusDays(1), 3),

            new Task(3L, "Deploy to production", "Deploy latest release",
                Task.Priority.CRITICAL, Task.Status.TODO,
                Set.of("deployment", "production"),
                LocalDateTime.now().minusDays(3),
                LocalDateTime.now().plusDays(2), 5),

            new Task(4L, "Update documentation", "Update API docs",
                Task.Priority.LOW, Task.Status.DONE,
                Set.of("documentation"),
                LocalDateTime.now().minusDays(5),
                LocalDateTime.now().minusDays(2), 2),

            new Task(5L, "Fix bug #123", "Critical production bug",
                Task.Priority.CRITICAL, Task.Status.IN_PROGRESS,
                Set.of("bug", "production"),
                LocalDateTime.now().minusHours(2),
                LocalDateTime.now().plusHours(4), 4),

            new Task(6L, "Team meeting", "Weekly sync",
                Task.Priority.MEDIUM, Task.Status.CANCELLED,
                Set.of("meeting"),
                LocalDateTime.now().minusDays(7),
                LocalDateTime.now().minusDays(3), null)
        );
    }

    @Nested
    @DisplayName("Filter Operations")
    class FilterTests {

        @Test
        @DisplayName("Should filter tasks by status")
        void filterByStatus() {
            List<Task> inProgressTasks = analyzer.filterTasks(
                task -> task.status() == Task.Status.IN_PROGRESS
            );

            assertThat(inProgressTasks)
                .hasSize(2)
                .extracting(Task::id)
                .containsExactlyInAnyOrder(1L, 5L);
        }

        @Test
        @DisplayName("Should filter tasks by priority")
        void filterByPriority() {
            List<Task> criticalTasks = analyzer.filterTasks(
                task -> task.priority() == Task.Priority.CRITICAL
            );

            assertThat(criticalTasks)
                .hasSize(2)
                .extracting(Task::title)
                .containsExactlyInAnyOrder("Deploy to production", "Fix bug #123");
        }

        @Test
        @DisplayName("Should filter using custom predicate")
        void filterWithCustomPredicate() {
            TaskPredicate highPriorityAndActive = TaskPredicate
                .byPriority(Task.Priority.HIGH)
                .and(TaskPredicate.isActive());

            List<Task> filtered = analyzer.filterWithCustomPredicate(highPriorityAndActive);

            assertThat(filtered)
                .hasSize(1)
                .first()
                .extracting(Task::title)
                .isEqualTo("Write unit tests");
        }

        @Test
        @DisplayName("Should filter overdue tasks")
        void filterOverdueTasks() {
            List<Task> overdueTasks = analyzer.filterTasks(Task::isOverdue);

            assertThat(overdueTasks)
                .hasSize(1)
                .first()
                .extracting(Task::title)
                .isEqualTo("Code review");
        }
    }

    @Nested
    @DisplayName("Optional Operations")
    class OptionalTests {

        @Test
        @DisplayName("Should find task by ID")
        void findTaskById() {
            Optional<Task> task = analyzer.findTaskById(3L);

            assertThat(task)
                .isPresent()
                .get()
                .extracting(Task::title)
                .isEqualTo("Deploy to production");
        }

        @Test
        @DisplayName("Should return empty Optional for non-existent ID")
        void findTaskByIdNotFound() {
            Optional<Task> task = analyzer.findTaskById(999L);

            assertThat(task).isEmpty();
        }

        @Test
        @DisplayName("Should calculate total estimated hours")
        void getTotalEstimatedHours() {
            Optional<Integer> total = analyzer.getTotalEstimatedHours();

            assertThat(total)
                .isPresent()
                .hasValue(22); // 8 + 3 + 5 + 2 + 4
        }

        @Test
        @DisplayName("Should calculate average estimated hours")
        void getAverageEstimatedHours() {
            OptionalDouble average = analyzer.getAverageEstimatedHours();

            assertThat(average)
                .isPresent()
                .hasValue(4.4); // 22 / 5 (task 6 has null hours)
        }

        @Test
        @DisplayName("Should get task summary")
        void getTaskSummary() {
            String summary = analyzer.getTaskSummary(1L);

            assertThat(summary).contains("Write unit tests");
        }

        @Test
        @DisplayName("Should return not found message for invalid ID")
        void getTaskSummaryNotFound() {
            String summary = analyzer.getTaskSummary(999L);

            assertThat(summary).isEqualTo("Task not found");
        }
    }

    @Nested
    @DisplayName("Grouping and Collecting")
    class GroupingTests {

        @Test
        @DisplayName("Should group tasks by status")
        void groupByStatus() {
            Map<Task.Status, List<Task>> grouped = analyzer.groupByStatus();

            assertThat(grouped)
                .hasSize(4)
                .containsKeys(Task.Status.TODO, Task.Status.IN_PROGRESS,
                             Task.Status.DONE, Task.Status.CANCELLED);

            assertThat(grouped.get(Task.Status.IN_PROGRESS)).hasSize(2);
            assertThat(grouped.get(Task.Status.TODO)).hasSize(2);
        }

        @Test
        @DisplayName("Should partition tasks by overdue status")
        void partitionByOverdue() {
            Map<Boolean, List<Task>> partitioned = analyzer.partitionByOverdue();

            assertThat(partitioned)
                .hasSize(2)
                .containsKeys(true, false);

            assertThat(partitioned.get(true))
                .hasSize(1)
                .extracting(Task::title)
                .containsExactly("Code review");

            assertThat(partitioned.get(false)).hasSize(5);
        }

        @Test
        @DisplayName("Should count tasks by priority")
        void countTasksByPriority() {
            Map<Task.Priority, Long> counts = analyzer.countTasksByPriority();

            assertThat(counts)
                .containsEntry(Task.Priority.CRITICAL, 2L)
                .containsEntry(Task.Priority.HIGH, 1L)
                .containsEntry(Task.Priority.MEDIUM, 2L)
                .containsEntry(Task.Priority.LOW, 1L);
        }

        @Test
        @DisplayName("Should get all unique tags")
        void getAllUniqueTags() {
            Set<String> tags = analyzer.getAllUniqueTags();

            assertThat(tags)
                .hasSize(8)
                .contains("testing", "development", "review",
                         "deployment", "production", "documentation",
                         "bug", "meeting");
        }

        @Test
        @DisplayName("Should get all tags sorted")
        void getAllTagsSorted() {
            List<String> tags = analyzer.getAllTagsSorted();

            assertThat(tags)
                .hasSize(11) // Some tasks have multiple tags
                .isSorted()
                .contains("bug", "deployment", "development", "documentation");
        }
    }

    @Nested
    @DisplayName("Sorting and Limiting")
    class SortingTests {

        @Test
        @DisplayName("Should get top priority tasks")
        void getTopPriorityTasks() {
            List<Task> topTasks = analyzer.getTopPriorityTasks(3);

            assertThat(topTasks)
                .hasSize(3)
                .extracting(Task::priority)
                .containsExactly(
                    Task.Priority.CRITICAL,
                    Task.Priority.CRITICAL,
                    Task.Priority.HIGH
                );
        }

        @Test
        @DisplayName("Should handle limit larger than task count")
        void getTopPriorityTasksWithLargeLimit() {
            List<Task> topTasks = analyzer.getTopPriorityTasks(10);

            assertThat(topTasks).hasSize(6);
        }
    }

    @Nested
    @DisplayName("Method References")
    class MethodReferenceTests {

        @Test
        @DisplayName("Should get task titles using method reference")
        void getTaskTitles() {
            List<String> titles = analyzer.getTaskTitles();

            assertThat(titles)
                .hasSize(6)
                .contains("Write unit tests", "Code review",
                         "Deploy to production", "Update documentation");
        }
    }

    @Nested
    @DisplayName("Match Operations")
    class MatchTests {

        @Test
        @DisplayName("Should detect overdue tasks")
        void hasOverdueTasks() {
            boolean hasOverdue = analyzer.hasOverdueTasks();

            assertThat(hasOverdue).isTrue();
        }

        @Test
        @DisplayName("Should check if all tasks are assigned")
        void areAllTasksAssigned() {
            boolean allAssigned = analyzer.areAllTasksAssigned();

            // In this test data, we consider tasks assigned if they have estimated hours
            // One task (team meeting) has null hours
            assertThat(allAssigned).isFalse();
        }
    }
}