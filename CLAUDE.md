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
- [x] Create Gradle multi-module project structure
- [x] Set up SOLID principles Slidev presentation
- [x] Implement SOLID examples with comprehensive tests
- [x] Update course schedule with expanded SOLID coverage
- [x] Establish edu.trincoll package structure
- [x] Configure JUnit 5.11.0 and AssertJ 3.26.3 for compatibility

## Remaining Tasks

### High Priority
- [ ] Create GitHub repository with proper structure
- [ ] Develop Modern Java examples (Weeks 1-2)
- [ ] Develop JUnit 5 testing curriculum with examples
- [ ] Design starter code templates for assignments
- [ ] Complete LSP, ISP, and DIP examples

### Medium Priority
- [ ] Create AssertJ fluent assertion examples
- [ ] Design property-based testing module with jqwik
- [ ] Develop live coding demo templates
- [ ] Create grading rubrics for major projects
- [ ] Set up remaining Slidev presentations

### Low Priority
- [ ] Create integrated examples showing all testing approaches
- [ ] Develop additional practice exercises
- [ ] Set up Discord/Slack for class communication
- [ ] Set up CI/CD pipeline with GitHub Actions

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
  - Traditional unit tests (JUnit 5.11.0) ✅ Implemented
  - Fluent assertions (AssertJ 3.26.3) ✅ Implemented
  - Property-based tests (jqwik) 📋 Planned
- Tests should demonstrate good practices ✅ SOLID examples include comprehensive tests
- Include both positive and negative test cases ✅ Demonstrated in discount strategy tests

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
2. Verify all tests pass before committing: `./gradlew test` ✅ All SOLID tests passing
3. Each module should focus on single concepts ✅ design-patterns module contains SOLID examples
4. Use realistic scenarios when possible ✅ Employee/payroll and discount examples
5. Remember the audience is undergraduate CS majors
6. New modules are easy to add to settings.gradle.kts ✅ Structure established
7. Common dependencies are inherited from root build.gradle.kts ✅ JUnit/AssertJ configured
8. Follow GitHub workflow: issue → branch → TDD → PR → merge → cleanup