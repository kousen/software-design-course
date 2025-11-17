package edu.trincoll.game.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Invoker for executing and managing commands.
 * Maintains command history for undo operations.
 *
 * TODO 4c: Implement executeCommand() and undoLastCommand()
 *
 * This class demonstrates the Command pattern's ability to:
 * - Queue commands for execution
 * - Maintain history
 * - Support undo operations
 */
public class CommandInvoker {
    private final Stack<GameCommand> commandHistory = new Stack<>();

    /**
     * TODO 4c: Implement executeCommand()
     *
     * Requirements:
     * 1. Execute the command: command.execute()
     * 2. Add the command to history: commandHistory.push(command)
     */
    public void executeCommand(GameCommand command) {
        // Execute the command
        command.execute();

        // Add to history
        commandHistory.push(command);
    }

    /**
     * TODO 4c: Implement undoLastCommand()
     *
     * Requirements:
     * 1. Check if history is empty - if so, return
     * 2. Pop the last command from history
     * 3. Call undo() on that command
     */
    public void undoLastCommand() {
        // Check if history is empty
        if (commandHistory.isEmpty()) {
            return;
        }

        // Pop the last command
        GameCommand command = commandHistory.pop();

        // Undo it
        command.undo();
    }

    /**
     * Get the command history (for testing and logging).
     */
    public List<GameCommand> getCommandHistory() {
        return new ArrayList<>(commandHistory);
    }

    /**
     * Clear all command history.
     */
    public void clearHistory() {
        commandHistory.clear();
    }

    /**
     * Check if there are commands to undo.
     */
    public boolean hasCommandsToUndo() {
        return !commandHistory.isEmpty();
    }
}
