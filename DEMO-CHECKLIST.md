# Live Demo Checklist - Day 1

## Pre-Class Setup (5 minutes before)
- [ ] Open IntelliJ IDEA
- [ ] Open browser to https://start.spring.io
- [ ] Open terminal for curl commands
- [ ] Have GitHub ready (logged in)
- [ ] Open AI tool (ChatGPT/Claude/Copilot)
- [ ] Clear desktop of distractions

## Demo Flow (10-15 minutes)

### 1. Spring Initializr (2 min)
```
Project: Gradle - Groovy (or Kotlin)
Language: Java
Spring Boot: 3.5.5
Group: edu.trincoll.demo
Artifact: task-tracker
Name: task-tracker
Package name: edu.trincoll.demo
Packaging: Jar
Java: 21

Dependencies:
- Spring Web
- Spring Boot DevTools
- Validation

GENERATE → Download → Open in IntelliJ
```

### 2. Create Task Record (2 min)
```java
package edu.trincoll.demo;

import java.time.LocalDateTime;

public record Task(
    Long id,
    String title,
    String description,
    boolean completed,
    LocalDateTime createdAt
) {
    public Task {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title required");
        }
    }
}
```

### 3. AI Prompt for Controller (3 min)
**Show this prompt to students:**
```
"Create a Spring Boot REST controller for Task with:
- GET all tasks
- GET task by id (return 404 if not found)
- POST create task
- PUT update task
- DELETE task
Use an in-memory Map<Long, Task> for storage."
```

**AI will generate something, but point out issues:**
- "Look, AI forgot @RequestBody"
- "No @Valid annotation"
- "ID generation might be wrong"
- "What about duplicate checking?"

### 4. Fix the Controller (3 min)
```java
package edu.trincoll.demo;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final Map<Long, Task> tasks = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();
    
    @GetMapping
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        Task task = tasks.get(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }
    
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody @Valid TaskRequest request) {
        // Check for duplicates
        boolean duplicate = tasks.values().stream()
            .anyMatch(t -> t.title().equalsIgnoreCase(request.title()));
        if (duplicate) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        
        Long id = idGenerator.incrementAndGet();
        Task task = new Task(id, request.title(), request.description(), 
                            false, LocalDateTime.now());
        tasks.put(id, task);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (!tasks.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        tasks.remove(id);
        return ResponseEntity.noContent().build();
    }
}

record TaskRequest(
    @NotBlank(message = "Title is required") String title,
    String description
) {}
```

### 5. Test with curl (2 min)
```bash
# Start the app
./gradlew bootRun

# In another terminal:
# Create a task
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Learn Spring Boot","description":"Build REST APIs"}'

# Get all tasks
curl http://localhost:8080/api/tasks

# Get specific task
curl http://localhost:8080/api/tasks/1

# Try to create duplicate (should get 409)
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Learn Spring Boot","description":"Different description"}'
```

### 6. Write One Test (2 min)
```java
package edu.trincoll.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void shouldCreateTask() throws Exception {
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Test Task\",\"description\":\"Test\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }
}
```

Run test: `./gradlew test`

### 7. Push to GitHub (1 min)
```bash
git init
git add .
git commit -m "Initial task tracker API"
# Create repo on GitHub (show them how)
git remote add origin https://github.com/kousen/class-demo-task-tracker.git
git push -u origin main
```

## Key Teaching Points

### What Went Right ✅
- Spring Boot started immediately
- Basic CRUD works
- AI saved typing time
- Test verifies behavior

### What AI Missed ❌
- Forgot @RequestBody initially
- No validation annotations
- Didn't handle duplicates
- Missing proper error responses

### The Lesson 💡
**"AI is your assistant, not your architect. You must understand every line."**

## Transition to Assignment
"Now you've seen me build a Task tracker. Your assignment is to take the starter code I've prepared - which has 15 comprehensive tests - and make it work for YOUR domain: Bookmarks, Quotes, Habits, Recipes, or Movies."

## If Things Go Wrong

### Spring won't start
- Check Java version: `java -version` (needs 21)
- Try: `./gradlew clean build`

### Port already in use
- Change port in application.properties: `server.port=8081`

### AI generates bad code
- Perfect! Show them why it's wrong
- "This is why we test everything"

### Can't push to GitHub
- Make sure logged in
- Create repo through UI if CLI fails

## Time Management
- 2 min: Spring Initializr
- 5 min: Code the API (with AI help)
- 3 min: Test with curl
- 2 min: Write one test
- 2 min: Push to GitHub
- 1 min: Explain assignment connection

**Total: ~15 minutes**

## Remember
- Go slow enough for them to follow
- Narrate what you're thinking
- Intentionally let AI make mistakes
- Show excitement about what's possible
- End with: "You'll build something better!"