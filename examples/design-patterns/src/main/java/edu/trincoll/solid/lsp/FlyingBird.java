package edu.trincoll.solid.lsp;

public class FlyingBird extends Bird {
    
    public FlyingBird(String name) {
        super(name, true);
    }
    
    @Override
    public void move() {
        fly();
    }
    
    public void fly() {
        System.out.println(name + " is flying");
    }
}