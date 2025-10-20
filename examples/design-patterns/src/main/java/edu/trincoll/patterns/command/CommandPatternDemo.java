package edu.trincoll.patterns.command;

import java.util.List;

/**
 * Demonstration of the Command Pattern.
 * <p>
 * Shows:
 * <ul>
 *   <li>Basic command execution</li>
 *   <li>Undo/redo functionality</li>
 *   <li>Macro commands (composite)</li>
 *   <li>Command history</li>
 * </ul>
 */
public class CommandPatternDemo {

    public static void main(String[] args) {
        System.out.println("=== Command Pattern Demonstrations ===\n");

        demonstrateBasicCommands();
        System.out.println("\n" + "=".repeat(60) + "\n");

        demonstrateUndoRedo();
        System.out.println("\n" + "=".repeat(60) + "\n");

        demonstrateMacroCommand();
    }

    private static void demonstrateBasicCommands() {
        System.out.println("--- Basic Command Execution ---");

        var editor = new TextEditor();
        var history = new CommandHistory();

        System.out.println("Initial content: '" + editor.getContent() + "'");

        // Execute commands
        var cmd1 = new InsertCommand(editor, "Hello", 0);
        history.executeCommand(cmd1);
        System.out.println("After insert 'Hello': '" + editor.getContent() + "'");

        var cmd2 = new InsertCommand(editor, " World", 5);
        history.executeCommand(cmd2);
        System.out.println("After insert ' World': '" + editor.getContent() + "'");

        var cmd3 = new InsertCommand(editor, "!", 11);
        history.executeCommand(cmd3);
        System.out.println("After insert '!': '" + editor.getContent() + "'");

        System.out.println("\nCommand History: " + history.getUndoHistory());
    }

    private static void demonstrateUndoRedo() {
        System.out.println("--- Undo/Redo Functionality ---");

        var editor = new TextEditor("The quick fox jumps");
        var history = new CommandHistory();

        System.out.println("Initial: '" + editor.getContent() + "'");

        // Fix "quick fox" to "quick brown fox"
        var cmd1 = new InsertCommand(editor, "brown ", 10);
        history.executeCommand(cmd1);
        System.out.println("After insert 'brown ': '" + editor.getContent() + "'");

        // Delete "jumps" and replace with "jumped"
        var cmd2 = new DeleteCommand(editor, 20, 5);
        history.executeCommand(cmd2);
        System.out.println("After delete 'jumps': '" + editor.getContent() + "'");

        var cmd3 = new InsertCommand(editor, "jumped", 20);
        history.executeCommand(cmd3);
        System.out.println("After insert 'jumped': '" + editor.getContent() + "'");

        // Undo operations
        System.out.println("\n--- Undo Operations ---");
        history.undo();
        System.out.println("After undo (1): '" + editor.getContent() + "'");

        history.undo();
        System.out.println("After undo (2): '" + editor.getContent() + "'");

        history.undo();
        System.out.println("After undo (3): '" + editor.getContent() + "'");

        // Redo operations
        System.out.println("\n--- Redo Operations ---");
        history.redo();
        System.out.println("After redo (1): '" + editor.getContent() + "'");

        history.redo();
        System.out.println("After redo (2): '" + editor.getContent() + "'");

        System.out.println("\nUndo count: " + history.getUndoCount());
        System.out.println("Redo count: " + history.getRedoCount());
    }

    private static void demonstrateMacroCommand() {
        System.out.println("--- Macro Command (Composite) ---");

        var editor = new TextEditor();
        var history = new CommandHistory();

        System.out.println("Initial: '" + editor.getContent() + "'");

        // Create a macro that formats text as a title
        // 1. Insert the title text
        // 2. Insert a newline
        // 3. Insert underline characters
        var titleText = "Design Patterns";
        var underline = "=".repeat(titleText.length());

        List<Command> titleFormatting = List.of(
            new InsertCommand(editor, titleText, 0),
            new InsertCommand(editor, "\n", titleText.length()),
            new InsertCommand(editor, underline, titleText.length() + 1)
        );

        var macro = new MacroCommand(titleFormatting, "Format Title");

        // Execute macro as a single command
        history.executeCommand(macro);
        System.out.println("After macro execution:");
        System.out.println(editor.getContent());

        // Add some content
        var cmd = new InsertCommand(editor, "\n\nCommand Pattern example",
            editor.getLength());
        history.executeCommand(cmd);
        System.out.println("\nAfter adding content:");
        System.out.println(editor.getContent());

        // Undo the content
        history.undo();
        System.out.println("\nAfter undo (content removed):");
        System.out.println(editor.getContent());

        // Undo the macro (undoes all 3 operations)
        history.undo();
        System.out.println("\nAfter undo (macro undone):");
        System.out.println("Content: '" + editor.getContent() + "'");

        // Redo the macro
        history.redo();
        System.out.println("\nAfter redo (macro restored):");
        System.out.println(editor.getContent());

        System.out.println("\nMacro description: " + macro.getDescription());
        System.out.println("Macro contains " + macro.getCommands().size() + " commands");
    }
}
