package edu.trincoll.repository;

import edu.trincoll.model.Task;
import edu.trincoll.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("john_doe", "john@example.com");
    }

    @Test
    void shouldSaveAndFindUser() {
        User saved = userRepository.save(testUser);

        assertThat(saved.getId()).isNotNull();
        assertThat(userRepository.findById(saved.getId()))
                .isPresent()
                .get()
                .extracting(User::getUsername, User::getEmail)
                .containsExactly("john_doe", "john@example.com");
    }

    @Test
    void shouldFindUserByUsername() {
        userRepository.save(testUser);

        Optional<User> found = userRepository.findByUsername("john_doe");

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("john@example.com");
    }

    @Test
    void shouldReturnEmptyWhenUsernameNotFound() {
        Optional<User> found = userRepository.findByUsername("nonexistent");

        assertThat(found).isEmpty();
    }

    @Test
    void shouldFindUserByEmail() {
        userRepository.save(testUser);

        Optional<User> found = userRepository.findByEmail("john@example.com");

        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("john_doe");
    }

    @Test
    void shouldCheckIfUsernameExists() {
        userRepository.save(testUser);

        assertThat(userRepository.existsByUsername("john_doe")).isTrue();
        assertThat(userRepository.existsByUsername("nonexistent")).isFalse();
    }

    @Test
    void shouldSaveUserWithTasks() {
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");

        testUser.addTask(task1);
        testUser.addTask(task2);

        User saved = userRepository.save(testUser);

        assertThat(saved.getTasks()).hasSize(2);
        assertThat(saved.getTasks())
                .extracting(Task::getTitle)
                .containsExactly("Task 1", "Task 2");
    }

    @Test
    void shouldCascadeDeleteTasks() {
        Task task = new Task("Task", "Description");
        testUser.addTask(task);

        User saved = userRepository.save(testUser);
        Long userId = saved.getId();

        userRepository.deleteById(userId);

        assertThat(userRepository.findById(userId)).isEmpty();
    }

    @Test
    void shouldFindUserWithTasksUsingJoinFetch() {
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");
        testUser.addTask(task1);
        testUser.addTask(task2);

        User saved = userRepository.save(testUser);

        Optional<User> found = userRepository.findByIdWithTasks(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getTasks()).hasSize(2);
    }

    @Test
    void shouldFindUserByUsernameWithTasks() {
        Task task = new Task("Task", "Description");
        testUser.addTask(task);

        userRepository.save(testUser);

        Optional<User> found = userRepository.findByUsernameWithTasks("john_doe");

        assertThat(found).isPresent();
        assertThat(found.get().getTasks()).hasSize(1);
    }

    @Test
    void shouldMaintainBidirectionalRelationship() {
        Task task = new Task("Task", "Description");
        testUser.addTask(task);

        User saved = userRepository.save(testUser);

        assertThat(saved.getTasks().get(0).getUser()).isEqualTo(saved);
    }
}