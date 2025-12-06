package com.adventofcode.year2025.day03;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@DayPuzzle(year = 2025, day = 3)
public class Lobby extends AbstractPuzzle<List<BatteryBank>> {
    private static final int TWO = 2;
    private static final int TWELVE = 12;

    static {
        puzzle = Lobby.class;
    }

    @Override
    protected long partOne(List<BatteryBank> input) {
        return input.stream()
                .mapToLong(batteryBank -> batteryBank.getLargestJoltagePossible(TWO))
                .sum();
    }

    @Override
    protected long partTwo(List<BatteryBank> input) {
        return input.stream()
                .mapToLong(batteryBank -> batteryBank.getLargestJoltagePossible(TWELVE))
                .sum();
    }

    @Override
    protected List<BatteryBank> readInput(Path path) throws IOException {
        return InputReader.readAsLineList(path, line -> {
            var ratings = line.chars().mapToObj(c -> Character.digit(c, 10)).toList();
            return new BatteryBank(ratings);
        });
    }
}
