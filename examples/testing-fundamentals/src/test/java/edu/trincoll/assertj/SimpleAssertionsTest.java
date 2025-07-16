package edu.trincoll.assertj;

import edu.trincoll.Person;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleAssertionsTest {
    
    @Test
    void aFewSimpleAssertions() {
        String title = "The Lord of the Rings";
        assertThat(title)
                .isNotNull()
                .startsWith("The")
                .contains("Lord")
                .endsWith(" Rings");
    }

    @Test
    void personAssertions() {
        Person jeanLuc = new Person("Jean-Luc", "Picard",
                LocalDate.of(2305, Month.JULY, 13));

        assertThat(jeanLuc)
                .extracting(Person::first, Person::last)
                .containsExactly("Jean-Luc", "Picard");

        assertThat(jeanLuc)
                .extracting(Person::first, Person::last, Person::dob)
                .containsExactly("Jean-Luc", "Picard", LocalDate.of(2305, Month.JULY, 13));
    }
    
    @Test
    void collectionAssertions() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        
        assertThat(names)
                .hasSize(3)
                .contains("Alice", "Bob")
                .doesNotContain("David")
                .startsWith("Alice")
                .endsWith("Charlie");
    }
    
    @Test
    void numericalAssertions() {
        assertThat(42)
                .isEqualTo(42)
                .isNotEqualTo(41)
                .isGreaterThan(40)
                .isLessThan(50)
                .isBetween(40, 50);
    }
    
    @Test
    void booleanAssertions() {
        assertThat(true)
                .isTrue()
                .isNotEqualTo(false);
                
        assertThat(false)
                .isFalse()
                .isNotEqualTo(true);
    }
    
    @Test
    void exceptionAssertions() {
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> {
            throw new IllegalArgumentException("boom!");
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("boom!");
    }
}