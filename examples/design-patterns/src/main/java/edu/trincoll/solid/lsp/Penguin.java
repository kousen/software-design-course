package edu.trincoll.solid.lsp;

public class Penguin extends Bird {
    
    public Penguin() {
        super("Penguin", false);
    }
    
    @Override
    public void move() {
        swim();
    }
    
    public void swim() {
        System.out.println(name + " is swimming");
    }
}