package edu.trincoll.gameoflife;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class PatternTransformerTest {

    private PatternTransformer transformer;

    @BeforeEach
    void setUp() {
        transformer = new PatternTransformer();
    }

    @Nested
    @DisplayName("Rotation Tests")
    class RotationTests {

        @Test
        @DisplayName("Rotate 90 degrees clockwise")
        void rotate90Clockwise() {
            Grid original = new Grid("""
                **.
                ...
                ...
                """);

            Grid rotated = transformer.rotate90(original);

            // After 90° clockwise rotation:
            // **.  becomes  ..*
            // ...           ..*
            // ...           ...
            String expected = """
                ..*
                ..*
                ...
                """;

            assertThat(rotated.toString())
                .as("Grid should be rotated 90 degrees clockwise")
                .isEqualTo(expected.trim());
        }

        @Test
        @DisplayName("Four rotations return to original")
        void fourRotationsReturnToOriginal() {
            Grid original = Pattern.GLIDER.toGrid();

            Grid rotated = original;
            for (int i = 0; i < 4; i++) {
                rotated = transformer.rotate90(rotated);
            }

            assertThat(rotated)
                .as("Four 90-degree rotations should return to original")
                .isEqualTo(original);
        }
    }

    @Nested
    @DisplayName("Flip Tests")
    class FlipTests {

        @Test
        @DisplayName("Flip horizontal (mirror across vertical axis)")
        void flipHorizontal() {
            Grid original = new Grid("""
                *..
                **.
                ...
                """);

            Grid flipped = transformer.flipHorizontal(original);

            String expected = """
                ..*
                .**
                ...
                """;

            assertThat(flipped.toString())
                .as("Grid should be flipped horizontally")
                .isEqualTo(expected.trim());
        }

        @Test
        @DisplayName("Flip vertical (mirror across horizontal axis)")
        void flipVertical() {
            Grid original = new Grid("""
                ***
                ...
                .*.
                """);

            Grid flipped = transformer.flipVertical(original);

            String expected = """
                .*.
                ...
                ***
                """;

            assertThat(flipped.toString())
                .as("Grid should be flipped vertically")
                .isEqualTo(expected.trim());
        }

        @Test
        @DisplayName("Double flip returns to original")
        void doubleFlipReturnsToOriginal() {
            Grid original = Pattern.GLIDER.toGrid();

            Grid doubleFlipped = transformer.flipHorizontal(
                transformer.flipHorizontal(original)
            );

            assertThat(doubleFlipped)
                .as("Two horizontal flips should return to original")
                .isEqualTo(original);
        }
    }

    @Nested
    @DisplayName("Translation Tests")
    class TranslationTests {

        @Test
        @DisplayName("Translate pattern within bounds")
        void translateWithinBounds() {
            Grid original = new Grid("""
                *....
                .....
                .....
                .....
                .....
                """);

            Grid translated = transformer.translate(original, 2, 3);

            String expected = """
                .....
                .....
                ...*.
                .....
                .....
                """;

            assertThat(translated.toString())
                .as("Cell should move from (0,0) to (2,3)")
                .isEqualTo(expected.trim());
        }

        @Test
        @DisplayName("Translation that goes out of bounds clips cells")
        void translateOutOfBounds() {
            Grid original = new Grid("""
                ...
                .*.
                ...
                """);

            Grid translated = transformer.translate(original, -1, 2);
            // Cell at (1,1) would go to (0,3) which is out of bounds

            assertThat(translated.countLiveCells())
                .as("Cell that moves out of bounds should be clipped")
                .isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("Valid Placement Tests")
    class ValidPlacementTests {

        @Test
        @DisplayName("Find valid placements in empty grid")
        void findPlacementsInEmptyGrid() {
            Grid target = new Grid(5, 5); // Empty 5x5 grid
            Grid pattern = new Grid("""
                **
                **
                """); // 2x2 block

            List<Cell> validPlacements = transformer.findValidPlacements(target, pattern)
                .collect(Collectors.toList());

            // A 2x2 pattern can be placed at positions where
            // top-left corner is at most (3,3) to fit in 5x5 grid
            assertThat(validPlacements)
                .as("Should find all positions where 2x2 pattern fits in 5x5 grid")
                .hasSize(16); // 4x4 possible positions
        }

        @Test
        @DisplayName("No valid placements when target has conflicts")
        void noPlacementsWithConflicts() {
            Grid target = new Grid("""
                ***
                ***
                ***
                """); // Fully populated

            Grid pattern = Pattern.BLOCK.toGrid();

            List<Cell> validPlacements = transformer.findValidPlacements(target, pattern)
                .collect(Collectors.toList());

            assertThat(validPlacements)
                .as("No valid placements when target is fully populated")
                .isEmpty();
        }

        @Test
        @DisplayName("Find placements avoiding existing cells")
        void findPlacementsAvoidingExisting() {
            Grid target = new Grid("""
                *....
                .....
                .....
                .....
                .....
                """);

            Grid pattern = new Grid("""
                **
                """); // 1x2 horizontal bar

            List<Cell> validPlacements = transformer.findValidPlacements(target, pattern)
                .collect(Collectors.toList());

            assertThat(validPlacements)
                .as("Should not allow placement at (0,0) due to conflict")
                .doesNotContain(Cell.of(0, 0))
                .contains(Cell.of(1, 0), Cell.of(2, 0)); // These should be valid
        }
    }
}