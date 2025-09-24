package edu.trincoll.gameoflife;

import java.util.Set;
import java.util.function.BiFunction;

@FunctionalInterface
public interface GameRules extends BiFunction<CellState, Integer, CellState> {

    static GameRules conway() {
        return (current, neighbors) -> {
            if (current.isAlive()) {
                return (neighbors == 2 || neighbors == 3)
                    ? CellState.ALIVE
                    : CellState.DEAD;
            } else {
                return neighbors == 3
                    ? CellState.ALIVE
                    : CellState.DEAD;
            }
        };
    }

    static GameRules highLife() {
        // HighLife: B36/S23
        return (current, neighbors) -> {
            if (current.isAlive()) {
                return (neighbors == 2 || neighbors == 3)
                    ? CellState.ALIVE
                    : CellState.DEAD;
            } else {
                return (neighbors == 3 || neighbors == 6)
                    ? CellState.ALIVE
                    : CellState.DEAD;
            }
        };
    }

    static GameRules seeds() {
        // Seeds: B2/S
        return (current, neighbors) -> {
            if (current.isAlive()) {
                return CellState.DEAD; // No survival rules
            } else {
                return neighbors == 2
                    ? CellState.ALIVE
                    : CellState.DEAD;
            }
        };
    }

    static GameRules custom(Set<Integer> birthRules, Set<Integer> survivalRules) {
        return (current, neighbors) -> {
            if (current.isAlive()) {
                return survivalRules.contains(neighbors)
                    ? CellState.ALIVE
                    : CellState.DEAD;
            } else {
                return birthRules.contains(neighbors)
                    ? CellState.ALIVE
                    : CellState.DEAD;
            }
        };
    }
}