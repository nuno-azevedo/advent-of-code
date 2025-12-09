package com.adventofcode.year2025.day07;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.grid.CharGrid;
import com.adventofcode.common.input.InputReader;

import java.io.IOException;
import java.nio.file.Path;

@DayPuzzle(year = 2025, day = 7)
public class Laboratories extends AbstractPuzzle<TachyonManifold> {
    static {
        puzzle = Laboratories.class;
    }

    @Override
    protected long partOne(TachyonManifold input) {
        return input.countBeamSplits(false);
    }

    @Override
    protected long partTwo(TachyonManifold input) {
        return input.countBeamSplits(true);
    }

    @Override
    protected TachyonManifold readInput(Path path) throws IOException {
        var diagram = InputReader.readAsCharGrid(path);
        return new TachyonManifold(new CharGrid(diagram));
    }
}
