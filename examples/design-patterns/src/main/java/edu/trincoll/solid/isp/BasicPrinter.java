package edu.trincoll.solid.isp;

public class BasicPrinter implements Printable {
    private String name;
    
    public BasicPrinter(String name) {
        this.name = name;
    }
    
    @Override
    public void print(String document) {
        System.out.println(name + " printing: " + document);
    }
    
    public String getName() {
        return name;
    }
}