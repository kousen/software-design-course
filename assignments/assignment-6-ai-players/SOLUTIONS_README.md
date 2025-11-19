# Assignment 6: Solutions Package

This directory contains the **complete reference implementation** for Assignment 6.

## 📁 Package Structure

```
edu.trincoll.solutions/
├── LLMPlayerSolution.java          # Complete TODO 1-3 implementation
├── GameControllerSolution.java     # Complete TODO 4-5 implementation
└── GameApplicationSolution.java    # Complete TODO 6 implementation
```

## 🎯 What's Implemented

### ✅ LLMPlayerSolution (TODOs 1-3)

**TODO 1: Build Prompt** (10 points)
- Comprehensive prompt with all required information
- Clear role definition and status
- Team and enemy information with health percentages
- Available actions with damage estimates
- Strategic guidance based on character type
- JSON format specification with examples

**TODO 2: Call LLM and Parse Response** (15 points)
- Spring AI ChatClient integration
- Proper use of fluent API with automatic deserialization (`.prompt().user().call().entity(Decision.class)`)
- Spring AI automatically handles JSON parsing to Decision record
- Error handling for API failures and invalid JSON

**TODO 3: Convert Decision to GameCommand** (10 points)
- Validation of action and target
- Character name lookup with fallbacks
- Switch expression to create appropriate GameCommand
- Robust error handling
- Default action when validation fails

### ✅ GameControllerSolution (TODOs 4-5)

**TODO 4: Game Loop** (15 points)
- Turn alternation between teams
- Win condition checking after each team's turns
- Round advancement
- Display of game progress
- Skipping defeated characters

**TODO 5: Process Turn** (10 points)
- Player retrieval from map
- Command decision via player
- Command execution through invoker
- Game state updates
- Action result display

### ✅ GameApplicationSolution (TODO 6)

**TODO 6: Team Configuration** (15 points)
- Three game modes:
  1. **Demo Mode**: Human + RuleBasedAI vs 3 LLMs
  2. **All AI Mode**: Watch LLMs battle each other
  3. **Human Mode**: Player controls entire team
- All three LLM models used (GPT-5, Claude Sonnet 4.5, Gemini 2.5 Pro)
- Character-to-player mapping
- Spring Boot integration

## 🚀 Running the Solution

### Option 1: From Command Line

```bash
# Set your API keys
export OPENAI_API_KEY=sk-...
export ANTHROPIC_API_KEY=sk-ant-...
export GOOGLE_CLOUD_PROJECT_ID=your-project-id
export GOOGLE_CLOUD_LOCATION=us-central1

# Run the solution
./gradlew bootRun -Pmain=edu.trincoll.solutions.GameApplicationSolution
```

### Option 2: From IntelliJ IDEA

1. Open `GameApplicationSolution.java`
2. Click the ▶️ icon next to the `main` method
3. Edit Run Configuration → Environment Variables
4. Add your API keys:
   ```
   OPENAI_API_KEY=sk-...
   ANTHROPIC_API_KEY=sk-ant-...
   GOOGLE_CLOUD_PROJECT_ID=your-project
   GOOGLE_CLOUD_LOCATION=us-central1
   ```
5. Run

### Option 3: Gradle Task (Recommended for Demos)

Add this to `build.gradle.kts`:

```kotlin
tasks.register<JavaExec>("runSolution") {
    group = "application"
    description = "Run the solution version"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("edu.trincoll.solutions.GameApplicationSolution")
}
```

Then run:
```bash
./gradlew runSolution
```

## 🎮 Game Modes

### Demo Mode (Default)
**Team 1**: Human + RuleBasedAI
**Team 2**: GPT-5 + Claude Sonnet 4.5 + Gemini 2.5 Pro

Best for demonstrations and testing LLM behavior.

### All AI Mode
**Both Teams**: LLM players only

Watch three different AI models strategize and compete. Great for observing model differences.

### Human Mode
**Team 1**: All human controlled
**Team 2**: All LLM players

Full control for testing strategies against AI opponents.

## 📊 What to Observe

### Prompt Engineering Quality
- How much context improves decisions
- Effect of strategic guidance
- Importance of clear format specification

