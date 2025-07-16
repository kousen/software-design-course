package edu.trincoll;

import org.apache.commons.validator.routines.ISBNValidator;
import org.apache.commons.validator.routines.UrlValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.EnumSource.Mode.MATCH_ALL;

public class ParameterizedTests {
    
    @ParameterizedTest(name = "{0} is prime and less than 20")
    @ValueSource(ints = {2, 3, 5, 7, 11, 13, 17, 19})
    void valueIsPrime(int arg) {
        assertTrue(UtilityMethods.isPrime(arg));
    }

    @ParameterizedTest(name = "{0} is composite and less than or equal to 20")
    @ValueSource(ints = {4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20})
    void valueIsComposite(int argument) {
        assertFalse(UtilityMethods.isPrime(argument));
    }

    @ParameterizedTest(name = "{0} is prime")
    @MethodSource("primesLessThan100")
    void checkPrimesLessThan100(int arg) {
        assertTrue(UtilityMethods.isPrime(arg));
    }

    private static IntStream primesLessThan100() {
        return IntStream.rangeClosed(2, 100)
                .filter(UtilityMethods::isPrime);
    }

    @ParameterizedTest(name = "max of {0} and {1} is {2}")
    @MethodSource("maxWithArgsList")
    void testMax(int x, int y, int max) {
        assertTrue(max >= x && max >= y);
        assertTrue(max == x || max == y);
    }

    private static List<Arguments> maxWithArgsList() {
        return List.of(
                Arguments.of(1, 2, 2),
                Arguments.of(7, 3, 7),
                Arguments.of(5, 5, 5),
                Arguments.of(-1, -5, -1)
        );
    }

    @ParameterizedTest(name = "The string \"{0}\" is not empty")
    @ValueSource(strings = {"this", "is", "a", "list", "of", "strings", "  "})
    void noneAreEmpty(String argument) {
        assertThat(argument).isNotEmpty();
    }

    @ParameterizedTest
    @EnumSource(Month.class)
    void monthsEnum(Month month) {
        assertAll(
                () -> assertNotNull(month),
                () -> assertTrue(month.getValue() >= 1 && month.getValue() <= 12)
        );
    }

    @ParameterizedTest
    @EnumSource(mode = MATCH_ALL, names = "^.*DAYS$")
    void testWithEnumSourceRegex(ChronoUnit unit) {
        assertTrue(unit.name().endsWith("DAYS"));
    }

    @ParameterizedTest
    @CsvSource({
            "Modern Java Recipes, https://www.oreilly.com/library/view/modern-java-recipes/9781491973165/",
            "Kotlin Cookbook, https://www.oreilly.com/library/view/kotlin-cookbook/9781492046660/",
            "Gradle Recipes for Android, https://www.oreilly.com/library/view/gradle-recipes-for/9781491947272/"
    })
    void courseList(String title, String url) {
        assertAll(
                () -> assertNotNull(title),
                () -> assertTrue(UrlValidator.getInstance().isValid(url))
        );
    }

    @ParameterizedTest
    @MethodSource("getBooks")
    void testBooks(Book book) {
        LocalDate now = LocalDate.now();
        LocalDate twentyThirteen = LocalDate.of(2013, Month.JANUARY, 1);
        assertAll(
                () -> assertTrue(ISBNValidator.getInstance().isValidISBN10(book.isbn())),
                () -> assertNotNull(book.title()),
                () -> assertNotNull(book.author()),
                () -> assertTrue(book.publicationDate().isAfter(twentyThirteen) &&
                                 book.publicationDate().isBefore(now))
        );
    }

    private static Stream<Book> getBooks() {
        return Stream.of(
                new Book("1935182943", "Making Java Groovy", "Ken Kousen", LocalDate.parse("2013-09-30")),
                new Book("1491947020", "Gradle Recipes for Android", "Ken Kousen", LocalDate.parse("2016-06-17")),
                new Book("149197317X", "Modern Java Recipes", "Ken Kousen", LocalDate.parse("2017-08-26")),
                new Book("1492046671", "Kotlin Cookbook", "Ken Kousen", LocalDate.parse("2019-12-03"))
        );
    }
}