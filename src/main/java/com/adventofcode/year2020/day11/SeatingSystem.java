package com.adventofcode.year2020.day11;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.Arrays2D;
import com.adventofcode.common.input.InputReader;

import java.nio.CharBuffer;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@DayPuzzle(year = 2020, day = 11)
public class SeatingSystem extends AbstractPuzzle<char[][]> {
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
    protected long partOne(char[][] input) {
        return simulateSeatingProcess(input, false, 4);
    }

    @Override
    protected long partTwo(char[][] input) {
        return simulateSeatingProcess(input, true, 5);
    }

    @Override
    protected char[][] readInput(Path path) {
        return InputReader.readAsCharMatrix(path);
    }

    private static long simulateSeatingProcess(char[][] layout, boolean loop, int threshold) {
        char[][] simulation = layout;
        boolean changed;

        do {
            changed = false;
            char[][] clone = Arrays2D.clone(simulation);

            for (int r = 0; r < layout.length; r++) {
                for (int c = 0; c < layout[r].length; c++) {
                    long occupied = countOccupiedSeats(simulation, r, c, loop);

                    if (simulation[r][c] == EMPTY && occupied == 0L) {
                        clone[r][c] = OCCUPIED;
                        changed = true;
                    } else if (simulation[r][c] == OCCUPIED && occupied >= threshold) {
                        clone[r][c] = EMPTY;
                        changed = true;
                    }
                }
            }

            simulation = clone;
        } while (changed);

        return Arrays.stream(simulation)
                .flatMap(row -> CharBuffer.wrap(row).chars().boxed())
                .filter(seat -> seat == OCCUPIED)
                .count();
    }

    private static long countOccupiedSeats(char[][] layout, int r, int c, boolean loop) {
        if (layout[r][c] == FLOOR) {
            return -1L;
        }

        return DIRECTIONS.stream().filter(direction -> {
            int ri = r;
            int ci = c;

            do {
                ri += direction.vertical();
                ci += direction.horizontal();
            } while (loop && Arrays2D.checkBoundaries(layout, ri, ci) && layout[ri][ci] == FLOOR);

            return Arrays2D.checkBoundaries(layout, ri, ci) && layout[ri][ci] == OCCUPIED;
        }).count();
    }
}
