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
- **Primary Language:** Java 17+ (not Kotlin as in previous years)
- **Testing Framework:** JUnit 5 with AssertJ and jqwik
- **Build Tool:** Maven or Gradle
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

## Remaining Tasks

### High Priority
- [ ] Set up Slidev presentation structure for first 3 weeks
- [ ] Create GitHub repository with proper structure
- [ ] Develop JUnit 5 testing curriculum with examples
- [ ] Design starter code templates for assignments

### Medium Priority
- [ ] Create AssertJ fluent assertion examples
- [ ] Design property-based testing module with jqwik
- [ ] Develop live coding demo templates
- [ ] Create grading rubrics for major projects

### Low Priority
- [ ] Create integrated examples showing all testing approaches
- [ ] Develop additional practice exercises
- [ ] Set up Discord/Slack for class communication

## File Structure Convention

```
slides/XX-topic/
├── slides.md          # Slidev presentation
├── examples/          # Code shown in slides
├── exercises/         # In-class exercises
└── README.md          # Topic overview and resources

examples/topic/
├── src/main/java/    # Production code
├── src/test/java/    # Test code
├── pom.xml           # Maven configuration
└── README.md         # Example explanation

assignments/assignment-X/
├── starter/          # Starter code for students
├── solution/         # Reference solution (private)
├── rubric.md         # Grading criteria
└── README.md         # Assignment instructions
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
  - Traditional unit tests (JUnit 5)
  - Fluent assertions (AssertJ)
  - Property-based tests (jqwik)
- Tests should demonstrate good practices
- Include both positive and negative test cases

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

1. Always check that Maven/Gradle builds work
2. Verify all tests pass before committing examples
3. Keep examples focused on single concepts
4. Use realistic scenarios when possible
5. Remember the audience is undergraduate CS majors