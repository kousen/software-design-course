package edu.trincoll.repository;

import edu.trincoll.model.Task;
import edu.trincoll.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("john_doe", "john@example.com");
        entityManager.persist(testUser);
        entityManager.flush();
    }

    @Test
    void shouldSaveAndFindTask() {
        Task task = new Task("Test Task", "Test Description");
        task.setUser(testUser);

        Task saved = taskRepository.save(task);

        assertThat(saved.getId()).isNotNull();
        assertThat(taskRepository.findById(saved.getId()))
                .isPresent()
                .get()
                .extracting(Task::getTitle, Task::getDescription)
                .containsExactly("Test Task", "Test Description");
    }

    @Test
    void shouldFindTasksByCompleted() {
        Task completed = new Task("Completed", "Done");
        completed.setCompleted(true);
        completed.setUser(testUser);

        Task incomplete = new Task("Incomplete", "Not done");
        incomplete.setUser(testUser);

        taskRepository.saveAll(List.of(completed, incomplete));

        List<Task> completedTasks = taskRepository.findByCompleted(true);
        List<Task> incompleteTasks = taskRepository.findByCompleted(false);

        assertThat(completedTasks).hasSize(1);
        assertThat(completedTasks.get(0).getTitle()).isEqualTo("Completed");

        assertThat(incompleteTasks).hasSize(1);
        assertThat(incompleteTasks.get(0).getTitle()).isEqualTo("Incomplete");
    }

    @Test
    void shouldFindTasksByUserId() {
        User anotherUser = new User("jane_doe", "jane@example.com");
        entityManager.persist(anotherUser);

        Task task1 = new Task("Task 1", "Description 1");
        task1.setUser(testUser);

        Task task2 = new Task("Task 2", "Description 2");
        task2.setUser(testUser);

        Task task3 = new Task("Task 3", "Description 3");
        task3.setUser(anotherUser);

        taskRepository.saveAll(List.of(task1, task2, task3));

        List<Task> johnTasks = taskRepository.findByUserId(testUser.getId());

        assertThat(johnTasks).hasSize(2);
        assertThat(johnTasks)
                .extracting(Task::getTitle)
                .containsExactly("Task 1", "Task 2");
    }

    @Test
    void shouldFindTasksByTitleContaining() {
        Task task1 = new Task("Buy groceries", "Milk and bread");
        task1.setUser(testUser);

        Task task2 = new Task("Buy clothes", "New shirt");
        task2.setUser(testUser);

        Task task3 = new Task("Read book", "Finish chapter 5");
        task3.setUser(testUser);

        taskRepository.saveAll(List.of(task1, task2, task3));

        List<Task> buyTasks = taskRepository.findByTitleContaining("Buy");

        assertThat(buyTasks).hasSize(2);
        assertThat(buyTasks)
                .extracting(Task::getTitle)
                .containsExactly("Buy groceries", "Buy clothes");
    }

    @Test
    void shouldFindTasksByUserIdAndCompleted() {
        Task completed = new Task("Completed Task", "Done");
        completed.setCompleted(true);
        completed.setUser(testUser);

        Task incomplete = new Task("Incomplete Task", "Not done");
        incomplete.setUser(testUser);

        taskRepository.saveAll(List.of(completed, incomplete));

        List<Task> userCompletedTasks = taskRepository.findByUserIdAndCompleted(testUser.getId(), true);

        assertThat(userCompletedTasks).hasSize(1);
        assertThat(userCompletedTasks.get(0).getTitle()).isEqualTo("Completed Task");
    }

    @Test
    void shouldFindTasksOrderedByCreatedAt() {
        Task task1 = new Task("First Task", "Created first");
        task1.setUser(testUser);
        task1.setCreatedAt(LocalDateTime.now().minusHours(2));

        Task task2 = new Task("Second Task", "Created second");
        task2.setUser(testUser);
        task2.setCreatedAt(LocalDateTime.now().minusHours(1));

        Task task3 = new Task("Third Task", "Created third");
        task3.setUser(testUser);
        task3.setCreatedAt(LocalDateTime.now());

        taskRepository.saveAll(List.of(task1, task2, task3));

        List<Task> tasks = taskRepository.findByUserIdOrderByCreatedAtDesc(testUser.getId());

        assertThat(tasks).hasSize(3);
        assertThat(tasks)
                .extracting(Task::getTitle)
                .containsExactly("Third Task", "Second Task", "First Task");
    }

    @Test
    void shouldCountTasksByCompleted() {
        Task completed1 = new Task("Task 1", "Done");
        completed1.setCompleted(true);
        completed1.setUser(testUser);

        Task completed2 = new Task("Task 2", "Done");
        completed2.setCompleted(true);
        completed2.setUser(testUser);

        Task incomplete = new Task("Task 3", "Not done");
        incomplete.setUser(testUser);

        taskRepository.saveAll(List.of(completed1, completed2, incomplete));

        long completedCount = taskRepository.countByCompleted(true);
        long incompleteCount = taskRepository.countByCompleted(false);

        assertThat(completedCount).isEqualTo(2);
        assertThat(incompleteCount).isEqualTo(1);
    }

    @Test
    void shouldCountTasksByUserId() {
        User anotherUser = new User("jane_doe", "jane@example.com");
        entityManager.persist(anotherUser);

        Task task1 = new Task("Task 1", "Description");
        task1.setUser(testUser);

        Task task2 = new Task("Task 2", "Description");
        task2.setUser(testUser);

        Task task3 = new Task("Task 3", "Description");
        task3.setUser(anotherUser);

        taskRepository.saveAll(List.of(task1, task2, task3));

        long johnTaskCount = taskRepository.countByUserId(testUser.getId());
        long janeTaskCount = taskRepository.countByUserId(anotherUser.getId());

        assertThat(johnTaskCount).isEqualTo(2);
        assertThat(janeTaskCount).isEqualTo(1);
    }

    @Test
    void shouldFindTasksCreatedAfterDate() {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(1);

        Task oldTask = new Task("Old Task", "Created long ago");
        oldTask.setCreatedAt(LocalDateTime.now().minusDays(2));
        oldTask.setUser(testUser);

        Task recentTask = new Task("Recent Task", "Created recently");
        recentTask.setCreatedAt(LocalDateTime.now());
        recentTask.setUser(testUser);

        taskRepository.saveAll(List.of(oldTask, recentTask));

        List<Task> recentTasks = taskRepository.findByCreatedAtAfter(cutoffDate);

        assertThat(recentTasks).hasSize(1);
        assertThat(recentTasks.get(0).getTitle()).isEqualTo("Recent Task");
    }

    @Test
    void shouldSearchTasksByKeyword() {
        Task task1 = new Task("Buy groceries", "Need milk");
        task1.setUser(testUser);

        Task task2 = new Task("Read book", "Buy new book about programming");
        task2.setUser(testUser);

        Task task3 = new Task("Exercise", "Go to gym");
        task3.setUser(testUser);

        taskRepository.saveAll(List.of(task1, task2, task3));

        List<Task> searchResults = taskRepository.searchTasks("buy");

        assertThat(searchResults).hasSize(2);
        assertThat(searchResults)
                .extracting(Task::getTitle)
                .containsExactlyInAnyOrder("Buy groceries", "Read book");
    }

    @Test
    void shouldFindTasksByUsername() {
        Task task1 = new Task("Task 1", "Description");
        task1.setUser(testUser);

        Task task2 = new Task("Task 2", "Description");
        task2.setUser(testUser);

        taskRepository.saveAll(List.of(task1, task2));

        List<Task> tasks = taskRepository.findTasksByUsername("john_doe");

        assertThat(tasks).hasSize(2);
    }

    @Test
    void shouldFindIncompleteTasksByUserId() {
        Task completed = new Task("Completed", "Done");
        completed.setCompleted(true);
        completed.setUser(testUser);

        Task incomplete1 = new Task("Incomplete 1", "Not done");
        incomplete1.setUser(testUser);

        Task incomplete2 = new Task("Incomplete 2", "Not done");
        incomplete2.setUser(testUser);

        taskRepository.saveAll(List.of(completed, incomplete1, incomplete2));

        List<Task> incompleteTasks = taskRepository.findIncompleteTasksByUserId(testUser.getId());

        assertThat(incompleteTasks).hasSize(2);
        assertThat(incompleteTasks)
                .extracting(Task::getTitle)
                .containsExactlyInAnyOrder("Incomplete 1", "Incomplete 2");
    }
}