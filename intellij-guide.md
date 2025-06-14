# IntelliJ IDEA Guide for CPSC 310

This guide covers IntelliJ IDEA basics for students new to the IDE. IDEA is a powerful Java development environment that will make your coding much more efficient.

## Getting Started

### Installation
1. Go to [JetBrains Student Pack](https://www.jetbrains.com/academy/student-pack/)
2. Apply for the student pack with your Trinity College email
3. Download **IntelliJ IDEA Ultimate** (not Community - Ultimate has more features)
4. Install and activate with your student license

### First Launch
1. **Import Settings:** Choose "Do not import settings" for first time
2. **UI Theme:** Choose Light or Dark (preference)
3. **Featured Plugins:** Accept defaults for now
4. **Project:** Select "Open" to open our course repository

## Opening the Course Project

### Method 1: From Welcome Screen
1. Click **Open**
2. Navigate to your cloned repository folder
3. Select the root folder (contains `build.gradle.kts`)
4. Click **OK**

### Method 2: From Menu
1. **File → Open**
2. Select project root folder
3. **Open as Project**

### Initial Setup
- IDEA will detect Gradle and start importing
- **Trust the Project** when prompted
- Wait for indexing to complete (progress bar at bottom)
- Ensure **Project SDK** is set to Java 21

## Essential Interface Elements

### Project Tool Window (Left Side)
- **Project View:** Shows all files in a tree structure
- **Packages View:** Organizes by Java packages
- Right-click files/folders for context menus

### Editor (Center)
- Tabbed interface for open files
- Syntax highlighting and error detection
- Line numbers on the left

### Tool Windows (Bottom/Sides)
- **Terminal:** Built-in command line
- **Version Control:** Git operations
- **Gradle:** Build tasks and dependencies
- **Run/Debug:** Output from running programs

## Basic Navigation

### Opening Files
| Action                | Shortcut                            |
|-----------------------|-------------------------------------|
| Open any file quickly | `Ctrl+Shift+N` (Cmd+Shift+O on Mac) |
| Open any class        | `Ctrl+N` (Cmd+O on Mac)             |
| Recent files          | `Ctrl+E` (Cmd+E on Mac)             |
| Switch between tabs   | `Ctrl+Tab`                          |

### Code Navigation
| Action               | Shortcut                        |
|----------------------|---------------------------------|
| Go to declaration    | `Ctrl+B` (Cmd+B on Mac)         |
| Go to implementation | `Ctrl+Alt+B` (Cmd+Alt+B on Mac) |
| Find usages          | `Alt+F7`                        |
| Back/Forward         | `Ctrl+Alt+Left/Right`           |

## Writing Code

### Code Completion
- Type and IDEA suggests completions
- Press `Tab` or `Enter` to accept
- `Ctrl+Space` for manual completion
- `Ctrl+Shift+Space` for smart completion

### Live Templates
| Template | Expands to                               |
|----------|------------------------------------------|
| `psvm`   | `public static void main(String[] args)` |
| `sout`   | `System.out.println()`                   |
| `fori`   | for loop with index                      |
| `iter`   | enhanced for loop                        |

### Code Generation
| Action           | Shortcut                  |
|------------------|---------------------------|
| Generate code    | `Alt+Insert`              |
| Override methods | Select from generate menu |
| Getters/Setters  | Select from generate menu |
| Constructor      | Select from generate menu |

### Refactoring
| Action           | Shortcut           |
|------------------|--------------------|
| Rename           | `Shift+F6`         |
| Extract method   | `Ctrl+Alt+M`       |
| Extract variable | `Ctrl+Alt+V`       |
| Refactor this    | `Ctrl+Alt+Shift+T` |

## Running and Testing

### Running Code
1. **Run main method:** Click green arrow next to `main()` method
2. **Run class:** Right-click class file → Run
3. **Run configuration:** Top toolbar dropdown

### Running Tests
1. **Single test:** Click green arrow next to `@Test` method
2. **Test class:** Click green arrow next to class name
3. **All tests:** Right-click `test` folder → Run All Tests
4. **Gradle tests:** Use Gradle tool window

### Debugging
1. Set breakpoints by clicking in the gutter (left of line numbers)
2. **Debug instead of Run:** Click debug icon (bug symbol)
3. **Debug controls:** Step over, step into, step out
4. **Variables view:** See current values

## Git Integration

### Version Control Tool Window
- **Local Changes:** See modified files
- **Log:** View commit history
- **Branches:** Switch branches

### Common Git Operations
| Action         | How to                                   |
|----------------|------------------------------------------|
| Commit changes | `Ctrl+K` (Cmd+K on Mac)                  |
| Update project | `Ctrl+T` (Cmd+T on Mac)                  |
| Push commits   | `Ctrl+Shift+K` (Cmd+Shift+K on Mac)      |
| View diff      | Right-click file → Git → Compare with... |

### GitHub Integration
1. **File → Settings → Version Control → GitHub**
2. Add your GitHub account
3. Enables easy clone/push operations

## Working with Our Course Project

### Multi-Module Navigation
- Expand modules in Project view: `examples`, `assignments`, `live-coding`
- Each module has its own `src/main/java` and `src/test/java`

### Running Gradle Tasks
1. Open **Gradle** tool window (right side)
2. Expand project → Tasks → verification
3. Double-click `test` to run all tests
4. Or use terminal: `./gradlew test`

### Working on Assignments
1. Navigate to `assignments/assignment-XX`
2. Open relevant files in `src/main/java`
3. Implement required methods
4. Run tests to verify: right-click test class → Run

## Essential Settings

### Code Style
- **File → Settings → Editor → Code Style → Java**
- Accept defaults (follows Java conventions)

### Auto Import
- **File → Settings → Editor → General → Auto Import**
- Check "Add unambiguous imports on the fly"
- Check "Optimize imports on the fly"

### Line Numbers
- **File → Settings → Editor → General → Appearance**
- Check "Show line numbers"

## Useful Features for This Course

### TODO Comments
```java
// TODO: Implement this method
public void someMethod() {
    throw new UnsupportedOperationException("Not implemented yet");
}
```
- View all TODOs: **View → Tool Windows → TODO**

### Code Analysis
- Red squiggles: Syntax errors
- Yellow squiggles: Warnings
- Hover over them for details
- `Alt+Enter` for quick fixes

### Testing Features
- Green/red indicators show test pass/fail
- Click failed tests to jump to assertion
- Coverage: Run with Coverage to see which lines are tested

## Keyboard Shortcuts Summary

### Most Important
| Action            | Windows/Linux | Mac           |
|-------------------|---------------|---------------|
| Find anything     | `Shift+Shift` | `Shift+Shift` |
| Run               | `Shift+F10`   | `Ctrl+R`      |
| Debug             | `Shift+F9`    | `Ctrl+D`      |
| Find in file      | `Ctrl+F`      | `Cmd+F`       |
| Replace           | `Ctrl+R`      | `Cmd+R`       |
| Comment/uncomment | `Ctrl+/`      | `Cmd+/`       |
| Format code       | `Ctrl+Alt+L`  | `Cmd+Alt+L`   |

### Learning More
- **Help → Keymap Reference** for complete shortcut list
- **Help → Productivity Guide** shows your usage stats

## Troubleshooting

### Project Won't Import
1. **File → Invalidate Caches and Restart**
2. Check Java SDK: **File → Project Structure → Project**
3. Ensure Gradle wrapper is executable: `chmod +x gradlew`

### Tests Won't Run
1. Check module structure: `src/test/java` exists
2. Test classes end with `Test.java`
3. Methods annotated with `@Test`
4. JUnit 5 dependency in `build.gradle.kts`

### Code Completion Not Working
1. Wait for indexing to complete
2. **File → Invalidate Caches and Restart**
3. Check Project SDK settings

### Git Not Working
1. **File → Settings → Version Control → Git**
2. Test Git executable path
3. Ensure repository is properly cloned

## Tips for Success

### Organization
- Use the **Favorites** feature for frequently accessed files
- Pin important tabs to keep them open
- Use **Bookmarks** (`F11`) to mark important lines

### Productivity
- Learn a few shortcuts each week
- Use **Find Action** (`Ctrl+Shift+A`) when you don't know a shortcut
- Enable **IdeaVim** plugin if you're familiar with Vim

### Code Quality
- Pay attention to yellow/red squiggles
- Use **Code → Reformat Code** before committing
- Run **Code → Inspect Code** to find potential issues

## Getting Help

- **Help → Find Action**—Search for any IDE function
- **Help → IntelliJ IDEA Help**—Complete documentation
- **View → Quick Documentation** (`Ctrl+Q`) for Java API docs
- Ask in office hours for IDEA-specific questions

Remember: IntelliJ IDEA is powerful but has a learning curve. Start with basics and gradually learn more features as you need them!