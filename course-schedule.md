# CPSC 310: Software Design - Course Schedule
Fall 2025 | Trinity College

## Course Overview
- **Duration:** September 2 - December 8, 2025
- **Schedule:** Tuesdays & Thursdays, 1:15 each session
- **Total Sessions:** 28 (14 weeks)
- **No Classes:** October 14, November 27 (Thanksgiving)

---

## Week 1: Java Foundations & Team Formation
**September 2-4**

### Session 1 (Sept 2): Course Introduction & Team Formation
- Course objectives and structure
- Live coding: Build a Spring Boot API with AI assistance
- Team formation activity (3-person teams, mixed experience)
- Development environment setup (IntelliJ, Git, GitHub)
- **Lab:** Teams create GitHub repository and push hello-world Spring Boot app
- **Milestone 1 Assigned:** Due Sept 11 (repository setup, SonarCloud, basic API)

### Session 2 (Sept 4): Java Basics & Testing Introduction
- Transition from Python to Java: syntax basics
- Classes, objects, and methods in Spring context
- Running JUnit tests (provided test suites)
- Test-first learning: tests as specifications
- **Team Assignment 1:** Fork demo app, modify it, make all tests pass (Due Sept 11)

---

## Week 2: Object-Oriented Programming in Java
**September 9-11**

### Session 3 (Sept 9): Inheritance & Polymorphism
- Inheritance and extends keyword
- Method overriding vs overloading  
- Abstract classes and methods
- Running inheritance test suites
- **Lab:** Implement Spring service classes to pass provided tests

### Session 4 (Sept 11): Interfaces & Collections
- Interfaces vs abstract classes
- Multiple interface implementation
- Java Collections Framework basics
- Using collections in Spring services
- **Team Assignment 2:** Implement service layer with collections (Due Sept 18)
- **Repository Analysis 1:** Analyze OOP patterns in Spring Framework (Individual, Due Sept 18)
- **Milestone 1 Due:** Basic Spring Boot app with SonarCloud

---

## Week 3: Spring Boot & REST APIs
**September 16-18**
**[Coordinated with CPSC 415: Both covering containerization basics]**

### Session 5 (Sept 16): Spring Boot REST APIs
- @RestController, @GetMapping, @PostMapping
- Request/Response bodies and DTOs
- Dependency injection with @Service, @Repository
- Testing with @SpringBootTest and MockMvc
- **Lab:** Teams build CRUD endpoints with AI assistance

### Session 6 (Sept 18): API Design & Documentation
- RESTful design principles
- OpenAPI/Swagger documentation
- Basic validation and error handling
- AI code evaluation strategies
- **Team Assignment 3:** Complete CRUD API with tests (Due Sept 25)
- **Repository Analysis 2:** Evaluate REST API design in popular GitHub projects (Individual, Due Sept 25)

---

## Week 4: Testing Fundamentals & TDD
**September 23-25**

### Session 7 (Sept 23): Writing Your Own Tests
- JUnit 5 annotations and lifecycle
- Test-Driven Development cycle
- AssertJ fluent assertions
- Testing Spring Boot applications with MockMvc
- **Lab:** Teams write comprehensive tests for their APIs

### Session 8 (Sept 25): Functional Programming Basics
- Lambda expressions in Java
- Stream API for data processing
- Functional interfaces (Predicate, Function, Consumer)
- Using streams in Spring services
- **Team Assignment 4:** Refactor services to use streams (Due Oct 2)

---

## Week 5: Data Layer & AI Collaboration
**September 30—October 2**

### Session 9 (Sept 30): Spring Data JPA
- Entity classes and relationships
- @Repository and Spring Data methods
- H2 in-memory database configuration
- Testing data layers with @DataJpaTest
- **Lab:** Teams add persistence layer with AI assistance

### Session 10 (Oct 2): AI Code Evaluation
- Prompt engineering for Spring Boot
- Common AI mistakes in Java/Spring
- Testing AI-generated code thoroughly
- Code review best practices
- **Team Assignment 5:** Add data persistence, ensure 80% test coverage (Due Oct 9)
- **Repository Analysis 3:** Compare human vs AI code quality in a project (Individual, Due Oct 9)

---

## Week 6: SOLID Principles
**October 7-9**

### Session 11 (Oct 7): SRP, OCP, LSP
- Single Responsibility Principle
- Open/Closed Principle
- Liskov Substitution Principle
- Java examples and violations

### Session 12 (Oct 9): ISP, DIP
- Interface Segregation Principle
- Dependency Inversion Principle
- Dependency injection in Java
- Spring framework introduction

---

## Week 7: SOLID Principles Applied  
**October 16 (Note: No class Oct 14)**
**[Coordinated with CPSC 415: Microservices architecture]**

### Session 13 (Oct 16): SOLID in Spring Boot
- SOLID principles in REST API design
- Service layer patterns
- Repository pattern and DIP
- Refactoring controllers for SRP
- **Milestone 2 Due:** Complete CRUD API with SOLID principles, 80% test coverage
- **Team Assignment 6:** Refactor code to follow SOLID (Due Oct 23)

---

## Week 8: Architecture & Creational Patterns
**October 21-23**

### Session 14 (Oct 21): Software Architecture
- Layered architecture
- MVC and MVP patterns
- Architecture patterns and SOLID
- Dependency injection in practice

### Session 15 (Oct 23): Factory & Builder Patterns
- Factory Method pattern
- Abstract Factory pattern
- Builder pattern
- How these patterns support OCP

---

## Week 9: Structural Design Patterns
**October 28-30**

