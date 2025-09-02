# CPSC 310: Software Design
## Trinity College—Fall 2025

This repository contains course materials for CPSC 310: Software Design, taught at Trinity College by Ken Kousen.

### Course Overview

This course teaches software design principles in the context of AI-assisted development. Students work in teams from day one, building production-ready Spring Boot applications while mastering:
- SOLID principles and design patterns
- Test-Driven Development (TDD)
- Modern Java features (Java 21)
- Spring Boot REST API development
- AI collaboration with critical evaluation
- Professional Git/GitHub workflows

### Repository Structure

```
software-design-course/
├── build.gradle.kts                           # Root Gradle build file
├── settings.gradle.kts                        # Multi-module configuration
├── DEMO-CHECKLIST.md                         # Live coding demo guide
├── slides/                                    # Slidev presentations
│   ├── week-01-java-foundations/             # Week 1 complete slides (37 slides)
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
│   └── assignment-1-task-tracker/            # First assignment starter code
├── live-coding/                              # In-class sessions (Gradle modules)
│   ├── session-01-java-basics/              # (planned)
│   └── session-03-streams/                  # (planned)
├── docs/                                     # Documentation and guides
│   ├── *.md                                 # Markdown versions
│   └── *.html                               # HTML versions for Moodle upload
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
- **Framework:** Spring Boot 3.5.5
- **Testing:** JUnit 5.11.0, AssertJ 3.26.3, jqwik 1.9.1
- **Build Tool:** Gradle 8.11.1 (multi-module project)
- **Code Quality:** SonarCloud integration
- **IDE:** IntelliJ IDEA Ultimate (free with student license)
- **Version Control:** Git/GitHub with team collaboration
- **CI/CD:** GitHub Actions
- **Presentations:** Slidev
- **AI Tools:** GitHub Copilot, Claude, ChatGPT, Gemini (required)

### Course Structure

**Grading:**
- 40% Progressive Team Project (4 milestones)
- 30% Repository Analysis (individual)
- 20% Code Reviews & Peer Assessment
- 10% Process & Participation

**Teams:** 8 teams of 3 students (one may have 4)
**Coordination:** Integrated with CPSC 415 (Cloud Computing)

### Course Topics by Week

1. **Week 1:** Java Foundations & Team Formation
2. **Week 2:** Object-Oriented Programming in Java
3. **Week 3:** Spring Boot & REST APIs
4. **Week 4:** Testing Fundamentals & TDD
5. **Week 5:** Data Layer & AI Collaboration
6. **Weeks 6-7:** SOLID Principles & Application
7. **Weeks 8-10:** Design Patterns (Creational, Structural, Behavioral)
8. **Week 11:** Security & Production Concerns
9. **Week 12:** Advanced Spring & Performance
10. **Week 13:** DevOps & Deployment
11. **Week 14:** Project Presentations

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

✅ **Ready for Day 1:**
- Complete Week 1 slides with Java basics for Python developers
- Live coding demo checklist for Spring Boot API
- Assignment 1 starter template on GitHub
- Moodle fully configured with team submission
- All documentation converted to HTML

📋 **Next Steps:**
- Week 2 OOP materials
- Additional design pattern examples
- Repository analysis assignment templates

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

### For Students

**Assignment 1 Starter:** https://github.com/kousen/assignment-1-starter

#### Getting Started:
1. One team member: Click "Use this template" (not Fork!)
2. Name it: `team-X-domain` (e.g., `team-3-bookmarks`)
3. Add teammates as collaborators
4. Clone YOUR NEW team repository
5. Run `./gradlew test` to see failing tests
6. Implement functionality to make tests pass
7. Submit repository URL to Moodle

#### Team Workflow:
- All team members must contribute via GitHub
- Use issues, branches, and pull requests
- Document AI assistance in code comments
- Maintain 80% test coverage (verified by SonarCloud)

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