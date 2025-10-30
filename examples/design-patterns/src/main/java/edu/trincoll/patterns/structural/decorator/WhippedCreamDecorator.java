package edu.trincoll.patterns.structural.decorator;

/**
 * Concrete Decorator - Adds whipped cream to beverage.
 */
public class WhippedCreamDecorator extends BeverageDecorator {

    public WhippedCreamDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.75;
    }

    @Override
    public String description() {
        return beverage.description() + ", Whipped Cream";
    }
}
