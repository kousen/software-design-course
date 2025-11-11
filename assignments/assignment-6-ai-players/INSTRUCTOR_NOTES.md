# Assignment 6: Instructor Notes

## Overview

This assignment extends Assignment 5 by adding AI-powered players using Spring AI. It demonstrates how design patterns enable system extension without modification (Open-Closed Principle).

## Learning Objectives

1. **Design Patterns as Enablers**: Shows how Strategy pattern from Assignment 5 makes this extension trivial
2. **Spring AI Integration**: Practical experience with enterprise AI framework
3. **Prompt Engineering**: Critical skill for working with LLMs
4. **Comparative Analysis**: Students observe different LLM behaviors

## Assignment Structure

### TODOs Breakdown (100 points)

| TODO | Points | File | Difficulty | Time Estimate |
|------|--------|------|------------|---------------|
| 1. Build Prompt | 10 | LLMPlayer.java | Medium | 1-2 hours |
| 2. Call LLM | 5 | LLMPlayer.java | Easy | 30 min |
| 3. Parse Response | 10 | LLMPlayer.java | Medium | 1-2 hours |
| 4. Game Loop | 15 | GameController.java | Medium | 2-3 hours |
| 5. Process Turn | 10 | GameController.java | Easy | 1 hour |
| 6. Team Config | 15 | GameApplication.java | Easy | 1 hour |
| Code Quality | 10 | All | - | Throughout |
| Testing | 10 | Test files | Medium | 2 hours |
| Documentation | 10 | README | Easy | 1 hour |
| Demo | 5 | - | - | 30 min |

**Total Estimated Time**: 10-15 hours

## Design Patterns Demonstrated

### From Assignment 5 (Reused)
- **Strategy**: Attack/Defense strategies, now also Player implementations
- **Command**: Game actions (attack, heal)
- **Factory**: Character creation
- **Builder**: Complex object construction
- **Template Method**: Battle sequences

### New Patterns (Assignment 6)
- **Adapter**: LLMPlayer adapts text to GameCommand
- **Facade**: GameController simplifies game loop
- **Mediator**: GameController coordinates components

## API Keys & Cost Considerations

### Pricing (2025 - Current Models)

**OpenAI GPT-5:**
- New accounts typically get credits
- Pricing varies by usage tier
- Estimated cost per game: ~$0.02-0.08
- More capable than GPT-4, comparable pricing

**Anthropic Claude Sonnet 4.5:**
- Free tier available for some accounts
- More cost-effective than Claude 3.5
- Estimated cost per game: ~$0.03-0.10
- Excellent performance for tactical reasoning

**Google Gemini 2.5 Pro:**
- Generous free tier with good rate limits
- Competitive pricing on paid tier
- Estimated cost per game: ~$0.01-0.05
- Best value for testing and development

### Cost Management

**For Course:**
1. **Instructor Provided Keys**: Recommend providing API keys with rate limits
2. **Budget Caps**: Set maximum spending per key
3. **Key Rotation**: Rotate keys weekly
4. **Free Tier Priority**: Start with Gemini (free)

**For Students:**
1. **Gemini 2.5 Pro First**: Test with Gemini's generous free tier before paid APIs
2. **Short Games**: Test with 2v2 instead of 3v3
3. **Limit Tokens**: Use max_tokens=500 setting (already configured)
4. **Mock Testing**: Mock LLM responses for development
5. **Model Comparison**: All three models now have excellent capabilities for this task

## Implementation Notes

### Critical Design Decisions

**1. Player as Strategy**
The key insight is that `Player` is just another Strategy pattern. Students see:
- Attack strategies (Assignment 5)
- Defense strategies (Assignment 5)
- Player strategies (Assignment 6)

All use the same pattern, just at different abstraction levels.

**2. Adapter Pattern for LLM**
`LLMPlayer` demonstrates the Adapter pattern:
- **Adaptee**: LLM (returns text)
- **Target**: Player interface (returns GameCommand)
- **Adapter**: LLMPlayer (converts text → GameCommand)

**3. Error Handling**
LLMs are unreliable. The code teaches robust error handling:
- Malformed JSON → fallback action
- API failures → switch to RuleBasedAI
- Invalid targets → default to first valid option

### Grading Considerations

