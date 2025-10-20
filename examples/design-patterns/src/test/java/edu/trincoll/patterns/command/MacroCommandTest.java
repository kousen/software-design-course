package edu.trincoll.patterns.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("MacroCommand Tests")
class MacroCommandTest {

    @Test
    @DisplayName("Should execute all commands in sequence")
    void shouldExecuteAllCommandsInSequence() {
        var editor = new TextEditor();

        List<Command> commands = List.of(
            new InsertCommand(editor, "Hello", 0),
            new InsertCommand(editor, " ", 5),
            new InsertCommand(editor, "World", 6)
        );

        var macro = new MacroCommand(commands);
        macro.execute();

        assertThat(editor.getContent()).isEqualTo("Hello World");
    }

    @Test
    @DisplayName("Should undo all commands in reverse order")
    void shouldUndoAllCommandsInReverseOrder() {
        var editor = new TextEditor();

        List<Command> commands = List.of(
            new InsertCommand(editor, "A", 0),
            new InsertCommand(editor, "B", 1),
            new InsertCommand(editor, "C", 2)
        );

        var macro = new MacroCommand(commands);
        macro.execute();
        assertThat(editor.getContent()).isEqualTo("ABC");

        macro.undo();
        assertThat(editor.getContent()).isEmpty();
    }

    @Test
    @DisplayName("Should execute and undo multiple times")
    void shouldExecuteAndUndoMultipleTimes() {
        var editor = new TextEditor();

        List<Command> commands = List.of(
            new InsertCommand(editor, "Hello", 0),
            new InsertCommand(editor, " World", 5)
        );

        var macro = new MacroCommand(commands);

        macro.execute();
        assertThat(editor.getContent()).isEqualTo("Hello World");

        macro.undo();
        assertThat(editor.getContent()).isEmpty();

        macro.execute();
        assertThat(editor.getContent()).isEqualTo("Hello World");

        macro.undo();
        assertThat(editor.getContent()).isEmpty();
    }

    @Test
    @DisplayName("Should provide description with command count")
    void shouldProvideDescriptionWithCommandCount() {
        var editor = new TextEditor();

        List<Command> commands = List.of(
            new InsertCommand(editor, "A", 0),
            new InsertCommand(editor, "B", 1),
            new InsertCommand(editor, "C", 2)
        );

        var macro = new MacroCommand(commands, "Add ABC");

        assertThat(macro.getDescription()).isEqualTo("Add ABC (3 commands)");
    }

    @Test
    @DisplayName("Should use default description")
    void shouldUseDefaultDescription() {
        var editor = new TextEditor();

        List<Command> commands = List.of(
            new InsertCommand(editor, "A", 0)
        );

        var macro = new MacroCommand(commands);

        assertThat(macro.getDescription()).isEqualTo("Macro (1 commands)");
    }

    @Test
    @DisplayName("Should return unmodifiable list of commands")
    void shouldReturnUnmodifiableListOfCommands() {
        var editor = new TextEditor();

        List<Command> commands = List.of(
            new InsertCommand(editor, "A", 0)
        );

        var macro = new MacroCommand(commands);
        var retrievedCommands = macro.getCommands();

        assertThat(retrievedCommands).hasSize(1);
        assertThatThrownBy(() -> retrievedCommands.add(new InsertCommand(editor, "B", 1)))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("Should work with CommandHistory")
    void shouldWorkWithCommandHistory() {
        var editor = new TextEditor();
        var history = new CommandHistory();

        List<Command> titleCommands = List.of(
            new InsertCommand(editor, "Title", 0),
            new InsertCommand(editor, "\n", 5),
            new InsertCommand(editor, "-----", 6)
        );

        var macro = new MacroCommand(titleCommands, "Format Title");
        history.executeCommand(macro);

        assertThat(editor.getContent()).isEqualTo("Title\n-----");

        history.undo();
        assertThat(editor.getContent()).isEmpty();

        history.redo();
        assertThat(editor.getContent()).isEqualTo("Title\n-----");
    }

    @Test
    @DisplayName("Should handle mixed command types in macro")
    void shouldHandleMixedCommandTypes() {
        var editor = new TextEditor("Original");

        List<Command> commands = List.of(
            new ReplaceCommand(editor, 0, 8, "New"),
            new InsertCommand(editor, " Text", 3)
        );

        var macro = new MacroCommand(commands);
        macro.execute();

        assertThat(editor.getContent()).isEqualTo("New Text");

        macro.undo();
        assertThat(editor.getContent()).isEqualTo("Original");
    }

    @Test
    @DisplayName("Should handle empty macro")
    void shouldHandleEmptyMacro() {
        var editor = new TextEditor("Test");
        var macro = new MacroCommand(List.of());

        macro.execute();
        assertThat(editor.getContent()).isEqualTo("Test");

        macro.undo();
        assertThat(editor.getContent()).isEqualTo("Test");
    }

    @Test
    @DisplayName("Should handle nested macros")
    void shouldHandleNestedMacros() {
        var editor = new TextEditor();

        // Inner macro: Add "Hello"
        var innerMacro = new MacroCommand(List.of(
            new InsertCommand(editor, "Hello", 0)
        ), "Add Hello");

        // Outer macro: Add "Hello World!"
        var outerMacro = new MacroCommand(List.of(
            innerMacro,
            new InsertCommand(editor, " World", 5),
            new InsertCommand(editor, "!", 11)
        ), "Add Greeting");

        outerMacro.execute();
        assertThat(editor.getContent()).isEqualTo("Hello World!");

        outerMacro.undo();
        assertThat(editor.getContent()).isEmpty();
    }
}
