package edu.trincoll.patterns.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.assertj.core.api.Assertions.*;

@DisplayName("CommandHistory Tests")
class CommandHistoryTest {

    private TextEditor editor;
    private CommandHistory history;

    @BeforeEach
    void setUp() {
        editor = new TextEditor();
        history = new CommandHistory();
    }

    @Nested
    @DisplayName("Basic Operations")
    class BasicOperations {

        @Test
        @DisplayName("Should execute command and add to history")
        void shouldExecuteCommandAndAddToHistory() {
            var command = new InsertCommand(editor, "Hello", 0);

            history.executeCommand(command);

            assertThat(editor.getContent()).isEqualTo("Hello");
            assertThat(history.canUndo()).isTrue();
            assertThat(history.getUndoCount()).isEqualTo(1);
        }

        @Test
        @DisplayName("Should execute multiple commands")
        void shouldExecuteMultipleCommands() {
            history.executeCommand(new InsertCommand(editor, "Hello", 0));
            history.executeCommand(new InsertCommand(editor, " ", 5));
            history.executeCommand(new InsertCommand(editor, "World", 6));

            assertThat(editor.getContent()).isEqualTo("Hello World");
            assertThat(history.getUndoCount()).isEqualTo(3);
        }
    }

    @Nested
    @DisplayName("Undo Operations")
    class UndoOperations {

        @Test
        @DisplayName("Should undo last command")
        void shouldUndoLastCommand() {
            history.executeCommand(new InsertCommand(editor, "Hello", 0));
            history.executeCommand(new InsertCommand(editor, " World", 5));

            assertThat(history.undo()).isTrue();

            assertThat(editor.getContent()).isEqualTo("Hello");
            assertThat(history.getUndoCount()).isEqualTo(1);
            assertThat(history.getRedoCount()).isEqualTo(1);
        }

        @Test
        @DisplayName("Should undo multiple commands in order")
        void shouldUndoMultipleCommandsInOrder() {
            history.executeCommand(new InsertCommand(editor, "A", 0));
            history.executeCommand(new InsertCommand(editor, "B", 1));
            history.executeCommand(new InsertCommand(editor, "C", 2));

            assertThat(editor.getContent()).isEqualTo("ABC");

            history.undo();
            assertThat(editor.getContent()).isEqualTo("AB");

            history.undo();
            assertThat(editor.getContent()).isEqualTo("A");

            history.undo();
            assertThat(editor.getContent()).isEmpty();
        }

        @Test
        @DisplayName("Should return false when nothing to undo")
        void shouldReturnFalseWhenNothingToUndo() {
            assertThat(history.undo()).isFalse();
            assertThat(history.canUndo()).isFalse();
        }

        @Test
        @DisplayName("Should not undo beyond first command")
        void shouldNotUndoBeyondFirstCommand() {
            history.executeCommand(new InsertCommand(editor, "Hello", 0));

            assertThat(history.undo()).isTrue();
            assertThat(history.undo()).isFalse();
            assertThat(editor.getContent()).isEmpty();
        }
    }

    @Nested
    @DisplayName("Redo Operations")
    class RedoOperations {

        @Test
        @DisplayName("Should redo last undone command")
        void shouldRedoLastUndo() {
            history.executeCommand(new InsertCommand(editor, "Hello", 0));
            history.undo();

            assertThat(history.redo()).isTrue();

            assertThat(editor.getContent()).isEqualTo("Hello");
            assertThat(history.getUndoCount()).isEqualTo(1);
            assertThat(history.getRedoCount()).isZero();
        }

        @Test
        @DisplayName("Should redo multiple commands in order")
        void shouldRedoMultipleCommandsInOrder() {
            history.executeCommand(new InsertCommand(editor, "A", 0));
            history.executeCommand(new InsertCommand(editor, "B", 1));
            history.executeCommand(new InsertCommand(editor, "C", 2));

            history.undo();
            history.undo();
            history.undo();

            assertThat(editor.getContent()).isEmpty();

            history.redo();
            assertThat(editor.getContent()).isEqualTo("A");

            history.redo();
            assertThat(editor.getContent()).isEqualTo("AB");

            history.redo();
            assertThat(editor.getContent()).isEqualTo("ABC");
        }

