# Week 12: Spring AI Integration

## Overview

This presentation introduces Spring AI 1.1.0 and demonstrates how to build AI-powered applications using the ChatClient API. The slides are designed to support Assignment 6, where students integrate OpenAI and Anthropic LLMs into their RPG game.

## Topics Covered

### Session 22 (Tuesday)
1. **Spring AI Introduction**
   - What is Spring AI?
   - Architecture and abstractions
   - Why use Spring AI vs direct API calls?

2. **ChatClient API Fundamentals**
   - Fluent API design
   - Basic usage patterns
   - Streaming responses
   - Accessing metadata

3. **LLM Provider Configuration**
   - OpenAI setup (GPT-4o)
   - Anthropic setup (Claude Sonnet 4.5)
   - Environment variables
   - Multiple ChatClient beans

4. **Practical Examples**
   - Simple chat interactions
   - JSON response parsing
   - Error handling
   - Cost considerations

### Session 23 (Thursday)
5. **Assignment 6 Walkthrough**
   - TODO 1: Building effective prompts
   - TODO 2: Calling the LLM
   - TODO 3: Parsing responses to GameCommands
   - Team configuration

6. **Advanced Topics**
   - Prompt engineering best practices
   - Security considerations
   - Testing strategies
   - Observability

## Key Learning Objectives

By the end of this week, students will:
- Understand Spring AI's abstraction layer
- Configure multiple LLM providers
- Use ChatClient fluent API effectively
- Implement prompt engineering strategies
- Handle errors and edge cases
- Complete Assignment 6 successfully

## Running the Slides

```bash
cd slides/week-12-spring-ai
npm install
npm run dev
```

Then open http://localhost:3030

## Export to PDF

```bash
npm run export
```

## Assignment Connection

These slides directly support **Assignment 6: AI-Powered Game Players**, which requires:
- Spring AI 1.1.0
- OpenAI integration (GPT-4o or GPT-5)
- Anthropic integration (Claude Sonnet 4.5)
- ChatClient API usage
- Prompt engineering
- Error handling

## Additional Resources

- [Spring AI Reference Documentation](https://docs.spring.io/spring-ai/reference/)
- [ChatClient API Guide](https://docs.spring.io/spring-ai/reference/api/chatclient.html)
- [OpenAI Platform Documentation](https://platform.openai.com/docs)
- [Anthropic Claude Documentation](https://docs.anthropic.com/)
- Assignment 6 README (complete implementation guide)

## Live Coding Demos

Suggested demos for Session 23:
1. Simple ChatClient call
2. Streaming response visualization
3. JSON parsing and validation
4. Error handling with fallback
5. Building the LLMPlayer prompt
6. Full game with AI players

## Notes for Instructor

### Slide Count: 35 slides
Each slide respects the 25-line limit and 15-line code block limit.

### Key Teaching Points

1. **Spring AI Philosophy** (Slides 3-5)
   - Emphasize abstraction benefits
   - Show how easy it is to switch providers
   - Compare to direct API usage

2. **ChatClient Fluent API** (Slides 7-11)
   - Demonstrate method chaining
   - Show both sync and streaming
   - Explain when to use each

3. **Configuration** (Slides 6-7)
   - Stress environment variable usage
   - Never commit API keys!
   - Show IntelliJ setup

4. **Prompt Engineering** (Slide 16)
   - Good vs bad prompts
   - Importance of structure
   - JSON format specification

5. **Error Handling** (Slide 18)
   - LLMs are unreliable
   - Always have fallbacks
   - Log for debugging

6. **Assignment 6 TODOs** (Slides 19-23)
   - Walk through each TODO
   - Show complete code examples
   - Explain design patterns used

7. **Security** (Slides 24-25)
   - API key management
   - Prompt injection awareness
   - Cost control

### Common Student Questions

**Q: Which model should I use?**
A: Both GPT-4o and Claude Sonnet 4.5 work well. Claude tends to follow JSON formats more consistently, but GPT has slightly lower costs.

**Q: My API key isn't working**
A: Check environment variables are set correctly. In IntelliJ, verify Run Configuration has them set.

**Q: The LLM returns malformed JSON**
A: This is normal! Always validate and have fallback logic. Improve your prompt with examples.

**Q: How much will this cost?**
A: Typically under $1 total for all testing. Both providers offer credits for new accounts.

**Q: Can I use Ollama instead?**
A: Yes, but performance may vary. The assignment requires at least one commercial provider (OpenAI or Anthropic).

### Timing Guide

**Session 22 (75 minutes):**
- Introduction (10 min) - Slides 1-5
- ChatClient basics (15 min) - Slides 7-11
- Configuration (10 min) - Slides 6-8
- Practical examples (20 min) - Slides 12-17
- Error handling (10 min) - Slide 18
- Q&A (10 min)

**Session 23 (75 minutes):**
- Assignment 6 overview (10 min) - Slide 19
- TODO 1-3 walkthrough (30 min) - Slides 20-22
- Live coding demo (20 min)
- Security & testing (10 min) - Slides 24-26
- Q&A (5 min)

### Related Course Materials

- Week 5: AI Code Evaluation (prompt engineering basics)
- Week 8: Design Patterns (Strategy, Adapter, Facade used in Assignment 6)
- Week 9: Structural Patterns (Adapter pattern for LLMPlayer)
- Week 10: Behavioral Patterns (Strategy pattern for Player interface)

## Updates Since Creation

- **2025-11-18**: Initial creation for Week 12
- Spring AI 1.1.0 (released November 2025)
- Assignment 6 integration complete

## Student Feedback Improvements

(To be added after delivery)

---

**Last Updated:** November 18, 2025
**Author:** Ken Kousen
**Course:** CPSC 310 - Software Design, Fall 2025
