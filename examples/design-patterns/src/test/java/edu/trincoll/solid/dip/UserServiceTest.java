package edu.trincoll.solid.dip;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class UserServiceTest {
    
    @Test
    void userServiceWorksWithDatabaseRepository() {
        UserRepository repository = new DatabaseUserRepository();
        UserService userService = new UserService(repository);
        
        assertThat(userService).isNotNull();
        
        userService.createUser("John Doe", "john@example.com");
        List<User> users = userService.getAllUsers();
        
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getName()).isEqualTo("John Doe");
    }
    
    @Test
    void userServiceWorksWithFileRepository() {
        UserRepository repository = new FileUserRepository();
        UserService userService = new UserService(repository);
        
        assertThat(userService).isNotNull();
        
        userService.createUser("Jane Smith", "jane@example.com");
        List<User> users = userService.getAllUsers();
        
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getName()).isEqualTo("Jane Smith");
    }
    
    @Test
    void userServiceDependsOnAbstraction() {
        UserRepository[] repositories = {
            new DatabaseUserRepository(),
            new FileUserRepository()
        };
        
        for (UserRepository repository : repositories) {
            UserService userService = new UserService(repository);
            assertThat(userService).isNotNull();
            
            userService.createUser("Test User", "test@example.com");
            List<User> users = userService.getAllUsers();
            assertThat(users).hasSize(1);
        }
    }
    
    @Test
    void databaseUserRepositoryImplementsUserRepository() {
        DatabaseUserRepository repository = new DatabaseUserRepository();
        
        assertThat(repository).isInstanceOf(UserRepository.class);
        
        User user = new User(1L, "Alice", "alice@example.com");
        repository.save(user);
        
        User retrieved = repository.findById(1L);
        assertThat(retrieved).isNotNull();
        assertThat(retrieved.getName()).isEqualTo("Alice");
    }
    
    @Test
    void fileUserRepositoryImplementsUserRepository() {
        FileUserRepository repository = new FileUserRepository();
        
        assertThat(repository).isInstanceOf(UserRepository.class);
        
        User user = new User(1L, "Bob", "bob@example.com");
        repository.save(user);
        
        User retrieved = repository.findById(1L);
        assertThat(retrieved).isNotNull();
        assertThat(retrieved.getName()).isEqualTo("Bob");
    }
    
    @Test
    void userServiceCanCreateAndRetrieveUsers() {
        UserRepository repository = new DatabaseUserRepository();
        UserService userService = new UserService(repository);
        
        userService.createUser("Charlie Brown", "charlie@example.com");
        List<User> users = userService.getAllUsers();
        
        assertThat(users).hasSize(1);
        User user = users.get(0);
        
        assertAll(
            () -> assertThat(user.getName()).isEqualTo("Charlie Brown"),
            () -> assertThat(user.getEmail()).isEqualTo("charlie@example.com"),
            () -> assertThat(user.getId()).isNotNull()
        );
    }
    
    @Test
    void userServiceCanDeleteUsers() {
        UserRepository repository = new DatabaseUserRepository();
        UserService userService = new UserService(repository);
        
        userService.createUser("David Wilson", "david@example.com");
        List<User> users = userService.getAllUsers();
        assertThat(users).hasSize(1);
        
        Long userId = users.get(0).getId();
        userService.deleteUser(userId);
        
        User deletedUser = userService.getUser(userId);
        assertThat(deletedUser).isNull();
    }
}