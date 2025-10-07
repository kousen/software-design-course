# Assignment 4: SOLID Principles with Spring Data JPA

**Due:** Thursday, October 23 at 11:59 PM
**Points:** 100
**Submission:** Via GitHub (one per team)

## Overview

This assignment combines two critical concepts from recent weeks: **SOLID design principles** and **Spring Data JPA**. You'll refactor a library management system that currently violates multiple SOLID principles while working with a real database using JPA.

## Learning Objectives

- Apply all five SOLID principles in practice
- Refactor code from violations to clean design
- Use Spring Data JPA for persistence
- Implement Strategy and other design patterns
- Write tests that prove your refactored code works
- Document design decisions and trade-offs

## What You're Given

A working but poorly designed Library Management System with:
- ✅ **Domain Models**: `Book` and `Member` entities with JPA annotations
- ✅ **Repositories**: Spring Data JPA repositories (already following SOLID!)
- ❌ **Service Layer**: `LibraryService` with **intentional SOLID violations**
- ✅ **Tests**: Comprehensive test suite for the existing (bad) design

Your task: **Refactor the service layer to follow SOLID principles**.

## Current SOLID Violations

The `LibraryService` class violates multiple SOLID principles:

### 1. Single Responsibility Principle (SRP)
- One class handles: book operations, member operations, notifications, search, reporting
- Each of these should be a separate service

### 2. Open-Closed Principle (OCP)
- Checkout limits use if-else for membership types
- Late fee calculation uses if-else for membership types
- Adding new membership types requires modifying existing code

### 3. Liskov Substitution Principle (LSP)
- Not directly violated, but the design doesn't allow for proper inheritance

### 4. Interface Segregation Principle (ISP)
- If we extracted interfaces from current design, they would be "fat" interfaces
- Clients would depend on methods they don't use

### 5. Dependency Inversion Principle (DIP)
- Notification logic is hard-coded (System.out.println)
- No abstraction between high-level logic and low-level details

## Your Task: 8 TODOs (100 points)

Each TODO in `LibraryService.java` explains what to refactor and why. Here's the breakdown:

### TODO 1: Extract BookService (15 points)
**SRP Violation Fix**

Extract book-specific operations into `BookService`:
- Book checkout logic
- Book return logic
- Book availability checking

```java
@Service
public class BookService {
    private final BookRepository bookRepository;

    public void checkoutBook(Book book, Member member, int loanPeriodDays) {
        // Just book operations
    }

    public void returnBook(Book book) {
        // Just book operations
    }
}
```

### TODO 2: Create CheckoutPolicy Strategy (15 points)
**OCP Violation Fix**

Replace if-else statements with Strategy pattern:

```java
public interface CheckoutPolicy {
    int getMaxBooks();
    int getLoanPeriodDays();
    boolean canCheckout(Member member);
}

public class RegularCheckoutPolicy implements CheckoutPolicy {
    public int getMaxBooks() { return 3; }
    public int getLoanPeriodDays() { return 14; }
    // ...
}

public class PremiumCheckoutPolicy implements CheckoutPolicy {
    public int getMaxBooks() { return 10; }
    public int getLoanPeriodDays() { return 30; }
    // ...
}
```

Use a factory or configuration to select the right policy:
```java
@Component
public class CheckoutPolicyFactory {
    public CheckoutPolicy getPolicyFor(MembershipType type) {
        return switch (type) {
            case REGULAR -> new RegularCheckoutPolicy();
            case PREMIUM -> new PremiumCheckoutPolicy();
            case STUDENT -> new StudentCheckoutPolicy();
        };
    }
}
```

### TODO 3: Create NotificationService (10 points)
**SRP & DIP Violation Fix**

Extract notification logic and depend on abstraction:

```java
public interface NotificationService {
    void sendCheckoutNotification(Member member, Book book, LocalDate dueDate);
    void sendReturnNotification(Member member, Book book, double lateFee);
}

@Service
public class EmailNotificationService implements NotificationService {
    // Actual email sending implementation
    // For now, can still use System.out.println
}
```

Now services depend on the `NotificationService` interface (DIP), not concrete implementation.

### TODO 4: Extract MemberService (15 points)
**SRP Violation Fix**

Create `MemberService` for member-specific operations:
- Update checkout count
- Retrieve member details
- Member validation

```java
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public void incrementCheckoutCount(Member member) {
        member.setBooksCheckedOut(member.getBooksCheckedOut() + 1);
        memberRepository.save(member);
    }

    public void decrementCheckoutCount(Member member) {
        member.setBooksCheckedOut(member.getBooksCheckedOut() - 1);
        memberRepository.save(member);
    }
}
```

