package edu.trincoll.patterns.structural.composite;

/**
 * Component interface - Unified interface for leaf and composite objects.
 */
public interface FileSystemElement {
    String getName();
    int getSize();
    void display(int indent);
}