### Session 16 (Oct 28): Adapter & Decorator
- Adapter pattern
- Decorator pattern
- Enhancing functionality
- Java I/O as decorator example

### Session 17 (Oct 30): Composite, Proxy & Facade
- Composite pattern for hierarchies
- Proxy pattern types
- Facade pattern for simplification
- How these patterns follow ISP

---

## Week 10: Behavioral Design Patterns
**November 4-6**

### Session 18 (Nov 4): Observer & Strategy
- Observer pattern
- Java event model
- Strategy pattern
- Functional strategies with lambdas

### Session 19 (Nov 6): Command & Iterator
- Command pattern
- Iterator pattern and Java collections
- Template Method pattern
- Behavioral patterns with functions

---

## Week 11: Security & Production Concerns
**November 11-13**
**[Coordinated with CPSC 415: Kubernetes deployment]**

### Session 20 (Nov 11): API Security Basics
- Authentication vs Authorization
- Spring Security fundamentals
- JWT tokens and session management
- Testing secured endpoints
- **Lab:** Teams add security layer with AI assistance

### Session 21 (Nov 13): Production Readiness
- Configuration management (application.yml)
- Logging and monitoring basics
- Docker containerization for Spring Boot
- Performance testing with JMeter
- **Milestone 3 Due:** Secured API with 3+ design patterns, documentation
- **Repository Analysis 6:** Security analysis of open-source projects (Individual, Due Nov 20)

---

## Week 12: Advanced Spring & Performance
**November 18-20**

### Session 22 (Nov 18): Async Operations & Caching
- @Async and CompletableFuture in Spring
- Spring Cache abstraction
- Database query optimization
- Performance testing with JMeter
- **Lab:** Profile and optimize AI-generated code

### Session 23 (Nov 20): Microservices Introduction
- Microservices vs Monoliths
- REST client with RestTemplate/WebClient
- Service discovery concepts
- API versioning strategies
- **Assignment:** Repository analysis of Spring Boot microservices architecture

---

## Week 13: DevOps & Deployment
**November 25** *(Thanksgiving week—shortened)*
**[Coordinated with CPSC 415: Production deployment]**

### Session 24 (Nov 25): CI/CD Pipeline
- GitHub Actions for Spring Boot
- Automated testing in CI pipeline
- SonarCloud quality gates
- Deployment strategies
- **Milestone 4 Due:** Deployed API with CI/CD pipeline
- **Final Repository Analysis:** Analyze a microservices architecture (Individual, Due Dec 2)

### Session 25: NO CLASS—Thanksgiving Break

---

## Week 14: Course Synthesis
**December 2-4**

### Session 26 (Dec 2): Project Presentations I
- Student project demonstrations
- Peer code reviews
- Design decisions discussion

### Session 27 (Dec 4): Project Presentations II & Wrap-up
- Final project presentations
- Industry preparation
- Course retrospective
- Future learning paths

---

## Assessment Timeline Summary

### Team Project Milestones (40% of grade)
- **Milestone 1 (Week 2, Sept 11):** Repository setup, SonarCloud, basic API - 10%
- **Milestone 2 (Week 7, Oct 16):** CRUD API with SOLID, 80% coverage - 10%
- **Milestone 3 (Week 11, Nov 13):** Security, patterns, documentation - 10%
- **Milestone 4 (Week 13, Nov 25):** Deployed with CI/CD pipeline - 10%

### Weekly Team Assignments (20% of grade)
- Due Thursdays at 11:59 PM
- Submitted via GitHub (one per team)
- Lowest grade dropped
- Late policy: -5% per day (max 3 days)

### Individual Repository Analysis Schedule (30% of grade)
- **Week 2 (Sept 18):** OOP patterns in Spring Framework
- **Week 3 (Sept 25):** REST API design evaluation
- **Week 5 (Oct 9):** Human vs AI code quality comparison
- **Week 6 (Oct 16):** SOLID violations in legacy code
- **Week 8 (Oct 30):** Design patterns in frameworks
- **Week 10 (Nov 6):** Testing strategies analysis
- **Week 11 (Nov 20):** Security implementation review
- **Week 13 (Dec 2):** Microservices architecture analysis

## Coordination with CPSC 415 (Cloud Computing)

### Shared Topics by Week:
- **Week 1:** Both courses introduce development environment
- **Week 3:** Software Design focuses on API design, Cloud covers containers
- **Week 7:** Software Design teaches SOLID, Cloud covers microservices
- **Week 11:** Software Design implements security, Cloud deploys to Kubernetes
- **Week 13:** Both courses cover CI/CD and deployment

### For Students in Both Courses:
- Use same project for both classes
- CPSC 310 grades design and code quality
- CPSC 415 grades deployment and operations
- Team presentations count for both courses

## AI Collaboration Requirements

### Every Team Assignment Must Include:
- Documentation of AI prompts used (in comments)
- Tests verifying AI-generated code works correctly
- At least one code review noting AI improvements made

### Repository Analysis Must Include:
- Critical evaluation of code quality
- Specific examples from the analyzed repository
- Suggestions for improvement using course principles

---

## Assessment Overview
- Progressive Team Project (4 milestones): 40%
- Repository Analysis Assignments: 30%
- Code Reviews & Peer Assessment: 20%
- Process & Participation: 10%

## Expected Weekly Workload
- Class time: 2.5 hours
- Pre-class preparation: 1-2 hours
- Assignments & projects: 4-5 hours
- Code reviews & team work: 2-3 hours
- **Total: 10-12 hours/week**