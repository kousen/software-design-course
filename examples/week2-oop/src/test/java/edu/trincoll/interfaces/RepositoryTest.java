package edu.trincoll.interfaces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class RepositoryTest {
    
    @Nested
    @DisplayName("Basic Repository Operations")
    class BasicRepositoryTests {
        private TaskRepository repository;
        private Task task1;
        private Task task2;
        
        @BeforeEach
        void setUp() {
            repository = new InMemoryTaskRepository();
            task1 = new Task("Task 1", "Description 1");
            task2 = new Task("Task 2", "Description 2");
        }
        
        @Test
        @DisplayName("Should save and retrieve task by ID")
        void testSaveAndFindById() {
            Task saved = repository.save(task1);
            
            assertThat(saved.getId()).isNotNull();
            
            Optional<Task> found = repository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getTitle()).isEqualTo("Task 1");
        }
        
        @Test
        @DisplayName("Should find all tasks")
        void testFindAll() {
            repository.save(task1);
            repository.save(task2);
            
            List<Task> all = repository.findAll();
            assertThat(all).hasSize(2);
            assertThat(all).extracting(Task::getTitle)
                    .containsExactlyInAnyOrder("Task 1", "Task 2");
        }
        
        @Test
        @DisplayName("Should check if task exists")
        void testExistsById() {
            Task saved = repository.save(task1);
            
            assertThat(repository.existsById(saved.getId())).isTrue();
            assertThat(repository.existsById(999L)).isFalse();
        }
        
        @Test
        @DisplayName("Should delete task by ID")
        void testDeleteById() {
            Task saved = repository.save(task1);
            Long id = saved.getId();
            
            repository.deleteById(id);
            
            assertThat(repository.existsById(id)).isFalse();
            assertThat(repository.count()).isZero();
        }
        
        @Test
        @DisplayName("Should count tasks")
        void testCount() {
            assertThat(repository.count()).isZero();
            
            repository.save(task1);
            repository.save(task2);
            
            assertThat(repository.count()).isEqualTo(2);
        }
        
        @Test
        @DisplayName("Should save multiple tasks")
        void testSaveAll() {
            List<Task> tasks = List.of(task1, task2);
            List<Task> saved = repository.saveAll(tasks);
            
            assertThat(saved).hasSize(2);
            assertThat(saved).allMatch(t -> t.getId() != null);
            assertThat(repository.count()).isEqualTo(2);
        }
    }
    
    @Nested
    @DisplayName("Task-Specific Repository Operations")
    class TaskSpecificTests {
        private TaskRepository repository;
        
        @BeforeEach
        void setUp() {
            repository = new InMemoryTaskRepository();
            
            Task todo = new Task("Todo Task", "A task to do");
            todo.setStatus(Task.TaskStatus.TODO);
            todo.setPriority(Task.TaskPriority.HIGH);
            todo.addTag("urgent");
            
            Task inProgress = new Task("In Progress Task", "Working on it");
            inProgress.setStatus(Task.TaskStatus.IN_PROGRESS);
            inProgress.setPriority(Task.TaskPriority.MEDIUM);
            inProgress.addTag("backend");
            
            Task done = new Task("Done Task", "Completed");
            done.setStatus(Task.TaskStatus.DONE);
            done.setPriority(Task.TaskPriority.LOW);
            done.addTag("frontend");
            
            repository.save(todo);
            repository.save(inProgress);
            repository.save(done);
        }
        
        @Test
        @DisplayName("Should find tasks by status")
        void testFindByStatus() {
            List<Task> todoTasks = repository.findByStatus(Task.TaskStatus.TODO);
            assertThat(todoTasks).hasSize(1);
            assertThat(todoTasks.getFirst().getTitle()).isEqualTo("Todo Task");
            
            List<Task> doneTasks = repository.findByStatus(Task.TaskStatus.DONE);
            assertThat(doneTasks).hasSize(1);
        }
        
        @Test
        @DisplayName("Should find tasks by priority")
        void testFindByPriority() {
            List<Task> highPriority = repository.findByPriority(Task.TaskPriority.HIGH);
            assertThat(highPriority).hasSize(1);
            assertThat(highPriority.getFirst().getTitle()).isEqualTo("Todo Task");
        }
        
        @Test
        @DisplayName("Should find tasks by tag")
        void testFindByTag() {
            List<Task> urgentTasks = repository.findByTag("urgent");
            assertThat(urgentTasks).hasSize(1);
            
            List<Task> backendTasks = repository.findByTag("backend");
            assertThat(backendTasks).hasSize(1);
        }
        
        @Test
        @DisplayName("Should find tasks by title containing keyword")
        void testFindByTitleContaining() {
            List<Task> tasksWithProgress = repository.findByTitleContaining("Progress");
            assertThat(tasksWithProgress).hasSize(1);
            assertThat(tasksWithProgress.getFirst().getTitle()).contains("Progress");
        }
        
        @Test
        @DisplayName("Should search tasks by query")
        void testSearchTasks() {
            List<Task> results = repository.searchTasks("task");
            assertThat(results).hasSize(3);
            
            results = repository.searchTasks("backend");
            assertThat(results).hasSize(1);
            
            results = repository.searchTasks("completed");
            assertThat(results).hasSize(1);
        }
    }
    
    @Nested
    @DisplayName("Overdue Tasks Tests")
    class OverdueTasksTests {
        private TaskRepository repository;
        
        @BeforeEach
        void setUp() {
            repository = new InMemoryTaskRepository();
            
            Task overdue = new Task("Overdue Task", "Should have been done");
            overdue.setDueDate(LocalDateTime.now().minusDays(1));
            overdue.setStatus(Task.TaskStatus.TODO);
            
            Task upcoming = new Task("Upcoming Task", "Due soon");
            upcoming.setDueDate(LocalDateTime.now().plusDays(1));
            upcoming.setStatus(Task.TaskStatus.TODO);
            
            Task completedOverdue = new Task("Completed Task", "Done on time");
            completedOverdue.setDueDate(LocalDateTime.now().minusDays(1));
            completedOverdue.setStatus(Task.TaskStatus.DONE);
            
            repository.save(overdue);
            repository.save(upcoming);
            repository.save(completedOverdue);
        }
        
        @Test
        @DisplayName("Should find overdue tasks")
        void testFindOverdueTasks() {
            List<Task> overdueTasks = repository.findOverdueTasks();
            
            assertThat(overdueTasks).hasSize(1);
            assertThat(overdueTasks.getFirst().getTitle()).isEqualTo("Overdue Task");
        }
        
        @Test
        @DisplayName("Should find tasks by due date range")
        void testFindByDueDateBetween() {
            LocalDateTime start = LocalDateTime.now().minusDays(2);
            LocalDateTime end = LocalDateTime.now().plusDays(2);
            
            List<Task> tasksInRange = repository.findByDueDateBetween(start, end);
            
            assertThat(tasksInRange).hasSize(3);
        }
    }
    
    @Nested
    @DisplayName("Interface Polymorphism Tests")
    class InterfacePolymorphismTests {
        
        @Test
        @DisplayName("Should work with generic repository interface")
        void testGenericRepository() {
            Repository<Task, Long> genericRepo = new InMemoryTaskRepository();
            
            Task task = new Task("Generic Task", "Testing polymorphism");
            Task saved = genericRepo.save(task);
            
            assertThat(saved.getId()).isNotNull();
            assertThat(genericRepo.count()).isEqualTo(1);
            
            Optional<Task> found = genericRepo.findById(saved.getId());
            assertThat(found).isPresent();
        }
        
        @Test
        @DisplayName("Should demonstrate interface segregation")
        void testInterfaceSegregation() {
            TaskRepository taskRepo = new InMemoryTaskRepository();
            
            Task task = new Task("Interface Test", "Testing ISP");
            task.addTag("test");
            task.setPriority(Task.TaskPriority.HIGH);
            taskRepo.save(task);
            
            List<Task> byTag = taskRepo.findByTag("test");
            List<Task> byPriority = taskRepo.findByPriority(Task.TaskPriority.HIGH);
            
            assertThat(byTag).hasSize(1);
            assertThat(byPriority).hasSize(1);
        }
    }
}