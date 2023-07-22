package com.adventofcode.year2021.day13;

record Fold(Axis axis, int line) {
    boolean skipRow(int row) {
        return this.axis == Axis.Y && this.line == row;
    }

    boolean skipColumn(int column) {
        return this.axis == Axis.X && this.line == column;
    }
}
