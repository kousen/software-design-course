package edu.trincoll.gameoflife;

import java.util.Set;
import java.util.function.BiFunction;

public class GameVariants {

    /**
     * Creates a custom game variant with specified birth/survival rules.
     * Birth rules: dead cells with this many neighbors become alive
     * Survival rules: living cells with this many neighbors survive
     * <p>
     * Example: B3/S23 is standard Conway's Life (birth on 3, survival on 2 or 3)
     *         B36/S23 is HighLife (birth on 3 or 6, survival on 2 or 3)
     * <p>
     * PARTIAL IMPLEMENTATION PROVIDED
     *
     * @param birthCounts Set of neighbor counts that cause birth
     * @param survivalCounts Set of neighbor counts that allow survival
     * @return BiFunction implementing these rules
     */
    public static BiFunction<CellState, Integer, CellState> createVariant(
            Set<Integer> birthCounts, Set<Integer> survivalCounts) {
        return (currentState, neighborCount) -> {
            if (currentState.isAlive()) {
                // TODO: Check if neighborCount is in survivalCounts
                // Return ALIVE if yes, DEAD if no
                throw new UnsupportedOperationException("Complete this part");
            } else {
                // TODO: Check if neighborCount is in birthCounts
                // Return ALIVE if yes, DEAD if no
                throw new UnsupportedOperationException("Complete this part");
            }
        };
    }

    /**
     * Creates a "Day and Night" variant where the rules are:
     * Birth: 3, 6, 7, 8
     * Survival: 3, 4, 6, 7, 8
     * <p>
     * EXAMPLE IMPLEMENTATION PROVIDED
     *
     * @return BiFunction implementing Day and Night rules
     */
    public static BiFunction<CellState, Integer, CellState> dayAndNight() {
        // Example of using createVariant
        return createVariant(
            Set.of(3, 6, 7, 8),      // Birth counts
            Set.of(3, 4, 6, 7, 8)    // Survival counts
        );
    }

    /**
     * Creates a "Life Without Death" variant where cells never die:
     * Birth: 3
     * Survival: 0, 1, 2, 3, 4, 5, 6, 7, 8
     *
     * @return BiFunction implementing Life Without Death rules
     */
    public static BiFunction<CellState, Integer, CellState> lifeWithoutDeath() {
        // TODO: Use createVariant with appropriate birth/survival counts
        // Hint: Set.of(3) for birth, Set.of(0,1,2,3,4,5,6,7,8) for survival
        throw new UnsupportedOperationException("Not implemented - complete using TDD");
    }

    /**
     * Creates a probabilistic variant where rules are applied with certain probability.
     * <p>
     * ADVANCED - OPTIONAL IMPLEMENTATION
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
        // OPTIONAL: This is more advanced
        // Students can skip this for base credit
        throw new UnsupportedOperationException("Optional advanced feature");
    }
}