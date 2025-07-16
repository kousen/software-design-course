package edu.trincoll.solid.isp;

public class HumanWorker implements Workable, Eatable, Sleepable {
    private String name;
    
    public HumanWorker(String name) {
        this.name = name;
    }
    
    @Override
    public void work() {
        System.out.println(name + " is working");
    }
    
    @Override
    public void eat() {
        System.out.println(name + " is eating");
    }
    
    @Override
    public void sleep() {
        System.out.println(name + " is sleeping");
    }
    
    public String getName() {
        return name;
    }
}