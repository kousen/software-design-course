package edu.trincoll.service;

import edu.trincoll.dto.CreateTaskRequest;
import edu.trincoll.dto.UpdateTaskRequest;
import edu.trincoll.exception.TaskNotFoundException;
import edu.trincoll.model.Task;
import edu.trincoll.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskService service;

    private Task testTask;

    @BeforeEach
    void setUp() {
        testTask = new Task(1L, "Test Task");
        testTask.setDescription("Test Description");
        testTask.setPriority(3);
    }

    @Test
    void shouldCreateTask() {
        CreateTaskRequest request = new CreateTaskRequest(
            "New Task",
            "Description",
            LocalDate.now().plusDays(7),
            "user@example.com",
            3,
            null
        );

        when(repository.save(any(Task.class))).thenAnswer(invocation -> {
            Task task = invocation.getArgument(0);
            task.setId(1L);
            return task;
        });

        Task created = service.createTask(request);

        assertThat(created.getTitle()).isEqualTo("New Task");
        assertThat(created.getDescription()).isEqualTo("Description");
        assertThat(created.getPriority()).isEqualTo(3);
        verify(repository).save(any(Task.class));
    }

    @Test
    void shouldFindTaskById() {
        when(repository.findById(1L)).thenReturn(Optional.of(testTask));

        Task found = service.findById(1L);

        assertThat(found).isEqualTo(testTask);
        verify(repository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(999L))
            .isInstanceOf(TaskNotFoundException.class)
            .hasMessageContaining("999");
    }

    @Test
    void shouldFindAllTasks() {
        when(repository.findAll()).thenReturn(List.of(testTask));

        List<Task> tasks = service.findAll();

        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0)).isEqualTo(testTask);
    }

    @Test
    void shouldUpdateTask() {
        UpdateTaskRequest request = new UpdateTaskRequest(
            "Updated Title",
            "Updated Description",
            true,
            null,
            null,
            4,
            "completed"
        );

        when(repository.findById(1L)).thenReturn(Optional.of(testTask));
        when(repository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task updated = service.updateTask(1L, request);

        assertThat(updated.getTitle()).isEqualTo("Updated Title");
        assertThat(updated.getDescription()).isEqualTo("Updated Description");
        assertThat(updated.isCompleted()).isTrue();
        assertThat(updated.getPriority()).isEqualTo(4);
        verify(repository).save(testTask);
    }

    @Test
    void shouldPartiallyUpdateTask() {
        Map<String, Object> updates = Map.of(
            "title", "Partially Updated",
            "priority", 5
        );

        when(repository.findById(1L)).thenReturn(Optional.of(testTask));
        when(repository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task updated = service.partialUpdate(1L, updates);

        assertThat(updated.getTitle()).isEqualTo("Partially Updated");
        assertThat(updated.getPriority()).isEqualTo(5);
        assertThat(updated.getDescription()).isEqualTo("Test Description");
    }

    @Test
    void shouldDeleteTask() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deleteTask(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentTask() {
        when(repository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> service.deleteTask(999L))
            .isInstanceOf(TaskNotFoundException.class);

        verify(repository, never()).deleteById(999L);
    }

    @Test
    void shouldCompleteTask() {
        when(repository.findById(1L)).thenReturn(Optional.of(testTask));
        when(repository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task completed = service.completeTask(1L);

        assertThat(completed.isCompleted()).isTrue();
        assertThat(completed.getStatus()).isEqualTo("completed");
        verify(repository).save(testTask);
    }

    @Test
    void shouldFindTasksByStatus() {
        when(repository.findByStatus("pending")).thenReturn(List.of(testTask));

        List<Task> tasks = service.findByStatus("pending");

        assertThat(tasks).hasSize(1);
        verify(repository).findByStatus("pending");
    }

    @Test
    void shouldCountTasks() {
        when(repository.count()).thenReturn(5L);

        long count = service.count();

        assertThat(count).isEqualTo(5L);
        verify(repository).count();
    }
}