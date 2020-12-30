package com.adventofcode.year2020.day11;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.grid.CharGrid;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.List;

@DayPuzzle(year = 2020, day = 11)
public class SeatingSystem extends AbstractPuzzle<CharGrid> {
    private static final char FLOOR = '.';
    private static final char EMPTY = 'L';
    private static final char OCCUPIED = '#';

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
        puzzle = SeatingSystem.class;
    }

    @Override
    protected long partOne(CharGrid input) {
        return simulateSeatingProcess(input, false, 4);
    }

    @Override
    protected long partTwo(CharGrid input) {
        return simulateSeatingProcess(input, true, 5);
    }

    @Override
    protected CharGrid readInput(Path path) {
        var grid = InputReader.readAsCharGrid(path);
        return new CharGrid(grid);
    }

    private static long simulateSeatingProcess(CharGrid layout, boolean loop, int threshold) {
        CharGrid simulation = layout;
        boolean changed;

        do {
            changed = false;
            CharGrid clone = simulation.deepCopy();

            for (int r = 0; r < layout.getRowsLength(); r++) {
                for (int c = 0; c < layout.getColumnsLength(); c++) {
                    long occupied = countOccupiedSeats(simulation, r, c, loop);

                    if (occupied == 0L && simulation.get(r, c) == EMPTY) {
                        clone.set(r, c, OCCUPIED);
                        changed = true;
                    } else if (occupied >= threshold && simulation.get(r, c) == OCCUPIED) {
                        clone.set(r, c, EMPTY);
                        changed = true;
                    }
                }
            }

            simulation = clone;
        } while (changed);

        return simulation.traverse().stream()
                .filter(cell -> cell.value() == OCCUPIED)
                .count();
    }

    private static long countOccupiedSeats(CharGrid layout, int r, int c, boolean loop) {
        if (layout.get(r, c) == FLOOR) {
            return -1L;
        }

        return DIRECTIONS.stream().filter(direction -> {
            int ri = r;
            int ci = c;

            do {
                ri += direction.vertical();
                ci += direction.horizontal();
            } while (loop && layout.inBounds(ri, ci) && layout.get(ri, ci) == FLOOR);

            return layout.inBounds(ri, ci) && layout.get(ri, ci) == OCCUPIED;
        }).count();
    }
}
