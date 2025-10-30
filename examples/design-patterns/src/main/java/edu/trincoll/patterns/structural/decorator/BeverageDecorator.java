package edu.trincoll.patterns.structural.decorator;

/**
 * Base Decorator - Wraps a Beverage and delegates to it.
 *
 * Decorator Pattern Benefits:
 * - Add responsibilities dynamically at runtime
 * - Open-Closed Principle (add decorators without modifying code)
 * - Single Responsibility (each decorator has one job)
 * - Flexible composition (combine in any order)
 */
public abstract class BeverageDecorator implements Beverage {
    protected final Beverage beverage;

    public BeverageDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public double cost() {
        return beverage.cost();
    }

    @Override
    public String description() {
        return beverage.description();
    }
}
