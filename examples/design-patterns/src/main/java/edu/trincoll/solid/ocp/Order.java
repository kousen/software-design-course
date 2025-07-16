package edu.trincoll.solid.ocp;

public class Order {
    private final String orderId;
    private final double amount;
    private final DiscountStrategy discountStrategy;
    
    public Order(String orderId, double amount, DiscountStrategy discountStrategy) {
        this.orderId = orderId;
        this.amount = amount;
        this.discountStrategy = discountStrategy;
    }
    
    public double calculateTotal() {
        double discount = discountStrategy.calculateDiscount(amount);
        return amount - discount;
    }
    
    public double getDiscountAmount() {
        return discountStrategy.calculateDiscount(amount);
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public String getDiscountDescription() {
        return discountStrategy.getDescription();
    }
}