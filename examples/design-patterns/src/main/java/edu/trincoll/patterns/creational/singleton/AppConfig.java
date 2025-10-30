package edu.trincoll.patterns.creational.singleton;

import java.util.Properties;

/**
 * Enum Singleton - Best practice for implementing Singleton pattern.
 *
 * Advantages:
 * - Thread-safe (JVM guarantees)
 * - Serialization-safe
 * - Reflection-safe
 * - Simplest implementation
 *
 * Joshua Bloch (Effective Java): "A single-element enum type is often
 * the best way to implement a singleton"
 */
public enum AppConfig {
    INSTANCE;

    private final Properties properties;

    AppConfig() {
        properties = new Properties();
        // Simulate loading configuration
        properties.setProperty("app.name", "Design Patterns Demo");
        properties.setProperty("app.version", "1.0");
        properties.setProperty("database.url", "jdbc:postgresql://localhost/demo");
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public void set(String key, String value) {
        properties.setProperty(key, value);
    }
}
