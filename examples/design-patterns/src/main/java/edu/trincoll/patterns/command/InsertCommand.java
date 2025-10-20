package edu.trincoll.patterns.command;

/**
 * Concrete command for inserting text.
 * <p>
 * Encapsulates the request to insert text at a specific position.
 * Supports undo by remembering what was inserted and where.
 */
public class InsertCommand implements Command {
    private final TextEditor editor;
    private final String text;
    private final int position;

    public InsertCommand(TextEditor editor, String text, int position) {
        this.editor = editor;
        this.text = text;
        this.position = position;
    }

    @Override
    public void execute() {
        editor.insert(text, position);
    }

    @Override
    public void undo() {
        editor.delete(position, text.length());
    }

    @Override
    public String getDescription() {
        return "Insert '%s' at position %d".formatted(text, position);
    }
}
