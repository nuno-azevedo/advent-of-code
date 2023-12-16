package com.adventofcode.year2023.day03;

import com.adventofcode.common.grid.Cell;
import com.adventofcode.common.grid.CharGrid;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

record PartNumber(List<Cell<Character>> digits, List<Cell<Character>> border) {
    private static final int RADIUS = 1;

    PartNumber(List<Cell<Character>> digits, CharGrid engineSchematic) {
        this(digits, border(digits, engineSchematic));
    }

    int value() {
        String value = digits.stream()
                .map(Cell::value)
                .map(String::valueOf)
                .collect(Collectors.joining());

        return Integer.parseInt(value);
    }

    private static List<Cell<Character>> border(List<Cell<Character>> digits, CharGrid engineSchematic) {
        return digits.stream()
                .map(cell -> engineSchematic.neighborsMoore(cell, RADIUS))
                .flatMap(Collection::stream)
                .distinct()
                .filter(Predicate.not(digits::contains))
                .toList();
    }
}
