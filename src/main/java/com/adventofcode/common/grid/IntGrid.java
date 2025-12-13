package com.adventofcode.common.grid;

import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class IntGrid {
    private final int[][] grid;
    @Getter
    private final int rowsLength;
    @Getter
    private final int columnsLength;

    public IntGrid(int[][] grid) {
        this.grid = grid;
        this.rowsLength = grid.length;
        this.columnsLength = grid.length > 0 ? grid[0].length : 0;
    }

    public int get(int row, int column) {
        return grid[row][column];
    }

    public Cell<Integer> getCell(int row, int column) {
        return new Cell<>(row, column, grid[row][column]);
    }

    public void set(int row, int column, int value) {
        grid[row][column] = value;
    }

    public void setCell(Cell<Integer> cell) {
        set(cell.row(), cell.column(), cell.value());
    }

    public boolean compareAndSet(int row, int column, int expectedValue, int newValue) {
        if (grid[row][column] == expectedValue) {
            grid[row][column] = newValue;
            return true;
        }
        return false;
    }

    public boolean compareAndSet(Cell<Integer> cell, int expectedValue, int newValue) {
        return compareAndSet(cell.row(), cell.column(), expectedValue, newValue);
    }

    public void increment(int row, int column, int delta) {
        grid[row][column] += delta;
    }

    public void increment(Cell<Integer> cell, int delta) {
        increment(cell.row(), cell.column(), delta);
    }

    public void decrement(int row, int column, int delta) {
        grid[row][column] -= delta;
    }

    public void decrement(Cell<Integer> cell, int delta) {
        decrement(cell.row(), cell.column(), delta);
    }

    public boolean inBounds(int row, int column) {
        return row >= 0 && row < rowsLength && column >= 0 && column < columnsLength;
    }

    public boolean inBounds(Cell<Integer> cell) {
        return inBounds(cell.row(), cell.column());
    }

    public List<Cell<Integer>> neighborsOrthogonal(int row, int column, int radius) {
        return neighbors(row, column, radius, false);
    }

    public List<Cell<Integer>> neighborsOrthogonal(Cell<Integer> cell, int radius) {
        return neighbors(cell.row(), cell.column(), radius, false);
    }

    public List<Cell<Integer>> neighborsMoore(int row, int column, int radius) {
        return neighbors(row, column, radius, true);
    }

    public List<Cell<Integer>> neighborsMoore(Cell<Integer> cell, int radius) {
        return neighbors(cell.row(), cell.column(), radius, true);
    }

    public List<Cell<Integer>> traverse() {
        var cells = new ArrayList<Cell<Integer>>(rowsLength * columnsLength);

        for (int r = 0; r < rowsLength; r++) {
            for (int c = 0; c < columnsLength; c++) {
                cells.add(new Cell<>(r, c, grid[r][c]));
            }
        }

        return cells;
    }

    public IntGrid deepCopy() {
        var copy = Arrays.stream(grid)
                .map(row -> Arrays.copyOf(row, columnsLength))
                .toArray(int[][]::new);

        return new IntGrid(copy);
    }

    public Integer[][] convertToObject() {
        return Arrays.stream(grid)
                .map(ArrayUtils::toObject)
                .toArray(Integer[][]::new);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        IntGrid intGrid = (IntGrid) o;
        return rowsLength == intGrid.rowsLength
               && columnsLength == intGrid.columnsLength
               && Objects.deepEquals(grid, intGrid.grid);
    }

    @SuppressWarnings("DuplicatedCode")
    private List<Cell<Integer>> neighbors(int row, int column, int radius, boolean diagonals) {
        if (!inBounds(row, column)) {
            return Collections.emptyList();
        }

        var cells = new ArrayList<Cell<Integer>>();

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
