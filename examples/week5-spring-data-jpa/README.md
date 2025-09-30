# Week 5: Spring Data JPA Example

A comprehensive example demonstrating Spring Data JPA with entity relationships, custom query methods, and repository pattern implementation.

## Overview

This project demonstrates:
- **JPA Entity classes** with proper annotations
- **Entity relationships** (OneToMany, ManyToOne)
- **Spring Data repositories** with custom query methods
- **Service layer** using repositories
- **REST controllers** exposing CRUD endpoints
- **@DataJpaTest** comprehensive test suite
- **H2 in-memory database** for development and testing

## Domain Model

### Entities

#### User
- `id` (Long, auto-generated primary key)
- `username` (String, unique, required)
- `email` (String, required)
- `tasks` (List<Task>, one-to-many relationship)

#### Task
- `id` (Long, auto-generated primary key)
- `title` (String, required, max 100 chars)
- `description` (String, TEXT type)
- `completed` (boolean, default false)
- `createdAt` (LocalDateTime, auto-set)
- `user` (User, many-to-one relationship)

### Relationship

- One User can have many Tasks
- Each Task belongs to one User
- Bidirectional relationship maintained with helper methods
- Cascade operations configured for proper lifecycle management

## Project Structure

```
src/
├── main/
│   ├── java/edu/trincoll/
│   │   ├── SpringDataJpaApplication.java   # Main Spring Boot application
│   │   ├── model/
│   │   │   ├── User.java                   # User entity
│   │   │   └── Task.java                   # Task entity
│   │   ├── repository/
│   │   │   ├── UserRepository.java         # User data access
│   │   │   └── TaskRepository.java         # Task data access
│   │   ├── service/
│   │   │   ├── UserService.java            # User business logic
│   │   │   └── TaskService.java            # Task business logic
│   │   └── controller/
│   │       ├── UserController.java         # User REST API
│   │       └── TaskController.java         # Task REST API
│   └── resources/
│       └── application.yml                 # H2 configuration
└── test/
    └── java/edu/trincoll/repository/
        ├── UserRepositoryTest.java         # User repository tests
        └── TaskRepositoryTest.java         # Task repository tests
```

## Key Features Demonstrated

### Entity Annotations
```java
@Entity
@Table(name = "users")
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(nullable = false, unique = true)
@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id")
@PrePersist
```

### Spring Data Query Methods

#### Method Naming Convention
- `findByUsername(String username)`
- `findByCompleted(boolean completed)`
- `findByTitleContaining(String keyword)`
- `findByUserIdAndCompleted(Long userId, boolean completed)`
- `findByCreatedAtAfter(LocalDateTime date)`
- `countByCompleted(boolean completed)`
- `existsByUsername(String username)`

#### Custom JPQL Queries
```java
@Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
List<Task> searchTasks(@Param("keyword") String keyword);

@Query("SELECT u FROM User u LEFT JOIN FETCH u.tasks WHERE u.id = :id")
Optional<User> findByIdWithTasks(@Param("id") Long id);
```

### Repository Pattern

All repositories extend `JpaRepository<T, ID>` providing:
- `save(T)`, `saveAll(Iterable<T>)`
- `findById(ID)`, `findAll()`
- `deleteById(ID)`, `delete(T)`, `deleteAll()`
- `count()`, `existsById(ID)`
- Pagination and sorting support

### Avoiding N+1 Problem

**Problem:**
```java
List<User> users = userRepository.findAll();
users.forEach(user -> user.getTasks().size()); // N additional queries!
```

**Solution:**
```java
@Query("SELECT u FROM User u LEFT JOIN FETCH u.tasks WHERE u.id = :id")
Optional<User> findByIdWithTasks(@Param("id") Long id);
```

### Testing with @DataJpaTest

- **Slice testing** - only repository layer
- **In-memory H2 database** - fast and isolated
- **Transactional** - automatic rollback after each test
- **TestEntityManager** - for test data setup

Example test structure:
```java
@DataJpaTest
class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        // Set up test data
    }

    @Test
    void shouldFindTasksByUserId() {
        // Arrange, Act, Assert
    }
}
```

## API Endpoints

### User Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/users` | Create new user |
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| GET | `/api/users/{id}/with-tasks` | Get user with tasks (JOIN FETCH) |
| GET | `/api/users/username/{username}` | Get user by username |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |

