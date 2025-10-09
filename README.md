# CPSC 310: Software Design
## Trinity College — Fall 2025

**A Modern Approach to Software Design with AI Collaboration**

This repository contains comprehensive course materials for CPSC 310: Software Design, taught at Trinity College by Ken Kousen. The course emphasizes practical software design principles, test-driven development, and critical evaluation of AI-assisted coding.

---

## 🎯 Course Philosophy

This course teaches students to be **software architects and designers**, not just coders. We embrace modern AI tools while maintaining critical thinking and emphasizing testing as the primary validation method for all code—especially AI-generated code.

### What Makes This Course Different

- **AI-Integrated Learning**: Students use GitHub Copilot, Claude, ChatGPT, and Gemini throughout the course
- **Team-First Approach**: All major work done in teams from day one
- **Test-Driven Development**: Testing is not an afterthought—it's the foundation
- **Modern Java**: Focus on Java 21 LTS features and idioms
- **Production-Ready Skills**: Build real Spring Boot applications with professional workflows

---

## 📚 Repository Structure

```
software-design-course/
├── slides/                                    # Weekly Slidev presentations
│   ├── week-01-java-foundations/             # Java basics (37 slides)
│   ├── week-02-oop-java/                     # OOP in Java (70 slides)
│   ├── week-03-spring-boot-rest/             # Spring Boot & REST APIs
│   ├── week-04-functional-tdd/               # Functional programming & TDD
│   ├── week-05-data-ai/                      # Data layer & AI collaboration
│   └── week-06-solid-principles/             # SOLID & DRY principles
│
├── examples/                                  # Code examples (Gradle modules)
│   ├── design-patterns/                      # SOLID & DRY implementations
│   │   └── src/main/java/edu/trincoll/
│   │       ├── solid/                        # All 5 SOLID principles
│   │       │   ├── srp/                      # Single Responsibility
│   │       │   ├── ocp/                      # Open-Closed
│   │       │   ├── lsp/                      # Liskov Substitution
│   │       │   ├── isp/                      # Interface Segregation
│   │       │   └── dip/                      # Dependency Inversion
│   │       └── dry/                          # DRY principle examples
│   ├── testing-fundamentals/                 # JUnit 5, AssertJ, jqwik
│   └── week2-oop/                           # OOP examples
│
├── assignments/                               # Student assignments
│   ├── assignment-1-task-tracker/            # REST API starter
│   └── assignment-2-service-layer/           # Service architecture
│
├── docs/                                      # Documentation & guides
│   ├── github-workflow-guide.md              # Git/GitHub best practices
│   ├── gradle-guide.md                       # Gradle setup
│   └── intellij-guide.md                     # IntelliJ IDEA setup
│
├── build.gradle.kts                          # Root Gradle configuration
├── settings.gradle.kts                       # Multi-module setup
├── course-schedule.md                        # 14-week schedule
├── syllabus.md                              # Course syllabus
└── DEMO-CHECKLIST.md                        # Live coding guides
```

---

## 🚀 Quick Start

### Prerequisites

