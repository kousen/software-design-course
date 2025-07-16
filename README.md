# CPSC 310: Software Design
## Trinity College—Fall 2025

This repository contains course materials for CPSC 310: Software Design, taught at Trinity College by Ken Kousen.

### Course Overview

This course teaches modern software design principles using Java, with emphasis on:
- Test-Driven Development (TDD)
- SOLID principles and design patterns
- Modern Java features (Java 8+)
- AI-assisted development practices
- Clean architecture and code quality

### Repository Structure

```
software-design-course/
├── build.gradle.kts                           # Root Gradle build file
├── settings.gradle.kts                        # Multi-module configuration
├── slides/                                    # Slidev presentations
│   ├── solid-principles.md                   # SOLID principles presentation
│   ├── dry-principle.md                      # DRY principle presentation
│   └── testing-fundamentals.md               # Testing fundamentals presentation
├── examples/                                  # Code examples (Gradle modules)
│   ├── modern-java/                          # (planned)
│   ├── testing-fundamentals/                 # JUnit 5, AssertJ, jqwik examples
│   │   └── src/main/java/edu/trincoll/       # Test subjects and utilities
│   │   └── src/test/java/edu/trincoll/       # Comprehensive test examples
│   └── design-patterns/                      # SOLID and DRY examples implemented
│       └── src/main/java/edu/trincoll/       # Package structure
│           ├── solid/                        # Complete SOLID examples
│           │   ├── srp/                     # Single Responsibility Principle
│           │   ├── ocp/                     # Open/Closed Principle
│           │   ├── lsp/                     # Liskov Substitution Principle
│           │   ├── isp/                     # Interface Segregation Principle
│           │   └── dip/                     # Dependency Inversion Principle
│           └── dry/                          # DRY principle examples
├── assignments/                               # Student assignments (Gradle modules)
│   ├── assignment-01-tdd/                    # (planned)
│   └── assignment-02-refactoring/            # (planned)
├── live-coding/                              # In-class sessions (Gradle modules)
│   ├── session-01-java-basics/              # (planned)
│   └── session-03-streams/                  # (planned)
├── docs/                                     # Documentation and guides
│   ├── github-workflow-guide.md             # Git/GitHub workflow for students
│   ├── gradle-guide.md                      # Gradle build tool guide
│   ├── intellij-guide.md                    # IntelliJ IDEA setup guide
│   └── moodle-submission-template.md        # Assignment submission template
├── course-schedule.md                        # Updated 14-week schedule
├── syllabus.md                              # Course syllabus
├── CLAUDE.md                                # Project context for AI assistance
└── README.md                                # This file
```

### Quick Links

