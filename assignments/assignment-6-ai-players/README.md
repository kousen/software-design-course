# Assignment 6: AI-Powered Game Players

- **Due:** December 2, 2025 at 11:59 PM
- **Points:** 100
- **Submission:** Via GitHub (one per team)
- **Prerequisites:** Assignment 5 (Design Patterns)
- **Build Requirements:** Gradle 9.1.0, Java 21+
- **GitHub Repository:** https://github.com/kousen/assignment-6-ai-players

## Overview

This assignment extends Assignment 5's design patterns by adding **AI-powered players** using **Spring AI**. You'll integrate Large Language Models (LLMs) as intelligent game players, comparing their decision-making against rule-based AI and human players.

### What Makes This Exciting

You're not just calling an API—you're **architecting AI integration** into an existing system using design patterns. This demonstrates:

1. **How design patterns enable extensibility**: The Strategy pattern from Assignment 5 makes adding AI players trivial
2. **Real-world AI integration**: Using Spring AI framework (same approach as production applications)
3. **Prompt engineering**: Crafting effective prompts for strategic decision-making
4. **Comparative AI analysis**: Observing different LLMs make tactical decisions

### Why These Skills Matter

- **Spring AI** is the standard framework for Java AI applications
- **Prompt engineering** is a critical skill for working with LLMs
- **Design patterns** show their value when requirements change
- **AI integration** is increasingly common in enterprise applications

## Learning Objectives

- Understand how design patterns facilitate system extension
- Integrate Spring AI with OpenAI, Anthropic
- Practice prompt engineering for structured decision-making
- Compare LLM decision-making strategies
- See patterns working together in a complete application

## What You're Building

You're **extending** the Assignment 5 game system with three types of players:

### 1. Human Player
- You control characters via console
- Choose actions: attack or heal
- Select targets from available characters

### 2. Rule-Based AI Player
- Simple if-then decision logic
- Deterministic and predictable
- Baseline for comparison with LLMs

### 3. LLM Player (Your Focus)
- Uses GPT-5 or Claude Sonnet 4.5 for decisions
- Requires prompt engineering
- Demonstrates state-of-the-art AI decision-making

## Design Patterns in Action

This assignment shows how patterns from Assignment 5 enable this extension **without modifying existing code**:

### Strategy Pattern (From Assignment 5)
- **Then**: Different attack/defense algorithms
- **Now**: Different player decision-making algorithms (Human, AI, LLM)
- **Key Insight**: `Player` interface is just another strategy!

### Command Pattern (From Assignment 5)
- **Then**: Encapsulated attack/heal actions
- **Now**: LLMs return commands that work with existing system
- **Key Insight**: AI just needs to choose which command to create

### Factory Pattern (From Assignment 5)
- **Then**: Created characters with stats
- **Now**: Used to create team configurations
- **Key Insight**: Same factories work in new context

### New Patterns

#### Adapter Pattern
- `LLMPlayer` adapts LLM responses (text) to game commands (objects)
- Converts JSON → `GameCommand`

#### Facade Pattern
- `GameController` simplifies complex game loop
- Hides interactions between players, characters, commands

#### Mediator Pattern
- `GameController` coordinates all game components
- Players don't talk directly to each other

## Assignment Structure (6 TODOs - 100 points)

### TODO 1: Build LLM Prompt (10 points)
**File**: `src/main/java/edu/trincoll/game/player/LLMPlayer.java` (method: `buildPrompt`)

Build a prompt that gives the LLM all information needed to make tactical decisions:

**Requirements:**
- Character status (HP, mana, stats, type)
- Allies status with health percentages
- Enemies status with health percentages
- Available actions (attack with damage estimate, heal)
- Strategic guidance
- JSON response format specification

