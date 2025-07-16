package edu.trincoll.solid.lsp;

public abstract class Shape {
    protected String name;
    
    public Shape(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public abstract double getArea();
    
    public abstract double getPerimeter();
    
    public void printInfo() {
        System.out.printf("%s: Area = %.2f, Perimeter = %.2f%n", 
                         name, getArea(), getPerimeter());
    }
}