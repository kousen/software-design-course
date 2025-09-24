package edu.trincoll.functional;

import edu.trincoll.model.Task;
import java.util.function.Predicate;

@FunctionalInterface
public interface TaskPredicate extends Predicate<Task> {

    default TaskPredicate and(TaskPredicate other) {
        return task -> this.test(task) && other.test(task);
    }

    default TaskPredicate or(TaskPredicate other) {
        return task -> this.test(task) || other.test(task);
    }

    default TaskPredicate negate() {
        return task -> !this.test(task);
    }

    static TaskPredicate byStatus(Task.Status status) {
        return task -> task.status() == status;
    }

    static TaskPredicate byPriority(Task.Priority priority) {
        return task -> task.priority() == priority;
    }

    static TaskPredicate hasTag(String tag) {
        return task -> task.tags() != null && task.tags().contains(tag);
    }

    static TaskPredicate isOverdue() {
        return Task::isOverdue;
    }

    static TaskPredicate isActive() {
        return Task::isActive;
    }
}