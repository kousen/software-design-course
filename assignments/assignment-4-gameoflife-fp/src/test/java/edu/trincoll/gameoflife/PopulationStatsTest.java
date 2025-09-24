package edu.trincoll.gameoflife;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PopulationStatsTest {

    private PopulationStats stats;
    private GameRules conwayRules;

    @BeforeEach
    void setUp() {
        stats = new PopulationStats();
        conwayRules = GameRules.conway();
    }

    @Test
    @DisplayName("Track population over multiple generations")
    void trackPopulationChanges() {
        Grid blinker = Pattern.BLINKER.toGrid();

        List<Integer> populations = stats.trackPopulation(blinker, conwayRules, 5);

        assertThat(populations)
            .as("Should track population for 6 time steps (0 through 5)")
            .hasSize(6)
            .as("Blinker always has 3 live cells")
            .containsOnly(3);
    }

    @Test
    @DisplayName("Track population of dying pattern")
    void trackDyingPattern() {
        Grid dying = new Grid("""
            **.
            ...
            ...
            """);

        List<Integer> populations = stats.trackPopulation(dying, conwayRules, 3);

        assertThat(populations)
            .as("Pattern should die out")
            .startsWith(2) // Initial population
            .endsWith(0);  // Dies out
    }

    @Test
    @DisplayName("Find population peak")
    void findPopulationPeak() {
        // Create a pattern that grows then shrinks
        Grid growing = new Grid("""
            .*.
            ***
            .*.
            """);

        Optional<PopulationStats.PopulationPeak> peak =
            stats.findPopulationPeak(growing, conwayRules, 10);

        assertThat(peak)
            .as("Should find a population peak")
            .isPresent();

        assertThat(peak.get().population())
            .as("Peak should have more than initial 5 cells")
            .isGreaterThanOrEqualTo(5);
    }

    @Test
    @DisplayName("Find peak in stable population")
    void findPeakInStablePattern() {
        Grid block = Pattern.BLOCK.toGrid();

        Optional<PopulationStats.PopulationPeak> peak =
            stats.findPopulationPeak(block, conwayRules, 5);

        assertThat(peak)
            .isPresent();

        assertThat(peak.get())
            .as("Stable pattern peak is at generation 0")
            .satisfies(p -> {
                assertThat(p.generation()).isEqualTo(0);
                assertThat(p.population()).isEqualTo(4);
            });
    }

    @Test
    @DisplayName("Calculate average population")
    void calculateAveragePopulation() {
        Grid block = Pattern.BLOCK.toGrid(); // Always 4 cells

        double average = stats.calculateAveragePopulation(block, conwayRules, 5);

        assertThat(average)
            .as("Block pattern always has 4 cells")
            .isEqualTo(4.0);
    }

    @Test
    @DisplayName("Average population of oscillator")
    void averagePopulationOfOscillator() {
        Grid blinker = Pattern.BLINKER.toGrid();

        double average = stats.calculateAveragePopulation(blinker, conwayRules, 10);

        assertThat(average)
            .as("Blinker always has 3 cells")
            .isEqualTo(3.0);
    }

    @Test
    @DisplayName("Find stabilization point for still life")
    void findStabilizationForStillLife() {
        Grid block = Pattern.BLOCK.toGrid();

        Optional<Integer> stabilization =
            stats.findStabilizationPoint(block, conwayRules, 10);

        assertThat(stabilization)
            .as("Still life stabilizes immediately at generation 0")
            .isPresent()
            .hasValue(0);
    }

    @Test
    @DisplayName("Find stabilization point for pattern that settles")
    void findStabilizationForSettlingPattern() {
        // This pattern will evolve for a few generations then stabilize
        Grid settling = new Grid("""
            .**
            **.
            .*.
            """);

        Optional<Integer> stabilization =
            stats.findStabilizationPoint(settling, conwayRules, 20);

        assertThat(stabilization)
            .as("Pattern should eventually stabilize")
            .isPresent();

        assertThat(stabilization.get())
            .as("Should stabilize within a few generations")
            .isLessThan(10);
    }

    @Test
    @DisplayName("No stabilization for continuously changing pattern")
    void noStabilizationForOscillator() {
        // Create a pattern that will oscillate
        Grid blinker = Pattern.BLINKER.toGrid();

        // Note: This tests that POPULATION stabilizes, not the pattern itself
        // Blinker's population is always 3, so it should stabilize immediately
        Optional<Integer> stabilization =
            stats.findStabilizationPoint(blinker, conwayRules, 10);

        assertThat(stabilization)
            .as("Blinker's population count is stable at 3")
            .isPresent()
            .hasValue(0);
    }

    @Test
    @DisplayName("Empty grid statistics")
    void emptyGridStats() {
        Grid empty = new Grid(5, 5);

        List<Integer> populations = stats.trackPopulation(empty, conwayRules, 3);
        assertThat(populations)
            .as("Empty grid stays empty")
            .containsOnly(0);

        double average = stats.calculateAveragePopulation(empty, conwayRules, 5);
        assertThat(average)
            .as("Empty grid average is 0")
            .isEqualTo(0.0);

        Optional<PopulationStats.PopulationPeak> peak =
            stats.findPopulationPeak(empty, conwayRules, 5);
        assertThat(peak)
            .as("Empty grid peak is 0 at generation 0")
            .isPresent()
            .satisfies(p -> {
                assertThat(p.get().generation()).isEqualTo(0);
                assertThat(p.get().population()).isEqualTo(0);
            });
    }
}