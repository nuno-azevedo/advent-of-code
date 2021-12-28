package com.adventofcode.year2021.day01;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.List;

@DayPuzzle(year = 2021, day = 1)
public class SonarSweep extends AbstractPuzzle<List<Integer>> {
    static {
        puzzle = SonarSweep.class;
    }

    @Override
    protected long partOne(List<Integer> input) {
        return countNumberOfDepthIncreases(input);
    }

    @Override
    protected long partTwo(List<Integer> input) {
        return countNumberOfDepthIncreasesSlidingWindow(input);
    }

    @Override
    protected List<Integer> readInput(Path path) {
        return InputReader.readAsIntegerList(path);
    }

    private static int countNumberOfDepthIncreases(List<Integer> measurements) {
        int increases = 0;
        int last = 0;

        for (int measurement : measurements) {
            if (measurement > last) {
                increases++;
            }
            last = measurement;
        }

        return Math.max(increases - 1, 0);
    }

    private static int countNumberOfDepthIncreasesSlidingWindow(List<Integer> measurements) {
        int increases = 0;
        int windowA = 0;
        int windowB = 0;
        int windowC = 0;

        for (int measurement : measurements) {
            if (windowB + windowC + measurement > windowA + windowB + windowC) {
                increases++;
            }
            windowA = windowB;
            windowB = windowC;
            windowC = measurement;
        }

        return Math.max(increases - 3, 0);
    }
}