- [Course Schedule](course-schedule.md)
- [Syllabus](syllabus.md)
- [GitHub Workflow Guide](docs/github-workflow-guide.md)
- [Gradle Guide](docs/gradle-guide.md)
- [IntelliJ IDEA Guide](docs/intellij-guide.md)
- [Student Resources](#student-resources)

### Getting Started

#### Prerequisites
- Java 21 LTS (required)
- IntelliJ IDEA (Ultimate Edition with student license)
- Git
- Gradle (included via wrapper)

#### Setup Instructions
1. Clone this repository
2. Open in IntelliJ IDEA (it will auto-detect the Gradle multi-module structure)
3. Ensure Java 21 is configured as Project SDK
4. Run `./gradlew build` to build all modules
5. Run `./gradlew test` to run all tests across modules

### Key Technologies

- **Language:** Java 21 LTS
- **Testing:** JUnit 5.11.0, AssertJ 3.26.3, jqwik 1.9.1, Mockito
- **Build Tool:** Gradle 8.14.2 (multi-module project)
- **IDE:** IntelliJ IDEA
- **Version Control:** Git/GitHub
- **CI/CD:** GitHub Actions (planned)
- **Presentations:** Slidev

### Course Topics by Week

1. **Weeks 1-3:** Modern Java & Testing Fundamentals
2. **Weeks 4–6:** Clean Code, SOLID, AI Collaboration
3. **Weeks 7-10:** SOLID Applied & Design Patterns
4. **Weeks 11–12:** Paradigm Integration & Concurrency
5. **Weeks 13–14:** DevOps & Final Projects

### Current Implementation Status

✅ **Completed:**
- **Project Infrastructure:**
  - Multi-module Gradle project structure with Java 21 LTS
  - MIT License for open educational use
  - Organized documentation in `docs/` folder
  - GitHub workflow with issues, feature branches, and pull requests

- **SOLID Principles (Week 6-7):**
  - Complete Slidev presentation with interactive examples
  - SRP examples: Employee, PayrollCalculator, EmployeeRepository separation
  - OCP examples: Discount strategy pattern implementation
  - LSP examples: Shape hierarchy (Rectangle, Square, Circle) and Bird hierarchy (Eagle, Sparrow, Penguin)
  - ISP examples: Worker interfaces (Workable, Eatable, Sleepable) and Printer interfaces (Printable, Scannable, Faxable)
  - DIP examples: MessageService abstraction and UserRepository abstraction with dependency injection
  - Comprehensive test suite (57 tests) demonstrating all SOLID principles
  - Updated course schedule with expanded SOLID coverage

- **DRY Principle:**
  - Complete Slidev presentation covering duplication elimination
  - Before/after validation examples showing refactoring process
  - String formatting examples with extracted utilities
  - ValidationUtils and ReportUtils as DRY solutions
  - TDD implementation with comprehensive test coverage

- **Testing Fundamentals (Week 3-4):**
  - Complete Slidev presentation (44 slides) covering JUnit 5, AssertJ, and modern testing
  - Comprehensive code examples module with 101 passing tests
  - JUnit 5 basics, lifecycle management, and advanced features
  - AssertJ fluent assertions for better test readability
  - Extensive parameterized testing (value, method, CSV, enum sources)
  - Property-based testing with jqwik 1.9.1 (compatible with JUnit Platform 1.11.0)
  - TDD methodology with Calculator examples using Arrange-Act-Assert pattern
  - Nested test organization for hierarchical test structure
  - Complete documentation with teaching objectives and progressive learning path

- **Student Resources:**
  - GitHub workflow guide (assignment + professional workflows)
  - Gradle build tool setup guide
  - IntelliJ IDEA configuration guide
  - Professional development process documentation

🚧 **In Progress:**
- Course materials development following established workflow
- Additional design principle examples

📋 **Planned:**
- Modern Java examples and exercises (Weeks 1-2)
- Complete design patterns catalog (Weeks 8-10)
- AI collaboration exercises and evaluation methods
- CI/CD pipeline setup with GitHub Actions

### Student Resources

#### Recommended Reading
- *Head First Java* (3rd Edition)—Course textbook
- *Effective Java* by Joshua Bloch
- *Design Patterns* by Gamma, Helm, Johnson, and Vlissides

#### Online Resources
- [Java Documentation](https://docs.oracle.com/en/java/)
- [Baeldung Java Tutorials](https://www.baeldung.com/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [AssertJ Documentation](https://assertj.github.io/doc/)
- [jqwik User Guide](https://jqwik.net/docs/current/user-guide.html)

### Assignment Submission

All assignments follow the fork-and-submit workflow:
1. Fork the assignment repository from GitHub
2. Clone your fork locally and complete the work
3. Push your solution to your forked repository
4. Submit your repository URL to Moodle

See [GitHub Workflow Guide](docs/github-workflow-guide.md) for detailed instructions.

For pair programming:
- One person forks and adds partner as collaborator
- Both students submit the same repository URL
- Include both names in README or CONTRIBUTORS file

### AI Tool Guidelines

This course encourages thoughtful use of AI tools:
- ✅ Use AI for learning and exploration
- ✅ Generate boilerplate code
- ✅ Debug with AI assistance
- ❌ Don't submit AI code without understanding
- ❌ Don't rely on AI for design decisions
- ❌ Always verify AI suggestions

### Contact Information

**Instructor:** Ken Kousen  
**Email:** kkousen@trincoll.edu  
**Office Hours:** Wednesdays 1:30-3:00 PM, MECC 175

### Contributing

For errors or suggestions:
1. Open an issue in this repository
2. Submit a pull request with fixes
3. Contact the instructor

### License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

This course material is freely available for educational use and can be adapted for other software design courses.