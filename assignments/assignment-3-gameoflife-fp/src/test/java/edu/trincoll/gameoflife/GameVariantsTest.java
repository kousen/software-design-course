package edu.trincoll.gameoflife;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GameVariantsTest {

    @Nested
    @DisplayName("Custom Variant Creation")
    class CustomVariantTests {

        @Test
        @DisplayName("Create standard Conway rules (B3/S23)")
        void createConwayRules() {
            BiFunction<CellState, Integer, CellState> conway =
                GameVariants.createVariant(Set.of(3), Set.of(2, 3));

            // Test birth rule
            assertThat(conway.apply(CellState.DEAD, 3))
                .as("Dead cell with 3 neighbors should become alive")
                .isEqualTo(CellState.ALIVE);

            assertThat(conway.apply(CellState.DEAD, 2))
                .as("Dead cell with 2 neighbors should stay dead")
                .isEqualTo(CellState.DEAD);

            // Test survival rule
            assertThat(conway.apply(CellState.ALIVE, 2))
                .as("Live cell with 2 neighbors should survive")
                .isEqualTo(CellState.ALIVE);

            assertThat(conway.apply(CellState.ALIVE, 3))
                .as("Live cell with 3 neighbors should survive")
                .isEqualTo(CellState.ALIVE);

            assertThat(conway.apply(CellState.ALIVE, 4))
                .as("Live cell with 4 neighbors should die")
                .isEqualTo(CellState.DEAD);
        }

        @Test
        @DisplayName("Create HighLife rules (B36/S23)")
        void createHighLifeRules() {
            BiFunction<CellState, Integer, CellState> highLife =
                GameVariants.createVariant(Set.of(3, 6), Set.of(2, 3));

            // Test birth on 6
            assertThat(highLife.apply(CellState.DEAD, 6))
                .as("Dead cell with 6 neighbors should become alive in HighLife")
                .isEqualTo(CellState.ALIVE);

            // Still births on 3
            assertThat(highLife.apply(CellState.DEAD, 3))
                .as("Dead cell with 3 neighbors should become alive")
                .isEqualTo(CellState.ALIVE);

            // Survival rules same as Conway
            assertThat(highLife.apply(CellState.ALIVE, 2))
                .as("Live cell with 2 neighbors should survive")
                .isEqualTo(CellState.ALIVE);
        }

        @Test
        @DisplayName("Create Seeds rules (B2/S - no survival)")
        void createSeedsRules() {
            BiFunction<CellState, Integer, CellState> seeds =
                GameVariants.createVariant(Set.of(2), Set.of());

            // Birth on 2
            assertThat(seeds.apply(CellState.DEAD, 2))
                .as("Dead cell with 2 neighbors should become alive in Seeds")
                .isEqualTo(CellState.ALIVE);

            // No survival
            for (int neighbors = 0; neighbors <= 8; neighbors++) {
                assertThat(seeds.apply(CellState.ALIVE, neighbors))
                    .as("Live cells never survive in Seeds (neighbors: %d)".formatted(neighbors))
                    .isEqualTo(CellState.DEAD);
            }
        }

        @ParameterizedTest(name = "Alive cell with {0} neighbors in B3/S23: {1}")
        @CsvSource({
            "0, DEAD",
            "1, DEAD",
            "2, ALIVE",
            "3, ALIVE",
            "4, DEAD",
            "5, DEAD",
            "6, DEAD",
            "7, DEAD",
            "8, DEAD"
        })
        @DisplayName("Conway survival rules")
        void testConwaySurvival(int neighbors, CellState expected) {
            BiFunction<CellState, Integer, CellState> conway =
                GameVariants.createVariant(Set.of(3), Set.of(2, 3));

            assertThat(conway.apply(CellState.ALIVE, neighbors))
                .isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("Predefined Variants")
    class PredefinedVariantTests {

        @Test
        @DisplayName("Day and Night variant (B3678/S34678)")
        void dayAndNightRules() {
            BiFunction<CellState, Integer, CellState> dayNight =
                GameVariants.dayAndNight();

            // Test birth rules
            assertThat(dayNight.apply(CellState.DEAD, 3)).isEqualTo(CellState.ALIVE);
            assertThat(dayNight.apply(CellState.DEAD, 6)).isEqualTo(CellState.ALIVE);
            assertThat(dayNight.apply(CellState.DEAD, 7)).isEqualTo(CellState.ALIVE);
            assertThat(dayNight.apply(CellState.DEAD, 8)).isEqualTo(CellState.ALIVE);

            // Test survival rules
            assertThat(dayNight.apply(CellState.ALIVE, 3)).isEqualTo(CellState.ALIVE);
            assertThat(dayNight.apply(CellState.ALIVE, 4)).isEqualTo(CellState.ALIVE);
            assertThat(dayNight.apply(CellState.ALIVE, 6)).isEqualTo(CellState.ALIVE);
            assertThat(dayNight.apply(CellState.ALIVE, 7)).isEqualTo(CellState.ALIVE);
            assertThat(dayNight.apply(CellState.ALIVE, 8)).isEqualTo(CellState.ALIVE);

            // Test death conditions
            assertThat(dayNight.apply(CellState.ALIVE, 2)).isEqualTo(CellState.DEAD);
            assertThat(dayNight.apply(CellState.ALIVE, 5)).isEqualTo(CellState.DEAD);
        }

        @Test
        @DisplayName("Life Without Death variant (B3/S012345678)")
        void lifeWithoutDeathRules() {
            BiFunction<CellState, Integer, CellState> lifeWithoutDeath =
                GameVariants.lifeWithoutDeath();

            // Birth only on 3
            assertThat(lifeWithoutDeath.apply(CellState.DEAD, 3))
                .as("Dead cell with 3 neighbors should become alive")
                .isEqualTo(CellState.ALIVE);

            assertThat(lifeWithoutDeath.apply(CellState.DEAD, 2))
                .as("Dead cell with 2 neighbors should stay dead")
                .isEqualTo(CellState.DEAD);

            // Cells never die once alive
            for (int neighbors = 0; neighbors <= 8; neighbors++) {
                assertThat(lifeWithoutDeath.apply(CellState.ALIVE, neighbors))
                    .as("Live cells never die in Life Without Death (neighbors: %d)".formatted(neighbors))
                    .isEqualTo(CellState.ALIVE);
            }
        }
    }

    @Nested
    @DisplayName("Probabilistic Variant")
    class ProbabilisticVariantTests {

        @Test
        @DisplayName("Probabilistic variant with 100% probability is deterministic")
        void probabilisticWith100Percent() {
            BiFunction<CellState, Integer, CellState> baseRules = GameRules.conway();
            BiFunction<CellState, Integer, CellState> probabilistic =
                GameVariants.probabilistic(baseRules, 1.0, 1.0);

            // With 100% probability, should behave exactly like base rules
            assertThat(probabilistic.apply(CellState.DEAD, 3))
                .as("Birth should occur with 100% probability")
                .isEqualTo(CellState.ALIVE);

            assertThat(probabilistic.apply(CellState.ALIVE, 2))
                .as("Survival should occur with 100% probability")
                .isEqualTo(CellState.ALIVE);

            assertThat(probabilistic.apply(CellState.ALIVE, 4))
                .as("Death should occur when rules say so")
                .isEqualTo(CellState.DEAD);
        }

        @Test
        @DisplayName("Probabilistic variant with 0% probability prevents changes")
        void probabilisticWith0Percent() {
            BiFunction<CellState, Integer, CellState> baseRules = GameRules.conway();
            BiFunction<CellState, Integer, CellState> probabilistic =
                GameVariants.probabilistic(baseRules, 0.0, 0.0);

            // With 0% probability, births and survivals shouldn't happen
            assertThat(probabilistic.apply(CellState.DEAD, 3))
                .as("Birth should not occur with 0% probability")
                .isEqualTo(CellState.DEAD);

            assertThat(probabilistic.apply(CellState.ALIVE, 2))
                .as("Survival should not occur with 0% probability")
                .isEqualTo(CellState.DEAD);
        }

        @Test
        @DisplayName("Probabilistic variant produces varied results")
        void probabilisticProducesVariation() {
            BiFunction<CellState, Integer, CellState> baseRules = GameRules.conway();
            BiFunction<CellState, Integer, CellState> probabilistic =
                GameVariants.probabilistic(baseRules, 0.5, 0.5);

            // Run many times and check for variation
            int births = 0;
            int deaths = 0;
            for (int i = 0; i < 100; i++) {
                if (probabilistic.apply(CellState.DEAD, 3) == CellState.ALIVE) {
                    births++;
                }
                if (probabilistic.apply(CellState.ALIVE, 4) == CellState.DEAD) {
                    deaths++;
                }
            }

            // With 50% probability, we expect roughly 50 births out of 100
            // Allow for some variation (between 30 and 70)
            assertThat(births)
                .as("With 50% birth probability, expect varied results")
                .isBetween(30, 70);

            // Death should always occur for overpopulation (not affected by survival probability)
            assertThat(deaths)
                .as("Death from overpopulation should always occur")
                .isEqualTo(100);
        }
    }
}