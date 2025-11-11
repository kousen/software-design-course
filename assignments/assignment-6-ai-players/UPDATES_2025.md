# Assignment 6 Updates - 2025 Models

## Summary of Changes

Updated Assignment 6 to use the latest generation of LLM models available in 2025:

### Model Updates

| Provider | Previous | Updated To | Notes |
|----------|----------|------------|-------|
| **OpenAI** | GPT-4o-mini | **GPT-5** | Latest flagship model with improved reasoning |
| **Anthropic** | Claude 3.5 Sonnet | **Claude Sonnet 4.5** | Enhanced strategic reasoning and format adherence |
| **Google** | Gemini 2.0 Flash | **Gemini 2.5 Pro** | More capable Pro-tier model with better reasoning |

### Pricing Updates (2025)

**Cost per game (estimated):**
- GPT-5: ~$0.02-0.08
- Claude Sonnet 4.5: ~$0.03-0.10
- Gemini 2.5 Pro: ~$0.01-0.05

**Recommendation:** Start with Gemini 2.5 Pro (lowest cost, generous free tier), then compare with GPT-5 and Claude Sonnet 4.5.

## Files Updated

### Configuration
- ✅ `src/main/resources/application.yml` - Updated model names
- ✅ `.env.example` - Updated model documentation

### Documentation
- ✅ `README.md` - Updated all model references, pricing information, and expected characteristics
- ✅ `INSTRUCTOR_NOTES.md` - Updated pricing, model capabilities, and teaching notes
- ✅ `src/main/java/edu/trincoll/game/GameApplication.java` - Updated comments and examples

### Model Capabilities (2025)

**GPT-5:**
- Excellent multi-step tactical planning
- Strong instruction following
- More concise than GPT-4
- Great at analyzing complex game states

**Claude Sonnet 4.5:**
- Exceptional format adherence for JSON responses
- Very strong strategic reasoning
- Balanced aggressive/defensive decision-making
- Clear decision explanations

**Gemini 2.5 Pro:**
- Fast responses with strong reasoning
- Creative and sometimes unexpected strategies
- Excellent pattern recognition
- Good long-term planning
- Best value for budget-conscious testing

## Key Improvements

All three current models (2025) are highly capable for this tactical decision-making task:

1. **Better Instruction Following**: All models reliably produce structured JSON output
2. **Improved Reasoning**: More sophisticated tactical analysis
3. **Better Cost/Performance**: Especially Gemini 2.5 Pro's free tier
4. **More Consistent**: Fewer formatting errors and better edge case handling

## Teaching Impact

**Enhanced Learning Opportunities:**
- Students can now observe state-of-the-art AI in tactical gameplay
- More reliable comparison between models (all are now very capable)
- Better demonstrations of prompt engineering effectiveness
- More interesting model behavior differences to analyze

## Testing Notes

When testing with updated models:
1. Start with Gemini 2.5 Pro (free tier, very capable)
2. Compare GPT-5's multi-step planning
3. Observe Claude Sonnet 4.5's format adherence
4. Document strategic differences in decision-making

All three models should now provide excellent tactical reasoning when given well-structured prompts.

---

**Updated:** November 2025
**Models Verified:** GPT-5, Claude Sonnet 4.5, Gemini 2.5 Pro
