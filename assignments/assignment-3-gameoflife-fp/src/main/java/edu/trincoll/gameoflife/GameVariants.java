package edu.trincoll.gameoflife;

import java.util.Set;
import java.util.function.BiFunction;

public class GameVariants {

    /**
     * Creates a custom game variant with specified birth/survival rules.
     * Birth rules: dead cells with this many neighbors become alive
     * Survival rules: living cells with this many neighbors survive
     *
     * Example: B3/S23 is standard Conway's Life (birth on 3, survival on 2 or 3)
     *         B36/S23 is HighLife (birth on 3 or 6, survival on 2 or 3)
     *
     * @param birthCounts Set of neighbor counts that cause birth
     * @param survivalCounts Set of neighbor counts that allow survival
     * @return BiFunction implementing these rules
     */
    public static BiFunction<CellState, Integer, CellState> createVariant(
            Set<Integer> birthCounts, Set<Integer> survivalCounts) {
        // TODO: Return a function that implements these rules
        // Hint: Check if current cell is alive, then apply appropriate rule set
        throw new UnsupportedOperationException("Not implemented - complete using TDD");
    }

    /**
     * Creates a "Day and Night" variant where the rules are:
     * Birth: 3, 6, 7, 8
     * Survival: 3, 4, 6, 7, 8
     *
     * @return BiFunction implementing Day and Night rules
     */
    public static BiFunction<CellState, Integer, CellState> dayAndNight() {
        // TODO: Implement using createVariant
        throw new UnsupportedOperationException("Not implemented - complete using TDD");
    }

    /**
     * Creates a "Life Without Death" variant where cells never die:
     * Birth: 3
     * Survival: 0, 1, 2, 3, 4, 5, 6, 7, 8
     *
     * @return BiFunction implementing Life Without Death rules
     */
    public static BiFunction<CellState, Integer, CellState> lifeWithoutDeath() {
        // TODO: Implement using createVariant
        throw new UnsupportedOperationException("Not implemented - complete using TDD");
    }

    /**
     * Creates a probabilistic variant where rules are applied with certain probability.
     *
     * @param baseRules The base rules to apply
     * @param survivalProbability Probability that a cell survives when it normally would
     * @param birthProbability Probability that a cell is born when it normally would
     * @return BiFunction implementing probabilistic rules
     */
    public static BiFunction<CellState, Integer, CellState> probabilistic(
            BiFunction<CellState, Integer, CellState> baseRules,
            double survivalProbability,
            double birthProbability) {
        // TODO: Implement probabilistic variant
        // Hint: Use Math.random() to add randomness
        // Note: This makes the game non-deterministic!
        throw new UnsupportedOperationException("Not implemented - complete using TDD");
    }
}