package edu.trincoll.interfaces;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends Repository<Task, Long> {
    
    List<Task> findByStatus(Task.TaskStatus status);
    
    List<Task> findByPriority(Task.TaskPriority priority);
    
    List<Task> findByTag(String tag);
    
    List<Task> findOverdueTasks();
    
    List<Task> findByDueDateBetween(LocalDateTime start, LocalDateTime end);
    
    List<Task> findByTitleContaining(String keyword);
    
    List<Task> searchTasks(String query);
}