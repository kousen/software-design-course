package edu.trincoll.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record CreateTaskRequest(
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    String title,

    @Size(max = 500, message = "Description must not exceed 500 characters")
    String description,

    @Future(message = "Due date must be in the future")
    LocalDate dueDate,

    @Email(message = "Invalid email format")
    String assigneeEmail,

    @Min(value = 1, message = "Priority must be at least 1")
    @Max(value = 5, message = "Priority must not exceed 5")
    Integer priority,

    Long projectId
) {}