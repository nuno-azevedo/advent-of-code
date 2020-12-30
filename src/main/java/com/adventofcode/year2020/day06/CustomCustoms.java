package com.adventofcode.year2020.day06;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@DayPuzzle(year = 2020, day = 6)
public class CustomCustoms extends AbstractPuzzle<List<List<String>>> {
    static {
        puzzle = CustomCustoms.class;
    }

    @Override
    protected long partOne(List<List<String>> input) {
        return countAnyoneGroupsAnswers(input);
    }

    @Override
    protected long partTwo(List<List<String>> input) {
        return countEveryoneGroupsAnswers(input);
    }

    @Override
    protected List<List<String>> readInput(Path path) throws IOException {
        String content = Files.readString(path, Charset.defaultCharset());

        return Arrays.stream(content.split("\\n{2}"))
                .map(lines -> Arrays.asList(lines.split("\\n")))
                .toList();
    }

    private static long countAnyoneGroupsAnswers(List<List<String>> groupsAnswers) {
        return groupsAnswers.stream()
                .mapToLong(group -> group.stream().flatMapToInt(CharSequence::chars).distinct().count())
                .sum();
    }

    private static long countEveryoneGroupsAnswers(List<List<String>> groupsAnswers) {
        return groupsAnswers.stream()
                .mapToLong(group -> group.stream()
                        .map(person -> person.chars().boxed().collect(Collectors.toSet()))
                        .reduce(Sets::intersection)
                        .orElseGet(Set::of)
                        .size())
                .sum();
    }
}
