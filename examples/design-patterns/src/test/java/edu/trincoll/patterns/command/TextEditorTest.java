package edu.trincoll.patterns.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.assertj.core.api.Assertions.*;

@DisplayName("TextEditor Tests")
class TextEditorTest {

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create empty editor")
        void shouldCreateEmptyEditor() {
            var editor = new TextEditor();

            assertThat(editor.getContent()).isEmpty();
            assertThat(editor.getLength()).isZero();
        }

        @Test
        @DisplayName("Should create editor with initial content")
        void shouldCreateEditorWithInitialContent() {
            var editor = new TextEditor("Hello World");

            assertThat(editor.getContent()).isEqualTo("Hello World");
            assertThat(editor.getLength()).isEqualTo(11);
        }
    }

    @Nested
    @DisplayName("Insert Operations")
    class InsertOperations {

        @Test
        @DisplayName("Should insert text at beginning")
        void shouldInsertAtBeginning() {
            var editor = new TextEditor("World");
            editor.insert("Hello ", 0);

            assertThat(editor.getContent()).isEqualTo("Hello World");
        }

        @Test
        @DisplayName("Should insert text at end")
        void shouldInsertAtEnd() {
            var editor = new TextEditor("Hello");
            editor.insert(" World", 5);

            assertThat(editor.getContent()).isEqualTo("Hello World");
        }

        @Test
        @DisplayName("Should insert text in middle")
        void shouldInsertInMiddle() {
            var editor = new TextEditor("Hello");
            editor.insert("XXX", 2);

            assertThat(editor.getContent()).isEqualTo("HeXXXllo");
        }

        @Test
        @DisplayName("Should reject negative position")
        void shouldRejectNegativePosition() {
            var editor = new TextEditor("Hello");

            assertThatThrownBy(() -> editor.insert("X", -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Position must be between");
        }

        @Test
        @DisplayName("Should reject position beyond length")
        void shouldRejectPositionBeyondLength() {
            var editor = new TextEditor("Hello");

            assertThatThrownBy(() -> editor.insert("X", 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Position must be between");
        }
    }

    @Nested
    @DisplayName("Delete Operations")
    class DeleteOperations {

        @Test
        @DisplayName("Should delete text from beginning")
        void shouldDeleteFromBeginning() {
            var editor = new TextEditor("Hello World");
            editor.delete(0, 6);

            assertThat(editor.getContent()).isEqualTo("World");
        }

        @Test
        @DisplayName("Should delete text from end")
        void shouldDeleteFromEnd() {
            var editor = new TextEditor("Hello World");
            editor.delete(5, 6);

            assertThat(editor.getContent()).isEqualTo("Hello");
        }

        @Test
        @DisplayName("Should delete text from middle")
        void shouldDeleteFromMiddle() {
            var editor = new TextEditor("Hello World");
            editor.delete(5, 1);

            assertThat(editor.getContent()).isEqualTo("HelloWorld");
        }

        @Test
        @DisplayName("Should reject negative position")
        void shouldRejectNegativePosition() {
            var editor = new TextEditor("Hello");

            assertThatThrownBy(() -> editor.delete(-1, 2))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Should reject deletion beyond length")
        void shouldRejectDeletionBeyondLength() {
            var editor = new TextEditor("Hello");

            assertThatThrownBy(() -> editor.delete(3, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot delete beyond content length");
        }
    }

    @Nested
    @DisplayName("Replace Operations")
    class ReplaceOperations {

        @Test
        @DisplayName("Should replace text with same length")
        void shouldReplaceWithSameLength() {
            var editor = new TextEditor("Hello World");
            editor.replace(0, 5, "Howdy");

            assertThat(editor.getContent()).isEqualTo("Howdy World");
        }

        @Test
        @DisplayName("Should replace text with shorter text")
        void shouldReplaceWithShorterText() {
            var editor = new TextEditor("Hello World");
            editor.replace(0, 5, "Hi");

            assertThat(editor.getContent()).isEqualTo("Hi World");
        }

        @Test
        @DisplayName("Should replace text with longer text")
        void shouldReplaceWithLongerText() {
            var editor = new TextEditor("Hello World");
            editor.replace(6, 5, "Universe");

            assertThat(editor.getContent()).isEqualTo("Hello Universe");
        }
    }

    @Nested
    @DisplayName("Clear Operation")
    class ClearOperation {

        @Test
        @DisplayName("Should clear all content")
        void shouldClearAllContent() {
            var editor = new TextEditor("Hello World");
            editor.clear();

            assertThat(editor.getContent()).isEmpty();
            assertThat(editor.getLength()).isZero();
        }

        @Test
        @DisplayName("Should handle clear on empty editor")
        void shouldHandleClearOnEmpty() {
            var editor = new TextEditor();
            editor.clear();

            assertThat(editor.getContent()).isEmpty();
        }
    }
}
