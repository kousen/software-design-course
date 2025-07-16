---
theme: seriph
background: https://source.unsplash.com/1920x1080/?code,clean
class: text-center
highlighter: shiki
lineNumbers: false
info: |
  ## DRY Principle: Don't Repeat Yourself
  
  CPSC 310: Software Design
  Trinity College, Fall 2025
  
  Kenneth Kousen
drawings:
  persist: false
transition: slide-left
title: "DRY Principle: Don't Repeat Yourself"
mdc: true
---

# DRY Principle: Don't Repeat Yourself

<div class="pt-12">
  <span @click="$slidev.nav.next" class="px-2 py-1 rounded cursor-pointer" hover="bg-white bg-opacity-10">
    Eliminating code duplication for better maintainability <carbon:arrow-right class="inline"/>
  </span>
</div>

---

# What is DRY?

<div class="flex items-center justify-between">
<div>

<v-clicks>

- **Don't Repeat Yourself**
- Every piece of knowledge should have a single representation
- Duplication is the root of many maintenance problems
- Make it easy to change related things together
- "Single source of truth" for each concept

</v-clicks>

</div>
<div class="w-1/3">
<img src="https://images.unsplash.com/photo-1553484771-cc0d9b8c2b33?w=400&h=300&fit=crop&brightness=1.2" alt="Clean code" class="rounded-lg opacity-50" />
</div>
</div>

---

# Why DRY Matters

<v-clicks>

- **Maintainability**: Change logic in one place, not many
- **Consistency**: Reduce the chance of inconsistent behavior
- **Debugging**: Fix bugs once, not multiple times
- **Understanding**: Less code to read and comprehend
- **Testing**: Fewer places to test the same logic

</v-clicks>

---

# Common Sources of Duplication

<v-clicks>

- **Copy-paste programming** - The most obvious culprit
- **Similar validation rules** across different classes
- **Repeated formatting logic** for output
- **Duplicated calculation formulas**
- **Repeated error handling patterns**
- **Similar database access patterns**

</v-clicks>

---

# Example: Validation Duplication

```java
// BEFORE: UserValidator - duplicated logic
public class UserValidator {
    public static boolean validateUser(User user) {
        // Email validation
        if (user.email() == null || user.email().trim().isEmpty()) {
            throw new ValidationException("Invalid email format");
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        if (!emailPattern.matcher(user.email()).matches()) {
            throw new ValidationException("Invalid email format");
        }
        
        // Name validation
        if (user.name() == null || user.name().trim().isEmpty()) {
            throw new ValidationException("Name cannot be empty");
        }
        return true;
    }
}
```

---

# The Same Logic Repeated

```java
// BEFORE: CustomerValidator - notice the duplication!
public class CustomerValidator {
    public static boolean validateCustomer(Customer customer) {
        // DUPLICATE: Same email validation as UserValidator
        if (customer.email() == null || customer.email().trim().isEmpty()) {
            throw new ValidationException("Invalid email format");
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        if (!emailPattern.matcher(customer.email()).matches()) {
            throw new ValidationException("Invalid email format");
        }
        
        // DUPLICATE: Same name validation as UserValidator
        if (customer.name() == null || customer.name().trim().isEmpty()) {
            throw new ValidationException("Name cannot be empty");
        }
        
        // Additional phone validation...
        return true;
    }
}
```

What's wrong with this approach?

---

# Problems with Duplication

<v-clicks>

- **Email regex appears twice** - what if we need to update it?
- **Validation logic repeated** - bug fixes needed in multiple places
- **Inconsistent error messages** - easy to have different text
- **More code to maintain** - increases cognitive load
- **Testing overhead** - same logic tested multiple times

</v-clicks>

---

# DRY Solution: Extract Utilities

```java
// AFTER: Single source of truth for validation
public class ValidationUtils {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    public static boolean isValidEmail(String email) {
        return email != null && 
               !email.trim().isEmpty() && 
               EMAIL_PATTERN.matcher(email).matches();
    }
    
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }
    
    public static void validateEmail(String email, String fieldName) {
        if (!isValidEmail(email)) {
            throw new ValidationException("Invalid " + fieldName + " format");
        }
    }
}
```

---

# Using the DRY Solution