**Prompt Quality (TODO 1)**
Look for:
- ✅ All character information included
- ✅ Clear role definition
- ✅ Strategic guidance provided
- ✅ JSON format specified
- ✅ Health percentages (not just raw numbers)

**Error Handling (TODO 3)**
Look for:
- ✅ Try-catch for JSON parsing
- ✅ Validation of action/target
- ✅ Fallback for invalid responses
- ✅ Logging of errors

**Game Flow (TODOs 4-5)**
Look for:
- ✅ Correct turn alternation
- ✅ Win condition checking
- ✅ Skipping defeated characters
- ✅ State updates

## Testing Strategy

### Unit Tests
Provided in starter code:
- `LLMPlayerTest`: Mock LLM responses
- `GameControllerTest`: Game flow logic
- `RuleBasedPlayerTest`: AI logic

Students should add:
- Prompt generation tests
- Response parsing tests
- Error handling tests

### Integration Tests
- Full game with mock LLMs
- Verifies end-to-end flow
- No API costs

### Manual Testing
- Start with Human vs RuleBasedAI (no LLMs)
- Add one LLM at a time
- Test error scenarios (invalid JSON, API failures)

## Common Student Issues

### Issue 1: API Keys
**Problem**: "API key not working"
**Causes**:
- Not exported in terminal
- Wrong key name
- Expired/invalid key

**Solution**: Check environment variables in application

### Issue 2: Malformed JSON
**Problem**: "LLM not returning valid JSON"
**Causes**:
- Weak format specification in prompt
- Model doesn't support JSON mode
- Incorrect parsing

**Solution**: Improve prompt, add examples, robust parsing

### Issue 3: Game Hangs
**Problem**: "Game freezes on AI turn"
**Causes**:
- API timeout
- Network issues
- Rate limiting

**Solution**: Add timeouts, implement fallback

### Issue 4: Poor AI Decisions
**Problem**: "AI makes poor strategic moves"
**Causes**:
- Incomplete prompt information
- Missing strategic guidance
- Wrong temperature setting
- Model not seeing full game context

**Solution**:
- Enhance prompt with more context
- Lower temperature (0.5-0.7 is good for tactical decisions)
- Include health percentages, not just raw numbers
- Add strategic guidance based on character role

**Note**: GPT-5, Claude Sonnet 4.5, and Gemini 2.5 Pro are all very capable at this task when given good prompts.

## Prompt Engineering Teaching Points

### Good vs Bad Prompts

**Bad Prompt:**
```
You are a game character. Choose an action. Return JSON.
```

**Good Prompt:**
```
You are Conan, a Warrior with 75/150 HP (50%, wounded).

YOUR TEAM:
- Conan (WARRIOR): 75/150 HP (YOU)
- Gandalf (MAGE): 20/80 HP (25%, CRITICAL!)

ENEMIES:
- Archer: 50/100 HP (50%, wounded)
- Rogue: 90/90 HP (100%, full health)

STRATEGIC SITUATION:
Your mage ally is in danger. Enemy archer is low on health.

OPTIONS:
1. attack "Archer" - Deal ~48 damage, likely kill
2. attack "Rogue" - Deal ~48 damage to healthy enemy
3. heal "Gandalf" - Restore 30 HP, save ally

RECOMMENDED: Finish the archer (focus fire) OR save the mage.

Respond with JSON:
{
  "action": "attack" | "heal",
  "target": "character name exactly as shown",
  "reasoning": "tactical explanation"
}
```

### What Makes It Good?
1. **Identity**: "You are Conan, a Warrior"
2. **Context**: Health percentages, status labels
3. **Options**: Explicit choices with outcomes
4. **Guidance**: Strategic hints
5. **Format**: Exact JSON structure
6. **Examples**: Shows valid target names

## Extension Ideas

### For Advanced Students

**1. Chain-of-Thought Reasoning**
Ask LLM to show reasoning steps:
```json
{
  "analysis": {
    "threats": ["Archer low HP", "Rogue full HP"],
    "priorities": ["Finish archer", "Protect mage"],
    "risks": ["Mage might die", "Rogue still strong"]
  },
  "decision": {
    "action": "attack",
    "target": "Archer",
    "reasoning": "Eliminating archer reduces enemy damage"
  }
}
```

