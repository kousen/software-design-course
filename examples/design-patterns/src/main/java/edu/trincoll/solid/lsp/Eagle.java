package edu.trincoll.solid.lsp;

public class Eagle extends FlyingBird {
    
    public Eagle() {
        super("Eagle");
    }
    
    public void hunt() {
        System.out.println(name + " is hunting prey");
    }
}