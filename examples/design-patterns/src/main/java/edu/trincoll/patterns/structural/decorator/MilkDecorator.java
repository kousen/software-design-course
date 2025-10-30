package edu.trincoll.patterns.structural.decorator;

/**
 * Concrete Decorator - Adds milk to beverage.
 */
public class MilkDecorator extends BeverageDecorator {

    public MilkDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.50;
    }

    @Override
    public String description() {
        return beverage.description() + ", Milk";
    }
}
