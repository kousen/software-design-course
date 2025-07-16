package edu.trincoll.solid.isp;

public class MultiFunctionPrinter implements Printable, Scannable, Faxable {
    private String name;
    
    public MultiFunctionPrinter(String name) {
        this.name = name;
    }
    
    @Override
    public void print(String document) {
        System.out.println(name + " printing: " + document);
    }
    
    @Override
    public String scan() {
        String scannedContent = "Scanned content from " + name;
        System.out.println(name + " scanning document");
        return scannedContent;
    }
    
    @Override
    public void fax(String document) {
        System.out.println(name + " faxing: " + document);
    }
    
    public String getName() {
        return name;
    }
}