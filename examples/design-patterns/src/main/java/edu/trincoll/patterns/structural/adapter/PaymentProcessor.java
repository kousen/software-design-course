package edu.trincoll.patterns.structural.adapter;

/**
 * Target interface - The interface our code expects.
 */
public interface PaymentProcessor {
    /**
     * Process a payment.
     * @param amount Payment amount in dollars
     * @param currency Currency code (e.g., "USD")
     * @return true if payment successful, false otherwise
     */
    boolean processPayment(double amount, String currency);
}
