package com.adventofcode.year2021.day14;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@DayPuzzle(year = 2021, day = 14)
public class ExtendedPolymerization extends AbstractPuzzle<PolymerFormula> {
    private static final Pattern PATTERN = Pattern.compile("^([A-Z]{2}) -> ([A-Z])$");

    static {
        puzzle = ExtendedPolymerization.class;
    }

    @Override
    protected long partOne(PolymerFormula input) {
        return applyStepsOfPairInsertionMap(input, 10);
    }

    @Override
    protected long partTwo(PolymerFormula input) {
        return applyStepsOfPairInsertionMap(input, 40);
    }

    @Override
    protected PolymerFormula readInput(Path path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String template = reader.readLine();
            reader.readLine();

            Map<String, String> insertionRules = reader.lines()
                    .map(line -> InputReader.matchPattern(PATTERN, line))
                    .collect(Collectors.toMap(m -> m.group(1), m -> m.group(2)));

            return new PolymerFormula(template, insertionRules);
        }
    }

    @SuppressWarnings("unused")
    private static long applyStepsOfPairInsertionString(PolymerFormula polymerFormula, int steps) {
        String polymer = polymerFormula.template();

        for (int i = 0; i < steps; i++) {
            polymer = applyInsertionRulesString(polymer, polymerFormula.insertionRules());
        }

        Map<Integer, Long> frequencies = polymer.chars().boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        LongSummaryStatistics stats = frequencies.values().stream()
                .mapToLong(i -> i)
                .summaryStatistics();

        return stats.getMax() - stats.getMin();
    }

    private static long applyStepsOfPairInsertionMap(PolymerFormula polymerFormula, int steps) {
        Map<String, Long> pairsCountMap = new HashMap<>();

        for (int i = 0; i < polymerFormula.template().length() - 1; i++) {
            String pair = polymerFormula.template().substring(i, i + 2);
            pairsCountMap.merge(pair, 1L, Long::sum);
        }

        for (int i = 0; i < steps; i++) {
            pairsCountMap = applyInsertionRulesMap(pairsCountMap, polymerFormula.insertionRules());
        }

        Map<Character, Long> frequencies = new HashMap<>();
        pairsCountMap.forEach((key, value) -> {
            frequencies.merge(key.charAt(0), value, Long::sum);
            frequencies.merge(key.charAt(1), value, Long::sum);
        });

        LongSummaryStatistics stats = frequencies.values().stream()
                .mapToLong(i -> i)
                .summaryStatistics();

        return Math.round((double) stats.getMax() / 2) - Math.round((double) stats.getMin() / 2);
    }

    private static String applyInsertionRulesString(String sequence, Map<String, String> rules) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < sequence.length(); i++) {
            builder.append(sequence.charAt(i));
            String pair = sequence.substring(i, Math.min(i + 2, sequence.length()));
            Optional.ofNullable(rules.get(pair)).ifPresent(builder::append);
        }

        return builder.toString();
    }

    private static Map<String, Long> applyInsertionRulesMap(Map<String, Long> pairs, Map<String, String> rules) {
        Map<String, Long> pairsCountMap = new HashMap<>();

        pairs.forEach((pair, count) -> Optional.ofNullable(rules.get(pair)).ifPresentOrElse(
                insert -> {
                    pairsCountMap.merge(pair.charAt(0) + insert, count, Long::sum);
                    pairsCountMap.merge(insert + pair.charAt(1), count, Long::sum);
                },
                () -> pairsCountMap.merge(pair, count, Long::sum)
        ));

        return pairsCountMap;
    }
}
