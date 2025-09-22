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
        // TODO: Implement using functional programming
        // Hint: Compare the grid with its evolution
        throw new UnsupportedOperationException("Not implemented - complete using TDD");
    }

    /**
     * Finds the period of an oscillating pattern.
     * An oscillator returns to its initial state after a fixed number of generations.
     *
     * @param grid The initial grid
     * @param rules The game rules to apply
     * @param maxGenerations Maximum generations to check
     * @return Optional containing the period, or empty if not an oscillator
     */
    public Optional<Integer> findPeriod(Grid grid, GameRules rules, int maxGenerations) {
        // TODO: Implement using streams and Optional
        // Hint: Track grid states and look for repetition
        throw new UnsupportedOperationException("Not implemented - complete using TDD");
    }

    /**
     * Classifies a pattern based on its behavior over multiple generations.
     *
     * @param grid The pattern to classify
     * @param rules The game rules
     * @param generations How many generations to observe
     * @return The pattern classification
     */
    public PatternType classifyPattern(Grid grid, GameRules rules, int generations) {
        // TODO: Implement using functional approach
        // Hint: Use isStillLife and findPeriod methods
        // Check if pattern moves (spaceship) or stays in place (still life/oscillator)
        throw new UnsupportedOperationException("Not implemented - complete using TDD");
    }

    public enum PatternType {
        STILL_LIFE,
        OSCILLATOR,
        SPACESHIP,
        UNKNOWN
    }
}