### TODO 5: Create LateFeeCalculator Strategy (10 points)
**OCP Violation Fix**

Extract late fee calculation with Strategy pattern:

```java
public interface LateFeeCalculator {
    double calculateLateFee(long daysLate);
}

public class RegularLateFeeCalculator implements LateFeeCalculator {
    public double calculateLateFee(long daysLate) {
        return daysLate * 0.50;
    }
}

public class PremiumLateFeeCalculator implements LateFeeCalculator {
    public double calculateLateFee(long daysLate) {
        return 0.0; // No late fees for premium
    }
}
```

### TODO 6: Create BookSearchService (10 points)
**SRP & ISP Violation Fix**

Extract search operations:

```java
@Service
public class BookSearchService {
    private final BookRepository bookRepository;

    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> searchByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public Optional<Book> searchByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }
}
```

Clients can now depend only on the search methods they need (ISP).

### TODO 7: Create ReportGenerator Strategy (10 points)
**OCP & LSP Violation Fix**

Use Strategy pattern for reports:

```java
public interface ReportGenerator {
    String generateReport();
}

public class OverdueReportGenerator implements ReportGenerator {
    private final BookRepository bookRepository;

    public String generateReport() {
        // Generate overdue books report
    }
}

public class AvailabilityReportGenerator implements ReportGenerator {
    private final BookRepository bookRepository;

    public String generateReport() {
        // Generate available books report
    }
}
```

### TODO 8: BONUS Integration & Documentation (15 points)

After completing all refactoring:

1. **Create LibraryFacade** (5 points)
   - Coordinate between all services
   - Present simplified API to controllers
   - Demonstrates Facade pattern

2. **Update Tests** (5 points)
   - Ensure all existing tests still pass
   - Add tests for new services
   - Demonstrate testability improvements with mocks

3. **Document Architecture** (5 points)
   - Create a before/after class diagram
   - Explain which SOLID principle each change addresses
   - Show dependency graph

## Architecture After Refactoring

```
┌─────────────────────────────────────────┐
│         LibraryFacade                   │  ← Simplified API
│  (orchestrates all services)            │
└─────────────────────────────────────────┘
                 ↓ depends on
    ┌────────────┴────────────┐
    ↓                         ↓
┌──────────┐            ┌──────────────┐
│ BookServ │            │ MemberService│
│  ice     │            │              │
└──────────┘            └──────────────┘
    ↓                         ↓
    ↓                   ┌──────────────┐
    ↓                   │Notification  │  ← Interface (DIP)
    ↓                   │Service       │
    ↓                   └──────────────┘
    ↓                         ↑
┌──────────────┐         implements
│CheckoutPolicy│  ← Strategy (OCP)
│  Factory     │
└──────────────┘
    ↓
┌──────────────┐
│RegularPolicy │
│PremiumPolicy │  ← Different implementations
│StudentPolicy │
└──────────────┘
```

## Testing Requirements

### Minimum Coverage: 80%
Your refactored code must maintain at least 80% test coverage.

```bash
./gradlew test
./gradlew jacocoTestReport
# Check build/reports/jacoco/test/html/index.html
```

### Test Categories

1. **Unit Tests** for each new service
   - Mock dependencies
   - Test in isolation
   - Fast execution

2. **Integration Tests** for LibraryFacade
   - Test service coordination
   - Use actual Spring context
   - Test with H2 database

3. **Demonstrate Testability**
   - Show how Strategy pattern enables easy testing
   - Show how DIP makes mocking easier
   - Compare to testing the original monolithic service

## Spring Data JPA Usage

This assignment uses **Spring Data JPA** with an **H2 in-memory database**:

### Entities
- `@Entity`, `@Table`, `@Id`, `@GeneratedValue`
- Relationships and constraints
- JPA lifecycle callbacks

### Repositories
- Extend `JpaRepository<T, ID>`
- Query methods (already implemented)
- Custom queries with `@Query` (optional bonus)

### Configuration
See `application.properties`:
- H2 database setup
- JPA settings
- Enable SQL logging for debugging

