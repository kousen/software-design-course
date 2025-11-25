package edu.trincoll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

/**
 * Comprehensive examples of AssertJ fluent assertions.
 * Demonstrates the most commonly used assertion patterns.
 */
@DisplayName("AssertJ Examples")
class AssertJExamplesTest {

    @Nested
    @DisplayName("String Assertions")
    class StringAssertions {

        @Test
        @DisplayName("basic string assertions")
        void basicStringAssertions() {
            String title = "The Lord of the Rings";

            assertThat(title)
                    .isNotNull()
                    .isNotEmpty()
                    .isNotBlank()
                    .hasSize(21);
        }

        @Test
        @DisplayName("string content assertions")
        void stringContentAssertions() {
            String title = "The Lord of the Rings";

            assertThat(title)
                    .startsWith("The")
                    .endsWith("Rings")
                    .contains("Lord")
                    .containsIgnoringCase("LORD")
                    .doesNotContain("Hobbit");
        }

        @Test
        @DisplayName("string comparison assertions")
        void stringComparisonAssertions() {
            //noinspection EqualsWithItself
            assertThat("Hello")
                    .isEqualTo("Hello")
                    .isEqualToIgnoringCase("HELLO")
                    .isNotEqualTo("World");
        }
    }

    @Nested
    @DisplayName("Number Assertions")
    class NumberAssertions {

        @Test
        @DisplayName("integer assertions")
        void integerAssertions() {
            int answer = 42;

            assertThat(answer)
                    .isEqualTo(42)
                    .isNotEqualTo(43)
                    .isPositive()
                    .isNotNegative()
                    .isGreaterThan(40)
                    .isLessThan(50)
                    .isBetween(40, 50);
        }

        @Test
        @DisplayName("floating point assertions")
        void floatingPointAssertions() {
            double pi = 3.14159;

            assertThat(pi)
                    .isCloseTo(3.14, within(0.01))
                    .isGreaterThan(3.0)
                    .isLessThan(4.0);
        }

        @Test
        @DisplayName("even and odd assertions")
        void evenOddAssertions() {
            assertThat(42).isEven();
            assertThat(43).isOdd();
        }
    }

    @Nested
    @DisplayName("Collection Assertions")
    class CollectionAssertions {

        private final List<String> hobbits = List.of("Frodo", "Sam", "Merry", "Pippin");

        @Test
        @DisplayName("basic collection assertions")
        void basicCollectionAssertions() {
            assertThat(hobbits)
                    .isNotNull()
                    .isNotEmpty()
                    .hasSize(4);
        }

        @Test
        @DisplayName("contains assertions")
        void containsAssertions() {
            assertThat(hobbits)
                    .contains("Frodo", "Sam")
                    .containsOnly("Frodo", "Sam", "Merry", "Pippin")
                    .containsExactly("Frodo", "Sam", "Merry", "Pippin")
                    .containsExactlyInAnyOrder("Sam", "Frodo", "Pippin", "Merry")
                    .doesNotContain("Gandalf", "Aragorn");
        }

        @Test
        @DisplayName("ordering assertions")
        void orderingAssertions() {
            assertThat(hobbits)
                    .startsWith("Frodo")
                    .endsWith("Pippin")
                    .containsSequence("Sam", "Merry");
        }

        @Test
        @DisplayName("filtering and extracting")
        void filteringAndExtracting() {
            assertThat(hobbits)
                    .filteredOn(name -> name.startsWith("M"))
                    .containsOnly("Merry");

            assertThat(hobbits)
                    .filteredOn(name -> name.length() > 4)
                    .hasSize(3)
                    .contains("Frodo", "Merry", "Pippin");
        }
    }

    @Nested
    @DisplayName("Object Assertions")
    class ObjectAssertions {

        @Test
        @DisplayName("extracting properties")
        void extractingProperties() {
            Person person = new Person("Jean-Luc", "Picard", LocalDate.of(2305, Month.JULY, 13));

            assertThat(person)
                    .extracting(Person::first, Person::last)
                    .containsExactly("Jean-Luc", "Picard");
        }

        @Test
        @DisplayName("extracting by field name")
        void extractingByFieldName() {
            Person person = new Person("Jean-Luc", "Picard", LocalDate.of(2305, Month.JULY, 13));

            assertThat(person)
                    .extracting("first", "last")
                    .containsExactly("Jean-Luc", "Picard");
        }

