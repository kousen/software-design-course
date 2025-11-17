# Assignment 6: Quick Start Guide

## 🚀 For Instructors: Running the Demo NOW

### 1. Set API Keys (30 seconds)

```bash
export OPENAI_API_KEY=sk-...
export ANTHROPIC_API_KEY=sk-ant-...
export GOOGLE_CLOUD_PROJECT_ID=your-project
export GOOGLE_CLOUD_LOCATION=us-central1
```

### 2. Run the Solution (5 seconds)

```bash
cd assignments/assignment-6-ai-players
./gradlew runSolution
```

### 3. Choose a Mode (Pick one)

- **1**: Demo Mode - You + AI vs 3 LLMs (Interactive)
- **2**: All AI Mode - Watch LLMs battle (Hands-off)
- **3**: Human Mode - You control team 1 (Full control)

**That's it!** The complete working solution runs immediately.

---

## 📁 What's Available

### Solution Code (`src/main/java/edu/trincoll/solutions/`)

| File | Implements | Lines |
|------|------------|-------|
| `LLMPlayerSolution.java` | TODOs 1-3 | 235 |
| `GameControllerSolution.java` | TODOs 4-5 | 235 |
| `GameApplicationSolution.java` | TODO 6 | 195 |

### Documentation

| File | Purpose | When to Use |
|------|---------|-------------|
| `SOLUTIONS_README.md` | Complete usage guide | Running solution, understanding code |
| `DEMO_GUIDE.md` | Teaching scenarios | Before class demos |
| `SOLUTION_SUMMARY.md` | Implementation overview | Quick reference |
| `QUICK_START.md` | This file | Right now! |

---

## 🎯 Common Use Cases

### "I need to demo this in class NOW"

```bash
./gradlew runSolution
# Choose Mode 2 (All AI - no interaction needed)
```

Point out LLM reasoning as it plays.

### "I want to show prompt engineering"

1. Run: `./gradlew runSolution` (Mode 2)
2. Open `src/main/java/edu/trincoll/solutions/LLMPlayerSolution.java`
3. Show `buildPrompt()` method
4. Highlight: role, status, guidance, JSON format

### "I need to verify it works before class"

```bash
./gradlew clean build
./gradlew runSolution
# Test each mode
```

### "Students want to see the solution"

**After assignment deadline**:

Tell them: "Compare your code with `edu.trincoll.solutions` package"

No merge conflicts - different package!

---

## 🎬 5-Minute Demo Script

```bash
./gradlew runSolution
# Choose Mode 2
```

While it plays:

> "Assignment 6 integrates three AI models: GPT-5, Claude, and Gemini.
>
> Watch the reasoning - that's the LLM making tactical decisions.
> Students implement the prompt engineering to get these behaviors.
>
> This extends Assignment 5 without changing existing code.
> That's the Strategy pattern and Open-Closed Principle.
>
> Students implement 6 TODOs covering prompt building, API calls,
> JSON parsing, game loop, turn processing, and team configuration."

Done!

---

## 🐛 Troubleshooting

### "Game hangs on LLM turn"

**Fix**: Check API keys are exported
```bash
echo $OPENAI_API_KEY  # Should show key
```

### "All LLMs use fallback"

**Fix**: Keys not set in current shell
```bash
export OPENAI_API_KEY=...
export ANTHROPIC_API_KEY=...
```

### "Build fails"

**Fix**: Clean and rebuild
```bash
./gradlew clean build
```

### "Can't find main class"

**Fix**: Use `runSolution` not `run`
```bash
./gradlew runSolution  # ✅ Correct
./gradlew run          # ❌ Runs student version (with TODOs)
```

---

## 📊 What to Expect

### Successful Start
```
============================================================
AI-POWERED RPG GAME - SOLUTION VERSION
============================================================
[Shows design patterns]
[Shows player types]
============================================================

Choose game mode:
1. Demo Mode (Human + AI vs 3 LLMs)
2. All AI Mode (Watch LLMs battle each other)
3. Human Mode (You control Team 1)
Select mode (1-3):
```

### During Game
```
Legolas's turn...
[GPT-5] Reasoning: Focus fire on wounded enemy at 45% HP to eliminate threats
→ Legolas attacks Shadow for 48 damage!
  Shadow: 42 → 0 HP (Defeated!)
```

### Game End
```
============================================================
GAME OVER
============================================================
🏆 Team 2 wins!

Total turns played: 24
Total rounds: 4
```

---

## 💡 Pro Tips

1. **Pre-test**: Run once before class to verify API keys work
2. **Mode 2 for demos**: All AI mode needs no interaction
3. **Show the code**: Split screen with game + source
4. **Multiple runs**: Each game is different (LLM variability)
5. **Have backup**: Screen recording in case of network issues

---

## 📞 Need Help?

- **Full guide**: See `SOLUTIONS_README.md`
- **Demo scenarios**: See `DEMO_GUIDE.md`
- **Code details**: See `SOLUTION_SUMMARY.md`
- **Office hours**: Wednesdays 1:30-3:00 PM

---

## ✅ Quick Checklist

Before your demo:

- [ ] API keys exported in terminal
- [ ] Test run: `./gradlew runSolution`
- [ ] Verify each mode works
- [ ] Review `DEMO_GUIDE.md` scenarios
- [ ] Prepare talking points
- [ ] Have backup plan (screen recording)

---

**Remember**: `./gradlew runSolution` runs the complete working version.

**Students work on**: `./gradlew run` (has TODOs - won't work yet)

---

Last Updated: November 2025
