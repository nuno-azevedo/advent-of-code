package com.adventofcode.year2021.day13;

record Fold(Axis axis, int line) {
    boolean skipRow(int row) {
        return axis == Axis.Y && line == row;
    }

    boolean skipColumn(int column) {
        return axis == Axis.X && line == column;
    }
}
