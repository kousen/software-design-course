package edu.trincoll.patterns.creational.singleton;

/**
 * Eager Singleton - Instance created when class loads.
 *
 * Thread-safe by default (class loading is thread-safe).
 * Simple but always creates instance even if never used.
 */
public class EagerSingleton {
    private static final EagerSingleton INSTANCE = new EagerSingleton();

    private EagerSingleton() {
        // Private constructor prevents external instantiation
    }

    public static EagerSingleton getInstance() {
        return INSTANCE;
    }
}
