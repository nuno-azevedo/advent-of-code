package com.adventofcode.year2020.day12;

public record NavInstruction(Direction direction, int value) {
    public int value() {
        return direction.getSignal() * value;
    }
}
