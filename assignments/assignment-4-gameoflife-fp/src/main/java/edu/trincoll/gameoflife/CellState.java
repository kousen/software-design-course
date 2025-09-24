package edu.trincoll.gameoflife;

public enum CellState {
    ALIVE('*', true),
    DEAD('.', false);

    private final char symbol;
    private final boolean alive;

    CellState(char symbol, boolean alive) {
        this.symbol = symbol;
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public char symbol() {
        return symbol;
    }

    public static CellState fromChar(char c) {
        return switch (c) {
            case '*', 'O', '1' -> ALIVE;
            case '.', ' ', '0' -> DEAD;
            default -> throw new IllegalArgumentException("Unknown cell character: " + c);
        };
    }

    public static CellState fromBoolean(boolean alive) {
        return alive ? ALIVE : DEAD;
    }
}