package edu.trincoll.patterns.command;

/**
 * Command interface for the Command Pattern.
 * <p>
 * The Command pattern encapsulates a request as an object,
 * allowing you to:
 * <ul>
 *   <li>Parameterize clients with different requests</li>
 *   <li>Queue or log requests</li>
 *   <li>Support undoable operations</li>
 * </ul>
 */
public interface Command {
    /**
     * Execute the command.
     */
    void execute();

    /**
     * Undo the command.
     * <p>
     * This method should reverse the effects of execute().
     */
    void undo();

    /**
     * Get a description of what this command does.
     *
     * @return human-readable description
     */
    default String getDescription() {
        return this.getClass().getSimpleName();
    }
}