        @Test
        @DisplayName("Should return false when nothing to redo")
        void shouldReturnFalseWhenNothingToRedo() {
            assertThat(history.redo()).isFalse();
            assertThat(history.canRedo()).isFalse();
        }

        @Test
        @DisplayName("Should clear redo stack on new command")
        void shouldClearRedoStackOnNewCommand() {
            history.executeCommand(new InsertCommand(editor, "Hello", 0));
            history.executeCommand(new InsertCommand(editor, " World", 5));

            history.undo();
            assertThat(history.getRedoCount()).isEqualTo(1);

            // Execute new command should clear redo stack
            history.executeCommand(new InsertCommand(editor, "!", 5));

            assertThat(history.getRedoCount()).isZero();
            assertThat(history.canRedo()).isFalse();
            assertThat(editor.getContent()).isEqualTo("Hello!");
        }
    }

    @Nested
    @DisplayName("Undo/Redo Combinations")
    class UndoRedoCombinations {

        @Test
        @DisplayName("Should handle undo-redo-undo sequence")
        void shouldHandleUndoRedoUndoSequence() {
            history.executeCommand(new InsertCommand(editor, "Hello", 0));

            history.undo();
            assertThat(editor.getContent()).isEmpty();

            history.redo();
            assertThat(editor.getContent()).isEqualTo("Hello");

            history.undo();
            assertThat(editor.getContent()).isEmpty();
        }

        @Test
        @DisplayName("Should handle complex undo/redo pattern")
        void shouldHandleComplexUndoRedoPattern() {
            history.executeCommand(new InsertCommand(editor, "A", 0));
            history.executeCommand(new InsertCommand(editor, "B", 1));
            history.executeCommand(new InsertCommand(editor, "C", 2));

            history.undo();  // Remove C
            history.undo();  // Remove B
            history.redo();  // Add B back

            assertThat(editor.getContent()).isEqualTo("AB");
            assertThat(history.getUndoCount()).isEqualTo(2);
            assertThat(history.getRedoCount()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("History Management")
    class HistoryManagement {

        @Test
        @DisplayName("Should clear all history")
        void shouldClearAllHistory() {
            history.executeCommand(new InsertCommand(editor, "Hello", 0));
            history.executeCommand(new InsertCommand(editor, " World", 5));
            history.undo();

            history.clear();

            assertThat(history.getUndoCount()).isZero();
            assertThat(history.getRedoCount()).isZero();
            assertThat(history.canUndo()).isFalse();
            assertThat(history.canRedo()).isFalse();
        }

        @Test
        @DisplayName("Should get undo history descriptions")
        void shouldGetUndoHistoryDescriptions() {
            history.executeCommand(new InsertCommand(editor, "Hello", 0));
            history.executeCommand(new InsertCommand(editor, " World", 5));

            var descriptions = history.getUndoHistory();

            assertThat(descriptions).hasSize(2);
            assertThat(descriptions.get(0)).contains("Insert 'Hello'");
            assertThat(descriptions.get(1)).contains("Insert ' World'");
        }

        @Test
        @DisplayName("Should get redo history descriptions")
        void shouldGetRedoHistoryDescriptions() {
            history.executeCommand(new InsertCommand(editor, "Hello", 0));
            history.executeCommand(new InsertCommand(editor, " World", 5));
            history.undo();
            history.undo();

            var descriptions = history.getRedoHistory();

            assertThat(descriptions).hasSize(2);
        }
    }

    @Nested
    @DisplayName("Different Command Types")
    class DifferentCommandTypes {

        @Test
        @DisplayName("Should handle mixed command types")
        void shouldHandleMixedCommandTypes() {
            var editor = new TextEditor("Hello World");

            history.executeCommand(new DeleteCommand(editor, 5, 1));
            assertThat(editor.getContent()).isEqualTo("HelloWorld");

            history.executeCommand(new InsertCommand(editor, " ", 5));
            assertThat(editor.getContent()).isEqualTo("Hello World");

            history.executeCommand(new ReplaceCommand(editor, 0, 5, "Howdy"));
            assertThat(editor.getContent()).isEqualTo("Howdy World");

            history.undo();
            assertThat(editor.getContent()).isEqualTo("Hello World");

            history.undo();
            assertThat(editor.getContent()).isEqualTo("HelloWorld");

            history.undo();
            assertThat(editor.getContent()).isEqualTo("Hello World");
        }
    }
}
