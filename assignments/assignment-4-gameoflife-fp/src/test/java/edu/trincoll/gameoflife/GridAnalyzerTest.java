package edu.trincoll.gameoflife;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GridAnalyzerTest {

    private GridAnalyzer analyzer;
    private GameRules conwayRules;

    @BeforeEach
    void setUp() {
        analyzer = new GridAnalyzer();
        conwayRules = GameRules.conway();
    }

    @Nested
    @DisplayName("Finding Changing Cells")
    class ChangingCellsTests {

        @Test
        @DisplayName("Identifies cells that will die from overpopulation")
        void findCellsDyingFromOverpopulation() {
            Grid grid = new Grid("""
                ***
                ***
                ***
                """);

            List<Cell> changingCells = analyzer.findChangingCells(grid, conwayRules)
                .collect(Collectors.toList());

            assertThat(changingCells)
                .as("Center cell should die from overpopulation")
                .contains(Cell.of(1, 1));

            assertThat(changingCells)
                .as("Corner cells survive with 3 neighbors")
                .doesNotContain(Cell.of(0, 0), Cell.of(0, 2), Cell.of(2, 0), Cell.of(2, 2));
        }

        @Test
        @DisplayName("Identifies cells that will be born")
        void findCellsBeingBorn() {
            Grid grid = Pattern.BLINKER.toGrid();

            List<Cell> changingCells = analyzer.findChangingCells(grid, conwayRules)
                .collect(Collectors.toList());

            assertThat(changingCells)
                .as("Should identify all cells that change in blinker pattern")
                .hasSize(4); // 2 die, 2 are born
        }

        @Test
        @DisplayName("Still life has no changing cells")
        void stillLifeHasNoChanges() {
            Grid block = Pattern.BLOCK.toGrid();

            List<Cell> changingCells = analyzer.findChangingCells(block, conwayRules)
                .collect(Collectors.toList());

            assertThat(changingCells)
                .as("Still life should have no changing cells")
                .isEmpty();
        }
    }

    @Nested
    @DisplayName("Grouping by Neighbor Count")
    class NeighborGroupingTests {

        @Test
        @DisplayName("Groups cells correctly by neighbor count")
        void groupsByNeighborCount() {
            Grid grid = new Grid("""
                **.
                **.
                ...
                """);

            Map<Integer, List<Cell>> groups = analyzer.groupByNeighborCount(grid);

            // Corner cells have 3 neighbors
            assertThat(groups.get(3))
                .as("Cells with 3 neighbors")
                .containsExactlyInAnyOrder(
                    Cell.of(0, 0), Cell.of(0, 1),
                    Cell.of(1, 0), Cell.of(1, 1)
                );

            // Edge cells have different counts
            assertThat(groups.get(2))
                .as("Cell at (0,2) should have 2 neighbors")
                .contains(Cell.of(0, 2));
        }

        @Test
        @DisplayName("Empty grid has all cells with 0 neighbors")
        void emptyGridAllZeroNeighbors() {
            Grid empty = new Grid(3, 3);
            Map<Integer, List<Cell>> groups = analyzer.groupByNeighborCount(empty);

            assertThat(groups.get(0))
                .as("All cells in empty grid have 0 neighbors")
                .hasSize(9);

            for (int i = 1; i <= 8; i++) {
                assertThat(groups.getOrDefault(i, List.of()))
                    .as("No cells should have %d neighbors".formatted(i))
                    .isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("Finding Isolated Cells")
    class IsolatedCellsTests {

        @Test
        @DisplayName("Finds truly isolated cells")
        void findIsolatedCells() {
            Grid grid = new Grid("""
                *....
                .....
                ..*..
                .....
                ....*
                """);

            List<Cell> isolated = analyzer.findIsolatedCells(grid)
                .collect(Collectors.toList());

            assertThat(isolated)
                .as("Should find all isolated cells")
                .containsExactlyInAnyOrder(
                    Cell.of(0, 0),
                    Cell.of(2, 2),
                    Cell.of(4, 4)
                );
        }

        @Test
        @DisplayName("No isolated cells in block pattern")
        void blockHasNoIsolatedCells() {
            Grid block = Pattern.BLOCK.toGrid();

            List<Cell> isolated = analyzer.findIsolatedCells(block)
                .collect(Collectors.toList());

            assertThat(isolated)
                .as("Block pattern should have no isolated cells")
                .isEmpty();
        }
    }

    @Nested
    @DisplayName("Density Calculation")
    class DensityTests {

        @Test
        @DisplayName("Calculates density correctly for partially filled grid")
        void calculateDensityPartiallyFilled() {
            Grid grid = new Grid("""
                **.
                ...
                .*.
                """);

            double density = analyzer.calculateDensity(grid);

            assertThat(density)
                .as("Grid with 3 live cells out of 9 should have density 1/3")
                .isEqualTo(3.0 / 9.0, within(0.001));
        }

        @Test
        @DisplayName("Empty grid has zero density")
        void emptyGridZeroDensity() {
            Grid empty = new Grid(5, 5);

            double density = analyzer.calculateDensity(empty);

            assertThat(density)
                .as("Empty grid should have density 0.0")
                .isEqualTo(0.0);
        }

        @Test
        @DisplayName("Full grid has density 1.0")
        void fullGridMaxDensity() {
            Grid full = new Grid("""
                ***
                ***
                ***
                """);

            double density = analyzer.calculateDensity(full);

            assertThat(density)
                .as("Fully populated grid should have density 1.0")
                .isEqualTo(1.0);
        }
    }

    // Helper method for floating point comparison
    private static org.assertj.core.data.Offset<Double> within(double delta) {
        return org.assertj.core.data.Offset.offset(delta);
    }
}