**Good Prompt Structure:**
```
You are {name}, a {type} in tactical RPG combat.

YOUR STATUS:
- HP: {current}/{max} ({percent}%)
- Mana: {current}/{max}
- Attack Power: {atk}, Defense: {def}
- Strategies: {attack_strategy}, {defense_strategy}

YOUR TEAM (allies):
{formatted list with HP percentages}

ENEMIES:
{formatted list with HP percentages}

AVAILABLE ACTIONS:
1. attack <enemy_name> - Estimated damage: ~{damage}
2. heal <ally_name> - Restores 30 HP

TACTICAL GUIDANCE:
- Focus fire: Attack wounded enemies to eliminate threats
- Protect allies: Heal teammates below 30% HP
- Consider your role: {type-specific advice}

Respond ONLY with JSON:
{
  "action": "attack" | "heal",
  "target": "character_name",
  "reasoning": "brief tactical explanation"
}
```

**Hints:**
- Use helper method `formatCharacterList()` (provided)
- Use `estimateDamage()` to calculate expected damage (provided)
- Include health percentages, not just raw numbers
- Clear format specification reduces parsing errors

---

### TODO 2: Call the LLM and Parse Response (15 points)
**File**: `src/main/java/edu/trincoll/game/player/LLMPlayer.java` (method: `decideAction`)

Use Spring AI's `ChatClient` to get a `Decision` object from the LLM. Spring AI will automatically deserialize the JSON response.

**Implementation:**
```java
Decision decision = chatClient.prompt()
    .user(prompt)
    .call()
    .entity(Decision.class);
```

**Key Concepts:**
- `ChatClient` is Spring AI's fluent API
- `.user(prompt)` sets the user message
- `.call()` executes synchronously
- `.entity(Decision.class)` automatically deserializes JSON to the `Decision` record

**Expected JSON from LLM:**
```json
{
  "action": "attack",
  "target": "enemy_warrior",
  "reasoning": "Finish off the wounded enemy to reduce incoming damage"
}
```

**Error Handling:**
- LLM calls can fail (network, rate limits, API errors, invalid JSON)
- Wrap in try-catch
- Fall back to `RuleBasedPlayer` logic on failure

---

### TODO 3: Convert Decision to GameCommand (10 points)
**File**: `src/main/java/edu/trincoll/game/player/LLMPlayer.java` (method: `decideAction`)

Convert the `Decision` object into an appropriate `GameCommand`.

**Implementation Steps:**
1. Validate action and target are not null
2. Find target character in appropriate list (enemies for attack, allies for heal)
3. Create corresponding `GameCommand`:
   - "attack" → `new AttackCommand(self, target)`
   - "heal" → `new HealCommand(target, 30)`

**Example:**
```java
// Validate
if (decision.action() == null || decision.target() == null) {
    // Fall back to default action
}

// Find target
Character target = decision.action().equals("attack")
    ? findCharacterByName(decision.target(), enemies)
    : findCharacterByName(decision.target(), allies);

// Create command
return switch (decision.action().toLowerCase()) {
    case "attack" -> new AttackCommand(self, target);
    case "heal" -> new HealCommand(target, 30);
    default -> defaultAction(self, enemies); // Fallback
};
```

**Error Handling:**
- LLMs don't always follow formats perfectly
- Handle validation failures gracefully
- Provide sensible defaults (attack weakest enemy)

---

### TODO 4: Implement Game Loop (15 points)
**File**: `src/main/java/edu/trincoll/game/controller/GameController.java` (method: `playGame`)

Create the main game loop that alternates between teams until one wins.

**Algorithm:**
```
while not gameOver:
    for each character in team1:
        if character alive:
            processTurn(character, team1, team2)
        if gameOver: break

    if gameOver: break

    for each character in team2:
        if character alive:
            processTurn(character, team2, team1)
        if gameOver: break

    gameState = gameState.nextRound()
    displayRoundSummary()

displayResult()
```

**Key Points:**
- Check `isGameOver()` after each team's turns
- Skip defeated characters (`health <= 0`)
- Update `gameState` each round
- Display clear turn/round information

