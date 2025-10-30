package edu.trincoll.patterns.structural.proxy;

/**
 * RealSubject - The actual document.
 */
public class SecureDocument implements Document {

    @Override
    public void view() {
        System.out.println("Viewing document");
    }

    @Override
    public void edit() {
        System.out.println("Editing document");
    }
}
