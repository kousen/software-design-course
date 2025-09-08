package edu.trincoll.interfaces;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryTaskRepository implements TaskRepository {
    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(idGenerator.getAndIncrement());
        }
        tasks.put(task.getId(), task);
        return task;
    }
    
    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }
    
    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }
    
    @Override
    public boolean existsById(Long id) {
        return tasks.containsKey(id);
    }
    
    @Override
    public void deleteById(Long id) {
        tasks.remove(id);
    }
    
    @Override
    public void delete(Task task) {
        if (task != null && task.getId() != null) {
            tasks.remove(task.getId());
        }
    }
    
    @Override
    public void deleteAll() {
        tasks.clear();
    }
    
    @Override
    public long count() {
        return tasks.size();
    }
    
    @Override
    public List<Task> saveAll(List<Task> entities) {
        return entities.stream()
                .map(this::save)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Task> findByStatus(Task.TaskStatus status) {
        return tasks.values().stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Task> findByPriority(Task.TaskPriority priority) {
        return tasks.values().stream()
                .filter(task -> task.getPriority() == priority)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Task> findByTag(String tag) {
        return tasks.values().stream()
                .filter(task -> task.hasTag(tag))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Task> findOverdueTasks() {
        return tasks.values().stream()
                .filter(Task::isOverdue)
                .sorted(Comparator.comparing(Task::getDueDate))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Task> findByDueDateBetween(LocalDateTime start, LocalDateTime end) {
        return tasks.values().stream()
                .filter(task -> task.getDueDate() != null)
                .filter(task -> !task.getDueDate().isBefore(start) && !task.getDueDate().isAfter(end))
                .sorted(Comparator.comparing(Task::getDueDate))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Task> findByTitleContaining(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return tasks.values().stream()
                .filter(task -> task.getTitle().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Task> searchTasks(String query) {
        String lowerQuery = query.toLowerCase();
        return tasks.values().stream()
                .filter(task -> 
                    task.getTitle().toLowerCase().contains(lowerQuery) ||
                    task.getDescription().toLowerCase().contains(lowerQuery) ||
                    task.getTags().stream().anyMatch(tag -> tag.contains(lowerQuery))
                )
                .collect(Collectors.toList());
    }
}