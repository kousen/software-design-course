# CLAUDE.md - Project Context

## Project Overview

This is a course development project for CPSC 310: Software Design at Trinity College, Fall 2025. The course is being redesigned to teach software design principles in the context of AI-assisted development.

## Key Decisions Made

### Course Philosophy
- Teach students to be software architects and designers, not just coders
- Embrace AI tools while maintaining critical thinking
- Focus on testing as a primary validation method for AI-generated code
- Emphasize multiple programming paradigms (OOP, FP, DOP)

### Technology Stack
- **Primary Language:** Java 21 LTS (not Kotlin as in previous years)
  - Java 25 LTS releases September 2025, but course maintains Java 21 compatibility
- **Framework:** Spring Boot 3.5.5 (introduced Week 3)
- **Testing Framework:** JUnit 5.11.0 with AssertJ 3.26.3 and jqwik 1.9.1
- **Build Tool:** Gradle 8.11.1 (multi-module project with Kotlin DSL)
- **Presentation Tool:** Slidev
- **Version Control:** Git/GitHub
- **AI Tools:** GitHub Copilot, Claude, ChatGPT, Gemini (required)

### Course Structure
- 14 weeks, 28 sessions total
- Tuesdays & Thursdays, 10:50 AM - 12:05 PM
- No classes: October 14, November 27
- Office hours: Wednesdays 1:30-3:00 PM
- 8 teams of 3 students (one may have 4)
- Coordinated with CPSC 415 (Cloud Computing)

### Core Topics Progression
1. Modern Java features (lambdas, streams, Optional, records)
2. Testing from day one (TDD, BDD)
3. SOLID principles with practical examples
4. Design patterns (creational, structural, behavioral)
5. AI collaboration and code evaluation
6. Multiple paradigms (OOP, functional, data-oriented)
7. CI/CD and DevOps practices

## Completed Tasks

### ✅ Day 1 Preparation (Completed - January 2025)
- [x] Redesign grading structure to team-based approach (40% project, 30% analysis, 20% reviews, 10% participation)
- [x] Update syllabus and course schedule for Fall 2025
- [x] Create Assignment 1 starter template with Spring Boot REST API
- [x] Implement 15 comprehensive tests for Assignment 1 using @WebMvcTest
- [x] Convert all documentation to HTML for Moodle upload
- [x] Create Week 1 Slidev presentation (37 slides) covering Java foundations
- [x] Develop live coding demo checklist for Spring Boot API
- [x] Set up Moodle with 8 team groups and groupings
- [x] Configure GitHub template repository for assignment distribution
- [x] Create course welcome message emphasizing practical approach

