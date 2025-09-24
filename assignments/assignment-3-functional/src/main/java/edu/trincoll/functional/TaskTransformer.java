package edu.trincoll.functional;

import edu.trincoll.model.Task;
import java.util.function.Function;

@FunctionalInterface
public interface TaskTransformer extends Function<Task, Task> {

    default TaskTransformer andThen(TaskTransformer after) {
        return task -> after.apply(this.apply(task));
    }

    static TaskTransformer withStatus(Task.Status newStatus) {
        return task -> new Task(
            task.id(),
            task.title(),
            task.description(),
            task.priority(),
            newStatus,
            task.tags(),
            task.createdAt(),
            task.dueDate(),
            task.estimatedHours()
        );
    }

    static TaskTransformer withPriority(Task.Priority newPriority) {
        return task -> new Task(
            task.id(),
            task.title(),
            task.description(),
            newPriority,
            task.status(),
            task.tags(),
            task.createdAt(),
            task.dueDate(),
            task.estimatedHours()
        );
    }
}