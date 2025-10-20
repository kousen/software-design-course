package edu.trincoll.patterns.command;

/**
 * Receiver class for the Command pattern.
 * <p>
 * The TextEditor is the object that actually performs the operations.
 * Commands delegate to this class to do the work.
 */
public class TextEditor {
    private final StringBuilder content;

    public TextEditor() {
        this.content = new StringBuilder();
    }

    public TextEditor(String initialContent) {
        this.content = new StringBuilder(initialContent);
    }

    /**
     * Insert text at the specified position.
     *
     * @param text text to insert
     * @param position position to insert at (0-based)
     */
    public void insert(String text, int position) {
        if (position < 0 || position > content.length()) {
            throw new IllegalArgumentException(
                "Position must be between 0 and " + content.length()
            );
        }
        content.insert(position, text);
    }

    /**
     * Delete text from the specified position.
     *
     * @param position start position (0-based)
     * @param length number of characters to delete
     */
    public void delete(int position, int length) {
        if (position < 0 || position >= content.length()) {
            throw new IllegalArgumentException(
                "Position must be between 0 and " + (content.length() - 1)
            );
        }
        if (position + length > content.length()) {
            throw new IllegalArgumentException(
                "Cannot delete beyond content length"
            );
        }
        content.delete(position, position + length);
    }

    /**
     * Replace text at the specified position.
     *
     * @param position start position (0-based)
     * @param length number of characters to replace
     * @param newText new text to insert
     */
    public void replace(int position, int length, String newText) {
        delete(position, length);
        insert(newText, position);
    }

    /**
     * Clear all content.
     */
    public void clear() {
        content.setLength(0);
    }

    /**
     * Get the current content.
     *
     * @return current text content
     */
    public String getContent() {
        return content.toString();
    }

    /**
     * Get the length of the content.
     *
     * @return number of characters
     */
    public int getLength() {
        return content.length();
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
