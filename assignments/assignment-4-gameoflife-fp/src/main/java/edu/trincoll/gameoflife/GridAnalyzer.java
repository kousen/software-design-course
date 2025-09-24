package edu.trincoll.gameoflife;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GridAnalyzer {

    /**
     * Finds all cells that will change state in the next generation.
     * <p>
     * SIMPLIFIED: Provided partial implementation
     *
     * @param grid The current grid state
     * @param rules The game rules to apply
     * @return Stream of cells that will change state
     */
    public Stream<Cell> findChangingCells(Grid grid, GameRules rules) {
        Grid nextGen = grid.evolveWith(rules);

        // TODO: Return a stream of cells where the state is different
        // Hint: Use grid.getAllCells().stream()
        // Filter where grid.getCellState(cell) != nextGen.getCellState(cell)
        throw new UnsupportedOperationException("Not implemented - complete using TDD");
    }

    /**
     * Groups cells by their number of live neighbors.
     * <p>
     * EXAMPLE IMPLEMENTATION PROVIDED
     *
     * @param grid The grid to analyze
     * @return Map from neighbor count (0-8) to list of cells with that many neighbors
     */
    public Map<Integer, List<Cell>> groupByNeighborCount(Grid grid) {
        // Example of using Collectors.groupingBy
        return grid.getAllCells().stream()
            .collect(Collectors.groupingBy(grid::countLiveNeighbors));
    }

    /**
     * Finds cells that form isolated groups (not connected to other live cells).
     *
     * @param grid The grid to analyze
     * @return Stream of cells that have no live neighbors
     */
    public Stream<Cell> findIsolatedCells(Grid grid) {
        // TODO: Return live cells with no live neighbors
        // Hint: Start with grid.getLiveCells().stream()
        // Filter where grid.countLiveNeighbors(cell) == 0
        throw new UnsupportedOperationException("Not implemented - complete using TDD");
    }

    /**
     * Calculates the density of live cells in the grid.
     *
     * @param grid The grid to analyze
     * @return The ratio of live cells to total cells (0.0 to 1.0)
     */
    public double calculateDensity(Grid grid) {
        // TODO: Simple calculation
        // Hint: grid.countLiveCells() / (grid.getRows() * grid.getCols())
        // Remember to cast to double!
        throw new UnsupportedOperationException("Not implemented - complete using TDD");
    }
}