package edu.trincoll.service;

import edu.trincoll.dto.CreateTaskRequest;
import edu.trincoll.dto.UpdateTaskRequest;
import edu.trincoll.exception.TaskNotFoundException;
import edu.trincoll.model.Task;
import edu.trincoll.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task createTask(CreateTaskRequest request) {
        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setDueDate(request.dueDate());
        task.setAssigneeEmail(request.assigneeEmail());
        task.setPriority(request.priority());
        task.setProjectId(request.projectId());

        return repository.save(task);
    }

    public Task findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public List<Task> findAll() {
        return repository.findAll();
    }

    public List<Task> findByStatus(String status) {
        return repository.findByStatus(status);
    }

    public List<Task> findByCompleted(boolean completed) {
        return repository.findByCompleted(completed);
    }

    public List<Task> findByProjectId(Long projectId) {
        return repository.findByProjectId(projectId);
    }

    public Task updateTask(Long id, UpdateTaskRequest request) {
        Task task = findById(id);

        if (request.title() != null) {
            task.setTitle(request.title());
        }
        if (request.description() != null) {
            task.setDescription(request.description());
        }
        if (request.completed() != null) {
            task.setCompleted(request.completed());
        }
        if (request.dueDate() != null) {
            task.setDueDate(request.dueDate());
        }
        if (request.assigneeEmail() != null) {
            task.setAssigneeEmail(request.assigneeEmail());
        }
        if (request.priority() != null) {
            task.setPriority(request.priority());
        }
        if (request.status() != null) {
            task.setStatus(request.status());
        }

        task.setUpdatedAt(LocalDateTime.now());
        return repository.save(task);
    }

    public Task partialUpdate(Long id, Map<String, Object> updates) {
        Task task = findById(id);

        updates.forEach((key, value) -> {
            switch (key) {
                case "title" -> task.setTitle((String) value);
                case "description" -> task.setDescription((String) value);
                case "completed" -> task.setCompleted((Boolean) value);
                case "priority" -> task.setPriority((Integer) value);
                case "status" -> task.setStatus((String) value);
                case "assigneeEmail" -> task.setAssigneeEmail((String) value);
            }
        });

        task.setUpdatedAt(LocalDateTime.now());
        return repository.save(task);
    }

    public void deleteTask(Long id) {
        if (!repository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        repository.deleteById(id);
    }

    public Task completeTask(Long id) {
        Task task = findById(id);
        task.setCompleted(true);
        task.setStatus("completed");
        task.setUpdatedAt(LocalDateTime.now());
        return repository.save(task);
    }

    public long count() {
        return repository.count();
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}