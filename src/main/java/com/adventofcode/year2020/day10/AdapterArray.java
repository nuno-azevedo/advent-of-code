package com.adventofcode.year2020.day10;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;
import com.google.common.collect.Iterables;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@DayPuzzle(year = 2020, day = 10)
public class AdapterArray extends AbstractPuzzle<List<Integer>> {
    private static final short MARGIN = 3;
    private static final List<Integer> TRIBONACCI = List.of(0, 1, 1, 2, 4, 7, 13);

    static {
        puzzle = AdapterArray.class;
    }

    @Override
    protected long partOne(List<Integer> input) {
        return chainAllAdapters(input);
    }

    @Override
    protected long partTwo(List<Integer> input) {
        return countArrangementsTribonacci(input);
    }

    @Override
    protected List<Integer> readInput(Path path) {
        List<Integer> adapters = InputReader.readAsLineStream(path)
                .map(Integer::valueOf)
                .sorted()
                .collect(Collectors.toList());

        adapters.addFirst(0);
        adapters.addLast(Iterables.getLast(adapters) + MARGIN);

        return adapters;
    }

    private static int chainAllAdapters(List<Integer> adapters) {
        int last = 0;
        int diff1Volts = 0;
        int diff3Volts = 0;

        for (int adapter : adapters) {
            switch (adapter - last) {
                case 1 -> diff1Volts++;
                case 3 -> diff3Volts++;
            }
            last = adapter;
        }

        return diff1Volts * diff3Volts;
    }

    private static long countArrangementsTribonacci(List<Integer> adapters) {
        int last = 0;
        byte contiguous = 1;
        long arrangements = 1L;

        for (int adapter : adapters) {
            if (adapter == last + 1) {
                contiguous++;
            } else {
                arrangements *= TRIBONACCI.get(contiguous);
                contiguous = 1;
            }
            last = adapter;
        }

        return arrangements;
    }

    @SuppressWarnings("unused")
    private static long countArrangementsRecursiveCached(List<Integer> adapters, Map<Integer, Long> cache, int index) {
        if (index == adapters.size() - 1) {
            return 1L;
        }

        long arrangements = 0L;

        for (int i = index + 1; i < adapters.size(); i++) {
            int adapter = adapters.get(i);
            int last = adapters.get(index);
            if (adapter - last > MARGIN) {
                break;
            }
            arrangements += cache.computeIfAbsent(i, k -> countArrangementsRecursiveCached(adapters, cache, k));
        }

        return arrangements;
    }
}
