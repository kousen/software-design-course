package edu.trincoll.gameoflife;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PatternDetectorTest {

    private PatternDetector detector;
    private GameRules conwayRules;

    @BeforeEach
    void setUp() {
        detector = new PatternDetector();
        conwayRules = GameRules.conway();
    }

    @Nested
    @DisplayName("Still Life Detection")
    class StillLifeTests {

        @Test
        @DisplayName("Block pattern is a still life")
        void blockIsStillLife() {
            Grid block = Pattern.BLOCK.toGrid();
            assertThat(detector.isStillLife(block, conwayRules))
                .as("Block pattern should be a still life")
                .isTrue();
        }

        @Test
        @DisplayName("Beehive pattern is a still life")
        void beehiveIsStillLife() {
            Grid beehive = Pattern.BEEHIVE.toGrid();
            assertThat(detector.isStillLife(beehive, conwayRules))
                .as("Beehive pattern should be a still life")
                .isTrue();
        }

        @Test
        @DisplayName("Boat pattern is a still life")
        void boatIsStillLife() {
            Grid boat = Pattern.BOAT.toGrid();
            assertThat(detector.isStillLife(boat, conwayRules))
                .as("Boat pattern should be a still life")
                .isTrue();
        }

        @Test
        @DisplayName("Blinker pattern is NOT a still life")
        void blinkerIsNotStillLife() {
            Grid blinker = Pattern.BLINKER.toGrid();
            assertThat(detector.isStillLife(blinker, conwayRules))
                .as("Blinker pattern should NOT be a still life")
                .isFalse();
        }

        @Test
        @DisplayName("Empty grid is a still life")
        void emptyGridIsStillLife() {
            Grid empty = new Grid(5, 5);
            assertThat(detector.isStillLife(empty, conwayRules))
                .as("Empty grid should be a still life")
                .isTrue();
        }

        @Test
        @DisplayName("Single cell dies (not a still life)")
        void singleCellDies() {
            Grid singleCell = new Grid("""
                .....
                ..*..
                .....
                """);
            assertThat(detector.isStillLife(singleCell, conwayRules))
                .as("Single cell should die (underpopulation)")
                .isFalse();
        }
    }

    @Nested
    @DisplayName("Oscillator Detection")
    class OscillatorTests {

        @Test
        @DisplayName("Blinker has period 2")
        void blinkerPeriod() {
            Grid blinker = Pattern.BLINKER.toGrid();
            Optional<Integer> period = detector.findPeriod(blinker, conwayRules, 10);

            assertThat(period)
                .as("Blinker should have a detectable period")
                .isPresent()
                .hasValue(2);
        }

        @Test
        @DisplayName("Toad has period 2")
        void toadPeriod() {
            Grid toad = Pattern.TOAD.toGrid();
            Optional<Integer> period = detector.findPeriod(toad, conwayRules, 10);

            assertThat(period)
                .as("Toad should have period 2")
                .isPresent()
                .hasValue(2);
        }

        @Test
        @DisplayName("Beacon has period 2")
        void beaconPeriod() {
            Grid beacon = Pattern.BEACON.toGrid();
            Optional<Integer> period = detector.findPeriod(beacon, conwayRules, 10);

            assertThat(period)
                .as("Beacon should have period 2")
                .isPresent()
                .hasValue(2);
        }

        @Test
        @DisplayName("Still life has period 1")
        void stillLifePeriod() {
            Grid block = Pattern.BLOCK.toGrid();
            Optional<Integer> period = detector.findPeriod(block, conwayRules, 10);

            assertThat(period)
                .as("Still life should have period 1")
                .isPresent()
                .hasValue(1);
        }

        @Test
        @DisplayName("Glider returns empty (it's a spaceship)")
        void gliderHasNoPeriod() {
            Grid glider = Pattern.GLIDER.toGrid();
            Optional<Integer> period = detector.findPeriod(glider, conwayRules, 20);

            assertThat(period)
                .as("Glider should not have a period (it moves)")
                .isEmpty();
        }

        @Test
        @DisplayName("Pattern that dies returns empty")
        void dyingPatternHasNoPeriod() {
            Grid dying = new Grid("""
                **.
                ...
                ...
                """);
            Optional<Integer> period = detector.findPeriod(dying, conwayRules, 10);

            assertThat(period)
                .as("Dying pattern should not have a period")
                .isEmpty();
        }
    }

    @Nested
    @DisplayName("Pattern Classification")
    class PatternClassificationTests {

        @ParameterizedTest(name = "{0} should be classified as {1}")
        @MethodSource("providePatternClassificationCases")
        @DisplayName("Patterns are correctly classified")
        void classifyPatterns(Pattern pattern, PatternDetector.PatternType expectedType) {
            Grid grid = pattern.toGrid();
            PatternDetector.PatternType actualType = detector.classifyPattern(grid, conwayRules, 25);

            assertThat(actualType)
                .as("Pattern %s should be classified as %s".formatted(pattern, expectedType))
                .isEqualTo(expectedType);
        }

        static Stream<Arguments> providePatternClassificationCases() {
            return Stream.of(
                Arguments.of(Pattern.BLOCK, PatternDetector.PatternType.STILL_LIFE),
                Arguments.of(Pattern.BEEHIVE, PatternDetector.PatternType.STILL_LIFE),
                Arguments.of(Pattern.BLINKER, PatternDetector.PatternType.OSCILLATOR),
                Arguments.of(Pattern.TOAD, PatternDetector.PatternType.OSCILLATOR),
                Arguments.of(Pattern.GLIDER, PatternDetector.PatternType.SPACESHIP),
                Arguments.of(Pattern.LIGHTWEIGHT_SPACESHIP, PatternDetector.PatternType.SPACESHIP)
            );
        }

        @Test
        @DisplayName("Random pattern is classified as UNKNOWN")
        void randomPatternIsUnknown() {
            Grid random = new Grid("""
                *.*.*
                .*.*.
                *.*.*
                .*.*.
                *.*.*
                """);
            PatternDetector.PatternType type = detector.classifyPattern(random, conwayRules, 20);

            assertThat(type)
                .as("Complex pattern should be classified as UNKNOWN")
                .isEqualTo(PatternDetector.PatternType.UNKNOWN);
        }
    }
}