/**
 * Command Pattern implementation for text editing with undo/redo.
 * <p>
 * The Command pattern encapsulates a request as an object, allowing you to:
 * <ul>
 *   <li>Parameterize clients with different requests</li>
 *   <li>Queue or log requests</li>
 *   <li>Support undoable operations</li>
 *   <li>Create macro commands (composite pattern)</li>
 * </ul>
 * <p>
 * <strong>Key Components:</strong>
 * <ul>
 *   <li>{@link edu.trincoll.patterns.command.Command} - Command interface with execute/undo</li>
 *   <li>{@link edu.trincoll.patterns.command.TextEditor} - Receiver that performs actual operations</li>
 *   <li>{@link edu.trincoll.patterns.command.CommandHistory} - Invoker that manages command execution and history</li>
 *   <li>Concrete commands: {@link edu.trincoll.patterns.command.InsertCommand},
 *       {@link edu.trincoll.patterns.command.DeleteCommand},
 *       {@link edu.trincoll.patterns.command.ReplaceCommand}</li>
 *   <li>{@link edu.trincoll.patterns.command.MacroCommand} - Composite command for macros</li>
 * </ul>
 * <p>
 * <strong>Example Usage:</strong>
 * <pre>{@code
 * var editor = new TextEditor();
 * var history = new CommandHistory();
 *
 * // Execute commands
 * history.executeCommand(new InsertCommand(editor, "Hello", 0));
 * history.executeCommand(new InsertCommand(editor, " World", 5));
 *
 * // Undo/redo
 * history.undo();  // Remove " World"
 * history.redo();  // Add " World" back
 * }</pre>
 * <p>
 * <strong>When to use Command Pattern:</strong>
 * <ul>
 *   <li>Need undo/redo functionality</li>
 *   <li>Want to queue or log operations</li>
 *   <li>Need to support transactions</li>
 *   <li>Want to create macros or composite operations</li>
 * </ul>
 * <p>
 * <strong>Real-world applications:</strong>
 * <ul>
 *   <li>Text editors (undo/redo)</li>
 *   <li>Database transactions</li>
 *   <li>GUI frameworks (button actions)</li>
 *   <li>Game input systems (replay/rewind)</li>
 *   <li>Job schedulers and queues</li>
 * </ul>
 *
 * @since 1.0
 * @see edu.trincoll.patterns.command.CommandPatternDemo
 */
package edu.trincoll.patterns.command;
