package edu.trincoll.model;

import java.time.LocalDateTime;
import java.util.Set;

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
) {
    public enum Priority {
        LOW(1), MEDIUM(2), HIGH(3), CRITICAL(4);

        private final int weight;

        Priority(int weight) {
            this.weight = weight;
        }

        public int getWeight() {
            return weight;
        }
    }

    public enum Status {
        TODO, IN_PROGRESS, BLOCKED, DONE, CANCELLED
    }

    public boolean isOverdue() {
        return dueDate != null &&
               LocalDateTime.now().isAfter(dueDate) &&
               status != Status.DONE &&
               status != Status.CANCELLED;
    }

    public boolean isActive() {
        return status == Status.TODO || status == Status.IN_PROGRESS;
    }
}