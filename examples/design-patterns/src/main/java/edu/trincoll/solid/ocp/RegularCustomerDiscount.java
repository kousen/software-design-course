package edu.trincoll.solid.ocp;

public class RegularCustomerDiscount implements DiscountStrategy {
    private static final double DISCOUNT_RATE = 0.05;
    
    @Override
    public double calculateDiscount(double amount) {
        return amount * DISCOUNT_RATE;
    }
    
    @Override
    public String getDescription() {
        return "Regular customer discount (5%)";
    }
}