package com.adventofcode.year2020.day12;

record NavInstruction(Direction direction, int value) {
    @Override
    public int value() {
        return this.direction.getSignal() * this.value;
    }
}
