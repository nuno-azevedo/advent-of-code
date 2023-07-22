package com.adventofcode.year2021.day11;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.Arrays2D;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@DayPuzzle(year = 2021, day = 11)
public class DumboOctopus extends AbstractPuzzle<int[][]> {
    private static final int STEPS = 100;
    private static final int MIN_ENERGY = 0;
    private static final int MAX_ENERGY = 9;
    private static final List<Direction> DIRECTIONS = List.of(
            new Direction(0, -1),
            new Direction(0, 1),
            new Direction(-1, 0),
            new Direction(1, 0),
            new Direction(-1, -1),
            new Direction(-1, 1),
            new Direction(1, -1),
            new Direction(1, 1)
    );

    static {
        puzzle = DumboOctopus.class;
    }

    @Override
    protected long partOne(int[][] input) {
        return modelEnergyLevelsAndFlashes(input);
    }

    @Override
    protected long partTwo(int[][] input) {
        return findFirstStepWhichAllFlash(input);
    }

    @Override
    protected int[][] readInput(Path path) {
        return InputReader.readAsIntMatrix(path);
    }

    private static int modelEnergyLevelsAndFlashes(int[][] energies) {
        int[][] clone = Arrays2D.clone(energies);
        return IntStream.range(0, STEPS).map(i -> processSingleStep(clone)).sum();
    }

    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    private static Integer findFirstStepWhichAllFlash(int[][] energies) {
        int[][] zero = new int[energies.length][energies[0].length];
        int[][] clone = Arrays2D.clone(energies);

        return IntStream.iterate(1, i -> i + 1).boxed()
                .peek(i -> processSingleStep(clone))
                .filter(i -> Arrays.deepEquals(clone, zero))
                .findFirst()
                .orElseThrow();
    }

    private static int processSingleStep(int[][] energies) {
        boolean[][] flashed = new boolean[energies.length][energies[0].length];
        int flashes = 0;

        for (int r = 0; r < energies.length; r++) {
            for (int c = 0; c < energies[r].length; c++) {
                flashes += raiseEnergyAndCountFlashes(energies, flashed, r, c);
            }
        }

        return flashes;
    }

    private static int raiseEnergyAndCountFlashes(int[][] energies, boolean[][] flashed, int r, int c) {
        if (Arrays2D.checkBoundaries(energies, r, c) && !flashed[r][c]) {
            if (energies[r][c] == MAX_ENERGY) {
                energies[r][c] = MIN_ENERGY;
                flashed[r][c] = true;

                return DIRECTIONS.stream()
                        .map(d -> raiseEnergyAndCountFlashes(energies, flashed, r + d.vertical(), c + d.horizontal()))
                        .reduce(1, Integer::sum);
            }
            energies[r][c]++;
        }

        return 0;
    }
}