### H2 Console
Access at http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:library`
- Username: `sa`
- Password: (empty)

## Getting Started

### 1. Fork and Clone
```bash
git clone [your-forked-repo-url]
cd assignment-4-solid
```

### 2. Run Existing Tests
```bash
./gradlew test
# All tests should pass with the current (bad) design
```

### 3. Understand the Violations
- Read through `LibraryService.java`
- Identify each SOLID violation
- Plan your refactoring strategy

### 4. Refactor Incrementally
1. Start with TODO 1 (BookService)
2. Ensure tests still pass
3. Move to TODO 2 (CheckoutPolicy)
4. Continue one TODO at a time
5. Run tests after each change

### 5. Run the Application
```bash
./gradlew bootRun
# Access H2 console at http://localhost:8080/h2-console
```

## Grading Rubric

| Component | Points | Requirements |
|-----------|--------|--------------|
| TODO 1 - BookService | 15 | Proper SRP separation, all book operations extracted |
| TODO 2 - CheckoutPolicy | 15 | Strategy pattern, OCP compliance, factory implemented |
| TODO 3 - NotificationService | 10 | Interface abstraction, DIP demonstrated |
| TODO 4 - MemberService | 15 | SRP compliance, clear responsibilities |
| TODO 5 - LateFeeCalculator | 10 | Strategy pattern for fees |
| TODO 6 - BookSearchService | 10 | ISP compliance, focused interface |
| TODO 7 - ReportGenerator | 10 | Strategy for reports, easily extensible |
| TODO 8 - BONUS | 15 | Facade, documentation, architecture diagram |
| **Total** | **100** | Plus 15 bonus points available |

### Quality Criteria
- All tests pass ✓
- 80%+ code coverage ✓
- No SOLID violations remaining ✓
- Code is well-organized ✓
- Javadoc for public APIs ✓

## AI Collaboration Requirements

Document at the top of `LibraryFacade.java`:

```java
/**
 * AI Collaboration Summary:
 * Tool: [ChatGPT/Claude/Copilot/Gemini]
 *
 * Refactoring Assistance:
 * 1. [How AI helped with Strategy pattern]
 * 2. [How AI helped with service extraction]
 *
 * AI Mistakes Corrected:
 * 1. [What AI suggested that violated SOLID]
 * 2. [How you identified and fixed it]
 *
 * SOLID Insights:
 * [What you learned about applying SOLID principles]
 * [Which principle was hardest to apply and why]
 *
 * Team: [Member names and contributions]
 */
```

## Common Pitfalls

❌ **DON'T:**
- Extract services but keep them tightly coupled
- Create interfaces without implementation flexibility
- Ignore the existing tests
- Make too many changes at once
- Forget to inject dependencies via constructors

✅ **DO:**
- Make one change at a time
- Run tests after each change
- Use constructor injection (@RequiredArgsConstructor works great)
- Keep strategies stateless when possible
- Write new tests for new services

## Submission Requirements

1. Push all code to your GitHub repository
2. Ensure `./gradlew test` passes with 80%+ coverage
3. Include a `REFACTORING.md` document:
   - List each SOLID principle
   - Explain how you fixed violations
   - Include before/after code snippets
4. Submit repository URL to Moodle
5. Each team member must have meaningful commits

## Tips for Success

1. **Understand SOLID First**
   - Review Week 6 slides
   - Study `examples/design-patterns/solid/`
   - Identify violations before coding

2. **Test-Driven Refactoring**
   - Keep tests green
   - Refactor when tests pass
   - Add new tests for new services

3. **Use Design Patterns**
   - Strategy for variable behavior
   - Factory for object creation
   - Facade for coordination

4. **Leverage Spring**
   - Constructor injection
   - Component scanning
   - Spring manages your objects

5. **Document Decisions**
   - Why you chose Strategy over Template Method
   - Trade-offs of your design
   - What you would do differently

## Resources

- Week 6 Slides: SOLID Principles
- `examples/design-patterns/solid/` - Working SOLID examples
- Spring Data JPA Documentation
- Design Patterns: Strategy, Factory, Facade
- Office Hours: Wednesdays 1:30-3:00 PM

## Academic Integrity

This is a **team assignment**. Collaboration within teams is expected and encouraged. However:
- Each team's solution must be original
- You may discuss concepts with other teams
- You may NOT share code with other teams
- Document all AI assistance used

## Extra Challenges (Optional)

1. **Add Transaction Management**
   - Use `@Transactional` appropriately
   - Handle rollback scenarios

2. **Implement Real Email Service**
   - Use Spring Mail
   - Make it configurable (dev vs prod)

3. **Add More Membership Types**
   - Demonstrate OCP in action
   - No modifications to existing code

4. **Create REST Controllers**
   - Build on your facade
   - Add API documentation

5. **Implement Observer Pattern**
   - Notify when books become available
   - Demonstrate loose coupling

---

**Remember:** The goal isn't just to make tests pass—it's to understand **why** SOLID principles lead to better software design. Focus on creating maintainable, extensible, and testable code!

Good luck! 🚀
