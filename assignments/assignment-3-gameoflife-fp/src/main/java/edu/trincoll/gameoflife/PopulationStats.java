package edu.trincoll.gameoflife;

import java.util.List;
import java.util.Optional;

public class PopulationStats {

    /**
     * Tracks population changes over multiple generations.
     * Returns a list of population counts for each generation.
     *
     * @param initial Starting grid
     * @param rules Game rules to apply
     * @param generations Number of generations to simulate
     * @return List of population counts (index 0 = initial population)
     */
    public List<Integer> trackPopulation(Grid initial, GameRules rules, int generations) {
        // TODO: Use Stream.iterate or similar functional approach
        // Hint: Generate sequence of grids and map to population counts
        throw new UnsupportedOperationException("Not implemented - complete using TDD");
    }

    /**
     * Finds the generation with maximum population within the given range.
     *
     * @param initial Starting grid
     * @param rules Game rules to apply
     * @param maxGenerations Maximum generations to check
     * @return Optional containing the generation number and population count
     */
    public Optional<PopulationPeak> findPopulationPeak(Grid initial, GameRules rules, int maxGenerations) {
        // TODO: Implement using streams and Optional
        // Hint: Track populations and find maximum
        throw new UnsupportedOperationException("Not implemented - complete using TDD");
    }

    /**
     * Calculates the average population over a range of generations.
     *
     * @param initial Starting grid
     * @param rules Game rules to apply
     * @param generations Number of generations to average over
     * @return Average population as a double
     */
    public double calculateAveragePopulation(Grid initial, GameRules rules, int generations) {
        // TODO: Implement using streams
        // Hint: Use trackPopulation and calculate average
        throw new UnsupportedOperationException("Not implemented - complete using TDD");
    }

    /**
     * Determines if the population stabilizes (stops changing) within the given generations.
     *
     * @param initial Starting grid
     * @param rules Game rules to apply
     * @param maxGenerations Maximum generations to check
     * @return Optional containing the generation where population stabilized, or empty if it doesn't
     */
    public Optional<Integer> findStabilizationPoint(Grid initial, GameRules rules, int maxGenerations) {
        // TODO: Implement using functional approach
        // Hint: Look for when population stops changing between generations
        throw new UnsupportedOperationException("Not implemented - complete using TDD");
    }

    /**
     * Record to represent a population peak.
     */
    public record PopulationPeak(int generation, int population) {
        @Override
        public String toString() {
            return "Peak at generation %d with population %d".formatted(generation, population);
        }
    }
}