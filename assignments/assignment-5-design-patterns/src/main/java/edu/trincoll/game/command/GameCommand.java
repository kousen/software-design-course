package edu.trincoll.game.command;

/**
 * Command pattern interface for game actions.
 * Commands encapsulate actions that can be executed and undone.
 *
 * This demonstrates the Command pattern - turning requests into objects
 * that can be queued, logged, and potentially undone.
 */
public interface GameCommand {
    /**
     * Execute the command.
     */
    void execute();

    /**
     * Undo the command (if possible).
     * Not all commands may be reversible.
     */
    void undo();

    /**
     * Get a description of this command for logging.
     */
    String getDescription();
}
