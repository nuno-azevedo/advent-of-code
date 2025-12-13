package com.adventofcode.common.grid;

public record Cell<T>(int row, int column, T value) {
    public Cell(int row, int column) {
        this(row, column, null);
    }
}
