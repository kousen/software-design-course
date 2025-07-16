package edu.trincoll.solid.ocp;

public class PremiumCustomerDiscount implements DiscountStrategy {
    private static final double DISCOUNT_RATE = 0.10;
    
    @Override
    public double calculateDiscount(double amount) {
        return amount * DISCOUNT_RATE;
    }
    
    @Override
    public String getDescription() {
        return "Premium customer discount (10%)";
    }
}