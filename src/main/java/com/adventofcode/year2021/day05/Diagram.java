package com.adventofcode.year2021.day05;

import java.util.Arrays;

record Diagram(int[][] map) {
    Diagram(int rows, int columns) {
        this(new int[rows][columns]);
    }

    void applyLineSegment(LineSegment line) {
        line.getCoveredPoints().forEach(point -> this.map[point.y()][point.x()]++);
    }

    long countPointsWithOverlap() {
        return Arrays.stream(this.map).flatMap(row -> Arrays.stream(row).boxed()).filter(n -> n > 1).count();
    }
}
