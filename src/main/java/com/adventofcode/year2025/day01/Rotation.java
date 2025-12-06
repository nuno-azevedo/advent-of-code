package com.adventofcode.year2025.day01;

public record Rotation(Direction direction, int distance) {
    enum Direction {
        L,
        R
    }
}
