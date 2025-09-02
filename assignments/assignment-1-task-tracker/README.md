# Assignment 1: Task Tracker Evolution

**Due:** Thursday, September 11 at 11:59 PM  
**Points:** 100  
**Submission:** Via GitHub (one per team)

## Overview

During our first class, we built a simple Task API together. Your team's mission is to evolve this starter code into your own unique domain while making all provided tests pass.

## Team Requirements

- Teams of 3 students (one team may have 4)
- All members must contribute via GitHub commits
- Use AI tools to assist development
- Document your AI usage

## Part 1: Choose Your Domain (20 points)

Each team must pick ONE unique domain from:

1. **BookmarkManager** - Save and categorize web links
2. **QuoteKeeper** - Store favorite quotes with authors  
3. **HabitTracker** - Track daily habits and streaks
4. **RecipeBox** - Store cooking recipes with ingredients
5. **MovieWatchlist** - Track movies to watch and ratings

First team to claim a domain gets it (post in Discord/Slack).

## Part 2: Implementation Requirements (60 points)

### Core Features
- Implement all CRUD operations (Create, Read, Update, Delete)
- All 15 provided tests MUST pass
- Rename classes/endpoints to match your domain
- Add at least 3 meaningful fields beyond `id`

### Example Domain Fields
- **Bookmark:** url, title, category, tags, favicon
- **Quote:** text, author, source, category, favorite
- **Habit:** name, frequency, currentStreak, lastCompleted, target
- **Recipe:** name, ingredients, instructions, prepTime, servings
- **Movie:** title, director, year, genre, rating, watched

## Part 3: AI Collaboration Report (10 points)

Add this comment block to the top of your controller:

```java
/**
 * AI Collaboration Report:
 * - AI Tool Used: [ChatGPT/Claude/Copilot/Gemini]
 * - Most Helpful Prompt: [paste your best prompt]
 * - AI Mistake We Fixed: [describe the error and fix]
 * - Time Saved: [estimate hours]
 * - Team Members: [list all names]
 */
```

## Part 4: Code Quality (10 points)

- Clean, readable code
- Proper package/class naming for your domain
- No major SonarCloud issues
- Meaningful commit messages

## Getting Started

### 1. Fork This Repository
One team member:
```bash
# Fork on GitHub, then clone
git clone https://github.com/YOUR-TEAM/assignment-1-task-tracker.git
cd assignment-1-task-tracker
```

### 2. Run the Tests
```bash
./gradlew test
# All tests will fail initially - that's expected!
```

### 3. Start the Application
```bash
./gradlew bootRun
# Visit http://localhost:8080/api/items
```

### 4. Implement Your Solution
- Rename `Item` to your domain object
- Rename `ItemController` to match
- Implement all endpoints
- Make tests pass one by one

### 5. Test Your API
```bash
# Create an item
curl -X POST http://localhost:8080/api/items \
  -H "Content-Type: application/json" \
  -d '{"name":"Test","description":"Test item"}'

# Get all items  
curl http://localhost:8080/api/items

# Get specific item
curl http://localhost:8080/api/items/1

# Update item
curl -X PUT http://localhost:8080/api/items/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Updated","description":"Updated item","completed":true}'

# Delete item
curl -X DELETE http://localhost:8080/api/items/1
```

## Grading Rubric

| Criteria | Points | Description |
|----------|--------|-------------|
| **Tests Pass** | 60 | All 15 tests must pass (4 points each) |
| **Domain Customization** | 20 | Proper domain modeling, not generic "Item" |
| **Code Quality** | 10 | Clean code, no major SonarCloud issues |
| **AI Documentation** | 10 | Thoughtful reflection on AI usage |
| **Bonus: Search** | +5 | Implement search functionality |

## Tips for Success

### Working with AI
- Start with: "I need to implement a Spring Boot REST controller for [domain]"
- Be specific: "Add validation to ensure names are unique"
- Verify everything: AI often forgets error handling
- Test incrementally: Make one test pass at a time

### Common Pitfalls
- Don't forget to validate input (empty strings, nulls)
- Remember to handle 404s for missing items
- Duplicate names should return 409 Conflict
- IDs should be generated, not provided by client

### Git Collaboration
```bash
# Create feature branch
git checkout -b feature/implement-create-endpoint

# Commit often
git add .
git commit -m "Implement POST endpoint for creating items"

# Push to GitHub
git push origin feature/implement-create-endpoint

# Create Pull Request on GitHub for team review
```

## Submission

1. Ensure all tests pass: `./gradlew test`
2. Push final code to main branch
3. Submit GitHub repository link on Moodle
4. Include team member names in submission

## Questions?

- Office hours: Wednesdays 1:30-3:00 PM
- Email: kkousen@trincoll.edu

## Academic Integrity

- This is a TEAM assignment
- You may help other teams with concepts
- AI use is encouraged but must be documented

Good luck! Remember: the tests are your specification. Make them pass, and you succeed!