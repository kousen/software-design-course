package edu.trincoll.solid.lsp;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ShapeTest {
    
    @Test
    void rectangleFollowsLSP() {
        Shape rectangle = new Rectangle(4, 5);
        
        assertAll(
            () -> assertThat(rectangle.getArea()).isEqualTo(20.0),
            () -> assertThat(rectangle.getPerimeter()).isEqualTo(18.0),
            () -> assertThat(rectangle.getName()).isEqualTo("Rectangle")
        );
    }
    
    @Test
    void squareFollowsLSP() {
        Shape square = new Square(4);
        
        assertAll(
            () -> assertThat(square.getArea()).isEqualTo(16.0),
            () -> assertThat(square.getPerimeter()).isEqualTo(16.0),
            () -> assertThat(square.getName()).isEqualTo("Square")
        );
    }
    
    @Test
    void circleFollowsLSP() {
        Shape circle = new Circle(3);
        
        assertAll(
            () -> assertThat(circle.getArea()).isCloseTo(28.27, within(0.01)),
            () -> assertThat(circle.getPerimeter()).isCloseTo(18.85, within(0.01)),
            () -> assertThat(circle.getName()).isEqualTo("Circle")
        );
    }
    
    @Test
    void allShapesCanBeUsedPolymorphically() {
        Shape[] shapes = {
            new Rectangle(3, 4),
            new Square(3),
            new Circle(2)
        };
        
        for (Shape shape : shapes) {
            assertThat(shape.getArea()).isGreaterThan(0);
            assertThat(shape.getPerimeter()).isGreaterThan(0);
            assertThat(shape.getName()).isNotNull();
        }
    }
    
    @Test
    void rectangleCanBeModified() {
        Rectangle rectangle = new Rectangle(4, 5);
        
        rectangle.setWidth(6);
        rectangle.setHeight(7);
        
        assertAll(
            () -> assertThat(rectangle.getWidth()).isEqualTo(6),
            () -> assertThat(rectangle.getHeight()).isEqualTo(7),
            () -> assertThat(rectangle.getArea()).isEqualTo(42.0)
        );
    }
    
    @Test
    void squareCanBeModified() {
        Square square = new Square(4);
        
        square.setSide(5);
        
        assertAll(
            () -> assertThat(square.getSide()).isEqualTo(5),
            () -> assertThat(square.getArea()).isEqualTo(25.0)
        );
    }
    
    private org.assertj.core.data.Offset<Double> within(double offset) {
        return org.assertj.core.data.Offset.offset(offset);
    }
}