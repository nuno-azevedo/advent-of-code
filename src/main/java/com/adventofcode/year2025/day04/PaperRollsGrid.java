package com.adventofcode.year2025.day04;

import com.adventofcode.common.grid.Cell;
import com.adventofcode.common.grid.CharGrid;

import java.util.List;

public record PaperRollsGrid(CharGrid grid) {
    private static final int RADIUS = 1;
    private static final int MAX = 4;

    private static final char PAPER = '@';
    private static final char EMPTY = '.';

    List<Cell<Character>> findAccessiblePaperRolls() {
        return grid.traverse().stream().parallel()
                .filter(cell -> cell.value() == PAPER)
                .filter(cell -> countAdjacentPaperRolls(cell) < MAX)
                .toList();
    }

    void removeAccessiblePaperRolls(List<Cell<Character>> accessiblePaperRolls) {
        accessiblePaperRolls.forEach(cell -> grid.set(cell.row(), cell.column(), EMPTY));
    }

    private long countAdjacentPaperRolls(Cell<Character> cell) {
        return grid.neighborsMoore(cell, RADIUS).stream()
                .filter(adjacent -> adjacent.value() == PAPER)
                .count();
    }
}