---

### TODO 5: Process Single Turn (10 points)
**File**: `src/main/java/edu/trincoll/game/controller/GameController.java` (method: `processTurn`)

Execute one character's turn.

**Steps:**
1. Get the `Player` controlling this character from `playerMap`
2. Call `player.decideAction(character, allies, enemies, gameState)`
3. Execute the returned command: `invoker.executeCommand(command)`
4. Display what happened
5. Update game state

**Implementation:**
```java
private void processTurn(Character character,
                        List<Character> allies,
                        List<Character> enemies) {
    if (character.getStats().health() <= 0) {
        return; // Skip defeated characters
    }

    System.out.println("\n" + character.getName() + "'s turn...");

    // Get player
    Player player = playerMap.get(character);

    // Get decision
    GameCommand command = player.decideAction(character, allies, enemies, gameState);

    // Execute
    invoker.executeCommand(command);

    // Display result
    displayActionResult(command);

    // Update state
    gameState = gameState.nextTurn()
        .withUndo(true, invoker.getCommandHistory().size());
}
```

---

### TODO 6: Configure Teams (15 points)
**File**: `src/main/java/edu/trincoll/game/GameApplication.java` (method: `run` and `createTeamConfiguration`)

Set up teams with a mix of player types, ensuring both LLM providers are used.

**Requirements:**
- **Team 1**: At least 1 human player, 1-2 AI players
- **Team 2**: Two LLM players (one OpenAI, one Anthropic)

**Example Configuration:**
```java
// Team 1: Human + RuleBasedAI
Character humanWarrior = CharacterFactory.createWarrior("Conan");
Character aiMage = CharacterFactory.createMage("Gandalf");
List<Character> team1 = List.of(humanWarrior, aiMage);

// Team 2: Two LLM players (GPT-5 and Claude)
Character gptArcher = CharacterFactory.createArcher("Legolas");
Character claudeRogue = CharacterFactory.createRogue("Shadow");
List<Character> team2 = List.of(gptArcher, claudeRogue);

// Map characters to players
Map<Character, Player> playerMap = new HashMap<>();
playerMap.put(humanWarrior, new HumanPlayer());
playerMap.put(aiMage, new RuleBasedPlayer());
playerMap.put(gptArcher, new LLMPlayer(openAiClient, "GPT-5"));
playerMap.put(claudeRogue, new LLMPlayer(anthropicClient, "Claude-Sonnet-4.5"));

// Create and run game
GameController controller = new GameController(team1, team2, playerMap);
controller.playGame();
controller.displayResult();
```

**Testing Different Configurations:**
- Human vs all AI
- All LLM vs all LLM (watch them play each other!)
- Mixed teams

---

## Setup & Environment

### 1. API Keys Required

You'll need API keys for both LLM providers. Spring Boot reads these from **environment variables** (configured in `application.yml`).

#### Setting Environment Variables

**Option 1: Shell Export (Mac/Linux)**
```bash
export OPENAI_API_KEY=sk-...
export ANTHROPIC_API_KEY=sk-ant-...
```

**Option 2: IntelliJ IDEA Run Configuration**
1. Edit Run Configuration
2. Environment Variables section
3. Add: `OPENAI_API_KEY=sk-...;ANTHROPIC_API_KEY=sk-ant-...`

**Option 3: Gradle Command Line**
```bash
OPENAI_API_KEY=sk-... ANTHROPIC_API_KEY=sk-ant-... ./gradlew run
```

**Option 4: System Environment Variables**
- Set permanently in your OS environment variables

#### Getting API Keys

**OpenAI (GPT-5):**
- Sign up: https://platform.openai.com/
- Generate key at: https://platform.openai.com/api-keys
- Model: `gpt-5.1` (latest)

**Anthropic (Claude Sonnet 4.5):**
- Sign up: https://console.anthropic.com/
- Generate key at: https://console.anthropic.com/settings/keys
- Model: `claude-sonnet-4-5` (note the dashes)

