package edu.trincoll.repository;

import edu.trincoll.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Query methods using naming conventions
    List<Task> findByCompleted(boolean completed);

    List<Task> findByUserId(Long userId);

    List<Task> findByTitleContaining(String keyword);

    List<Task> findByDescriptionContaining(String keyword);

    // Find by user ID and completion status
    List<Task> findByUserIdAndCompleted(Long userId, boolean completed);

    // Find tasks ordered by creation date
    List<Task> findByUserIdOrderByCreatedAtDesc(Long userId);

    // Count methods
    long countByCompleted(boolean completed);

    long countByUserId(Long userId);

    // Find tasks created after a certain date
    List<Task> findByCreatedAtAfter(LocalDateTime date);

    // Custom JPQL query - search in title or description
    @Query("SELECT t FROM Task t WHERE " +
           "LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Task> searchTasks(@Param("keyword") String keyword);

    // Custom JPQL query - find tasks by username
    @Query("SELECT t FROM Task t JOIN t.user u WHERE u.username = :username")
    List<Task> findTasksByUsername(@Param("username") String username);

    // Find incomplete tasks for a user
    @Query("SELECT t FROM Task t WHERE t.user.id = :userId AND t.completed = false ORDER BY t.createdAt DESC")
    List<Task> findIncompleteTasksByUserId(@Param("userId") Long userId);
}