### Model Behaviors
- **GPT-5**: Multi-step planning, creative strategies
- **Claude Sonnet 4.5**: Reliable format adherence, balanced decisions
- **Gemini 2.5 Pro**: Fast responses, pattern recognition

### Strategic Patterns
- Focus fire (finishing wounded enemies)
- Triage healing (saving critical allies)
- Role-based tactics (warriors tank, mages damage)

## 🔍 Code Quality Highlights

### Design Patterns Used
1. **Strategy Pattern**: `Player` interface with multiple implementations
2. **Adapter Pattern**: `LLMPlayerSolution` adapts text → commands
3. **Facade Pattern**: `GameControllerSolution` simplifies complexity
4. **Mediator Pattern**: Controller coordinates all components
5. **Command Pattern**: Encapsulated, undoable actions

### Error Handling
- Try-catch for all LLM calls
- JSON parsing validation
- Character name fallbacks
- Default actions when failures occur
- Informative error logging

### Code Organization
- Clear separation of concerns
- Helper methods for reusability
- Meaningful variable names
- Comprehensive documentation

## 📝 Teaching Points

### For Students
1. **Compare your implementation** with the solution
2. **Note the prompt structure** - key to good LLM behavior
3. **Observe error handling** - production code must be robust
4. **See patterns working together** - this is the payoff

### For Instructors
1. **Demo all three modes** to show versatility
2. **Run multiple times** to show LLM variability
3. **Highlight prompt differences** and their effects
4. **Discuss trade-offs** (complexity vs. reliability)

## 🧪 Testing Strategy

### Manual Testing
```bash
# Test each mode
./gradlew runSolution
# Choose mode 1, 2, and 3 in separate runs
```

### API Key Validation
```bash
# Test with one provider at a time
export OPENAI_API_KEY=...
# Run and use only GPT player
```

### Error Resilience
- Test with invalid API keys (should fallback gracefully)
- Test with network issues (should use default actions)
- Test with malformed prompts (should still parse or fallback)

## 💡 Extension Ideas

Students can enhance the solution with:

1. **Better Prompts**
   - Few-shot learning with examples
   - Chain-of-thought reasoning
   - Game history in context

2. **More Actions**
   - Defend (reduce damage)
   - Special abilities per character type
   - Item usage

3. **Tournament Mode**
   - Track win rates across models
   - Statistical analysis
   - Best-of-N matches

4. **Visualization**
   - ASCII art battle display
   - Health bars
   - Turn history replay

5. **Streaming Responses**
   - Use `ChatClient.stream()`
   - Display reasoning in real-time

## 🛠️ Troubleshooting

### Issue: Can't find main class
**Solution**: Make sure `@ComponentScan` includes both packages:
```java
@ComponentScan(basePackages = {"edu.trincoll.game", "edu.trincoll.solutions"})
```

### Issue: ChatClient beans not found
**Solution**: Verify `ChatClientConfig` is in component scan path

### Issue: API calls failing
**Solution**:
1. Check environment variables are set
2. Verify API keys are valid
3. Check network connectivity
4. Review API usage limits

### Issue: LLM returns invalid JSON
**Solution**: This is expected occasionally. The code handles it with:
- Try-catch around parsing
- Validation of parsed data
- Fallback to default action

## 📚 Related Files

- `README.md` - Student assignment instructions
- `INSTRUCTOR_NOTES.md` - Teaching guidance
- `UPDATES_2025.md` - Model version updates
- `TEST_SUITE_README.md` - Test documentation

## 🎓 Learning Outcomes Demonstrated

After studying this solution, students should understand:

1. ✅ How to integrate Spring AI with multiple LLM providers
2. ✅ Effective prompt engineering techniques
3. ✅ Robust error handling for external APIs
4. ✅ Design patterns working together in a real system
5. ✅ The value of the Strategy pattern for extensibility
6. ✅ Professional code organization and documentation

## 📞 Support

For questions about the solution:
- Office Hours: Wednesdays 1:30-3:00 PM
- Email: kkousen@trincoll.edu
- Course Discussion Board

---

**Note**: This solution is provided as a reference implementation. Students should attempt all TODOs independently before reviewing this code. The learning comes from struggling with the problems, not from copying solutions.

**License**: MIT (same as main project)
**Last Updated**: November 2025
**Author**: Prof. Kenneth Kousen
