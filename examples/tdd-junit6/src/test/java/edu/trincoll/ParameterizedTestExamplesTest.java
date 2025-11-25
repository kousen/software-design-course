package edu.trincoll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.time.Month;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Comprehensive examples of JUnit parameterized tests.
 * Demonstrates all major parameter source types.
 */
@DisplayName("Parameterized Test Examples")
class ParameterizedTestExamplesTest {

    @Nested
    @DisplayName("@ValueSource")
    class ValueSourceExamples {

        @ParameterizedTest(name = "\"{0}\" is not blank")
        @ValueSource(strings = {"hello", "world", "  spaces  ", "123"})
        void stringsAreNotBlank(String input) {
            assertThat(input).isNotBlank();
        }

        @ParameterizedTest(name = "{0} is positive")
        @ValueSource(ints = {1, 5, 10, 100, Integer.MAX_VALUE})
        void integersArePositive(int number) {
            assertThat(number).isPositive();
        }

        @ParameterizedTest(name = "{0} is greater than 0.0")
        @ValueSource(doubles = {0.1, 1.0, 3.14, 100.5})
        void doublesArePositive(double number) {
            assertThat(number).isGreaterThan(0.0);
        }
    }

    @Nested
    @DisplayName("@NullAndEmptySource")
    class NullAndEmptySourceExamples {

        @ParameterizedTest(name = "input is null or empty: \"{0}\"")
        @NullAndEmptySource
        void isNullOrEmpty(String input) {
            assertThat(input == null || input.isEmpty()).isTrue();
        }

        @ParameterizedTest(name = "input is null, empty, or blank: \"{0}\"")
        @NullAndEmptySource
        @ValueSource(strings = {"  ", "\t", "\n"})
        void isNullEmptyOrBlank(String input) {
            assertThat(input == null || input.isBlank()).isTrue();
        }
    }

    @Nested
    @DisplayName("@EnumSource")
    class EnumSourceExamples {

        @ParameterizedTest(name = "{0} is a valid month")
        @EnumSource(Month.class)
        void allMonthsAreValid(Month month) {
            assertThat(month.getValue()).isBetween(1, 12);
        }

        @ParameterizedTest(name = "{0} has 30 days")
        @EnumSource(value = Month.class, names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER"})
        void monthsWith30Days(Month month) {
            assertThat(month.maxLength()).isEqualTo(30);
        }

        @ParameterizedTest(name = "{0} has 31 days")
        @EnumSource(value = Month.class, mode = EnumSource.Mode.MATCH_ALL,
                names = "^(JANUARY|MARCH|MAY|JULY|AUGUST|OCTOBER|DECEMBER)$")
        void monthsWith31Days(Month month) {
            assertThat(month.maxLength()).isEqualTo(31);
        }

        @ParameterizedTest(name = "{0} is not February")
        @EnumSource(value = Month.class, mode = EnumSource.Mode.EXCLUDE, names = "FEBRUARY")
        void monthsExceptFebruary(Month month) {
            assertThat(month).isNotEqualTo(Month.FEBRUARY);
        }
    }

    @Nested
    @DisplayName("@CsvSource")
    class CsvSourceExamples {

        @ParameterizedTest(name = "{0} + {1} = {2}")
        @CsvSource({
                "1, 2, 3",
                "0, 0, 0",
                "-1, 1, 0",
                "100, 200, 300"
        })
        void addition(int a, int b, int expected) {
            assertThat(a + b).isEqualTo(expected);
        }

        @ParameterizedTest(name = "\"{0}\".toUpperCase() = \"{1}\"")
        @CsvSource({
                "hello, HELLO",
                "world, WORLD",
                "'foo bar', 'FOO BAR'",
                "'', ''"
        })
        void toUpperCase(String input, String expected) {
            assertThat(input.toUpperCase()).isEqualTo(expected);
        }

        @ParameterizedTest(name = "Parse \"{0}\" to {1}")
        @CsvSource(value = {
                "42 | 42",
                "-100 | -100",
                "0 | 0"
        }, delimiter = '|')
        void parseInteger(String input, int expected) {
            assertThat(Integer.parseInt(input.trim())).isEqualTo(expected);
        }

        @ParameterizedTest
        @CsvSource(useHeadersInDisplayName = true, textBlock = """
                INPUT,    EXPECTED
                apple,    APPLE
                banana,   BANANA
                cherry,   CHERRY
                """)
        void withHeaders(String input, String expected) {
            assertThat(input.toUpperCase()).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("@MethodSource")
    class MethodSourceExamples {

        @ParameterizedTest(name = "{0} is a prime number")
        @MethodSource("primeNumbers")
        void isPrime(int number) {
            assertThat(isPrimeNumber(number)).isTrue();
        }

        static Stream<Integer> primeNumbers() {
            return Stream.of(2, 3, 5, 7, 11, 13, 17, 19, 23);
        }

        @ParameterizedTest(name = "{0} * {1} = {2}")
        @MethodSource("multiplicationProvider")
        void multiplication(int a, int b, int expected) {
            assertThat(a * b).isEqualTo(expected);
        }

        static Stream<Arguments> multiplicationProvider() {
            return Stream.of(
                    arguments(2, 3, 6),
                    arguments(0, 100, 0),
                    arguments(-2, 3, -6),
                    arguments(-2, -3, 6)
            );
        }

        @ParameterizedTest(name = "Person: {0}")
        @MethodSource("personProvider")
        void personHasValidName(Person person) {
            assertThat(person.first()).isNotBlank();
            assertThat(person.last()).isNotBlank();
        }

        static Stream<Person> personProvider() {
            return Stream.of(
                    new Person("John", "Doe"),
                    new Person("Jane", "Smith"),
                    new Person("Alice", "Johnson")
            );
        }

        private boolean isPrimeNumber(int n) {
            if (n < 2) return false;
            for (int i = 2; i * i <= n; i++) {
                if (n % i == 0) return false;
            }
            return true;
        }
    }

    @Nested
    @DisplayName("@ArgumentsSource")
    class ArgumentsSourceExamples {

        @ParameterizedTest(name = "Custom source: {0}, {1}")
        @ArgumentsSource(CustomArgumentsProvider.class)
        void withCustomProvider(String text, int number) {
            assertThat(text).isNotNull();
            assertThat(number).isPositive();
        }
    }

    static class CustomArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(
                org.junit.jupiter.api.extension.ExtensionContext context) {
            return Stream.of(
                    arguments("first", 1),
                    arguments("second", 2),
                    arguments("third", 3)
            );
        }
    }
}