### ✅ High Priority (Completed)
- [x] Create Gradle multi-module project structure with Java 21 LTS
- [x] Create GitHub repository with proper structure and MIT License
- [x] Set up SOLID principles Slidev presentation with comprehensive examples
- [x] Implement complete SOLID examples (SRP, OCP, LSP, ISP, DIP) with comprehensive tests
- [x] Update course schedule with expanded SOLID coverage (3 sessions)
- [x] Establish edu.trincoll package structure for all examples
- [x] Configure JUnit 5.11.0 and AssertJ 3.26.3 for Gradle compatibility
- [x] Implement DRY principle examples and materials
- [x] Create DRY Slidev presentation with before/after refactoring examples
- [x] Develop validation and string formatting DRY examples
- [x] Organize documentation structure with docs/ folder
- [x] Update GitHub workflow guide with professional development process
- [x] Establish issue → branch → TDD → PR → merge workflow
- [x] Implement comprehensive testing fundamentals module (Issue #10)
- [x] Create 44-slide Slidev presentation on testing fundamentals
- [x] Develop 101 test examples covering JUnit 5, AssertJ, and jqwik
- [x] Resolve jqwik version compatibility with JUnit Platform 1.11.0
- [x] Implement property-based testing with jqwik 1.9.1
- [x] Create TDD methodology examples with Calculator
- [x] Add nested test organization and parameterized testing examples
- [x] Complete remaining SOLID principles: LSP, ISP, DIP examples (Issue #15)
- [x] Implement LSP with Shape hierarchy and Bird hierarchy examples
- [x] Implement ISP with Worker and Printer interface segregation examples
- [x] Implement DIP with MessageService and UserRepository dependency injection examples
- [x] Create comprehensive test suite for all SOLID principles (57 tests total)

### ✅ Medium Priority (Completed)
- [x] Create AssertJ fluent assertion examples (in SOLID and DRY tests)
- [x] Develop comprehensive testing approach with TDD methodology
- [x] Set up professional development workflow documentation
- [x] Create student setup guides (GitHub, Gradle, IntelliJ)
- [x] Design property-based testing module with jqwik (Issue #10)

### ✅ Week 2 (Completed - January 2025)
- [x] Create Week 2 OOP materials and slides (70 slides covering inheritance, polymorphism, interfaces, collections)
- [x] Split long slides into shorter, focused slides for better presentation
- [x] Create week2-oop module with comprehensive examples:
  - Inheritance hierarchy (Employee/Manager/Developer/Executive)
  - Abstract classes and template method pattern (PaymentProcessor)
  - Interfaces and repository pattern (Repository/TaskRepository)
  - Collections framework demonstrations
- [x] Create Assignment 2: Service Layer Architecture
  - 40 tests (29 passing, 11 failing for students to fix)
  - Repository pattern with generic interface
  - Service layer with abstract base class
  - 80% test coverage requirement

## Remaining Tasks

### High Priority
- [ ] Develop Modern Java examples (Week 3-4)
  - Lambda expressions and method references
  - Stream API and collectors
  - Optional and modern error handling
  - Records and pattern matching
- [ ] Design starter code templates for remaining assignments
- [ ] Create design patterns examples (Weeks 8-10)

### Medium Priority
- [ ] Create repository analysis assignment templates (individual work)
- [ ] Develop live coding demo templates for remaining sessions
- [ ] Create grading rubrics for major projects
- [ ] Set up remaining Slidev presentations for each week
- [ ] Develop AI collaboration exercises and evaluation methods
- [ ] Create modern Java feature demonstrations

### Low Priority
- [ ] Create integrated examples showing all testing approaches
- [ ] Develop additional practice exercises and coding challenges
- [ ] Set up Discord/Slack for class communication
- [ ] Set up CI/CD pipeline with GitHub Actions
- [ ] Create video tutorials for complex topics
- [ ] Develop peer review and code evaluation exercises

## Multi-Module Structure

This project uses Gradle's multi-module capabilities:

```
Root Project (software-design-course)
├── build.gradle.kts           # Root build configuration
├── settings.gradle.kts        # Module definitions
└── subprojects/              # All inherit Java 21, JUnit 5, AssertJ

Module Structure:
slides/XX-topic/
├── slides.md                 # Slidev presentation
├── README.md                 # Topic overview and resources

examples/module-name/         # Gradle submodule
├── build.gradle.kts         # Module-specific dependencies
├── src/main/java/           # Production code
├── src/test/java/           # Test code
└── README.md                # Example explanation

assignments/assignment-X/     # Gradle submodule
├── build.gradle.kts         # Assignment dependencies
├── src/main/java/           # Starter/skeleton code
├── src/test/java/           # Test cases
├── rubric.md                # Grading criteria
└── README.md                # Assignment instructions

live-coding/session-X/       # Gradle submodule
├── build.gradle.kts         # Session dependencies
├── src/main/java/           # Demo code
├── src/test/java/           # Test examples
└── README.md                # Session notes
```

## Code Style Guidelines

- Use modern Java features where appropriate
- Prefer composition over inheritance
- Write tests first (TDD)
- Keep methods small and focused
- Use meaningful names
- No comments unless specifically requested
- Follow existing code conventions in each module

## Testing Philosophy

- Every example should have comprehensive tests
- Show multiple testing approaches:
  - Traditional unit tests (JUnit 5.11.0) ✅ Implemented across all modules
  - Fluent assertions (AssertJ 3.26.3) ✅ Implemented across all test suites
  - Property-based tests (jqwik 1.9.1) ✅ Implemented in testing-fundamentals module
  - Parameterized tests ✅ Extensive examples with multiple sources
  - Nested test organization ✅ Hierarchical test structure demonstrated
- Tests should demonstrate good practices ✅ TDD approach used for all implementations
- Include both positive and negative test cases ✅ Comprehensive coverage in validation examples
- Use TDD methodology ✅ Red-Green-Refactor cycle demonstrated throughout

## AI Integration Notes

- Include AI evaluation exercises throughout
- Show both good and bad AI-generated code
- Teach prompt engineering for code generation
- Emphasize verification over generation
- Document AI tool usage in examples

## Presentation Guidelines

- Keep slides concise and visual
- Use code animations in Slidev
- Include speaker notes for teaching points
- Provide live coding opportunities
- Export to PDF for student access

## Assignment Design

- Start simple, build complexity
- Always include test suites
- Encourage pair programming
- Include AI collaboration components
- Clear rubrics with specific criteria

### Assignment Distribution Strategy

**Approach: Fork + Solutions Package**
- Students fork the assignment repository (not use as template)
- Solutions are later distributed via non-conflicting `solutions` package
- Students can pull upstream changes to get solutions without merge conflicts
- This allows easy distribution of reference implementations after deadlines

**Implementation:**
1. Create assignment repository with starter code
2. Students fork the repository to their own GitHub account
3. After assignment deadline, add solutions in `edu.trincoll.solutions` package
4. Students pull upstream changes: `git pull upstream main`
5. Solutions appear in separate package, preserving student work

**Benefits:**
- Simple distribution mechanism
- No merge conflicts (different package)
- Students retain their original implementations
- Easy to compare their solution with reference solution

## Day 1 Ready Status

### What's Ready
- Complete Week 1 slides (37 slides) with Java basics for Python developers
- Live coding demo checklist for Spring Boot API demonstration
- Assignment 1 starter template on GitHub (https://github.com/kousen/assignment-1-starter)
- Moodle fully configured with team submission and all documentation uploaded
- 8 teams created (Teams 1-8) in Moodle grouping
- All documentation converted to HTML for Moodle compatibility

### Key Teaching Points for Demo
- Use @WebMvcTest for slice testing (not @SpringBootTest)
- Use Java records for DTOs and entities
- Use text blocks for JSON in tests
- Show AI assistance but emphasize critical evaluation
- Intentionally let AI make mistakes to demonstrate testing importance

## Important Reminders

1. Always check that Gradle builds work: `./gradlew build` ✅ Verified working
2. Verify all tests pass before committing: `./gradlew test` ✅ All tests passing (57 tests in design-patterns, 101 tests in testing-fundamentals)
3. Each module should focus on single concepts ✅ Separate modules for design patterns and testing
4. Use realistic scenarios when possible ✅ Employee/payroll, discount, validation, Calculator, FizzBuzz examples
5. Remember the audience is undergraduate CS majors
6. New modules are easy to add to settings.gradle.kts ✅ Structure established
7. Common dependencies are inherited from root build.gradle.kts ✅ JUnit/AssertJ/jqwik configured
8. Follow GitHub workflow: issue → branch → TDD → PR → merge → cleanup ✅ Demonstrated with Issues #1-3, #10, #15
9. Document all implementations with comprehensive slide presentations ✅ SOLID, DRY, and testing fundamentals complete
10. Use edu.trincoll package structure for all examples ✅ Consistent across all implementations
11. Maintain JUnit Platform compatibility when adding jqwik ✅ jqwik 1.9.1 works with JUnit Platform 1.11.0
12. Use @WebMvcTest for controller testing instead of @SpringBootTest ✅ Demonstrated in demo checklist
13. Use text blocks for JSON content in tests ✅ Modern Java feature emphasis