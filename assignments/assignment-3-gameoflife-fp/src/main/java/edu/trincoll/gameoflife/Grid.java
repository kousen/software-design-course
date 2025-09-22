package edu.trincoll.gameoflife;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Grid {
    private final int rows;
    private final int cols;
    private final Map<Cell, CellState> cells;

    public Grid(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException(
                "Grid dimensions must be positive: %dx%d".formatted(rows, cols)
            );
        }
        this.rows = rows;
        this.cols = cols;
        this.cells = new HashMap<>();
    }

    public Grid(String pattern) {
        var lines = pattern.trim().lines().toList();
        this.rows = lines.size();
        this.cols = lines.isEmpty() ? 0 : lines.getFirst().length();
        this.cells = new HashMap<>();

        IntStream.range(0, rows).forEach(row -> {
            var line = lines.get(row);
            IntStream.range(0, Math.min(cols, line.length())).forEach(col -> {
                var state = CellState.fromChar(line.charAt(col));
                if (state.isAlive()) {
                    cells.put(Cell.of(row, col), state);
                }
            });
        });
    }

    private Grid(int rows, int cols, Map<Cell, CellState> cells) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new HashMap<>(cells);
    }

    public void setCellState(Cell cell, CellState state) {
        if (isValidCell(cell)) {
            if (state.isAlive()) {
                cells.put(cell, state);
            } else {
                cells.remove(cell);
            }
        }
    }

    public CellState getCellState(Cell cell) {
        return cells.getOrDefault(cell, CellState.DEAD);
    }

    public boolean isValidCell(Cell cell) {
        return cell.row() >= 0 && cell.row() < rows &&
               cell.col() >= 0 && cell.col() < cols;
    }

    public List<Cell> getNeighbors(Cell cell) {
        return Stream.of(
            cell.north(), cell.south(), cell.east(), cell.west(),
            cell.northEast(), cell.northWest(),
            cell.southEast(), cell.southWest()
        )
        .filter(this::isValidCell)
        .toList();
    }

    public int countLiveNeighbors(Cell cell) {
        return (int) getNeighbors(cell).stream()
            .map(this::getCellState)
            .filter(CellState::isAlive)
            .count();
    }

    public Set<Cell> getAllCells() {
        return IntStream.range(0, rows)
            .boxed()
            .flatMap(row -> IntStream.range(0, cols)
                .mapToObj(col -> Cell.of(row, col)))
            .collect(Collectors.toSet());
    }

    public Set<Cell> getLiveCells() {
        return new HashSet<>(cells.keySet());
    }

    public int countLiveCells() {
        return cells.size();
    }

    public Grid evolveWith(GameRules rules) {
        // Get all cells that need evaluation (live cells + their neighbors)
        Set<Cell> cellsToEvaluate = cells.keySet().stream()
            .flatMap(cell -> Stream.concat(
                Stream.of(cell),
                getNeighbors(cell).stream()
            ))
            .filter(this::isValidCell)
            .collect(Collectors.toSet());

        // Apply rules to determine next generation
        Map<Cell, CellState> nextGeneration = new HashMap<>();
        cellsToEvaluate.forEach(cell -> {
            CellState current = getCellState(cell);
            int neighbors = countLiveNeighbors(cell);
            CellState next = rules.apply(current, neighbors);
            if (next.isAlive()) {
                nextGeneration.put(cell, next);
            }
        });

        return new Grid(rows, cols, nextGeneration);
    }

    public Grid copy() {
        return new Grid(rows, cols, new HashMap<>(cells));
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Grid other)) return false;
        return rows == other.rows && cols == other.cols && cells.equals(other.cells);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rows, cols, cells);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                sb.append(getCellState(Cell.of(row, col)).symbol());
            }
            if (row < rows - 1) sb.append('\n');
        }
        return sb.toString();
    }
}