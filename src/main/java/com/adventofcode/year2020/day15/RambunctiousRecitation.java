package com.adventofcode.year2020.day15;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

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
        // index = number, value = last round
        int[] lastSeen = new int[iterations];
        int lastNumber = numbers.getFirst();

        // seed initial numbers
        for (int round = 1; round < numbers.size(); round++) {
            lastSeen[lastNumber] = round;
            lastNumber = numbers.get(round);
        }

        for (int round = numbers.size(); round < iterations; round++) {
            int previousRound = lastSeen[lastNumber];
            lastSeen[lastNumber] = round;
            lastNumber = (previousRound == 0) ? 0 : round - previousRound;
        }

        return lastNumber;
    }
}
