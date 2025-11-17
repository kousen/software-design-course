# Assignment 6: Demo Guide for Instructors

Quick reference for demonstrating the complete working solution.

## 🎬 Quick Demo Setup

### 1. Set API Keys (One-Time Setup)

```bash
export OPENAI_API_KEY=sk-...
export ANTHROPIC_API_KEY=sk-ant-...
export GOOGLE_CLOUD_PROJECT_ID=your-project-id
export GOOGLE_CLOUD_LOCATION=us-central1
```

Or add to `~/.zshrc` or `~/.bashrc` for persistence.

### 2. Run the Demo

```bash
cd assignments/assignment-6-ai-players
./gradlew runSolution
```

### 3. Choose Mode

When prompted:
- **Mode 1 (Demo)**: Best for classroom - Human + AI vs 3 LLMs
- **Mode 2 (All AI)**: Watch LLMs battle - great for showing model differences
- **Mode 3 (Human)**: Full control - good for live coding demos

## 🎓 Teaching Scenarios

### Scenario 1: Introduction to Assignment (5-10 minutes)

**Goal**: Show what students will build

```bash
./gradlew runSolution
# Choose Mode 1 (Demo)
# Play 1-2 turns interactively
# Point out:
# - LLM reasoning displayed
# - Different model behaviors
# - How patterns enable this
```

**Key Points**:
- "This extends Assignment 5 using Strategy pattern"
- "Notice how we didn't change Character or Command code"
- "Each LLM is just another Strategy implementation"
- "Watch the reasoning - that's prompt engineering at work"

### Scenario 2: Prompt Engineering Demo (10-15 minutes)

**Goal**: Show importance of good prompts

**Setup**: Open `LLMPlayerSolution.java` in editor alongside running game

```bash
./gradlew runSolution
# Choose Mode 2 (All AI - watch them play)
```

**During Game**:
1. Pause and show `buildPrompt()` method
2. Point out key sections:
   - Role definition
   - Status with percentages (not raw numbers)
   - Strategic guidance
   - JSON format specification
3. Ask: "What happens if we remove strategic guidance?"
4. Resume watching LLM decisions

**Key Points**:
- "The prompt is everything - garbage in, garbage out"
- "Notice health percentages - easier for LLM to reason about"
- "Strategic tips guide the LLM toward good tactics"
- "JSON format spec reduces parsing errors"

### Scenario 3: Model Comparison (15-20 minutes)

**Goal**: Compare LLM behaviors

```bash
./gradlew runSolution
# Choose Mode 2 (All AI)
# Let it play to completion
```

**Watch For**:
- **GPT-5**: Multi-step planning, focus fire tactics
- **Claude Sonnet 4.5**: Consistent format, balanced decisions
- **Gemini 2.5 Pro**: Creative moves, pattern recognition

**Discussion Questions**:
- "Which model made the most aggressive choices?"
- "Which one healed teammates most effectively?"
- "Did any make surprising moves?"
- "Which reasoning was clearest?"

### Scenario 4: Error Handling Demo (10 minutes)

**Goal**: Show robust code practices

**Deliberately Cause Errors**:

1. **Invalid API Key**:
```bash
OPENAI_API_KEY=invalid ./gradlew runSolution
# Shows fallback to default action
```

2. **Network Issues**:
```bash
# Disconnect Wi-Fi during a turn
# Watch graceful degradation
```

**Show Code**:
Open `LLMPlayerSolution.java` → `decideAction()` method

Point out:
- Try-catch blocks
- Validation of parsed JSON
- Fallback to `defaultAction()`
- Error logging

**Key Points**:
- "Production code must handle failures gracefully"
- "Never trust external APIs to always work"
- "Fallbacks keep the game playable"
- "Logging helps debug issues"

### Scenario 5: Design Patterns in Action (15-20 minutes)

**Goal**: See multiple patterns working together

**Preparation**: Draw this on board/screen:

```
┌─────────────────────────────────────────┐
│         GameController (Facade)         │
│              (Mediator)                 │
└─────────────────────────────────────────┘
              │
      ┌───────┴───────┐
      ▼               ▼
┌──────────┐    ┌──────────┐
│  Player  │    │ Command  │
│(Strategy)│    │(Command) │
└──────────┘    └──────────┘
      │               │
   ┌──┴────┬────┬────┤
   ▼       ▼    ▼    ▼
Human   LLM  Rule  Attack
       (Adapter)   Heal
```

