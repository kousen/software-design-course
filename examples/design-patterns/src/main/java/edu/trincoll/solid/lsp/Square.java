package edu.trincoll.solid.lsp;

public class Square extends Shape {
    private double side;
    
    public Square(double side) {
        super("Square");
        this.side = side;
    }
    
    public double getSide() {
        return side;
    }
    
    public void setSide(double side) {
        this.side = side;
    }
    
    @Override
    public double getArea() {
        return side * side;
    }
    
    @Override
    public double getPerimeter() {
        return 4 * side;
    }
}