```java
// Clean, DRY validators
public class UserValidator {
    public static boolean validateUser(User user) {
        ValidationUtils.validateEmail(user.email(), "email");
        ValidationUtils.validateName(user.name(), "name");
        return true;
    }
}

public class CustomerValidator {
    public static boolean validateCustomer(Customer customer) {
        ValidationUtils.validateEmail(customer.email(), "email");
        ValidationUtils.validateName(customer.name(), "name");
        ValidationUtils.validatePhone(customer.phone(), "phone");
        return true;
    }
}
```

<v-clicks>

- **Single source of truth** for each validation rule
- **Consistent behavior** across all validators
- **Easy to modify** - change logic in one place
- **Better testability** - test utilities independently

</v-clicks>

---

# DRY Techniques: Extract Method

```java
// BEFORE: Repeated calculation
public class ShapeCalculator {
    public double circleArea(double radius) {
        double pi = 3.14159;
        return pi * radius * radius;
    }
    
    public double circleCircumference(double radius) {
        double pi = 3.14159;  // Duplicate!
        return 2 * pi * radius;
    }
    
    public double sphereVolume(double radius) {
        double pi = 3.14159;  // Duplicate!
        return (4.0/3.0) * pi * radius * radius * radius;
    }
}
```

---

# Extract Method Solution

```java
// AFTER: DRY with extracted constant and method
public class ShapeCalculator {
    private static final double PI = Math.PI;  // Use built-in constant
    
    public double circleArea(double radius) {
        return PI * square(radius);
    }
    
    public double circleCircumference(double radius) {
        return 2 * PI * radius;
    }
    
    public double sphereVolume(double radius) {
        return (4.0/3.0) * PI * cube(radius);
    }
    
    private double square(double value) {
        return value * value;
    }
    
    private double cube(double value) {
        return value * value * value;
    }
}
```

---

# DRY Techniques: Extract Class

```java
// BEFORE: Formatting logic scattered throughout
public class ReportGenerator {
    public String generateUserReport(User user) {
        StringBuilder report = new StringBuilder();
        report.append("=================================\n");
        report.append("         USER REPORT            \n");
        report.append("=================================\n\n");
        
        report.append("Name: ").append(user.name()).append("\n");
        report.append("Email: ").append(user.email()).append("\n\n");
        
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        report.append("Generated on: ").append(now.format(formatter)).append("\n");
        
        return report.toString();
    }
    // generateCustomerReport has similar duplication...
}
```

---

# Extract Class Solution

```java
// AFTER: DRY with extracted ReportUtils class
public class ReportUtils {
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public static String formatHeader(String title) {
        String border = "=".repeat(33);
        int padding = (33 - title.length()) / 2;
        String centeredTitle = " ".repeat(padding) + title + " ".repeat(padding);
        return border + "\n" + centeredTitle + "\n" + border + "\n\n";
    }
    
    public static String formatField(String label, String value) {
        return label + ": " + value;
    }
    
    public static String formatTimestamp() {
        return "Generated on: " + LocalDateTime.now().format(TIMESTAMP_FORMATTER);
    }
    
    public static String buildReport(String title, String... fields) {
        StringBuilder report = new StringBuilder();
        report.append(formatHeader(title));
        for (String field : fields) {
            report.append(field).append("\n");
        }
        report.append("\n").append(formatTimestamp()).append("\n");
        return report.toString();
    }
}
```

---

# Using the Report Utils

```java
// Clean, DRY report generation
public class ReportGenerator {
    public static String generateUserReport(User user) {
        return ReportUtils.buildReport(
            "USER REPORT",
            ReportUtils.formatField("Name", user.name()),
            ReportUtils.formatField("Email", user.email())
        );
    }
    
    public static String generateCustomerReport(Customer customer) {
        return ReportUtils.buildReport(
            "CUSTOMER REPORT",
            ReportUtils.formatField("Name", customer.name()),
            ReportUtils.formatField("Email", customer.email()),
            ReportUtils.formatField("Phone", customer.phone())
        );
    }
}
```

Much cleaner and maintainable!

---

# DRY with Generic Methods

```java
// BEFORE: Type-specific duplicated logic
public class ListProcessor {
    public List<String> filterNonEmptyStrings(List<String> strings) {
        List<String> result = new ArrayList<>();
        for (String s : strings) {
            if (s != null && !s.trim().isEmpty()) {
                result.add(s);
            }
        }
        return result;
    }
    
    public List<Integer> filterPositiveIntegers(List<Integer> integers) {
        List<Integer> result = new ArrayList<>();
        for (Integer i : integers) {
            if (i != null && i > 0) {
                result.add(i);
            }
        }
        return result;
    }
}
```

---

# Generic DRY Solution

