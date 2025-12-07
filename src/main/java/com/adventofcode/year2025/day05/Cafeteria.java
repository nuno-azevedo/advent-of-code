package com.adventofcode.year2025.day05;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.domain.Range;
import com.adventofcode.common.input.InputReader;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.regex.Pattern;

@DayPuzzle(year = 2025, day = 5)
public class Cafeteria extends AbstractPuzzle<IngredientDatabase> {
    private static final Pattern PATTERN = Pattern.compile("^(\\d+)-(\\d+)$");

    static {
        puzzle = Cafeteria.class;
    }

    @Override
    protected long partOne(IngredientDatabase input) {
        return input.countAvailableAndFreshIngredients();
    }

    @Override
    protected long partTwo(IngredientDatabase input) {
        return input.countFreshIngredients();
    }

    @Override
    protected IngredientDatabase readInput(Path path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String line;

            var freshIngredients = new LinkedList<Range<Long>>();
            while (StringUtils.isNotBlank(line = reader.readLine())) {
                var matcher = InputReader.matchPattern(PATTERN, line);
                var range = new Range<>(Long.parseLong(matcher.group(1)), Long.parseLong(matcher.group(2)));
                freshIngredients.add(range);
            }

            var availableIngredients = new LinkedList<Long>();
            while (StringUtils.isNotBlank(line = reader.readLine())) {
                availableIngredients.add(Long.parseLong(line));
            }

            return new IngredientDatabase(freshIngredients, availableIngredients);
        }
    }
}
