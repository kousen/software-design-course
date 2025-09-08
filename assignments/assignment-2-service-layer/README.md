# Assignment 2: Service Layer Architecture

**Due:** Thursday, September 18 at 11:59 PM  
**Points:** 100  
**Submission:** Via GitHub (one per team)

## Overview

Building on your Assignment 1 domain, you'll now implement a proper service layer architecture using inheritance, interfaces, and collections. This assignment tests your understanding of OOP principles in a Spring Boot context.

## Learning Objectives

- Implement service layer with inheritance hierarchy
- Design and use repository interfaces
- Apply collections effectively for data management
- Achieve 80% test coverage
- Practice SOLID principles (especially SRP and DIP)

## Architecture Requirements

Your application must have THREE distinct layers:

```
┌─────────────────────────┐
│   Controller Layer      │  (HTTP endpoints)
├─────────────────────────┤
│   Service Layer         │  (Business logic)
├─────────────────────────┤
│   Repository Layer      │  (Data access)
└─────────────────────────┘
```

## Part 1: Repository Layer (30 points)

### Create Generic Repository Interface
```java
public interface Repository<T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
    boolean existsById(ID id);
    long count();
}
```

### Create Domain-Specific Repository
Extend the generic repository with domain-specific methods:
```java
public interface YourDomainRepository extends Repository<YourDomain, Long> {
    List<YourDomain> findByStatus(Status status);
    List<YourDomain> findByCategory(String category);
    // Add 3+ custom query methods
}
```

### Implement with Collections
```java
@Repository
public class InMemoryYourDomainRepository implements YourDomainRepository {
    private final Map<Long, YourDomain> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    // Implement all methods using collections
}
```

## Part 2: Service Layer Hierarchy (40 points)

### Abstract Base Service
```java
public abstract class BaseService<T, ID> {
    protected abstract Repository<T, ID> getRepository();
    
    public T save(T entity) {
        validateEntity(entity);
        return getRepository().save(entity);
    }
    
    public abstract void validateEntity(T entity);
    
    // Common CRUD operations
}
```

### Concrete Service Implementation
```java
@Service
public class YourDomainService extends BaseService<YourDomain, Long> {
    private final YourDomainRepository repository;
    
    @Override
    protected Repository<YourDomain, Long> getRepository() {
        return repository;
    }
    
    @Override
    public void validateEntity(YourDomain entity) {
        // Domain-specific validation
    }
    
    // Business logic methods
    public Map<String, List<YourDomain>> groupByCategory() {
        // Use streams and collectors
    }
    
    public Set<String> getAllUniqueTags() {
        // Use Set operations
    }
}
```

## Part 3: Collections Usage (20 points)

Demonstrate mastery of Java Collections:

1. **List** - Maintain ordered items, support pagination
2. **Set** - Track unique values (tags, categories)
3. **Map** - Cache, group items, count occurrences
4. **Queue** - Process items in order (optional, bonus points)

Required collection operations:
- Stream filtering and mapping
- Grouping with Collectors
- Set operations (union, intersection)
- Defensive copying for getters

## Part 4: Testing Requirements (10 points)

### Minimum 80% Code Coverage
```bash
./gradlew test
./gradlew jacocoTestReport
# Check build/reports/jacoco/test/html/index.html
```

### Test Categories Required
- Repository layer tests (all CRUD operations)
- Service layer tests (business logic)
- Integration tests (controller → service → repository)
- Edge cases (null, empty, duplicates)

## Example Domains from Assignment 1

### BookmarkManager Extensions
- Group bookmarks by domain
- Find most frequent tags
- Track visit counts with Map

### QuoteKeeper Extensions
- Group quotes by author
- Find quotes by multiple tags (Set intersection)
- Random quote selection

### HabitTracker Extensions
- Group habits by frequency
- Calculate streak statistics
- Priority queue for habits due today

## Grading Rubric

| Component | Points | Requirements |
|-----------|--------|--------------|
| Repository Pattern | 30 | Generic interface, domain-specific methods, collection-based implementation |
| Service Hierarchy | 40 | Abstract base service, concrete implementation, proper inheritance |
| Collections Usage | 20 | Effective use of List, Set, Map with streams |
| Testing | 10 | 80% coverage, comprehensive test cases |

## Starter Code Structure

```
assignment-2-service-layer/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── edu/trincoll/
│   │           ├── model/
│   │           │   └── YourDomain.java
│   │           ├── repository/
│   │           │   ├── Repository.java
│   │           │   ├── YourDomainRepository.java
│   │           │   └── InMemoryYourDomainRepository.java
│   │           ├── service/
│   │           │   ├── BaseService.java
│   │           │   └── YourDomainService.java
│   │           └── controller/
│   │               └── YourDomainController.java
│   └── test/
│       └── java/
│           └── edu/trincoll/
│               ├── repository/
│               │   └── YourDomainRepositoryTest.java
│               ├── service/
│               │   └── YourDomainServiceTest.java
│               └── integration/
│                   └── YourDomainIntegrationTest.java
```

## Getting Started

### 1. Setup
```bash
# Clone the starter repository
git clone [starter-repo-url]
cd assignment-2-service-layer

# Run initial tests (will fail)
./gradlew test
```

### 2. Implementation Order
1. Start with the model/entity class
2. Implement Repository interface and InMemoryRepository
3. Create BaseService abstract class
4. Implement concrete service with business logic
5. Update controller to use service layer
6. Write comprehensive tests

### 3. Testing Your Implementation
```bash
# Run all tests
./gradlew test

# Generate coverage report
./gradlew jacocoTestReport

# Start the application
./gradlew bootRun
```

## AI Collaboration Requirements

Document your AI usage at the top of `YourDomainService.java`:

```java
/**
 * AI Collaboration Summary:
 * Tool: [ChatGPT/Claude/Copilot/Gemini]
 * 
 * Helpful Prompts:
 * 1. [Prompt that helped with collections]
 * 2. [Prompt that helped with inheritance]
 * 
 * AI Mistakes Fixed:
 * 1. [What went wrong and how you fixed it]
 * 
 * Learning Insights:
 * [What you learned about OOP from this assignment]
 * 
 * Team: [Member names]
 */
```

## Common Pitfalls to Avoid

❌ **DON'T:**
- Put business logic in the controller
- Access repository directly from controller
- Return repository's internal collections without defensive copying
- Forget to validate inputs in service layer
- Use raw types with collections

✅ **DO:**
- Keep layers separate and focused
- Use dependency injection via constructors
- Return immutable or defensive copies
- Validate in service, not controller
- Use generics for type safety

## Submission Requirements

1. Push your completed code to GitHub
2. Ensure all tests pass with `./gradlew test`
3. Verify 80% coverage with JaCoCo report
4. Submit repository URL to Moodle
5. Each team member must have commits

## Tips for Success

1. **Start Early** - This is more complex than Assignment 1
2. **Pair Program** - Work together on design decisions
3. **Test First** - Write tests before implementation
4. **Use AI Wisely** - Verify generated code with tests
5. **Ask Questions** - Office hours Wed 1:30-3:00 PM

## Bonus Challenges (Optional, +10 points)

- Implement caching with time-based expiration
- Add pagination support to repository
- Create a second service that depends on the first
- Implement the Observer pattern for change notifications
- Add comprehensive JavaDoc documentation

---

**Remember:** This assignment builds the foundation for the rest of the semester. A well-designed service layer will make future assignments much easier!