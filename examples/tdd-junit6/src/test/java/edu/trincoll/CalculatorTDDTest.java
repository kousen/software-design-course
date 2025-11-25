package edu.trincoll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

/**
 * TDD demonstration with Calculator.
 * Tests organized using nested classes for clarity.
 */
@DisplayName("Calculator")
class CalculatorTDDTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Nested
    @DisplayName("add()")
    class AddTests {

        @Test
        @DisplayName("adds two positive numbers")
        void addsTwoPositiveNumbers() {
            assertThat(calculator.add(2, 3)).isEqualTo(5);
        }

        @Test
        @DisplayName("adds positive and negative numbers")
        void addsPositiveAndNegative() {
            assertThat(calculator.add(5, -3)).isEqualTo(2);
        }

        @Test
        @DisplayName("adds zero to a number")
        void addsZero() {
            assertThat(calculator.add(7, 0)).isEqualTo(7);
        }

        @ParameterizedTest(name = "{0} + {1} = {2}")
        @CsvSource({
                "0, 0, 0",
                "1, 1, 2",
                "-1, -1, -2",
                "100, 200, 300",
                "-50, 50, 0"
        })
        @DisplayName("adds various number pairs correctly")
        void addsVariousPairs(int a, int b, int expected) {
            assertThat(calculator.add(a, b)).isEqualTo(expected);
        }

        @Test
        @DisplayName("adds multiple numbers using varargs")
        void addsMultipleNumbers() {
            assertThat(calculator.add(1, 2, 3, 4, 5)).isEqualTo(15);
        }
    }

    @Nested
    @DisplayName("subtract()")
    class SubtractTests {

        @ParameterizedTest(name = "{0} - {1} = {2}")
        @CsvSource({
                "5, 3, 2",
                "10, 10, 0",
                "0, 5, -5",
                "-5, -3, -2"
        })
        void subtractsCorrectly(int a, int b, int expected) {
            assertThat(calculator.subtract(a, b)).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("multiply()")
    class MultiplyTests {

        @ParameterizedTest(name = "{0} * {1} = {2}")
        @CsvSource({
                "2, 3, 6",
                "0, 100, 0",
                "-2, 3, -6",
                "-2, -3, 6"
        })
        void multipliesCorrectly(int a, int b, int expected) {
            assertThat(calculator.multiply(a, b)).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("divide()")
    class DivideTests {

        @ParameterizedTest(name = "{0} / {1} = {2}")
        @CsvSource({
                "6, 2, 3",
                "10, 3, 3",
                "0, 5, 0",
                "-6, 2, -3"
        })
        void dividesCorrectly(int a, int b, int expected) {
            assertThat(calculator.divide(a, b)).isEqualTo(expected);
        }

        @Test
        @DisplayName("throws ArithmeticException when dividing by zero")
        void throwsWhenDividingByZero() {
            assertThatThrownBy(() -> calculator.divide(10, 0))
                    .isInstanceOf(ArithmeticException.class)
                    .hasMessageContaining("zero");
        }
    }

    @Nested
    @DisplayName("isEven()")
    class IsEvenTests {

        @ParameterizedTest(name = "{0} is even")
        @ValueSource(ints = {0, 2, 4, 100, -2, -100})
        void recognizesEvenNumbers(int number) {
            assertThat(calculator.isEven(number)).isTrue();
        }

        @ParameterizedTest(name = "{0} is odd")
        @ValueSource(ints = {1, 3, 5, 99, -1, -99})
        void recognizesOddNumbers(int number) {
            assertThat(calculator.isEven(number)).isFalse();
        }
    }

    @Nested
    @DisplayName("isPrime()")
    class IsPrimeTests {

        @ParameterizedTest(name = "{0} is prime")
        @ValueSource(ints = {2, 3, 5, 7, 11, 13, 17, 19, 23, 97})
        void recognizesPrimeNumbers(int number) {
            assertThat(calculator.isPrime(number)).isTrue();
        }

        @ParameterizedTest(name = "{0} is not prime")
        @ValueSource(ints = {0, 1, 4, 6, 8, 9, 10, 15, 100})
        void recognizesNonPrimeNumbers(int number) {
            assertThat(calculator.isPrime(number)).isFalse();
        }
    }
}
