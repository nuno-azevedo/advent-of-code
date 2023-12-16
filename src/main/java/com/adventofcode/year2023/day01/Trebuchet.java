package com.adventofcode.year2023.day01;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@DayPuzzle(year = 2023, day = 1)
public class Trebuchet extends AbstractPuzzle<List<String>> {
    static {
        puzzle = Trebuchet.class;
    }

    @Override
    protected long partOne(List<String> input) {
        return sumCalibrationValues(input, Trebuchet::extractDigits);
    }

    @Override
    protected long partTwo(List<String> input) {
        return sumCalibrationValues(input, Trebuchet::replaceLettersWithDigits);
    }

    @Override
    protected List<String> readInput(Path path) {
        return InputReader.readAsLineList(path);
    }

    private static int sumCalibrationValues(List<String> lines, Function<String, String> digitsExtractor) {
        return lines.stream()
                .map(digitsExtractor)
                .filter(Predicate.not(String::isEmpty))
                .map(digits -> String.format("%c%c", digits.charAt(0), digits.charAt(digits.length() - 1)))
                .mapToInt(Integer::parseInt)
                .sum();
    }

    private static String extractDigits(String line) {
        String regex = "\\d";

        return Pattern.compile(regex).matcher(line).results()
                .map(MatchResult::group)
                .collect(Collectors.joining());
    }

    private static String replaceLettersWithDigits(String line) {
        Map<String, String> lettersToDigits = Map.of(
                "one", "1",
                "two", "2",
                "three", "3",
                "four", "4",
                "five", "5",
                "six", "6",
                "seven", "7",
                "eight", "8",
                "nine", "9"
        );

        String regex = lettersToDigits.keySet().stream()
                .collect(Collectors.joining("|", "(?=(\\d|", "))"));

        return Pattern.compile(regex).matcher(line).results()
                .map(matchResult -> matchResult.group(1))
                .map(digit -> lettersToDigits.getOrDefault(digit, digit))
                .collect(Collectors.joining());
    }
}
