package com.adventofcode.year2022.day02;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.regex.Pattern;

@DayPuzzle(year = 2022, day = 2)
public class RockPaperScissors extends AbstractPuzzle<List<Round>> {
    private static final Pattern PATTERN = Pattern.compile("^([ABC]) ([XYZ])$");

    static {
        puzzle = RockPaperScissors.class;
    }

    @Override
    protected long partOne(List<Round> input) {
        return calculateTotalScoreAccordingToStrategyGuide(input, Round::scorePartOne);
    }

    @Override
    protected long partTwo(List<Round> input) {
        return calculateTotalScoreAccordingToStrategyGuide(input, Round::scorePartTwo);
    }

    @Override
    protected List<Round> readInput(Path path) {
        return InputReader.readAsLineList(path, PATTERN, matcher -> new Round(matcher.group(1), matcher.group(2)));
    }

    private int calculateTotalScoreAccordingToStrategyGuide(List<Round> rounds, ToIntFunction<Round> strategy) {
        return rounds.stream().mapToInt(strategy).sum();
    }
}
