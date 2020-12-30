package com.adventofcode.year2020.day03;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.List;

@DayPuzzle(year = 2020, day = 3)
public class TobogganTrajectory extends AbstractPuzzle<char[][]> {
    private static final char TREE = '#';

    private static final Slope SLOPE = new Slope(1, 3);

    private static final List<Slope> SLOPES = List.of(
            new Slope(1, 1),
            new Slope(1, 3),
            new Slope(1, 5),
            new Slope(1, 7),
            new Slope(2, 1)
    );

    static {
        puzzle = TobogganTrajectory.class;
    }

    @Override
    protected long partOne(char[][] input) {
        return findTreesForSingleSlope(input, SLOPE);
    }

    @Override
    protected long partTwo(char[][] input) {
        return findTreesForMultipleSlopes(input);
    }

    @Override
    protected char[][] readInput(Path path) {
        return InputReader.readAsCharGrid(path);
    }

    private static long findTreesForSingleSlope(char[][] map, Slope slope) {
        long trees = 0L;
        int r = 0;

        for (int c = 0; r < map.length; c += slope.right()) {
            if (map[r][c % map[r].length] == TREE) {
                trees++;
            }
            r += slope.down();
        }

        return trees;
    }

    private static long findTreesForMultipleSlopes(char[][] map) {
        return SLOPES.stream()
                .map(slope -> findTreesForSingleSlope(map, slope))
                .reduce(1L, Math::multiplyExact);
    }
}
