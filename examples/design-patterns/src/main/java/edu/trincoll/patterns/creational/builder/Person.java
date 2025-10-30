package edu.trincoll.patterns.creational.builder;

/**
 * Builder pattern - Step-by-step construction of complex objects.
 *
 * Benefits:
 * - Clear, readable construction
 * - Flexible parameter handling
 * - Validation before construction
 * - Immutable result objects
 *
 * Use when:
 * - 4+ constructor parameters
 * - Many optional parameters
 * - Need validation before construction
 */
public class Person {
    // Immutable fields
    private final String name;
    private final int age;
    private final String email;
    private final String phone;
    private final String address;

    // Private constructor - only Builder can create
    private Person(String name, int age, String email, String phone, String address) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Static factory for Builder
    public static Builder builder() {
        return new Builder();
    }

    // Getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }

    @Override
    public String toString() {
        return "Person{name='%s', age=%d, email='%s', phone='%s', address='%s'}"
            .formatted(name, age, email, phone, address);
    }

    /**
     * Inner static Builder class.
     */
    public static class Builder {
        private String name;
        private int age;
        private String email;
        private String phone;
        private String address;

        // Fluent setters return this for chaining
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        /**
         * Builds the Person with validation.
         */
        public Person build() {
            // Validate required fields
            if (name == null || name.isBlank()) {
                throw new IllegalStateException("name is required");
            }
            if (age < 0) {
                throw new IllegalStateException("age must be non-negative");
            }

            // Construct and return immutable object
            return new Person(name, age, email, phone, address);
        }
    }
}
