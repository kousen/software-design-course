package edu.trincoll.patterns.structural.decorator;

/**
 * Concrete Decorator - Adds sugar to beverage.
 */
public class SugarDecorator extends BeverageDecorator {

    public SugarDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.25;
    }

    @Override
    public String description() {
        return beverage.description() + ", Sugar";
    }
}
