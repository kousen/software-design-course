package edu.trincoll.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.trincoll.dto.CreateTaskRequest;
import edu.trincoll.dto.TaskDTO;
import edu.trincoll.model.Task;
import edu.trincoll.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Task testTask;

    @BeforeEach
    void setUp() {
        testTask = new Task(1L, "Test Task");
        testTask.setDescription("Test Description");
        testTask.setPriority(3);
    }

    @Test
    void shouldGetAllTasks() throws Exception {
        when(service.findAll()).thenReturn(List.of(testTask));

        mockMvc.perform(get("/api/tasks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].title").value("Test Task"));
    }

    @Test
    void shouldGetTaskById() throws Exception {
        when(service.findById(1L)).thenReturn(testTask);

        mockMvc.perform(get("/api/tasks/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void shouldCreateTask() throws Exception {
        CreateTaskRequest request = new CreateTaskRequest(
            "New Task",
            "Task description",
            LocalDate.now().plusDays(7),
            "user@example.com",
            3,
            null
        );

        Task createdTask = new Task(1L, "New Task");
        createdTask.setDescription("Task description");
        when(service.createTask(any())).thenReturn(createdTask);

        String json = """
            {
                "title": "New Task",
                "description": "Task description",
                "dueDate": "%s",
                "assigneeEmail": "user@example.com",
                "priority": 3
            }
            """.formatted(LocalDate.now().plusDays(7));

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("New Task"));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        Task updatedTask = new Task(1L, "Updated Task");
        when(service.updateTask(eq(1L), any())).thenReturn(updatedTask);

        String json = """
            {
                "title": "Updated Task",
                "description": "Updated description"
            }
            """;

        mockMvc.perform(put("/api/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Updated Task"));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        doNothing().when(service).deleteTask(1L);

        mockMvc.perform(delete("/api/tasks/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetTasksByStatus() throws Exception {
        when(service.findByStatus("completed")).thenReturn(List.of(testTask));

        mockMvc.perform(get("/api/tasks?status=completed"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value("Test Task"));
    }

    @Test
    void shouldHandlePagination() throws Exception {
        List<Task> tasks = List.of(
            new Task(1L, "Task 1"),
            new Task(2L, "Task 2"),
            new Task(3L, "Task 3")
        );
        when(service.findAll()).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks?page=0&size=2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldValidateCreateTaskRequest() throws Exception {
        String json = """
            {
                "title": "",
                "priority": 10
            }
            """;

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldCompleteTask() throws Exception {
        Task completedTask = new Task(1L, "Test Task");
        completedTask.setCompleted(true);
        when(service.completeTask(1L)).thenReturn(completedTask);

        mockMvc.perform(post("/api/tasks/1/complete"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void shouldGetProjectTasks() throws Exception {
        when(service.findByProjectId(123L)).thenReturn(List.of(testTask));

        mockMvc.perform(get("/api/tasks/projects/123/tasks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value("Test Task"));
    }
}