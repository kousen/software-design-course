package edu.trincoll;

import edu.trincoll.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService.deleteAll();
    }

    @Test
    void shouldCreateAndRetrieveTask() throws Exception {
        String createJson = """
            {
                "title": "Integration Test Task",
                "description": "Testing full flow",
                "dueDate": "%s",
                "priority": 3
            }
            """.formatted(LocalDate.now().plusDays(7));

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"));

        mockMvc.perform(get("/api/tasks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value("Integration Test Task"));
    }

    @Test
    void shouldUpdateAndDeleteTask() throws Exception {
        String createJson = """
            {
                "title": "Task to Update",
                "description": "Original description",
                "priority": 2
            }
            """;

        var result = mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
            .andExpect(status().isCreated())
            .andReturn();

        String location = result.getResponse().getHeader("Location");
        String taskId = location.substring(location.lastIndexOf("/") + 1);

        String updateJson = """
            {
                "title": "Updated Task",
                "description": "New description",
                "completed": true
            }
            """;

        mockMvc.perform(put("/api/tasks/" + taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Updated Task"));

        mockMvc.perform(delete("/api/tasks/" + taskId))
            .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/tasks/" + taskId))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldHandleValidationErrors() throws Exception {
        String invalidJson = """
            {
                "title": "ab",
                "priority": 10,
                "assigneeEmail": "invalid-email"
            }
            """;

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
            .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    void shouldPatchTask() throws Exception {
        String createJson = """
            {
                "title": "Task to Patch",
                "priority": 1
            }
            """;

        var result = mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
            .andExpect(status().isCreated())
            .andReturn();

        String location = result.getResponse().getHeader("Location");
        String taskId = location.substring(location.lastIndexOf("/") + 1);

        String patchJson = """
            {
                "priority": 5,
                "completed": true
            }
            """;

        mockMvc.perform(patch("/api/tasks/" + taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(patchJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.priority").value(5))
            .andExpect(jsonPath("$.completed").value(true))
            .andExpect(jsonPath("$.title").value("Task to Patch"));
    }

    @Test
    void shouldFilterTasksByStatus() throws Exception {
        String task1 = """
            {"title": "Pending Task 1"}
            """;
        String task2 = """
            {"title": "Pending Task 2"}
            """;

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(task1))
            .andExpect(status().isCreated());

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(task2))
            .andExpect(status().isCreated());

        var result = mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"title": "Task to Complete"}
                    """))
            .andExpect(status().isCreated())
            .andReturn();

        String location = result.getResponse().getHeader("Location");
        String taskId = location.substring(location.lastIndexOf("/") + 1);

        mockMvc.perform(post("/api/tasks/" + taskId + "/complete"))
            .andExpect(status().isOk());

        mockMvc.perform(get("/api/tasks/pending"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));

        mockMvc.perform(get("/api/tasks/completed"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }
}