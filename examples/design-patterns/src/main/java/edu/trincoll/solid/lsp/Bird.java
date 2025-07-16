package edu.trincoll.solid.lsp;

public abstract class Bird {
    protected String name;
    protected boolean canFly;
    
    public Bird(String name, boolean canFly) {
        this.name = name;
        this.canFly = canFly;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean canFly() {
        return canFly;
    }
    
    public abstract void move();
    
    public void eat() {
        System.out.println(name + " is eating");
    }
    
    public void sleep() {
        System.out.println(name + " is sleeping");
    }
}