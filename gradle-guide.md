# Gradle Guide for CPSC 310

This guide covers essential Gradle tasks for this course. We use Gradle as our build tool with the Kotlin DSL for configuration.

## What is Gradle?

Gradle is a build automation tool that handles:
- Compiling Java source code
- Managing dependencies (JUnit, AssertJ, etc.)
- Running tests
- Creating JAR files
- Multi-module project management

## Gradle Wrapper

This project uses the **Gradle Wrapper** (`gradlew`), which ensures everyone uses the same Gradle version regardless of what's installed locally.

### Key Benefits
- ✅ No need to install Gradle separately
- ✅ Consistent builds across all environments
- ✅ Automatically downloads the correct Gradle version

## Essential Commands

Always use `./gradlew` (Linux/Mac) or `gradlew.bat` (Windows) instead of `gradle`:

### Basic Tasks

| Command | Description |
|---------|-------------|
| `./gradlew build` | Compile code, run tests, create JARs |
| `./gradlew test` | Run all tests |
| `./gradlew clean` | Delete build outputs |
| `./gradlew assemble` | Compile and package (skip tests) |

### Development Tasks

| Command | Description |
|---------|-------------|
| `./gradlew test --continuous` | Automatically re-run tests when files change |
| `./gradlew build --scan` | Generate build scan with detailed info |
| `./gradlew dependencies` | Show all project dependencies |
| `./gradlew tasks` | List all available tasks |

### Multi-Module Commands

Since this is a multi-module project, you can run tasks on specific modules:

| Command | Description |
|---------|-------------|
| `./gradlew :examples:modern-java:test` | Run tests for specific module |
| `./gradlew :assignments:assignment-01-tdd:build` | Build specific assignment |
| `./gradlew examples:build` | Build all example modules |

## Project Structure

### Root Files
- `build.gradle.kts` - Root build configuration
- `settings.gradle.kts` - Multi-module setup
- `gradlew` / `gradlew.bat` - Wrapper scripts
- `gradle/wrapper/` - Wrapper configuration

### Module Structure
Each module (examples, assignments, live-coding) has:
```
module-name/
├── build.gradle.kts    # Module-specific config
├── src/
│   ├── main/java/     # Production code
│   └── test/java/     # Test code
└── build/             # Generated files (gitignored)
```

## Common Workflows

### Working on an Assignment
```bash
# Navigate to project root
cd software-design-course

# Build everything to ensure setup is correct
./gradlew build

# Work on specific assignment
cd assignments/assignment-01-tdd

# Run tests for this module only
../../gradlew :assignments:assignment-01-tdd:test

# Or from root directory
./gradlew :assignments:assignment-01-tdd:test
```

### Continuous Testing
```bash
# Auto-run tests when files change
./gradlew test --continuous

# Or for specific module
./gradlew :examples:modern-java:test --continuous
```

### Checking Your Work
```bash
# Before committing, always run
./gradlew build

# This will:
# 1. Compile all code
# 2. Run all tests
# 3. Generate build artifacts
# 4. Fail if anything is broken
```

## Build Configuration

### Root `build.gradle.kts`
- Configures Java 21 for all modules
- Sets up common dependencies (JUnit 5, AssertJ)
- Defines shared build logic

### Module `build.gradle.kts`
- Adds module-specific dependencies
- Can override inherited configuration if needed

## Troubleshooting

### Common Issues

**"Permission denied" on `./gradlew`**
```bash
chmod +x gradlew
```

**"Could not find or load main class"**
- Run `./gradlew clean build`
- Check Java version: `java -version` (should be 21)

**Tests not running**
- Ensure test files end with `Test.java`
- Check they're in `src/test/java/` directory
- Verify test methods are annotated with `@Test`

**Dependency not found**
- Run `./gradlew build --refresh-dependencies`
- Check internet connection (Gradle downloads from Maven Central)

### Getting Help

**Verbose output:**
```bash
./gradlew build --info    # More detailed output
./gradlew build --debug   # Full debug information
```

**Build scan:**
```bash
./gradlew build --scan
# Provides web-based build analysis
```

## IntelliJ IDEA Integration

### Importing Project
1. Open IntelliJ IDEA
2. **File → Open** → Select project root folder
3. IDEA automatically detects Gradle and imports modules
4. Wait for indexing to complete

### Running Tasks in IDEA
- **View → Tool Windows → Gradle**
- Expand project → Tasks → verification → test
- Double-click to run

### Gradle Settings in IDEA
- **File → Settings → Build → Gradle**
- **Use Gradle from:** 'gradle-wrapper.properties' file (recommended)
- **Build and run using:** Gradle (recommended)

## Best Practices

### Always Use the Wrapper
✅ `./gradlew build`  
❌ `gradle build`

### Run Clean Builds
```bash
./gradlew clean build
```

### Check Before Committing
```bash
./gradlew build
git add .
git commit -m "Your message"
```

### Module-Specific Work
When working on one module, you can run tasks for just that module to save time:
```bash
./gradlew :examples:modern-java:test
```

## Additional Resources

- [Gradle User Manual](https://docs.gradle.org/current/userguide/userguide.html)
- [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
- [Multi-Project Builds](https://docs.gradle.org/current/userguide/multi_project_builds.html)

Remember: When in doubt, `./gradlew tasks` shows all available tasks for your project!