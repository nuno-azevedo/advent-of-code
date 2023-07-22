package com.adventofcode.year2020.day15;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DayPuzzle(year = 2020, day = 15)
public class RambunctiousRecitation extends AbstractPuzzle<List<Integer>> {
    static {
        puzzle = RambunctiousRecitation.class;
    }

    @Override
    protected long partOne(List<Integer> input) {
        return playGame(input, 2020);
    }

    @Override
    protected long partTwo(List<Integer> input) {
        return playGame(input, 30000000);
    }

    @Override
    protected List<Integer> readInput(Path path) {
        return InputReader.readAsLineStream(path)
                .flatMap(line -> Arrays.stream(line.split(",")))
                .map(Integer::valueOf)
                .toList();
    }

    private static int playGame(List<Integer> numbers, int iterations) {
        Map<Integer, Integer> previous = new HashMap<>();
        int lastNumber = numbers.iterator().next();

        for (int i = 2; i <= iterations; i++) {
            int lastRound = i - 1;
            Integer previousRound = previous.put(lastNumber, lastRound);

            if (i <= numbers.size()) {
                lastNumber = numbers.get(lastRound);
            } else if (previousRound != null) {
                lastNumber = lastRound - previousRound;
            } else {
                lastNumber = 0;
            }
        }

        return lastNumber;
    }
}
