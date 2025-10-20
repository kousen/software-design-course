package edu.trincoll.patterns.command;

/**
 * Concrete command for replacing text.
 * <p>
 * Encapsulates the request to replace text at a specific position.
 * Supports undo by remembering what was replaced.
 */
public class ReplaceCommand implements Command {
    private final TextEditor editor;
    private final int position;
    private final int length;
    private final String newText;
    private String oldText;

    public ReplaceCommand(TextEditor editor, int position, int length, String newText) {
        this.editor = editor;
        this.position = position;
        this.length = length;
        this.newText = newText;
    }

    @Override
    public void execute() {
        // Save what we're about to replace for undo
        oldText = editor.getContent().substring(position, position + length);
        editor.replace(position, length, newText);
    }

    @Override
    public void undo() {
        if (oldText == null) {
            throw new IllegalStateException("Cannot undo - command has not been executed");
        }
        editor.replace(position, newText.length(), oldText);
    }

    @Override
    public String getDescription() {
        return "Replace %d characters at position %d with '%s'"
            .formatted(length, position, newText);
    }
}
