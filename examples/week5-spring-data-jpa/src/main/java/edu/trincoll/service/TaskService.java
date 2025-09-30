package edu.trincoll.service;

import edu.trincoll.model.Task;
import edu.trincoll.model.User;
import edu.trincoll.repository.TaskRepository;
import edu.trincoll.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }
        return taskRepository.save(task);
    }

    public Task createTaskForUser(Long userId, Task task) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        task.setUser(user);
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Task> getTasksByUsername(String username) {
        return taskRepository.findTasksByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<Task> getCompletedTasks() {
        return taskRepository.findByCompleted(true);
    }

    @Transactional(readOnly = true)
    public List<Task> getIncompleteTasks() {
        return taskRepository.findByCompleted(false);
    }

    @Transactional(readOnly = true)
    public List<Task> getIncompleteTasksByUserId(Long userId) {
        return taskRepository.findIncompleteTasksByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Task> searchTasks(String keyword) {
        return taskRepository.searchTasks(keyword);
    }

    @Transactional(readOnly = true)
    public List<Task> getRecentTasks(int days) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        return taskRepository.findByCreatedAtAfter(cutoffDate);
    }

    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setCompleted(updatedTask.isCompleted());
                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + id));
    }

    public Task completeTask(Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setCompleted(true);
                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + id));
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long countCompletedTasks() {
        return taskRepository.countByCompleted(true);
    }

    @Transactional(readOnly = true)
    public long countTasksByUserId(Long userId) {
        return taskRepository.countByUserId(userId);
    }
}