**Run Demo**:
```bash
./gradlew runSolution
# Choose Mode 1
```

**As It Plays, Point Out**:

1. **Strategy Pattern**: Each player type (Human, LLM, RuleBasedAI)
2. **Adapter Pattern**: `LLMPlayer` converts text → `GameCommand`
3. **Command Pattern**: `AttackCommand`, `HealCommand` are undoable
4. **Facade**: `GameController` hides complexity
5. **Mediator**: Controller coordinates all components

**Key Points**:
- "Six patterns working together seamlessly"
- "This is why we learned patterns in Assignment 5"
- "Open-Closed Principle: Extended without modification"

## 🎯 Quick Demo Script (5 minutes)

For a fast overview:

```bash
./gradlew runSolution
# Mode 2 (All AI)
```

**While it plays**:

> "This is Assignment 6. Students integrate three LLM models - GPT-5,
> Claude Sonnet 4.5, and Gemini 2.5 Pro - into the game from Assignment 5.
>
> Notice the reasoning displayed - that's the LLM explaining its tactical
> decisions. Students learn prompt engineering to get these good decisions.
>
> This demonstrates how design patterns enable extension. We added AI players
> without changing any existing Character or Command code. That's the Strategy
> pattern and Open-Closed Principle in action.
>
> Students implement six TODOs: building prompts, calling LLMs, parsing responses,
> game loop, turn processing, and team configuration. It's a complete integration
> of Spring AI into a real application."

## 📊 Expected Outputs

### Good LLM Decision (Example)
```
Legolas's turn...
[GPT-5] Reasoning: Focus fire on Shadow who is critically wounded at 25% HP to eliminate threats
→ Legolas attacks Shadow for 45 damage!
  Shadow: 20 → 0 HP (Defeated!)
```

### Error Handling (Example)
```
Tank's turn...
[Gemini-2.5-Pro] Error: API timeout, using fallback
→ Tank attacks Gandalf for 48 damage!
```

### Game End (Example)
```
============================================================
GAME OVER
============================================================
🏆 Team 2 wins!

Game Statistics:
Total turns played: 24
Total rounds: 4
Total commands executed: 24
```

## 🐛 Common Demo Issues

### Issue: Game hangs on LLM turn
**Cause**: API timeout or no internet
**Fix**:
- Check Wi-Fi connection
- Verify API keys are set
- Check API service status

### Issue: All LLMs using fallback
**Cause**: API keys not exported
**Fix**:
```bash
echo $OPENAI_API_KEY  # Should show key
export OPENAI_API_KEY=sk-...
```

### Issue: Compilation errors
**Cause**: Gradle cache issue
**Fix**:
```bash
./gradlew clean build
```

## 💡 Demo Tips

1. **Pre-test before class**: Run through once to verify API keys work
2. **Have backup**: Keep a screen recording in case live demo fails
3. **Interactive moments**: Let students choose actions in Mode 1
4. **Pause for questions**: After each pattern demonstration
5. **Show the code**: Toggle between running game and source code
6. **Emphasize learning**: "You'll implement this yourself"

## 📝 Follow-Up Activities

After demo:

1. **Code walkthrough**: Show `LLMPlayerSolution` line by line
2. **Prompt editing**: Modify prompt live and re-run
3. **Student predictions**: "What will the LLM do next?"
4. **Compare to Assignment 5**: Show unchanged base classes

## 🎬 Video Recording Tips

If recording for async learning:

1. **Screen layout**: Code on left, terminal on right
2. **Zoom in**: Make text readable
3. **Pause often**: Give time to absorb
4. **Annotate**: Circle/highlight important parts
5. **Chapter markers**: One per TODO implementation
6. **Length**: 15-20 minutes total

## ⏱️ Time Estimates

- Quick overview: 5 minutes
- Prompt engineering focus: 10 minutes
- Design patterns focus: 15 minutes
- Complete walkthrough: 30 minutes
- With Q&A: 45-60 minutes

---

**Remember**: The goal is to excite students about what they'll build, not to give away all the solutions. Show the "what" and "why," let them figure out the "how."

**Pro Tip**: End with "By the end of this assignment, you'll be able to integrate AI into Java applications - a skill employers are actively seeking right now."
