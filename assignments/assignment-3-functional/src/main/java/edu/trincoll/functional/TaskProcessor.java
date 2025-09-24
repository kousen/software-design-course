package edu.trincoll.functional;

import edu.trincoll.model.Task;
import java.util.List;

@FunctionalInterface
public interface TaskProcessor {

    void process(List<Task> tasks);

    default TaskProcessor andThen(TaskProcessor after) {
        return tasks -> {
            this.process(tasks);
            after.process(tasks);
        };
    }

    static TaskProcessor logTasks(String message) {
        return tasks -> {
            System.out.println(message + ": " + tasks.size() + " tasks");
            tasks.forEach(task -> System.out.println("  - " + task.title()));
        };
    }
}