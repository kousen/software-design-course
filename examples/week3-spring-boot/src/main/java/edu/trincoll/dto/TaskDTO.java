package edu.trincoll.dto;

import edu.trincoll.model.Task;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskDTO(
    Long id,
    String title,
    String description,
    boolean completed,
    LocalDate dueDate,
    Integer priority,
    String status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    String assigneeEmail
) {
    public static TaskDTO from(Task task) {
        return new TaskDTO(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.isCompleted(),
            task.getDueDate(),
            task.getPriority(),
            task.getStatus(),
            task.getCreatedAt(),
            task.getUpdatedAt(),
            task.getAssigneeEmail()
        );
    }

    public Task toTask() {
        Task task = new Task();
        task.setId(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setCompleted(completed);
        task.setDueDate(dueDate);
        task.setPriority(priority);
        task.setStatus(status);
        task.setCreatedAt(createdAt);
        task.setUpdatedAt(updatedAt);
        task.setAssigneeEmail(assigneeEmail);
        return task;
    }
}