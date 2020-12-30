package com.adventofcode.year2020.day13;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.codepoetics.protonpack.StreamUtils;
import com.google.common.primitives.Ints;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.LongStream;

@DayPuzzle(year = 2020, day = 13)
public class ShuttleSearch extends AbstractPuzzle<Schedule> {
    static {
        puzzle = ShuttleSearch.class;
    }

    @Override
    protected long partOne(Schedule input) {
        return discoverEarliestBus(input).orElseThrow();
    }

    @Override
    protected long partTwo(Schedule input) {
        return discoverEarliestSubsequentTimestamp(input);
    }

    @Override
    protected Schedule readInput(Path path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            int timestamp = Integer.parseInt(reader.readLine());
            List<Integer> buses = Arrays.stream(reader.readLine().split(",")).map(Ints::tryParse).toList();
            return new Schedule(timestamp, buses);
        }
    }

    private static Optional<Long> discoverEarliestBus(Schedule schedule) {
        Optional<Congruence> earliest = schedule.buses().stream()
                .filter(Objects::nonNull)
                .map(bus -> new Congruence(bus, getWaitTime(bus, schedule.timestamp())))
                .min(Comparator.comparingLong(Congruence::remainder));

        return earliest.map(e -> e.modulo() * e.remainder());
    }

    private static long discoverEarliestSubsequentTimestamp(Schedule schedule) {
        List<Congruence> congruences = StreamUtils.zipWithIndex(schedule.buses().stream())
                .filter(bus -> bus.getValue() != null)
                .map(bus -> new Congruence(bus.getValue(), getWaitTime(bus.getValue(), bus.getIndex())))
                .toList();

        long product = congruences.stream()
                .map(Congruence::modulo)
                .reduce(1L, Math::multiplyExact);

        long result = congruences.stream()
                .mapToLong(congruence -> {
                    long partial = product / congruence.modulo();
                    long inverse = modularMultiplicativeInverse(partial, congruence.modulo());
                    return congruence.remainder() * partial * inverse;
                })
                .sum();

        return result % product;
    }

    private static long getWaitTime(long bus, long timestamp) {
        return (bus - timestamp % bus) % bus;
    }

    private static long modularMultiplicativeInverse(long a, long m) {
        return LongStream.range(1L, m).filter(n -> a * n % m == 1L).findFirst().orElse(1L);
    }
}
