package edu.trincoll.exception;

public class TaskNotFoundException extends RuntimeException {
    private final Long taskId;

    public TaskNotFoundException(Long taskId) {
        super("Task with id " + taskId + " not found");
        this.taskId = taskId;
    }

    public Long getTaskId() {
        return taskId;
    }
}