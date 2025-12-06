package com.adventofcode.year2025.day01;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

@DayPuzzle(year = 2025, day = 1)
public class SecretEntrance extends AbstractPuzzle<List<Rotation>> {
    private static final Pattern PATTERN = Pattern.compile("^([LR])(\\d+)$");

    static {
        puzzle = SecretEntrance.class;
    }

    @Override
    protected long partOne(List<Rotation> input) {
        var safeDial = new SafeDial();
        input.forEach(safeDial::rotate);
        return safeDial.getZeroStopsCount();
    }

    @Override
    protected long partTwo(List<Rotation> input) {
        var safeDial = new SafeDial();
        input.forEach(safeDial::rotate);
        return safeDial.getZeroPassesCount();
    }

    @Override
    protected List<Rotation> readInput(Path path) {
        return InputReader.readAsLineList(path, PATTERN, matcher -> {
            var direction = Rotation.Direction.valueOf(matcher.group(1));
            var distance = Integer.parseInt(matcher.group(2));
            return new Rotation(direction, distance);
        });
    }
}
