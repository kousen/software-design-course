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
├── build.gradle.kts          # Root Gradle build file
├── settings.gradle.kts       # Multi-module configuration
├── slides/                   # Slidev presentations for each week
│   ├── 01-introduction/
│   ├── 02-modern-java/
│   └── ...
├── examples/                 # Code examples (Gradle modules)
│   ├── modern-java/
│   ├── testing-fundamentals/
│   └── design-patterns/
├── assignments/              # Student assignments (Gradle modules)
│   ├── assignment-01-tdd/
│   └── assignment-02-refactoring/
├── live-coding/             # In-class sessions (Gradle modules)
│   ├── session-01-java-basics/
│   └── session-03-streams/
├── course-schedule.md       # Detailed 14-week schedule
├── syllabus.md             # Course syllabus
└── README.md               # This file
```

### Quick Links

- [Course Schedule](course-schedule.md)
- [Syllabus](syllabus.md)
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
- **Testing:** JUnit 5, AssertJ, Mockito, jqwik
- **Build Tool:** Gradle (multi-module project)
- **IDE:** IntelliJ IDEA
- **Version Control:** Git/GitHub
- **CI/CD:** GitHub Actions
- **Presentations:** Slidev

### Course Topics by Week

1. **Weeks 1-3:** Modern Java & Testing Fundamentals
2. **Weeks 4–6:** Clean Code, SOLID, AI Collaboration
3. **Weeks 7-10:** Design Patterns
4. **Weeks 11–12:** Paradigm Integration & Concurrency
5. **Weeks 13–14:** DevOps & Final Projects

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

### Assignment Submission

All assignments follow the fork-and-submit workflow:
1. Fork the assignment repository from GitHub
2. Clone your fork locally and complete the work
3. Push your solution to your forked repository
4. Submit your repository URL to Moodle

See [GitHub Workflow Guide](github-workflow-guide.md) for detailed instructions.

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

This course material is provided for educational purposes to Trinity College students enrolled in CPSC 310.