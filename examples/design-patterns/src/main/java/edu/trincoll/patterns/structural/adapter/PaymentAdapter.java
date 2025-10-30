package edu.trincoll.patterns.structural.adapter;

/**
 * Adapter - Converts PaymentProcessor interface to LegacyPaymentSystem.
 *
 * Adapter Pattern Benefits:
 * - Reuse existing code without modification
 * - Single Responsibility (adapter only does conversion)
 * - Open-Closed Principle (add adapters without changing code)
 */
public class PaymentAdapter implements PaymentProcessor {
    private final LegacyPaymentSystem legacySystem;

    public PaymentAdapter() {
        this.legacySystem = new LegacyPaymentSystem();
    }

    @Override
    public boolean processPayment(double amount, String currency) {
        // Convert dollars to cents
        int amountInCents = (int) (amount * 100);

        // Call legacy system
        int statusCode = legacySystem.charge(amountInCents);

        // Convert status code to boolean
        return statusCode == 200;
    }
}
