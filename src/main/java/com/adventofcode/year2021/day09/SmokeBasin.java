package com.adventofcode.year2021.day09;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.grid.IntGrid;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@DayPuzzle(year = 2021, day = 9)
public class SmokeBasin extends AbstractPuzzle<IntGrid> {
    private static final int LARGEST = 3;
    private static final int HIGHEST = 9;

    static {
        puzzle = SmokeBasin.class;
    }

    @Override
    protected long partOne(IntGrid input) {
        return findLowPointsAndSumRiskLevels(input);
    }

    @Override
    protected long partTwo(IntGrid input) {
        return findThreeLargestBasinsAndMultiplySizes(input);
    }

    @Override
    protected IntGrid readInput(Path path) {
        var grid = InputReader.readAsDigitGrid(path);
        return new IntGrid(grid);
    }

    private static int findLowPointsAndSumRiskLevels(IntGrid heightmap) {
        int risk = 0;

        for (int y = 0; y < heightmap.getRowsLength(); y++) {
            for (int x = 0; x < heightmap.getColumnsLength(); x++) {
                if (isLowPoint(heightmap, y, x)) {
                    risk += 1 + heightmap.get(y, x);
                }
            }
        }

        return risk;
    }

    private static int findThreeLargestBasinsAndMultiplySizes(IntGrid heightmap) {
        List<Integer> basins = new LinkedList<>();
        boolean[][] visited = new boolean[heightmap.getRowsLength()][heightmap.getColumnsLength()];

        for (int y = 0; y < heightmap.getRowsLength(); y++) {
            for (int x = 0; x < heightmap.getColumnsLength(); x++) {
                int size = findSizeOfBasin(heightmap, visited, y, x);
                if (size > 0) {
                    basins.add(size);
                }
            }
        }

        return basins.stream()
                .sorted(Comparator.reverseOrder())
                .limit(LARGEST)
                .reduce(Math::multiplyExact)
                .orElseThrow();
    }

    private static boolean isLowPoint(IntGrid heightmap, int y, int x) {
        int value = heightmap.get(y, x);
        return (y - 1 < 0 || heightmap.get(y - 1, x) > value) &&
                (y + 1 >= heightmap.getRowsLength() || heightmap.get(y + 1, x) > value) &&
                (x - 1 < 0 || heightmap.get(y, x - 1) > value) &&
                (x + 1 >= heightmap.getColumnsLength() || heightmap.get(y, x + 1) > value);
    }

    private static int findSizeOfBasin(IntGrid heightmap, boolean[][] visited, int y, int x) {
        if (heightmap.inBounds(y, x) && heightmap.get(y, x) != HIGHEST && !visited[y][x]) {
            visited[y][x] = true;
            return 1 + findSizeOfBasin(heightmap, visited, y - 1, x)
                    + findSizeOfBasin(heightmap, visited, y + 1, x)
                    + findSizeOfBasin(heightmap, visited, y, x - 1)
                    + findSizeOfBasin(heightmap, visited, y, x + 1);
        }
        return 0;
    }
}