- **Java 21 LTS** (required)
- **IntelliJ IDEA Ultimate** (free with [student license](https://www.jetbrains.com/student/))
- **Git** (2.30 or later)
- **Gradle** (included via wrapper)

### Setup

```bash
# Clone the repository
git clone https://github.com/kousen/software-design-course.git
cd software-design-course

# Build all modules
./gradlew build

# Run all tests
./gradlew test
```

IntelliJ IDEA will automatically detect the multi-module Gradle structure when you open the project.

---

## 🛠️ Technology Stack

| Category | Technology | Version |
|----------|-----------|---------|
| **Language** | Java | 21 LTS |
| **Framework** | Spring Boot | 3.5.5 |
| **Testing** | JUnit 5 | 5.11.0 |
| | AssertJ | 3.26.3 |
| | jqwik | 1.9.1 |
| **Build Tool** | Gradle | 8.11.1 |
| **Code Quality** | SonarCloud | Latest |
| **Presentations** | Slidev | Latest |
| **AI Tools** | GitHub Copilot, Claude, ChatGPT, Gemini | - |

---

## 📖 Course Structure

### Grading Breakdown

- **40%** Progressive Team Project (4 milestones)
- **30%** Repository Analysis (individual)
- **20%** Code Reviews & Peer Assessment
- **10%** Process & Participation

### Weekly Schedule

1. **Week 1**: Java Foundations & Team Formation
2. **Week 2**: Object-Oriented Programming in Java
3. **Week 3**: Spring Boot & REST APIs
4. **Week 4**: Functional Programming & Test-Driven Development
5. **Week 5**: Data Layer with JPA & AI Code Evaluation
6. **Week 6**: SOLID Principles & DRY Principle
7. **Week 7**: Advanced Design Principles
8. **Week 8**: Creational Design Patterns
9. **Week 9**: Structural Design Patterns
10. **Week 10**: Behavioral Design Patterns
11. **Week 11**: Security & Production Concerns
12. **Week 12**: Advanced Spring & Performance
13. **Week 13**: DevOps & Deployment
14. **Week 14**: Project Presentations

**No classes**: October 14 (Fall Break), November 27 (Thanksgiving)

---

## 📝 For Students

### Getting Started with Assignments

**Assignment repositories are distributed via GitHub template:**

1. **One team member**: Click "Use this template" on the assignment repo
2. **Name it**: `team-X-domain` (e.g., `team-3-bookmarks`)
3. **Add teammates**: Settings → Collaborators
4. **Everyone clones**: `git clone <your-team-repo-url>`
5. **Run tests**: `./gradlew test` (many will fail initially!)
6. **Implement features**: Make those tests pass!
7. **Submit**: Repository URL to Moodle (team submission)

### Team Workflow Requirements

- ✅ All team members must contribute via GitHub
- ✅ Use issues, feature branches, and pull requests
- ✅ Document AI assistance in code comments
- ✅ Maintain 80% test coverage (verified by SonarCloud)
- ✅ Follow professional Git workflow practices

### AI Tool Guidelines

This course **encourages** thoughtful use of AI tools:

**✅ Good Uses:**
- Generate boilerplate code
- Explore different approaches
- Debug with AI assistance
- Learn new concepts
- Refactor existing code

**❌ Bad Uses:**
- Submit AI code without understanding
- Let AI make design decisions
- Skip writing tests
- Copy-paste without verification

**Remember**: Tests validate everything, including AI output!

---

## 🎓 Learning Outcomes

By the end of this course, students will be able to:

1. **Design** maintainable software using SOLID principles and design patterns
2. **Implement** production-ready Spring Boot REST APIs
3. **Test** code comprehensively using TDD and multiple testing strategies
4. **Evaluate** AI-generated code critically using testing and design principles
5. **Collaborate** effectively using professional Git/GitHub workflows
6. **Apply** functional programming concepts in Java
7. **Deploy** applications using modern DevOps practices

---

## 📚 Course Materials

### Slides & Presentations

All slides are created with [Slidev](https://sli.dev/) and available in the `slides/` directory. Each week includes:
- Markdown source files
- Exported PDF versions
- Live code examples
- Practice exercises

### Code Examples

All code examples are organized as Gradle submodules with:
- Comprehensive test suites
- Real-world scenarios
- Before/after refactoring examples
- Progressive complexity

### Documentation

Extensive guides available in `docs/`:
- GitHub workflow guide
- Gradle configuration guide
- IntelliJ IDEA setup guide
- Testing best practices
- Spring Boot patterns

---

## 📖 Recommended Resources

### Textbooks

- **Primary**: *Head First Java* (3rd Edition)
- *Effective Java* by Joshua Bloch
- *Design Patterns* by Gamma, Helm, Johnson, and Vlissides (Gang of Four)
- *Clean Architecture* by Robert C. Martin

### Online Resources

- [Java Documentation](https://docs.oracle.com/en/java/)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [AssertJ Documentation](https://assertj.github.io/doc/)
- [jqwik User Guide](https://jqwik.net/docs/current/user-guide.html)
- [Baeldung Java Tutorials](https://www.baeldung.com/)

---

## 🤝 Contributing

This repository is a public educational resource! Contributions are welcome:

1. **Report Issues**: Found a bug or typo? [Open an issue](../../issues)
2. **Suggest Improvements**: Have ideas for better examples? Let us know!
3. **Submit Fixes**: Fork, fix, and submit a pull request

See [CONTRIBUTING.md](CONTRIBUTING.md) for detailed guidelines.

---

## 👥 Contact

**Instructor**: Ken Kousen
**Email**: kkousen@trincoll.edu
**Office**: MECC 175
**Office Hours**: Wednesdays 1:30-3:00 PM

---

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

**This course material is freely available for educational use and can be adapted for other software design courses.**

---

## 🌟 Acknowledgments

- Trinity College Computer Science Department
- The Java and Spring communities
- All the students who have helped refine these materials
- AI tools (Claude, ChatGPT, GitHub Copilot, Gemini) for development assistance

---

**Note**: This repository is actively maintained and updated throughout the Fall 2025 semester. Content and structure may evolve as the course progresses.

*Last Updated: October 2025*
