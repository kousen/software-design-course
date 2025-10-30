package edu.trincoll.patterns.creational.singleton;

/**
 * Bill Pugh Singleton (Holder Pattern) - Thread-safe lazy initialization.
 *
 * Uses static inner class for lazy initialization without synchronization.
 * The inner class is loaded only when getInstance() is called.
 */
public class DatabaseConnection {

    private DatabaseConnection() {
        // Expensive initialization
        System.out.println("Creating database connection...");
    }

    private static class Holder {
        private static final DatabaseConnection INSTANCE = new DatabaseConnection();
    }

    public static DatabaseConnection getInstance() {
        return Holder.INSTANCE;
    }

    public void executeQuery(String query) {
        System.out.println("Executing: " + query);
    }
}
