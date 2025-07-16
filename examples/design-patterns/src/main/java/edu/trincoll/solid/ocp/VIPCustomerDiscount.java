package edu.trincoll.solid.ocp;

public class VIPCustomerDiscount implements DiscountStrategy {
    private static final double DISCOUNT_RATE = 0.20;
    
    @Override
    public double calculateDiscount(double amount) {
        return amount * DISCOUNT_RATE;
    }
    
    @Override
    public String getDescription() {
        return "VIP customer discount (20%)";
    }
}