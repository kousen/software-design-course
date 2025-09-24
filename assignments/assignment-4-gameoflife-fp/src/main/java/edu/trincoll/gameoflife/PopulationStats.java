package edu.trincoll.gameoflife;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class PopulationStats {

    /**
     * Tracks population changes over multiple generations.
     * Returns a list of population counts for each generation.
     * <p>
     * EXAMPLE IMPLEMENTATION PROVIDED
     *
     * @param initial Starting grid
     * @param rules Game rules to apply
     * @param generations Number of generations to simulate
     * @return List of population counts (index 0 = initial population)
     */
    public List<Integer> trackPopulation(Grid initial, GameRules rules, int generations) {
        // Example of Stream.iterate to generate sequence
        return Stream.iterate(initial, grid -> grid.evolveWith(rules))
            .limit(generations + 1)  // +1 to include initial state
            .map(Grid::countLiveCells)
            .toList();
    }

    /**
     * Finds the generation with maximum population within the given range.
     * <p>
     * SIMPLIFIED: Partial implementation provided
     *
     * @param initial Starting grid
     * @param rules Game rules to apply
     * @param maxGenerations Maximum generations to check
     * @return Optional containing the generation number and population count
     */
    public Optional<PopulationPeak> findPopulationPeak(Grid initial, GameRules rules, int maxGenerations) {
        List<Integer> populations = trackPopulation(initial, rules, maxGenerations);

        // TODO: Find the maximum population and its index (generation)
        // Return Optional.of(new PopulationPeak(generation, population))
        // Hint: IntStream.range(0, populations.size()) might help
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
        // TODO: Use trackPopulation to get all populations
        // Then calculate the average
        // Hint: stream.mapToInt(Integer::intValue).average()
        throw new UnsupportedOperationException("Not implemented - complete using TDD");
    }

    /**
     * Determines if the population stabilizes (stops changing) within the given generations.
     * <p>
     * SIMPLIFIED: Check if population stays the same for 3 consecutive generations
     *
     * @param initial Starting grid
     * @param rules Game rules to apply
     * @param maxGenerations Maximum generations to check
     * @return Optional containing the generation where population stabilized, or empty if it doesn't
     */
    public Optional<Integer> findStabilizationPoint(Grid initial, GameRules rules, int maxGenerations) {
        // TODO: Use trackPopulation to get populations
        // Find where population is same for 3 consecutive generations
        // Return the first generation where it stabilizes
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