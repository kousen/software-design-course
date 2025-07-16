package edu.trincoll.solid.ocp;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class DiscountStrategyTest {
    
    @Test
    void regularCustomerGets5PercentDiscount() {
        DiscountStrategy strategy = new RegularCustomerDiscount();
        double discount = strategy.calculateDiscount(100.0);
        
        assertThat(discount).isCloseTo(5.0, within(0.01));
    }
    
    @Test
    void premiumCustomerGets10PercentDiscount() {
        DiscountStrategy strategy = new PremiumCustomerDiscount();
        double discount = strategy.calculateDiscount(100.0);
        
        assertThat(discount).isCloseTo(10.0, within(0.01));
    }
    
    @Test
    void vipCustomerGets20PercentDiscount() {
        DiscountStrategy strategy = new VIPCustomerDiscount();
        double discount = strategy.calculateDiscount(100.0);
        
        assertThat(discount).isCloseTo(20.0, within(0.01));
    }
    
    @Test
    void orderCalculatesTotalWithDiscount() {
        Order order = new Order("ORD-001", 100.0, new VIPCustomerDiscount());
        
        assertThat(order.calculateTotal()).isCloseTo(80.0, within(0.01));
        assertThat(order.getDiscountAmount()).isCloseTo(20.0, within(0.01));
        assertThat(order.getDiscountDescription()).isEqualTo("VIP customer discount (20%)");
    }
    
    @Test
    void canAddNewDiscountTypeWithoutModifyingExistingCode() {
        DiscountStrategy studentDiscount = new DiscountStrategy() {
            @Override
            public double calculateDiscount(double amount) {
                return amount * 0.15;
            }
            
            @Override
            public String getDescription() {
                return "Student discount (15%)";
            }
        };
        
        Order order = new Order("ORD-002", 100.0, studentDiscount);
        
        assertThat(order.calculateTotal()).isCloseTo(85.0, within(0.01));
        assertThat(order.getDiscountDescription()).isEqualTo("Student discount (15%)");
    }
}