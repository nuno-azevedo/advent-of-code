package com.adventofcode.year2021.day09;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.Arrays2D;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@DayPuzzle(year = 2021, day = 9)
public class SmokeBasin extends AbstractPuzzle<int[][]> {
    private static final int LARGEST = 3;
    private static final int HIGHEST = 9;

    static {
        puzzle = SmokeBasin.class;
    }

    @Override
    protected long partOne(int[][] input) {
        return findLowPointsAndSumRiskLevels(input);
    }

    @Override
    protected long partTwo(int[][] input) {
        return findThreeLargestBasinsAndMultiplySizes(input);
    }

    @Override
    protected int[][] readInput(Path path) {
        return InputReader.readAsIntMatrix(path);
    }

    private static int findLowPointsAndSumRiskLevels(int[][] heightmap) {
        int risk = 0;

        for (int y = 0; y < heightmap.length; y++) {
            for (int x = 0; x < heightmap[y].length; x++) {
                if (isLowPoint(heightmap, y, x)) {
                    risk += 1 + heightmap[y][x];
                }
            }
        }

        return risk;
    }

    private static int findThreeLargestBasinsAndMultiplySizes(int[][] heightmap) {
        List<Integer> basins = new LinkedList<>();
        boolean[][] visited = new boolean[heightmap.length][heightmap[0].length];

        for (int y = 0; y < heightmap.length; y++) {
            for (int x = 0; x < heightmap[y].length; x++) {
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

    private static boolean isLowPoint(int[][] heightmap, int y, int x) {
        int value = heightmap[y][x];
        return (y - 1 < 0 || heightmap[y - 1][x] > value) &&
                (y + 1 >= heightmap.length || heightmap[y + 1][x] > value) &&
                (x - 1 < 0 || heightmap[y][x - 1] > value) &&
                (x + 1 >= heightmap[y].length || heightmap[y][x + 1] > value);
    }

    private static int findSizeOfBasin(int[][] heightmap, boolean[][] visited, int y, int x) {
        if (Arrays2D.checkBoundaries(heightmap, y, x) && heightmap[y][x] != HIGHEST && !visited[y][x]) {
            visited[y][x] = true;
            return 1 + findSizeOfBasin(heightmap, visited, y - 1, x)
                    + findSizeOfBasin(heightmap, visited, y + 1, x)
                    + findSizeOfBasin(heightmap, visited, y, x - 1)
                    + findSizeOfBasin(heightmap, visited, y, x + 1);
        }
        return 0;
    }
}
