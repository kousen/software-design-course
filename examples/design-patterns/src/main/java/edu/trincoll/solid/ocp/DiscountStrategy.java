package edu.trincoll.solid.ocp;

public interface DiscountStrategy {
    double calculateDiscount(double amount);
    String getDescription();
}