**Note:** You can request course API keys from the instructor if needed.

#### Cost Considerations (2025)

**OpenAI GPT-5:**
- Pricing varies by usage tier
- Estimated cost per game: ~$0.02-0.08
- New accounts typically get credits

**Anthropic Claude Sonnet 4.5:**
- More cost-effective than previous versions
- Estimated cost per game: ~$0.03-0.10
- Free tier available for some accounts

**Recommendation:** Both providers offer reasonable pricing for this assignment. Most students will complete the assignment for under $1 total cost across all testing.

### 2. Verify Setup
```bash
./gradlew build
./gradlew test
```

### 3. Run the Game
```bash
# Set environment variables first!
export OPENAI_API_KEY=...
export ANTHROPIC_API_KEY=...

./gradlew run
```

---

## Implementation Strategy

### Recommended Order

1. **Start with TODO 6** (Team Configuration)
   - Set up a simple team with Human + RuleBasedPlayer
   - Get the game loop working without LLMs first
   - Verify the game runs end-to-end

2. **Then TODOs 4-5** (Game Controller)
   - Implement game loop and turn processing
   - Test with Human vs RuleBasedAI (no LLM yet)
   - Debug game flow before adding AI complexity

3. **Then TODOs 1-3** (LLM Integration)
   - Start with simple prompt (TODO 1)
   - Add LLM call (TODO 2)
   - Parse response (TODO 3)
   - Test with one LLM first, then add others

### Testing Strategy

**Phase 1: Manual Testing**
```bash
# Human vs RuleBasedAI (no LLM calls)
./gradlew run
```

**Phase 2: Single LLM**
```bash
# Test just OpenAI first
# Set only OPENAI_API_KEY
./gradlew run
```

**Phase 3: All LLMs**
```bash
# Full configuration
./gradlew run
```

**Phase 4: Watch AIs Battle**
```bash
# Set Team 1 to all LLMs too!
# Watch three models compete
```

---

## What Success Looks Like

When complete, running `./gradlew run` produces:

```
============================================================
AI-POWERED RPG GAME
============================================================

=== Team Setup ===
Team 1:
  - Conan (WARRIOR) - Human controlled
  - Gandalf (MAGE) - RuleBasedAI

Team 2:
  - Legolas (ARCHER) - OpenAI GPT-5
  - Shadow (ROGUE) - Anthropic Claude Sonnet 4.5

============================================================
TURN 1 - ROUND 1
============================================================

Your Team:
  Conan (WARRIOR) (YOU) - HP: 150/150, Mana: 0/0
  Gandalf (MAGE) - HP: 80/80, Mana: 100/100

Enemy Team:
  Legolas (ARCHER) - HP: 100/100, Mana: 20/20
  Shadow (ROGUE) - HP: 90/90, Mana: 30/30

Your turn, Conan!
1. Attack an enemy
2. Heal an ally
Choose action (1-2): 1

Available targets to attack:
1. Legolas (ARCHER) - HP: 100/100
2. Shadow (ROGUE) - HP: 90/90
Choose target (1-2): 2

→ Conan attacks Shadow for 48 damage!
  Shadow: 90 → 42 HP

Gandalf's turn...
[RuleBasedAI] Attacking weakest enemy: Shadow
→ Gandalf attacks Shadow for 55 damage!
  Shadow: 42 → 0 HP (Defeated!)

============================================================
TURN 2 - ROUND 1
============================================================

Legolas's turn...
[GPT-5] Reasoning: Focus fire on the human player to reduce their effectiveness
→ Legolas attacks Conan for 32 damage!
  Conan: 150 → 118 HP

...
```

The game continues until one team is defeated, showing:
- Human decisions (interactive)
- RuleBasedAI decisions (logged logic)
- LLM decisions (with reasoning displayed)

---

## Grading Rubric

