package com.adventofcode.year2025.day10;

import com.adventofcode.common.algorithms.Combinations;

import java.util.BitSet;
import java.util.List;
import java.util.stream.IntStream;

public record MachineManual(BitSet diagram, List<BitSet> buttons, List<Integer> requirements) {
    int fewestButtonsToCorrectlyConfigure() {
        return IntStream.iterate(1, k -> k + 1).boxed()
                .flatMap(k -> Combinations.stream(buttons, k))
                .filter(this::checkButtonsConfiguration)
                .map(List::size)
                .findFirst()
                .orElseThrow();
    }

    private boolean checkButtonsConfiguration(List<BitSet> selectedButtons) {
        var configuration = new BitSet();
        selectedButtons.forEach(configuration::xor);
        return configuration.equals(diagram);
    }
}
