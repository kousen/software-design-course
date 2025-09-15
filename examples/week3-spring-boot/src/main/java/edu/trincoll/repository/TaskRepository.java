package edu.trincoll.repository;

import edu.trincoll.model.Task;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class TaskRepository {
    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(idGenerator.getAndIncrement());
        }
        tasks.put(task.getId(), task);
        return task;
    }

    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    public List<Task> findByStatus(String status) {
        return tasks.values().stream()
            .filter(task -> task.getStatus().equalsIgnoreCase(status))
            .collect(Collectors.toList());
    }

    public List<Task> findByProjectId(Long projectId) {
        return tasks.values().stream()
            .filter(task -> projectId.equals(task.getProjectId()))
            .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        tasks.remove(id);
    }

    public void deleteAll() {
        tasks.clear();
    }

    public boolean existsById(Long id) {
        return tasks.containsKey(id);
    }

    public long count() {
        return tasks.size();
    }

    public List<Task> findByCompleted(boolean completed) {
        return tasks.values().stream()
            .filter(task -> task.isCompleted() == completed)
            .collect(Collectors.toList());
    }
}