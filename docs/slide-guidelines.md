# Slidev Slide Creation Guidelines

## Critical Rules to Prevent Overflow

These rules were developed after multiple rounds of fixes to slides that ran off the bottom of the browser window when rendered. They are **strict limits**, not suggestions.

## Hard Limits

### Line Count Limits (Enforced)

- **Code blocks**: Maximum **15 lines** per slide
- **Total content**: Maximum **25 lines** per slide (including headers, bullets, code, spacing)
- **If any section exceeds limits**: MUST split into multiple slides immediately

### Structural Rules

- **Maximum 2 code blocks** per slide
- If using `<v-clicks>` with **4+ sections** → split the slide
- Example exercises with **>20 lines of code** → split into "Part 1" and "Part 2" slides

## Enforcement Process

When creating each slide with code:

1. **Count total lines** (headers + bullets + code + spacing)
2. **If > 25 lines**, immediately split before continuing
3. **Never defer splitting** to "later review"

## Why These Rules Exist

Slidev renders in **fixed browser windows**. Content that looks fine in markdown source will run off the bottom when rendered.

These limits have been empirically validated through multiple rounds of overflow fixes in:
- Week 7 Design Patterns slides (required 7+ slide splits)
- Week 6 SOLID Principles slides (required multiple revisions)

## Common Patterns That Cause Overflow

### ❌ Too Much Content

```markdown
# Pattern Name

<v-clicks>

## Section 1
- Point 1
- Point 2
- Point 3

## Section 2
Code block (15 lines)

## Section 3
- Point 1
- Point 2

## Section 4
Another code block (10 lines)

</v-clicks>
```

**Problem**: 4 sections + 2 code blocks + multiple bullets = ~35+ lines

### ✅ Split Appropriately

```markdown
# Pattern Name: Concept

<v-clicks>

## Section 1
- Point 1
- Point 2
- Point 3

## Section 2
- Point 1
- Point 2

</v-clicks>

---

# Pattern Name: Implementation

Code block (15 lines max)

Explanation text
```

**Result**: Two slides, each under 25 lines

## Examples of Good Splits

### Strategy Pattern Example

**Before** (too long):
- "Using the Traditional Strategy" with Context Class + Usage + Benefits = 31 lines

**After** (split):
- Slide 1: "Traditional Strategy: Context Class" = 15 lines
- Slide 2: "Traditional Strategy: Usage & Benefits" = 12 lines

### Exercise Example

**Before** (too long):
- "Exercise: Temperature Converter Strategy" with full problem + solution = 40 lines

**After** (split):
- Slide 1: "Exercise: Temperature Converter Strategy" (problem setup) = 18 lines
- Slide 2: "Exercise: Temperature Converter (continued)" (solution structure) = 16 lines

## Measurement Tips

### Quick Line Count

In your editor:
1. Select the slide content (from `---` to next `---`)
2. Count lines or use line numbers
3. If > 25, split immediately

### What Counts as a Line

- Every line in markdown source counts
- Blank lines count
- `<v-clicks>` opening/closing tags count
- Code fence lines (` ``` `) count
- Header lines count

## When in Doubt

**Split the slide.** It's easier to combine two short slides than to debug overflow issues after the fact.

## Testing

After creating slides, render them in Slidev to verify:
```bash
cd slides/week-XX-topic
npx slidev slides.md
```

Check each slide in the browser at 1920x1080 resolution (typical presentation size).

---

**Last Updated**: 2025-01-20
**Reason**: After fixing 7+ overflow issues in Week 7 Design Patterns slides
