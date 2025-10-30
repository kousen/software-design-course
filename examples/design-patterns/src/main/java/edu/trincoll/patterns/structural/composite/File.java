package edu.trincoll.patterns.structural.composite;

/**
 * Leaf - Individual object with no children.
 */
public class File implements FileSystemElement {
    private final String name;
    private final int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void display(int indent) {
        System.out.println(" ".repeat(indent) + "- " + name + " (" + size + " bytes)");
    }
}
