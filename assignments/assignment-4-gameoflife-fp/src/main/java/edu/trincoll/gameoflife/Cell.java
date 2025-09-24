package edu.trincoll.gameoflife;

public record Cell(int row, int col) {

    public static Cell of(int row, int col) {
        return new Cell(row, col);
    }

    public Cell translate(int rowDelta, int colDelta) {
        return new Cell(row + rowDelta, col + colDelta);
    }

    public Cell north() {
        return translate(-1, 0);
    }

    public Cell south() {
        return translate(1, 0);
    }

    public Cell east() {
        return translate(0, 1);
    }

    public Cell west() {
        return translate(0, -1);
    }

    public Cell northEast() {
        return translate(-1, 1);
    }

    public Cell northWest() {
        return translate(-1, -1);
    }

    public Cell southEast() {
        return translate(1, 1);
    }

    public Cell southWest() {
        return translate(1, -1);
    }
}