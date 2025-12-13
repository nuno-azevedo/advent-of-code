package com.adventofcode.common.grid;

import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CharGrid {
    private final char[][] grid;
    @Getter
    private final int rowsLength;
    @Getter
    private final int columnsLength;

    public CharGrid(char[][] grid) {
        this.grid = grid;
        this.rowsLength = grid.length;
        this.columnsLength = grid.length > 0 ? grid[0].length : 0;
    }

    public char get(int row, int column) {
        return grid[row][column];
    }

    public Cell<Character> getCell(int row, int column) {
        return new Cell<>(row, column, grid[row][column]);
    }

    public void set(int row, int column, char value) {
        grid[row][column] = value;
    }

    public void setCell(Cell<Character> cell) {
        set(cell.row(), cell.column(), cell.value());
    }

    public boolean compareAndSet(int row, int column, char expectedValue, char newValue) {
        if (grid[row][column] == expectedValue) {
            grid[row][column] = newValue;
            return true;
        }
        return false;
    }

    public boolean compareAndSet(Cell<Character> cell, char expectedValue, char newValue) {
        return compareAndSet(cell.row(), cell.column(), expectedValue, newValue);
    }

    public boolean inBounds(int row, int column) {
        return row >= 0 && row < rowsLength && column >= 0 && column < columnsLength;
    }

    public boolean inBounds(Cell<Character> cell) {
        return inBounds(cell.row(), cell.column());
    }

    public List<Cell<Character>> neighborsOrthogonal(int row, int column, int radius) {
        return neighbors(row, column, radius, false);
    }

    public List<Cell<Character>> neighborsOrthogonal(Cell<Character> cell, int radius) {
        return neighbors(cell.row(), cell.column(), radius, false);
    }

    public List<Cell<Character>> neighborsMoore(int row, int column, int radius) {
        return neighbors(row, column, radius, true);
    }

    public List<Cell<Character>> neighborsMoore(Cell<Character> cell, int radius) {
        return neighbors(cell.row(), cell.column(), radius, true);
    }

    public List<Cell<Character>> traverse() {
        var cells = new ArrayList<Cell<Character>>(rowsLength * columnsLength);

        for (int r = 0; r < rowsLength; r++) {
            for (int c = 0; c < columnsLength; c++) {
                cells.add(new Cell<>(r, c, grid[r][c]));
            }
        }

        return cells;
    }

    public CharGrid deepCopy() {
        var copy = Arrays.stream(grid)
                .map(row -> Arrays.copyOf(row, columnsLength))
                .toArray(char[][]::new);

        return new CharGrid(copy);
    }

    public Character[][] convertToObject() {
        return Arrays.stream(grid)
                .map(ArrayUtils::toObject)
                .toArray(Character[][]::new);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CharGrid charGrid = (CharGrid) o;
        return rowsLength == charGrid.rowsLength
               && columnsLength == charGrid.columnsLength
               && Objects.deepEquals(grid, charGrid.grid);
    }

    @SuppressWarnings("DuplicatedCode")
    private List<Cell<Character>> neighbors(int row, int column, int radius, boolean diagonals) {
        if (!inBounds(row, column)) {
            return Collections.emptyList();
        }

        var cells = new ArrayList<Cell<Character>>();

        int minRow = Math.max(0, row - radius);
        int maxRow = Math.min(rowsLength, row + radius + 1);

        for (int r = minRow; r < maxRow; r++) {
            int minColumn = Math.max(0, column - radius);
            int maxColumn = Math.min(columnsLength, column + radius + 1);

            for (int c = minColumn; c < maxColumn; c++) {
                if (r == row && c == column) {
                    continue;
                }
                if (!diagonals && r != row && c != column) {
                    continue;
                }
                cells.add(new Cell<>(r, c, grid[r][c]));
            }
        }

        return cells;
    }
}
