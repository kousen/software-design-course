package edu.trincoll.solid.isp;

public class Robot implements Workable {
    private String model;
    
    public Robot(String model) {
        this.model = model;
    }
    
    @Override
    public void work() {
        System.out.println("Robot " + model + " is working");
    }
    
    public String getModel() {
        return model;
    }
}