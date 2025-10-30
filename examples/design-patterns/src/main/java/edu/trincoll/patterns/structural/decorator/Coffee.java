package edu.trincoll.patterns.structural.decorator;

/**
 * Concrete Component - Basic beverage implementation.
 */
public class Coffee implements Beverage {

    @Override
    public double cost() {
        return 2.00;
    }

    @Override
    public String description() {
        return "Coffee";
    }
}
