package com.adventofcode.year2025.day03;

import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public record BatteryBank(List<Integer> batteryJoltageRatings) {
    long getLargestJoltagePossible(int batteries) {
        var joltageOutput = new StringBuilder();

        int idx = -1;
        for (int i = batteries - 1; i >= 0; i--) {
            idx = findIndexOfHighestDigit(idx + 1, batteryJoltageRatings.size() - i);
            joltageOutput.append(batteryJoltageRatings.get(idx));
        }

        return Long.parseLong(joltageOutput.toString());
    }

    private int findIndexOfHighestDigit(int start, int end) {
        int highestDigit = 0;
        int idx = -1;

        for (int i = start; i < end; i++) {
            int rating = batteryJoltageRatings.get(i);
            if (rating > highestDigit) {
                highestDigit = rating;
                idx = i;
            }
        }

        return idx;
    }
}
