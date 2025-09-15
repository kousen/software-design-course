# Week 3: Spring Boot REST API Example

This module contains a complete Spring Boot REST API implementation that corresponds to the code examples shown in the Week 3 slides.

## Overview

This example demonstrates:
- Spring Boot REST controllers with full CRUD operations
- Dependency injection using constructor injection (best practice)
- Service layer pattern
- Repository pattern for data access
- DTOs (Data Transfer Objects) for API contracts
- Request validation using Jakarta Bean Validation
- Global exception handling
- Comprehensive testing with MockMvc

## Project Structure

```
src/main/java/edu/trincoll/
├── SpringBootRestApiApplication.java    # Main Spring Boot application
├── controller/
│   └── TaskController.java             # REST controller with CRUD endpoints
├── service/
│   └── TaskService.java                # Business logic layer
├── repository/
│   └── TaskRepository.java             # Data access layer (in-memory)
├── model/
│   └── Task.java                       # Domain model
├── dto/
│   ├── TaskDTO.java                    # Response DTO
│   ├── CreateTaskRequest.java          # Request DTO for creation
│   └── UpdateTaskRequest.java          # Request DTO for updates
└── exception/
    ├── TaskNotFoundException.java      # Custom exception
    ├── ErrorResponse.java              # Error response format
    └── GlobalExceptionHandler.java     # Global exception handling
```

## Running the Application

```bash
# From the root directory
./gradlew :examples:week3-spring-boot:bootRun

# The API will be available at http://localhost:8080
```

## API Endpoints

### Tasks CRUD Operations

- `GET /api/tasks` - Get all tasks (with pagination)
- `GET /api/tasks/{id}` - Get a specific task
- `POST /api/tasks` - Create a new task
- `PUT /api/tasks/{id}` - Update a task (full update)
- `PATCH /api/tasks/{id}` - Partial update
- `DELETE /api/tasks/{id}` - Delete a task

### Additional Endpoints

- `GET /api/tasks/completed` - Get completed tasks
- `GET /api/tasks/pending` - Get pending tasks
- `GET /api/tasks/projects/{projectId}/tasks` - Get tasks by project
- `POST /api/tasks/{id}/complete` - Mark task as complete
- `GET /api/tasks/count` - Get total task count

### Query Parameters

- `GET /api/tasks?status=completed` - Filter by status
- `GET /api/tasks?page=0&size=10` - Pagination

## Request/Response Examples

### Create Task
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Learn Spring Boot",
    "description": "Master REST APIs",
    "dueDate": "2025-12-31",
    "priority": 3,
    "assigneeEmail": "student@trincoll.edu"
  }'
```

### Update Task
```bash
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Updated Title",
    "completed": true
  }'
```

### Partial Update
```bash
curl -X PATCH http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{
    "priority": 5,
    "status": "in-progress"
  }'
```

## Key Spring Boot Concepts Demonstrated

### 1. Dependency Injection
```java
@RestController
public class TaskController {
    private final TaskService service;

    // Constructor injection (preferred)
    public TaskController(TaskService service) {
        this.service = service;
    }
}
```

### 2. REST Annotations
- `@RestController` - Combines @Controller and @ResponseBody
- `@RequestMapping` - Base path for all methods
- `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`, `@PatchMapping`
- `@PathVariable` - Extract values from URL path
- `@RequestParam` - Extract query parameters
- `@RequestBody` - Deserialize request body
- `@Valid` - Trigger validation

### 3. Service Layer Pattern
The service layer contains business logic and orchestrates between controller and repository layers.

### 4. DTOs vs Entities
- **Task** - Domain model with internal fields
- **TaskDTO** - What clients see (hides internal notes)
- **CreateTaskRequest** - Validated input for creation
- **UpdateTaskRequest** - Validated input for updates

### 5. Exception Handling
- Custom exceptions for domain-specific errors
- `@RestControllerAdvice` for global exception handling
- Consistent error response format

## Testing

```bash
# Run all tests
./gradlew :examples:week3-spring-boot:test

# Run with coverage report
./gradlew :examples:week3-spring-boot:test jacocoTestReport
```

### Test Types

1. **Unit Tests** (`TaskServiceTest`)
   - Tests service layer in isolation
   - Uses Mockito to mock repository

2. **Web Layer Tests** (`TaskControllerTest`)
   - Tests controller with `@WebMvcTest`
   - Mocks service layer
   - Validates HTTP semantics

3. **Integration Tests** (`TaskIntegrationTest`)
   - Full application context with `@SpringBootTest`
   - Tests complete request/response flow
   - No mocking - tests real interactions

## Common Patterns to Notice

1. **Constructor Injection**: Always use constructor injection with final fields
2. **Validation**: Use Jakarta Bean Validation annotations
3. **HTTP Status Codes**: Use appropriate status codes (201 for creation, 204 for deletion)
4. **Error Handling**: Never expose stack traces to clients
5. **DTOs**: Never expose entities directly in API responses
6. **Testing**: Test at multiple levels (unit, web layer, integration)

## Learning Objectives

After studying this example, you should understand:
- How to build REST APIs with Spring Boot
- The importance of layered architecture
- Dependency injection and inversion of control
- DTO pattern for API design
- Proper HTTP method usage and status codes
- Testing strategies for Spring Boot applications

## Comparison with Week 3 Slides

This implementation corresponds directly to the code examples shown in the Week 3 slides:
- Slides 183-196: Basic REST controller setup
- Slides 256-365: CRUD operations
- Slides 434-456: Validation
- Slides 460-486: DTOs
- Slides 558-630: Dependency injection
- Slides 631-704: Service and repository layers
- Slides 734-811: Testing approaches