| Component                      | Points  | Requirements                                       |
|--------------------------------|---------|----------------------------------------------------|
| **TODO 1: Build Prompt**       | 10      | Comprehensive prompt with all required information |
| **TODO 2: Call LLM**           | 5       | Correct use of ChatClient API                      |
| **TODO 3: Parse Response**     | 10      | Robust JSON parsing with error handling            |
| **TODO 4: Game Loop**          | 15      | Correct turn alternation and win detection         |
| **TODO 5: Process Turn**       | 10      | Proper command execution and state updates         |
| **TODO 6: Team Configuration** | 15      | Both LLM providers used, valid team setup          |
| **Code Quality**               | 10      | Clean code, proper error handling, good naming     |
| **Testing**                    | 10      | Unit tests for key components                      |
| **Documentation**              | 10      | README with observations, prompt analysis          |
| **Demo**                       | 5       | Game runs successfully with all player types       |
| **TOTAL**                      | **100** |                                                    |

### Code Quality Criteria
- Clean, readable code with meaningful names
- Proper error handling (especially for LLM calls)
- No code duplication
- Follows Java conventions
- Good use of Java 21 features

### Testing Requirements
- Unit tests for `LLMPlayer.buildPrompt()`
- Unit tests for game state transitions
- Integration test running full game
- Mock testing for LLM calls

---

## Design Patterns Demonstrated

This assignment reinforces patterns from Assignment 5 and introduces new ones:

### From Assignment 5

| Pattern             | Where Used                                        | Purpose                          |
|---------------------|---------------------------------------------------|----------------------------------|
| **Strategy**        | Attack/Defense strategies, Player implementations | Interchangeable algorithms       |
| **Command**         | AttackCommand, HealCommand                        | Encapsulate actions, enable undo |
| **Factory Method**  | CharacterFactory                                  | Create pre-configured characters |
| **Builder**         | Character.builder()                               | Construct complex objects        |
| **Template Method** | BattleSequence                                    | Define algorithm skeleton        |

### New in Assignment 6

| Pattern      | Where Used     | Purpose                                          |
|--------------|----------------|--------------------------------------------------|
| **Adapter**  | LLMPlayer      | Adapt LLM text responses to GameCommand objects  |
| **Facade**   | GameController | Simplify complex game interactions               |
| **Mediator** | GameController | Coordinate between players, characters, commands |

---

## Prompt Engineering Tips

### What Makes a Good Prompt?

1. **Clear Role Definition**
   - Tell the LLM who it is: "You are a Warrior in an RPG battle"
   - Provides context for decision-making

2. **Complete Information**
   - Current status (HP, mana, stats)
   - Team status (who needs help?)
   - Enemy status (who to target?)
   - Available actions with outcomes

3. **Strategic Guidance**
   - "Focus fire on wounded enemies"
   - "Heal allies below 30% HP"
   - Helps LLM make good tactical decisions

4. **Format Specification**
   - Exact JSON structure expected
   - Reduces parsing errors
   - Use JSON schema if supported by model

5. **Examples (Few-Shot Learning)**
   - Show 1-2 example decisions
   - LLMs learn format better with examples

### Common Pitfalls

❌ **Vague prompts:**
```
You're a character. What do you do? Respond with JSON.
```

✅ **Specific prompts:**
```
You are Conan, a Warrior with 75/150 HP.
Your mage ally has 20/80 HP (critical!).
Enemies: Archer (50/100 HP), Rogue (90/90 HP).
Action: attack or heal.
If heal, prioritize critically wounded allies.
JSON: {"action": "heal", "target": "Gandalf", "reasoning": "Mage critical"}
```

---

## AI Model Comparison

As you test, observe differences between models:

### Expected Characteristics

**GPT-5 (OpenAI):**
- Excellent instruction following and strategic reasoning
- Improved multi-step tactical planning
- More concise than GPT-4
- Strong at analyzing complex game states

