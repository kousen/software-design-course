package edu.trincoll.patterns.structural.proxy;

/**
 * RealSubject - The actual object with expensive initialization.
 */
public class ExpensiveService implements Service {

    public ExpensiveService() {
        // Simulate expensive initialization
        System.out.println("Creating expensive service (takes time)...");
        try {
            Thread.sleep(1000); // Simulate delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void doWork() {
        System.out.println("ExpensiveService doing work");
    }
}
