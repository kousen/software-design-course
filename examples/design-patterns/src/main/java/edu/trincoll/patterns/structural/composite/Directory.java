package edu.trincoll.patterns.structural.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite - Contains other elements (leaf or composite).
 *
 * Composite Pattern Benefits:
 * - Treat individual and composite objects uniformly
 * - Recursive operations (automatic traversal)
 * - Easy to add new types (just implement interface)
 * - Flexible tree structures
 */
public class Directory implements FileSystemElement {
    private final String name;
    private final List<FileSystemElement> children = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    public void add(FileSystemElement element) {
        children.add(element);
    }

    public void remove(FileSystemElement element) {
        children.remove(element);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        // Recursively sum all children
        return children.stream()
            .mapToInt(FileSystemElement::getSize)
            .sum();
    }

    @Override
    public void display(int indent) {
        System.out.println(" ".repeat(indent) + "+ " + name + "/ (" + getSize() + " bytes total)");
        children.forEach(child -> child.display(indent + 2));
    }
}
