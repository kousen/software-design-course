package edu.trincoll.abstractclasses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.within;

class PaymentProcessorTest {
    
    @Nested
    @DisplayName("CreditCard Processor Tests")
    class CreditCardProcessorTests {
        private CreditCardProcessor processor;
        
        @BeforeEach
        void setUp() {
            processor = new CreditCardProcessor(
                    "1234567890123456",
                    "John Doe",
                    LocalDate.now().plusMonths(6),
                    "123"
            );
        }
        
        @Test
        @DisplayName("Should validate valid credit card details")
        void testValidCreditCard() {
            assertThat(processor.validatePaymentDetails()).isTrue();
        }
        
        @Test
        @DisplayName("Should reject invalid card number")
        void testInvalidCardNumber() {
            processor.setCardNumber("123");
            assertThat(processor.validatePaymentDetails()).isFalse();
            
            processor.setCardNumber("123456789012345A");
            assertThat(processor.validatePaymentDetails()).isFalse();
        }
        
        @Test
        @DisplayName("Should reject expired card")
        void testExpiredCard() {
            processor.setExpiryDate(LocalDate.now().minusMonths(1));
            assertThat(processor.validatePaymentDetails()).isFalse();
        }
        
        @Test
        @DisplayName("Should calculate processing fee correctly")
        void testProcessingFee() {
            double fee = processor.calculateProcessingFee(100);
            assertThat(fee).isCloseTo(3.20, within(0.01));
        }
        
        @Test
        @DisplayName("Should process payment successfully")
        void testSuccessfulPayment() {
            PaymentProcessor.PaymentResult result = processor.makePayment(100);
            
            assertThat(result.isSuccess()).isTrue();
            assertThat(result.getMessage()).contains("success");
            assertThat(result.getTransactionId()).isNotNull();
        }
        
        @Test
        @DisplayName("Should mask card number correctly")
        void testMaskedCardNumber() {
            assertThat(processor.getMaskedCardNumber()).isEqualTo("**** **** **** 3456");
        }
    }
    
    @Nested
    @DisplayName("PayPal Processor Tests")
    class PayPalProcessorTests {
        private PayPalProcessor processor;
        
        @BeforeEach
        void setUp() {
            processor = new PayPalProcessor("user@example.com", "password123");
        }
        
        @Test
        @DisplayName("Should validate valid PayPal credentials")
        void testValidPayPalCredentials() {
            assertThat(processor.validatePaymentDetails()).isTrue();
        }
        
        @Test
        @DisplayName("Should reject invalid email")
        void testInvalidEmail() {
            processor.setEmail("notanemail");
            assertThat(processor.validatePaymentDetails()).isFalse();
        }
        
        @Test
        @DisplayName("Should reject short password")
        void testShortPassword() {
            PayPalProcessor shortPass = new PayPalProcessor("user@example.com", "pass");
            assertThat(shortPass.validatePaymentDetails()).isFalse();
        }
        
        @Test
        @DisplayName("Should calculate tiered processing fee")
        void testTieredFee() {
            assertThat(processor.calculateProcessingFee(5)).isEqualTo(0.30);
            assertThat(processor.calculateProcessingFee(50)).isCloseTo(1.45, within(0.01));
            assertThat(processor.calculateProcessingFee(200)).isEqualTo(5.0);
        }
        
        @Test
        @DisplayName("Should manage account balance")
        void testAccountBalance() {
            processor.addFunds(1000);
            assertThat(processor.getAccountBalance()).isEqualTo(6000);
            
            PaymentProcessor.PaymentResult result = processor.makePayment(100);
            assertThat(result.isSuccess()).isTrue();
        }
        
        @Test
        @DisplayName("Should reject payment when insufficient balance")
        void testInsufficientBalance() {
            PaymentProcessor.PaymentResult result = processor.makePayment(10000);
            assertThat(result.isSuccess()).isFalse();
        }
    }
    
    @Nested
    @DisplayName("Abstract Class Polymorphism Tests")
    class PolymorphismTests {
        
        @Test
        @DisplayName("Should demonstrate template method pattern")
        void testTemplateMethodPattern() {
            PaymentProcessor creditCard = new CreditCardProcessor(
                    "1234567890123456",
                    "John Doe",
                    LocalDate.now().plusYears(1),
                    "123"
            );
            
            PaymentProcessor paypal = new PayPalProcessor(
                    "user@example.com",
                    "password123"
            );
            
            PaymentProcessor.PaymentResult ccResult = creditCard.makePayment(50);
            PaymentProcessor.PaymentResult ppResult = paypal.makePayment(50);
            
            assertThat(ccResult.isSuccess()).isTrue();
            assertThat(ppResult.isSuccess()).isTrue();
            
            assertThat(creditCard.getTransactionLog()).isNotEmpty();
            assertThat(paypal.getTransactionLog()).isNotEmpty();
        }
        
        @Test
        @DisplayName("Should handle different payment methods polymorphically")
        void testPolymorphicPaymentProcessing() {
            PaymentProcessor[] processors = {
                    new CreditCardProcessor("1234567890123456", "User", LocalDate.now().plusYears(1), "123"),
                    new PayPalProcessor("test@example.com", "secure123")
            };
            
            for (PaymentProcessor processor : processors) {
                assertThat(processor.getPaymentMethodName()).isNotEmpty();
                assertThat(processor.calculateProcessingFee(100)).isPositive();
                
                if (processor.validatePaymentDetails()) {
                    PaymentProcessor.PaymentResult result = processor.makePayment(25);
                    assertThat(result).isNotNull();
                }
            }
        }
    }
}