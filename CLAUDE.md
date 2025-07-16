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
- **Testing Framework:** JUnit 5 with AssertJ and jqwik
- **Build Tool:** Gradle (multi-module project with Kotlin DSL)
- **Presentation Tool:** Slidev
- **Version Control:** Git/GitHub

### Course Structure
- 14 weeks, 28 sessions total
- Tuesdays & Thursdays, 10:50 AM - 12:05 PM
- No classes: October 14, November 27
- Office hours: Wednesdays 1:30-3:00 PM

### Core Topics Progression
1. Modern Java features (lambdas, streams, Optional, records)
2. Testing from day one (TDD, BDD)
3. SOLID principles with practical examples
4. Design patterns (creational, structural, behavioral)
5. AI collaboration and code evaluation
6. Multiple paradigms (OOP, functional, data-oriented)
7. CI/CD and DevOps practices

## Completed Tasks

### ✅ High Priority (Completed)
- [x] Create Gradle multi-module project structure with Java 21 LTS
- [x] Create GitHub repository with proper structure and MIT License
- [x] Set up SOLID principles Slidev presentation with comprehensive examples
- [x] Implement SOLID examples (SRP, OCP) with comprehensive tests
- [x] Update course schedule with expanded SOLID coverage (3 sessions)
- [x] Establish edu.trincoll package structure for all examples
- [x] Configure JUnit 5.11.0 and AssertJ 3.26.3 for Gradle compatibility
- [x] Implement DRY principle examples and materials
- [x] Create DRY Slidev presentation with before/after refactoring examples
- [x] Develop validation and string formatting DRY examples
- [x] Organize documentation structure with docs/ folder
- [x] Update GitHub workflow guide with professional development process
- [x] Establish issue → branch → TDD → PR → merge workflow

### ✅ Medium Priority (Completed)
- [x] Create AssertJ fluent assertion examples (in SOLID and DRY tests)
- [x] Develop comprehensive testing approach with TDD methodology
- [x] Set up professional development workflow documentation
- [x] Create student setup guides (GitHub, Gradle, IntelliJ)

## Remaining Tasks

### High Priority
- [ ] Develop Modern Java examples (Weeks 1-2)
  - Lambda expressions and method references
  - Stream API and collectors
  - Optional and modern error handling
  - Records and pattern matching
- [ ] Complete remaining SOLID principles: LSP, ISP, DIP examples
- [ ] Develop JUnit 5 testing curriculum with comprehensive examples
- [ ] Design starter code templates for assignments
- [ ] Create design patterns examples (Weeks 8-10)

### Medium Priority
- [ ] Design property-based testing module with jqwik
- [ ] Develop live coding demo templates for each session
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
  - Traditional unit tests (JUnit 5.11.0) ✅ Implemented in SOLID and DRY examples
  - Fluent assertions (AssertJ 3.26.3) ✅ Implemented across all test suites
  - Property-based tests (jqwik) 📋 Planned for Week 4
- Tests should demonstrate good practices ✅ TDD approach used for all implementations
- Include both positive and negative test cases ✅ Comprehensive coverage in validation examples
- Use TDD methodology ✅ Red-Green-Refactor cycle demonstrated in DRY implementation

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

## Important Reminders

1. Always check that Gradle builds work: `./gradlew build` ✅ Verified working
2. Verify all tests pass before committing: `./gradlew test` ✅ All tests passing (19/19)
3. Each module should focus on single concepts ✅ design-patterns module contains SOLID and DRY examples
4. Use realistic scenarios when possible ✅ Employee/payroll, discount, validation, and reporting examples
5. Remember the audience is undergraduate CS majors
6. New modules are easy to add to settings.gradle.kts ✅ Structure established
7. Common dependencies are inherited from root build.gradle.kts ✅ JUnit/AssertJ configured
8. Follow GitHub workflow: issue → branch → TDD → PR → merge → cleanup ✅ Demonstrated with Issues #1-3
9. Document all implementations with comprehensive slide presentations ✅ SOLID and DRY complete
10. Use edu.trincoll package structure for all examples ✅ Consistent across all implementations