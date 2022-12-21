package com.adventofcode.year2022.day01;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.TextSplitter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@DayPuzzle(year = 2022, day = 1)
public class CalorieCounting extends AbstractPuzzle<List<List<Integer>>> {
    static {
        puzzle = CalorieCounting.class;
    }

    @Override
    protected long partOne(List<List<Integer>> input) {
        return sumCaloriesOfTopElves(input, 1);
    }

    @Override
    protected long partTwo(List<List<Integer>> input) {
        return sumCaloriesOfTopElves(input, 3);
    }

    @Override
    protected List<List<Integer>> readInput(Path path) throws IOException {
        String content = Files.readString(path, Charset.defaultCharset());
        return TextSplitter.onPattern("\\n{2}", content)
                .map(calories -> TextSplitter.onNewLine(calories).map(Integer::parseInt).toList())
                .toList();
    }

    private static int sumCaloriesOfTopElves(List<List<Integer>> elvesCalories, int top) {
        return elvesCalories.stream()
                .map(elf -> elf.stream().reduce(0, Integer::sum))
                .sorted(Collections.reverseOrder())
                .limit(top)
                .reduce(0, Integer::sum);
    }
}
