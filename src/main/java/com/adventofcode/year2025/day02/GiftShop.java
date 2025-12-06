package com.adventofcode.year2025.day02;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;
import com.adventofcode.common.input.TextSplitter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

@DayPuzzle(year = 2025, day = 2)
public class GiftShop extends AbstractPuzzle<List<ProductIDRange>> {
    private static final Pattern PATTERN = Pattern.compile("^(\\d+)-(\\d+)$");

    static {
        puzzle = GiftShop.class;
    }

    @Override
    protected long partOne(List<ProductIDRange> input) {
        return input.stream()
                .map(ProductIDRange::getSequenceRepeatedTwice)
                .flatMap(List::stream)
                .reduce(0L, Long::sum);
    }

    @Override
    protected long partTwo(List<ProductIDRange> input) {
        return input.stream()
                .map(ProductIDRange::getSequenceRepeatedAtLeastTwice)
                .flatMap(List::stream)
                .reduce(0L, Long::sum);
    }

    @Override
    protected List<ProductIDRange> readInput(Path path) throws IOException {
        return TextSplitter.onComma(Files.readString(path))
                .map(line -> InputReader.matchPattern(PATTERN, line))
                .map(matcher -> {
                    long firstID = Long.parseLong(matcher.group(1));
                    long lastID = Long.parseLong(matcher.group(2));
                    return new ProductIDRange(firstID, lastID);
                })
                .toList();
    }
}
