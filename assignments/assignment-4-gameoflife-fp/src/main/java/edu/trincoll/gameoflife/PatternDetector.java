package edu.trincoll.gameoflife;

import java.util.Optional;

public class PatternDetector {

    /**
     * Determines if a grid represents a still life pattern.
     * A still life remains unchanged after one evolution.
     *
     * @param grid The grid to check
     * @param rules The game rules to apply
     * @return true if the grid is a still life
     */
    public boolean isStillLife(Grid grid, GameRules rules) {
        // EXAMPLE IMPLEMENTATION PROVIDED
        Grid nextGeneration = grid.evolveWith(rules);
        return grid.equals(nextGeneration);
    }

    /**
     * Finds the period of an oscillating pattern.
     * An oscillator returns to its initial state after a fixed number of generations.
     * <p>
     * SIMPLIFIED: Only check if grid returns to INITIAL state (not intermediate cycles)
     *
     * @param grid The initial grid
     * @param rules The game rules to apply
     * @param maxGenerations Maximum generations to check
     * @return Optional containing the period, or empty if not an oscillator
     */
    public Optional<Integer> findPeriod(Grid grid, GameRules rules, int maxGenerations) {
        // TODO: Evolve the grid up to maxGenerations times
        // Check each generation to see if it equals the original grid
        // Return the generation number when it first matches
        // Hint: Use a for loop or IntStream.range()
        throw new UnsupportedOperationException("Not implemented - complete using TDD");
    }

    /**
     * Classifies a pattern based on its behavior over multiple generations.
     * <p>
     * SIMPLIFIED: Only detect STILL_LIFE and OSCILLATOR
     * Return UNKNOWN for everything else (including spaceships)
     *
     * @param grid The pattern to classify
     * @param rules The game rules
     * @param generations How many generations to observe
     * @return The pattern classification
     */
    public PatternType classifyPattern(Grid grid, GameRules rules, int generations) {
        // TODO: Use your isStillLife method to check for still life
        // TODO: Use your findPeriod method to check for oscillator
        // TODO: Return UNKNOWN for anything else
        // (Don't worry about detecting spaceships - that's advanced!)
        throw new UnsupportedOperationException("Not implemented - complete using TDD");
    }

    public enum PatternType {
        STILL_LIFE,
        OSCILLATOR,
        SPACESHIP,
        UNKNOWN
    }
}