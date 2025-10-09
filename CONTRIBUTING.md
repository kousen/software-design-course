# Contributing to CPSC 310: Software Design

Thank you for your interest in contributing to this educational repository! This course material is designed to be a shared resource for teaching software design principles.

## 🎯 Ways to Contribute

### 1. Report Issues

Found a bug, typo, or problem with the code examples?

- **Check existing issues** first to avoid duplicates
- **Open a new issue** with a clear description
- Include:
  - What you found
  - Where it's located (file path, line number)
  - Expected vs actual behavior
  - Screenshots if applicable

### 2. Suggest Improvements

Have ideas for better examples or explanations?

- Open an issue with the `enhancement` label
- Describe your suggestion clearly
- Explain why it would improve the course material
- If you have example code, include it!

### 3. Fix Bugs or Typos

Small fixes are always welcome:

1. Fork the repository
2. Create a branch: `git checkout -b fix/description`
3. Make your changes
4. Test if applicable: `./gradlew test`
5. Commit with clear message
6. Push and open a pull request

### 4. Add Examples

Contributing new code examples:

1. Follow the existing package structure: `edu.trincoll.<topic>`
2. Include comprehensive tests
3. Add JavaDoc comments
4. Follow the code style guidelines below
5. Update relevant documentation

### 5. Improve Documentation

Documentation contributions are valuable:

- Fix typos or unclear explanations
- Add missing documentation
- Improve setup guides
- Translate materials (if applicable)

## 📋 Code Style Guidelines

### Java Code

- **Use Java 21 features** where appropriate
- **Prefer composition over inheritance**
- **Write tests first** (TDD approach)
- **Keep methods small and focused** (< 20 lines)
- **Use meaningful names** (no single-letter variables except loop indices)
- **Follow existing conventions** in each module

### Example Structure

```java
package edu.trincoll.topic;

/**
 * Brief description of what this class demonstrates.
 * Explain the design principle or pattern being illustrated.
 */
public class ExampleClass {

    /**
     * Clear description of method purpose.
     * @param param description
     * @return description
     */
    public ReturnType methodName(ParamType param) {
        // Implementation
    }
}
```

### Test Structure

```java
package edu.trincoll.topic;

import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;

class ExampleClassTest {

    @Test
    @DisplayName("Should describe what this test verifies")
    void testMethodName() {
        // Arrange
        var example = new ExampleClass();

        // Act
        var result = example.methodName(input);

        // Assert
        assertThat(result).isEqualTo(expected);
    }
}
```

## 🔄 Pull Request Process

1. **Fork** the repository
2. **Create a feature branch** from `main`
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. **Make your changes** following the guidelines
4. **Test thoroughly**
   ```bash
   ./gradlew test
   ./gradlew build
   ```
5. **Commit with clear messages**
   ```bash
   git commit -m "Add validation example for email format"
   ```
6. **Push to your fork**
   ```bash
   git push origin feature/your-feature-name
   ```
7. **Open a Pull Request** with:
   - Clear title describing the change
   - Description of what changed and why
   - Reference to any related issues (#123)
   - Screenshots if UI/documentation changes

### PR Review Process

- Maintainers will review your PR
- You may be asked to make changes
- Once approved, your PR will be merged
- Your contribution will be acknowledged!

## 📝 Commit Message Guidelines

Use clear, concise commit messages:

**Good:**
```
Add property-based testing example with jqwik
Fix typo in SOLID principles slide 42
Update README with correct Spring Boot version
```

**Avoid:**
```
Update
Fix stuff
Changed things
```

### Commit Message Format

```
<type>: <short description>

[optional body]

[optional footer]
```

**Types:**
- `feat`: New feature or example
- `fix`: Bug fix
- `docs`: Documentation changes
- `test`: Adding or updating tests
- `refactor`: Code refactoring
- `style`: Code formatting (no logic change)
- `chore`: Maintenance tasks

## 🧪 Testing Requirements

All code contributions must include tests:

- **New examples**: Comprehensive test coverage
- **Bug fixes**: Test that reproduces the bug
- **Refactoring**: Existing tests must pass
- **Aim for 80%+ coverage** where applicable

Run tests before submitting:
```bash
./gradlew test
./gradlew build
```

## 📚 Documentation Requirements

When adding new examples or features:

1. **Update README.md** if structure changes
2. **Add JavaDoc** to all public classes/methods
3. **Include inline comments** for complex logic
4. **Update relevant slide content** if applicable
5. **Add to appropriate module README**

## 🎓 Educational Guidelines

This is an educational repository, so contributions should:

- **Be pedagogically sound**: Teach concepts clearly
- **Progress logically**: Build on previous concepts
- **Include explanations**: Don't just show code
- **Demonstrate best practices**: Show the right way
- **Avoid over-complexity**: Keep it understandable for students

## ⚖️ License

By contributing, you agree that your contributions will be licensed under the same MIT License that covers this project.

## 🤔 Questions?

- **Open an issue** for general questions
- **Email the instructor**: kkousen@trincoll.edu
- **Check existing documentation** first

## 🌟 Recognition

All contributors will be acknowledged in:
- The repository's contributor list
- Course acknowledgments (if significant contribution)
- Git commit history

Thank you for helping make this course better!

---

*This is an evolving document. Suggestions for improving these guidelines are welcome!*
