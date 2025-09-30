# Week 5: Spring Data JPA & AI Code Evaluation

## Overview

This week combines two critical aspects of modern software development:
1. **Data persistence** using Spring Data JPA
2. **AI-assisted development** with best practices for code evaluation

## Session 9: Spring Data JPA (September 30)

### Topics Covered

#### JPA Fundamentals
- Object-Relational Mapping (ORM) concepts
- Entity classes and annotations (@Entity, @Id, @GeneratedValue)
- Using Java records as entities

#### Entity Relationships
- @ManyToOne and @OneToMany relationships
- Bidirectional relationships with `mappedBy`
- Cascade operations and orphan removal
- Helper methods for relationship consistency

#### Spring Data Repositories
- JpaRepository interface and inherited methods
- Custom query methods using naming conventions
- JPQL queries with @Query
- Query method keywords (findBy, And, Or, Like, etc.)

#### H2 Database
- In-memory database configuration
- H2 console for development/debugging
- DDL auto options (create-drop, update, validate)

#### Testing with @DataJpaTest
- Slice testing for repository layer
- Transactional test isolation
- TestEntityManager usage
- Testing relationships and custom queries

#### Common Pitfalls
- N+1 query problem and solutions (JOIN FETCH, @EntityGraph)
- LazyInitializationException outside transactions
- Entity vs DTO patterns

### Learning Objectives

By the end of Session 9, students will be able to:
- Create JPA entities with proper annotations
- Define entity relationships (OneToMany, ManyToOne)
- Use Spring Data repositories for data access
- Write custom query methods using naming conventions
- Configure H2 in-memory database
- Write comprehensive @DataJpaTest tests
- Avoid common JPA pitfalls (N+1, lazy loading)

### Key Concepts

- **Entity**: Java class mapped to database table
- **Repository**: Interface for data access operations
- **Relationship Mapping**: Connecting entities (OneToMany, ManyToOne)
- **Query Methods**: Automatically implemented based on method name
- **Slice Testing**: Testing only the repository layer in isolation

## Session 10: AI Code Evaluation (October 2)

### Topics Covered

#### AI as Pair Programmer
- Traditional pair programming vs AI collaboration
- AI as driver, developer as navigator
- Developer responsibility for correctness

#### Effective Prompt Engineering
- The 4 C's: Context, Constraints, Clarity, Code examples
- Writing detailed prompts vs vague requests
- Iterative refinement approach

#### Common AI Mistakes
- Outdated patterns (field injection, deprecated APIs)
- Over-engineering simple solutions
- Missing edge case handling
- Security vulnerabilities
- Deprecated API usage

#### Testing AI-Generated Code
- Why thorough testing is critical
- Test-first approach with AI
- Edge case identification
- Tests as executable specifications

#### Code Review Checklist
- SOLID principles review
- Quality checks (naming, null safety, error handling)
- Security considerations
- Performance implications

#### When to Trust AI
- High-trust scenarios: boilerplate, CRUD, configuration
- Low-trust scenarios: business logic, security, architecture
- The litmus test: Can you explain, understand, debug?

### Learning Objectives

By the end of Session 10, students will be able to:
- Write effective prompts for code generation
- Identify common AI mistakes in Java/Spring code
- Apply systematic code review checklist
- Test AI-generated code thoroughly
- Make informed decisions about when to trust AI output
- Document AI assistance in code and commits

### Key Concepts

- **Prompt Engineering**: Crafting effective instructions for AI
- **Code Review**: Systematic evaluation of generated code
- **Test-First**: Writing tests before generating code
- **Iterative Refinement**: Improving code through multiple AI interactions
- **Documentation**: Recording AI assistance for team awareness

## Assignments

### Team Assignment 5
**Due**: October 9, 11:59 PM

Add data persistence layer to your REST API:
- Create JPA entities with relationships
- Implement Spring Data repositories
- Configure H2 database
- Write @DataJpaTest tests
- Achieve 80% test coverage
- Use AI assistance (document your prompts)

### Repository Analysis 3 (Individual)
**Due**: October 9, 11:59 PM

Compare human vs AI code quality:
- Select a Spring Boot project on GitHub
- Generate similar code using AI
- Compare approaches (structure, quality, completeness)
- Identify strengths and weaknesses of each
- Provide specific examples and recommendations

## Resources

### Documentation
- [Spring Data JPA Reference](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [JPA Annotations Guide](https://jakarta.ee/specifications/persistence/3.0/jakarta-persistence-spec-3.0.html)
- [H2 Database Documentation](https://h2database.com/html/main.html)
- [Spring Data Query Methods](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods)

### Tools
- [H2 Console](http://localhost:8080/h2-console) - Visual database inspection
- [GitHub Copilot](https://github.com/features/copilot) - AI code assistant
- [Claude](https://claude.ai) - AI assistant
- [ChatGPT](https://chat.openai.com) - AI code generation

### Examples
See the course repository for complete working examples:
- Entity classes with relationships
- Custom repository interfaces
- @DataJpaTest examples
- Service layer integration
- AI prompt examples

## Live Coding Demos

### Session 9 Demo
1. Create Task entity with User relationship
2. Implement TaskRepository with custom queries
3. Update TaskService to use repository
4. Write @DataJpaTest tests
5. Verify in H2 console
6. Run full application with persistence

### Session 10 Demo
1. Ask AI to generate UserService
2. Review generated code against checklist
3. Identify issues and red flags
4. Write tests to verify behavior
5. Refactor and improve
6. Document changes made

## Key Takeaways

### Data Persistence
- Spring Data JPA eliminates boilerplate data access code
- Proper entity relationships prevent data inconsistencies
- Testing with @DataJpaTest is fast and focused
- Be aware of N+1 queries and lazy loading issues
- Use DTOs to decouple API from database schema

### AI Collaboration
- AI generates code, you ensure quality
- Detailed prompts produce better results
- All AI code requires thorough testing
- Understanding is mandatory before committing
- Document AI assistance for team awareness

## Preparation for Next Week

Week 6 will cover **SOLID Principles** in depth. Review:
- Single Responsibility Principle (SRP)
- Open/Closed Principle (OCP)
- Liskov Substitution Principle (LSP)

Consider how SOLID applies to the code you're writing now with Spring Data JPA.