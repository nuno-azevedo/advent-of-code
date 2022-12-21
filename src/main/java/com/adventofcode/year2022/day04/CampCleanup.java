package com.adventofcode.year2022.day04;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.domain.Pair;
import com.adventofcode.common.domain.Range;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

@DayPuzzle(year = 2022, day = 4)
public class CampCleanup extends AbstractPuzzle<List<Pair<Range<Integer>, Range<Integer>>>> {
    private static final Pattern PATTERN = Pattern.compile("^(\\d+)-(\\d+),(\\d+)-(\\d+)$");

    static {
        puzzle = CampCleanup.class;
    }

    @Override
    protected long partOne(List<Pair<Range<Integer>, Range<Integer>>> input) {
        return countPairsWhichFullyOverlap(input);
    }

    @Override
    protected long partTwo(List<Pair<Range<Integer>, Range<Integer>>> input) {
        return countPairsWhichOverlap(input);
    }

    @Override
    protected List<Pair<Range<Integer>, Range<Integer>>> readInput(Path path) {
        return InputReader.readAsLineList(path, PATTERN, matcher -> new Pair<>(
                Range.between(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))),
                Range.between(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)))
        ));
    }

    private static long countPairsWhichFullyOverlap(List<Pair<Range<Integer>, Range<Integer>>> assignmentPairs) {
        return assignmentPairs.stream()
                .filter(pair -> pair.left().contains(pair.right()) || pair.right().contains(pair.left()))
                .count();
    }

    private static long countPairsWhichOverlap(List<Pair<Range<Integer>, Range<Integer>>> assignmentPairs) {
        return assignmentPairs.stream()
                .filter(pair -> pair.left().overlaps(pair.right()))
                .count();
    }
}
