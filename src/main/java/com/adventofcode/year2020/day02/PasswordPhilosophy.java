package com.adventofcode.year2020.day02;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

@DayPuzzle(year = 2020, day = 2)
public class PasswordPhilosophy extends AbstractPuzzle<List<PasswordPolicy>> {
    private static final Pattern PATTERN = Pattern.compile("^([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)$");

    static {
        puzzle = PasswordPhilosophy.class;
    }

    @Override
    protected long partOne(List<PasswordPolicy> input) {
        return countValidPasswordsMinMax(input);
    }

    @Override
    protected long partTwo(List<PasswordPolicy> input) {
        return countValidPasswordsExactMatch(input);
    }

    @Override
    protected List<PasswordPolicy> readInput(Path path) {
        return InputReader.readAsLineList(path, PATTERN, matcher -> {
            int min = Integer.parseInt(matcher.group(1));
            int max = Integer.parseInt(matcher.group(2));
            char character = matcher.group(3).charAt(0);
            String password = matcher.group(4);
            return new PasswordPolicy(min, max, character, password);
        });
    }

    private static long countValidPasswordsMinMax(List<PasswordPolicy> passwords) {
        return passwords.stream().filter(password -> {
            long characterCount = password.password().chars().filter(c -> c == password.character()).count();
            return password.min() <= characterCount && characterCount <= password.max();
        }).count();
    }

    private static long countValidPasswordsExactMatch(List<PasswordPolicy> passwords) {
        return passwords.stream().filter(password -> {
            char characterA = password.password().charAt(password.min() - 1);
            char characterB = password.password().charAt(password.max() - 1);
            return characterA == password.character() ^ characterB == password.character();
        }).count();
    }
}
