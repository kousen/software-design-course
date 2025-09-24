package edu.trincoll.gameoflife;

import java.util.stream.Stream;

/**
 * OPTIONAL/BONUS CLASS - For extra credit only
 * These transformations are more complex and not required for base credit.
 * Students can skip this entire class and still get full marks.
 */
public class PatternTransformer {

    /**
     * Rotates a pattern 90 degrees clockwise.
     *
     * BONUS METHOD - Optional implementation for extra credit
     *
     * @param grid The grid to rotate
     * @return New rotated grid
     */
    public Grid rotate90(Grid grid) {
        // BONUS: For 90° clockwise rotation:
        // new_row = col
        // new_col = (rows - 1) - row
        throw new UnsupportedOperationException("Bonus feature - optional");
    }

    /**
     * Flips a pattern horizontally (mirror across vertical axis).
     *
     * BONUS METHOD - Optional implementation for extra credit
     *
     * @param grid The grid to flip
     * @return New flipped grid
     */
    public Grid flipHorizontal(Grid grid) {
        // BONUS: Cell at (row, col) moves to (row, cols-1-col)
        throw new UnsupportedOperationException("Bonus feature - optional");
    }

    /**
     * Flips a pattern vertically (mirror across horizontal axis).
     *
     * BONUS METHOD - Optional implementation for extra credit
     *
     * @param grid The grid to flip
     * @return New flipped grid
     */
    public Grid flipVertical(Grid grid) {
        // BONUS: Cell at (row, col) moves to (rows-1-row, col)
        throw new UnsupportedOperationException("Bonus feature - optional");
    }

    /**
     * Finds all positions where a pattern could be placed in a larger grid
     * without overlapping existing live cells.
     *
     * BONUS METHOD - Optional implementation for extra credit
     *
     * @param target The target grid
     * @param pattern The pattern to place
     * @return Stream of valid positions (top-left corners where pattern fits)
     */
    public Stream<Cell> findValidPlacements(Grid target, Grid pattern) {
        // BONUS: Check each possible position in target grid
        throw new UnsupportedOperationException("Bonus feature - optional");
    }

    /**
     * Translates a pattern by the given offset.
     *
     * BONUS METHOD - Optional implementation for extra credit
     *
     * @param grid The grid to translate
     * @param rowOffset Row translation amount
     * @param colOffset Column translation amount
     * @return New translated grid with same dimensions
     */
    public Grid translate(Grid grid, int rowOffset, int colOffset) {
        // BONUS: Add offsets to each live cell's coordinates
        throw new UnsupportedOperationException("Bonus feature - optional");
    }
}