package com.adventofcode.year2022.day03;

import java.util.Set;
import java.util.stream.Collectors;

public record Rucksack(String firstCompartment, String secondCompartment, Set<Character> uniqueItems) {
    Rucksack(String items) {
        this(
                items.substring(0, items.length() / 2),
                items.substring(items.length() / 2),
                items.chars().mapToObj(c -> (char) c).collect(Collectors.toSet())
        );
    }
}