### Task Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/tasks` | Create new task |
| POST | `/api/tasks/user/{userId}` | Create task for user |
| GET | `/api/tasks` | Get all tasks |
| GET | `/api/tasks/{id}` | Get task by ID |
| GET | `/api/tasks/user/{userId}` | Get tasks by user ID |
| GET | `/api/tasks/username/{username}` | Get tasks by username |
| GET | `/api/tasks/completed` | Get completed tasks |
| GET | `/api/tasks/incomplete` | Get incomplete tasks |
| GET | `/api/tasks/user/{userId}/incomplete` | Get incomplete tasks by user |
| GET | `/api/tasks/search?keyword={keyword}` | Search tasks |
| GET | `/api/tasks/recent?days={days}` | Get recent tasks |
| PUT | `/api/tasks/{id}` | Update task |
| PATCH | `/api/tasks/{id}/complete` | Mark task as complete |
| DELETE | `/api/tasks/{id}` | Delete task |
| GET | `/api/tasks/stats/completed-count` | Count completed tasks |
| GET | `/api/tasks/stats/user/{userId}/count` | Count user's tasks |

## Running the Application

### Build and Run
```bash
./gradlew bootRun
```

The application starts on `http://localhost:8080`

### Run Tests
```bash
./gradlew test
```

### Access H2 Console

While the application is running, access the H2 console at:
```
http://localhost:8080/h2-console
```

**Connection Settings:**
- JDBC URL: `jdbc:h2:mem:taskdb`
- Username: `sa`
- Password: (empty)

## Example Usage

### Create a User
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"username": "john_doe", "email": "john@example.com"}'
```

### Create a Task for User
```bash
curl -X POST http://localhost:8080/api/tasks/user/1 \
  -H "Content-Type: application/json" \
  -d '{"title": "Buy groceries", "description": "Milk, bread, eggs"}'
```

### Get All Tasks for User
```bash
curl http://localhost:8080/api/tasks/user/1
```

### Search Tasks
```bash
curl http://localhost:8080/api/tasks/search?keyword=groceries
```

### Mark Task as Complete
```bash
curl -X PATCH http://localhost:8080/api/tasks/1/complete
```

## Configuration

### application.yml

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:taskdb
    driver-class-name: org.h2.Driver
    username: sa
    password:

  h2:
    console:
      enabled: true
      path: /h2-console

  jpa:
    hibernate:
      ddl-auto: create-drop        # Recreate schema on startup
    show-sql: true                 # Log SQL statements
    properties:
      hibernate:
        format_sql: true           # Pretty-print SQL
```

## Key Concepts Illustrated

### 1. Entity Mapping
- `@Entity` marks class as JPA entity
- `@Table` specifies table name
- `@Id` designates primary key
- `@GeneratedValue` auto-generates ID
- `@Column` defines column constraints

### 2. Relationships
- `@OneToMany` one entity has many
- `@ManyToOne` many entities to one
- `mappedBy` indicates owning side
- `cascade` propagates operations
- `orphanRemoval` deletes orphaned entities
- Helper methods maintain bidirectional consistency

### 3. Query Methods
- Method name → SQL query
- Keywords: `findBy`, `And`, `Or`, `Like`, `Between`, etc.
- `@Query` for custom JPQL
- `@Param` for named parameters

### 4. Repository Pattern
- Interface-based data access
- No implementation needed
- Spring generates at runtime
- Type-safe and database-agnostic

### 5. Testing
- `@DataJpaTest` for repository tests
- In-memory database (H2)
- Transactional rollback
- Fast and isolated

## Common Pitfalls Avoided

1. **N+1 Query Problem** - Use JOIN FETCH
2. **LazyInitializationException** - Use `@Transactional` or JOIN FETCH
3. **Circular JSON References** - Use DTOs or `@JsonIgnore`
4. **Missing Bidirectional Updates** - Use helper methods
5. **Improper equals/hashCode** - Use only ID for entity equality

## Learning Outcomes

After studying this example, you should understand:
- How to create JPA entities with annotations
- How to model entity relationships
- How to use Spring Data repositories
- How to write custom query methods
- How to avoid common JPA pitfalls
- How to test repository layer with @DataJpaTest
- How to configure H2 for development
- How to build a complete REST API with persistence

## Related Topics

- **Week 3**: Spring Boot REST APIs
- **Week 4**: Testing Fundamentals & TDD
- **Week 6**: SOLID Principles (especially DIP)
- **Week 11**: Security Implementation

## Further Reading

- [Spring Data JPA Reference](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [JPA 3.0 Specification](https://jakarta.ee/specifications/persistence/3.0/)
- [H2 Database Documentation](https://h2database.com/html/main.html)
- [Hibernate ORM User Guide](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html)