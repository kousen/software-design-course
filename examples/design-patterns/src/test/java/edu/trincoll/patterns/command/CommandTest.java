package edu.trincoll.patterns.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Command Tests")
class CommandTest {

    @Nested
    @DisplayName("InsertCommand Tests")
    class InsertCommandTests {

        @Test
        @DisplayName("Should execute insert command")
        void shouldExecuteInsert() {
            var editor = new TextEditor();
            var command = new InsertCommand(editor, "Hello", 0);

            command.execute();

            assertThat(editor.getContent()).isEqualTo("Hello");
        }

        @Test
        @DisplayName("Should undo insert command")
        void shouldUndoInsert() {
            var editor = new TextEditor();
            var command = new InsertCommand(editor, "Hello", 0);

            command.execute();
            command.undo();

            assertThat(editor.getContent()).isEmpty();
        }

        @Test
        @DisplayName("Should provide description")
        void shouldProvideDescription() {
            var editor = new TextEditor();
            var command = new InsertCommand(editor, "Hello", 5);

            assertThat(command.getDescription())
                .isEqualTo("Insert 'Hello' at position 5");
        }

        @Test
        @DisplayName("Should execute multiple inserts")
        void shouldExecuteMultipleInserts() {
            var editor = new TextEditor();

            var cmd1 = new InsertCommand(editor, "Hello", 0);
            cmd1.execute();

            var cmd2 = new InsertCommand(editor, " ", 5);
            cmd2.execute();

            var cmd3 = new InsertCommand(editor, "World", 6);
            cmd3.execute();

            assertThat(editor.getContent()).isEqualTo("Hello World");

            cmd3.undo();
            cmd2.undo();
            cmd1.undo();

            assertThat(editor.getContent()).isEmpty();
        }
    }

    @Nested
    @DisplayName("DeleteCommand Tests")
    class DeleteCommandTests {

        @Test
        @DisplayName("Should execute delete command")
        void shouldExecuteDelete() {
            var editor = new TextEditor("Hello World");
            var command = new DeleteCommand(editor, 6, 5);

            command.execute();

            assertThat(editor.getContent()).isEqualTo("Hello ");
        }

        @Test
        @DisplayName("Should undo delete command")
        void shouldUndoDelete() {
            var editor = new TextEditor("Hello World");
            var command = new DeleteCommand(editor, 6, 5);

            command.execute();
            command.undo();

            assertThat(editor.getContent()).isEqualTo("Hello World");
        }

        @Test
        @DisplayName("Should throw on undo before execute")
        void shouldThrowOnUndoBeforeExecute() {
            var editor = new TextEditor("Hello");
            var command = new DeleteCommand(editor, 0, 2);

            assertThatThrownBy(command::undo)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Cannot undo - command has not been executed");
        }

        @Test
        @DisplayName("Should provide description")
        void shouldProvideDescription() {
            var editor = new TextEditor("Hello");
            var command = new DeleteCommand(editor, 2, 3);

            assertThat(command.getDescription())
                .isEqualTo("Delete 3 characters at position 2");
        }
    }

    @Nested
    @DisplayName("ReplaceCommand Tests")
    class ReplaceCommandTests {

        @Test
        @DisplayName("Should execute replace command")
        void shouldExecuteReplace() {
            var editor = new TextEditor("Hello World");
            var command = new ReplaceCommand(editor, 0, 5, "Howdy");

            command.execute();

            assertThat(editor.getContent()).isEqualTo("Howdy World");
        }

        @Test
        @DisplayName("Should undo replace command")
        void shouldUndoReplace() {
            var editor = new TextEditor("Hello World");
            var command = new ReplaceCommand(editor, 0, 5, "Howdy");

            command.execute();
            command.undo();

            assertThat(editor.getContent()).isEqualTo("Hello World");
        }

        @Test
        @DisplayName("Should throw on undo before execute")
        void shouldThrowOnUndoBeforeExecute() {
            var editor = new TextEditor("Hello");
            var command = new ReplaceCommand(editor, 0, 2, "XX");

            assertThatThrownBy(command::undo)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Cannot undo - command has not been executed");
        }

        @Test
        @DisplayName("Should provide description")
        void shouldProvideDescription() {
            var editor = new TextEditor("Hello");
            var command = new ReplaceCommand(editor, 2, 2, "XXX");

            assertThat(command.getDescription())
                .isEqualTo("Replace 2 characters at position 2 with 'XXX'");
        }

        @Test
        @DisplayName("Should handle replace with different length text")
        void shouldHandleReplaceWithDifferentLength() {
            var editor = new TextEditor("Hello World");

            // Replace with longer text
            var cmd1 = new ReplaceCommand(editor, 6, 5, "Universe");
            cmd1.execute();
            assertThat(editor.getContent()).isEqualTo("Hello Universe");

            // Undo should restore original
            cmd1.undo();
            assertThat(editor.getContent()).isEqualTo("Hello World");

            // Replace with shorter text
            var cmd2 = new ReplaceCommand(editor, 6, 5, "All");
            cmd2.execute();
            assertThat(editor.getContent()).isEqualTo("Hello All");

            cmd2.undo();
            assertThat(editor.getContent()).isEqualTo("Hello World");
        }
    }
}
