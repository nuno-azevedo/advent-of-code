package com.adventofcode.year2021.day11;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.grid.Cell;
import com.adventofcode.common.grid.IntGrid;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

@DayPuzzle(year = 2021, day = 11)
public class DumboOctopus extends AbstractPuzzle<IntGrid> {
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
    protected long partOne(IntGrid input) {
        return modelEnergyLevelsAndFlashes(input);
    }

    @Override
    protected long partTwo(IntGrid input) {
        return findFirstStepWhichAllFlash(input);
    }

    @Override
    protected IntGrid readInput(Path path) {
        var grid = InputReader.readAsDigitGrid(path);
        return new IntGrid(grid);
    }

    private static int modelEnergyLevelsAndFlashes(IntGrid energies) {
        IntGrid clone = energies.deepCopy();
        return IntStream.range(0, STEPS).map(_ -> processSingleStep(clone)).sum();
    }

    private static Integer findFirstStepWhichAllFlash(IntGrid energies) {
        IntGrid zero = new IntGrid(new int[energies.getRowsLength()][energies.getColumnsLength()]);
        IntGrid clone = energies.deepCopy();

        return IntStream.iterate(1, i -> i + 1).boxed()
                .peek(_ -> processSingleStep(clone))
                .filter(_ -> clone.equals(zero))
                .findFirst()
                .orElseThrow();
    }

    private static int processSingleStep(IntGrid energies) {
        boolean[][] flashed = new boolean[energies.getRowsLength()][energies.getColumnsLength()];

        return energies.traverse().stream()
                .mapToInt(cell -> raiseEnergyAndCountFlashes(energies, flashed, cell))
                .sum();
    }

    private static int raiseEnergyAndCountFlashes(IntGrid energies, boolean[][] flashed, Cell<Integer> cell) {
        if (energies.inBounds(cell) && !flashed[cell.row()][cell.column()]) {
            if (energies.compareAndSet(cell, MAX_ENERGY, MIN_ENERGY)) {
                flashed[cell.row()][cell.column()] = true;

                return DIRECTIONS.stream()
                        .map(direction -> {
                            var row = cell.row() + direction.vertical();
                            var column = cell.column() + direction.horizontal();
                            return raiseEnergyAndCountFlashes(energies, flashed, new Cell<>(row, column));
                        })
                        .reduce(1, Integer::sum);
            }
            energies.increment(cell, 1);
        }

        return 0;
    }
}