```java
// AFTER: DRY with generics and predicates
public class ListProcessor {
    public static <T> List<T> filter(List<T> items, Predicate<T> predicate) {
        return items.stream()
                   .filter(predicate)
                   .collect(Collectors.toList());
    }
    
    // Usage becomes much simpler
    public List<String> filterNonEmptyStrings(List<String> strings) {
        return filter(strings, s -> s != null && !s.trim().isEmpty());
    }
    
    public List<Integer> filterPositiveIntegers(List<Integer> integers) {
        return filter(integers, i -> i != null && i > 0);
    }
    
    // Easy to add new filtering without duplication
    public List<Double> filterLargeDoubles(List<Double> doubles) {
        return filter(doubles, d -> d != null && d > 100.0);
    }
}
```

---

# When NOT to Apply DRY

<v-clicks>

- **Coincidental duplication** - similar code with different reasons to change
- **Premature abstraction** - creating complex abstractions too early
- **Over-generalization** - making code harder to understand
- **Different domains** - similar logic but different business contexts
- **Performance critical** - when abstraction adds overhead

</v-clicks>

---

# Example: Coincidental Duplication

```java
// These look similar but serve different purposes
public class UserService {
    public boolean isValidUser(User user) {
        return user.name() != null && user.name().length() >= 2;
    }
}

public class ProductService {
    public boolean isValidProductName(String name) {
        return name != null && name.length() >= 2;  // Same logic!
    }
}
```

<v-clicks>

- **Different reasons to change**: User validation vs. product naming rules
- **Different contexts**: User requirements vs. business rules
- **Don't extract** just because the code looks similar
- **Wait for a third occurrence** before considering abstraction

</v-clicks>

---

# DRY and the Rule of Three

<v-clicks>

1. **First occurrence**: Write the code
2. **Second occurrence**: Note the duplication, but wait
3. **Third occurrence**: Now extract the common functionality

This prevents premature abstraction while catching real duplication.

</v-clicks>

---

# Modern Java and DRY

```java
// Records reduce boilerplate duplication
public record User(String email, String name) {
    // Automatic equals(), hashCode(), toString()
}

// Stream API reduces iteration duplication
List<String> validEmails = users.stream()
    .map(User::email)
    .filter(ValidationUtils::isValidEmail)
    .collect(toList());

// Method references eliminate lambda duplication
users.forEach(System.out::println);  // Instead of user -> System.out.println(user)

// Optional eliminates null-checking duplication
return Optional.ofNullable(user)
               .map(User::email)
               .filter(ValidationUtils::isValidEmail)
               .orElse("No valid email");
```

---

# DRY Testing Strategies

```java
@Test
void validationUtils_shouldValidateEmailCorrectly() {
    // Test both positive and negative cases in one method
    assertThat(ValidationUtils.isValidEmail("test@example.com")).isTrue();
    assertThat(ValidationUtils.isValidEmail("invalid-email")).isFalse();
    assertThat(ValidationUtils.isValidEmail("")).isFalse();
    assertThat(ValidationUtils.isValidEmail(null)).isFalse();
}

// Parameterized tests reduce test duplication
@ParameterizedTest
@ValueSource(strings = {"", "   ", "invalid-email", "no-at-sign"})
void isValidEmail_shouldRejectInvalidEmails(String email) {
    assertThat(ValidationUtils.isValidEmail(email)).isFalse();
}

@ParameterizedTest
@ValueSource(strings = {"test@example.com", "user@domain.org", "name@company.co.uk"})
void isValidEmail_shouldAcceptValidEmails(String email) {
    assertThat(ValidationUtils.isValidEmail(email)).isTrue();
}
```

---

# DRY in Configuration

```java
// BEFORE: Hardcoded values scattered throughout
public class EmailService {
    public void sendEmail(String to, String subject, String body) {
        String smtpHost = "smtp.gmail.com";  // Duplicate
        int smtpPort = 587;                  // Duplicate
        // ...
    }
}

public class NotificationService {
    public void sendNotification(String recipient, String message) {
        String smtpHost = "smtp.gmail.com";  // Duplicate
        int smtpPort = 587;                  // Duplicate
        // ...
    }
}
```

---

# DRY Configuration Solution