        @Test
        @DisplayName("extracting from list of objects")
        void extractingFromList() {
            List<Person> crew = List.of(
                    new Person("Jean-Luc", "Picard", LocalDate.of(2305, 7, 13)),
                    new Person("William", "Riker", LocalDate.of(2335, 8, 19)),
                    new Person("Deanna", "Troi", LocalDate.of(2336, 3, 29))
            );

            assertThat(crew)
                    .extracting(Person::first)
                    .containsExactly("Jean-Luc", "William", "Deanna");

            assertThat(crew)
                    .extracting(Person::first, Person::last)
                    .contains(
                            tuple("Jean-Luc", "Picard"),
                            tuple("William", "Riker"),
                            tuple("Deanna", "Troi")
                    );
        }

        @Test
        @DisplayName("age calculation with valid date of birth")
        void ageCalculationWithValidDob() {
            Person person = new Person("John", "Doe", LocalDate.of(1990, Month.JUNE, 15));

            assertThat(person.age())
                    .isPositive()
                    .isLessThan(150);
        }

        @Test
        @DisplayName("age throws exception when dob is null")
        void ageThrowsWhenDobIsNull() {
            Person person = new Person("John", "Doe");

            assertThatIllegalStateException()
                    .isThrownBy(person::age)
                    .withMessageContaining("Date of birth");
        }
    }

    @Nested
    @DisplayName("Exception Assertions")
    class ExceptionAssertions {

        @Test
        @DisplayName("assertThatThrownBy pattern")
        void assertThatThrownByPattern() {
            assertThatThrownBy(() -> {
                throw new IllegalArgumentException("Invalid input");
            })
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Invalid input")
                    .hasMessageContaining("Invalid");
        }

        @Test
        @DisplayName("specific exception type shortcuts")
        void specificExceptionShortcuts() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> {
                        throw new IllegalArgumentException("bad value");
                    })
                    .withMessage("bad value");

            assertThatNullPointerException()
                    .isThrownBy(() -> {
                        throw new NullPointerException("value is null");
                    });
        }

        @SuppressWarnings("NumericOverflow")
        @Test
        @DisplayName("assertThatCode for no exception")
        void assertNoException() {
            assertThatCode(() -> {
                // This code should not throw
                //noinspection divzero
                double result = 1.0 / 0.0;
                System.out.println(result);
            }).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("exception cause chain")
        void exceptionCauseChain() {
            assertThatThrownBy(() -> {
                throw new RuntimeException("Outer", new IllegalStateException("Inner"));
            })
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("Outer")
                    .hasCauseInstanceOf(IllegalStateException.class)
                    .hasRootCauseMessage("Inner");
        }
    }

    @Nested
    @DisplayName("Optional Assertions")
    class OptionalAssertions {

        @Test
        @DisplayName("present Optional")
        void presentOptional() {
            Optional<String> present = Optional.of("value");

            assertThat(present)
                    .isPresent()
                    .isNotEmpty()
                    .hasValue("value")
                    .contains("value");
        }

        @Test
        @DisplayName("empty Optional")
        void emptyOptional() {
            Optional<String> empty = Optional.empty();

            assertThat(empty)
                    .isEmpty()
                    .isNotPresent();
        }

        @Test
        @DisplayName("Optional with condition")
        void optionalWithCondition() {
            Optional<String> present = Optional.of("hello world");

            assertThat(present)
                    .hasValueSatisfying(value -> {
                        assertThat(value).startsWith("hello");
                        assertThat(value).hasSize(11);
                    });
        }
    }

    @Nested
    @DisplayName("Map Assertions")
    class MapAssertions {

        private final Map<String, Integer> ages = Map.of(
                "Alice", 30,
                "Bob", 25,
                "Charlie", 35
        );

        @Test
        @DisplayName("basic map assertions")
        void basicMapAssertions() {
            assertThat(ages)
                    .isNotEmpty()
                    .hasSize(3)
                    .containsKey("Alice")
                    .containsKeys("Alice", "Bob")
                    .doesNotContainKey("David");
        }

        @Test
        @DisplayName("value assertions")
        void valueAssertions() {
            assertThat(ages)
                    .containsValue(30)
                    .containsValues(25, 30)
                    .containsEntry("Alice", 30)
                    .contains(entry("Bob", 25));
        }
    }

    @Nested
    @DisplayName("Soft Assertions")
    class SoftAssertionsExamples {

        @Test
        @DisplayName("all assertions run even if some fail")
        void softAssertionsExample() {
            Person person = new Person("John", "Doe", LocalDate.of(1990, 1, 15));

            // All assertions run, failures collected at end
            assertSoftly(softly -> {
                softly.assertThat(person.first()).isEqualTo("John");
                softly.assertThat(person.last()).isEqualTo("Doe");
                softly.assertThat(person.fullName()).isEqualTo("John Doe");
            });
        }
    }
}
