package edu.trincoll.controller;

import edu.trincoll.dto.CreateTaskRequest;
import edu.trincoll.dto.TaskDTO;
import edu.trincoll.dto.UpdateTaskRequest;
import edu.trincoll.model.Task;
import edu.trincoll.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<TaskDTO> getAllTasks(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<Task> tasks;
        if (status != null) {
            tasks = service.findByStatus(status);
        } else {
            tasks = service.findAll();
        }

        return tasks.stream()
            .skip(page * size)
            .limit(size)
            .map(TaskDTO::from)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long id) {
        Task task = service.findById(id);
        return ResponseEntity.ok(TaskDTO.from(task));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody CreateTaskRequest request) {
        Task task = service.createTask(request);
        URI location = URI.create("/api/tasks/" + task.getId());
        return ResponseEntity.created(location).body(TaskDTO.from(task));
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Long id,
                             @Valid @RequestBody UpdateTaskRequest request) {
        Task task = service.updateTask(id, request);
        return TaskDTO.from(task);
    }

    @PatchMapping("/{id}")
    public TaskDTO partialUpdate(@PathVariable Long id,
                                @RequestBody Map<String, Object> updates) {
        Task task = service.partialUpdate(id, updates);
        return TaskDTO.from(task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        service.deleteTask(id);
    }

    @GetMapping("/completed")
    public List<TaskDTO> getCompletedTasks() {
        return service.findByCompleted(true)
            .stream()
            .map(TaskDTO::from)
            .collect(Collectors.toList());
    }

    @GetMapping("/pending")
    public List<TaskDTO> getPendingTasks() {
        return service.findByCompleted(false)
            .stream()
            .map(TaskDTO::from)
            .collect(Collectors.toList());
    }

    @GetMapping("/projects/{projectId}/tasks")
    public List<TaskDTO> getProjectTasks(@PathVariable Long projectId) {
        return service.findByProjectId(projectId)
            .stream()
            .map(TaskDTO::from)
            .collect(Collectors.toList());
    }

    @PostMapping("/{id}/complete")
    public TaskDTO completeTask(@PathVariable Long id) {
        Task task = service.completeTask(id);
        return TaskDTO.from(task);
    }

    @GetMapping("/count")
    public Map<String, Long> getTaskCount() {
        return Map.of("count", service.count());
    }
}