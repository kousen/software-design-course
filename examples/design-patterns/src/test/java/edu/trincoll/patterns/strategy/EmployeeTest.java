package edu.trincoll.patterns.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Employee Tests")
class EmployeeTest {

    @Test
    @DisplayName("Should create employee with valid data")
    void shouldCreateEmployeeWithValidData() {
        var hireDate = LocalDate.of(2020, 1, 15);
        var employee = new Employee("Alice Johnson", 1001, hireDate);

        assertThat(employee.name()).isEqualTo("Alice Johnson");
        assertThat(employee.id()).isEqualTo(1001);
        assertThat(employee.hireDate()).isEqualTo(hireDate);
    }

    @Test
    @DisplayName("Should reject null name")
    void shouldRejectNullName() {
        assertThatThrownBy(() ->
            new Employee(null, 1001, LocalDate.of(2020, 1, 15)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Name cannot be null or blank");
    }

    @Test
    @DisplayName("Should reject blank name")
    void shouldRejectBlankName() {
        assertThatThrownBy(() ->
            new Employee("   ", 1001, LocalDate.of(2020, 1, 15)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Name cannot be null or blank");
    }

    @Test
    @DisplayName("Should reject non-positive ID")
    void shouldRejectNonPositiveId() {
        assertThatThrownBy(() ->
            new Employee("Alice", 0, LocalDate.of(2020, 1, 15)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("ID must be positive");

        assertThatThrownBy(() ->
            new Employee("Alice", -1, LocalDate.of(2020, 1, 15)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("ID must be positive");
    }

    @Test
    @DisplayName("Should reject null hire date")
    void shouldRejectNullHireDate() {
        assertThatThrownBy(() ->
            new Employee("Alice", 1001, null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Hire date cannot be null");
    }
}
