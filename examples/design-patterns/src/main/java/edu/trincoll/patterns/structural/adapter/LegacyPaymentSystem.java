package edu.trincoll.patterns.structural.adapter;

/**
 * Adaptee - Existing legacy system with incompatible interface.
 * We cannot modify this class (third-party library).
 */
public class LegacyPaymentSystem {

    /**
     * Legacy method that accepts amount in cents and returns status code.
     * @param amountInCents Payment amount in cents
     * @return Status code (200 = success, 400 = failure)
     */
    public int charge(int amountInCents) {
        System.out.println("Legacy system charging " + amountInCents + " cents");
        // Simulate processing
        return amountInCents > 0 ? 200 : 400;
    }
}
