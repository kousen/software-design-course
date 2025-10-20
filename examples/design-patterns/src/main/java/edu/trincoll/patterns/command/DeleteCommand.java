package edu.trincoll.patterns.command;

/**
 * Concrete command for deleting text.
 * <p>
 * Encapsulates the request to delete text at a specific position.
 * Supports undo by remembering what was deleted and where.
 */
public class DeleteCommand implements Command {
    private final TextEditor editor;
    private final int position;
    private final int length;
    private String deletedText;

    public DeleteCommand(TextEditor editor, int position, int length) {
        this.editor = editor;
        this.position = position;
        this.length = length;
    }

    @Override
    public void execute() {
        // Save what we're about to delete for undo
        deletedText = editor.getContent().substring(position, position + length);
        editor.delete(position, length);
    }

    @Override
    public void undo() {
        if (deletedText == null) {
            throw new IllegalStateException("Cannot undo - command has not been executed");
        }
        editor.insert(deletedText, position);
    }

    @Override
    public String getDescription() {
        return "Delete %d characters at position %d".formatted(length, position);
    }
}
