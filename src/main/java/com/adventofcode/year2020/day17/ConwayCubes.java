package com.adventofcode.year2020.day17;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;
import com.google.common.primitives.Chars;

import java.nio.file.Path;
import java.util.List;

@DayPuzzle(year = 2020, day = 17)
public class ConwayCubes extends AbstractPuzzle<List<List<List<Character>>>> {
    private static final char ACTIVE = '#';
    private static final char INACTIVE = '.';
    private static final int CYCLES = 6;

    static {
        puzzle = ConwayCubes.class;
    }

    @Override
    protected long partOne(List<List<List<Character>>> input) {
        return simulate(input);
    }

    @Override
    protected long partTwo(List<List<List<Character>>> input) {
        return 0;
    }

    @Override
    protected List<List<List<Character>>> readInput(Path path) {
        List<List<Character>> slice = InputReader.readAsLineList(path, line -> Chars.asList(line.toCharArray()));
        return List.of(slice);
    }

    private static int simulate(List<List<List<Character>>> pocket) {
        for (int i = 0; i < 6; i++) {
            List<List<List<Character>>> clone = pocket.stream()
                    .map(slice -> slice.stream().map(List::copyOf).toList())
                    .toList();

            for (int z = 0; z < pocket.size(); z++) {
                for (int y = 0; y < pocket.get(z).size(); y++) {
                    for (int x = 0; x < pocket.get(z).get(y).size(); x++) {
                        // TODO
                    }
                }
            }
        }

        return 0;
    }

    private static int countActiveNeighbours(List<List<List<Character>>> pocket, int x, int y, int z) {
        return 0;
    }
}
