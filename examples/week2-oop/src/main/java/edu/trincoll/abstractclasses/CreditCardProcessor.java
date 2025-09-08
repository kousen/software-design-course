package edu.trincoll.abstractclasses;

import java.time.LocalDate;

public class CreditCardProcessor extends PaymentProcessor {
    private String cardNumber;
    private String cardHolderName;
    private LocalDate expiryDate;
    private String cvv;
    
    public CreditCardProcessor(String cardNumber, String cardHolderName, 
                              LocalDate expiryDate, String cvv) {
        super();
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }
    
    @Override
    public boolean validatePaymentDetails() {
        if (cardNumber == null || cardNumber.length() != 16) {
            logTransaction("Invalid card number");
            return false;
        }
        
        if (!cardNumber.matches("\\d+")) {
            logTransaction("Card number must contain only digits");
            return false;
        }
        
        if (cardHolderName == null || cardHolderName.trim().isEmpty()) {
            logTransaction("Card holder name is required");
            return false;
        }
        
        if (expiryDate == null || expiryDate.isBefore(LocalDate.now())) {
            logTransaction("Card has expired");
            return false;
        }
        
        if (cvv == null || cvv.length() != 3 || !cvv.matches("\\d+")) {
            logTransaction("Invalid CVV");
            return false;
        }
        
        return true;
    }
    
    @Override
    public boolean processPayment(double amount) {
        logTransaction("Connecting to payment gateway...");
        
        simulateNetworkDelay();
        
        logTransaction("Authorizing card ending in " + getLastFourDigits());
        
        if (amount > 10000) {
            logTransaction("Payment amount exceeds limit");
            return false;
        }
        
        if (Math.random() > 0.05) {
            logTransaction("Card authorized successfully");
            return true;
        } else {
            logTransaction("Card authorization failed");
            return false;
        }
    }
    
    @Override
    public double calculateProcessingFee(double amount) {
        double baseFee = 0.30;
        double percentageFee = amount * 0.029;
        return baseFee + percentageFee;
    }
    
    @Override
    public String getPaymentMethodName() {
        return "CreditCard";
    }
    
    private String getLastFourDigits() {
        return cardNumber.substring(cardNumber.length() - 4);
    }
    
    private void simulateNetworkDelay() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public String getMaskedCardNumber() {
        return "**** **** **** " + getLastFourDigits();
    }
    
    public String getCardNumber() {
        return cardNumber;
    }
    
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public String getCardHolderName() {
        return cardHolderName;
    }
    
    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }
    
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public String getCvv() {
        return cvv;
    }
    
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}