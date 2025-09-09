package edu.trincoll.abstractclasses;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class PaymentProcessor {
    protected double amount;
    protected String currency;
    protected LocalDateTime transactionTime;
    protected final List<String> transactionLog;
    protected boolean isProcessing;
    
    public PaymentProcessor() {
        this.currency = "USD";
        this.transactionLog = new ArrayList<>();
        this.isProcessing = false;
    }
    
    public abstract boolean validatePaymentDetails();
    
    public abstract boolean processPayment(double amount);
    
    public abstract double calculateProcessingFee(double amount);
    
    public abstract String getPaymentMethodName();
    
    public final PaymentResult makePayment(double amount) {
        if (isProcessing) {
            return new PaymentResult(false, "Another payment is already in progress", null);
        }
        
        isProcessing = true;
        this.amount = amount;
        this.transactionTime = LocalDateTime.now();
        
        try {
            logTransaction("Starting payment of " + amount + " " + currency);
            
            if (!validatePaymentDetails()) {
                logTransaction("Payment validation failed");
                return new PaymentResult(false, "Invalid payment details", null);
            }
            
            double fee = calculateProcessingFee(amount);
            double totalAmount = amount + fee;
            
            logTransaction("Processing fee: " + fee);
            
            if (processPayment(totalAmount)) {
                String transactionId = generateTransactionId();
                logTransaction("Payment successful. Transaction ID: " + transactionId);
                sendConfirmation(transactionId, totalAmount);
                return new PaymentResult(true, "Payment processed successfully", transactionId);
            } else {
                logTransaction("Payment processing failed");
                return new PaymentResult(false, "Payment processing failed", null);
            }
        } finally {
            isProcessing = false;
        }
    }
    
    protected void logTransaction(String message) {
        String logEntry = String.format("[%s] %s: %s", 
                LocalDateTime.now(), getPaymentMethodName(), message);
        transactionLog.add(logEntry);
        System.out.println(logEntry);
    }
    
    protected String generateTransactionId() {
        return getPaymentMethodName().substring(0, 3).toUpperCase() + 
               System.currentTimeMillis();
    }
    
    protected void sendConfirmation(String transactionId, double amount) {
        System.out.println("Confirmation sent for transaction " + transactionId);
    }
    
    public List<String> getTransactionLog() {
        return new ArrayList<>(transactionLog);
    }
    
    public void clearTransactionLog() {
        transactionLog.clear();
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public record PaymentResult(boolean success, String message, String transactionId) {
    }
}