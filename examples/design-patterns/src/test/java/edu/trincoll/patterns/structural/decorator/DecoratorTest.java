package edu.trincoll.patterns.structural.decorator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Decorator Pattern Tests")
class DecoratorTest {

    @Test
    @DisplayName("Basic coffee has correct cost and description")
    void basicCoffee() {
        Beverage coffee = new Coffee();

        assertThat(coffee.cost()).isEqualTo(2.00);
        assertThat(coffee.description()).isEqualTo("Coffee");
    }

    @Test
    @DisplayName("Coffee with milk adds cost and description")
    void coffeeWithMilk() {
        Beverage coffee = new MilkDecorator(new Coffee());

        assertThat(coffee.cost()).isEqualTo(2.50);
        assertThat(coffee.description()).isEqualTo("Coffee, Milk");
    }

    @Test
    @DisplayName("Coffee with sugar adds cost and description")
    void coffeeWithSugar() {
        Beverage coffee = new SugarDecorator(new Coffee());

        assertThat(coffee.cost()).isEqualTo(2.25);
        assertThat(coffee.description()).isEqualTo("Coffee, Sugar");
    }

    @Test
    @DisplayName("Coffee with milk and sugar stacks decorators")
    void coffeeWithMilkAndSugar() {
        Beverage coffee = new SugarDecorator(
            new MilkDecorator(new Coffee())
        );

        assertThat(coffee.cost()).isEqualTo(2.75);
        assertThat(coffee.description()).isEqualTo("Coffee, Milk, Sugar");
    }

    @Test
    @DisplayName("Coffee with all add-ons")
    void coffeeWithAllAddOns() {
        Beverage coffee = new WhippedCreamDecorator(
            new SugarDecorator(
                new MilkDecorator(new Coffee())
            )
        );

        assertThat(coffee.cost()).isEqualTo(3.50);
        assertThat(coffee.description()).isEqualTo("Coffee, Milk, Sugar, Whipped Cream");
    }

    @Test
    @DisplayName("Can apply decorators in any order")
    void decoratorsInDifferentOrder() {
        Beverage coffee1 = new MilkDecorator(new SugarDecorator(new Coffee()));
        Beverage coffee2 = new SugarDecorator(new MilkDecorator(new Coffee()));

        // Same cost regardless of order
        assertThat(coffee1.cost()).isEqualTo(coffee2.cost());
        // But different descriptions
        assertThat(coffee1.description()).isEqualTo("Coffee, Sugar, Milk");
        assertThat(coffee2.description()).isEqualTo("Coffee, Milk, Sugar");
    }
}
