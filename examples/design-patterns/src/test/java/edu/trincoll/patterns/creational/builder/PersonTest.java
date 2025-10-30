package edu.trincoll.patterns.creational.builder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Builder Pattern Tests")
class PersonTest {

    @Test
    @DisplayName("Build person with all fields")
    void buildPersonWithAllFields() {
        Person person = Person.builder()
            .name("Alice")
            .age(30)
            .email("alice@example.com")
            .phone("555-1234")
            .address("123 Main St")
            .build();

        assertThat(person.getName()).isEqualTo("Alice");
        assertThat(person.getAge()).isEqualTo(30);
        assertThat(person.getEmail()).isEqualTo("alice@example.com");
        assertThat(person.getPhone()).isEqualTo("555-1234");
        assertThat(person.getAddress()).isEqualTo("123 Main St");
    }

    @Test
    @DisplayName("Build person with only required fields")
    void buildPersonWithRequiredFields() {
        Person person = Person.builder()
            .name("Bob")
            .age(25)
            .build();

        assertThat(person.getName()).isEqualTo("Bob");
        assertThat(person.getAge()).isEqualTo(25);
        assertThat(person.getEmail()).isNull();
    }

    @Test
    @DisplayName("Builder throws exception when name is missing")
    void builderThrowsWhenNameMissing() {
        assertThatThrownBy(() ->
            Person.builder()
                .age(30)
                .build()
        )
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("name is required");
    }

    @Test
    @DisplayName("Builder throws exception when name is blank")
    void builderThrowsWhenNameBlank() {
        assertThatThrownBy(() ->
            Person.builder()
                .name("   ")
                .age(30)
                .build()
        )
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("name is required");
    }

    @Test
    @DisplayName("Builder throws exception when age is negative")
    void builderThrowsWhenAgeNegative() {
        assertThatThrownBy(() ->
            Person.builder()
                .name("Charlie")
                .age(-5)
                .build()
        )
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("age must be non-negative");
    }

    @Test
    @DisplayName("Builder allows flexible parameter order")
    void builderAllowsFlexibleOrder() {
        Person person = Person.builder()
            .email("test@example.com")
            .name("Diana")
            .phone("555-9999")
            .age(40)
            .build();

        assertThat(person.getName()).isEqualTo("Diana");
        assertThat(person.getAge()).isEqualTo(40);
    }
}
