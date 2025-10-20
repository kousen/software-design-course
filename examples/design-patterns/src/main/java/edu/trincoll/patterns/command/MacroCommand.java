package edu.trincoll.patterns.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Composite command that executes multiple commands as a single unit.
 * <p>
 * This is an example of the Composite pattern combined with Command pattern.
 * Useful for:
 * <ul>
 *   <li>Recording macros</li>
 *   <li>Atomic transactions (all or nothing)</li>
 *   <li>Batch operations</li>
 * </ul>
 */
public class MacroCommand implements Command {
    private final List<Command> commands;
    private final String description;

    public MacroCommand(List<Command> commands) {
        this(commands, "Macro");
    }

    public MacroCommand(List<Command> commands, String description) {
        this.commands = new ArrayList<>(commands);
        this.description = description;
    }

    @Override
    public void execute() {
        for (Command command : commands) {
            command.execute();
        }
    }

    @Override
    public void undo() {
        // Undo in reverse order
        List<Command> reversed = new ArrayList<>(commands);
        Collections.reverse(reversed);
        for (Command command : reversed) {
            command.undo();
        }
    }

    @Override
    public String getDescription() {
        return description + " (%d commands)".formatted(commands.size());
    }

    /**
     * Get the individual commands in this macro.
     *
     * @return unmodifiable list of commands
     */
    public List<Command> getCommands() {
        return Collections.unmodifiableList(commands);
    }
}
