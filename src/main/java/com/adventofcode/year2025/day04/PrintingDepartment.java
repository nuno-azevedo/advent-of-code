package com.adventofcode.year2025.day04;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.grid.Cell;
import com.adventofcode.common.grid.CharGrid;
import com.adventofcode.common.input.InputReader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@DayPuzzle(year = 2025, day = 4)
public class PrintingDepartment extends AbstractPuzzle<PaperRollsGrid> {
    static {
        puzzle = PrintingDepartment.class;
    }

    @Override
    protected long partOne(PaperRollsGrid input) {
        return input.findAccessiblePaperRolls().size();
    }

    @Override
    protected long partTwo(PaperRollsGrid input) {
        int removed = 0;

        List<Cell<Character>> accessible;
        while (!(accessible = input.findAccessiblePaperRolls()).isEmpty()) {
            removed += accessible.size();
            input.removeAccessiblePaperRolls(accessible);
        }

        return removed;
    }

    @Override
    protected PaperRollsGrid readInput(Path path) throws IOException {
        var grid = InputReader.readAsCharGrid(path);
        return new PaperRollsGrid(new CharGrid(grid));
    }
}
