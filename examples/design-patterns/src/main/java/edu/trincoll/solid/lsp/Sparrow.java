package edu.trincoll.solid.lsp;

public class Sparrow extends FlyingBird {
    
    public Sparrow() {
        super("Sparrow");
    }
    
    public void chirp() {
        System.out.println(name + " is chirping");
    }
}