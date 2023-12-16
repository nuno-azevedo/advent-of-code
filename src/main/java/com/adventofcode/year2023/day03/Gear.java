package com.adventofcode.year2023.day03;

public record Gear(PartNumber a, PartNumber b) {
    int value() {
        return a.value() * b.value();
    }
}
