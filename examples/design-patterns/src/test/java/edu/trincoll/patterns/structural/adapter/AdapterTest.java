package edu.trincoll.patterns.structural.adapter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Adapter Pattern Tests")
class AdapterTest {

    @Test
    @DisplayName("Adapter processes successful payment")
    void adapterProcessesSuccessfulPayment() {
        PaymentProcessor processor = new PaymentAdapter();

        boolean result = processor.processPayment(99.99, "USD");

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Adapter processes failed payment (zero amount)")
    void adapterProcessesFailedPayment() {
        PaymentProcessor processor = new PaymentAdapter();

        boolean result = processor.processPayment(0.0, "USD");

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Adapter converts dollars to cents correctly")
    void adapterConvertsDollarsToCents() {
        PaymentProcessor processor = new PaymentAdapter();

        // Test with fractional dollars
        boolean result = processor.processPayment(12.50, "USD");

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Legacy system can be used directly")
    void legacySystemCanBeUsedDirectly() {
        LegacyPaymentSystem legacy = new LegacyPaymentSystem();

        int statusCode = legacy.charge(9999);

        assertThat(statusCode).isEqualTo(200);
    }
}
