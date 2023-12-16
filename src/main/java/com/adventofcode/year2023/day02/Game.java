package com.adventofcode.year2023.day02;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record Game(int id, List<Map<Cube, Integer>> subsets) {
    boolean isGamePossible(Map<Cube, Integer> bag) {
        return subsets.stream().allMatch(subset -> isSubsetPossible(bag, subset));
    }

    int calculatePowerOfMinimumSetOfCubes() {
        return findMinimumSetOfCubesInBag().values().stream().reduce(1, Math::multiplyExact);
    }

    private static boolean isSubsetPossible(Map<Cube, Integer> bag, Map<Cube, Integer> subset) {
        return subset.entrySet().stream().allMatch(cubes -> cubes.getValue() <= bag.get(cubes.getKey()));
    }

    private Map<Cube, Integer> findMinimumSetOfCubesInBag() {
        return subsets.stream()
                .flatMap(subset -> subset.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Math::max));
    }
}
