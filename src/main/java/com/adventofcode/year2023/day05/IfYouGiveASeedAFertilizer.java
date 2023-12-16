package com.adventofcode.year2023.day05;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;
import com.adventofcode.common.input.TextSplitter;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@DayPuzzle(year = 2023, day = 5)
public class IfYouGiveASeedAFertilizer extends AbstractPuzzle<Almanac> {
    private static final Pattern PATTERN = Pattern.compile("^seeds:((?: \\d+)+)$");

    static {
        puzzle = IfYouGiveASeedAFertilizer.class;
    }

    @Override
    protected long partOne(Almanac input) {
        return findLowestLocationNumber(input, input.seeds().stream());
    }

    @Override
    protected long partTwo(Almanac input) {
        return findLowestLocationNumber(input, generateSeedNumbers(input.seeds()));
    }

    @Override
    protected Almanac readInput(Path path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            Matcher matcher = InputReader.matchPattern(PATTERN, reader.readLine());
            reader.readLine();

            List<Long> seeds = TextSplitter.onSpace(matcher.group(1)).map(Long::valueOf).toList();

            List<CategoryMap> categoryMaps = new LinkedList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                String categoryName = line;
                List<CategoryRange> categoryRanges = new LinkedList<>();

                while (StringUtils.isNotBlank(line = reader.readLine())) {
                    List<Long> range = TextSplitter.onSpace(line).map(Long::valueOf).toList();
                    categoryRanges.add(new CategoryRange(range.get(0), range.get(1), range.get(2)));
                }

                categoryMaps.add(new CategoryMap(categoryName, categoryRanges));
            }

            return new Almanac(seeds, categoryMaps);
        }
    }

    private static long findLowestLocationNumber(Almanac almanac, Stream<Long> seeds) {
        return seeds.mapToLong(almanac::convert).min().orElseThrow();
    }

    private static Stream<Long> generateSeedNumbers(List<Long> seeds) {
        return IntStream.iterate(0, i -> i < seeds.size(), i -> i + 2).boxed()
                .flatMap(i -> {
                    long seedRangeStart = seeds.get(i);
                    long seedRangeLength = seeds.get(i + 1);
                    long seedRangeEnd = seedRangeStart + seedRangeLength;
                    return LongStream.range(seedRangeStart, seedRangeEnd).boxed();
                })
                .parallel();
    }
}