```java
// AFTER: Centralized configuration
public class EmailConfig {
    public static final String SMTP_HOST = "smtp.gmail.com";
    public static final int SMTP_PORT = 587;
    public static final String FROM_ADDRESS = "noreply@company.com";
}

// Or even better, use dependency injection
@Component
public class EmailService {
    private final EmailConfig config;
    
    public EmailService(EmailConfig config) {
        this.config = config;
    }
    
    public void sendEmail(String to, String subject, String body) {
        // Use config.getSmtpHost(), config.getSmtpPort()
    }
}
```

---

# DRY Refactoring Steps

<v-clicks>

1. **Identify duplication** - Look for copy-paste code
2. **Verify it's real duplication** - Same logic, same reason to change
3. **Extract the common part** - Method, class, or utility
4. **Parameterize differences** - Make the extracted code flexible
5. **Replace all occurrences** - Update all duplicate code
6. **Test thoroughly** - Ensure behavior is preserved
7. **Review and refine** - Is the abstraction clear and useful?

</v-clicks>

---

# Benefits of DRY Code

<v-clicks>

- **Faster development** - Write logic once, reuse everywhere
- **Easier maintenance** - Change business rules in one place
- **Fewer bugs** - Less code means fewer places for bugs to hide
- **Better consistency** - Uniform behavior across the application
- **Improved readability** - Less noise, more signal

</v-clicks>

---

# DRY Anti-Patterns to Avoid

<v-clicks>

- **God utilities** - Classes that do everything
- **Over-parameterization** - Too many parameters to handle all cases
- **Deep inheritance** - Complex hierarchies just to share code
- **Premature optimization** - Extracting before understanding patterns
- **Blind abstraction** - Creating abstractions that don't make sense

</v-clicks>

---

# Tools to Help with DRY

<v-clicks>

- **IDE refactoring tools** - Extract method, extract class
- **Static analysis** - PMD, SpotBugs can detect some duplication
- **Copy-paste detectors** - Tools that find duplicate code blocks
- **Code reviews** - Human eyes are great at spotting duplication
- **Pair programming** - Two people catch more duplication

</v-clicks>

---

# DRY and Other Principles

<div class="flex items-center justify-between">
<div>

<v-clicks>

- **Single Responsibility** - DRY utilities should have one job
- **Open-Closed** - DRY abstractions should be extensible
- **Interface Segregation** - Don't create fat utility interfaces
- **Dependency Inversion** - Depend on DRY abstractions

</v-clicks>

</div>
<div class="w-1/3">
<img src="https://images.unsplash.com/photo-1558618966-fcd25c85cd64?w=400&h=300&fit=crop&brightness=1.2" alt="Connected principles" class="rounded-lg opacity-50" />
</div>
</div>

---

# Exercise: Find the Duplication

```java
public class OrderProcessor {
    public void processOrder(Order order) {
        System.out.println("Processing order: " + order.getId());
        System.out.println("Customer: " + order.getCustomer().getName());
        System.out.println("Total: $" + order.getTotal());
        
        if (order.getTotal() > 100) {
            order.setDiscount(0.10);
        }
        
        order.setStatus("PROCESSED");
        orderRepository.save(order);
        System.out.println("Order processed successfully");
    }
    
    public void processRefund(Refund refund) {
        System.out.println("Processing refund: " + refund.getId());
        System.out.println("Customer: " + refund.getCustomer().getName());
        System.out.println("Amount: $" + refund.getAmount());
        
        refund.setStatus("PROCESSED");
        refundRepository.save(refund);
        System.out.println("Refund processed successfully");
    }
}
```

What duplications do you see? How would you fix them?

---

# Key Takeaways

<v-clicks>

- **DRY reduces maintenance burden** and improves consistency
- **Look for patterns** in your code - similar logic, repeated structures
- **Extract responsibly** - wait for clear patterns to emerge
- **Balance DRY with readability** - don't over-abstract
- **Use modern Java features** to naturally eliminate duplication
- **Test your extractions** to ensure behavior is preserved

</v-clicks>

---

# Next Steps

<v-clicks>

- Practice identifying duplication in existing code
- Refactor duplicate code using extract method/class
- Use parameterized tests to reduce test duplication
- Combine DRY with SOLID principles for better design
- Remember: **Don't Repeat Yourself, but don't abstract yourself into complexity!**

</v-clicks>

---

# Questions?

<div class="text-center">

Ready to eliminate duplication and write cleaner, more maintainable code!

<div class="mt-8">
<img src="https://images.unsplash.com/photo-1519389950473-47ba0277781c?w=600&h=200&fit=crop" alt="Clean code workspace" class="rounded-lg mx-auto opacity-60" />
</div>

</div>