**Claude Sonnet 4.5 (Anthropic):**
- Exceptional at following formats and structured output
- Very strong strategic reasoning
- Balanced approach between aggressive and defensive
- Good at explaining decisions clearly

### What to Document

In your README, include observations:
- Which model made the best tactical decisions?
- Which followed the JSON format most consistently?
- Which had the most interesting reasoning?
- Which made surprising moves?
- Did one model seem more aggressive or defensive?
- Cost comparison (tokens used)?

---

## Extending the Assignment (Optional)

Want to go further? Try these:

### 1. Add More Actions
- Defend (reduce incoming damage next turn)
- Special abilities (character-type specific)
- Items (potions, equipment)

### 2. Improve Prompts
- Few-shot learning (show examples)
- Chain-of-thought reasoning
- Multi-turn conversation history

### 3. Tournament Mode
- LLM vs LLM battles
- Track win rates by model
- Statistical analysis

### 4. Visualization
- Add ASCII art battle display
- Health bars
- Turn history

### 5. Streaming Responses
- Use `ChatClient.stream()` instead of `.call()`
- Display LLM reasoning in real-time

---

## Common Issues & Solutions

### Issue: API Key Not Found
```
Error: Missing API key for OpenAI
```
**Solution:** Set environment variable before running:
```bash
export OPENAI_API_KEY=sk-...
./gradlew run
```

### Issue: LLM Returns Malformed JSON
```
Error parsing LLM response: Unexpected token
```
**Solution:**
- Improve prompt format specification
- Add examples to prompt
- Implement robust error handling with fallback

### Issue: Game Hangs on LLM Turn
```
[Waiting for LLM response...]
```
**Solution:**
- Check API key is valid
- Check network connection
- Add timeout to LLM calls
- Implement fallback to RuleBasedAI

### Issue: LLM Makes Illegal Moves
```
Error: Target "wizard" not found
```
**Solution:**
- Validate LLM output
- Use `findCharacterByName()` with fallback
- Default to first valid target on error

---

## Learning Reflection Questions

Include answers in your README:

1. **Pattern Extension:**
   - How did the Strategy pattern from Assignment 5 make adding players easy?
   - What would this look like without design patterns?

2. **Prompt Engineering:**
   - What prompt elements were most important?
   - How did you handle format consistency?

3. **AI Behavior:**
   - Did LLMs make better decisions than rule-based AI?
   - Were there surprising or creative moves?
   - Which model performed best?

4. **Error Handling:**
   - What errors did you encounter?
   - How did you make the system robust?

5. **Architecture:**
   - How does Spring AI simplify LLM integration?
   - What's the benefit of the ChatClient abstraction?

---

## Resources

### Spring AI Documentation
- Spring AI Reference: https://docs.spring.io/spring-ai/reference/
- ChatClient API: https://docs.spring.io/spring-ai/reference/api/chatclient.html

### LLM Provider Docs
- OpenAI API: https://platform.openai.com/docs
- Anthropic Claude: https://docs.anthropic.com/

### Design Patterns
- Assignment 5 patterns
- Course slides on Strategy, Adapter, Facade

---

## Submission Requirements

1. **All TODOs Implemented** - Code compiles and runs
2. **Tests Pass** - `./gradlew test` succeeds
3. **Demo Video** - Record a successful game run (optional but recommended)
4. **README** - Include observations and reflections
5. **API Keys** - Don't commit these! Use environment variables
6. **Clean Commit History** - Meaningful commit messages

### Submit to Moodle
- Repository URL
- Team member names
- Brief summary of observations (which LLM performed best?)
- Any challenges encountered

---

## Questions?

- Office hours: Wednesdays 1:30-3:00 PM
- Email: kkousen@trincoll.edu
- Course discussion board

---

**Remember**: This assignment shows why design patterns matter. The Strategy pattern from Assignment 5 makes this extension possible **without changing a single line of existing code**. That's the power of good design! 🎮🤖
