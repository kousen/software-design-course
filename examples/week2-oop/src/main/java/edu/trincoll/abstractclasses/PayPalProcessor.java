package edu.trincoll.abstractclasses;

public class PayPalProcessor extends PaymentProcessor {
    private String email;
    private String password;
    private boolean twoFactorEnabled;
    private double accountBalance;
    
    public PayPalProcessor(String email, String password) {
        super();
        this.email = email;
        this.password = password;
        this.twoFactorEnabled = false;
        this.accountBalance = 5000.00;
    }
    
    @Override
    public boolean validatePaymentDetails() {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            logTransaction("Invalid email address");
            return false;
        }
        
        if (password == null || password.length() < 8) {
            logTransaction("Invalid password");
            return false;
        }
        
        if (twoFactorEnabled) {
            logTransaction("Two-factor authentication required");
            boolean authenticated = simulateTwoFactorAuth();
            if (!authenticated) {
                logTransaction("Two-factor authentication failed");
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public boolean processPayment(double amount) {
        logTransaction("Logging into PayPal account: " + getMaskedEmail());
        
        simulateNetworkDelay();
        
        if (amount > accountBalance) {
            logTransaction("Insufficient PayPal balance");
            return false;
        }
        
        accountBalance -= amount;
        logTransaction("Payment processed. New balance: $" + String.format("%.2f", accountBalance));
        return true;
    }
    
    @Override
    public double calculateProcessingFee(double amount) {
        if (amount < 10) {
            return 0.30;
        } else if (amount < 100) {
            return amount * 0.029;
        } else {
            return amount * 0.025;
        }
    }
    
    @Override
    public String getPaymentMethodName() {
        return "PayPal";
    }
    
    private boolean simulateTwoFactorAuth() {
        logTransaction("Sending 2FA code to registered device");
        simulateNetworkDelay();
        return Math.random() > 0.1;
    }
    
    private void simulateNetworkDelay() {
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private String getMaskedEmail() {
        int atIndex = email.indexOf('@');
        if (atIndex > 2) {
            return email.substring(0, 2) + "***" + email.substring(atIndex);
        }
        return "***" + email.substring(atIndex);
    }
    
    public void enableTwoFactor() {
        this.twoFactorEnabled = true;
        logTransaction("Two-factor authentication enabled");
    }
    
    public void disableTwoFactor() {
        this.twoFactorEnabled = false;
        logTransaction("Two-factor authentication disabled");
    }
    
    public void addFunds(double amount) {
        if (amount > 0) {
            accountBalance += amount;
            logTransaction("Added $" + amount + " to PayPal balance");
        }
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public boolean isTwoFactorEnabled() {
        return twoFactorEnabled;
    }
    
    public double getAccountBalance() {
        return accountBalance;
    }
}