**2. Multi-Agent Collaboration**
LLMs discuss strategy before deciding:
- Warrior LLM: "I'll tank"
- Mage LLM: "I'll support"
- Coordination logic

**3. Learning from History**
Include previous turns in prompt:
```
HISTORY:
Turn 1: You attacked Archer (dealt 48 damage)
Turn 2: Archer healed (recovered 30 HP)
Turn 3: You attacked Archer again (dealt 48 damage, Archer defeated)

Learn from this: Focus fire works!
```

**4. Difficulty Levels**
Adjust prompt quality:
- Easy: Detailed prompts with hints
- Hard: Minimal prompts, no guidance

**5. Tournament Mode**
- Track win rates by model
- Statistical analysis
- Cost comparison

## Troubleshooting

### Gradle Build Issues

**Issue**: Spring AI dependencies not found
**Solution**: Ensure milestone repo is added:
```kotlin
repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}
```

### Spring Boot Issues

**Issue**: ChatClient beans not created
**Solution**: Check `@Configuration` and `@SpringBootApplication` annotations

### LLM Issues

**Issue**: Different behavior across models
**Expected**: This is normal and interesting! Each model has different:
- Training data and capabilities
- Instruction following styles
- Strategic reasoning approaches
- Risk tolerance and creativity

**Current Generation (2025):**
- **GPT-5**: Excellent multi-step planning, strong instruction following
- **Claude Sonnet 4.5**: Very reliable format adherence, balanced strategy
- **Gemini 2.5 Pro**: Creative solutions, good pattern recognition

All three models are now highly capable for this task. Document these differences in observations as part of the learning experience.

## Assessment Rubric Details

### Code Quality (10 points)
- **10**: Clean, well-documented, no duplication
- **8**: Minor style issues, good logic
- **6**: Some code smell, works correctly
- **4**: Messy but functional
- **0**: Doesn't compile

### Testing (10 points)
- **10**: Comprehensive unit + integration tests
- **8**: Good unit test coverage
- **6**: Basic tests present
- **4**: Minimal testing
- **0**: No tests

### Documentation (10 points)
- **10**: Excellent observations, analysis, reflections
- **8**: Good documentation of process
- **6**: Basic README present
- **4**: Minimal documentation
- **0**: No documentation

### Demo (5 points)
- **5**: Runs perfectly, all features work
- **4**: Runs with minor issues
- **3**: Runs but some features broken
- **2**: Partially runs
- **0**: Doesn't run

## Time Allocation

Recommended schedule for 2-3 week assignment:

**Week 1:**
- Day 1-2: Setup, run Assignment 5 code
- Day 3-4: Implement TODOs 4-6 (game loop, no LLM)
- Day 5: Test with Human vs RuleBasedAI

**Week 2:**
- Day 1-2: Implement TODO 1 (prompt building)
- Day 3: Implement TODOs 2-3 (LLM call and parsing)
- Day 4-5: Test with one LLM, debug issues

**Week 3:**
- Day 1-2: Add all three LLMs
- Day 3: Write tests
- Day 4: Document observations
- Day 5: Final testing, submission

## Pedagogical Value

This assignment teaches:

1. **Design Patterns in Practice**: Shows Open-Closed Principle in action
2. **AI Integration**: Real-world Spring AI usage
3. **Prompt Engineering**: Critical modern skill
4. **Error Handling**: Dealing with unreliable external systems
5. **Comparative Analysis**: Scientific approach to AI evaluation

Students leave understanding:
- How patterns enable extension
- How to integrate AI into Java apps
- How to work with LLMs effectively
- How different models behave

## Success Criteria

Students should be able to:
1. ✅ Explain how Strategy pattern enabled the extension
2. ✅ Integrate Spring AI with multiple providers
3. ✅ Write effective prompts for structured output
4. ✅ Handle errors gracefully
5. ✅ Compare AI model behaviors

## Resources for Instructor

- Spring AI Reference: https://docs.spring.io/spring-ai/reference/
- Prompt Engineering Guide: https://www.promptingguide.ai/
- Assignment 5 (prerequisite): Design patterns foundation

---

**Last Updated**: November 2025
**Author**: Prof. Kenneth Kousen
**Course**: CPSC 310 - Software Design
