package edu.trincoll.patterns.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Invoker class for the Command pattern.
 * <p>
 * Maintains a history of executed commands and supports undo/redo operations.
 * This is the "Command Manager" that coordinates command execution.
 */
public class CommandHistory {
    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();

    /**
     * Execute a command and add it to history.
     *
     * @param command the command to execute
     */
    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear(); // Clear redo stack when new command is executed
    }

    /**
     * Undo the last command.
     *
     * @return true if undo was successful, false if nothing to undo
     */
    public boolean undo() {
        if (undoStack.isEmpty()) {
            return false;
        }

        Command command = undoStack.pop();
        command.undo();
        redoStack.push(command);
        return true;
    }

    /**
     * Redo the last undone command.
     *
     * @return true if redo was successful, false if nothing to redo
     */
    public boolean redo() {
        if (redoStack.isEmpty()) {
            return false;
        }

        Command command = redoStack.pop();
        command.execute();
        undoStack.push(command);
        return true;
    }

    /**
     * Check if undo is available.
     *
     * @return true if there are commands to undo
     */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    /**
     * Check if redo is available.
     *
     * @return true if there are commands to redo
     */
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    /**
     * Get the number of commands in the undo stack.
     *
     * @return number of undoable commands
     */
    public int getUndoCount() {
        return undoStack.size();
    }

    /**
     * Get the number of commands in the redo stack.
     *
     * @return number of redoable commands
     */
    public int getRedoCount() {
        return redoStack.size();
    }

    /**
     * Clear all command history.
     */
    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }

    /**
     * Get descriptions of all commands in undo stack.
     *
     * @return list of command descriptions
     */
    public List<String> getUndoHistory() {
        return undoStack.stream()
            .map(Command::getDescription)
            .toList();
    }

    /**
     * Get descriptions of all commands in redo stack.
     *
     * @return list of command descriptions
     */
    public List<String> getRedoHistory() {
        return redoStack.stream()
            .map(Command::getDescription)
            .toList();
    }
}
