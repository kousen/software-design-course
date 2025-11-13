package edu.trincoll.game.player;

/**
 * Immutable record representing the current game state.
 * <p>
 * This record uses Java's modern features (records from Java 14+)
 * to create an immutable data structure that can be safely shared
 * between different players without worrying about mutation.
 * <p>
 * Design Pattern: VALUE OBJECT
 * <p>
 * - Immutable
 * - Equals/hashCode based on values
 * - No identity
 */
public record GameState(
    int turnNumber,
    int roundNumber,
    boolean canUndo,
    int commandHistorySize
) {
    public static GameState initial() {
        return new GameState(1, 1, false, 0);
    }

    public GameState nextTurn() {
        return new GameState(turnNumber + 1, roundNumber, canUndo, commandHistorySize);
    }

    public GameState nextRound() {
        return new GameState(1, roundNumber + 1, canUndo, commandHistorySize);
    }

    public GameState withUndo(boolean canUndo, int historySize) {
        return new GameState(turnNumber, roundNumber, canUndo, historySize);
    }
}
