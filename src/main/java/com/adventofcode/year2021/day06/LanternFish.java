package com.adventofcode.year2021.day06;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.TextSplitter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@DayPuzzle(year = 2021, day = 6)
public class LanternFish extends AbstractPuzzle<List<Fish>> {
    static {
        puzzle = LanternFish.class;
    }

    @Override
    protected long partOne(List<Fish> input) {
        return simulateFishLifecycleMap(input, 80);
    }

    @Override
    protected long partTwo(List<Fish> input) {
        return simulateFishLifecycleMap(input, 256);
    }

    @Override
    protected List<Fish> readInput(Path path) throws IOException {
        String content = Files.readString(path, Charset.defaultCharset());
        return TextSplitter.onComma(content).map(Short::parseShort).map(Fish::new).toList();
    }

    @SuppressWarnings("unused")
    private static int simulateFishLifecycleList(List<Fish> initial, int days) {
        List<Fish> fishes = initial.stream()
                .map(Fish::copy)
                .collect(Collectors.toCollection(LinkedList::new));

        for (int i = 0; i < days; i++) {
            List<Fish> iteration = fishes.stream()
                    .map(Fish::process)
                    .flatMap(Optional::stream)
                    .collect(Collectors.toCollection(LinkedList::new));

            fishes.addAll(iteration);
        }

        return fishes.size();
    }

    private static long simulateFishLifecycleMap(List<Fish> initial, int days) {
        Map<Fish, Long> fishes = initial.stream()
                .map(Fish::copy)
                .collect(Collectors.toMap(Function.identity(), fish -> 1L, Long::sum));

        for (int i = 0; i < days; i++) {
            Map<Fish, Long> iteration = new HashMap<>();

            fishes.forEach((fish, count) -> {
                fish.process().ifPresent(child -> iteration.merge(child, count, Long::sum));
                iteration.merge(fish, count, Long::sum);
            });

            fishes = iteration;
        }

        return fishes.values().stream().mapToLong(n -> n).sum();
    }
}
